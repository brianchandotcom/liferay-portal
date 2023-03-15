import Heading from '../Heading';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection Heading', () => {
	it('should render component', () => {
		const {container, getByText} = render(<Heading value={100} />);

		expect(getByText('100')).toHaveClass(
			'analytics-summary-section-heading'
		);
		expect(container).toMatchSnapshot();
	});
});
