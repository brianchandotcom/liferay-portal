/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ACCOUNTS_PATH = '/accounts';

const CHANNELS_PATH = '/channels';

const VERSION = 'v1.0';

function resolvePath(basePath = '', channelId = '') {
	return `${basePath}${VERSION}${CHANNELS_PATH}/${channelId}${ACCOUNTS_PATH}`;
}

export default function Account(basePath) {
	return {
		baseURL: (channelId) => resolvePath(basePath, channelId),
		getAccounts: (channelId, ...params) =>
			AJAX.GET(resolvePath(basePath, channelId), ...params),
	};
}
