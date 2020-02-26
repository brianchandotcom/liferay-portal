/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useMemo} from 'react';

import {getFiltersParam} from '../components/filter/util/filterUtil.es';
import {useRouter} from './useRouter.es';

const useRouterParams = () => {
	const {
		location: {search},
		match: {params},
	} = useRouter();

	const filters = useMemo(() => getFiltersParam(search), [search]);

	const routerParams = useMemo(
		() => ({
			...params,
			filters,
		}),
		[filters, params]
	);

	return routerParams;
};

export {useRouterParams};
