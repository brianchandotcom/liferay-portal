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

import {assert, expect} from 'chai';
import fetchMock from 'fetch-mock';

import AnalyticsClient from '../src/analytics';

let Analytics;

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

		Analytics.send(eventId, applicationId, properties);
	}
}

describe('Analytics MiddleWare Integration', () => {
	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();
		fetchMock.restore();
	});

	beforeEach(() => {
		fetchMock.mock('*', () => 200);
		Analytics = AnalyticsClient.create();
	});

	describe('.registerMiddleware', () => {
		it('is exposed as an Analytics static method', () => {
			Analytics.registerMiddleware.should.be.a('function');
		});

		it('processes the given middleware', () => {
			const middleware = (req, analytics) => {
				analytics.should.be.equal(Analytics);
				req.should.be.a('object');

				return req;
			};

			const spy = sinon.spy(middleware);

			Analytics.registerMiddleware(spy);

			sendDummyEvents();

			return Analytics.flush()
				.then(() => {
					assert.isTrue(spy.calledOnce);
				})
				.catch(e => {
					console.error('caught', e);
				});
		});
	});

	describe('default middlewares', () => {
		it('includes document metadata by default', done => {
			let body = null;

			fetchMock.restore();
			fetchMock.mock('*', (url, opts) => {
				body = JSON.parse(opts.body);

				return 200;
			});

			sendDummyEvents();

			Analytics.flush()
				.then(() => {
					expect(body.context).to.include.all.keys(
						'canonicalUrl',
						'contentLanguageId',
						'description',
						'keywords',
						'languageId',
						'referrer',
						'title',
						'url',
						'userAgent'
					);

					done();
				})
				.catch(done);
		});
	});
});
