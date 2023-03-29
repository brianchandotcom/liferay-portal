import DateRangeInput from '../DateRangeInput';
import moment from 'moment';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DateRangeInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DateRangeInput
				value={{end: moment(100000000000), start: moment(0)}}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
