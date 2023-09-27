/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ACCOUNTS_PATH = '/accounts';

const VERSION = 'v1.0';

function resolvePath(basePath = '') {
	return `${basePath}${VERSION}${ACCOUNTS_PATH}`;
}

export default function Account(basePath) {
	return {
		baseURL: resolvePath(basePath),
		getAccounts: (...params) => AJAX.GET(resolvePath(basePath), ...params),
	};
}
