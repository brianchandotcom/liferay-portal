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

import React, {useContext, useEffect, useState} from 'react';
import {AppContext} from '../../AppContext.es';
import {ErrorContext} from '../../../shared/components/request/Error.es';
import Icon from '../../../shared/components/Icon.es';
import {LoadingContext} from '../../../shared/components/request/Loading.es';
import Panel from '../../../shared/components/Panel.es';
import PerformanceByStepCard from './PerformanceByStepCard.es';
import Request from '../../../shared/components/request/Request.es';
import {TimeRangeContext} from '../filter/store/TimeRangeStore.es';

const Body = ({processId}) => {
	const {client} = useContext(AppContext);
	const {getSelectedTimeRange} = useContext(TimeRangeContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [data, setData] = useState({});

	const timeRange = getSelectedTimeRange();

	useEffect(() => {
		setLoading(true);

		client
			.get(getRequestUrl(processId, timeRange))
			.then(({data}) => {
				setData(data);
			})
			.catch(error => {
				setError(error);
			})
			.then(() => {
				setLoading(false);
			});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [timeRange]);

	return (
		<Request.Success>
			<Panel.Body>
				<PerformanceByStepCard.Table items={data.items} />

				<div className="mb-1 text-right">
					<button className="border-0 btn btn-secondary btn-sm">
						<span data-testId="viewAllSteps">
							{Liferay.Language.get('view-all-steps') +
								` (${data.totalCount})`}
						</span>

						<Icon iconName="caret-right-l" />
					</button>
				</div>
			</Panel.Body>
		</Request.Success>
	);
};

const getRequestUrl = (processId, timeRange) => {
	let requestUrl = `/processes/${processId}/tasks?completed=true`;

	if (timeRange) {
		const {dateEnd, dateStart} = timeRange;

		requestUrl += `&dateEnd=${dateEnd.toISOString()}&dateStart=${dateStart.toISOString()}`;
	}

	requestUrl += '&page=1&pageSize=10&sort=durationAvg:desc';

	return requestUrl;
};

export {Body};
