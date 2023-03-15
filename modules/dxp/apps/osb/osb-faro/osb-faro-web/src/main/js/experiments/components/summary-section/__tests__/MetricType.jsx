import MetricType from '../MetricType';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection MetricType', () => {
	it('should render component', () => {
		const {container} = render(<MetricType value='Click rate' />);

		expect(container).toMatchSnapshot();
	});
});
