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
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';
import Dropdown from '../components/Dropdown';

export default function getMDFListColumns(
	data?: TableColumn<MDFRequestListItem>[],
	siteURL?: string
): TableColumn<MDFRequestListItem>[] | undefined {
	return (
		data && [
			{
				columnKey: MDFColumnKey.ID,
				label: 'Request ID',
				render: (data) => <>{`Request-${data}`}</>,
			},
			...data,
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
