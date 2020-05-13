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
import {fetch, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import LanguageSelector from './LanguageSelector';

const FriendlyURLHistoryModal = ({
	defaultLanguageId,
	deleteFriendlyURLEntryLocalizationURL,
	friendlyURLEntryLocalizationsURL,
	initialLanguageId,
	observer,
	portletNamespace,
}) => {
	const [languageId, setLanguageId] = useState();
	const [loading, setLoading] = useState(true);
	const [
		friendlyURLEntryLocalizations,
		setFriendlyURLEntryLocalizations,
	] = useState({});
	const [availableLanguages, setAvailableLanguages] = useState([]);
	const isMounted = useIsMounted();

	useEffect(() => {
		fetch(friendlyURLEntryLocalizationsURL)
			.then((response) => response.json())
			.then((response) => {
				if (isMounted()) {
					setAvailableLanguages(Object.keys(response));
					setFriendlyURLEntryLocalizations(response);
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [friendlyURLEntryLocalizationsURL, isMounted]);

	useEffect(() => {
		if (loading) {
			let selectedLanguageId;

			if (friendlyURLEntryLocalizations[initialLanguageId]) {
				selectedLanguageId = initialLanguageId;
			}
			else if (friendlyURLEntryLocalizations[defaultLanguageId]) {
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

	const handleDeleteFriendlyUrl = (deleteFriendlyURLEntryId) => {
		const formData = new FormData();

		formData.append(
			`${portletNamespace}friendlyURLEntryId`,
			deleteFriendlyURLEntryId
		);

		formData.append(`${portletNamespace}languageId`, languageId);

		fetch(deleteFriendlyURLEntryLocalizationURL, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((response) => {
				if (response.success) {
					setFriendlyURLEntryLocalizations(
						(friendlyURLEntryLocalizations) => {
							friendlyURLEntryLocalizations[
								languageId
							].history = friendlyURLEntryLocalizations[
								languageId
							].history.filter(
								({friendlyURLEntryId}) =>
									friendlyURLEntryId !=
									deleteFriendlyURLEntryId
							);

							return {...friendlyURLEntryLocalizations};
						}
					);
				}
				else {
					showToastError();
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
				showToastError();
			});
	};

	const showToastError = () => {
		openToast({
			message: Liferay.Language.get('an-unexpected-error-occurred'),
			title: Liferay.Language.get('error'),
			type: 'danger',
		});
	};

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
															className="d-none"
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
};

export default FriendlyURLHistoryModal;
