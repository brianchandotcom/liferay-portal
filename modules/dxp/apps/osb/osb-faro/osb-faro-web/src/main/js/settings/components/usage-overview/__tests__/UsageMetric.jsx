import React from 'react';
import UsageMetric from '../UsageMetric';
import {fromJS} from 'immutable';
import {getTimestamp, mockPlan} from 'test/data';
import {Plan} from 'shared/util/records';
import {render} from '@testing-library/react';
import {SubscriptionStatuses} from 'shared/util/constants';

jest.unmock('react-dom');

const DefaultComponent = ({count, data = {}, limit, status}) => (
	<UsageMetric
		currentPlan={
			new Plan(
				fromJS(
					mockPlan({
						data,
						pageViews: {
							count,
							limit,
							status
						}
					})
				)
			)
		}
		metricType='pageViews'
		planType='enterprise'
	/>
);

describe('UsageMetric', () => {
	it('should render', () => {
		const {container} = render(
			<UsageMetric
				currentPlan={new Plan(fromJS(mockPlan()))}
				metricType='pageViews'
				planType='enterprise'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render as a warning usage level', () => {
		const props = {
			count: 6500000,
			limit: 7000000,
			status: SubscriptionStatuses.Approaching
		};

		const {container} = render(<DefaultComponent {...props} />);

		expect(container.querySelector('.bar-warning')).toBeTruthy();
	});

	it('should render as a danger usage level if metric status is 2', () => {
		const props = {
			count: 7500000,
			limit: 7000000,
			status: SubscriptionStatuses.Over
		};

		const {container} = render(<DefaultComponent {...props} />);

		expect(container.querySelector('.bar-danger')).toBeTruthy();
	});

	it('should display last anniversary date when subscription plan is enterprise', () => {
		const props = {
			count: 700,
			limit: 7000,
			status: SubscriptionStatuses.Ok
		};

		const {container} = render(<DefaultComponent {...props} />);

		expect(
			container.querySelector('.usage-since-label').textContent
		).toEqual('10% since July 8, 2018');
	});

	it('should display last anniversary date when subscription plan is enterprise', () => {
		const props = {
			count: 700,
			data: {
				name: 'Liferay Analytics Cloud Basic',
				startDate: getTimestamp(-365)
			},
			limit: 7000,
			status: SubscriptionStatuses.Ok
		};

		const {container} = render(<DefaultComponent {...props} />);

		expect(
			container.querySelector('.usage-since-label').textContent
		).toEqual('10% since July 10, 2017');
	});
});
