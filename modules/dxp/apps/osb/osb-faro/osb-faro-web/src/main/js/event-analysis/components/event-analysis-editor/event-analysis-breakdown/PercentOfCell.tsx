import React from 'react';
import {BreakdownDataItem} from 'event-analysis/utils/types';
import {get, round} from 'lodash';
import {getPercentage} from 'shared/util/util';

interface IPercentOfCellProps extends React.HTMLAttributes<HTMLElement> {
	compareToPrevious?: boolean;
	events: BreakdownDataItem[];
	totalValue: number;
}

const PercentOfCell: React.FC<IPercentOfCellProps> = ({
	compareToPrevious = false,
	events = [],
	totalValue
}) => {
	const isComparingEvent = events.length > 1;
	const isComparingSegment = get(events[0], 'breakdownItems', []).length > 1;

	const data = isComparingSegment
		? getItems(events[0].breakdownItems, compareToPrevious)
		: getItems(events, compareToPrevious);

	return (
		<>
			<ul className='percentage-column'>
				{data.map(({value}, i) => (
					<li key={i}>{`${round(
						getPercentage(value, totalValue),
						2
					)}%`}</li>
				))}
			</ul>

			{isComparingSegment && isComparingEvent && (
				<ul className='percentage-column'>
					{getItems(events[1].breakdownItems, compareToPrevious).map(
						({value}, i) => (
							<li key={i}>{`${round(
								getPercentage(value, totalValue),
								2
							)}%`}</li>
						)
					)}
				</ul>
			)}
		</>
	);
};

const getItems = (
	events: BreakdownDataItem[],
	compareToPrevious: boolean
): {
	value: number;
}[] => {
	const data = [];

	events.forEach(({previousValue, value}) => {
		data.push({
			value
		});

		if (compareToPrevious) {
			data.push({
				value: previousValue
			});
		}
	});

	return data;
};

export default PercentOfCell;
