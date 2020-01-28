/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import fetchMock from 'fetch-mock';

import AnalyticsClient from '../src/analytics';

/**
 * Sends dummy events to test the Analytics API
 * @param {number} eventsNumber Number of events to send
 */
function sendDummyEvents(eventsNumber = 5) {
	for (let i = 0; i <= eventsNumber; i++) {
		const applicationId = 'test';

		const eventId = i;

		const properties = {
			a: 1,
			b: 2,
			c: 3
		};

		global.Analytics.send(eventId, applicationId, properties);
	}
}

describe('Analytics MiddleWare Integration', () => {
	let Analytics;

	beforeEach(() => {
		fetchMock.mock('*', () => 200);

		Analytics = AnalyticsClient.create();
	});

	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();

		fetchMock.restore();
	});

	describe('registerMiddleware()', () => {
		it('is exposed as an Analytics static method', () => {
			expect(typeof Analytics.registerMiddleware).toBe('function');
		});

		it('processes the given middleware', async () => {
			const middleware = jest.fn((req, _analytics) => {
				return req;
			});

			Analytics.registerMiddleware(middleware);

			sendDummyEvents();

			await Analytics.flush();

			expect(middleware).toHaveBeenCalledWith(
				expect.objectContaining({context: expect.anything()}),
				Analytics
			);
		});
	});

	describe('default middlewares', () => {
		it('includes document metadata by default', async () => {
			let body = null;

			fetchMock.restore();

			fetchMock.mock('*', (url, opts) => {
				body = JSON.parse(opts.body);

				return 200;
			});

			sendDummyEvents();

			await Analytics.flush();

			expect(body.context).toEqual(
				expect.objectContaining({
					canonicalUrl: expect.anything(),
					contentLanguageId: expect.anything(),
					description: expect.anything(),
					keywords: expect.anything(),
					languageId: expect.anything(),
					referrer: expect.anything(),
					title: expect.anything(),
					url: expect.anything(),
					userAgent: expect.anything()
				})
			);
		});
	});
});
