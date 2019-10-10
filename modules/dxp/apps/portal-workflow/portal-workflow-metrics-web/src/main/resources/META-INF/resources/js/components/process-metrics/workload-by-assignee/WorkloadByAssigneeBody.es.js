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

import React, {useContext, useEffect, useState, useMemo} from 'react';
import {AppContext} from '../../AppContext.es';
import {ErrorContext} from '../../../shared/components/request/Error.es';
import Icon from '../../../shared/components/Icon.es';
import {LoadingContext} from '../../../shared/components/request/Loading.es';
import {ProcessStepContext} from '../filter/store/ProcessStepStore.es';
import Request from '../../../shared/components/request/Request.es';
import WorkloadByAssigneeCard from './WorkloadByAssigneeCard.es';

const Body = ({currentTab, processId, query}) => {
	const {client} = useContext(AppContext);
	const {getSelectedProcessSteps} = useContext(ProcessStepContext);
	const {setError} = useContext(ErrorContext);
	const {setLoading} = useContext(LoadingContext);
	const [data, setData] = useState({});

	const processSteps = useMemo(getSelectedProcessSteps, [query]);

	useEffect(() => {
		setLoading(true);

		client
			.get(getRequestUrl(currentTab, processId, processSteps))
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
	}, [currentTab, processId, processSteps]);

	return (
		<Request.Success>
			{data.totalCount ? (
				<>
					<WorkloadByAssigneeCard.Table
						currentTab={currentTab}
						items={data.items}
					/>

					<div className="mb-1 text-right">
						<button className="border-0 btn btn-secondary btn-sm">
							<span data-testid="viewAllSteps">
								{Liferay.Language.get('view-all-assignees') +
									` (${data.totalCount})`}
							</span>

							<Icon iconName="caret-right-l" />
						</button>
					</div>
				</>
			) : (
				<WorkloadByAssigneeCard.Empty currentTab={currentTab} />
			)}
		</Request.Success>
	);
};

const Empty = ({currentTab}) => {
	const getEmptyMessage = () => {
		switch (currentTab) {
			case 'onTime':
				return Liferay.Language.get(
					'there-are-no-assigned-items-on-time-at-the-moment'
				);
			case 'overdue':
				return Liferay.Language.get(
					'there-are-no-assigned-items-overdue-at-the-moment'
				);
			default:
				return Liferay.Language.get(
					'there-are-no-items-assigned-to-users-at-the-moment'
				);
		}
	};

	return (
		<div className="empty-message mb-6 ml-4 mr-4 mt-6 text-center">
			{getEmptyMessage()}
		</div>
	);
};

const getRequestUrl = (currentTab, processId, processSteps) => {
	let requestUrl = `/processes/${processId}/assignee-users?page=1&pageSize=10`;

	if (processSteps && processSteps.length) {
		const {key} = processSteps[0];

		if (key !== 'allSteps') {
			requestUrl += `&taskKeys=${key}`;
		}
	}

	if (currentTab === 'overdue') {
		requestUrl += `&sort=overdueTaskCount:desc`;
	} else if (currentTab === 'onTime') {
		requestUrl += '&sort=onTimeTaskCount:desc';
	} else {
		requestUrl += '&sort=taskCount:desc';
	}

	return requestUrl;
};

export {Body, Empty};
