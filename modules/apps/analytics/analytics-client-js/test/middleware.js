/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'fetch-mock';

import AnalyticsClient from '../src/analytics';
import {meta} from '../src/middlewares/meta';
import {sendDummyEvents, wait} from './helpers';

const FLUSH_INTERVAL = 100;

describe('Analytics MiddleWare Integration', () => {
	let Analytics;

	beforeEach(() => {
		fetchMock.mock('*', () => 200);

		Analytics = AnalyticsClient.create({flushInterval: FLUSH_INTERVAL});
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

			sendDummyEvents(Analytics);

			await wait(FLUSH_INTERVAL * 2);

			expect(middleware).toHaveBeenCalledWith(
				expect.objectContaining({context: expect.anything()}),
				Analytics
			);
		});
	});

	describe('default middlewares', () => {
		it('includes document metadata by default', () => {
			const req = {context: {}};

			expect(meta(req).context).toEqual(
				expect.objectContaining({
					canonicalUrl: expect.anything(),
					contentLanguageId: expect.anything(),
					description: expect.anything(),
					keywords: expect.anything(),
					languageId: expect.anything(),
					referrer: expect.anything(),
					timezoneOffset: expect.anything(),
					title: expect.anything(),
					url: expect.anything(),
					userAgent: expect.anything(),
				})
			);
		});
	});
});
