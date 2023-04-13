// @ts-nocheck - Fix it at this LRAC-13388

import * as d3 from 'd3';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useRef} from 'react';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';
import {toThousands} from 'shared/util/numbers';

const {martellD2, martellD4, martellL1, martellL4} = CHART_COLOR_NAMES;

const DEFAULT_COLOR_RANGE = [
	'#dddfe2',
	martellL4,
	martellL1,
	martellD2,
	martellD4
];
const MARGIN = {bottom: 50, left: 40, right: 20, top: 20};

const attachChart = (chartElement: HTMLElement, props: HeatmapChartIProps) => {
	const {
		columnAxisFormatter,
		data,
		height,
		rowAxisFormatter,
		thresholds,
		width
	} = props;

	const computedHeight = height - MARGIN.top - MARGIN.bottom;
	const computedWidth = width - MARGIN.left - MARGIN.right;

	const legendMarginTop = 16;

	// Attach tooltip container
	d3.select('body')
		.append('div')
		.style('opacity', 0)
		.attr('class', 'tooltip heatmap-tooltip bb-tooltip-container');

	// Append the svg object to the chart element
	const svg = d3
		.select(chartElement)
		.append('svg')
		.attr('viewBox', `0 0 ${width} ${height}`)
		.append('g')
		.attr('transform', `translate(${MARGIN.left},${MARGIN.top})`);

	// Get row and column keys
	const columns = d3.map(data, ({column}) => column).keys();
	const rows = d3.map(data, ({row}) => row).keys();

	// Build X scales and axis
	const x = d3
		.scaleBand()
		.domain(columns)
		.range([0, computedWidth])
		.padding(0.1);

	svg.append('g')
		.attr('class', 'axis column-axis')
		.call(d3.axisTop(x).tickSize(0).tickFormat(columnAxisFormatter))
		.select('.domain')
		.remove();

	// Build Y scales and axis
	const y = d3
		.scaleBand()
		.domain(rows)
		.range([0, computedHeight])
		.padding(0.1);

	svg.append('g')
		.attr('class', 'axis row-axis')
		.call(d3.axisLeft(y).tickSize(0).tickFormat(rowAxisFormatter))
		.select('.domain')
		.remove();

	// Add the squares
	svg.append('g')
		.attr('class', 'rects')
		.selectAll()
		.data(data, ({column, row}) => `${column}:${row}`)
		.enter()
		.append('rect')
		.attr('x', ({column}) => x(column))
		.attr('y', ({row}) => y(row))
		.attr('width', x.bandwidth())
		.attr('height', y.bandwidth());

	const legend = svg
		.append('g')
		.attr('class', 'heatmap-legend')
		.attr(
			'transform',
			`translate(${0},${computedHeight + legendMarginTop})`
		)
		.selectAll()
		.data(thresholds);

	// Add the legend items
	legend.enter().append('rect');

	// Add legend labels
	legend.enter().append('text');

	return svg;
};

