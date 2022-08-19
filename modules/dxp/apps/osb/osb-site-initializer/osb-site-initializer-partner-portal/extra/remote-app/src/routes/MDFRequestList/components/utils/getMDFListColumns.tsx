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

import DropDown from '../Dropdown';

export default function getMDFListColumns() {
	return [
		{
			columnKey: 'id',
			label: 'Request ID',
		},
		{columnKey: 'activityPeriod', label: 'Activity Period'},
		{columnKey: 'totalCost', label: 'Total Cost'},
		{columnKey: 'requested', label: 'Requested'},
		{columnKey: 'approved', label: 'Approved'},
		{
			columnKey: 'reimpursementClaim',
			label: 'Reimpursement Claim(s)',
		},
		{
			columnKey: '',
			label: '',
			render: () => (
				<div>
					<ClayIcon symbol="comments" />
				</div>
			),
		},
		{
			columnKey: '',
			label: '',
			render: () => (
				<DropDown
					optionList={[
						{
							icon: 'check',
							key: 'approve',
							label: 'Approve',
						},
						{
							icon: 'times',
							key: 'reject',
							label: 'Reject',
						},
					]}
				></DropDown>
			),
		},
	];
}
