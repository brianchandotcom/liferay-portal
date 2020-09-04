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
