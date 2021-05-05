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

import moment from 'moment';

export function durationAsMilliseconds(days, fullHours) {
	const [hours, minutes] = fullHours.split(':');

	return moment
		.duration({
			days,
			hours,
			minutes,
		})
		.asMilliseconds();
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
	const fullDuration = moment.duration(durationValue);

	return {
		// eslint-disable-next-line radix
		days: parseInt(fullDuration.asDays()) || null,
		hours: fullDuration.hours() || null,
		minutes: fullDuration.minutes() || null,
		seconds: fullDuration.seconds() || null,
	};
}

export function remainingTimeFormat(
	onTime,
	remainingTime,
	ignoreZeros = false
) {
	const remainingTimePositive = onTime ? remainingTime : remainingTime * -1;

	const remainingTimeUTC = moment.utc(remainingTimePositive);

	const days = remainingTimeUTC.format('D') - 1;

	const hours = remainingTimeUTC.format('HH');

	const minutes = remainingTimeUTC.format('mm');

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
			const seconds = remainingTimeUTC.format('ss');

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
