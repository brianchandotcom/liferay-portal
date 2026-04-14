/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import '../../../../css/components/RoomStatistics.scss';

import moment from 'moment';

import useAnalyticsQuery from '../../../common/hooks/useAnalyticsQuery';
import {
	IRoomStatistics,
	IRoomStatisticsItem,
} from '../../../common/utils/types';
import RoomStatisticsQuery from '../queries/RoomStatisticsQuery';
import AnalyticsFrame from './AnalyticsFrame';
import Loader from './Loader';

const formatTime = (minutes?: number): string => {
	if (!minutes) {
		return sub(Liferay.Language.get('x-minutes'), 0);
	}

	const duration = moment.duration(minutes, 'minutes');

	const hours = Math.floor(duration.asHours());
	const mins = duration.minutes();

	const hoursLabel =
		hours === 1
			? Liferay.Language.get('1-hour').toLowerCase()
			: sub(Liferay.Language.get('x-hours'), [hours]).toLowerCase();

	const minutesLabel =
		mins === 1
			? Liferay.Language.get('1-minute').toLowerCase()
			: sub(Liferay.Language.get('x-minutes'), [mins]).toLowerCase();

	return `${hoursLabel} ${minutesLabel}`;
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

function RoomStatistics({
	dsrDevEnvEnabled: useDevEnvData,
}: {
	dsrDevEnvEnabled: boolean;
}) {
	const [data, setData] = useState<IRoomStatisticsItem[]>([]);
	const [element, setElement] = useState<HTMLElement | null>(null);

	const {isLoading, response} = useAnalyticsQuery({
		element,
		query: RoomStatisticsQuery,
		settings: {
			checkViewportVisibility: true,
			useDevEnvData,
		},
		variables: {
			channelId: '',
			entityType: 'INDIVIDUAL',
			keywords: '',
			page: 1,
			rangeEnd: null,
			rangeKey: 7,
			rangeStart: null,
			size: 20,
		},
	});

	useEffect(() => {
		if (response) {
			setData(formatData(response));
		}

		return () => {};
	}, [response, setData]);

	return (
		<AnalyticsFrame>
			<div className="room-statistics-container" ref={setElement}>
				{isLoading ? (
					<Loader />
				) : !data?.length ? (
					<p className="mt-3 text-center text-muted">
						{Liferay.Language.get('no-data-available')}
					</p>
				) : (
					<div className="p-4">
						<ClayLayout.Row className="align-items-center justify-content-between">
							{data.map(
								(
									roomStatisticsItem: IRoomStatisticsItem,
									index: number
								) => {
									return (
										<ClayLayout.Col
											className={`${index !== 0 ? 'border-left' : ''} col-auto pl-5`}
											key={roomStatisticsItem.id}
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
													className={
														roomStatisticsItem.className
													}
													symbol={
														roomStatisticsItem.icon
													}
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
				)}
			</div>
		</AnalyticsFrame>
	);
}

export default RoomStatistics;
