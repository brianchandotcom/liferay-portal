/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore - Check possibility to install package in ts format

import fetchMock from 'fetch-mock';

import Analytics from '../src/analytics';
import QueueFlushService from '../src/queueFlushService';
import EventMessageQueue from '../src/queues/eventMessageQueue';
import IdentityMessageQueue from '../src/queues/identityMessageQueue';
import {Analytics as AnalyticsTypes} from '../src/types';
import {INITIAL_ANALYTICS_CONFIG, wait} from './helpers';

// We don't need any of the timing callbacks to run during these tests.

jest.mock('../src/plugins/timing');

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

const FLUSH_INTERVAL = 100;

const getMockMessageItem = (id = '0', data = {}) => {
	return {
		channelId: '1234',
		dataSourceId: '4321',
		emailAddressHashed: 'Gh4PyAipwQAsaAyMjD58bdIIxU2rnX',
		id,
		userId: 'TT0MyOwsiRKVbVS93lIy91VPWG36D6',
		...data,
	};
};

describe('QueueFlushService', () => {
	let queueFlushService: QueueFlushService;
	let identityQueue: IdentityMessageQueue;

	afterEach(() => {
		fetchMock.restore();

		queueFlushService.dispose();
		identityQueue.reset();
	});

	beforeEach(() => {
		queueFlushService = new QueueFlushService({
			...INITIAL_ANALYTICS_CONFIG,
			flushInterval: 100,
		});
		identityQueue = new IdentityMessageQueue({
			analyticsInstance,
			name: AnalyticsTypes.Queues.Messages,
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
		const sentEvents: string[] = [];
		const eventQueue = new EventMessageQueue({
			analyticsInstance,
			name: 'EventQueueTest' as AnalyticsTypes.Queues,
		});
		const priorityItem = getMockMessageItem('priority');

		fetchMock.mock(/ac-server/i, (url: string, {body}: {body: string}) => {
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
