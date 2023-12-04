import React from 'react';
import {ComposedChart} from '../../ComposedChart';
import {formatYAxis} from 'experiments/util/experiments';
import {getMetricUnit} from 'experiments/util/experiments';
import {Tooltip} from './Tooltip';

const formatData = experiment => {
	const intervals = [];
	const chartData = [];

	const metricUnit = getMetricUnit(experiment.goal?.metric);
	const format = value => formatYAxis(metricUnit)(value);

	experiment.metricsHistogram.forEach(metric => {
		const control = metric.variantMetrics[0];
		const variant = metric.variantMetrics[1];

		chartData.push({
			data_control: control.median,
			data_variant: variant.median,
			key: metric.processedDate,
			tooltip: {
				control: {
					high: format(control.confidenceInterval[1]),
					improvement:
						control.improvement && format(control.improvement),
					low: format(control.confidenceInterval[0]),
					median: format(control.median)
				},
				variant: {
					high: format(variant.confidenceInterval[1]),
					improvement:
						variant.improvement && format(variant.improvement),
					low: format(variant.confidenceInterval[0]),
					median: format(variant.median)
				}
			}
		});

		intervals.push(metric.processedDate);
	});

	return {
		controlLabel: experiment.dxpVariants[0].dxpVariantName,
		data: chartData,
		format: formatYAxis(metricUnit),
		intervals,
		variantLabel: experiment.dxpVariants[1].dxpVariantName
	};
};

export const PerDayChart = ({experiment}) => (
	<ComposedChart
		chartType='area'
		data={formatData(experiment)}
		Tooltip={Tooltip}
	/>
);
