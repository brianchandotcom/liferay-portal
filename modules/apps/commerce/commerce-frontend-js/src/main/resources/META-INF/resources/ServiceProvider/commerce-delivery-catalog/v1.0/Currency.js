/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const VERSION = 'v1.0';

function resolvePath(basePath, channelId) {
	return `${basePath}${VERSION}/channels/${channelId}/currencies`;
}

export default function Currency(basePath) {
	return {
		getBaseURL: (channelId) => resolvePath(basePath, channelId),
		getCurrenciesByChannelId: (channelId, ...params) =>
			AJAX.GET(resolvePath(basePath, channelId), ...params),
	};
}
