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
		key: 'assigneeUserIds',
		title: Liferay.Language.get('assignee')
	},
	processStatus: {
		key: 'statuses',
		title: Liferay.Language.get('process-status')
	},
	processStep: {
		key: 'taskKeys',
		title: Liferay.Language.get('process-step')
	},
	roles: {
		key: 'roleIds',
		title: Liferay.Language.get('roles')
	},
	slaStatus: {
		key: 'slaStatuses',
		title: Liferay.Language.get('sla-status')
	},
	timeRange: {
		key: 'timeRange',
		title: Liferay.Language.get('completion-period')
	},
	velocityUnit: {
		key: 'velocityUnit',
		title: Liferay.Language.get('velocity-unit')
	}
};

const filterKeys = {
	assignee: 'assigneeUserIds',
	processStatus: 'statuses',
	processStep: 'taskKeys',
	roles: 'roleIds',
	slaStatus: 'slaStatuses',
	timeRange: 'timeRange',
	timeRangeDateEnd: 'dateEnd',
	timeRangeDateStart: 'dateStart'
};

const filterTitles = {
	assignee: Liferay.Language.get('assignee'),
	processStatus: Liferay.Language.get('process-status'),
	processStep: Liferay.Language.get('process-step'),
	roles: Liferay.Language.get('roles'),
	slaStatus: Liferay.Language.get('sla-status')
};

export {filterKeys, filterTitles};
export default filterConstants;
