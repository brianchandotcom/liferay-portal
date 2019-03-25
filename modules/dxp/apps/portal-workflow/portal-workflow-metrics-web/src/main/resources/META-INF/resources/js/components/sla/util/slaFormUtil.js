import { sub } from '../../../shared/util/lang';

const hasErrors = errors => {
	return Object.keys(errors).some(key => errors[key]);
};

const validateDays = days => {
	if (Number(days) === 0) {
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
	const validHours = hours.match(hoursRegex);

	if (hours && !validHours) {
		return Liferay.Language.get('hours-must-be-between');
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