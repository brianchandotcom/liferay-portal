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

import useWindowDimensions from '../../../hooks/useWindowDimensions';
import TableListComponent, {TableListHeaders} from './table-list';
import TableListMobileComponent from './table-list-mobile';

const HEADERS: TableListHeaders[] = [
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

const HEADERSMOBILE: TableListHeaders[] = [
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

type ActivitieDataType = {
	BodyElement?: () => void;
	activitiesData: {[keys: string]: string | boolean}[];
};

const ActivitiesComponent = ({
	activitiesData,
	BodyElement = () => null,
}: ActivitieDataType) => {
	const {width} = useWindowDimensions();

	const desktopBreakPoint = 1030;

	const isMobile = width < desktopBreakPoint;

	return (
		<div>
			{!isMobile ? (
				<div className="activities-detail-content d-flex rounded-top">
					<div className="bg-neutral-0 d-flex w-100">
						<TableListComponent
							BodyElement={BodyElement}
							headers={HEADERS}
							rows={activitiesData}
						></TableListComponent>
					</div>
				</div>
			) : (
				<div className="activities-detail-content d-flex rounded-top">
					<TableListMobileComponent
						headers={HEADERSMOBILE}
						rows={activitiesData}
					></TableListMobileComponent>
				</div>
			)}
		</div>
	);
};

export default ActivitiesComponent;
