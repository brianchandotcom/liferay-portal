/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import i18n from '../../common/I18n';

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
const LOWCASE_NUMBERS_REGEX = /^[0-9a-z]+$/;
const FRIENDLY_URL_REGEX = /^\/[^. "]+[0-9a-z]+[^A-Z]$/;

const required = (value) => {
	if (!value) {
		return i18n.translate('this-field-is-required');
	}
};

const maxLength = (value, max) => {
	if (value.length > max) {
		return i18n.sub('this-field-exceeded-x-characters', [max]);
	}
};

const isValidEmail = (value, bannedEmailDomains) => {
	if (value && !EMAIL_REGEX.test(value)) {
		return i18n.translate('please-insert-a-valid-email');
	}

	if (bannedEmailDomains.length) {
		return i18n.translate('email-domain-not-allowed');
	}
};

const isValidEmailDomain = (bannedEmailDomains) => {
	if (bannedEmailDomains.length) {
		return i18n.translate('domain-not-allowed');
	}
};

const isLowercaseAndNumbers = (value) => {
	if (value && !LOWCASE_NUMBERS_REGEX.test(value)) {
		return i18n.translate('lowercase-letters-and-numbers-only');
	}
};

const isValidFriendlyURL = (value) => {
	if (value && value[0] !== '/') {
		return i18n.translate('the-workspace-url-should-start-with-/');
	}

	if (value && value.indexOf(' ') > 0) {
		return i18n.translate('the-workspace-url-must-not-have-spaces');
	}

	if (value && !FRIENDLY_URL_REGEX.test(value)) {
		return i18n.translate('lowercase-letters-numbers-and-dashes-only');
	}
};

const isValidHost = (value) => {
	if (value.indexOf(' ') > 0) {
		return i18n.translate('the-workspace-host-must-not-have-spaces');
	}
};

const isValidIp = (value) => {
	if (!value) {
		return;
	}

	const ipArray = value.split('\n');

	for (let i = 0; i < ipArray.length; i++) {
		if (ipArray[i].indexOf(' ') > 0) {
			return i18n.translate('the-ip-must-not-have-spaces');
		}

		if (
			!/^(?:(?:^|\.)(?:2(?:5[0-5]|[0-4]\d)|1?\d?\d)){4}$/.test(ipArray[i])
		) {
			return i18n.translate('invalid-ip');
		}
	}
};

const isValidMac = (value) => {
	if (!value) {
		return;
	}

	const macArray = value.split('\n');

	for (let i = 0; i < macArray.length; i++) {
		if (macArray[i].indexOf(' ') > 0) {
			return i18n.translate('the-mac-must-not-have-spaces');
		}

		if (!/^([0-9A-F]{2}[.:-]){5}[0-9A-F]{2}$/i.test(macArray[i])) {
			return i18n.translate('invalid-mac');
		}
	}
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

const validateEmailsArray = (emailArray, emailsAvailable) => {
	const seenEmails = new Set();
	const invalidEmails = [];
	const repeatedEmails = [];
	const errorMessages = [];

	for (const email of emailArray) {
		if (!emailsAvailable.find((item) => item.email === email)) {
			invalidEmails.push(email);
		}
		else if (seenEmails.has(email)) {
			repeatedEmails.push(email);
		}
		else {
			seenEmails.add(email);
		}
	}

	if (invalidEmails.length) {
		errorMessages.push(
			`${i18n.translate(
				'please-insert-a-valid-email'
			)} ${invalidEmails.join(', ')}`
		);
	}
	if (repeatedEmails.length) {
		errorMessages.push(
			`${i18n.translate(
				'please-remove-duplicate-emails'
			)} ${repeatedEmails.join(', ')}`
		);
	}

	return errorMessages.join(' | ') || undefined;
};

export {
	isLowercaseAndNumbers,
	isValidEmail,
	isValidFriendlyURL,
	isValidEmailDomain,
	maxLength,
	required,
	validate,
	isValidHost,
	isValidIp,
	isValidMac,
	validateEmailsArray,
};
