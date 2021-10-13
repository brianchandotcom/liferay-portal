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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useRef} from 'react';

import ServiceProvider from '../../../ServiceProvider/index';
import Sticker from '../Sticker';
import {VIEWS} from '../util/constants';
import EmptyListView from './EmptyListView';
import ListView from './ListView';

const {baseURL: ACCOUNTS_RESOURCE_ENDPOINT} = ServiceProvider.AdminAccountAPI(
	'v1'
);

function AccountsListView({
	accountEntryAllowedTypes,
	changeAccount,
	currentAccount,
	disabled,
	setCurrentView,
}) {
	const accountsListRef = useRef();

	const apiUrl = new URL(
		ACCOUNTS_RESOURCE_ENDPOINT,
		themeDisplay.getPortalURL()
	);

	const filterString = accountEntryAllowedTypes
		.map((accountEntryAllowedType) => `'${accountEntryAllowedType}'`)
		.join(', ');

	if (filterString) {
		apiUrl.searchParams.append('filter', `type in (` + filterString + ')');
	}

	return (
		<ClayDropDown.ItemList className="accounts-list-container">
			<ClayDropDown.Section className="item-list-head">
				<span className="text-truncate-inline">
					<span className="text-truncate">
						{Liferay.Language.get('select-account')}
					</span>
				</span>

				{currentAccount && (
					<ClayButtonWithIcon
						displayType="unstyled"
						onClick={() => setCurrentView(VIEWS.ORDERS_LIST)}
						symbol="angle-right-small"
					/>
				)}
			</ClayDropDown.Section>

			<ClayDropDown.Divider />

			<ClayDropDown.Section>
				<ListView
					apiUrl={apiUrl.toString()}
					contentWrapperRef={accountsListRef}
					customView={({items, loading}) => {
						if (!items || !items.length) {
							return (
								<EmptyListView
									caption={Liferay.Language.get(
										'no-accounts-were-found'
									)}
									loading={loading}
								/>
							);
						}

						return (
							<ClayDropDown.ItemList className="accounts-list">
								{items.map((account) => (
									<ClayDropDown.Item
										key={account.id}
										onClick={() => changeAccount(account)}
									>
										<Sticker
											className="current-account-thumbnail mr-2"
											{...account}
										/>

										<span className="ml-2 text-truncate-inline">
											<span className="text-truncate">
												{account.name}
											</span>
										</span>
									</ClayDropDown.Item>
								))}
							</ClayDropDown.ItemList>
						);
					}}
					disabled={disabled}
					placeholder={Liferay.Language.get('search')}
				/>
			</ClayDropDown.Section>

			<ClayDropDown.Divider />

			<li>
				<div ref={accountsListRef} />
			</li>
		</ClayDropDown.ItemList>
	);
}

export default AccountsListView;
