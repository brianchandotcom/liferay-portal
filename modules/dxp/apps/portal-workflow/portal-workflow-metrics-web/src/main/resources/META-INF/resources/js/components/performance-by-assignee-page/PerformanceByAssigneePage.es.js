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

import React, {useState, useMemo, useContext} from 'react';

import PromisesResolver from '../../shared/components/request/PromisesResolver.es';
import Request from '../../shared/components/request/Request.es';
import {AppContext} from '../AppContext.es';
import {Body} from './PerformanceByAssigneePageBody.es';
import {Header} from './PerformanceByAssigneePageHeader.es';
import {Item, Table} from './PerformanceByAssigneePageTable.es';

const PerformanceByAssigneePage = ({
	page,
	pageSize,
	processId,
	query,
	search,
	sort
}) => {
	const {client} = useContext(AppContext);
	const [data, setData] = useState({});

	const fetchData = () => {
		const requestUrl = `/processes/${processId}/assignee-users`;
		const params = {
			completed: true,
			page,
			pageSize,
			sort: decodeURIComponent(sort)
		};

		return client.get(requestUrl, {params}).then(({data}) => {
			setData(data);
		});
	};

	const promises = useMemo(
		() => [fetchData()],
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[processId, query, search, sort]
	);

	return (
		<Request>
			<PromisesResolver promises={promises}>
				<PerformanceByAssigneePage.Header />
				<PerformanceByAssigneePage.Body data={data} />
			</PromisesResolver>
		</Request>
	);
};

PerformanceByAssigneePage.Body = Body;
PerformanceByAssigneePage.Header = Header;
PerformanceByAssigneePage.Item = Item;
PerformanceByAssigneePage.Table = Table;

export default PerformanceByAssigneePage;
