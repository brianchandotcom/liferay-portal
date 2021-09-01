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

import Analytics from '../../src/analytics';
import BaseSendMessageQueue from '../../src/queues/baseSendMessageQueue';
import {STORAGE_KEY_CONTEXTS} from '../../src/utils/constants';
import {setItem} from '../../src/utils/storage';
import {INITIAL_ANALYTICS_CONFIG, getDummyEvent} from '../helpers';

const analyticsInstance = Analytics.create(INITIAL_ANALYTICS_CONFIG);

const getMockItem = (id = 0, data = {}) => {
	return {
		dataSourceId: 'foo-datasource',
		events: [getDummyEvent()],
		id: `${id}`,
		...data,
	};
};

describe('BaseSendMessageQueue', () => {
	let baseSendMessageQueue;

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
			name: 'BaseSendMessageQueue',
		});
	});

	it('On Flush', async () => {
		expect(baseSendMessageQueue.getItems().length).toEqual(0);

		setItem(STORAGE_KEY_CONTEXTS, [['context', {}]]);

		await baseSendMessageQueue.addItem(getMockItem(1));

		const messages = await Promise.all(baseSendMessageQueue.onFlush());

		expect(messages.length).toEqual(1);
	});
});
