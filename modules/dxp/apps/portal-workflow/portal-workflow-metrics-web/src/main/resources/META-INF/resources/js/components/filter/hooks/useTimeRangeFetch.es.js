/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useEffect} from 'react';

import {FilterContext} from '../../../shared/components/filter/FilterContext.es';
import filterConstants from '../../../shared/components/filter/util/filterConstants.es';
import {useBeforeUnload} from '../../../shared/hooks/useBeforeUnload.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {useSessionStorage} from '../../../shared/hooks/useStorage.es';

const useTimeRangeFetch = () => {
	const {dispatchFilterError} = useContext(FilterContext);
	const [, update, remove] = useSessionStorage('timeRanges');

	const clean = () => {
		dispatchFilterError(filterConstants.timeRange.key, true);
		remove();
	};

	useBeforeUnload(clean);

	const {fetchData} = useFetch({
		callback: (data) => update({items: data.items}),
		url: '/time-ranges',
	});

	useEffect(() => {
		fetchData().catch(() => {
			dispatchFilterError(filterConstants.timeRange.key);
		});

		return clean;

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
};

export {useTimeRangeFetch};
