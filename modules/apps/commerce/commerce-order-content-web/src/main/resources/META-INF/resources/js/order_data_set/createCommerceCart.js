/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openModal, openToast} from 'frontend-js-web';

const DeliveryCartAPI = CommerceServiceProvider.DeliveryCartAPI('v1');

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

const createCommerceCart = async ({
	accountId,
	commerceChannelId,
	currencyCode,
	orderDetailURL,
	orderTypes,
}) => {
	const onBeforeCreate =
		orderTypes.length > 1
			? openOrderTypeSelectionModal
			: () => Promise.resolve(null);

	onBeforeCreate(orderTypes)
		.then((orderTypeId) => {
			const commerceCart = {accountId, currencyCode};

			if (orderTypeId) {
				commerceCart.orderTypeId = orderTypeId;
			}

			return DeliveryCartAPI.createCartByChannelId(
				commerceChannelId,
				commerceCart
			);
		})
		.then(({id: cartId = null}) => {
			if (cartId) {
				window.location.href = `${orderDetailURL}${cartId}`;
			}
		})
		.catch(({message}) => {
			if (message !== 'cancel') {
				openToast({
					message:
						message ||
						Liferay.Language.get('an-unexpected-error-occurred'),
					type: 'danger',
				});
			}
		});
};

export default function ({additionalProps, orderTypes}) {
	const handler = () => createCommerceCart({...additionalProps, orderTypes});

	Liferay.on('addCommerceOrder', handler);

	return {
		dispose: () => {
			Liferay.detach('addCommerceOrder', handler);
		},
	};
}
