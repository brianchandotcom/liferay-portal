/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentData} from './types';

export function getGoogleCalendarEvent(event: ContentData) {
	const formatDate = (date: Date) => {
		return date.toISOString().replace(/[:.-]/g, '');
	};

	const eventDate = new Date(event.date || event.dateCreated);
	const eventEndDate = new Date(eventDate.getTime() + 60 * 60 * 1000);

	const searchParams = new URLSearchParams({
		action: 'TEMPLATE',
		dates: `${formatDate(eventDate)}/${formatDate(eventEndDate)}`,
		details: event.summary,
		location: event.locationName,
		text: event.title,
	});

	return `https://calendar.google.com/calendar/render?${searchParams.toString()}`;
}

export function getReadingTime(text: string): string {
	if (!text) {
		return '1 min read';
	}

	const wordsPerMinute = 200;
	const words = text
		.trim()
		.split(/\s+/)
		.filter((word) => !!word.length).length;
	const minutes = Math.max(1, Math.ceil(words / wordsPerMinute));

	return `${minutes} min read`;
}
