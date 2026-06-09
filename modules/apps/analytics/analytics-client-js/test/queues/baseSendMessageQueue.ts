/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore - Check possibility to install package in ts format

import fetchMock from 'fetch-mock';

import Analytics from '../../src/analytics';
import BaseSendMessageQueue from '../../src/queues/baseSendMessageQueue';
import {Analytics as AnalyticsType} from '../../src/types';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

const getMockItem = (id: number, data = {}) => {
	return {
		dataSourceId: 'foo-datasource',
		events: [getDummyEvent()],
		id: `${id}`,
		...data,
	};
};

describe('BaseSendMessageQueue', () => {
	let baseSendMessageQueue: BaseSendMessageQueue;

	afterEach(() => {
		fetchMock.restore();

		baseSendMessageQueue.reset();
	});

	beforeEach(() => {
		fetchMock.mock(/ac-server/i, () => {
			return Promise.resolve(200);
		});

		baseSendMessageQueue = new BaseSendMessageQueue({
			analyticsInstance,
			flushTo: analyticsInstance.config.endpointUrl,
			name: AnalyticsType.Queues.Messages,
		});
	});

	it('On Flush', async () => {
		expect(baseSendMessageQueue.getItems().length).toEqual(0);

		setItem(AnalyticsType.Keys.Contexts, [['context', {}]]);

		await baseSendMessageQueue.addItem(
			getMockItem(1) as unknown as AnalyticsType.Event
		);

		const messages = await Promise.all(baseSendMessageQueue.onFlush());

		expect(messages.length).toEqual(1);
	});

	it('dequeues the message and does not retry when the response status is 403', async () => {
		fetchMock.restore();
		fetchMock.mock(/ac-server/i, 403);

		await baseSendMessageQueue.addItem(
			getMockItem(1) as unknown as AnalyticsType.Event
		);

		expect(baseSendMessageQueue.getItems().length).toEqual(1);

		await Promise.allSettled(baseSendMessageQueue.onFlush());

		expect(baseSendMessageQueue.getItems().length).toEqual(0);
	});

	it('dequeues the message and does not retry when the response status is 400', async () => {
		fetchMock.restore();
		fetchMock.mock(/ac-server/i, 400);

		await baseSendMessageQueue.addItem(
			getMockItem(1) as unknown as AnalyticsType.Event
		);

		expect(baseSendMessageQueue.getItems().length).toEqual(1);

		await Promise.allSettled(baseSendMessageQueue.onFlush());

		expect(baseSendMessageQueue.getItems().length).toEqual(0);
	});

	it('keeps the message in the queue to retry when the response status is 500', async () => {
		fetchMock.restore();
		fetchMock.mock(/ac-server/i, 500);

		await baseSendMessageQueue.addItem(
			getMockItem(1) as unknown as AnalyticsType.Event
		);

		expect(baseSendMessageQueue.getItems().length).toEqual(1);

		await Promise.allSettled(baseSendMessageQueue.onFlush());

		expect(baseSendMessageQueue.getItems().length).toEqual(1);
	});
});
