/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

export function selectOrderType(orderTypes) {
	return new Promise((proceed, stop) => {
		openModal({
			bodyHTML: `
				<div class="form-group" id="orderTypeSelection">
					<div class="alert alert-info">
						${Liferay.Language.get('commerce-order-type-info')}
					</div>
					
					<div class="my-4">
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
					label: Liferay.Language.get('add-order'),
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
}
