/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const placedOrders = {
	items: [
		{
			accountId: 11832440,
			customFields: {
				'cloud-provisioning': JSON.stringify([
					{
						deployments: [{id: 'aaaaa'}],
						orderItemId: 37185998,
					},
				]),
			},
			id: 37185997,
			orderTypeId: 12914873,
			placedOrderItems: [
				{
					customFields: {
						'cloud-provisioning': JSON.stringify([
							{
								deployments: [{id: 'aaaaa'}],
								orderItemId: 37185998,
							},
						]),
					},
					id: 37185998,
					productId: 35343140,
				},
			],
		},
	],
};
export default placedOrders;
