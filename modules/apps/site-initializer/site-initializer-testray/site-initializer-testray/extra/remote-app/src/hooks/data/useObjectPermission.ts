/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {APIResponse} from '~/services/rest';

import {useFetch} from '../useFetch';

const useObjectPermission = (resource: string) => {
	const {data} = useFetch<APIResponse>(resource, {
		params: {
			fields: 'actions,id',
			pageSize: 1,
		},
	});

	return {
		canCreate: !!data?.actions.create,
	};
};

export {useObjectPermission};
