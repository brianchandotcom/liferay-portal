/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useMemo, useRef} from 'react';

import ServiceProvider from '../../../ServiceProvider/index';
import {OPEN_MODAL} from '../../../utilities/eventsDefinitions';
import {liferayNavigate} from '../../../utilities/index';
import {MEDIUM_MODAL_SIZE} from '../../../utilities/modals/constants';
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
	namespace,
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
							? Liferay.fire(OPEN_MODAL, {
									id: `${namespace}add-order-modal`,
									size: MEDIUM_MODAL_SIZE,
							  })
							: liferayNavigate(createOrderURL)
					}
				>
					{Liferay.Language.get('create-new-order')}
				</ClayButton>
			</ClayDropDown.Section>

			{showOrderTypeModal ? (
				<Modal
					id={`${namespace}add-order-modal`}
					refreshPageOnClose={true}
					url={createOrderURL}
				/>
			) : null}
		</ClayDropDown.ItemList>
	);
}

export default OrdersListView;
