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

import React, {useContext} from 'react';

import Filter from '../../../shared/components/filter/Filter.es';
import {AssigneeContext} from './store/AssigneeStore.es';

const AssigneeFilter = ({
	className,
	filterKey = 'assigneeUserIds',
	hideControl = false,
	position = 'left'
}) => {
	const {assignees} = useContext(AssigneeContext);

	return (
		<Filter
			elementClasses={className}
			filterKey={filterKey}
			hideControl={hideControl}
			items={assignees}
			multiple={true}
			name={Liferay.Language.get('assignee')}
			position={position}
		/>
	);
};

export default AssigneeFilter;
