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

import {fetch} from 'frontend-js-web';

const RESOURCE_ENDPOINT = '/o/commerce-ui/wish-list-item';

const LegacyWishListResource = {

	/**
	 * @param accountId (required)
	 * @param cpDefinitionId (required)
	 * @param skuId (optional)
	 * @returns {Promise<any | {success: boolean}>}
	 */
	toggleInWishList({accountId = '0', cpDefinitionId, skuId = '0'}) {
		if (!cpDefinitionId) {
			return Promise.resolve({success: false});
		}

		const formData = new FormData();

		formData.append('commerceAccountId', accountId);
		formData.append('groupId', Liferay.ThemeDisplay.getScopeGroupId());
		formData.append('productId', cpDefinitionId);
		formData.append('skuId', skuId);
		formData.append('options', '[]');

		return fetch(RESOURCE_ENDPOINT, {
			body: formData,
			credentials: 'include',
			headers: new Headers({'x-csrf-token': window.Liferay.authToken}),
			method: 'POST',
		})
			.then((response) => response.json())
			.catch(() => ({success: false}));
	},
};

export default LegacyWishListResource;
