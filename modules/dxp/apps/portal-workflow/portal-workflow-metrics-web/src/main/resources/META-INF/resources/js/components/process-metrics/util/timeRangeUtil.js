import moment from 'shared/util/moment';

export function formatTimeRange(timeRange) {
	const {dateEnd, dateStart} = timeRange;

	if (!dateEnd && !dateStart) {
		return null;
	}

	const dateEndMoment = moment.utc(dateEnd);
	const dateStartMoment = moment.utc(dateStart);

	const formatPattern = getFormatPattern(dateEndMoment, dateStartMoment);

	return `${dateStartMoment.format(formatPattern)} - ${dateEndMoment.format(
		formatPattern
	)}`;
}

function getFormatPattern(dateEndMoment, dateStartMoment) {
	const daysDiff = dateEndMoment.diff(dateStartMoment, 'days');

	if (daysDiff <= 1) {
		return Liferay.Language.get('dd-mmm-hh-a');
	}

	const yearsDiff = dateEndMoment.diff(dateStartMoment, 'years');

	if (yearsDiff < 1) {
		return Liferay.Language.get('dd-mmm');
	}

	return Liferay.Language.get('dd-mmm-yyyy');
}
