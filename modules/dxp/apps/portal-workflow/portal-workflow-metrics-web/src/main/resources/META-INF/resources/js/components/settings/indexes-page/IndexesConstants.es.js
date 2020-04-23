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

const ALL_INDEXES_KEY = 'All';
const METRIC_INDEXES_KEY = 'Metric';
const SLA_INDEXES_KEY = 'SLA';

const INDEXES_GROUPS_KEYS = [
	ALL_INDEXES_KEY,
	METRIC_INDEXES_KEY,
	SLA_INDEXES_KEY,
];

const getIndexesGroups = () => ({
	[METRIC_INDEXES_KEY]: {
		indexes: [
			{
				key: METRIC_INDEXES_KEY,
				label: Liferay.Language.get('workflow-metrics-indexes'),
			},
		],
		key: METRIC_INDEXES_KEY,
		label: Liferay.Language.get('metrics'),
	},
	[SLA_INDEXES_KEY]: {
		indexes: [
			{
				key: SLA_INDEXES_KEY,
				label: Liferay.Language.get('workflow-sla-indexes'),
			},
		],
		key: SLA_INDEXES_KEY,
		label: Liferay.Language.get('slas'),
	},
});

export {
	ALL_INDEXES_KEY,
	METRIC_INDEXES_KEY,
	SLA_INDEXES_KEY,
	INDEXES_GROUPS_KEYS,
	getIndexesGroups,
};
