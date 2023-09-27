/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Parameters, parametersFormater} from '.';
import {axios} from './liferay/api';

const headlessAPI = 'o/headless-user-notification/v1.0';

export function getUserNotification(parameters: Parameters) {
	const parametersList = Object.keys(parameters);

	if (parametersList.length) {
		return axios.get(
			`${headlessAPI}/my-user-notifications/?${parametersFormater(
				parametersList,
				parameters
			)}`
		);
	}

	return axios.get(`${headlessAPI}/my-user-notifications`);
}

export function putUserNotificationRead(userNotificationId: number) {
	return axios.put(
		`${headlessAPI}/user-notifications/${userNotificationId}/read`
	);
}
