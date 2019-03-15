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

export function formatDuration(durationValue) {
	const duration = moment.duration(durationValue);

	const durationParts = [
		{
			label: Liferay.Language.get('days-abbreviation'),
			value: duration.days()
		},
		{
			label: Liferay.Language.get('hours-abbreviation'),
			value: duration.hours()
		},
		{
			label: Liferay.Language.get('minutes-abbreviation'),
			value: duration.minutes()
		}
	];

	return durationParts
		.filter(part => part.value > 0)
		.map(part => `${part.value}${part.label}`)
		.join(' ');
}