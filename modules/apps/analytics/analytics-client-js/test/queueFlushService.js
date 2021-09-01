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

import Analytics from '../src/analytics';
import QueueFlushService from '../src/queueFlushService';
import EventMessageQueue from '../src/queues/eventMessageQueue';
import IdentityMessageQueue from '../src/queues/identityMessageQueue';
import {INITIAL_ANALYTICS_CONFIG, wait} from './helpers';

// We don't need any of the timing callbacks to run during these tests.

jest.mock('../src/plugins/timing');

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

const FLUSH_INTERVAL = 100;

const INITIAL_CONFIG = {
	flushInterval: FLUSH_INTERVAL,
};

const getMockMessageItem = (id = 0, data = {}) => {
	return {
		foo: 'bar',
		id: `${id}`,
		...data,
	};
};

describe('QueueFlushService', () => {
	let queueFlushService;
	let identityQueue;

	afterEach(() => {
		fetchMock.restore();

		queueFlushService.dispose();
		identityQueue.reset();
	});

	beforeEach(() => {
		queueFlushService = new QueueFlushService(INITIAL_CONFIG);
		identityQueue = new IdentityMessageQueue({
			analyticsInstance,
			name: 'IdentityQueueTest',
		});
	});

	it('flush queue items', async () => {
		let fetchCalled = 0;

		fetchMock.mock(/ac-server/i, () => {
			fetchCalled += 1;

			return Promise.resolve(200);
		});

		queueFlushService.addQueue(identityQueue);

		const item = getMockMessageItem('test-1');

		identityQueue.addItem(item);

		await wait(FLUSH_INTERVAL);

		expect(fetchCalled).toBe(1);
	});

	it('flush queue items ordered by queue priority', async () => {
		const sentEvents = [];
		const eventQueue = new EventMessageQueue({
			analyticsInstance,
			name: 'EventQueueTest',
		});
		const priorityItem = getMockMessageItem('priority');

		fetchMock.mock(/ac-server/i, (url, {body}) => {
			sentEvents.push(JSON.parse(body));

			return Promise.resolve(200);
		});

		queueFlushService.addQueue(eventQueue);
		queueFlushService.addQueue(identityQueue, {
			priority: 10,
		});

		eventQueue.addItem(getMockMessageItem('test-1'));
		identityQueue.addItem(priorityItem);

		await wait(FLUSH_INTERVAL);

		expect(sentEvents[0]).toEqual(priorityItem);
	});
});
