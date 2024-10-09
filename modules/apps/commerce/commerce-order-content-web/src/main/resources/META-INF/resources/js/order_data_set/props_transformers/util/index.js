/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CommerceServiceProvider} from 'commerce-frontend-js';
import {openModal, openToast} from 'frontend-js-web';

const DeliveryOrderAPI = CommerceServiceProvider.DeliveryOrderAPI('v1');
const DeliveryCartAPI = CommerceServiceProvider.DeliveryCartAPI('v1');

export function openOrderNameModal({dataSetId, isOpen, orderId, orderName}) {
	const update = isOpen
		? DeliveryCartAPI.updateCartById
		: DeliveryOrderAPI.updatePlacedOrderById;

	openModal({
		bodyHTML: `
			<div class="form-group" id="editOrderName">
				<label for="orderNameInput">
					${Liferay.Language.get('name')}
				</label>
			
				<input 
					class="form-control" 
					id="orderNameInput" 
					maxlength="30"
					placeholder="${Liferay.Language.get('name')}" 
					type="text" 
					value="${orderName}"
				/>
			</div>
		`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick: ({processClose}) => {
					processClose();
				},
				type: 'button',
			},
			{
				displayType: 'primary',
				label: Liferay.Language.get('save'),
				onClick: ({processClose}) => {
					const orderNameInputElement =
						document.querySelector('#orderNameInput');

					if (orderNameInputElement) {
						update(orderId, {
							name: orderNameInputElement.value,
						})
							.then(() => {
								window.top.Liferay.fire('fds-update-display', {
									id: dataSetId,
								});

								processClose();
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
					else {
						openToast({
							message: Liferay.Language.get(
								'an-unexpected-error-occurred'
							),
							type: 'danger',
						});
					}
				},
				type: 'button',
			},
		],
		title: Liferay.Language.get('rename-order'),
	});
}
