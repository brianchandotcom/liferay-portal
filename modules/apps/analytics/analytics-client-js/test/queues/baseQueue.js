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
import BaseQueue from '../../src/queues/baseQueue';
import {STORAGE_KEY_MESSAGES} from '../../src/utils/constants';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const getMockItem = (id = 0, data = {}) => {
	return {
		dataSourceId: 'foo-datasource',
		events: [getDummyEvent()],
		id: `${id}`,
		...data,
	};
};

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

describe('BaseQueue', () => {
	let baseQueue;

	afterEach(() => {
		baseQueue.reset();
	});

	beforeEach(() => {
		baseQueue = new BaseQueue({
			analyticsInstance,
			name: 'baseQueue',
		});
	});

	it('adds an item to its queue', async () => {
		expect(baseQueue.getItems().length).toEqual(0);

		await baseQueue.addItem(getMockItem('test-1'));

		expect(baseQueue.getItems().length).toEqual(1);
	});

	it('Dequeues an item after add', async () => {
		await baseQueue.addItem(getMockItem('test-3'));

		expect(baseQueue.getItems().length).toEqual(1);

		baseQueue._dequeue('test-3');

		expect(baseQueue.getItems().length).toEqual(0);
	});

	it('Dequeues first entry when the storage limit is reached', async () => {
		const mockItems = [1, 2, 3, 4, 5].map(getMockItem);

		setItem(STORAGE_KEY_MESSAGES, mockItems); // slightly larger than .5kb

		baseQueue.maxSize = 0.5;

		const newMessage = getMockItem('6');

		await baseQueue.addItem(newMessage);
		const itemsArray = baseQueue.getItems();

		expect(itemsArray).toEqual(expect.not.arrayContaining([mockItems[0]]));

		expect(itemsArray[itemsArray.length - 1].id).toEqual(newMessage.id);
	});

	it('Resets the queue', async () => {
		await baseQueue.addItem(getMockItem('test'));

		expect(baseQueue.getItems().length).toEqual(1);

		baseQueue.reset();

		expect(baseQueue.getItems().length).toEqual(0);
	});

	it('onFlush', async () => {
		expect(baseQueue.onFlush).toThrowError(
			'onFlush should be implemented on the new class'
		);
	});
});
