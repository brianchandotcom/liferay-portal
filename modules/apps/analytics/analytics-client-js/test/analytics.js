/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'fetch-mock';

import AnalyticsClient from '../src/analytics';
import {
	DXP_APPLICATION_IDS,
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

	it('returns channelId from config by default', () => {
		expect(Analytics._getContext().channelId).toBe('4321');
	});

	it('returns channelId from middleware', () => {
		Analytics.registerMiddleware((request) => {
			request.context.channelId = '5678';

			return request;
		});

		expect(Analytics._getContext().channelId).toBe('5678');
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

		Analytics.reset();
		Analytics.dispose();

		Analytics = AnalyticsClient.create(INITIAL_CONFIG);

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

	it('does not replace the user id whenever the set identity hash is the same', async () => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
		fetchMock.mock(/identity$/, () => Promise.resolve(200));

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		const firstUserId = getItem(STORAGE_KEY_USER_ID);

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		const secondUserId = getItem(STORAGE_KEY_USER_ID);

		expect(firstUserId).toEqual(secondUserId);
	});

	it('does not replace the user id whenever the set identity hash is the same and emailAddress is uppercase', async () => {
		fetchMock.mock(/ac-server/i, () => Promise.resolve(200));
		fetchMock.mock(/identity$/, () => Promise.resolve(200));

		await Analytics.setIdentity({
			email: 'JOHN@LIFERAY.COM',
			name: 'John',
		});

		const firstUserId = getItem(STORAGE_KEY_USER_ID);

		await Analytics.setIdentity({
			email: 'john@liferay.com',
			name: 'John',
		});

		const secondUserId = getItem(STORAGE_KEY_USER_ID);

		expect(firstUserId).toEqual(secondUserId);
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

		it('adds the given event to the event queue', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = 'eventId';
			const applicationId = 'applicationId';
			const properties = {a: 1, b: 2, c: 3};

			await Analytics.send(eventId, applicationId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId,
					properties,
				}),
			]);
		});

		it('persists the given events to the LocalStorage', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);
			const eventsNumber = 5;

			await sendDummyEvents(Analytics, eventsNumber);

			const events = Analytics.getEvents();

			expect(events.length).toBeGreaterThanOrEqual(eventsNumber);
		});
	});

	describe('track()', () => {
		afterEach(() => {
			if (console.error.mockRestore) {
				console.error.mockRestore();
			}
		});

		it('is exposed as an Analytics method', () => {
			expect(typeof Analytics.track).toBe('function');
		});

		it('adds the given event to the event queue', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = 'customEventId';
			const properties = {a: 1, b: 2, c: 3};

			await Analytics.track(eventId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'CustomEvent',
					eventId,
					properties,
				}),
			]);
		});

		it('returns a type error if the eventId is not a string', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = {test: 'test'};

			console.error = jest.fn((val) => val);

			await Analytics.track(eventId);

			expect(console.error).toHaveBeenCalledTimes(1);
		});

		it('returns a type error if the attribute type is not valid', () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			console.error = jest.fn((val) => val);

			Analytics.track(
				'foo',
				{bar: [], type: null},
				{applicationId: 'Any'}
			);

			expect(console.error).toHaveBeenCalledTimes(2);
		});

		it('does not returns a type error if the attribute type is not valid and applicationId is from DXP', () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			console.error = jest.fn((val) => val);

			DXP_APPLICATION_IDS.forEach((applicationId) => {
				Analytics.track('foo', {bar: [], type: null}, {applicationId});

				expect(console.error).toHaveBeenCalledTimes(0);
			});
		});

		it('uses the applicationId from options', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = 'test';
			const applicationId = 'Page';
			const properties = {a: 1, b: 2, c: 3};

			await Analytics.track(eventId, properties, {applicationId});

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId,
					eventId,
					properties,
				}),
			]);
		});

		it('uses the assetType from properties over the applicationId from options', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const assetType = 'Blog';
			const eventId = 'test';
			const properties = {a: 1, assetType};

			await Analytics.track(eventId, properties, {applicationId: 'Page'});

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: assetType,
					eventId,
					properties: {a: 1},
				}),
			]);
		});

		it('uses CustomEvent as default applicationId', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = 'customEventId';
			const properties = {a: 1, b: 2, c: 3};

			await Analytics.track(eventId, properties);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'CustomEvent',
					eventId,
					properties,
				}),
			]);
		});

		it('uses applicationId from options', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);

			const eventId = 'BlogView';
			const properties = {a: 1, b: 2, c: 3};
			const options = {applicationId: 'Blog'};

			await Analytics.track(eventId, properties, options);

			const events = Analytics.getEvents();

			expect(events).toEqual([
				expect.objectContaining({
					applicationId: 'Blog',
					eventId,
					properties,
				}),
			]);
		});

		it('persists the given events to the LocalStorage', async () => {
			Analytics = AnalyticsClient.create(INITIAL_CONFIG);
			const eventsNumber = 5;

			await trackDummyEvents(Analytics, eventsNumber);

			const events = Analytics.getEvents();

			expect(events.length).toBeGreaterThanOrEqual(eventsNumber);
		});
	});
});
