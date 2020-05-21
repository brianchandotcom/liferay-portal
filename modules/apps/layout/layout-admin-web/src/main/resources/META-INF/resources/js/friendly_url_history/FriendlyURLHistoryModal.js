/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch, objectToFormData, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import LanguageSelector from './LanguageSelector';

function logError(error) {
	if (process.env.NODE_ENV === 'development' && error) {
		console.error(error);
	}
}

const showToastError = () => {
	openToast({
		message: Liferay.Language.get('an-unexpected-error-occurred'),
		title: Liferay.Language.get('error'),
		type: 'danger',
	});
};

const FriendlyURLHistoryModal = ({
	defaultLanguageId,
	deleteFriendlyURLEntryLocalizationURL,
	friendlyURLEntryLocalizationsURL,
	initialLanguageId,
	observer,
	portletNamespace,
	restoreFriendlyURLEntryLocalizationURL,
}) => {
	const [languageId, setLanguageId] = useState();
	const [loading, setLoading] = useState(true);
	const [
		friendlyURLEntryLocalizations,
		setFriendlyURLEntryLocalizations,
	] = useState({});
	const [availableLanguages, setAvailableLanguages] = useState([]);
	const isMounted = useIsMounted();

	const getFriendlyUrlLocalizations = useCallback(() => {
		fetch(friendlyURLEntryLocalizationsURL)
			.then((response) => response.json())
			.then((response) => {
				if (isMounted()) {
					setAvailableLanguages(
						Object.entries(response).reduce(
							(acc, [language, {current}]) => {
								if (current && current.urlTitle) {
									acc.push(language);
								}

								return acc;
							},
							[]
						)
					);

					setFriendlyURLEntryLocalizations(response);
				}
			})
			.catch((error) => {
				logError(error);
				showToastError();
			});
	}, [friendlyURLEntryLocalizationsURL, isMounted]);

	useEffect(() => {
		getFriendlyUrlLocalizations();
	}, [getFriendlyUrlLocalizations]);

	useEffect(() => {
		if (loading) {
			let selectedLanguageId;

			if (
				friendlyURLEntryLocalizations[initialLanguageId] &&
				availableLanguages.includes(initialLanguageId)
			) {
				selectedLanguageId = initialLanguageId;
			}
			else if (
				friendlyURLEntryLocalizations[defaultLanguageId] &&
				availableLanguages.includes(defaultLanguageId)
			) {
				selectedLanguageId = defaultLanguageId;
			}
			else {
				selectedLanguageId = availableLanguages[0];
			}

			setLanguageId(selectedLanguageId);
		}
	}, [
		availableLanguages,
		defaultLanguageId,
		friendlyURLEntryLocalizations,
		initialLanguageId,
		loading,
	]);

	useEffect(() => {
		if (
			loading &&
			friendlyURLEntryLocalizations &&
			languageId &&
			friendlyURLEntryLocalizations[languageId]
		) {
			setLoading(false);
		}
	}, [friendlyURLEntryLocalizations, loading, languageId]);

	const sendRequest = useCallback(
		(url, friendlyURLEntryId) => {
			return fetch(url, {
				body: objectToFormData({
					[`${portletNamespace}friendlyURLEntryId`]: friendlyURLEntryId,
					[`${portletNamespace}languageId`]: languageId,
				}),
				method: 'POST',
			})
				.then((response) => response.json())
				.catch((error) => {
					logError(error);
					showToastError();
				});
		},
		[languageId, portletNamespace]
	);

	const handleDeleteFriendlyUrl = useCallback(
		(deleteFriendlyURLEntryId) => {
			sendRequest(
				deleteFriendlyURLEntryLocalizationURL,
				deleteFriendlyURLEntryId
			).then(({success} = {}) => {
				if (success) {
					setFriendlyURLEntryLocalizations(
						(friendlyURLEntryLocalizations) => ({
							...friendlyURLEntryLocalizations,
							[languageId]: {
								...friendlyURLEntryLocalizations[languageId],
								history: friendlyURLEntryLocalizations[
									languageId
								].history.filter(
									({friendlyURLEntryId}) =>
										friendlyURLEntryId !=
										deleteFriendlyURLEntryId
								),
							},
						})
					);
				}
				else {
					showToastError();
				}
			});
		},
		[deleteFriendlyURLEntryLocalizationURL, languageId, sendRequest]
	);

	const handleRestoreFriendlyUrl = useCallback(
		(restoreFriendlyUrlEntryId, urlTitle) => {
			sendRequest(
				restoreFriendlyURLEntryLocalizationURL,
				restoreFriendlyUrlEntryId
			).then(({success} = {}) => {
				if (isMounted() && success) {
					getFriendlyUrlLocalizations();

					const inputComponent = Liferay.component(
						`${portletNamespace}friendlyURL`
					);

					if (inputComponent.getSelectedLanguageId() === languageId) {
						inputComponent.updateInput(urlTitle);
					}
					else {
						inputComponent.updateInputLanguage(
							urlTitle,
							languageId
						);
					}
				}
				else {
					showToastError();
				}
			});
		},
		[
			getFriendlyUrlLocalizations,
			isMounted,
			languageId,
			portletNamespace,
			restoreFriendlyURLEntryLocalizationURL,
			sendRequest,
		]
	);

	return (
		<ClayModal
			className="portlet-layouts-admin-url-history-modal"
			observer={observer}
			size="md"
		>
			<ClayModal.Header>
				{Liferay.Language.get('history')}
			</ClayModal.Header>

			<ClayModal.Body>
				{loading ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<div className="language-selector-container">
							<LanguageSelector
								defaultLanguageId={defaultLanguageId}
								languageIds={availableLanguages}
								onChange={(value) => {
									setLanguageId(value);
								}}
								selectedLanguageId={languageId}
							/>
						</div>

						<div className="active-url">
							<div className="active-url-tite">
								{Liferay.Language.get('active-url')}
							</div>
							<p className="active-url-text">
								{
									friendlyURLEntryLocalizations[languageId]
										.current.urlTitle
								}
							</p>
						</div>

						<ClayList
							className="show-quick-actions-one-line"
							showQuickActionsOnHover
						>
							{friendlyURLEntryLocalizations[languageId].history
								.length > 0 && (
								<>
									<ClayList.Header>
										{Liferay.Language.get(
											'old-friendly-urls'
										)}
									</ClayList.Header>
									{friendlyURLEntryLocalizations[
										languageId
									].history.map(
										({friendlyURLEntryId, urlTitle}) => (
											<ClayList.Item
												flex
												key={friendlyURLEntryId}
											>
												<ClayList.ItemField expand>
													<ClayList.ItemText className="text-truncate">
														{urlTitle}
													</ClayList.ItemText>
												</ClayList.ItemField>
												<ClayList.ItemField>
													<ClayList.QuickActionMenu>
														<ClayList.QuickActionMenu.Item
															className="lfr-portal-tooltip"
															data-title={Liferay.Language.get(
																'restore-url'
															)}
															onClick={() => {
																handleRestoreFriendlyUrl(
																	friendlyURLEntryId,
																	urlTitle
																);
															}}
															symbol="reload"
														/>
														<ClayList.QuickActionMenu.Item
															className="lfr-portal-tooltip"
															data-title={Liferay.Language.get(
																'forget-url'
															)}
															onClick={() => {
																handleDeleteFriendlyUrl(
																	friendlyURLEntryId
																);
															}}
															symbol="times-circle"
														/>
													</ClayList.QuickActionMenu>
												</ClayList.ItemField>
											</ClayList.Item>
										)
									)}
								</>
							)}
						</ClayList>
					</>
				)}
			</ClayModal.Body>
		</ClayModal>
	);
};

FriendlyURLHistoryModal.propTypes = {
	defaultLanguageId: PropTypes.string.isRequired,
	deleteFriendlyURLEntryLocalizationURL: PropTypes.string.isRequired,
	friendlyURLEntryLocalizationsURL: PropTypes.string.isRequired,
	observer: PropTypes.object.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	restoreFriendlyURLEntryLocalizationURL: PropTypes.string.isRequired,
};

export default FriendlyURLHistoryModal;
