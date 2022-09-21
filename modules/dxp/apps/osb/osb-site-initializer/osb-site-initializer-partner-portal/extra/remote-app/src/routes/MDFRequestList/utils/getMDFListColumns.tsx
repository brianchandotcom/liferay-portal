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

import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {RequestStatus} from '../../../common/enums/requestStatus';
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';
import Dropdown from '../components/Dropdown';
import RequestStatusBadge from '../components/RequestStatusBadge';

export default function getMDFListColumns(
	columns?: TableColumn<MDFRequestListItem>[],
	siteURL?: string
): TableColumn<MDFRequestListItem>[] | undefined {
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
				render: (data) => (
					<RequestStatusBadge status={data as RequestStatus} />
				),
			},
			...columns,
			{
				columnKey: MDFColumnKey.ACTION,
				label: '',
				render: (_, row) => (
					<Dropdown
						onClick={() =>
							Liferay.Util.navigate(
								`${siteURL}/l/${row[MDFColumnKey.ID]}`
							)
						}
						options={[
							{
								icon: 'view',
								key: 'approve',
								label: ' View',
							},
						]}
					></Dropdown>
				),
			},
		]
	);
}
