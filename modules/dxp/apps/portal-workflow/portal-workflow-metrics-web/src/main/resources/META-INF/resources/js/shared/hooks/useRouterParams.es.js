/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	return useMemo(() => ({...params, filters}), [filters, params]);
};

export {useRouterParams};
