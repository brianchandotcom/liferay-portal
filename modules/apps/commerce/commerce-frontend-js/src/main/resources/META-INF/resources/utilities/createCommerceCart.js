/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal, openToast} from 'frontend-js-web';

import ServiceProvider from '../ServiceProvider/index';
import {DEFAULT_ORDER_DETAILS_PORTLET_ID} from '../components/mini_cart/util/constants';
import {liferayNavigate} from './index';

const DeliveryCartAPI = ServiceProvider.DeliveryCartAPI('v1');

const openOrderTypeSelectionModal = (orderTypes) =>
	new Promise((proceed, stop) => {
		openModal({
			bodyHTML: `
				<div class="form-group" id="orderTypeSelection">
					<label for="orderTypeSelect">
						${Liferay.Language.get('order-type')}
					</label>

					<select class="form-control" id="orderTypeSelect">
						${orderTypes
							.map(
								({name_i18n, orderTypeId}) =>
									`<option value="${orderTypeId}">${name_i18n}</option>`
							)
							.join('')}
					</select>
				</div>
			`,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					onClick: ({processClose}) => {
						processClose();
						stop(new Error('cancel'));
					},
					type: 'button',
				},
				{
					displayType: 'primary',
					label: Liferay.Language.get('submit'),
					onClick: ({processClose}) => {
						let orderTypeId = null;

						const orderTypeSelect =
							document.querySelector('#orderTypeSelect');

						if (orderTypeSelect) {
							orderTypeId = parseInt(
								orderTypeSelect.selectedOptions[0]?.value,
								10
							);
						}

						proceed(orderTypeId);
						processClose();
					},
					type: 'button',
				},
			],
			onClose: () => {
				stop(new Error('cancel'));
			},
			title: Liferay.Language.get('select-order-type'),
		});
	});

export function createCommerceCart({
	accountId,
	commerceChannelId,
	currencyCode,
	hasCommerceOpenOrderContentPortlet,
	orderDetailURL,
	orderTypes,
}) {
	const onBeforeCreate =
		orderTypes.length > 1
			? openOrderTypeSelectionModal
			: () => Promise.resolve(null);

	const createOrder = hasCommerceOpenOrderContentPortlet
		? (orderTypeId) => {
				const createOrderActionURL = new URL(orderDetailURL);

				if (orderTypeId) {
					createOrderActionURL.searchParams.set(
						`_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_commerceOrderTypeId`,
						String(orderTypeId)
					);
				}

				return liferayNavigate(createOrderActionURL);
			}
		: (orderTypeId) => {
				const commerceCart = {accountId, currencyCode};

				if (orderTypeId) {
					commerceCart.orderTypeId = orderTypeId;
				}

				return DeliveryCartAPI.createCartByChannelId(
					commerceChannelId,
					commerceCart
				)
					.then(({id: cartId = null}) => {
						if (cartId && !hasCommerceOpenOrderContentPortlet) {
							window.location.href = `${orderDetailURL}${cartId}`;
						}
					})
					.catch(({message}) => {
						if (message !== 'cancel') {
							openToast({
								message:
									message ||
									Liferay.Language.get(
										'an-unexpected-error-occurred'
									),
								type: 'danger',
							});
						}
					});
			};

	onBeforeCreate(orderTypes).then(createOrder);
}

export default function ({additionalProps, orderTypes}) {
	const handler = () => createCommerceCart({...additionalProps, orderTypes});

	Liferay.on('addCommerceOrder', handler);

	return {
		dispose: () => {
			Liferay.detach('addCommerceOrder', handler);
		},
	};
}
