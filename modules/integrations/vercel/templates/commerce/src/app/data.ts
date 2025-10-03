/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getChannelProductsPage} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import {SearchBuilder} from 'odata-search-builder';

import {WithLiferay} from '../liferay/server';

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

	const filter = searchBuider.build();

	const response = await getChannelProductsPage({
		client: liferay.client,
		path: {
			channelId: liferay.getChannel().id,
		},
		query: {
			...(specificationValues && {filter}),
			nestedFields: 'skus',
			page,
			pageSize,
			search: keywords,
		},
	});

	return response;
}
