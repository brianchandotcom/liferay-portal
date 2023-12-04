import HTMLBarChart, {IHTMLBarChartProps} from 'shared/components/HTMLBarChart';
import Legend from 'shared/components/Legend';
import React from 'react';
import {
	getLegendData,
	getMedianGraphData,
	getMetricUnit,
	mergedVariants
} from 'experiments/util/experiments';

export const MediansChart = ({experiment}) => {
	const {
		dxpVariants,
		goal,
		metrics: {variantMetrics}
	} = experiment;

	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metricUnit = getMetricUnit(goal?.metric);
	const mediansData = getMedianGraphData({
		dxpVariants: variants,
		metricUnit
	});

	return (
		<>
			<HTMLBarChart {...(mediansData as IHTMLBarChartProps)} />

			<Legend data={getLegendData(variants)} />
		</>
	);
};
