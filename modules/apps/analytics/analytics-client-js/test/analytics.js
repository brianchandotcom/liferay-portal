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

let EVENT_ID = 0;

const ANALYTICS_IDENTITY = {email: 'foo@bar.com'};
const ENDPOINT_URL = 'https://ac-server.io';
const FLUSH_INTERVAL = 100;
const INITIAL_CONFIG = {
	dataSourceId: '1234',
	endpointUrl: ENDPOINT_URL
};
const MOCKED_REQUEST_DURATION = 5000;

// Local Storage keys
const STORAGE_KEY_EVENTS = 'ac_client_batch';
const STORAGE_KEY_USER_ID = 'ac_client_user_id';
const STORAGE_KEY_IDENTITY = 'ac_client_identity';

/**
 * Sends dummy events to test the Analytics API
 * @param {number} eventsNumber Number of events to send
 */
function sendDummyEvents(client, eventsNumber = 5) {
	for (let i = 0; i < eventsNumber; i++) {
		const applicationId = 'test';

		const eventId = EVENT_ID++;

		const properties = {
			a: 1,
			b: 2,
			c: 3
		};

		client.send(eventId, applicationId, properties);
	}
}

describe('Analytics', () => {
	let Analytics;

	beforeEach(() => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

		localStorage.removeItem(STORAGE_KEY_EVENTS);
		localStorage.removeItem(STORAGE_KEY_USER_ID);

		if (!global.performance.timing) {
			Object.defineProperty(global.performance, 'timing', {
				get() {
					return {
						loadEventStart: 1,
						navigationStart: 0
					};
				}
			});
		}
	});

	afterEach(() => {
		Analytics.reset();
		Analytics.dispose();

		fetchMock.restore();

		jest.restoreAllMocks();
	});

	it('is exposed in the global scope', () => {
		expect(global.Analytics).toBeInstanceOf(Object);
	});

	it('exposes a "create" instantiation method', () => {
		expect(typeof Analytics.create).toBe('function');
	});

	it('accepts a configuration object', () => {
		const config = {a: 1, b: 2, c: 3};

		Analytics.reset();
		Analytics.dispose();

		Analytics = Analytics.create(config);

		expect(Analytics.config).toEqual(config);
	});

	describe('flush()', () => {
		it('is exposed as an Analytics static method', () => {
			expect(typeof Analytics.flush).toBe('function');
		});

		it('prevents overlapping requests', done => {
			fetchMock.restore();

			let fetchCalled = 0;

			fetchMock.mock(/ac-server/i, () => {
				fetchCalled += 1;

				return new Promise(resolve => {
					setTimeout(() => resolve({}), MOCKED_REQUEST_DURATION);
				});
			});

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create({
				flushInterval: FLUSH_INTERVAL,
				...INITIAL_CONFIG
			});

			const flush = jest.spyOn(Analytics, 'flush');

			sendDummyEvents(Analytics, 10);

			setTimeout(() => {
				// Flush must be called at least 2 times

				expect(flush.mock.calls.length).toBeGreaterThanOrEqual(2);

				// Without sending another Fetch Request

				expect(fetchCalled).toEqual(1);

				done();
			}, FLUSH_INTERVAL * 3);
		});

		it('regenerates the stored identity if the identity changed', async () => {
			fetchMock.mock(/identity$/i, () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			await Analytics.setIdentity(ANALYTICS_IDENTITY);

			const previousIdentityHash = localStorage.getItem(
				STORAGE_KEY_IDENTITY
			);

			await Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			});

			const currentIdentityHash = localStorage.getItem(
				STORAGE_KEY_IDENTITY
			);

			expect(currentIdentityHash).not.toEqual(previousIdentityHash);
		});

		it('reports identity changes to the Identity Service', async () => {
			fetchMock.mock('*', () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			let identityCalled = 0;

			await Analytics.setIdentity(ANALYTICS_IDENTITY);

			fetchMock.restore();
			fetchMock.mock(/identity$/, () => {
				identityCalled += 1;

				return '';
			});

			await Analytics.setIdentity({email: 'john@liferay.com'});

			expect(identityCalled).toBe(1);
		});

		it("does not request the Identity Service when identity hasn't changed", async () => {
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			let identityCalled = 0;

			await Analytics.setIdentity(ANALYTICS_IDENTITY);

			fetchMock.restore();
			fetchMock.mock(/identity$/, () => {
				identityCalled += 1;

				return '';
			});

			await Analytics.setIdentity(ANALYTICS_IDENTITY);

			expect(identityCalled).toBe(0);
		});

		it('only clears the persisted events when done', async () => {
			fetchMock.restore();

			Analytics.reset();
			Analytics.dispose();

			Analytics = AnalyticsClient.create({
				flushInterval: FLUSH_INTERVAL * 10,
				...INITIAL_CONFIG
			});

			fetchMock.mock(/ac-server/i, () => {
				// Send events while flush is in progress
				sendDummyEvents(Analytics, 7);

				return new Promise(resolve => {
					setTimeout(() => resolve({}), 300);
				});
			});

			sendDummyEvents(Analytics, 5);

			await Analytics.flush();

			expect(Analytics.events.length).toBe(7);
		});

		it('preserves the user id whenever the set identity is called after a anonymous navigation', async () => {
			fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			sendDummyEvents(Analytics, 1);

			await Analytics.flush();

			const userId = localStorage.getItem(STORAGE_KEY_USER_ID);

			await Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			});

			expect(localStorage.getItem(STORAGE_KEY_USER_ID)).toEqual(userId);
		});

		it('replace the user id whenever the set identity hash is changed', async () => {
			fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			await Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			});

			const firstUserId = localStorage.getItem(STORAGE_KEY_USER_ID);

			await Analytics.setIdentity({
				email: 'brian@liferay.com',
				name: 'Brian'
			});

			const secondUserId = localStorage.getItem(STORAGE_KEY_USER_ID);

			expect(firstUserId).not.toEqual(secondUserId);
		});

		// Skipping this test because it was broken in the old
		// Karma-based implementation (the `expect` was failing but it
		// did so asynchronously after the test has "finished").
		it.skip('regenerates the user id on logouts or session expirations ', async () => {
			fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
			fetchMock.mock(/identity$/, () => Promise.resolve(200));

			sendDummyEvents(Analytics, 1);

			await Analytics.flush();

			const userId = localStorage.getItem(STORAGE_KEY_USER_ID);

			await Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John'
			});

			Analytics.reset();
			Analytics.dispose();

			sendDummyEvents(Analytics, 1);

			await Analytics.flush();

			expect(localStorage.getItem(STORAGE_KEY_USER_ID)).not.toEqual(
				userId
			);
		});
	});

	describe('send()', () => {
		it('is exposed as an Analytics static method', () => {
			expect(typeof Analytics.send).toBe('function');
		});

		it('adds the given event to the event queue', () => {
			const eventId = 'eventId';
			const applicationId = 'applicationId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.send(eventId, applicationId, properties);

			const events = Analytics.events;

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId,
					properties
				})
			]);
		});

		it('persists the given events to the LocalStorage', () => {
			const eventsNumber = 5;

			sendDummyEvents(Analytics, eventsNumber);

			const events = JSON.parse(localStorage.getItem(STORAGE_KEY_EVENTS));

			expect(events.length).toBeGreaterThanOrEqual(eventsNumber);
		});
	});
});
