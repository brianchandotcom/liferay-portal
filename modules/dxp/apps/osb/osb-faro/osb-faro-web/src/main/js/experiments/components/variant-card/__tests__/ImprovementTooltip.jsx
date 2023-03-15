import ImprovementTooltip from '../ImprovementTooltip';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Unique Visitors Tooltip', () => {
	it('should render', () => {
		const {container} = render(<ImprovementTooltip improvement={10} />);

		expect(container).toMatchSnapshot();
	});

	it('should render negative improvements', () => {
		const {container, getByText} = render(
			<ImprovementTooltip improvement={-10} />
		);

		expect(getByText(/-10/)).toBeTruthy();
		expect(container.querySelector('.analytics-trend')).toHaveStyle(
			'color: rgb(218, 20, 20)'
		);
	});
});
