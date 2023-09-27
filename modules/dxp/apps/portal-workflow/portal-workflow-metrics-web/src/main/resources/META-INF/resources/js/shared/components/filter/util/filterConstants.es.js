/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
