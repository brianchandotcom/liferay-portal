/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createPortletURL, openModal} from 'frontend-js-web';

const DeliveryGroupFDSPropsTransformer = (props) => ({
	...props,
	onActionDropdownItemClick: ({
		action: {
			data: {action: actionId, viewDeliveryGroupURL},
		},
		itemData: {addressId, deliveryDate, name},
	}) => {
		if (actionId === 'view') {
			openModal({
				buttons: [
					{
						autofocus: true,
						label: Liferay.Language.get('ok'),
						onClick: ({processClose}) => {
							processClose();
						},
						type: 'button',
					},
				],
				height: '32rem',
				iframeBodyCssClass: '',
				size: 'default',
				title: name,
				url: createPortletURL(viewDeliveryGroupURL, {
					addressId,
					deliveryDate,
					deliveryGroupName: name,
				}),
			});
		}
	},
});

export default DeliveryGroupFDSPropsTransformer;
