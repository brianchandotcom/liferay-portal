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
	defaultLocaleId,
	friendlyURLEntryLocalizationslURL,
	initialLanguageId,
	observer,
}) => {
	const [locale, setLocale] = useState();
	const [loading, setLoading] = useState(true);
	const [
		friendlyURLEntryLocalizations,
		setFriendlyURLEntryLocalizations,
	] = useState({});
	const [availableLocales, setAvailableLocales] = useState([]);
	const isMounted = useIsMounted();

	useEffect(() => {
		fetch(friendlyURLEntryLocalizationslURL)
			.then((response) => response.json())
			.then((response) => {
				if (isMounted()) {
					setAvailableLocales(Object.keys(response));
					setFriendlyURLEntryLocalizations(response);
					setLoading(false);
				}
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}
			});
	}, [friendlyURLEntryLocalizationslURL, isMounted]);

	useEffect(() => {
		if (!loading) {
			let selectedLanguageId;

			if (friendlyURLEntryLocalizations[initialLanguageId]) {
				selectedLanguageId = initialLanguageId;
			}
			else if (friendlyURLEntryLocalizations[defaultLocaleId]) {
				selectedLanguageId = defaultLocaleId;
			}
			else {
				selectedLanguageId = availableLocales[0];
			}

			setLocale(selectedLanguageId);
		}
	}, [
		availableLocales,
		defaultLocaleId,
		friendlyURLEntryLocalizations,
		initialLanguageId,
		loading,
	]);

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
				{loading || !locale ? (
					<ClayLoadingIndicator />
				) : (
					<>
						<p>
							<ClaySelect
								disabled={availableLocales.length < 2}
								onChange={({target: {value}}) => {
									setLocale(value);
								}}
								value={locale}
							>
								{availableLocales.map((localeId) => (
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
												locale
											].current.urlTitle
										}
									</ClayList.ItemText>
								</ClayList.ItemField>
							</ClayList.Item>
							<ClayList.Header>Old Friendly URLs</ClayList.Header>
							{friendlyURLEntryLocalizations[locale].history.map(
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
													onClick={() =>
														alert(
															'Clicked the reload!'
														)
													}
													symbol="reload"
												/>
												<ClayList.QuickActionMenu.Item
													onClick={() =>
														alert(
															'Clicked the circle!'
														)
													}
													symbol="times-circle"
												/>
											</ClayList.QuickActionMenu>
										</ClayList.ItemField>
									</ClayList.Item>
								)
							)}
						</ClayList>
					</>
				)}
			</ClayModal.Body>
		</ClayModal>
	);
};

FriendlyURLHistoryModal.propTypes = {
	defaultLocaleId: PropTypes.string.isRequired,
	friendlyURLEntryLocalizationslURL: PropTypes.string.isRequired,
	observer: PropTypes.object.isRequired,
};

export default FriendlyURLHistoryModal;
