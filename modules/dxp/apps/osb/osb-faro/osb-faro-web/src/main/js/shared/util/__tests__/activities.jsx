import * as data from 'test/data';
import {
	buildLegendItems,
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
