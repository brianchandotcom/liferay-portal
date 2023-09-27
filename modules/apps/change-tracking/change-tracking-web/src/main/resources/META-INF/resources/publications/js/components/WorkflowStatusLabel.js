/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
