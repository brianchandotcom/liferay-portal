/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const productResponseMock = {
	items: [
		{
			catalogName: 'Liferay Recife',
			categories: [
				{
					id: 100,
					name: 'Navigation and Discovery',
				},
			],
			createDate: '2025-01-01T00:00:00Z',
			description: 'My App',
			id: 111,
			images: [
				{
					priority: 0,
					src: 'https://marketplace.liferay.com/my-app/logo.png',
				},
			],
			name: 'Map for Objects',
			productId: 999999,
			productSpecifications: [
				{
					specificationKey: 'price-model',
					value: 'Free',
				},
			],
			skus: [
				{
					id: 101,
					productId: 112,
				},
			],
			urlImage: 'https://marketplace.liferay.com/my-app/logo.png',
		},
	],
};
