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

import ClayDropDown from '@clayui/drop-down';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import Trigger from './Trigger';
import {VIEWS} from './util/constants';
import {selectAccount} from './util/index';
import AccountsListView from './views/AccountsListView';
import OrdersListView from './views/OrdersListView';

function AccountSelector({
	accountEntryAllowedTypes,
	alignmentPosition,
	commerceChannelId,
	createNewOrderURL,
	currentCommerceAccount: account,
	currentCommerceOrder: order,
	refreshPageOnAccountSelected: forceRefresh,
	selectOrderURL,
	setCurrentAccountURL,
	showOrderTypeModal,
}) {
	const [active, setActive] = useState(false);
	const [currentAccount, setCurrentAccount] = useState(account);
	const [currentOrder, setCurrentOrder] = useState({
		...order,
		id: order?.orderId || 0,
	});
	const [currentView, setCurrentView] = useState(
		account ? VIEWS.ORDERS_LIST : VIEWS.ACCOUNTS_LIST
	);

	const changeAccount = (account) => {
		selectAccount(account.id, setCurrentAccountURL)
			.then(() => {
				if (forceRefresh) {
					window.location.reload();
				}
				else {
					Liferay.fire(CURRENT_ACCOUNT_UPDATED, {id: account.id});

					setCurrentAccount(account);
					setCurrentView(VIEWS.ORDERS_LIST);
					setCurrentOrder(null);
				}
			})
			.catch(showErrorNotification);
	};

	const updateOrderModel = useCallback(
		({order}) => {
			if (!currentOrder || currentOrder.id !== order.id) {
				setCurrentOrder((current) => ({...current, ...order}));
			}
		},
		[currentOrder, setCurrentOrder]
	);

	useEffect(() => {
		Liferay.on(CURRENT_ORDER_UPDATED, updateOrderModel);

		return () => {
			Liferay.detach(CURRENT_ORDER_UPDATED, updateOrderModel);
		};
	}, [updateOrderModel]);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={alignmentPosition}
			className="account-selector account-selector-dropdown"
			menuElementAttrs={{className: 'account-selector-dropdown-menu'}}
			onActiveChange={setActive}
			trigger={
				<Trigger
					active={active}
					currentAccount={currentAccount}
					currentOrder={currentOrder}
				/>
			}
		>
			{currentView === VIEWS.ACCOUNTS_LIST && (
				<AccountsListView
					accountEntryAllowedTypes={
						accountEntryAllowedTypes
							? JSON.parse(accountEntryAllowedTypes)
							: ''
					}
					changeAccount={changeAccount}
					currentAccount={currentAccount}
					disabled={!active}
					setCurrentView={setCurrentView}
				/>
			)}

			{currentView === VIEWS.ORDERS_LIST && (
				<OrdersListView
					commerceChannelId={commerceChannelId}
					createOrderURL={createNewOrderURL}
					currentAccount={currentAccount}
					disabled={!active}
					selectOrderURL={selectOrderURL}
					setCurrentView={setCurrentView}
					showOrderTypeModal={showOrderTypeModal}
				/>
			)}
		</ClayDropDown>
	);
}

AccountSelector.propTypes = {
	accountEntryAllowedTypes: PropTypes.string.isRequired,
	alignmentPosition: PropTypes.number,
	commerceChannelId: PropTypes.oneOfType([
		PropTypes.number,
		PropTypes.string,
	]),
	createNewOrderURL: PropTypes.string.isRequired,
	currentCommerceAccount: PropTypes.shape({
		id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
		logoURL: PropTypes.string,
		name: PropTypes.string,
	}),
	currentCommerceOrder: PropTypes.shape({
		orderId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
		workflowStatusInfo: PropTypes.shape({
			label_i18n: PropTypes.string,
		}),
	}),
	refreshPageOnAccountSelected: PropTypes.bool,
	selectOrderURL: PropTypes.string.isRequired,
	setCurrentAccountURL: PropTypes.string.isRequired,
	showOrderTypeModal: PropTypes.bool,
};

AccountSelector.defaultProps = {
	alignmentPosition: 3,
	currentCommerceOrder: {
		orderId: 0,
	},
	refreshPageOnAccountSelected: false,
};

export default AccountSelector;
