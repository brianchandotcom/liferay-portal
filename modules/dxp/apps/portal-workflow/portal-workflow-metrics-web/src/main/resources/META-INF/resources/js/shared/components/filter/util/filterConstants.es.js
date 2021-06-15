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

const filterConstants = {
	assignee: {
		key: 'assigneeIds',
		pinned: false,
		title: Liferay.Language.get('assignee'),
	},
	processStatus: {
		key: 'statuses',
		pinned: false,
		title: Liferay.Language.get('process-status'),
	},
	processStep: {
		key: 'taskNames',
		pinned: false,
		title: Liferay.Language.get('process-step'),
	},
	processVersion: {
		key: 'processVersion',
		pinned: false,
		title: Liferay.Language.get('process-version'),
	},
	roles: {
		key: 'roleIds',
		pinned: false,
		title: Liferay.Language.get('roles'),
	},
	slaStatus: {
		key: 'slaStatuses',
		pinned: false,
		title: Liferay.Language.get('sla-status'),
	},
	timeRange: {
		key: 'timeRange',
		pinned: true,
		title: Liferay.Language.get('completion-period'),
	},
	velocityUnit: {
		key: 'velocityUnit',
		pinned: false,
		title: Liferay.Language.get('velocity-unit'),
	},
};

export default filterConstants;
