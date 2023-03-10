/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import * as d3Instance from 'd3';

import {DATA_COLORS} from './constants';
import {findAndReplaceProperty, getPercentLabel} from './graph.util';

interface DonutLegendOptions {
	data: any[];
	elementId: string;
	total: number;
}

export const TPL_LEGEND_ITEM = `
<div class="tr-legend-container__item__square" style="background-color: {color}"></div>
<div class="tr-legend-container__item__metrics">
	<div class="tr-legend-container__item__metrics__metric">
		<span class="tr-legend-container__item__metrics__metric--value">{value}</span>
		<span class="tr-legend-container__item__metrics__metric--percent">({percent})</span>
	</div>
	<div class="tr-legend-container__item__metrics__label">{label}</div>
</div>
 `;

export function getDonutLegend(graph: any, options: DonutLegendOptions) {
	const {data, elementId, total} = options;

	if (!d3Instance) {
		console.warn('d3 instance is not present');

		return;
	}

	d3Instance
		.select('#' + elementId)
		.insert('ul', '#' + elementId)
		.attr('class', 'tr-legend-container')
		.selectAll('li')
		.data(data)
		.enter()
		.append('li')
		.attr('class', 'tr-legend-container__item')
		.html((id) => {
			const [value] = graph.data.values(id);

			return findAndReplaceProperty(TPL_LEGEND_ITEM, {
				color: (DATA_COLORS as any)[
					`metrics.${id.toLowerCase().replace(' ', '-')}`
				],
				label: id,
				percent: getPercentLabel((value / (total as number)) * 100),
				value,
			});
		})
		.on('mouseover', (id) => graph.focus(id.toUpperCase()))
		.on('mouseout', () => graph.revert());
}
