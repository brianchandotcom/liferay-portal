/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useMemo, useRef} from 'react';

import ServiceProvider from '../../../ServiceProvider/index';
import {createCommerceCart} from '../../../utilities/createCommerceCart';
import OrdersTable from '../OrdersTable';
import {VIEWS} from '../util/constants';
import EmptyListView from './EmptyListView';
import ListView from './ListView';

function OrdersListView({
	commerceChannelId,
	createOrderURL,
	currencyCode,
	currentAccount,
	disabled,
	hasAddCommerceOrderPermission,
	selectOrderURL,
	setCurrentView,
}) {
	const CartResource = useMemo(
		() => ServiceProvider.DeliveryCartAPI('v1'),
		[]
	);

	const ordersListRef = useRef();

	return (
		<ClayDropDown.ItemList className="orders-list-container" role="none">
			<ClayDropDown.Section className="item-list-head">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('back-to-accounts')}
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
					apiUrl={CartResource.cartsByAccountIdAndChannelIdURL(
						currentAccount.id,
						commerceChannelId
					)}
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

			<ClayDropDown.ItemList className="orders-list" role="none">
				<ClayDropDown.Section>
					<div ref={ordersListRef} />
				</ClayDropDown.Section>
			</ClayDropDown.ItemList>

			<ClayDropDown.Section>
				<ClayButton
					block
					disabled={!hasAddCommerceOrderPermission}
					onClick={(event) => {
						event.preventDefault();

						createCommerceCart({
							accountId: currentAccount.id,
							commerceChannelId,
							currencyCode,
							orderDetailURL: createOrderURL,
						});
					}}
				>
					{Liferay.Language.get('create-new-order')}
				</ClayButton>
			</ClayDropDown.Section>
		</ClayDropDown.ItemList>
	);
}

export default OrdersListView;
