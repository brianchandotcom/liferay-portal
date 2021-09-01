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

import Analytics from '../../src/analytics';
import BaseCreateMessageQueue from '../../src/queues/baseCreateMessageQueue';
import BaseQueue from '../../src/queues/baseQueue';
import {STORAGE_KEY_CONTEXTS} from '../../src/utils/constants';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);
const flushTo = 'TEST_STORAGE_MESSAGE';

describe('BaseCreateMessageQueue', () => {
	let baseCreateMessageQueue;

	afterEach(() => {
		baseCreateMessageQueue.reset();
	});

	beforeEach(() => {
		analyticsInstance[flushTo] = new BaseQueue({
			analyticsInstance,
			name: 'flushToQueue',
		});

		baseCreateMessageQueue = new BaseCreateMessageQueue({
			analyticsInstance,
			flushTo,
			name: 'BaseCreateMessageQueue',
		});
	});

	it('Create Message', async () => {
		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			})
		);

		const message = baseCreateMessageQueue._createMessage({
			context: {channelId: '4321', page: 'Test Page'},
			events: [
				getDummyEvent(1, {
					contextHash: 'context',
				}),
			],
			userId: 'userIdTest',
		});

		expect(message).toHaveProperty('channelId', '4321');
		expect(message).toHaveProperty('context', {
			page: 'Test Page',
		});
		expect(message).toHaveProperty('dataSourceId', '1234');
		expect(message).toHaveProperty('userId', 'userIdTest');
	});

	it('On Flush', async () => {
		expect(baseCreateMessageQueue.getItems().length).toEqual(0);

		setItem(STORAGE_KEY_CONTEXTS, [['context', {}]]);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			})
		);

		const messages = await Promise.all(baseCreateMessageQueue.onFlush());

		expect(messages.length).toEqual(1);
	});

	it('On Flush should return messages grouped by contexts', async () => {
		expect(baseCreateMessageQueue.getItems().length).toEqual(0);

		setItem(STORAGE_KEY_CONTEXTS, [
			['context', {}],
			['context2', {}],
		]);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(1, {
				contextHash: 'context',
			})
		);

		await baseCreateMessageQueue.addItem(
			getDummyEvent(2, {
				contextHash: 'context2',
			})
		);

		const messages = await Promise.all(baseCreateMessageQueue.onFlush());

		expect(messages.length).toEqual(2);
	});
});
