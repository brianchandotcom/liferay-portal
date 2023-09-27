/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {axios} from './liferay/api';

const headlessAPI = '/o/c/raylifeauthentications';

export function getRaylifeAuthentication() {
	return axios.get(`${headlessAPI}`).then(({data}) => data.items[0]);
}
