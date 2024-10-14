/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MetricType} from '../../types/global';
import {getPercentage} from '../../utils/math';
import {metricNameByType} from '../../utils/metrics';
import {ChartData} from '../stacked-bar/StackedBarChart';
import {Data} from './Technology';

export function formatData(data: Data, metricType: MetricType): ChartData {
	const curMetricName = metricNameByType[metricType];

	const selectedMetric = data.deviceMetrics.find(
		({metricName}) => metricName === curMetricName
	);

	const total =
		selectedMetric?.metrics.reduce((sum, {value}) => sum + value, 0) ?? 0;

	const chartData = selectedMetric?.metrics
		.map(({value, valueKey}) => ({
			label: valueKey,
			percentage: getPercentage((value / total) * 100),
			value,
		}))
		.sort((a, b) => b.percentage - a.percentage);

	return {
		data: chartData ?? [],
		total,
	};
}
