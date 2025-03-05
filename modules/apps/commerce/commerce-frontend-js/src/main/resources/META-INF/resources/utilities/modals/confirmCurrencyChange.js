/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openModal} from 'frontend-js-components-web';

import {createCommerceCart as createCartAndRedirect} from '../createCommerceCart';

export function confirmCurrencyChange({
	currencyCode,
	onCancel,
	orderDetailURL,
	...payload
}) {
	return new Promise((proceed) => {
		openModal({
			bodyHTML: `
				<div>
					<p>
						${Liferay.Language.get(
							'changing-your-currency-will-automatically-create-a-new-order-with-the-selected-currency.-your-current-order-will-be-saved-and-accessible.-are-you-sure-you-want-to-proceed'
						)}
					</p>
				</div>
			`,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('cancel'),
					onClick: ({processClose}) => {
						onCancel();
						proceed();
						processClose();
					},
					type: 'button',
				},
				{
					displayType: 'warning',
					label: Liferay.Language.get('proceed'),
					onClick: ({processClose}) => {
						processClose();

						createCartAndRedirect({
							...payload,
							currencyCode,
							onCancel,
							orderDetailURL,
						});
					},
					type: 'button',
				},
			],
			center: true,
			onClose: () => {
				onCancel();
				proceed();
			},
			status: 'warning',
			title: Liferay.Language.get('change-active-currency'),
		});
	});
}
