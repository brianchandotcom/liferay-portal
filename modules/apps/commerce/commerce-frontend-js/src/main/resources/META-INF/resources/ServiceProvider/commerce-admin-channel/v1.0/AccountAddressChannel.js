/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AJAX from '../../../utilities/AJAX/index';

const ACCOUNT_ADDRESSES_PATH = '/account-addresses';

const ACCOUNT_ADDRESS_CHANNELS_PATH = '/account-address-channels';

const VERSION = 'v1.0';

function resolvePath(
	basePath = '',
	addressId = '',
	accountAddressChannelId = ''
) {
	return `${basePath}${VERSION}${ACCOUNT_ADDRESSES_PATH}/${addressId}${ACCOUNT_ADDRESS_CHANNELS_PATH}/${accountAddressChannelId}`;
}

export default function AccountAddressChannel(basePath) {
	return {
		addAccountAddressChannel: (addressId, json) =>
			AJAX.POST(resolvePath(basePath, addressId), json),
	};
}
