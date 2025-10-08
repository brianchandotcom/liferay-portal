/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {Col, Row} from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {StatusRenderer} from '@liferay/frontend-data-set-web';
import classnames from 'classnames';
import {
	AccountUtils,
	CommerceNotificationUtils,
	AccountSelectionModal,
	CommerceServiceProvider,
	commerceEvents,
} from 'commerce-frontend-js';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

const DeliveryCatalogResource = CommerceServiceProvider.DeliveryCatalogAPI('v1');

const ACCOUNT_ENTRY_ID_DEFAULT = 0;

function AccountSelectorButton({
	account,
	accountEntryAllowedTypes,
	checkoutURL,
	label,
	order,
	setCurrentAccountURL,
	showAccountImage,
	showAccountInfo,
	showButtonIcon,
	showOrderInfo,
	...props
}) {
	const [availableAccounts, setAvailableAccounts] = useState([]);
	const [currentOrder, setCurrentOrder] = useState({
		...order,
		id: order?.id || order?.orderId || 0,
	});
	const [currentUser, setCurrentUser] = useState({});

	const currentAccount = useMemo(() => ({
		...account,
		id: account?.id ?? ACCOUNT_ENTRY_ID_DEFAULT,
	}), [account]);

	const commerceChannelId = useMemo(() =>
		Liferay?.CommerceContext?.commerceChannelId || 0, []);

	const changeAccount = useCallback(async (account, doCheckout) => {
		try {
			await AccountUtils.selectAccount(account.id, setCurrentAccountURL);

			if (doCheckout) {
				window.location.href = checkoutURL;
			}
			else {
				Liferay.fire(commerceEvents.CURRENT_ACCOUNT_UPDATED, {
					id: account.id,
				});
			}
		} catch(error) {
			CommerceNotificationUtils.showErrorNotification(error);
		}
	}, [checkoutURL, setCurrentAccountURL]);

	const updateCurrentOrder = useCallback(
		({order}) => {
			if (!currentOrder || currentOrder.id !== order.id) {
				setCurrentOrder((current) => ({...current, ...order}));
			}
		},
		[currentOrder, setCurrentOrder]
	);

	useEffect(() => {
		DeliveryCatalogResource.getAccountsByChannelId(commerceChannelId)
			.then((response) => {
				setCurrentUser(response);

				if (response.items.length) {
					setAvailableAccounts(response.items);
				}
			})
			.catch((error) => CommerceNotificationUtils.showErrorNotification(error.message));
	}, [commerceChannelId]);

	useEffect(() => {
		Liferay.on(commerceEvents.CURRENT_ORDER_DELETED, updateCurrentOrder);
		Liferay.on(commerceEvents.CURRENT_ORDER_UPDATED, updateCurrentOrder);

		return () => {
			Liferay.detach(
				commerceEvents.CURRENT_ORDER_DELETED,
				updateCurrentOrder
			);
			Liferay.detach(
				commerceEvents.CURRENT_ORDER_UPDATED,
				updateCurrentOrder
			);
		};
	}, [updateCurrentOrder]);

	return (
		<>
		<ClayButton
			{...props}
			className={classnames(
				'align-items-center btn-account-selector d-flex',
				currentAccount?.id === ACCOUNT_ENTRY_ID_DEFAULT && 'account-selected'
			)}
			displayType="unstyled"
		>
			{currentAccount?.id ? (
				<>
					{showAccountImage ? (
						<ClaySticker
							className="current-account-thumbnail"
							shape="user-icon"
						>
							{currentAccount.logoURL ? (
								<ClaySticker.Image
									alt={account.name}
									src={account.logoURL}
								/>
							) : (
								currentAccount.name
									.split(' ')
									.map((chunk) =>
										chunk.charAt(0).toUpperCase()
									)
									.join('')
							)}
						</ClaySticker>
					) : null}

					<div className="d-flex flex-column mx-2">
						{showAccountInfo ? (
							<div className="account-name">
								<span className="text-truncate-inline">
									<span className="text-truncate">
										{currentAccount.name}
									</span>
								</span>
							</div>
						) : null}

						{showOrderInfo ? (
							<div className="d-flex order-info">
								{currentOrder?.id ? (
									<>
										<span className="order-id">
											{order.id}
										</span>
										<span className="col order-label">
											<StatusRenderer
												value={
													currentOrder?.workflowStatusInfo
												}
											/>
										</span>
									</>
								) : (
									<Row>
										<Col>
											{Liferay.Language.get(
												'there-is-no-order-selected'
											)}
										</Col>
									</Row>
								)}
							</div>
						) : null}
					</div>
				</>
			) : (
				<div className="mr-2 no-account-selected-placeholder">
					<span className="text-truncate-inline">
						<span className="text-truncate">{label}</span>
					</span>
				</div>
			)}

			{showButtonIcon ? (
				<ClayIcon
					className="account-selector-button-icon"
					symbol="angle-down"
				/>
			) : null}
		</ClayButton>

			{!!availableAccounts.length &&
			 AccountUtils.shouldSelectAccount(currentAccount.id, currentOrder.id) ? (
				<AccountSelectionModal
					accountEntryAllowedTypes={accountEntryAllowedTypes}
					availableAccounts={availableAccounts}
					changeAccount={changeAccount}
					checkoutURL={checkoutURL}
					commerceChannelId={commerceChannelId}
					hasCreatePermission={!!currentUser.actions?.create}
				/>
			) : null}
		</>
	);
}

export default AccountSelectorButton;
