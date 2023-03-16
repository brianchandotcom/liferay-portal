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

import i18n from '../../../../../../../../../../../../common/I18n';

const columns = [
	{
		accessor: 'start-end-date',
		align: 'center',
		bodyClass: 'border-0',
		expanded: true,
		header: {
			name: i18n.translate('start-end-date'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'quantity',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('purchased'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'instance-size',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('instance-size'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
	{
		accessor: 'subscription-term-status',
		align: 'center',
		bodyClass: 'border-0',
		header: {
			name: i18n.translate('status'),
			styles:
				'bg-neutral-1 font-weight-bold text-neutral-8 table-cell-expand-smaller py-3',
		},
	},
];

export default function getColumns(isProvisioned) {
	const displayColumns = [...columns];

	if (!isProvisioned) {
		displayColumns.splice(1, 1);
	}

	return displayColumns;
}
