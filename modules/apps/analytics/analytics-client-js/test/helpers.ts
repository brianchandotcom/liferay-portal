/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from '../src/analytics';
import {Analytics as AnalyticsType} from '../src/types';

const ENDPOINT_URL = 'https://ac-server.io';

export const INITIAL_ANALYTICS_CONFIG: AnalyticsType.Config = {
	channelId: '4321',
	dataSourceId: '1234',
	endpointUrl: ENDPOINT_URL,
	flushInterval: 0,
	identity: {
		emailAddressHashed: '',
	},
	identityEndpoint: '',
	projectId: '',
	userId: '',
};

/**
 * Flush the current Promise queue.
 */
export function flushPromises() {
	return new Promise((resolve) => setImmediate(resolve));
}

/**
 * Generate a single dummy event.
 */
export function getDummyEvent(eventId = 0, data = {}) {
	return {
		applicationId: 'test' as AnalyticsType.ApplicationId,
		eventId: String(eventId) as AnalyticsType.EventId,
		properties: {
			a: 1,
			b: 2,
			c: 3,
		},
		...data,
	};
}

/**
 * Generate dummy events.
 */
export function getDummyEvents(eventsNumber = 5) {
	const events = [];

	for (let i = 0; i < eventsNumber; i++) {
		events.push(getDummyEvent(i));
	}

	return events;
}

/**
 * Sends dummy events to test the Analytics API
 */
export async function sendDummyEvents(
	analyticsInstance: Analytics,
	eventsNumber?: number
) {
	const events = getDummyEvents(eventsNumber);

	await events.forEach((event) => {
		analyticsInstance.send(
			event.eventId,
			event.applicationId,
			event.properties
		);
	});
}
export async function trackDummyEvents(
	analyticsInstance: Analytics,
	eventsNumber: number
) {
	const events = getDummyEvents(eventsNumber);

	await events.forEach((event) => {
		analyticsInstance.track(event.eventId, event.properties);
	});
}

/**
 * Wait during a test. Cannot use with jest.useFakeTimers()
 */
export function wait(msToWait: number) {
	return new Promise((resolve) => {
		setTimeout(resolve, msToWait);
	});
}
