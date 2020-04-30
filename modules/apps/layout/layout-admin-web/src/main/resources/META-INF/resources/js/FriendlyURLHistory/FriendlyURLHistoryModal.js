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
import ClayModal from '@clayui/modal';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const FriendlyURLHistoryModal = ({
	availableLocales,
	observer,
	siteDefaultLocaleId,
	urls,
}) => {
	const [locale, setLocale] = useState(siteDefaultLocaleId);

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
				<p>
					<ClaySelect
						onChange={({target: {value}}) => {
							setLocale(value);
						}}
					>
						{availableLocales.map(({displayName, localeId}) => (
							<ClaySelect.Option
								key={localeId}
								label={displayName}
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
								{urls[locale].current.urlTitle}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
					<ClayList.Header>Old Friendly URLs</ClayList.Header>
					{(urls[locale].history || []).map(
						({friendlyURLEntryId, urlTitle}) => (
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
						)
					)}
				</ClayList>
			</ClayModal.Body>
		</ClayModal>
	);
};

FriendlyURLHistoryModal.propTypes = {
	availableLocales: PropTypes.object.isRequired,
	observer: PropTypes.object.isRequired,
	siteDefaultLocaleId: PropTypes.string.isRequired,
	urls: PropTypes.object.isRequired,
};

export default FriendlyURLHistoryModal;
