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

import React, {useContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../shared/components/request/PromisesResolver.es';
import {AppContext} from '../../AppContext.es';
import {ProcessStepContext} from '../filter/store/ProcessStepStore.es';
import {TimeRangeContext} from '../filter/store/TimeRangeStore.es';
import PerformanceByAssigneeCard from './PerformanceByAssigneeCard.es';

const Container = ({processId, query}) => {
	const {client, defaultDelta} = useContext(AppContext);
	const [data, setData] = useState({});
	const {getSelectedProcessSteps} = useContext(ProcessStepContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {processSteps, timeRange} = useMemo(
		() => ({
			processSteps: getSelectedProcessSteps(),
			timeRange: getSelectedTimeRange()
		}),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[processId, query]
	);

	let processStepsKey;

	if (
		processSteps &&
		processSteps.length &&
		processSteps[0].key !== 'allSteps'
	) {
		processStepsKey = processSteps[0].key;
	}

	const fetchData = processId => {
		const requestUrl = `/processes/${processId}/assignee-users`;
		const params = {
			completed: true,
			page: 1,
			pageSize: 10,
			sort: 'durationTaskAvg:desc'
		};

		if (processStepsKey) {
			params.taskKeys = processStepsKey;
		}

		if (timeRange) {
			const {dateEnd, dateStart} = timeRange;
			params.dateEnd = dateEnd.toISOString();
			params.dateStart = dateStart.toISOString();
		}

		return client.get(requestUrl, {params}).then(({data}) => {
			setData(data);
		});
	};

	const promises = useMemo(
		() => [fetchData(processId)],
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[processId, query]
	);

	return (
		<PromisesResolver promises={promises}>
			<PerformanceByAssigneeCard.Header
				processId={processId}
				query={query}
			/>

			<PerformanceByAssigneeCard.Body
				data={data}
				defaultDelta={defaultDelta}
				processId={processId}
				processStepsKey={processStepsKey}
			/>
		</PromisesResolver>
	);
};

export {Container};
