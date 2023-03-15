import Description from '../Description';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection Description', () => {
	it('should render component', () => {
		const {container, getByText} = render(<Description value={100} />);

		expect(getByText('100')).toHaveClass(
			'analytics-summary-section-description'
		);
		expect(container).toMatchSnapshot();
	});
});
