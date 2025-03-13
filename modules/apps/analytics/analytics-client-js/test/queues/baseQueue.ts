/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from '../../src/analytics';
import BaseQueue from '../../src/queues/baseQueue';
import {Analytics as AnalyticsType} from '../../src/types';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const getMockItem = (id: string | number, data: any = {}) => {
	return {
		dataSourceId: 'foo-datasource',
		events: [getDummyEvent()],
		id: `${id}`,
		...data,
	};
};

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

describe('BaseQueue', () => {
	let baseQueue: BaseQueue;

	afterEach(() => {
		baseQueue.reset();
	});

	beforeEach(() => {
		baseQueue = new BaseQueue({
			analyticsInstance,
			name: AnalyticsType.Queues.Messages,
		});
	});

	it('adds an item to its queue', async () => {
		expect(baseQueue.getItems().length).toEqual(0);

		await baseQueue.addItem(
			getMockItem('test-1') as unknown as AnalyticsType.Event
		);

		expect(baseQueue.getItems().length).toEqual(1);
	});

	it('Dequeues an item after add', async () => {
		await baseQueue.addItem(
			getMockItem('test-3') as unknown as AnalyticsType.Event
		);

		expect(baseQueue.getItems().length).toEqual(1);

		baseQueue._dequeue('test-3');

		expect(baseQueue.getItems().length).toEqual(0);
	});

	it('Dequeues first entry when the storage limit is reached', async () => {
		const mockItems = [1, 2, 3, 4, 5].map(getMockItem);

		setItem(AnalyticsType.Queues.Messages, mockItems); // slightly larger than .5kb

		baseQueue.maxSize = 0.5;

		const newMessage = getMockItem(
			'6'
		) as unknown as AnalyticsType.Identity;

		await baseQueue.addItem(newMessage);

		const itemsArray = baseQueue.getItems<AnalyticsType.Identity>();

		expect(itemsArray).toEqual(expect.not.arrayContaining([mockItems[0]]));

		expect(itemsArray[itemsArray.length - 1].id).toEqual(newMessage.id);
	});

	it('Resets the queue', async () => {
		await baseQueue.addItem(
			getMockItem('test') as unknown as AnalyticsType.Event
		);

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
