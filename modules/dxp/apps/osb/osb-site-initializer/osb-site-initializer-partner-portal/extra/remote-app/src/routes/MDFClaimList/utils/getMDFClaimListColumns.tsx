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

import Dropdown from '../../../common/components/Dropdown';
import StatusBadge from '../../../common/components/StatusBadge';
import {MDFClaimColumnKey} from '../../../common/enums/mdfClaimColumnKey';
import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import {MDFClaimListItem} from '../../../common/interfaces/mdfClaimListItem';
import Role from '../../../common/interfaces/role';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';
import {Status} from '../../../common/utils/constants/status';
import {isLiferayManager} from '../../../common/utils/isLiferayManager';

export default function getMDFClaimListColumns(
	isPartnerManagerRole?: boolean,
	siteURL?: string,
	roleEntries?: Role[]
): TableColumn<MDFClaimListItem>[] | undefined {
	const getDropdownOptions = (row: MDFClaimListItem) => {
		const userAccountRolesCanEdit =
			isLiferayManager(roleEntries as Role[]) || isPartnerManagerRole;

		if (
			!userAccountRolesCanEdit &&
			row[MDFClaimColumnKey.STATUS] !== Status.DRAFT.name &&
			row[MDFClaimColumnKey.STATUS] !== Status.REQUEST_MORE_INFO.name
		) {
			return (
				<Dropdown
					closeOnClick={true}
					options={[
						{
							icon: 'view',
							key: 'approve',
							label: ' View',
							onClick: () =>
								Liferay.Util.navigate(
									`${siteURL}/l/${
										row[MDFClaimColumnKey.CLAIM_ID]
									}`
								),
						},
					]}
				></Dropdown>
			);
		}

		const options = [
			{
				icon: 'view',
				key: 'approve',
				label: ' View',
				onClick: () =>
					Liferay.Util.navigate(
						`${siteURL}/l/${row[MDFClaimColumnKey.CLAIM_ID]}`
					),
			},
			{
				icon: 'pencil',
				key: 'edit',
				label: ' Edit',
				onClick: () =>
					Liferay.Util.navigate(
						`${siteURL}/${
							PRMPageRoute.CREATE_MDF_CLAIM
						}/#/mdfrequest/${
							row[MDFClaimColumnKey.REQUEST_ID]
						}/mdfclaim/${row[MDFClaimColumnKey.CLAIM_ID]}`
					),
			},
		];

		return <Dropdown closeOnClick={true} options={options}></Dropdown>;
	};

	const columns = [
		{
			columnKey: MDFClaimColumnKey.CLAIM_ID,
			label: 'Claim ID',
			render: (data: string | undefined, row: MDFClaimListItem) => (
				<a
					className="link"
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/l/${row[MDFClaimColumnKey.CLAIM_ID]}`
						)
					}
				>{`Claim-${data}`}</a>
			),
		},
		{
			columnKey: MDFClaimColumnKey.REQUEST_ID,
			label: 'Request ID',
			render: (data: string | undefined, row: MDFClaimListItem) => (
				<a
					className="link"
					onClick={() =>
						Liferay.Util.navigate(
							`${siteURL}/l/${row[MDFClaimColumnKey.REQUEST_ID]}`
						)
					}
				>{`Request-${data}`}</a>
			),
		},
		{
			columnKey: MDFClaimColumnKey.PARTNER,
			label: 'Partner',
		},
		{
			columnKey: MDFClaimColumnKey.STATUS,
			label: 'Status',
			render: (data?: string) => <StatusBadge status={data as string} />,
		},
		{
			columnKey: MDFClaimColumnKey.TYPE,
			label: 'Type',
		},
		{
			columnKey: MDFClaimColumnKey.AMOUNT_CLAIMED,
			label: 'Amount Claimed',
		},
		{
			columnKey: MDFClaimColumnKey.PAID,
			label: 'Paid',
		},
		{
			columnKey: MDFClaimColumnKey.DATE_SUBMITTED,
			label: 'Date Submitted',
		},
	];

	return (
		columns && [
			...columns,
			{
				columnKey: MDFColumnKey.ACTION,
				label: '',
				render: (_, row) => getDropdownOptions(row),
			},
		]
	);
}
