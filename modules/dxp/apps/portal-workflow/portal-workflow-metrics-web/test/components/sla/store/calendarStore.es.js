/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {CalendarStore} from '../../../../src/main/resources/META-INF/resources/js/components/sla/store/calendarStore.es';
import client from '../../../mock/fetch.es';

test('Should fetch calendars', () => {
	const data = {
		items: [
			{
				defaultCalendar: true,
				key: 'working-hours',
				title: 'Working Hours'
			}
		]
	};

	const calendarStore = new CalendarStore(client(data));

	return calendarStore.fetchCalendars().then(() => {
		expect(calendarStore.getState().calendars).toMatchObject(data.items);
	});
});

test('Should get default calendar', () => {
	const calendarStore = new CalendarStore(client());

	const calendars = [
		{
			defaultCalendar: true,
			key: 'full-hours',
			title: 'Full Hours'
		},
		{
			defaultCalendar: false,
			key: 'working-hours',
			title: 'Working Hours'
		}
	];

	calendarStore.setState({calendars});

	expect(calendarStore.defaultCalendar).toMatchObject(calendars[0]);
});

test('Should init with default state', () => {
	const calendarStore = new CalendarStore(client());

	const defaultState = {
		calendars: []
	};

	expect(calendarStore.getState()).toMatchObject(defaultState);
});

test('Should return empty json when there is no default calendar', () => {
	const calendarStore = new CalendarStore(client());

	const calendars = [
		{
			defaultCalendar: false,
			key: 'working-hours',
			title: 'Working Hours'
		}
	];

	calendarStore.setState({calendars});

	expect(calendarStore.defaultCalendar).toEqual({});
});

test('Should set state', () => {
	const calendarStore = new CalendarStore(client());

	const newState = {
		calendars: [
			{
				defaultCalendar: true,
				key: 'working-hours',
				title: 'Working Hours'
			}
		]
	};

	calendarStore.setState(newState);

	expect(calendarStore.getState()).toMatchObject(newState);
});
