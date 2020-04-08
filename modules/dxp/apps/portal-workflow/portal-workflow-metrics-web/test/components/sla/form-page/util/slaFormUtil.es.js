/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys,
} from '../../../../../src/main/resources/META-INF/resources/js/components/sla/form-page/util/slaFormUtil.es';

test('Should test duration', () => {
	const invalidKey = 'a-duration-time-is-required';

	expect(validateDuration('1', '2')).toBe('');
	expect(validateDuration('', '')).toBe(invalidKey);
	expect(validateDuration('', '')).toBe(invalidKey);
	expect(validateDuration(null, '')).toBe(invalidKey);
	expect(validateDuration()).toBe(invalidKey);
});

test('Should test errors', () => {
	expect(hasErrors({})).toBe(false);
	expect(hasErrors({A: 'TESTE'})).toBe(true);
});

test('Should test hours', () => {
	const invalidKey = 'value-must-be-an-hour-below';

	expect(validateHours('12:45')).toBe('');
	expect(validateHours('44:45')).toBe(invalidKey);
});

test('Should test name', () => {
	const invalidKey = 'a-name-is-required';

	expect(validateName('test')).toBe('');
	expect(validateName()).toBe(invalidKey);
	expect(validateName(' ')).toBe(invalidKey);
	expect(validateName('')).toBe(invalidKey);
});

test('Should test nodes', () => {
	const invalidKey = 'at-least-one-parameter-is-required';

	expect(validateNodeKeys([])).toBe(invalidKey);
	expect(validateNodeKeys(null)).toBe(invalidKey);
	expect(validateNodeKeys([1, 2])).toBe('');
});
