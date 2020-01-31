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

import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../../../shared/hooks/useFetch.es';
import {usePaginationState} from '../../../../../shared/hooks/usePaginationState.es';
import {InstanceListContext} from '../../../store/InstanceListPageStore.es';
import {ModalContext} from '../../ModalContext.es';
import {Body} from './BulkReassignSelectTasksStepBody.es';
import {Header} from './BulkReassignSelectTasksStepHeader.es';

const BulkReassignSelectTasksStep = ({setErrorToast}) => {
	const {selectedItems} = useContext(InstanceListContext);
	const {singleModal} = useContext(ModalContext);

	const [tasks, setTasks] = useState([]);
	const [retry, setRetry] = useState(0);

	const {page, pageSize, pagination} = usePaginationState({
		initialPageSize: 5
	});

	const workflowInstanceIds = useMemo(() => {
		return selectedItems.length
			? selectedItems.map(item => item.id)
			: singleModal.selectedItem.id;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const {data, fetchData} = useFetch({
		admin: true,
		params: {
			completed: false,
			page,
			pageSize,
			workflowInstanceIds
		},
		url: '/workflow-tasks'
	});

	const paginationState = {
		...pagination,
		totalCount: data.totalCount
	};

	useEffect(() => {
		if (data.items && data.items.length) {
			const parsedTasks =
				data.items.map(task => {
					const item = selectedItems.length
						? selectedItems.find(
								item => item.id === task.workflowInstanceId
						  )
						: singleModal.selectedItem;

					const {assetTitle, assetType} = item || {};

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
				setErrorToast(Liferay.Language.get('your-request-has-failed'));
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
				pagination={paginationState}
				setRetry={setRetry}
			/>
		</PromisesResolver>
	);
};

BulkReassignSelectTasksStep.Body = Body;
BulkReassignSelectTasksStep.Header = Header;

export {BulkReassignSelectTasksStep};
