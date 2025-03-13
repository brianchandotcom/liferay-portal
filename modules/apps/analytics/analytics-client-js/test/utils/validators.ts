/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../../src/types';
import {
	isValidEvent,
	validateAttributeType,
	validateEmptyString,
	validateIsString,
	validateMaxLength,
	validatePropsLength,
} from '../../src/utils/validators';

describe('isValidEvent()', () => {
	const originalError = console.error;

	beforeAll(() => {
		console.error = jest.fn();
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		console.error = originalError;
	});

	it('returns if an event is valid', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: Analytics.EventId.BlogViewed,
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(true);
		expect(console.error).not.toBeCalled();
	});

	it('returns false if an event id has more than 255 chars', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: new Array(256).fill('a').join('') as Analytics.EventId,
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event id is empty', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: '' as Analytics.EventId,
			eventProps: {
				someKey: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop key has more than 255 chars', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: Analytics.EventId.BlogViewed,
			eventProps: {
				[new Array(256).fill('a').join('')]: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop key is empty', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: Analytics.EventId.BlogViewed,
			eventProps: {
				[new Array(256).fill('a').join('')]: 'Some Value',
			},
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns false if an event prop value has more than 1024 chars and application id is from DXP', () => {
		const event = {
			applicationId: '123',
			eventId: 'Small Event Name',
			eventProps: {
				someKey: new Array(1025).fill('a').join(''),
			},
		};

		expect(isValidEvent(event as unknown as Analytics.Event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('returns true if an event prop value has more than 1024 chars and application id is from DXP', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: Analytics.EventId.BlogViewed,
			eventProps: {
				someKey: new Array(1025).fill('a').join(''),
			},
		};

		expect(isValidEvent(event)).toBe(true);
		expect(console.error).not.toBeCalled();
	});

	it('returns false if eventProps has more than 25 items', () => {
		const event = {
			applicationId: Analytics.ApplicationId.Blog,
			eventId: Analytics.EventId.BlogViewed,
			eventProps: new Array(26)
				.fill('a')
				.reduce((o, k, i) => ({...o, [`key_${i}`]: `value ${i}`}), {}),
		};

		expect(isValidEvent(event)).toBe(false);
		expect(console.error).toBeCalled();
	});

	it('show all errors in console', () => {
		const event = {
			applicationId: '123',
			eventId: '',
			eventProps: {
				someKey: new Array(1025).fill('a').join(''),
			},
		};

		expect(isValidEvent(event as unknown as Analytics.Event)).toBe(false);
		expect(console.error).toBeCalledTimes(2);
	});
});

describe('validateAttributeType()', () => {
	it('returns nothing when attribute is a string', () => {

		// @ts-ignore

		const errorMsg = validateAttributeType('testLabel');

		expect(errorMsg).toBeFalsy();
	});

	it('returns nothing when attribute is a number', () => {

		// @ts-ignore

		const errorMsg = validateAttributeType(123);

		expect(errorMsg).toBeFalsy();
	});

	it('returns nothing when attribute is a boolean', () => {

		// @ts-ignore

		const errorMsg = validateAttributeType(false);

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when attribute is an object', () => {
		const errorMsg = validateAttributeType({test: 'test'});

		expect(errorMsg).toBeTruthy();
	});

	it('returns an error msg when attribute is an array', () => {

		// @ts-ignore

		const errorMsg = validateAttributeType([1, 2, 3]);

		expect(errorMsg).toBeTruthy();
	});

	it('returns an error msg when attribute is a function', () => {

		// @ts-ignore

		const errorMsg = validateAttributeType(() => {});

		expect(errorMsg).toBeTruthy();
	});
});

describe('validateEmptyString()', () => {
	it('returns an empty string when string is not empty', () => {
		const errorMsg = validateEmptyString('testLabel')('Value');

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when string is empty', () => {
		const errorMsg = validateEmptyString('testLabel')('');

		expect(errorMsg).toBeTruthy();
	});
});

describe('validateIsString()', () => {
	it('returns an empty string when value is a string', () => {
		const errorMsg = validateIsString('testLabel')('Value');

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when value is not a string', () => {
		const errorMsg = validateIsString('testLabel')({test: 'test'});

		expect(errorMsg).toBeTruthy();
	});
});

describe('validateMaxLength()', () => {
	it('returns an empty string when string is not greater than limit', () => {
		const errorMsg = validateMaxLength(5)('Value');

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when string is empty', () => {
		const errorMsg = validateMaxLength(5)('Value1');

		expect(errorMsg).toBeTruthy();
	});
});

describe('validatePropsLength()', () => {
	it('returns an empty string when eventProps size is not greater than limit', () => {
		const errorMsg = validatePropsLength(4)({
			eventId: 'eventId',
			eventProps: {
				key_0: 'value 0',
				key_1: 'value 1',
				key_2: 'value 2',
				key_3: 'value 3',
			},
		});

		expect(errorMsg).toBeFalsy();
	});

	it('returns an empty string when eventProps is undefined', () => {
		const errorMsg = validatePropsLength(4)({
			eventId: 'eventId',
		});

		expect(errorMsg).toBeFalsy();
	});

	it('returns an error msg when eventProps size is greater than limit', () => {
		const errorMsg = validatePropsLength(4)({
			eventId: 'eventId',
			eventProps: {
				key_0: 'value 0',
				key_1: 'value 1',
				key_2: 'value 2',
				key_3: 'value 3',
				key_4: 'value 4',
			},
		});

		expect(errorMsg).toBeTruthy();
	});
});
