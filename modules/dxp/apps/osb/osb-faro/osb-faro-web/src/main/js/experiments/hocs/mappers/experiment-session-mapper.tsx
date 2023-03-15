import ChartTooltip from 'shared/components/chart-tooltip';
import React from 'react';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {getAxisFormatter} from 'shared/util/charts';
import {getFormattedDataTooltip} from 'experiments/util/experiments';

const {stark: CHART_BLUE} = CHART_COLOR_NAMES;

export default ({experiment}) => {
	if (
		!experiment.sessionsHistogram ||
		experiment.sessionsHistogram.length === 0
	) {
		return {
			empty: true
		};
	}

	return {
		data: [
			{
				color: CHART_BLUE,
				data: experiment.sessionsHistogram,
				name: Liferay.Language.get('total')
			}
		],
		format: getAxisFormatter('number'),
		intervals: experiment.sessionsHistogram.map(({key}) => key),
		Tooltip: ({dataPoint}) => (
			<div className='bb-tooltip-container position-static'>
				<ChartTooltip {...getFormattedDataTooltip(dataPoint)} />
			</div>
		)
	};
};
