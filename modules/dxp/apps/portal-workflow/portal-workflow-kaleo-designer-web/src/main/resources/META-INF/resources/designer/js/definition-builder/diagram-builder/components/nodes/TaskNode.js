/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import {defaultLanguageId} from '../../../constants';
import BaseNode from './BaseNode';

export default function TaskNode({
	data: {
		actions,
		assignments,
		description,
		label,
		newNode,
		notifications,
		taskTimers,
	} = {},
	descriptionSidebar,
	id,
	...otherProps
}) {
	if (!label || !label[defaultLanguageId]) {
		label = {
			[defaultLanguageId]: Liferay.Language.get('task'),
		};
	}

	if (!assignments) {
		assignments = {assignmentType: ['user']};
	}

	return (
		<BaseNode
			actions={actions}
			assignments={assignments}
			description={description}
			descriptionSidebar={descriptionSidebar}
			icon="check-circle-full"
			id={id}
			label={label}
			newNode={newNode}
			nodeTypeClassName="task-node"
			notifications={notifications}
			taskTimers={taskTimers}
			type="task"
			{...otherProps}
		/>
	);
}

TaskNode.propTypes = {
	data: PropTypes.object,
	descriptionSidebar: PropTypes.string,
	id: PropTypes.string,
};
