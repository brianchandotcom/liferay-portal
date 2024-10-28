/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useState} from 'react';

import './account_selector.scss';
import ServiceProvider from '../../ServiceProvider/index';
import {
	CURRENT_ACCOUNT_UPDATED,
	CURRENT_ORDER_DELETED,
	CURRENT_ORDER_UPDATED,
} from '../../utilities/eventsDefinitions';
import {showErrorNotification} from '../../utilities/notifications';
import Trigger from './Trigger';
import {VIEWS} from './util/constants';
import {selectAccount} from './util/index';
import AccountsListView from './views/AccountsListView';
import OrdersListView from './views/OrdersListView';

const DeliveryCatalogAPIServiceProvider =
	ServiceProvider.DeliveryCatalogAPI('v1');

function AccountSelector({
	accountEntryAllowedTypes,
	alignmentPosition,
	commerceChannelId,
	createNewOrderURL,
	currentCommerceAccount: account,
	currentCommerceOrder: order,
	namespace,
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
	const [currentUser, setCurrentUser] = useState({});

	useEffect(() => {
		const accountsApi = new URL(
			`${themeDisplay.getPathContext()}${DeliveryCatalogAPIServiceProvider.baseURL(
				commerceChannelId
			)}`,
			themeDisplay.getPortalURL()
		);

		fetch(accountsApi.toString())
			.then((response) => response.json())
			.then((response) => {
				setCurrentUser(response);
			})
			.catch((error) => showErrorNotification(error.message));
	}, [commerceChannelId]);

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
				setCurrentOrder((current) =>
					order.id === 0 ? {id: order.id} : {...current, ...order}
				);
			}
		},
		[currentOrder, setCurrentOrder]
	);

	useEffect(() => {
		Liferay.on(CURRENT_ORDER_UPDATED, updateOrderModel);
		Liferay.on(CURRENT_ORDER_DELETED, updateOrderModel);

		return () => {
			Liferay.detach(CURRENT_ORDER_DELETED, updateOrderModel);
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
					accountEntryAllowedTypes={accountEntryAllowedTypes}
					changeAccount={changeAccount}
					commerceChannelId={commerceChannelId}
					currentAccount={currentAccount}
					currentUser={currentUser}
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
					namespace={namespace}
					selectOrderURL={selectOrderURL}
					setCurrentView={setCurrentView}
					showOrderTypeModal={showOrderTypeModal}
				/>
			)}
		</ClayDropDown>
	);
}

AccountSelector.propTypes = {
	accountEntryAllowedTypes: PropTypes.array.isRequired,
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
	namespace: PropTypes.string,
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
