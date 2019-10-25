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

import Icon from '../../../shared/components/Icon.es';
import Panel from '../../../shared/components/Panel.es';
import EmptyState from '../../../shared/components/list/EmptyState.es';
import {ErrorContext} from '../../../shared/components/request/Error.es';
import {LoadingContext} from '../../../shared/components/request/Loading.es';
import Request from '../../../shared/components/request/Request.es';
import {ChildLink} from '../../../shared/components/router/routerWrapper.es';
import {AppContext} from '../../AppContext.es';
import {TimeRangeContext} from '../filter/store/TimeRangeStore.es';
import PerformanceByStepCard from './PerformanceByStepCard.es';

const Body = ({processId}) => {
	const {client, defaultDelta} = useContext(AppContext);
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

	const viewAllStepsUrl = `/performance/${processId}/${defaultDelta}/1/overdueInstanceCount:desc`;

	return (
		<Request.Success>
			<Panel.Body>
				{data.totalCount ? (
					<>
						<PerformanceByStepCard.Table items={data.items} />

						<div className="mb-1 text-right">
							<ChildLink to={viewAllStepsUrl}>
								<button className="border-0 btn btn-secondary btn-sm">
									<span data-testid="viewAllSteps">
										{Liferay.Language.get(
											'view-all-steps'
										) + ` (${data.totalCount})`}
									</span>

									<Icon iconName="caret-right-l" />
								</button>
							</ChildLink>
						</div>
					</>
				) : (
					<PerformanceByStepCard.Empty />
				)}
			</Panel.Body>
		</Request.Success>
	);
};

const Empty = () => {
	return (
		<EmptyState
			className="border-0"
			hideAnimation={true}
			message={Liferay.Language.get('there-is-no-data-at-the-moment')}
			messageClassName="small"
		/>
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

export {Body, Empty};
