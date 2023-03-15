import HistogramChart from '../HistogramChart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('HistogramChart', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<HistogramChart data={[]} />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a fallback bar width', () => {
		const {container} = render(
			<HistogramChart bar={{width: {ratio: 0.6}}} data={[]} />
		);
		expect(container).toBeTruthy();
	});
});
