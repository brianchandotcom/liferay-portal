/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from '../../src/analytics';
import BaseCreateMessageQueue from '../../src/queues/baseCreateMessageQueue';
import BaseQueue from '../../src/queues/baseQueue';
import {Analytics as AnalyticsType} from '../../src/types';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

describe('BaseCreateMessageQueue', () => {
	let baseCreateMessageQueue: BaseCreateMessageQueue;

	afterEach(() => {
		baseCreateMessageQueue.reset();
	});

	beforeEach(() => {

		// @ts-ignore

		analyticsInstance[AnalyticsType.Queues.Messages] = new BaseQueue({
			analyticsInstance,
			name: 'flushToQueue' as AnalyticsType.Queues,
		});

		baseCreateMessageQueue = new BaseCreateMessageQueue({
			analyticsInstance,
			flushTo: AnalyticsType.Queues.Messages,
			name: 'BaseCreateMessageQueue' as AnalyticsType.Queues,
		});
	});

	it('Create Message', async () => {
		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			}) as unknown as AnalyticsType.Event
		);

		const message = baseCreateMessageQueue._createMessage({
			context: {
				channelId: '4321',
				dataSourceId: '1234',
				page: 'Test Page',
			},
			events: [
				getDummyEvent(1, {
					contextHash: 'context',
				}) as unknown as AnalyticsType.Event,
			],
			userId: 'userIdTest',
		});

		expect(message).toHaveProperty('channelId', '4321');
		expect(message).toHaveProperty('context', {
			dataSourceId: '1234',
			page: 'Test Page',
		});
		expect(message).toHaveProperty('dataSourceId', '1234');
		expect(message).toHaveProperty('userId', 'userIdTest');
	});

	it('On Flush', async () => {
		expect(baseCreateMessageQueue.getItems().length).toEqual(0);

		setItem(AnalyticsType.Keys.Contexts, [['context', {}]]);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			}) as unknown as AnalyticsType.Event
		);

		const messages = await Promise.all(baseCreateMessageQueue.onFlush());

		expect(messages.length).toEqual(1);
	});

	it('On Flush should return messages grouped by contexts', async () => {
		expect(baseCreateMessageQueue.getItems().length).toEqual(0);

		setItem(AnalyticsType.Keys.Contexts, [
			['context', {}],
			['context2', {}],
		]);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			}) as unknown as AnalyticsType.Event
		);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(2, {
				contextHash: 'context2',
			}) as unknown as AnalyticsType.Event
		);

		const messages = await Promise.all(baseCreateMessageQueue.onFlush());

		expect(messages.length).toEqual(2);
	});
});
