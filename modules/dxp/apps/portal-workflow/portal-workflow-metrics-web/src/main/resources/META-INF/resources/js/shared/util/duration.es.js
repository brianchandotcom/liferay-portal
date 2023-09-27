/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	hoursToMilliseconds,
	intervalToDuration,
	minutesToMilliseconds,
} from 'date-fns';

export function durationAsMilliseconds(days = 0, fullHours) {
	const [hours = 0, minutes = 0] = fullHours.split(':');

	return (
		hoursToMilliseconds(Number(days) * 24 + Number(hours)) +
		minutesToMilliseconds(Number(minutes))
	);
}

export function formatDuration(millisecondsDuration) {
	const duration = getDurationValues(millisecondsDuration);

	const durationParts = [
		{
			label: Liferay.Language.get('days-abbreviation'),
			value: duration.days,
		},
		{
			label: Liferay.Language.get('hours-abbreviation'),
			value: duration.hours,
		},
		{
			label: Liferay.Language.get('minutes-abbreviation'),
			value: duration.minutes,
		},
	].filter((part) => part.value > 0);

	if (!durationParts.length) {
		return `${duration.seconds ? 1 : 0}${Liferay.Language.get(
			'minutes-abbreviation'
		)}`;
	}

	return durationParts.map((part) => `${part.value}${part.label}`).join(' ');
}

export function formatHours(hours, minutes) {
	const padHours = (value) =>
		(value && value.toString().padStart(2, '0')) || '00';

	if (hours || minutes) {
		return [hours, minutes].map(padHours).join(':');
	}

	return '';
}

export function getDurationValues(durationValue) {
	const fullDuration = intervalToDuration({
		end: new Date(durationValue),
		start: new Date(0),
	});

	return {
		days: fullDuration.days || null,
		hours: fullDuration.hours || null,
		minutes: fullDuration.minutes || null,
		seconds: fullDuration.seconds || null,
	};
}

export function remainingTimeFormat(
	onTime,
	remainingTime = 0,
	ignoreZeros = false
) {
	const remainingTimePositive = onTime ? remainingTime : remainingTime * -1;

	const {days, hours, minutes, seconds} = intervalToDuration({
		end: new Date(0, 0, 0, 0, 0, 0, remainingTimePositive),
		start: new Date(0, 0, 0, 0, 0, 0, 0),
	});

	let durationText = '';

	if (ignoreZeros) {
		if (Number(days) > 0) {
			durationText += `${days}d `;
		}

		if (Number(hours) > 0) {
			durationText += `${hours}h `;
		}

		if (Number(minutes) > 0) {
			durationText += `${minutes}min`;
		}

		if (!durationText) {
			durationText += `${seconds}sec`;
		}

		durationText = String(durationText).trimEnd();
	}
	else {
		durationText = `${days}d ${hours}h ${minutes}min`;
	}

	const onTimeText = onTime
		? Liferay.Language.get('left')
		: Liferay.Language.get('overdue');

	return [durationText, onTimeText];
}
