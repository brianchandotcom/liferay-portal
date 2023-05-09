import DatePickerInput from './DatePickerInput';
import Form from 'shared/components/form';
import React, {useState} from 'react';
import TimePeriodInput from './TimePeriodInput';
import {Criterion} from '../../utils/types';
import {
	EVER,
	FunctionalOperators,
	RelationalOperators,
	SINCE,
	TIME_CONJUNCTION_OPTIONS,
	TIME_PERIOD_OPTIONS,
	TimeSpans
} from '../../utils/constants';
import {Map} from 'immutable';
import {Option, Picker} from '@clayui/core';

const {Between} = FunctionalOperators;
const {EQ, GT, LT} = RelationalOperators;

const TIME_PERIOD_VALUES = TIME_PERIOD_OPTIONS.map(({value}) => value);

export const getInitialConjunction = (
	conjunctionCriterion: Criterion
): FunctionalOperators | RelationalOperators | 'since' | 'ever' => {
	const {operatorName, value} = conjunctionCriterion;

	if (operatorName === GT && TIME_PERIOD_VALUES.includes(value)) {
		return SINCE;
	} else if (!operatorName) {
		return EVER;
	}

	return operatorName as FunctionalOperators | RelationalOperators;
};

interface IDateFilterConjunctionInputProps {
	conjunctionCriterion: Criterion & {
		touched: boolean;
		valid: boolean;
	};
	onChange: (conjunctionCriterion: Criterion) => void;
}

const DateFilterConjunctionInput: React.FC<IDateFilterConjunctionInputProps> = ({
	conjunctionCriterion,
	onChange
}) => {
	const [conjunction, setConjunction] = useState(
		getInitialConjunction(conjunctionCriterion)
	);

	const handleConjunctionChange = value => {
		const {propertyName, value: dateFilter} = conjunctionCriterion;

		switch (value) {
			case SINCE:
				onChange({
					operatorName: GT,
					propertyName,
					touched: false,
					valid: true,
					value: TimeSpans.Last24Hours
				} as Criterion);
				break;
			case Between:
				onChange({
					operatorName: Between,
					propertyName,
					touched: false,
					valid: false,
					value: Map({end: '', start: ''})
				} as Criterion);
				break;
			case EVER:
				onChange(null);
				break;
			default:
				onChange({
					operatorName: value,
					propertyName,
					touched: false,
					valid: ![SINCE, Between, EVER].includes(conjunction),
					value: [SINCE, Between, EVER].includes(conjunction)
						? ''
						: dateFilter
				} as Criterion);
				break;
		}

		setConjunction(value);
	};

	const handleDateFilterBlur = () => {
		onChange({
			...conjunctionCriterion,
			touched: true
		});
	};

	const handleDateFilterChange = dateFilter => {
		const {operatorName, propertyName} = conjunctionCriterion;

		onChange({
			operatorName,
			propertyName,
			touched: true,
			valid:
				operatorName === Between
					? !!dateFilter.end && !!dateFilter.start
					: !!dateFilter,
			value: dateFilter
		});
	};

	const {touched, valid, value} = conjunctionCriterion;

	const showDatePicker = [Between, EQ, GT, LT].includes(
		conjunction as FunctionalOperators | RelationalOperators
	);

	const showTimePeriod = conjunction === SINCE;

	return (
		<>
			<Form.GroupItem shrink>
				<Picker
					className='conjunction-input'
					data-testid='conjunction-input'
					items={TIME_CONJUNCTION_OPTIONS}
					onSelectionChange={handleConjunctionChange}
					selectedKey={conjunction}
				>
					{({label, value}) => <Option key={value}>{label}</Option>}
				</Picker>
			</Form.GroupItem>

			<Form.GroupItem shrink>
				{showTimePeriod && (
					<TimePeriodInput
						onChange={handleDateFilterChange}
						value={value}
					/>
				)}

				{showDatePicker && (
					<DatePickerInput
						isRange={conjunction === Between}
						onBlur={handleDateFilterBlur}
						onChange={handleDateFilterChange}
						touched={touched}
						valid={valid}
						value={value}
					/>
				)}
			</Form.GroupItem>
		</>
	);
};

export default DateFilterConjunctionInput;
