/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	normalizeEvent,
	removeDups,
	sortByEventDate,
} from '../../src/utils/events';

describe('Event Utils', () => {
	it('sorts events by eventDate, from oldest most recent events', () => {
		const events = [
			'2019-12-01',
			'2017-03-01',
			'2020-04-20',
			'2020-04-07',
			'2017-07-15',
		].map((dateStr) => {
			const date = new Date(dateStr);

			return {
				eventDate: date.toISOString(),
			};
		});

		const expectedResultOrder = [
			events[1],
			events[4],
			events[0],
			events[3],
			events[2],
		];

		expect(events.sort(sortByEventDate)).toEqual(
			expect.arrayContaining(expectedResultOrder)
		);
	});

	it('returns a normalized event', () => {
		const eventId = 'pageViewed';
		const applicationId = 'Page';
		const contextHash = 12345;
		const properties = {};

		expect(
			normalizeEvent(eventId, applicationId, properties, contextHash)
		).toEqual(
			expect.objectContaining({
				applicationId,
				contextHash,
				eventDate: expect.any(String),
				eventId,
				eventLocalDate: expect.any(String),
				properties,
			})
		);
	});

	it('removes duplicated events and return an empty', () => {
		const results = [
			{
				status: 'fulfilled',
				value: {
					events: [
						{
							contextHash:
								'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
							eventDate: '2021-12-02T22:32:50.887Z',
							eventId: 'pageUnloaded',
						},
						{
							contextHash:
								'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
							eventDate: '2021-12-02T22:32:51.659Z',
							eventId: 'tabFocused',
						},
					],
				},
			},
		];

		const items = [
			{
				contextHash:
					'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
				eventDate: '2021-12-02T22:32:51.659Z',
				eventId: 'tabFocused',
			},
			{
				contextHash:
					'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
				eventDate: '2021-12-02T22:32:50.887Z',
				eventId: 'pageUnloaded',
			},
		];

		const events = removeDups(results, items);

		expect(events).toHaveLength(0);
	});

	it('removes duplicated events and return non dupped events', () => {
		const results = [
			{
				status: 'fulfilled',
				value: {
					events: [
						{
							contextHash:
								'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
							eventDate: '2021-12-02T22:32:50.887Z',
							eventId: 'pageUnloaded',
						},
						{
							contextHash:
								'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
							eventDate: '2021-12-02T22:32:51.421Z',
							eventId: 'tabFocused',
						},
					],
				},
			},
		];

		const items = [
			{
				contextHash:
					'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
				eventDate: '2021-12-02T22:32:51.659Z',
				eventId: 'tabFocused',
			},
			{
				contextHash:
					'e45443beb1d1b7d58236d4df5aa985020ebf7740f5d152d5dbde8419df9b69b6',
				eventDate: '2021-12-02T22:32:50.555Z',
				eventId: 'pageUnloaded',
			},
		];

		const events = removeDups(results, items);

		expect(events).toHaveLength(2);
	});
});
