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

import React from 'react';

import {useFetch} from '../../shared/hooks/useFetch.es';
import {useFilter} from '../../shared/hooks/useFilter.es';
import {useProcessTitle} from '../../shared/hooks/useProcessTitle.es';
import {processStatusConstants} from '../filter/ProcessStatusFilter.es';
import {useTimeRangeFetch} from '../filter/hooks/useTimeRangeFetch.es';
import {getTimeRangeParams} from '../filter/util/timeRangeUtil.es';
import Body from './InstanceListPageBody.es';
import Header from './InstanceListPageHeader.es';
import InstanceListPageProvider from './InstanceListPageProvider.es';
import ModalProvider from './modal/ModalProvider.es';

function InstanceListPage({routeParams}) {
	useTimeRangeFetch();

	const {page, pageSize, processId, sort} = routeParams;

	useProcessTitle(processId, Liferay.Language.get('all-items'));

	const filterKeys = [
		'assignee',
		'processStep',
		'processStatus',
		'slaStatus',
		'timeRange',
	];

	const {
		filterValues: {
			assigneeIds,
			dateEnd,
			dateStart,
			slaStatuses,
			statuses,
			taskNames,
		},
		prefixedKeys,
		selectedFilters,
	} = useFilter({filterKeys});

	const completed = statuses?.some(
		(status) => status === processStatusConstants.completed
	);
	const pending = statuses?.some(
		(status) => status === processStatusConstants.pending
	);

	const processStatuses = [];

	if (completed) {
		processStatuses.push(processStatusConstants.completed);
	}

	if (pending) {
		processStatuses.push(processStatusConstants.pending);
	}

	const timeRange = completed ? getTimeRangeParams(dateStart, dateEnd) : {};

	const {data, fetchData} = useFetch({
		params: {
			assigneeIds,
			page,
			pageSize,
			processStatuses,
			slaStatuses,
			sort,
			taskNames,
			...timeRange,
		},
		url: `/processes/${processId}/instances`,
	});

	return (
		<ModalProvider processId={processId}>
			<InstanceListPageProvider>
				<InstanceListPage.Header
					filterKeys={prefixedKeys}
					items={data.items}
					processId={processId}
					routeParams={routeParams}
					selectedFilters={selectedFilters}
					totalCount={data.totalCount}
				/>

				<InstanceListPage.Body
					data={data}
					fetchData={fetchData}
					filtered={selectedFilters.length > 0}
					routeParams={routeParams}
				/>
			</InstanceListPageProvider>
		</ModalProvider>
	);
}

InstanceListPage.Body = Body;
InstanceListPage.Header = Header;

export default InstanceListPage;
