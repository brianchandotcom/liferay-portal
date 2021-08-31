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
import {
	STORAGE_KEY_EVENTS,
	STORAGE_KEY_IDENTITY,
	STORAGE_KEY_USER_ID,
} from '../src/utils/constants';
import {getItem} from '../src/utils/storage';
import {sendDummyEvents, trackDummyEvents, wait} from './helpers';

const ANALYTICS_IDENTITY = {email: 'foo@bar.com'};
const ENDPOINT_URL = 'https://ac-server.io';
const FLUSH_INTERVAL = 100;
const INITIAL_CONFIG = {
	channelId: '4321',
	dataSourceId: '1234',
	endpointUrl: ENDPOINT_URL,
	flushInterval: FLUSH_INTERVAL,
};

describe('Analytics', () => {
	let Analytics;

	beforeEach(() => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

		localStorage.removeItem(STORAGE_KEY_EVENTS);
		localStorage.removeItem(STORAGE_KEY_USER_ID);
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

	it('regenerates the stored identity if the identity changed', async () => {
		fetchMock.mock(/identity$/i, () => Promise.resolve(200));

		Analytics.reset();
		Analytics.dispose();

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

		await Analytics.setIdentity(ANALYTICS_IDENTITY);

		const previousIdentityHash = getItem(STORAGE_KEY_IDENTITY);

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		const currentIdentityHash = getItem(STORAGE_KEY_IDENTITY);

		expect(currentIdentityHash).not.toEqual(previousIdentityHash);
	});

	it('reports identity changes to the Identity Service', async () => {
		fetchMock.mock('*', () => Promise.resolve(200));

		Analytics.reset();
		Analytics.dispose();

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

		let identityCalled = 0;

		await Analytics.setIdentity(ANALYTICS_IDENTITY);

		await wait(FLUSH_INTERVAL);

		fetchMock.restore();
		fetchMock.mock(/identity$/, () => {
			identityCalled += 1;

			return '';
		});

		await Analytics.setIdentity({
			email: 'john@liferay.com',
		});

		await wait(FLUSH_INTERVAL);

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

			return 200;
		});

		await Analytics.setIdentity(ANALYTICS_IDENTITY);

		expect(identityCalled).toBe(0);
	});

	it('preserves the user id whenever the set identity is called after a anonymous navigation', async () => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
		fetchMock.mock(/identity$/, () => Promise.resolve(200));

		sendDummyEvents(Analytics, 1);

		setTimeout(async () => {

			// Flush should have happened at least once

			const userId = getItem(STORAGE_KEY_USER_ID);

			await Analytics.setIdentity({
				email: 'john@liferay.com',
				name: 'John',
			});

			expect(getItem(STORAGE_KEY_USER_ID)).toEqual(userId);
		}, FLUSH_INTERVAL * 2);
	});

	it('replace the user id whenever the set identity hash is changed', async () => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
		fetchMock.mock(/identity$/, () => Promise.resolve(200));

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		const firstUserId = getItem(STORAGE_KEY_USER_ID);

		await Analytics.setIdentity({
			email: 'brian@liferay.com',
			name: 'Brian',
		});

		const secondUserId = getItem(STORAGE_KEY_USER_ID);

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

		const userId = getItem(STORAGE_KEY_USER_ID);

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		Analytics.reset();
		Analytics.dispose();

		sendDummyEvents(Analytics, 1);

		await Analytics.flush();

		expect(getItem(STORAGE_KEY_USER_ID)).not.toEqual(userId);
	});

	describe('send()', () => {
		it('is exposed as an Analytics method', () => {
			expect(typeof Analytics.send).toBe('function');
		});

		it('adds the given event to the event queue', () => {
			const eventId = 'eventId';
			const applicationId = 'applicationId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.send(eventId, applicationId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId,
					properties,
				}),
			]);
		});

		it('persists the given events to the LocalStorage', () => {
			const eventsNumber = 5;

			sendDummyEvents(Analytics, eventsNumber);

			const events = Analytics.getEvents();

			expect(events.length).toBeGreaterThanOrEqual(eventsNumber);
		});
	});

	describe('track()', () => {
		it('is exposed as an Analytics method', () => {
			expect(typeof Analytics.track).toBe('function');
		});

		it('adds the given event to the event queue', () => {
			const eventId = 'customEventId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.track(eventId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'CustomEvent',
					eventId,
					properties,
				}),
			]);
		});

		it('uses CustomEvent as default applicationId', () => {
			const eventId = 'customEventId';
			const properties = {a: 1, b: 2, c: 3};

			Analytics.track(eventId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'CustomEvent',
					eventId,
					properties,
				}),
			]);
		});

		it('uses applicationId from options', () => {
			const eventId = 'BlogView';
			const properties = {a: 1, b: 2, c: 3};
			const options = {applicationId: 'Blog'};

			Analytics.track(eventId, properties, options);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'Blog',
					eventId,
					properties,
				}),
			]);
		});

		it('persists the given events to the LocalStorage', () => {
			const eventsNumber = 5;

			trackDummyEvents(Analytics, eventsNumber);

			const events = Analytics.getEvents();

			expect(events.length).toBeGreaterThanOrEqual(eventsNumber);
		});
	});
});
