/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext, useState} from 'react';

import {AppContext} from '../../components/AppContext.es';
import {paginateArray} from '../util/array.es';

const usePaginationState = (props) => {
	const {defaultDelta} = useContext(AppContext);
	const {initialPage = 1, initialPageSize = defaultDelta, items} = props;

	const defaultPageSize =
		initialPageSize <= defaultDelta ? initialPageSize : defaultDelta;

	const [page, setPage] = useState(initialPage);
	const [pageSize, setPageSize] = useState(defaultPageSize);

	const paginatedItems = items ? paginateArray(items, page, pageSize) : [];

	return {
		page,
		pageSize,
		paginatedItems,
		pagination: {
			page,
			pageSize,
			setPage,
			setPageSize,
		},
	};
};

export {usePaginationState};
