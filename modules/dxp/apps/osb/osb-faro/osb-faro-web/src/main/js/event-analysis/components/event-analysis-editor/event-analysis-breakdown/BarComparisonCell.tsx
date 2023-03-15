import BarComparisonTable, {
	BarComparisonTableItems
} from './BarComparisonTable';
import React from 'react';
import {BreakdownDataItem, Event} from 'event-analysis/utils/types';
import {get} from 'lodash';

enum BAR_COMPARISON_COLORS {
	Blue = 'blue',
	Green = 'green'
}

const EMPTY_BAR_COLOR = '#CDCED9';

const MAP_COLORS: Record<
	BAR_COMPARISON_COLORS,
	Array<{
		current: string;
		previous: string;
	}>
> = {
	blue: [
		{
			current: '#187FFF',
			previous: '#97C5FF'
		},
		{
			current: '#4B9BFF',
			previous: '#97C5FF'
		}
	],
	green: [
		{
			current: '#31BE88',
			previous: '#8DE2C1'
		},
		{
			current: '#3CCD95',
			previous: '#8DE2C1'
		}
	]
};

interface IBarComparisonCellProps extends React.HTMLAttributes<HTMLElement> {
	compareToPrevious: boolean;
	event: Event;
	events: BreakdownDataItem[];
	topValue: number;
}

const BarComparisonCell: React.FC<IBarComparisonCellProps> = ({
	compareToPrevious = false,
	event,
	events = [],
	topValue
}) => {
	const isComparingSegment = get(events[0], 'breakdownItems', []).length > 1;

	const sections = getSections(
		events,
		compareToPrevious,
		isComparingSegment,
		topValue
	);

	return (
		<div className='table-responsive table-root bar-comparison-root'>
			{sections.map((items, i) => (
				<BarComparisonTable
					event={event}
					isComparingSegment={isComparingSegment}
					items={items}
					key={i}
				/>
			))}
		</div>
	);
};

const getSections = (
	events: BreakdownDataItem[],
	compareToPrevious: boolean,
	isComparingSegment: boolean,
	topValue: number
): BarComparisonTableItems[] => {
	const isComparingEvent = events.length > 1;

	const sections = [];
	let data = [];

	const addToData = (
		{name, previousValue = undefined, value},
		color,
		index
	) => {
		data.push({
			isPreviousValue: false,
			name,
			percent: value / topValue,
			style: {
				backgroundColor:
					value > 0
						? MAP_COLORS[color][index].current
						: EMPTY_BAR_COLOR
			},
			value
		});

		if (compareToPrevious) {
			data.push({
				isPreviousValue: true,
				name: Liferay.Language.get('previous-value'),
				percent: previousValue / topValue,
				style: {
					backgroundColor:
						previousValue > 0
							? MAP_COLORS[color][index].previous
							: EMPTY_BAR_COLOR
				},
				value: previousValue
			});
		}
	};

	events.forEach((event, i) => {
		const color =
			i === 0 ? BAR_COMPARISON_COLORS.Blue : BAR_COMPARISON_COLORS.Green;

		if (i !== 0 && isComparingEvent && isComparingSegment) {
			sections.push([...data]);
			data = [];
		}

		if (isComparingSegment) {
			event.breakdownItems.forEach((item, i) => {
				addToData(item, color, i);
			});
		} else {
			addToData(event, color, i);
		}
	});

	sections.push([...data]);
	return sections;
};

export default BarComparisonCell;
