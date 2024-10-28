/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider, commerceEvents} from 'commerce-frontend-js';
import {openConfirmModal, openToast, sub} from 'frontend-js-web';

import {openOrderNameModal} from './util';

const DeliveryCartAPI = CommerceServiceProvider.DeliveryCartAPI('v1');

const PendingOrdersFDSPropsTransformer = (props) => ({
	...props,
	onActionDropdownItemClick: ({
		action: {
			data: {id: actionId},
		},
		itemData: {accountId, id: cartId, name: orderName},
		loadData,
	}) => {
		if (actionId === 'delete') {
			openConfirmModal({
				message: `${sub(
					Liferay.Language.get(
						'are-you-sure-you-want-to-delete-order-x'
					),
					cartId
				)}\n${Liferay.Language.get('this-operation-cannot-be-undone')}`,
				onConfirm: (isConfirmed = false) => {
					if (isConfirmed) {
						DeliveryCartAPI.deleteCartById(cartId)
							.then(() => {
								loadData();

								openToast({
									message: Liferay.Language.get(
										'your-request-completed-successfully'
									),
									type: 'success',
								});

								Liferay.fire(commerceEvents.CART_RESET, {
									accountId,
									id: cartId,
								});
							})
							.catch(() => {
								openToast({
									message: Liferay.Language.get(
										'an-unexpected-error-occurred'
									),
									type: 'danger',
								});
							});
					}
				},
				title: sub(Liferay.Language.get('delete-order-x'), cartId),
			});
		}

		if (actionId === 'place-order') {
			DeliveryCartAPI.getCartTransitionsById(cartId)
				.then(({items: cartTransitions}) => {
					let isCheckoutAvailable = false;
					let isSubmitAvailable = false;

					cartTransitions.forEach(({name}) => {
						if (name === 'checkout') {
							isCheckoutAvailable = true;
						}
						else if (name === 'submit') {
							isSubmitAvailable = true;
						}
					});

					if (isCheckoutAvailable) {
						const checkoutActionURL = new URL(
							props.additionalProps.checkoutActionURL
						);

						checkoutActionURL.searchParams.set(
							'commerceOrderId',
							cartId
						);

						window.location.href = checkoutActionURL.toString();
					}
					else if (isSubmitAvailable) {
						DeliveryCartAPI.executeCartTransitionsById(cartId, {
							name: 'submit',
						}).then(({cartId}) => {
							window.location.href = `${props.additionalProps.orderDetailURL}${cartId}`;
						});
					}
					else {
						window.location.href = `${props.additionalProps.orderDetailURL}${cartId}`;
					}
				})
				.catch((error) => {
					openToast({
						message:
							error.message ||
							Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
						type: 'danger',
					});
				});
		}

		if (actionId === 'rename') {
			openOrderNameModal({
				dataSetId: props.id,
				isOpen: true,
				orderId: cartId,
				orderName,
			});
		}
	},
	onBulkActionItemClick: ({
		action: {
			data: {id: actionId},
		},
		loadData,
		selectedData: {items: selectedCarts},
	}) => {
		if (actionId === 'delete') {
			openConfirmModal({
				message: `${Liferay.Language.get(
					'are-you-sure-you-want-to-delete-the-selected-orders'
				)}\n${Liferay.Language.get('this-operation-cannot-be-undone')}`,
				onConfirm: (isConfirmed) => {
					if (isConfirmed) {
						DeliveryCartAPI.deleteCartsById(selectedCarts)
							.then(() => {
								setTimeout(() => {
									loadData();

									openToast({
										message: Liferay.Language.get(
											'your-request-completed-successfully'
										),
										type: 'success',
									});

									const {orderId: activeCart} =
										Liferay?.CommerceContext?.order;

									const includesActiveCart =
										!!selectedCarts.find(
											({id}) => id === activeCart
										);

									if (includesActiveCart) {
										Liferay.fire(
											commerceEvents.CURRENT_ORDER_DELETED,
											{
												accountId: parseInt(
													props.additionalProps
														.accountId,
													10
												),
												id: 0,
												order: {id: 0},
											}
										);
									}
								}, 500);
							})
							.catch((error) => {
								openToast({
									message:
										error.message ||
										Liferay.Language.get(
											'an-unexpected-error-occurred'
										),
									type: 'danger',
								});
							});
					}
				},
			});
		}
	},
});

export default PendingOrdersFDSPropsTransformer;
