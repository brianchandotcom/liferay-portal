import moment from '../../../shared/util/moment';

export function formatTimeRange(timeRange) {
	const {dateEnd, dateStart} = timeRange;

	if (!dateEnd && !dateStart) {
		return null;
	}

	const dateEndMoment = moment.utc(dateEnd);
	const dateStartMoment = moment.utc(dateStart);

	const {dateEndPattern, dateStartPattern} = getFormatPattern(
		dateEndMoment,
		dateStartMoment
	);

	return `${dateStartMoment.format(
		dateStartPattern
	)} - ${dateEndMoment.format(dateEndPattern)}`;
}

function getFormatPattern(dateEndMoment, dateStartMoment) {
	let dateStartPattern = Liferay.Language.get('dd-mmm-yyyy');

	if (dateEndMoment.diff(dateStartMoment, 'days') <= 1) {
		dateStartPattern = Liferay.Language.get('dd-mmm-hh-a');
	} else if (dateEndMoment.diff(dateStartMoment, 'years') < 1) {
		dateStartPattern = Liferay.Language.get('dd-mmm');
	}

	let dateEndPattern = dateStartPattern;
	const yesterday = moment.utc().subtract(1, 'd');

	if (
		dateEndMoment.date() === yesterday.date() &&
		dateStartMoment.date() === yesterday.date()
	) {
		dateEndPattern = Liferay.Language.get('dd-mmm-hh-mm-a');
	} else if (dateEndMoment.diff(dateStartMoment, 'days') > 90) {
		dateEndPattern = Liferay.Language.get('dd-mmm-yyyy');
	}

	return {
		dateEndPattern,
		dateStartPattern
	};
}
