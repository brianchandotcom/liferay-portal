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
import {Status} from '../../../common/enums/status';
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';

export default function getMDFListColumns(
	columns?: TableColumn<MDFRequestListItem>[],
	siteURL?: string
): TableColumn<MDFRequestListItem>[] | undefined {
	const getDropdownOptions = (row: MDFRequestListItem) => {
		if (row[MDFColumnKey.STATUS] !== Status.DRAFT) {
			return (
				<Dropdown
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

		return <Dropdown options={options}></Dropdown>;
	};

	return (
		columns && [
			{
				columnKey: MDFColumnKey.ID,
				label: 'Request ID',
				render: (data) => <>{`Request-${data}`}</>,
			},
			{
				columnKey: MDFColumnKey.STATUS,
				label: 'Status',
				render: (data) => <StatusBadge status={data as Status} />,
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
