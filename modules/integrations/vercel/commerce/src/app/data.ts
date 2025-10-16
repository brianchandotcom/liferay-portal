/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {WithLiferay} from '../liferay/index';
import {PageProduct} from '../types/product';
import {SearchBuilder} from './search-builder/SearchBuilder';

export async function getProductsPage({
	keywords,
	liferay,
	page,
	pageSize,
	specificationValues,
}: WithLiferay<{
	keywords: string | undefined;
	page: string;
	pageSize: string;
	specificationValues: string[];
}>) {
	const searchBuider = new SearchBuilder();

	if (specificationValues.length) {
		specificationValues.forEach((specificationValue, index) => {
			const lastIndex = index + 1 === specificationValues.length;

			searchBuider.any('specificationValues', {
				operator: 'contains',
				value: specificationValue,
			});

			if (!lastIndex) {
				searchBuider.or();
			}
		});
	}

	const searchParams = new URLSearchParams({
		...(specificationValues && {filter: searchBuider.build()}),
		nestedFields: 'skus',
		page,
		pageSize,
		search: keywords || '',
	});

	try {
		const response = await liferay.fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${liferay.getChannel().id}/products?${searchParams.toString()}`
		);

		return {
			data: (await response.json()) as unknown as PageProduct,
		};
	}
	catch (error) {
		return {
			error,
		};
	}
}
