import Promise from 'metal-promise';
import {formatStringToLowercase} from 'shared/util/util';
import {formatTime, getMillisecondsFromTime} from 'shared/util/time';
import {isArray, isNil, isObject, isString} from 'lodash';
import {sub} from 'shared/util/lang';

/**
 * Wraps value with a Promise. If passed an error message Promise will reject.
 * @param {Promise|Object} value
 * @return {Promise}
 */

export function toPromise(
	value: typeof Promise | Object | string
): typeof Promise {
	if (value instanceof Promise) {
		return (value as typeof Promise).then(val => toPromise(val));
	} else if (value) {
		return Promise.reject(value);
	}

	return Promise.resolve(value);
}

export function validateInputMessage(messageValue: string) {
	return value => {
		let error = '';

		const invalid =
			formatStringToLowercase(value) !==
			formatStringToLowercase(messageValue);

		if (invalid) {
			error = Liferay.Language.get('string-does-not-match');
		}

		return error;
	};
}

export function validateDateRangeRequired({
	end,
	start
}: {
	end: string;
	start: string;
}) {
	let error = '';

	if (!end || !start) {
		error = Liferay.Language.get('required');
	}

	return toPromise(error);
}

export const validateGreaterThanZero = (value: string) => {
	let error = '';

	if (Number(value) <= 0) {
		error = sub(Liferay.Language.get('must-be-greater-than-x'), [
			'0'
		]) as string;
	}

	return toPromise(error);
};

export const validateIsInteger = (value: string) => {
	let error = '';

	if (!Number.isInteger(Number(value))) {
		error = Liferay.Language.get('must-be-an-integer');
	}

	return toPromise(error);
};

export function validateRequired(value: {value: any} | string | Array<string>) {
	let error = '';

	if (
		isNil(value) ||
		(isString(value) && !value.trim()) ||
		(isArray(value) && !value.length) ||
		(!isArray(value) && isObject(value) && !value.value)
	) {
		error = Liferay.Language.get('required');
	}

	return toPromise(error);
}

export function validateMinDuration(minDuration: string) {
	return (value: string) => {
		let error = '';

		const minDurationInMilliseconds: number = getMillisecondsFromTime(
			minDuration.replace(/_/g, '0')
		);

		const valueInMilliseconds: number = getMillisecondsFromTime(
			value.replace(/_/g, '0')
		);

		if (valueInMilliseconds < minDurationInMilliseconds) {
			error = sub(Liferay.Language.get('must-be-greater-than-x'), [
				formatTime(minDurationInMilliseconds - 1000)
			]) as string;
		}

		return toPromise(error);
	};
}

export function validateMinLength(minLength: number) {
	return value => {
		let error = '';

		if (value.length && value.length < minLength) {
			error = Liferay.Language.get(
				'does-not-meet-minimum-length-required'
			);
		}

		return toPromise(error);
	};
}

export function validateMinValue(minValue: number) {
	return value => {
		let error = '';

		if (Number(value) < minValue) {
			error = sub(Liferay.Language.get('must-be-greater-than-x'), [
				minValue - 1
			]) as string;
		}

		return toPromise(error);
	};
}

export function validateMaxLength(maxLength: number) {
	return value => {
		let error = '';

		if (value.length > maxLength) {
			error = Liferay.Language.get('exceeds-maximum-length');
		}

		return toPromise(error);
	};
}

export function validatePattern(
	regex,
	errorMessage: string
): (value: any) => typeof Promise {
	return value => {
		let error = '';

		if (value.length && !regex.test(value)) {
			error = errorMessage;
		}

		return toPromise(error);
	};
}

export const validateProtocol = validatePattern(
	/^(http[s]?:\/\/)/i,
	Liferay.Language.get(
		'your-url-is-missing-the-protocol.-please-include-http-or-https'
	)
);
