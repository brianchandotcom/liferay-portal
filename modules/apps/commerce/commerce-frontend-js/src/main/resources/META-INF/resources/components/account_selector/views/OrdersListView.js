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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useMemo, useRef} from 'react';

import ServiceProvider from '../../../ServiceProvider/index';
import {OPEN_MODAL} from '../../../utilities/eventsDefinitions';
import {liferayNavigate} from '../../../utilities/index';
import Modal from '../../modal/Modal';
import OrdersTable from '../OrdersTable';
import {VIEWS} from '../util/constants';
import EmptyListView from './EmptyListView';
import ListView from './ListView';

function OrdersListView({
	commerceChannelId,
	createOrderURL,
	currentAccount,
	disabled,
	selectOrderURL,
	setCurrentView,
	showOrderTypeModal,
}) {
	const CartResource = useMemo(
		() => ServiceProvider.DeliveryCartAPI('v1'),
		[]
	);

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

			<li>
				<div ref={ordersListRef} />
			</li>

			<ClayDropDown.Section>
				<ClayButton
					className="m-auto w-100"
					displayType="primary"
					onClick={() =>
						showOrderTypeModal
							? Liferay.fire(OPEN_MODAL, {id: 'add-order-modal'})
							: liferayNavigate(createOrderURL)
					}
				>
					{Liferay.Language.get('create-new-order')}
				</ClayButton>
			</ClayDropDown.Section>

			{showOrderTypeModal ? (
				<Modal
					id="add-order-modal"
					refreshPageOnClose={true}
					url={createOrderURL}
				/>
			) : null}
		</ClayDropDown.ItemList>
	);
}

export default OrdersListView;
