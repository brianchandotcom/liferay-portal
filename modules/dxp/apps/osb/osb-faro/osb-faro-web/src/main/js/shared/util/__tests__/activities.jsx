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

			const result = buildLegendItems(mockChangeData);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(1);
			expect(result[0].change).toBe(20);
			expect(result[0].id).toBe('activities');
			expect(result[0].secondaryInfo).toContain('30');
			expect(result[0].title).toContain('10');
		});
	});

	describe('formatGroupingTime', () => {
		it('should format grouping time', () => {
			const result = formatGroupingTime(data.getTimestamp());

			expect(typeof result).toBe('string');
			expect(result.length).toBeGreaterThan(0);
		});
	});

	describe('formatEvents', () => {
		it('should decode canonicalUrl into subtitle for DXP events', () => {
			const result = formatEvents([
				{
					applicationId: 'Page',
					assetTitle: 'this is a page title',
					canonicalUrl:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					eventId: 'pageViewed',
					name: 'eventName',
					pageDescription: 'this is a page description',
					pageTitle: 'this is a page title',
					referrer:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					url: 'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88'
				}
			]);

			expect(result).toMatchObject([
				{
					attributes: {
						applicationId: 'Page',
						eventId: 'pageViewed'
					},
					description: 'this is a page title',
					subtitle: 'http://localhost:7400/新しいサイト',
					title: 'eventName'
				}
			]);
		});

		it('should not set subtitle for webhook events regardless of provider', () => {
			const hubSpotResult = formatEvents(
				[
					{
						applicationId: 'HubSpot',
						assetTitle: null,
						canonicalUrl: 'https://hubspot.com',
						eventId: 'emailView',
						name: 'emailView'
					}
				],
				'HubSpot Webhook'
			);

			const marketoResult = formatEvents(
				[
					{
						applicationId: 'Marketo',
						assetTitle: null,
						canonicalUrl: 'https://marketo.com',
						eventId: 'emailView',
						name: 'emailView'
					}
				],
				'Marketo Webhook'
			);

			expect(hubSpotResult[0].subtitle).toBeUndefined();
			expect(marketoResult[0].subtitle).toBeUndefined();
		});

		it('should transform properties array into an object in attributes', () => {
			const result = formatEvents([
				{
					applicationId: 'HubSpot',
					eventId: 'formSubmit',
					name: 'formSubmit',
					properties: [
						{name: 'formId', value: 'abc123'},
						{name: 'pageUrl', value: 'https://hubspot.com/landing'}
					]
				}
			]);

			expect(result[0].attributes.properties).toEqual({
				formId: 'abc123',
				pageUrl: 'https://hubspot.com/landing'
			});
		});

		it('should include eventDate in attributes only when present', () => {
			const withDate = formatEvents([
				{
					applicationId: 'Page',
					eventDate: '2026-05-07T19:57:21.000Z',
					eventId: 'pageViewed',
					name: 'pageViewed'
				}
			]);

			const withoutDate = formatEvents([
				{
					applicationId: 'Page',
					eventId: 'pageViewed',
					name: 'pageViewed'
				}
			]);

			expect(withDate[0].attributes.eventDate).toBe(
				'2026-05-07T19:57:21.000Z'
			);
			expect(withoutDate[0].attributes).not.toHaveProperty('eventDate');
		});
	});

	describe('formatSessions', () => {
		it('should format sessions', () => {
			const result = formatSessions(
				[data.mockSession(2, {}, {assetType: 'foo'})],
				'123',
				'321'
			);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBeGreaterThan(0);

			const header = result[0];
			expect(header.header).toBe(true);
			expect(typeof header.title).toBe('string');
			expect(typeof header.totalEvents).toBe('number');

			const session = result[1];
			expect(session).toHaveProperty('attributes');
			expect(session).toHaveProperty('device');
			expect(session).toHaveProperty('nestedItems');
			expect(Array.isArray(session.nestedItems)).toBe(true);
		});
	});

	describe('getActivityLabel', () => {
		it('should get singular label', () => {
			const result = getActivityLabel(1);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(2);
			expect(result[0]).toContain('Event');
		});

		it('should plural label', () => {
			const result = getActivityLabel(2);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(2);
			expect(result[0]).toContain('Events');
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
