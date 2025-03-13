/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from '../analytics';
import {Analytics as AnalyticsType} from '../types';
import {getClosestAssetElement} from './assets';
import {convertUTCDateToLocalDate} from './date';

const onReady = (fn: Function) => {
	if (
		document.readyState === 'interactive' ||
		document.readyState === 'complete'
	) {
		fn();
	}
	else {
		document.addEventListener('DOMContentLoaded', fn as EventListener);
	}

	return () =>
		document.removeEventListener('DOMContentLoaded', fn as EventListener);
};

/**
 * Creates an event listener for all event types in events array.
 */
const onEvents = (events: (keyof DocumentEventMap)[], fn: Function) => {
	events.forEach((eventName) =>
		document.addEventListener(eventName, fn as EventListener)
	);

	return () => {
		events.forEach((eventName) => {
			document.removeEventListener(eventName, fn as EventListener);
		});
	};
};

const clickEvent = ({
	analytics,
	applicationId,
	eventType,
	getPayload,
	isTrackable,
	type,
}: {
	analytics: Analytics;
	applicationId: AnalyticsType.ApplicationId;
	eventType: AnalyticsType.EventId;
	getPayload: (
		element: AnalyticsType.HTMLElement
	) => AnalyticsType.EventProps;
	isTrackable: (element: AnalyticsType.HTMLElement) => boolean;
	type: AnalyticsType.ElementType | AnalyticsType.ElementType[];
}) => {
	const onClick = (event: MouseEvent) => {
		const target = event.target as AnalyticsType.HTMLElement & {
			control?: boolean;
			href?: string;
			innerText?: string;
			src?: string;
		};
		const element = getClosestAssetElement(
			target,
			type
		) as AnalyticsType.HTMLElement;

		if (!isTrackable(element) || target.control) {
			return;
		}

		const tagName = target.tagName.toLowerCase();

		const payload = element ? getPayload(element) : {};

		Object.assign(payload, {tagName});

		if (tagName === 'a') {
			Object.assign(payload, {
				href: target.href,
				text: target.innerText,
			});
		}
		else if (tagName === 'img') {
			Object.assign(payload, {
				src: target.src,
			});
		}

		analytics.send(eventType, applicationId, payload);
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
};

/**
 * Serializes data and returns the result appending a timestamp
 * to the returned data as well.
 */
export function normalizeEvent(
	eventId: AnalyticsType.EventId,
	applicationId: AnalyticsType.ApplicationId,
	properties: AnalyticsType.EventProps,
	contextHash: string
): AnalyticsType.Event {
	const date = new Date();
	const eventDate = date.toISOString();
	const eventLocalDate = convertUTCDateToLocalDate(date).toISOString();

	return {
		applicationId,
		contextHash,
		eventDate,
		eventId,
		eventLocalDate,
		properties,
	};
}

/**
 * Sort comparator for ISO 8601 eventDates in ascending order.
 */
const sortByEventDate = (a: {eventDate: string}, b: {eventDate: string}) => {
	if (a.eventDate < b.eventDate) {
		return -1;
	}

	if (a.eventDate > b.eventDate) {
		return 1;
	}

	return 0;
};

const removeDups = (
	results: AnalyticsType.FlushResult[],
	items: AnalyticsType.Event[]
) => {
	const events = results.flatMap(({value}) => value.events);

	return items.filter(
		({contextHash, eventDate, eventId}) =>
			!events.some(
				({
					contextHash: resultContextHash,
					eventDate: resultEventDate,
					eventId: resultEventId,
				}) =>
					contextHash === resultContextHash &&
					eventId === resultEventId &&
					eventDate === resultEventDate
			)
	);
};

export {clickEvent, onEvents, onReady, removeDups, sortByEventDate};
