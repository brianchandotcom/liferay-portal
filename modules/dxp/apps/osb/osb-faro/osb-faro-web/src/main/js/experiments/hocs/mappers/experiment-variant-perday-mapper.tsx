import * as d3 from 'd3';
import ChartTooltip, {
	Alignments,
	Weights
} from 'shared/components/chart-tooltip';
import React from 'react';
import {CHART_COLORS} from 'shared/components/Chart';
import {Column} from 'shared/components/chart-tooltip/types';
import {
	dateFormatter,
	formatHistogramKeyValue,
	formatYAxis,
	getFormattedDataHistogram,
	normalizeHistogram,
	sortOrderExperiment,
	TOOLTIP_METRICS
} from 'experiments/util/experiments';
import {getDate as getDateUtil} from 'shared/util/date';

const CONTROL_COLOR = '#6B6C7E';

export default metricUnit => ({experiment}) => {
	if (!experiment.dxpVariants || experiment.dxpVariants.length === 0) {
		return {
			empty: true
		};
	}

	const normalizedHistogram = normalizeHistogram(experiment);

	const variantsKeyValue = formatHistogramKeyValue(
		normalizedHistogram,
		metricUnit
	);

	const data = normalizedHistogram
		.sort(sortOrderExperiment)
		.map(({control, dxpVariantName, variantsHistogram}, index) => {
			const data = getFormattedDataHistogram(variantsHistogram, index);

			return {
				color: control ? CONTROL_COLOR : CHART_COLORS[index - 1],
				data,
				name: dxpVariantName
			};
		});

	return {
		chartType: 'area',
		data,
		format: formatYAxis(metricUnit),
		intervals: normalizedHistogram[0].variantsHistogram.map(
			({processedDate}) => processedDate
		),
		Tooltip: ({dataPoint}) => {
			const date = dateFormatter(getDateUtil(dataPoint[0].payload.key));
			const variant = variantsKeyValue[dataPoint[0].payload.id][date];

			let header: {columns: Column[]}[];
			let rows: {columns: Column[]}[];

			if (dataPoint.length > 1) {
				header = [
					{
						columns: [
							{
								label: `${Liferay.Language.get(
									'variants'
								)} - ${d3.utcFormat('%m/%d/%Y')(
									getDateUtil(dataPoint[0].payload.key)
								)}`,
								weight: Weights.Semibold,
								width: 100
							},
							...TOOLTIP_METRICS.map(({title}) => ({
								align: Alignments.Right,
								label: title,
								weight: Weights.Semibold,
								width: 60
							}))
						]
					}
				];

				rows = dataPoint.map(point => {
					const variant = variantsKeyValue[point.payload.id][date];

					return {
						columns: [
							{
								color: point.color,
								label: variant.name,
								truncated: true
							},
							...TOOLTIP_METRICS.map(
								({accessor, dataRenderer}) => ({
									align: 'right',
									className: 'align-items-end',
									label: dataRenderer
										? dataRenderer(
												variantsKeyValue[
													point.payload.id
												][date]
										  )
										: variantsKeyValue[point.payload.id][
												date
										  ][accessor],
									weight: 'semibold'
								})
							)
						]
					};
				});
			} else {
				header = [
					{
						columns: [
							{
								label: `${variant.name} - ${d3.utcFormat(
									'%m/%d/%Y'
								)(dataPoint[0].x)}`,
								weight: Weights.Semibold
							},
							{
								label: ''
							}
						]
					}
				];

				rows = TOOLTIP_METRICS.map(
					({accessor, dataRenderer, title}) => ({
						columns: [
							{
								label: title
							},
							{
								align: Alignments.Right,
								label: dataRenderer
									? dataRenderer(
											variantsKeyValue[dataPoint[0].id][
												date
											]
									  )
									: variantsKeyValue[dataPoint[0].id][date][
											accessor
									  ],
								weight: Weights.Semibold
							}
						]
					})
				);
			}

			return (
				<div className='bb-tooltip-container position-static'>
					<ChartTooltip header={header} rows={rows} />
				</div>
			);
		}
	};
};
