import PlansList from '../PlansList';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const mockPlans = [
	{
		baseSubscriptionPlan: null,
		limits: {
			individuals: 0,
			pageViews: 0
		},
		name: 'Liferay Analytics Cloud Business',
		price: 0
	},
	{
		baseSubscriptionPlan: null,
		limits: {
			individuals: 5,
			pageViews: 30
		},
		name: 'Liferay Limited Business',
		price: 45
	}
];

describe('PlansList', () => {
	it('should render', () => {
		const {container} = render(<PlansList />);

		expect(container).toMatchSnapshot();
	});

	it('should render multiple plans', () => {
		const {container} = render(<PlansList plans={mockPlans} />);

		expect(container.querySelectorAll('.plan-name').length).toBeGreaterThan(
			1
		);
	});

	it('should render with a label in the list of plans for the current plan', () => {
		const {getByText} = render(
			<PlansList
				currentPlanName='Liferay Analytics Cloud Business'
				plans={mockPlans}
			/>
		);

		expect(getByText('Current Plan')).toBeTruthy();
	});
});
