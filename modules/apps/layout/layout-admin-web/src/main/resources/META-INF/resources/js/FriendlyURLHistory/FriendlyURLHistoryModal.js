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

import {ClaySelect} from '@clayui/form';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

const FriendlyURLHistoryModal = ({
	defaultLanguageId,
	friendlyURLEntryLocalizationslURL,
	initialLanguageId,
	observer,
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
		fetch(friendlyURLEntryLocalizationslURL)
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
	}, [friendlyURLEntryLocalizationslURL, isMounted]);

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
						<p>
							<ClaySelect
								disabled={availableLanguages.length < 2}
								onChange={({target: {value}}) => {
									setLanguageId(value);
								}}
								value={languageId}
							>
								{availableLanguages.map((localeId) => (
									<ClaySelect.Option
										key={localeId}
										label={localeId}
										value={localeId}
									/>
								))}
							</ClaySelect>
						</p>

						<ClayList
							className="show-quick-actions-one-line"
							showQuickActionsOnHover
						>
							<ClayList.Header>Active URL</ClayList.Header>
							<ClayList.Item flex>
								<ClayList.ItemField expand>
									<ClayList.ItemText className="text-truncate">
										{
											friendlyURLEntryLocalizations[
												languageId
											].current.urlTitle
										}
									</ClayList.ItemText>
								</ClayList.ItemField>
							</ClayList.Item>
							<ClayList.Header>Old Friendly URLs</ClayList.Header>
							{friendlyURLEntryLocalizations[
								languageId
							].history.map(({friendlyURLEntryId, urlTitle}) => (
								<ClayList.Item flex key={friendlyURLEntryId}>
									<ClayList.ItemField expand>
										<ClayList.ItemText className="text-truncate">
											{urlTitle}
										</ClayList.ItemText>
									</ClayList.ItemField>
									<ClayList.ItemField>
										<ClayList.QuickActionMenu>
											<ClayList.QuickActionMenu.Item
												onClick={() =>
													alert('Clicked the reload!')
												}
												symbol="reload"
											/>
											<ClayList.QuickActionMenu.Item
												onClick={() =>
													alert('Clicked the circle!')
												}
												symbol="times-circle"
											/>
										</ClayList.QuickActionMenu>
									</ClayList.ItemField>
								</ClayList.Item>
							))}
						</ClayList>
					</>
				)}
			</ClayModal.Body>
		</ClayModal>
	);
};

FriendlyURLHistoryModal.propTypes = {
	defaultLanguageId: PropTypes.string.isRequired,
	friendlyURLEntryLocalizationslURL: PropTypes.string.isRequired,
	observer: PropTypes.object.isRequired,
};

export default FriendlyURLHistoryModal;
