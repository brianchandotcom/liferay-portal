/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {WithLiferay} from '../../../liferay/index';
import {Product} from '../../../types/product';

export async function getProductDetails({
	friendlyUrlPath,
	liferay,
	nestedFields,
}: WithLiferay<{
	friendlyUrlPath: string;
	nestedFields: string;
}>) {
	try {
		const response = await liferay.fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${liferay.getChannel().id}/products/by-friendly-url-path/${friendlyUrlPath}?nestedFields=${nestedFields}`
		);

		return {
			data: (await response.json()) as Product,
		};
	}
	catch (error) {
		return {data: null, error};
	}
}
