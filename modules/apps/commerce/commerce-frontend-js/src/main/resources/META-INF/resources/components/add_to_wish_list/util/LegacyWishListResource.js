/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		}).then((response) => response.json());
	},
};

export default LegacyWishListResource;
