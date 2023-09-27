/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					<div className="bg-neutral-0 d-flex rounded rounded-top w-100">
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
