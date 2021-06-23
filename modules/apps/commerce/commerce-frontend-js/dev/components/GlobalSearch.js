/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import launcher from '../../../src/main/resources/META-INF/resources/components/global_search/entry';

import '../../../src/main/resources/META-INF/resources/styles/main.scss';

launcher('global-search', 'global-search-root', {
	accountURLTemplate: '/account-page/{id}',
	accountsSearchURLTemplate: '/accounts?search={query}',
	channelId: 41117,
	globalSearchURLTemplate: '/global?search={query}',
	orderURLTemplate: '/order-page/{id}',
	ordersSearchURLTemplate: '/orders?search={query}',
	productURLTemplate: '/product-page/{id}',
	productsSearchURLTemplate: '/products?search={query}',
	spritemap: './assets/icons.svg',
});
