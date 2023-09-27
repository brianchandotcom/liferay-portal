/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
