import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys
} from '../slaFormUtil';

test('Should test duration', () => {
	const invalidKey = 'A duration time is required.';

	expect(validateDuration('1', '2')).toBe(undefined);
	expect(validateDuration('', '')).toBe(invalidKey);
	expect(validateDuration('', '')).toBe(invalidKey);
	expect(validateDuration(null, '')).toBe(invalidKey);
	expect(validateDuration()).toBe(invalidKey);
});

test('Should test errors', () => {
	expect(hasErrors({})).toBe(false);
	expect(hasErrors({ A: 'TESTE' })).toBe(true);
});

test('Should test hours', () => {
	const invalidKey = 'Value must be an hour below 23:59.';

	expect(validateHours('12:45')).toBe(undefined);
	expect(validateHours('44:45')).toBe(invalidKey);
});

test('Should test name', () => {
	const invalidKey = 'A name is required.';

	expect(validateName('test')).toBe(undefined);
	expect(validateName()).toBe(invalidKey);
	expect(validateName(' ')).toBe(invalidKey);
	expect(validateName('')).toBe(invalidKey);
});

test('Should test nodes', () => {
	const invalidKey = 'At least one parameter is required.';

	expect(validateNodeKeys([])).toBe(invalidKey);
	expect(validateNodeKeys(null)).toBe(invalidKey);
	expect(validateNodeKeys([1, 2])).toBe(undefined);
});