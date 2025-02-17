/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SKU} from '../../../src/main/resources/META-INF/resources/js/types';

const skus = [
	{
		id: 1010,
		price: {
			priceFormatted: '$10.00',
		},
		purchasable: true,
	},
	{
		id: 1020,
		price: {
			priceFormatted: '$20.00',
		},
		purchasable: false,
	},
] as SKU[];

export default skus;
