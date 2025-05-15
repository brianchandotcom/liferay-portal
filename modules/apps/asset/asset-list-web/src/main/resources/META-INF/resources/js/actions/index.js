/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const saveVariationsListPriorityService = ({method = 'POST', url}) => {
	return fetch(url, {method})
		.then(({ok, status}) => {
			return {ok, status};
		})
		.catch((error) => {
			return error;
		});
};

export {saveVariationsListPriorityService};
