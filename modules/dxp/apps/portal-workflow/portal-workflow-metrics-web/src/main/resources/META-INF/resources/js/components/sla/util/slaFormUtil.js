import { sub } from '../../../shared/util/lang';

const hasErrors = errors => {
	return Object.keys(errors).some(key => errors[key]);
};

const validateDays = days => {
	if (days && Number(days) === 0) {
		return sub(Liferay.Language.get('value-must-be-an-integer-above-x'), [0]);
	}
};

const validateDuration = (days, hours) => {
	if (!days && !hours) {
		return `${Liferay.Language.get(
			'a-duration-time-is-required'
		)} ${Liferay.Language.get('please-enter-at-least-one-of-the-fields')}`;
	}
};

const validateHours = hours => {
	const hoursRegex = /([01][0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?/;

	if (hours && !hours.match(hoursRegex)) {
		return Liferay.Language.get('value-must-be-an-hour-below');
	}
};

const validateName = name => {
	if (!name || !name.trim()) {
		return Liferay.Language.get('a-name-is-required');
	}
};

export {
	hasErrors,
	validateDays,
	validateDuration,
	validateHours,
	validateName
};