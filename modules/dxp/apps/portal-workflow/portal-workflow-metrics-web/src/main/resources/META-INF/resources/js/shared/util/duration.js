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
	let hoursStr;

	if (hours || minutes) {
		hoursStr = (hours && hours.toString().padStart(2, '0')) || '00';
		hoursStr += ':';
		hoursStr += (minutes && minutes.toString().padStart(2, '0')) || '00';
	}

	return hoursStr || '';
}

export function getDurationValues(durationValue) {
	const fullDuration = moment.duration(durationValue);
	const hoursDuration = moment.duration({
		hours: fullDuration.hours(),
		minutes: fullDuration.minutes()
	});
	const daysDuration = fullDuration.subtract(hoursDuration);

	return {
		days: daysDuration.asDays(),
		hours: hoursDuration.hours() || '',
		minutes: hoursDuration.minutes() || ''
	};
}