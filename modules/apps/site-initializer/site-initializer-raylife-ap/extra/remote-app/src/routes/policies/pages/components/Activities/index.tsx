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
import useWindowDimensions from '../../../../../hooks/useWindowDimensions';
import dataActivities from './DataActivities';
import TableList, {TableHeaders} from './TableList';
import TableListMobile from './TableListMobile';

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

const HEADERSMOBILE: TableHeaders[] = [
	{
		key: 'date',
		value: 'Date',
	},
	{
		bold: true,
		key: 'activity',
		value: 'Activity',
	},
];

const Activities = () => {
	const {width} = useWindowDimensions();

	const desktopBreakPoint = 1030;

	const isMobile = width < desktopBreakPoint;

	return (
		<div>
			{!isMobile ? (
				<div className="d-flex p-6 policy-detail-content rounded-top">
					<div className="bg-neutral-0 w-100">
						<TableList
							headers={HEADERS}
							rows={dataActivities}
						></TableList>
					</div>
				</div>
			) : (
				<div className="d-flex p-6 policy-detail-content rounded-top w-100">
					<TableListMobile
						headers={HEADERSMOBILE}
						rows={dataActivities}
					></TableListMobile>
				</div>
			)}
		</div>
	);
};

export default Activities;
