import React from 'react';
import TimePeriodInput from '../TimePeriodInput';
import {cleanup, render} from '@testing-library/react';
import {TimeSpans} from '../../../utils/constants';

jest.unmock('react-dom');

describe('TimePeriodInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<TimePeriodInput onChange={jest.fn()} value={TimeSpans.Last7Days} />
		);

		expect(container).toMatchSnapshot();
	});
});
