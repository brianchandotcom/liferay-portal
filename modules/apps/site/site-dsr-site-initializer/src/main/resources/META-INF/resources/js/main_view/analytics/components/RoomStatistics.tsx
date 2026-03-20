/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import React from 'react';

import '../../../../css/components/RoomStatistics.scss';

import moment from 'moment';

import {
	IRoomStatistics,
	IRoomStatisticsItem,
	IRoomStatisticsProps,
} from '../../../common/utils/types';
import Loader from './Loader';

const formatTime = (minutes?: number): string => {
	if (!minutes) {
		return Liferay.Util.sub(Liferay.Language.get('x-min'), 0);
	}

	const duration = moment.duration(minutes, 'minutes');

	const hours = Math.floor(duration.asHours());
	const mins = duration.minutes();

	return hours > 0
		? Liferay.Util.sub(Liferay.Language.get('x-h-x-min'), [hours, mins])
		: Liferay.Util.sub(Liferay.Language.get('x-min'), mins);
};

const formatData = (data: IRoomStatistics): IRoomStatisticsItem[] => {
	return [
		{
			className: 'icon-pink-light',
			icon: 'time',
			id: 'time',
			label: Liferay.Language.get('time-viewed'),
			value: formatTime(data.timeViewedMinutes),
		},
		{
			className: 'icon-blue-light',
			icon: 'view',
			id: 'visits',
			label: Liferay.Language.get('total-visits'),
			value: data.totalVisits || 0,
		},
		{
			className: 'icon-purple-light',
			icon: 'users',
			id: 'visitors',
			label: Liferay.Language.get('visitors'),
			value: data.uniqueVisitors || 0,
		},
		{
			className: 'icon-teal-light',
			icon: 'click',
			id: 'actions',
			label: Liferay.Language.get('actions'),
			value: data.totalActions || 0,
		},
		{
			className: 'icon-indigo-light',
			icon: 'comments',
			id: 'comments',
			label: Liferay.Language.get('comments'),
			value: data.totalComments || 0,
		},
	];
};

function RoomStatistics({data, isLoading}: IRoomStatisticsProps) {
	if (isLoading) {
		return <Loader />;
	}

	if (!data) {
		return <p>{Liferay.Language.get('no-data-available')}</p>;
	}

	const formattedData = formatData(data);

	return (
		<div className="py-4">
			<ClayLayout.Row className="align-items-center">
				{formattedData.map(
					(
						roomStatisticsItem: IRoomStatisticsItem,
						index: number
					) => {
						const showBorder = index !== formattedData.length - 1;

						return (
							<ClayLayout.Col
								className={`${showBorder ? 'border-right' : ''} pl-5`}
								key={roomStatisticsItem.id}
								size={2}
							>
								<div>
									<span className="font-weight-semi-bold mb-0 mr-2 room-statistics-label text-secondary">
										{roomStatisticsItem.label}
									</span>

									<ClayIcon
										className="text-secondary"
										symbol="question-circle"
									/>
								</div>

								<div>
									<ClayIcon
										className={roomStatisticsItem.className}
										symbol={roomStatisticsItem.icon}
									/>

									<span className="font-weight-semi-bold ml-2 room-statistics-text">
										{roomStatisticsItem.value}
									</span>
								</div>
							</ClayLayout.Col>
						);
					}
				)}
			</ClayLayout.Row>
		</div>
	);
}

export default RoomStatistics;
