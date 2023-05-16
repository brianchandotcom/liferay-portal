import React from 'react';
import ValueInput from '../ValueInput';
import {DataTypes} from 'event-analysis/utils/types';
import {fireEvent, render} from '@testing-library/react';
import {
	FunctionalOperators,
	RelationalOperators
} from '../../../../utils/constants';

jest.unmock('react-dom');

describe('ValueInput', () => {
	it('should render', () => {
		const {container, getByText} = render(
			<ValueInput
				dataType={DataTypes.Boolean}
				onChange={jest.fn()}
				operatorName={RelationalOperators.EQ}
				touched={false}
				valid
				value='true'
			/>
		);
		fireEvent.click(getByText('Select an option'));

		expect(getByText('True')).toBeTruthy();
		expect(getByText('False')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it.each`
		dataType             | operatorName                    | value                                       | testId
		${DataTypes.Boolean} | ${RelationalOperators.EQ}       | ${'true'}                                   | ${'attribute-value-boolean-input'}
		${DataTypes.Date}    | ${RelationalOperators.EQ}       | ${'2020-12-12'}                             | ${'date-input'}
		${DataTypes.Date}    | ${FunctionalOperators.Between}  | ${{end: '2020-12-12', start: '2020-12-01'}} | ${'date-range-input'}
		${DataTypes.Number}  | ${RelationalOperators.GT}       | ${1000}                                     | ${'number-input'}
		${DataTypes.Number}  | ${FunctionalOperators.Between}  | ${{end: 1000, start: 1}}                    | ${'between-number-end-input'}
		${DataTypes.String}  | ${FunctionalOperators.Contains} | ${'Stuff'}                                  | ${'attribute-value-string-input'}
	`(
		'should find $testId if dataType is $dataType and operatorName is $operatorName',
		({dataType, operatorName, testId, value}) => {
			const {queryByTestId} = render(
				<ValueInput
					dataType={dataType}
					onChange={jest.fn()}
					operatorName={operatorName}
					touched={false}
					valid
					value={value}
				/>
			);

			expect(queryByTestId(testId)).toBeTruthy();
		}
	);
});
