/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ACCOUNTS_PATH = '/accounts';

const VERSION = 'v1.0';

function resolvePath(basePath = '', accountId) {
	return `${basePath}${VERSION}${ACCOUNTS_PATH}/${accountId}`;
}

export default function Account(basePath) {
	return {
		baseURL: resolvePath(basePath),

		getPostalAddresses: (accountId, ...params) =>
			AJAX.GET(
				resolvePath(basePath, accountId) + '/postal-addresses',
				...params
			),

		postPostalAddress: (accountId, json) =>
			AJAX.POST(
				resolvePath(basePath, accountId) + '/postal-addresses',
				json
			),
	};
}
