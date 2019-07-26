import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys
} from '../../../../src/main/resources/META-INF/resources/js/components/sla/util/slaFormUtil';

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
