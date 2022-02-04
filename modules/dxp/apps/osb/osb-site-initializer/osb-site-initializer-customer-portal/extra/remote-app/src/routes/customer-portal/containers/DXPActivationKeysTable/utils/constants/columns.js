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

import ClayIcon from '@clayui/icon';

export const COLUMNS = [
	{
		accessor: 'envName',
		bodyClass: 'border-0 cursor-pointer',
		expanded: true,
		header: {
			description: 'Description',
			name: 'Environment Name',
			styles: 'bg-transparent',
		},
	},
	{
		accessor: 'keyType',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			description: 'Host Name / Cluster Size',
			name: 'Key Type',
			noWrap: true,
			styles: 'bg-transparent',
		},
	},
	{
		accessor: 'envType',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: 'Environment Type',
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
	{
		accessor: 'expirationDate',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: 'Exp. Date',
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
		noWrap: true,
	},
	{
		accessor: 'status',
		align: 'center',
		bodyClass: 'border-0 cursor-pointer',
		header: {
			name: 'Status',
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
	{
		accessor: 'download',
		align: 'center',
		bodyClass: 'border-0',
		disableCustomClickOnRow: true,
		header: {
			name: <ClayIcon symbol="download" />,
			styles: 'bg-transparent text-neutral-10 font-weight-bold',
		},
	},
];
