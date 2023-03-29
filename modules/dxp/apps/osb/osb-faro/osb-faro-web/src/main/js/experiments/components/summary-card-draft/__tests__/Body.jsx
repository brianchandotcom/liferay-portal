import Body from '../Body';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_DATA = {
	steps: [
		{
			buttonProps: {
				label: 'btn label 1',
				symbol: 'home'
			},
			Description: () => 'Step 1 Description',
			title: 'Step 1 title'
		},
		{
			buttonProps: {
				label: 'btn label 2',
				symbol: 'home'
			},
			Description: () => 'Step 2 Description',
			title: 'Step 2 title'
		}
	],
	subtitle: 'Setup subtitle',
	title: 'Setup title'
};

describe('SummaryCardDraft Body', () => {
	it('should render component', () => {
		const {container} = render(<Body {...MOCK_DATA} current={1} />);

		expect(container).toMatchSnapshot();
	});

	it('Current step should be 0 by default when there is no value', () => {
		const {getByText} = render(<Body {...MOCK_DATA} />);

		expect(
			getByText('Step 1 title').parentElement.parentElement
		).toHaveClass('analytics-summary-card-step-content-active');
	});
});
