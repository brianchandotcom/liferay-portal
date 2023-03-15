import ProgressBar from '../ProgressBar';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection ProgressBar', () => {
	it('should render component', () => {
		const {container} = render(<ProgressBar value={50} />);

		expect(container).toMatchSnapshot();
	});

	it('should render component with completed progress', () => {
		const {container} = render(<ProgressBar value={100} />);

		expect(container.querySelectorAll('.complete')).toBeTruthy();
	});
});
