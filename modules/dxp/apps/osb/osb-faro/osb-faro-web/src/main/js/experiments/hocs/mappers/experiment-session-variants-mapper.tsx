import ChartTooltip from 'shared/components/chart-tooltip';
import React from 'react';
import {CHART_COLORS} from 'shared/components/Chart';
import {getAxisFormatter} from 'shared/util/charts';
import {
	getFormattedDataTooltip,
	sortOrderExperiment
} from 'experiments/util/experiments';

const CONTROL_COLOR = '#6B6C7E';

export default ({experiment}) => {
	if (!experiment.dxpVariants || experiment.dxpVariants.length === 0) {
		return {
			empty: true
		};
	}

	const data = experiment.dxpVariants
		.sort(sortOrderExperiment)
		.map(({control, dxpVariantName, sessionsHistogram}, index) => ({
			color: control ? CONTROL_COLOR : CHART_COLORS[index - 1],
			data: sessionsHistogram,
			name: dxpVariantName
		}));

	return {
		data,
		format: getAxisFormatter('number'),
		intervals: experiment.dxpVariants[0].sessionsHistogram.map(
			({key}) => key
		),
		Tooltip: ({dataPoint}) => (
			<div className='bb-tooltip-container position-static'>
				<ChartTooltip {...getFormattedDataTooltip(dataPoint)} />
			</div>
		)
	};
};
