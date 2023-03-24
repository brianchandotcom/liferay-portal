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
import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import Role from '../../../common/interfaces/role';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';
import {Status} from '../../../common/utils/constants/status';
import {isLiferayManager} from '../../../common/utils/isLiferayManager';

export default function getMDFListColumns(
	columns?: TableColumn<MDFRequestListItem>[],
	siteURL?: string,
	roleEntries?: Role[],
	isPartnerManagerRole?: boolean
): TableColumn<MDFRequestListItem>[] | undefined {
	const getDropdownOptions = (row: MDFRequestListItem) => {
		const userAccountRolesCanEdit =
			isLiferayManager(roleEntries as Role[]) || isPartnerManagerRole;

		if (
			!userAccountRolesCanEdit &&
			row[MDFColumnKey.STATUS] !== Status.DRAFT.name &&
			row[MDFColumnKey.STATUS] !== Status.REQUEST_MORE_INFO.name
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
									`${siteURL}/l/${row[MDFColumnKey.ID]}`
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
						`${siteURL}/l/${row[MDFColumnKey.ID]}`
					),
			},
			{
				icon: 'pencil',
				key: 'edit',
				label: ' Edit',
				onClick: () =>
					Liferay.Util.navigate(
						`${siteURL}/${PRMPageRoute.CREATE_MDF_REQUEST}/#/${
							row[MDFColumnKey.ID]
						}`
					),
			},
		];

		return <Dropdown closeOnClick={true} options={options}></Dropdown>;
	};

	return (
		columns && [
			{
				columnKey: MDFColumnKey.ID,
				label: 'Request ID',
				render: (data, row) => (
					<a
						className="link"
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/l/${row[MDFColumnKey.ID]}`
							)
						}
					>{`Request-${data}`}</a>
				),
			},
			{
				columnKey: MDFColumnKey.STATUS,
				label: 'Status',
				render: (data) => <StatusBadge status={data as string} />,
			},
			...columns,
			{
				columnKey: MDFColumnKey.ACTION,
				label: '',
				render: (_, row) => getDropdownOptions(row),
			},
		]
	);
}
