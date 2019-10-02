/* eslint-disable react-hooks/exhaustive-deps */
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

import {AppContext} from '../../../AppContext.es';
import React, {createContext, useContext, useEffect, useState} from 'react';

const usePerformanceData = (page, pageSize, processId, search, sort) => {
	const {client} = useContext(AppContext);
	const [items, setItems] = useState([]);
	const [totalCount, setTotalCount] = useState(0);

	const fetchData = (page, pageSize, processId, search, sort) => {
		const params = {
			page,
			pageSize,
			sort: decodeURIComponent(sort)
		};

		if (typeof search === 'string' && search) {
			params.key = decodeURIComponent(search);
		}

		client.get(`/processes/${processId}/tasks`, {params}).then(({data}) => {
			setTotalCount(() => data.totalCount);
			setItems(() => data.items);
		});
	};

	useEffect(() => {
		if (page && pageSize && processId && sort)
			fetchData(page, pageSize, processId, search, sort);
	}, [page, pageSize, processId, search, sort]);

	return {
		items,
		totalCount
	};
};

const PerformanceDataContext = createContext();

const PerformanceDataProvider = ({
	children,
	page,
	pageSize,
	processId,
	search,
	sort
}) => {
	return (
		<PerformanceDataContext.Provider
			value={usePerformanceData(page, pageSize, processId, search, sort)}
		>
			{children}
		</PerformanceDataContext.Provider>
	);
};

export {PerformanceDataProvider, PerformanceDataContext, usePerformanceData};
