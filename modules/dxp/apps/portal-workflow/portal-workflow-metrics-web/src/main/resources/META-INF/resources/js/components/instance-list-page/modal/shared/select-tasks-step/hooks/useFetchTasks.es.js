/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
