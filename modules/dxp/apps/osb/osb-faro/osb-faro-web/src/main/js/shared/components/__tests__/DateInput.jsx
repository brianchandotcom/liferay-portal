import DateInput from '../DateInput';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DateInput', () => {
	it('should render', () => {
		const {container} = render(<DateInput />);
		expect(container).toMatchSnapshot();
	});

	it('should use the displayFormat prop for displaying the date', () => {
		const {getByDisplayValue} = render(
			<DateInput
				displayFormat='YYYY MM DD HH:mm'
				onDateInputChange={jest.fn()}
				value='1970-01-01'
			/>
		);

		expect(getByDisplayValue('1970 01 01 00:00')).toBeTruthy();
	});
});
