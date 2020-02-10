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

import PromisesResolver from '../../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../../../shared/hooks/useFetch.es';
import {useFilter} from '../../../../../shared/hooks/useFilter.es';
import {usePaginationState} from '../../../../../shared/hooks/usePaginationState.es';
import {InstanceListContext} from '../../../store/InstanceListPageStore.es';
import {ModalContext} from '../../ModalContext.es';
import {Body} from './BulkReassignSelectTasksStepBody.es';
import {Header} from './BulkReassignSelectTasksStepHeader.es';

const BulkReassignSelectTasksStep = ({processId, setErrorToast}) => {
	const {selectAll, selectedItems} = useContext(InstanceListContext);
	const {singleModal} = useContext(ModalContext);

	const filterKeys = ['processStep', 'assignee'];
	const prefixKey = 'bulk';
	const prefixKeys = [prefixKey];
	const {
		filterValues: {bulkAssigneeUserIds: userIds, bulkTaskKeys: taskNames},
		prefixedKeys,
		selectedFilters
	} = useFilter({filterKeys, prefixKeys, withoutRouteParams: true});

	const [retry, setRetry] = useState(0);
	const {page, pageSize, pagination} = usePaginationState({
		initialPageSize: 5
	});

	const params = useMemo(() => {
		const filterByUser = userIds && userIds.length;

		const assigneeIds = filterByUser
			? userIds.filter(id => id !== '-1')
			: undefined;

		const searchByRoles = filterByUser && userIds.includes('-1');

		const params = {
			assigneeIds,
			completed: false,
			page,
			pageSize,
			searchByRoles,
			sort: 'workflowInstanceId:asc',
			taskNames
		};

		if (selectAll) {
			params.workflowDefinitionId = processId;
		}
		else {
			params.workflowInstanceIds = selectedItems.length
				? selectedItems.map(item => item.id)
				: singleModal.selectedItem.id;
		}

		return params;
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page, pageSize, taskNames, userIds]);

	const {data, fetchData} = useFetch({
		admin: true,
		params,
		url: '/workflow-tasks'
	});

	const paginationState = {
		...pagination,
		totalCount: data.totalCount
	};

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
		<div className="fixed-height modal-metrics-content">
			<PromisesResolver promises={promises}>
				<BulkReassignSelectTasksStep.Header
					filterKeys={prefixedKeys}
					prefixKey={prefixKey}
					selectedFilters={selectedFilters}
					{...data}
				/>

				<BulkReassignSelectTasksStep.Body
					{...data}
					pagination={paginationState}
					setRetry={setRetry}
				/>
			</PromisesResolver>
		</div>
	);
};

BulkReassignSelectTasksStep.Body = Body;
BulkReassignSelectTasksStep.Header = Header;

export {BulkReassignSelectTasksStep};