const updateChart = (chartElement, props) => {
	const {colorRange, data, renderTooltip, thresholds, width} = props;

	const tooltip = d3.select('.heatmap-tooltip');
	const computedWidth = width - MARGIN.left - MARGIN.right;

	const legendItems = thresholds.slice(1);
	const legendColors = colorRange.slice(1);
	const legendItemPadding = 4;
	const legendItemWidth =
		(computedWidth - legendItemPadding * (legendItems.length - 1)) /
		legendItems.length;

	const svg = d3.select(chartElement);

	// Build color scale
	const colorScale = d3.scaleThreshold().domain(thresholds).range(colorRange);

	// Update the square colors
	svg.select('.rects')
		.selectAll('rect')
		.data(data, ({column, row}) => `${column}:${row}`)
		.style('fill', ({value}) => colorScale(value))
		.on('mouseenter', (d, i, nodes) => {
			tooltip.style('opacity', 1);

			tooltip.html(renderTooltip(d));

			const {width: widthRect, x: pageXRect} = nodes[
				i
			].getBoundingClientRect();

			const {
				width: widthTooltip
			} = tooltip.node().getBoundingClientRect();

			tooltip
				.style(
					'left',
					`${pageXRect + widthRect / 2 - widthTooltip / 2}px`
				)
				.style('top', `${d3.event.pageY + 28}px`);

			d3.select(nodes[i]).style(
				'fill',
				colorRange[colorRange.length - 1]
			);
		})
		.on('mouseout', ({value}, i, nodes) => {
			tooltip.style('opacity', 0);

			d3.select(nodes[i]).style('fill', colorScale(value));
		});

	// Update the legend
	const legendItemsSelection = svg
		.select('.heatmap-legend')
		.selectAll('rect')
		.data(legendItems);
	const legendLabelSelection = svg
		.select('.heatmap-legend')
		.selectAll('text')
		.data(thresholds);

	// Remove any un-needed legends
	legendItemsSelection.exit().remove();
	legendLabelSelection.exit().remove();

	// Add any additional legends
	legendItemsSelection.enter().append('rect');
	legendLabelSelection.enter().append('text');

	// Update all legend items
	legendItemsSelection
		.attr('x', (_, i) =>
			i > 0
				? i * legendItemWidth + i * legendItemPadding
				: i * legendItemWidth
		)
		.attr('y', 0)
		.attr('width', legendItemWidth)
		.attr('height', 14)
		.style('fill', (_, i) => legendColors[i]);

	// Update all legend labels
	legendLabelSelection
		.attr('class', 'legend-label')
		.attr('x', (_, i) =>
			i > 0 ? i * legendItemWidth + i * legendItemPadding : 0
		)
		.attr('y', 30)
		.text(d => toThousands(d));
};

const destroyChart = svg => {
	svg && svg.remove();

	d3.select('.heatmap-tooltip').remove();
};

export const getNicedExtent = ([min, max]: [number, number]): [
	number,
	number
] => {
	const threshold = d3.scaleQuantize().domain([min, max]).nice(3);

	const thresholds = threshold.ticks(4);

	return [thresholds[0], thresholds[thresholds.length - 1]];
};

export const getThresholdsFromData = (data: {value: number}[]): number[] => {
	const valuesOverZero = data.map(({value}) => value).filter(d => d > 0);
	const extent = d3.extent(valuesOverZero);

	const [min, max]: [number, number] =
		extent[1] > 5 ? getNicedExtent(extent) : extent;

	const stepSize = (max - min) / 4;

	return [
		min === 0 ? 1 : min,
		Math.round(min + stepSize),
		Math.round(min + stepSize * 2),
		Math.round(min + stepSize * 3),
		max
	];
};

interface HeatmapChartIProps extends React.HTMLAttributes<HTMLElement> {
	colorRange?: string[];
	columnAxisFormatter?: (label: string) => string;
	data: any;
	height?: number;
	renderTooltip?: (val) => string;
	rowAxisFormatter?: (label: string) => string;
	thresholds?: number[];
	width?: number;
}

const HeatmapChart: React.FC<HeatmapChartIProps> = props => {
	const chartRef = useRef();
	const {className, data} = props;

	const thresholds = props.thresholds || getThresholdsFromData(data);

	useEffect(() => {
		const svg = attachChart(chartRef.current, {...props, thresholds});

		return () => destroyChart(svg);
	}, []);

	useEffect(() => {
		updateChart(chartRef.current, {...props, thresholds});
	}, [props.data]);

	return (
		<div className={getCN(className, 'heatmap-chart-root')}>
			<div className='heatmap-chart' id='chart' ref={chartRef} />
		</div>
	);
};

HeatmapChart.propTypes = {
	colorRange: PropTypes.array,
	columnAxisFormatter: PropTypes.func,
	data: PropTypes.array.isRequired,
	height: PropTypes.number,
	renderTooltip: PropTypes.func,
	rowAxisFormatter: PropTypes.func,
	thresholds: PropTypes.array,
	width: PropTypes.number
};

HeatmapChart.defaultProps = {
	colorRange: DEFAULT_COLOR_RANGE,
	columnAxisFormatter: val => val,
	height: 440,
	renderTooltip: ({value}) => value,
	rowAxisFormatter: val => val,
	width: 300
};

export default HeatmapChart;
