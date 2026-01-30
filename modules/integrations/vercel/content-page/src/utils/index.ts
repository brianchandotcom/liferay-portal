/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentData} from './types';

export function getEventCalendar(event: ContentData) {
	const formatDate = (date: Date) => {
		return date.toISOString().replace(/[:.-]/g, '');
	};

	const today = new Date();
	const laterToday = new Date();
	laterToday.setHours(today.getHours() + 1);

	const searchParams = new URLSearchParams({
		action: 'TEMPLATE',
		dates: `${formatDate(today)}/${formatDate(laterToday)}`,
		details: event.summary,
		location: event.locationName,
		text: event.title,
	});

	return `https://calendar.google.com/calendar/render?${searchParams.toString()}`;
}
