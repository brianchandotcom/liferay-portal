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

import {useContext} from 'react';

import {useFilter} from '../../../../../../shared/hooks/useFilter.es';
import {usePost} from '../../../../../../shared/hooks/usePost.es';
import {AppContext} from '../../../../../AppContext.es';
import {InstanceListContext} from '../../../../InstanceListPageProvider.es';
import {ModalContext} from '../../../ModalProvider.es';

const useFetchTasks = ({
	callback,
	page = 1,
	pageSize = 10000,
	withoutUnassigned,
} = {}) => {
	const {
		filterValues: {
			assigneeIds: userIds = [],
			slaStatuses,
			taskNames: instanceTasks,
		},
	} = useFilter({});

	const {
		dispatch,
		filterValues: {bulkAssigneeIds = [], bulkTaskNames = []},
	} = useFilter({withoutRouteParams: true});

	const {processId} = useContext(ModalContext);
	const {userId} = useContext(AppContext);
	const {selectAll, selectedItems = []} = useContext(InstanceListContext);

	const assignees = bulkAssigneeIds.length ? bulkAssigneeIds : userIds;
	const availableUsers = withoutUnassigned ? [userId] : [userId, '-1'];
	const assigneeIds = assignees.filter((id) => availableUsers.includes(id));
	const instanceIds = !selectAll
		? selectedItems.map(({id}) => id)
		: undefined;
	const taskNames = bulkTaskNames.length ? bulkTaskNames : instanceTasks;

	let {data, postData: fetchTasks} = usePost({
		body: {
			assigneeIds: withoutUnassigned ? availableUsers : assigneeIds,
			instanceIds,
			processId,
			slaStatuses,
			taskNames,
		},
		callback,
		params: {
			page,
			pageSize,
			sort: 'instanceId:asc',
		},
		url: '/tasks',
	});

	const clearFilters = () => {
		dispatch({
			bulkAssigneeIds: [],
			bulkTaskNames: [],
		});
	};

	const noResultsFetch = () => {
		data = {
			items: [],
			page,
			pageSize,
			totalCount: 0,
		};

		return Promise.resolve(data);
	};

	if (withoutUnassigned && assignees.length && !assignees.includes(userId)) {
		fetchTasks = noResultsFetch;
	}

	return {clearFilters, data, fetchTasks, instanceIds};
};

export {useFetchTasks};
