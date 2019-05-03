const hasErrors = errors => {
	return Object.keys(errors).some(key => errors[key]);
};

const validateDuration = (days, hours) => {
	if (!days && !hours) {
		return Liferay.Language.get('a-duration-time-is-required');
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

const validateNodeKeys = nodeKeys => {
	if (!nodeKeys || !nodeKeys.length) {
		return Liferay.Language.get('at-least-one-parameter-is-required');
	}
};

export {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys
};