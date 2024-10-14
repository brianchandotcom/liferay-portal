/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Alignments, RangeSelectors, Weights} from '../../types/global';
import {formatTooltipDate} from '../../utils/date';
import ChartTooltip from '../ChartTooltip';
import {IAccessibleTickProps} from '../metrics/AccessibleTick';
import {DotProps} from '../metrics/Dots';
import {DataKey} from '../metrics/MetricsChart';
import {FormattedData} from '../metrics/utils';
import {Dot} from './InteractionsByPageChart';

const TOOLTIP_FIRST_COLUMN_WIDTH = 250;

interface IInteractionsByPageChartTooltipProps
	extends IAccessibleTickProps,
		React.HTMLAttributes<HTMLDivElement> {
	formattedData: FormattedData;
	rangeSeletor: RangeSelectors;
	title?: string;
}

const InteractionsByPageChartTooltip: React.FC<
	IInteractionsByPageChartTooltipProps
> = ({formattedData, index, rangeSeletor, style, title}) => {
	const payload = formattedData.combinedData?.[index];

	if (!payload) {
		return null;
	}

	const header = [
		{
			columns: [
				{
					label: title ?? '',
					weight: Weights.Semibold,
					width: TOOLTIP_FIRST_COLUMN_WIDTH,
				},
				{
					align: Alignments.Right,
					label: formatTooltipDate(
						payload.x as unknown as Date,
						rangeSeletor
					),
					width: 55,
				},
			],
		},
	];

	const rows = Object.keys(formattedData.data)
		.filter(
			(dataKey) => dataKey !== DataKey.AxisX && dataKey !== DataKey.AxisY
		)
		.map((dataKey) => {
			const page = formattedData.data[dataKey];
			const SelectedDot: React.JSXElementConstructor<DotProps> =
				Dot[dataKey as DataKey];

			return {
				columns: [
					{
						label: () => (
							<>
								<span className="mr-2">
									<SelectedDot
										displayOutsideOfRecharts
										size={8}
										stroke={page?.color ?? 'none'}
									/>
								</span>

								<span>
									<div
										style={{
											maxWidth:
												TOOLTIP_FIRST_COLUMN_WIDTH,
										}}
									>
										<div className="text-truncate">
											{page.title}
										</div>
									</div>
								</span>
							</>
						),
					},
					{
						align: Alignments.Right,
						label: page.format?.(payload[dataKey]),
					},
				],
			};
		});

	return (
		<div
			className="bb-tooltip-container metrics-chart__tooltip"
			style={{...style, maxWidth: 400, position: 'static'}}
		>
			<ChartTooltip header={header} rows={rows} />
		</div>
	);
};

export default InteractionsByPageChartTooltip;
