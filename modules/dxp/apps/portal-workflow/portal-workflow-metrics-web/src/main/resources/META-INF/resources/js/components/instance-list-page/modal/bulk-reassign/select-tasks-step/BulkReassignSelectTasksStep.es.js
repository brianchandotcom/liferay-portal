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

import React, {useContext, useMemo, useState, useEffect} from 'react';

import PromisesResolver from '../../../../../shared/components/request/PromisesResolver.es';
import {useFetch} from '../../../../../shared/hooks/useFetch.es';
import {InstanceListContext} from '../../../store/InstanceListPageStore.es';
import {Body} from './BulkReassignSelectTasksStepBody.es';
import {Header} from './BulkReassignSelectTasksStepHeader.es';

const BulkReassignSelectTasksStep = ({setErrorToast}) => {
	const {selectedItems} = useContext(InstanceListContext);

	const [tasks, setTasks] = useState([]);
	const [retry, setRetry] = useState(0);

	const {data, fetchData} = useFetch({
		admin: true,
		params: {
			andOperator: true,
			pageSize: 1000,
			workflowInstanceIds: selectedItems.map(item => item.id)
		},
		url: '/workflow-tasks'
	});

	useEffect(() => {
		if (data.items && data.items.length) {
			const parsedTasks =
				data.items.map(task => {
					const {assetTitle, assetType} =
						selectedItems.find(
							item => item.id === task.instanceId
						) || {};

					return {
						assetTitle,
						assetType,
						...task
					};
				}) || [];

			setTasks(parsedTasks);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	const promises = useMemo(() => {
		setErrorToast(false);

		return [
			fetchData().catch(err => {
				setErrorToast(
					Liferay.Language.get(
						'your-connection-was-unexpectedly-lost'
					)
				);
				return Promise.reject(err);
			})
		];
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [fetchData, retry]);

	return (
		<PromisesResolver promises={promises}>
			<PromisesResolver.Resolved>
				<BulkReassignSelectTasksStep.Header {...data} items={tasks} />
			</PromisesResolver.Resolved>

			<BulkReassignSelectTasksStep.Body
				{...data}
				items={tasks}
				setRetry={setRetry}
			/>
		</PromisesResolver>
	);
};

BulkReassignSelectTasksStep.Body = Body;
BulkReassignSelectTasksStep.Header = Header;

export {BulkReassignSelectTasksStep};
