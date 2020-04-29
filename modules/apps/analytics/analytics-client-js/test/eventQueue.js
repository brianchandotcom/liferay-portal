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

import Analytics from '../src/analytics';
import EventQueue from '../src/eventQueue';
import {STORAGE_KEY_EVENTS} from '../src/utils/constants';
import hash from '../src/utils/hash';
import {setItem} from '../src/utils/storage';
import {
	INITIAL_ANALYTICS_CONFIG,
	flushPromises,
	getDummyEvent,
	getDummyEvents,
} from './helpers';

// We don't need any of the timing callbacks to run during these tests.

jest.mock('../src/plugins/timing');

jest.mock('../src/messageQueue', () => {
	return jest.fn().mockImplementation(() => {
		return {
			addItem: () => Promise.resolve(true),
		};
	});
});

const FLUSH_INTERVAL = 100;

const INITIAL_CONFIG = {
	analyticsInstance: Analytics.create(INITIAL_ANALYTICS_CONFIG),
	flushInterval: FLUSH_INTERVAL,
};

jest.useFakeTimers();

describe('EventQueue', () => {
	let eventQueue;

	afterEach(() => {
		eventQueue.reset();
	});

	beforeEach(() => {
		eventQueue = new EventQueue(STORAGE_KEY_EVENTS, INITIAL_CONFIG);
	});

	it('Adds an item to the event queue', () => {
		expect(eventQueue.getEvents().length).toEqual(0);

		eventQueue.addItem({foo: 'bar'});

		expect(eventQueue.getEvents()[0]).toEqual({foo: 'bar'});
	});

	it('Resets the queue', () => {
		eventQueue.addItem({foo: 'bar'});

		expect(eventQueue.getEvents().length).toEqual(1);

		eventQueue.reset();

		expect(eventQueue.getEvents().length).toEqual(0);
	});

	it('Flushes a message of events for every context that has events in the queue', async () => {
		const mockContext = {
			url: '/test',
		};
		const contextHash = hash(mockContext);

		setItem(eventQueue.keys.contexts, [mockContext]);

		for (let i = 0; i < 3; i++) {
			eventQueue.addItem({
				contextHash,
			});
		}

		expect(eventQueue.getEvents().length).toEqual(3);

		jest.advanceTimersByTime(FLUSH_INTERVAL);
		await flushPromises();

		expect(eventQueue.getEvents().length).toEqual(0);

		setItem(eventQueue.keys.contexts, []);
	});

	it('deletes the oldest events from the queue when the storage limit is reached', async () => {
		const mockEvents = getDummyEvents(5);

		setItem(STORAGE_KEY_EVENTS, mockEvents); // slightly larger than .5kb

		eventQueue = new EventQueue(STORAGE_KEY_EVENTS, {
			...INITIAL_CONFIG,
			maxSize: 0.5,
		});

		const newEvent = getDummyEvent(6);

		eventQueue.addItem(newEvent);
		await flushPromises();

		const eventArray = eventQueue.getEvents();

		expect(eventArray).toEqual(expect.not.arrayContaining([mockEvents[0]]));

		expect(eventArray[eventArray.length - 1].eventId).toEqual(
			newEvent.eventId
		);
	});
});
