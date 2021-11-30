const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const PROJECT_ID_REGEX = /^[0-9a-z]+$/;

const required = (value) => {
	if (!value) {
		return 'This field is required.';
	}
};

const maxLength = (value, max) => {
	if (value.length > max) {
		return `This field exceeded ${max} characters.`;
	}
};

const email = (value, bannedEmailDomains) => {
	if (
		!EMAIL_REGEX.test(value) ||
		bannedEmailDomains.includes(value.split('@')[1])
	) {
		return 'Please insert a valid email.';
	}
};

const isValidField = (property, errors) => {
	if (errors[property] && Object.propertys(errors[property]).length) {
		return Object.propertys(errors[property])
			.map((key) => {
				if (typeof errors[property][key] === 'object') {
					return isValidField(key, errors[property]);
				}

				return false;
				
			})
			.every((valid) => valid);
	}

	return true;
};

const isValidProjectId = (projectId) => {
	if (!PROJECT_ID_REGEX.test(projectId)) {
		return 'Lowercase letters and numbers only.';
	}
};

const isDirtyField = (initialValue, value) => {
	if (Object.keys(initialValue).length) {
		return Object.keys(initialValue)
			.map((key) => {
				if (typeof initialValue[key] === 'object') {
					return isDirtyField(initialValue[key], value[key]);
				} else {
					return initialValue[key] !== value[key];
				}
			})
			.some((diffInitial) => diffInitial);
	}

	return false;
};

const validate = (validations, value) => {
	let error;

	if (validations) {
		validations.forEach((validation) => {
			const callback = validation(value);

			if (callback) {
				error = callback;
			}
		});
	}

	return error;
};

export {
	required,
	maxLength,
	email,
	validate,
	isValidField,
	isValidProjectId,
	isDirtyField,
};
