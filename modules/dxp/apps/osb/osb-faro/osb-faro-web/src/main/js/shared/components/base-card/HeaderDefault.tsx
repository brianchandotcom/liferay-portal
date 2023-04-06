import Card from 'shared/components/Card';
import DropdownRangeKey from 'shared/hoc/DropdownRangeKey';
import IntervalSelector from 'shared/components/IntervalSelector';
import React, {useCallback} from 'react';
import {Interval} from 'shared/types';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {isHourlyRangeKey} from 'shared/util/time';
import {RangeSelectors} from 'shared/types';

export interface BaseCardHeaderDefaultIProps
	extends React.HTMLAttributes<HTMLElement> {
	interval: Interval;
	label: string;
	legacy: boolean;
	onChangeInterval: (val: Interval) => void;
	onRangeSelectorsChange: (val: any) => void;
	rangeSelectors: RangeSelectors;
	showInterval: boolean;
	showRangeKey?: boolean;
}

const BaseCardHeaderDefault: React.FC<BaseCardHeaderDefaultIProps> = ({
	interval,
	label,
	legacy,
	onChangeInterval,
	onRangeSelectorsChange,
	rangeSelectors,
	showInterval,
	showRangeKey = true
}) => {
	const handleRangeSelectorsChange = useCallback(newVal => {
		onRangeSelectorsChange && onRangeSelectorsChange(newVal);

		if (isHourlyRangeKey(newVal.rangeKey)) {
			onChangeInterval(INTERVAL_KEY_MAP.day);
		}
	}, []);

	const handleChangeInterval = useCallback(
		newVal => onChangeInterval && onChangeInterval(newVal),
		[]
	);

	return (
		<Card.Header className='align-items-center d-flex justify-content-between'>
			<Card.Title>{label}</Card.Title>

			<div className='d-flex'>
				{showInterval && (
					<IntervalSelector
						activeInterval={interval}
						className='mr-3'
						disabled={isHourlyRangeKey(rangeSelectors.rangeKey)}
						onChange={handleChangeInterval}
					/>
				)}

				{showRangeKey && (
					<DropdownRangeKey
						legacy={legacy}
						onChange={handleRangeSelectorsChange}
						rangeSelectors={rangeSelectors}
					/>
				)}
			</div>
		</Card.Header>
	);
};

export default BaseCardHeaderDefault;
