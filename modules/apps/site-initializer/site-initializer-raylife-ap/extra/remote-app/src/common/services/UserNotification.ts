/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const DeliveryAPI = 'o/headless-user-notification/v1.0';

export function getUserNotification() {
	return axios.get(`${DeliveryAPI}/my-user-notifications`);
}
