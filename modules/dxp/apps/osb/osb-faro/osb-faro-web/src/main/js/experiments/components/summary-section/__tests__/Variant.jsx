import React from 'react';
import Variant from '../Variant';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SummarySection Variant', () => {
	it('should render component', () => {
		const {container, getByText} = render(
			<Variant lift='50%' status='up' />
		);

		expect(getByText('50% lift').parentElement).toHaveClass(
			'analytics-summary-section-variant'
		);

		expect(container.querySelector('use')).toHaveAttribute(
			'xlink:href',
			'#caret-top'
		);

		expect(container).toMatchSnapshot();
	});

	it('should render component with status down', () => {
		const {container} = render(<Variant lift='50%' status='down' />);

		expect(container.querySelector('use')).toHaveAttribute(
			'xlink:href',
			'#caret-bottom'
		);
	});
});
