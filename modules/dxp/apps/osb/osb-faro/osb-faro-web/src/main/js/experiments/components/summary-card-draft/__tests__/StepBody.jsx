import React from 'react';
import StepBody from '../StepBody';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_STEP = {
	buttonProps: {
		label: 'button label',
		symbol: 'home'
	},
	Description: () => 'StepBody description',
	modal: [
		{
			title: 'modal 01'
		}
	],
	subtitle: 'StepBody subtitle',
	title: 'StepBody title'
};

describe('SummaryCardDraft StepBody', () => {
	it('should render component', () => {
		const {container} = render(<StepBody status='wait' step={MOCK_STEP} />);

		expect(container).toMatchSnapshot();
	});

	it('should render "wait card" when status is wait', () => {
		const {container} = render(<StepBody status='wait' step={MOCK_STEP} />);

		expect(
			container.querySelector('.analytics-summary-card-step-content-wait')
		).toBeTruthy();
	});

	it('should render "active card" when status is active', () => {
		const {container} = render(
			<StepBody status='active' step={MOCK_STEP} />
		);

		expect(
			container.querySelector(
				'.analytics-summary-card-step-content-active'
			)
		).toMatchSnapshot();
	});
});
