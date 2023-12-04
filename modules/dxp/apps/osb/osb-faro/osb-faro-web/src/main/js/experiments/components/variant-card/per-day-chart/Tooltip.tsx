import * as d3 from 'd3';
import ChartTooltip, {
	Alignments,
	Weights
} from 'shared/components/chart-tooltip';
import React from 'react';
import Trend from 'shared/components/Trend';
import {Colors} from 'shared/util/charts';
import {getDate as getDateUtil} from 'shared/util/date';

export const Tooltip = ({dataPoint}) => {
	const control = dataPoint[0];
	const variant = dataPoint[1];

	const improvementLabel = improvement => {
		if (improvement) {
			return (
				<Trend
					color={improvement > 0 ? Colors.positive : Colors.negative}
					icon={improvement > 0 ? 'caret-top' : 'caret-bottom'}
					label={improvement}
				/>
			);
		}

		return <span>{'-'}</span>;
	};

	const header = [
		{
			columns: [
				{
					label: `${Liferay.Language.get(
						'variants'
					)} | ${d3.utcFormat('%b %-d %Y')(
						getDateUtil(control.payload.key)
					)}`,
					weight: Weights.Semibold,
					width: 140
				},
				{
					align: Alignments.Right,
					label: Liferay.Language.get('high'),
					weight: Weights.Semibold,
					width: 60
				},
				{
					align: Alignments.Right,
					label: Liferay.Language.get('low'),
					weight: Weights.Semibold,
					width: 60
				},
				{
					align: Alignments.Right,
					label: Liferay.Language.get('median'),
					weight: Weights.Semibold,
					width: 60
				},
				{
					label: '',
					width: 60
				}
			]
		}
	];

	const rows = [
		{
			columns: [
				{
					color: control.color,
					label: control.name,
					weight: Weights.Semibold
				},
				{
					align: Alignments.Right,
					label: control.payload.tooltip.control.high
				},
				{
					align: Alignments.Right,
					label: control.payload.tooltip.control.low
				},
				{
					align: Alignments.Right,
					label: control.payload.tooltip.control.median
				},
				{
					align: Alignments.Right,
					label: improvementLabel(
						control.payload.tooltip.control.improvement
					)
				}
			]
		},
		{
			columns: [
				{
					color: variant.color,
					label: variant.name,
					weight: Weights.Semibold
				},
				{
					align: Alignments.Right,
					label: variant.payload.tooltip.variant.high
				},
				{
					align: Alignments.Right,
					label: variant.payload.tooltip.variant.low
				},
				{
					align: Alignments.Right,
					label: variant.payload.tooltip.variant.median
				},
				{
					align: Alignments.Right,
					label: improvementLabel(
						variant.payload.tooltip.variant.improvement
					)
				}
			]
		}
	];

	return (
		<div className='bb-tooltip-container position-static'>
			<ChartTooltip header={header} rows={rows as any} />
		</div>
	);
};
