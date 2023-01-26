/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import './index.scss';
import dataActivities from './DataActivities';
import TableList, {TableHeaders} from './TableList';

const HEADERS: TableHeaders[] = [
	{
		key: 'date',
		value: 'Date',
	},
	{
		bold: true,
		key: 'activity',
		value: 'Activity',
	},
	{
		key: 'by',
		value: 'By',
	},
];

const Activities = () => {
	return (
		<div className="d-flex p-6 policy-detail-content rounded-top">
			<div className="bg-neutral-0 w-100">
				<TableList headers={HEADERS} rows={dataActivities}></TableList>
			</div>
		</div>
	);
};

export default Activities;
