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

import MessageQueue from '../src/messageQueue';
import {STORAGE_KEY_MESSAGES} from '../src/utils/constants';
import {setItem} from '../src/utils/storage';
import {getDummyEvent, wait} from './helpers';

const PROCESS_DELAY = 100;

const PROCESS_FN_TIMEOUT = 50;

const getMockMessageItem = (id = 0, data = {}) => {
	return {
		dataSourceId: 'foo-datasource',
		events: [getDummyEvent()],
		id: `${id}`,
		...data,
	};
};

const getMockMessage = (id = 0, data = {}, itemData = {}) => {
	return {
		attemptNumber: 0,
		item: getMockMessageItem(id, itemData),
		time: Date.now(),
		...data,
	};
};

const getProcessFn = (outcome) => (_, done) =>
	new Promise((resolve) => setTimeout(resolve, PROCESS_FN_TIMEOUT)).then(() =>
		done(outcome)
	);

const INITIAL_CONFIG = {
	maxRetries: 2,
	processDelay: PROCESS_DELAY,
	processFn: getProcessFn(true),
};

describe('MessageQueue', () => {
	let messageQueue;

	afterEach(() => {
		messageQueue.dispose();
		messageQueue.reset();
	});

	beforeEach(() => {
		messageQueue = new MessageQueue(STORAGE_KEY_MESSAGES, INITIAL_CONFIG);
	});

	it('adds an item to its queue', async () => {
		expect(messageQueue.getMessages().length).toEqual(0);

		await messageQueue.addItem(getMockMessageItem('test-1'));

		expect(messageQueue.getMessages().length).toEqual(1);
	});

	it('processes queue items after an item is enqueued', async () => {
		const spy = jest.fn();

		messageQueue._processMessages = spy;

		await messageQueue.addItem(getMockMessageItem('test-2'));

		expect(spy).toHaveBeenCalledTimes(1);
	});

	it('dequeues an item after successfully processing it', async () => {
		await messageQueue.addItem(getMockMessageItem('test-3'));

		expect(messageQueue.getMessages().length).toEqual(1);

		await wait(PROCESS_DELAY * 1.1);

		expect(messageQueue.getMessages().length).toEqual(0);
	});

	it('requeues an item with a scheduled execution delay after failing to process it', async () => {
		messageQueue.processFn = getProcessFn(false);

		await messageQueue.addItem(getMockMessageItem('test-5'));
		expect(messageQueue.getMessages().length).toEqual(1);
		const initialProcessTime = messageQueue.getMessages()[0].time;

		await wait(PROCESS_FN_TIMEOUT * 2.1);

		const requeuedProcessTime = messageQueue.getMessages()[0].time;
		expect(messageQueue.getMessages().length).toEqual(1);
		expect(requeuedProcessTime).toBeGreaterThan(initialProcessTime);
	});

	it('dequeues first entry when the storage limit is reached', async () => {
		const mockItems = [1, 2, 3, 4, 5].map(getMockMessage);

		setItem(STORAGE_KEY_MESSAGES, mockItems); // slightly larger than .5kb

		messageQueue.maxSize = 0.5;

		messageQueue._processMessages = jest.fn(); // we don't want processing to occur

		const newMessage = getMockMessageItem('6');

		await messageQueue.addItem(newMessage);
		const messageArray = messageQueue.getMessages();

		expect(messageArray).toEqual(
			expect.not.arrayContaining([mockItems[0]])
		);

		expect(messageArray[messageArray.length - 1].item.id).toEqual(
			newMessage.id
		);
	});

	it('only processes messages that were in the queue before processing started', async () => {
		const mockItems = [7, 8, 9].map(getMockMessage);
		setItem(STORAGE_KEY_MESSAGES, mockItems); // prefill queue

		messageQueue._processMessages();

		messageQueue.addItem({id: 'foo'}); // add item to queue while processing first entry

		await wait(PROCESS_DELAY);

		expect(messageQueue.getMessages()[0].item.id).toEqual('foo');
	});
});
