/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayLabel from '@clayui/label';
import React from 'react';

const WORKFLOW_STATUS_APPROVED = 0;
const WORKFLOW_STATUS_DENIED = 4;
const WORKFLOW_STATUS_DRAFT = 2;
const WORKFLOW_STATUS_PENDING = 1;

const WorkflowStatusLabel = ({workflowStatus}) => {
	let displayType = null;
	let label = null;

	if (workflowStatus === WORKFLOW_STATUS_APPROVED) {
		displayType = 'success';
		label = Liferay.Language.get('approved');
	}
	else if (workflowStatus === WORKFLOW_STATUS_DENIED) {
		displayType = 'danger';
		label = Liferay.Language.get('denied');
	}
	else if (workflowStatus === WORKFLOW_STATUS_DRAFT) {
		displayType = 'secondary';
		label = Liferay.Language.get('draft');
	}
	else if (workflowStatus === WORKFLOW_STATUS_PENDING) {
		displayType = 'info';
		label = Liferay.Language.get('pending');
	}

	return displayType && label ? (
		<ClayLabel displayType={displayType}>{label}</ClayLabel>
	) : null;
};

export default WorkflowStatusLabel;
