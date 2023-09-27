/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v1.0';

function resolveProductsPath(basePath, channelId) {
	return `${basePath}${VERSION}/channels/${channelId}/products`;
}

export default function Product(basePath) {
	return {
		getBaseURL: (channelId) => resolveProductsPath(basePath, channelId),
		getProductsByChannelId: (channelId, ...params) =>
			AJAX.GET(resolveProductsPath(basePath, channelId), ...params),
	};
}
