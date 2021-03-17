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
import ClayLink from '@clayui/link';
import React, {useRef} from 'react';

import ServiceProvider from '../../../ServiceProvider/index';
import OrdersTable from '../OrdersTable';
import {VIEWS} from '../util/constants';
import {composeFilterByAccountId} from '../util/index';
import EmptyListView from './EmptyListView';
import ListView from './ListView';

const {baseURL: ORDERS_RESOURCE_ENDPOINT} = ServiceProvider.AdminOrderAPI('v1');

function OrdersListView({
	createOrderURL,
	currentAccount,
	disabled,
	selectOrderURL,
	setCurrentView,
}) {
	const ordersListRef = useRef();

	return (
		<ClayDropDown.ItemList className="orders-list-container">
			<ClayDropDown.Section className="item-list-head">
				<ClayButtonWithIcon
					displayType="unstyled"
					onClick={() => setCurrentView(VIEWS.ACCOUNTS_LIST)}
					symbol="angle-left-small"
				/>

				<span className="text-truncate-inline">
					<span className="text-truncate">{currentAccount.name}</span>
				</span>
			</ClayDropDown.Section>

			<ClayDropDown.Divider />

			<ClayDropDown.Section className="item-list-body">
				<ListView
					apiUrl={`${ORDERS_RESOURCE_ENDPOINT}?${composeFilterByAccountId(
						currentAccount.id
					)}`}
					contentWrapperRef={ordersListRef}
					customView={({items, loading}) => {
						if (!items || !items.length) {
							return (
								<EmptyListView
									caption={Liferay.Language.get(
										'no-orders-were-found'
									)}
									loading={loading}
								/>
							);
						}

						return (
							<OrdersTable
								orders={items}
								selectOrderURL={selectOrderURL}
							/>
						);
					}}
					disabled={disabled}
					placeholder={Liferay.Language.get('search-order')}
				/>
			</ClayDropDown.Section>

			<ClayDropDown.Divider />

			<li>
				<div ref={ordersListRef} />
			</li>

			<ClayDropDown.Section>
				<ClayLink
					className="btn btn-primary d-block"
					displayType="unstyled"
					href={createOrderURL}
				>
					{Liferay.Language.get('create-new-order')}
				</ClayLink>
			</ClayDropDown.Section>
		</ClayDropDown.ItemList>
	);
}

export default OrdersListView;
