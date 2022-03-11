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

import PopoverIconButton from '../../components/PopoverIconButton';

export const COLUMNS = [
	{
		accessor: 'name',
		bodyClass: 'border-0',
		header: {
			name: 'Name',
			styles:
				'bg-transparent text-neutral-10 font-weight-bold table-cell-expand',
		},
		truncate: true,
	},
	{
		accessor: 'email',
		bodyClass: 'border-0',

		header: {
			name: 'Email',
			styles:
				'bg-transparent text-neutral-10 font-weight-bold table-cell-expand-small',
		},
		truncate: true,
	},
	{
		accessor: 'supportSeat',
		align: 'center',
		bodyClass: 'border-0',

		header: {
			name: (
				<div className="align-items-center d-flex justify-content-center">
					<p className="m-0">Support Seat</p>

					<PopoverIconButton />
				</div>
			),
			noWrap: true,
			styles:
				'bg-transparent text-neutral-10 font-weight-bold table-cell-expand-smaller',
		},
	},
	{
		accessor: 'role',
		bodyClass: 'border-0',
		header: {
			name: 'Role',
			styles:
				'bg-transparent text-neutral-10 font-weight-bold table-cell-expand-smaller',
		},
		truncate: true,
	},
	{
		accessor: 'status',
		bodyClass: 'border-0',
		header: {
			name: 'Status',
			styles:
				'bg-transparent text-neutral-10 font-weight-bold table-cell-expand-smallest',
		},
	},
	{
		accessor: 'options',
		align: 'right',
		bodyClass: 'border-0',
		header: {
			name: '',
			styles: 'bg-transparent',
		},
	},
];
