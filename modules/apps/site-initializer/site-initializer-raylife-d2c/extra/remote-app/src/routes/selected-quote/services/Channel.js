/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from '../../../common/services/liferay/api';

const DeliveryAPI = 'o/headless-commerce-admin-channel';
const ChannelName = 'Raylife D2C Channel';

export function getChannel() {
	return axios.get(
		`${DeliveryAPI}/v1.0/channels?filter=name eq '${ChannelName}'`
	);
}
