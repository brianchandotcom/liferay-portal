/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo, useState} from 'react';
import {Line} from 'recharts';

import {Colors, MetricType} from '../../../types/global';
import {formatTooltipDate, toUnix} from '../../../utils/date';
import {CircleDot, DashedDotIcon, PreviousDot} from '../../metrics/Dots';
import MetricsChart from '../../metrics/MetricsChart';
import {formatter, getFillOpacity} from '../../metrics/utils';
import {Histogram, ICommonProps} from './AssetMetrics';
import {AssetMetricsTooltip} from './AssetMetricsTooltip';

export enum MetricDataKey {
	Current = 'METRIC_DATA_KEY',
	Previous = 'PREV_METRIC_DATA_KEY',
}

type FormattedData = {
	combinedData: {[key in string]: number | string | null}[];
	data: {
		[key in string]: {
			color?: Colors;
			format?: (value: any) => any;
			title: string;
			total: string | number;
			url?: string;
		};
	};
	intervals: (number | null)[];
};

function getTitle(metricType: MetricType) {
	const title: Partial<{
		[key in MetricType]: string;
	}> = {
		[MetricType.Views]: Liferay.Language.get('views'),
		[MetricType.Impressions]: Liferay.Language.get('impressions'),
		[MetricType.Downloads]: Liferay.Language.get('downloads'),
	};

	return title[metricType];
}

export function formatData(
	histogram: Histogram,
	metricType: MetricType
): FormattedData {
	const title = getTitle(metricType) as string;

	const data = {
		METRIC_DATA_KEY: {
			color: Colors.Blue,
			title,
			total: formatter('number')(histogram?.totalValue ?? 0),
		},
		PREV_METRIC_DATA_KEY: {
			color: Colors.LightGray,
			title,
			total: formatter('number')(histogram?.totalValue ?? 0),
		},
		x: {
			title: Liferay.Language.get('x'),
			total: 0,
		},
		y: {
			title: Liferay.Language.get('y'),
			total: 0,
		},
	};

	if (histogram?.metrics.length) {
		const axisXData = histogram.metrics.map(({valueKey}) =>
			toUnix(valueKey)
		);

		const combinedData = [];

		const metricData = histogram.metrics.map(({value}) => value);
		const prevMetricData = histogram.metrics.map(
			({previousValue}) => previousValue
		);

		for (let i = 0; i < axisXData.length; i++) {
			combinedData.push({
				METRIC_DATA_KEY: metricData[i],
				PREV_METRIC_DATA_KEY: prevMetricData[i],
				x: axisXData[i],
				y: null,
			});
		}

		return {
			combinedData,
			data,
			intervals: axisXData,
		};
	}

	return {
		combinedData: [],
		data,
		intervals: [],
	};
}

export type DotProps = {
	cx?: number;
	cy?: number;
	displayOutsideOfRecharts?: boolean;
	size?: number;
	stroke: string;
	strokeOpacity?: string;
	value?: number | null;
};

export interface IMetricsChartLegendProps {
	activeTabIndex: boolean;
	legendItems: {
		Dot: React.JSXElementConstructor<DotProps>;
		block?: boolean;
		dataKey: string;
		dotColor: string;
		title: string;
		total?: string | number;
		url?: string;
	}[];
	onDatakeyChange: (dataKey: string | null) => void;
}

const AssetMetricsChart: React.FC<ICommonProps> = ({histogram, metricType}) => {
	const [activeTabIndex, setActiveTabIndex] = useState(false);

	const [activeLegendItem, setActiveLegendItem] = useState<string | null>(
		null
	);

	const formattedData = useMemo(
		() => formatData(histogram, metricType),
		[metricType, histogram]
	);

	const metricsChartData = formattedData.data[MetricDataKey.Current];
	const prevMetricsChartData = formattedData.data[MetricDataKey.Previous];

	const legendItems: IMetricsChartLegendProps['legendItems'] = [
		{
			Dot: DashedDotIcon,
			dataKey: MetricDataKey.Previous,
			dotColor: prevMetricsChartData?.color ?? 'none',
			title: Liferay.Language.get('previous-period'),
		},
		{
			Dot: CircleDot,
			dataKey: MetricDataKey.Current,
			dotColor: metricsChartData?.color ?? 'none',
			title: Liferay.Language.get('current-period'),
		},
	];

	return (
		<>
			<MetricsChart
				MetricsChartTooltip={AssetMetricsTooltip}
				activeTabIndex={activeTabIndex}
				emptyChartProps={{
					description: Liferay.Language.get(
						'check-back-later-to-see-if-your-data-sources-are-populated-with-data'
					),
					link: {
						title: Liferay.Language.get(
							'learn-more-about-visitors-behavior'
						),
						url: 'https://learn.liferay.com/w/dxp/content-authoring-and-management/content-dashboard/content-dashboard-interface',
					},
					show: !formattedData.combinedData.length,
					title: Liferay.Language.get(
						'there-is-no-data-for-visitors-behavior'
					),
				}}
				formattedData={formattedData}
				legendAlign="text-right"
				legendItems={legendItems}
				onChartBlur={() => setActiveTabIndex(false)}
				onChartFocus={() => setActiveTabIndex(true)}
				onDatakeyChange={(dataKey) => setActiveLegendItem(dataKey)}
				rangeSelector={30 as any}
				tooltipTitle={getTitle(metricType) as string}
				xAxisDataKey="METRIC_DATA_KEY"
			>
				<Line
					activeDot={
						<PreviousDot
							fill="white"
							stroke={metricsChartData.color ?? 'none'}
						/>
					}
					animationDuration={100}
					dataKey="METRIC_DATA_KEY"
					dot={
						<PreviousDot
							fill={metricsChartData.color ?? 'none'}
							stroke={metricsChartData.color ?? 'none'}
						/>
					}
					fill={Colors.Blue}
					fillOpacity={getFillOpacity(
						MetricDataKey.Current,
						activeLegendItem
					)}
					legendType="plainline"
					stroke={metricsChartData.color ?? 'none'}
					strokeOpacity={getFillOpacity(
						MetricDataKey.Current,
						activeLegendItem
					)}
					strokeWidth={2}
					type="linear"
				/>

				<Line
					activeDot={
						<PreviousDot
							fill="none"
							stroke={prevMetricsChartData.color ?? 'none'}
						/>
					}
					animationDuration={100}
					dataKey="PREV_METRIC_DATA_KEY"
					dot={false}
					fill={Colors.LightGray}
					fillOpacity={getFillOpacity(
						MetricDataKey.Previous,
						activeLegendItem
					)}
					legendType="plainline"
					stroke={prevMetricsChartData.color ?? 'none'}
					strokeDasharray="5 5"
					strokeOpacity={getFillOpacity(
						MetricDataKey.Previous,
						activeLegendItem
					)}
					strokeWidth={2}
					type="linear"
				/>
			</MetricsChart>

			{/* Used on playwright to test data */}

			<div
				data-qa-cur-chart-data={JSON.stringify(
					formattedData.combinedData.map(
						(dataKey) => dataKey[MetricDataKey.Current]
					)
				)}
				data-qa-prev-chart-data={JSON.stringify(
					formattedData.combinedData.map(
						(dataKey) => dataKey[MetricDataKey.Previous]
					)
				)}
				data-qa-tooltip-formatted-date={JSON.stringify(
					formatTooltipDate(
						formattedData.combinedData[0]?.['x'] as number,
						30 as any
					)
				)}
				data-testid="metrics-chart-data"
			/>
		</>
	);
};

export {AssetMetricsChart};
