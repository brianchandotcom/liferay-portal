/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	const timeRange = completed ? getTimeRangeParams(dateStart, dateEnd) : {};

	const {data, fetchData} = useFetch({
		params: {
			assigneeIds,
			page,
			pageSize,
			slaStatuses,
			sort,
			statuses,
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
					items={data?.items}
					processId={processId}
					routeParams={routeParams}
					selectedFilters={selectedFilters}
					totalCount={data?.totalCount}
				/>

				<InstanceListPage.Body
					data={data}
					fetchData={fetchData}
					filtered={!!selectedFilters.length}
					routeParams={routeParams}
				/>
			</InstanceListPageProvider>
		</ModalProvider>
	);
}

InstanceListPage.Body = Body;
InstanceListPage.Header = Header;

export default InstanceListPage;
