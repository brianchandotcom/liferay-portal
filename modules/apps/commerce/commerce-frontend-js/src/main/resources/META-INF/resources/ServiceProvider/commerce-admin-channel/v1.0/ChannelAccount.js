/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const CHANNELS_PATH = '/channels';

const CHANNEL_ACCOUNTS_PATH = '/channel-accounts';

const VERSION = 'v1.0';

function resolvePath(basePath = '', channelId = '', channelAccountId = '') {
	return `${basePath}${VERSION}${CHANNELS_PATH}/${channelId}${CHANNEL_ACCOUNTS_PATH}/${channelAccountId}`;
}

export default function ChannelAccount(basePath) {
	return {
		addChannelAccount: (channelId, json) =>
			AJAX.POST(resolvePath(basePath, channelId), json),
	};
}
