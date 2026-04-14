/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import useAnalyticsQuery from '../../../common/hooks/useAnalyticsQuery';
import {IEngagementChartItem} from '../../../common/utils/types';
import RecentEngagementChartQuery from '../queries/RecentEngagementChartQuery';
import {BASE_URL} from '../utils/constants';
import AnalyticsFrame from './AnalyticsFrame';
import EngagementChart from './EngagementChart';
import Loader from './Loader';

function RecentEngagementChart({
	dsrDevEnvEnabled: useDevEnvData,
}: {
	dsrDevEnvEnabled: boolean;
}) {
	const [data, setData] = useState<IEngagementChartItem[]>([]);
	const [element, setElement] = useState<HTMLElement | null>(null);

	const {isLoading, response} = useAnalyticsQuery({
		element,
		query: RecentEngagementChartQuery,
		settings: {
			checkViewportVisibility: true,
			useDevEnvData,
		},
		variables: {
			channelId: '',
			devices: 'Any',
			interval: 'D',
			location: 'Any',
			rangeEnd: null,
			rangeKey: 7,
			rangeStart: null,
		},
	});

	useEffect(() => {
		if (response) {
			setData(response);
		}

		return () => {};
	}, [response]);

	return (
		<AnalyticsFrame
			icon="analytics"
			title={Liferay.Language.get('recent-engagement')}
			url={`${BASE_URL}/view-timeline`}
		>
			<div ref={setElement}>
				{isLoading ? (
					<Loader />
				) : !data?.length ? (
					<p className="mt-3 text-center text-muted">
						{Liferay.Language.get('no-data-available')}
					</p>
				) : (
					<EngagementChart data={data} />
				)}
			</div>
		</AnalyticsFrame>
	);
}

export default RecentEngagementChart;
