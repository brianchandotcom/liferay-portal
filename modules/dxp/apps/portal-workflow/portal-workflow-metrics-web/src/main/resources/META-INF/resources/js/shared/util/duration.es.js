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
			minutes
		})
		.asMilliseconds();
}

export function formatDuration(millisecondsDuration) {
	const duration = getDurationValues(millisecondsDuration);

	const durationParts = [
		{
			label: Liferay.Language.get('days-abbreviation'),
			value: duration.days
		},
		{
			label: Liferay.Language.get('hours-abbreviation'),
			value: duration.hours
		},
		{
			label: Liferay.Language.get('minutes-abbreviation'),
			value: duration.minutes
		}
	];

	return durationParts
		.filter(part => part.value > 0)
		.map(part => `${part.value}${part.label}`)
		.join(' ');
}

export function formatHours(hours, minutes) {
	const padHours = value =>
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
		minutes: fullDuration.minutes() || null
	};
}
