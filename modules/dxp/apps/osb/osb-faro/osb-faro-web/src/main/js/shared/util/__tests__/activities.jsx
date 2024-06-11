import * as data from 'test/data';
import {
	buildLegendItems,
	formatEvents,
	formatGroupingTime,
	formatSessions,
	getActivityLabel,
	getSafeRangeKey
} from '../activities';

describe('activities', () => {
	describe('buildLegendItems', () => {
		it('should return an array formatted for use as items in ChangeLegend', () => {
			const mockChangeData = {
				activityChange: 20,
				activityCount: 10
			};

			expect(buildLegendItems(mockChangeData)).toMatchSnapshot();
		});
	});

	describe('formatGroupingTime', () => {
		it('should format grouping time', () => {
			const result = formatGroupingTime(data.getTimestamp());

			expect(result).toMatchSnapshot();
		});
	});

	describe('formatEvents', () => {
		it('should decode canonicalUrl, referrer and url params', () => {
			const result = formatEvents([
				{
					canonicalUrl:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					name: 'eventName',
					pageDescription: 'this is a page description',
					pageTitle: 'this is a page title',
					referrer:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					url:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88'
				}
			]);

			expect(result).toMatchObject([
				{
					attributes: {
						canonicalUrl: 'http://localhost:7400/新しいサイト',
						header: 'Event Attributes',
						referrer: 'http://localhost:7400/新しいサイト',
						title: 'this is a page title',
						url: 'http://localhost:7400/新しいサイト'
					},
					description: 'this is a page title',
					subtitle: 'http://localhost:7400/新しいサイト',
					title: 'eventName'
				}
			]);
		});
	});

	describe('formatSessions', () => {
		it('should format sessions', () => {
			const result = formatSessions(
				[data.mockSession(2, {}, {assetType: 'foo'})],
				'123',
				'321'
			);

			expect(result).toMatchSnapshot();
		});
	});

	describe('getActivityLabel', () => {
		it('should get singular label', () => {
			const result = getActivityLabel(1);

			expect(result).toMatchSnapshot();
		});

		it('should plural label', () => {
			const result = getActivityLabel(2);

			expect(result).toMatchSnapshot();
		});
	});

	describe('getSafeRangeKey', () => {
		it('should return the rangeKey when it is different of CUSTOM', () => {
			const rangeKey = getSafeRangeKey('30');

			expect(rangeKey).toBe('30');
		});

		it('should return null when it is CUSTOM', () => {
			const rangeKey = getSafeRangeKey('CUSTOM');

			expect(rangeKey).toBe(null);
		});
	});
});
