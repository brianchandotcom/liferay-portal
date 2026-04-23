import HTMLBarChart, {IHTMLBarChartProps} from 'shared/components/HTMLBarChart';
import Legend from 'shared/components/Legend';
import React from 'react';
import {
	getLegendData,
	getMedianGraphData,
	getMetricUnit,
	mergedVariants
} from 'experiments/util/experiments';
import {IExperiment} from '../summary-card/types';
import {MetricName} from 'experiments/util/types';

export const MediansChart = ({experiment}: {experiment: IExperiment}) => {
	const {dxpVariants, goal, metrics} = experiment;

	const variantMetrics = metrics?.variantMetrics ?? [];

	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metricUnit = getMetricUnit(goal?.metric as MetricName);
	const mediansData = getMedianGraphData({
		dxpVariants: variants,
		metricUnit
	});

	return (
		<>
			<HTMLBarChart {...(mediansData as unknown as IHTMLBarChartProps)} />

			<Legend
				data={getLegendData(variants).map(item => ({
					...item,
					color: item.color ?? ''
				}))}
			/>
		</>
	);
};
