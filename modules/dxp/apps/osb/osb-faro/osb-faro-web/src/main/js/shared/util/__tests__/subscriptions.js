import {
	formatPlanData,
	getPlanAddOns,
	getPropIcon,
	getPropLabel,
	INDIVIDUALS,
	PAGEVIEWS
} from '../subscriptions';
import {fromJS} from 'immutable';
import {mockAddOns, mockSubscription} from 'test/data';
import {Plan} from '../../util/records';

describe('subscriptions', () => {
	describe('getPlanAddOns', () => {
		it('should return the correct plan addons', () => {
			const planAddOns = getPlanAddOns('enterprise');

			expect(planAddOns).toEqual(mockAddOns());
		});

		it('should not have addons for LXC customers', () => {
			const planAddOns = getPlanAddOns('lxcSubscriptionEngageSite');

			expect(!!planAddOns.filter(Boolean).length).toBeFalsy();
		});
	});

	describe('getPropIcon', () => {
		it('should return the prop icon symbol', () => {
			const symbol = getPropIcon(INDIVIDUALS);

			expect(symbol).toEqual('ac-individual');
		});
	});

	describe('getPropLabel', () => {
		it('should return the correct prop label', () => {
			const label = getPropLabel(PAGEVIEWS);

			expect(label).toEqual('Page Views');
		});
	});

	describe('formatPlanData', () => {
		it('should format the plan data as a basic Plan record', () => {
			const plan = formatPlanData(
				fromJS(
					mockSubscription({
						individualsCount: 3000,
						name: 'Liferay Analytics Cloud Basic',
						pageViewsCount: 200000
					})
				)
			);

			expect(plan).toBeInstanceOf(Plan);

			const metrics = plan.metrics;

			const individualsMetrics = metrics.get('individuals');

			expect(individualsMetrics.count).toEqual(3000);

			const pageViewsMetrics = metrics.get('pageViews');

			expect(pageViewsMetrics.count).toEqual(200000);
		});

		it('should format the plan data as an enterprise Plan record', () => {
			const plan = formatPlanData(fromJS(mockSubscription()));

			expect(plan).toBeInstanceOf(Plan);

			const metrics = plan.metrics;

			const individualsMetrics = metrics.get('individuals');

			expect(individualsMetrics.count).toEqual(2057);

			const pageViewsMetrics = metrics.get('pageViews');

			expect(pageViewsMetrics.count).toEqual(100023);
		});

		it('should format the plan data when faroSusbcription is null', () => {
			const plan = formatPlanData(null);

			expect(plan).toMatchSnapshot();
		});
	});
});
