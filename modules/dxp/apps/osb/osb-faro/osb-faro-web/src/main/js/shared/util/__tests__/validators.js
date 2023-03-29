import Promise from 'metal-promise';
import {
	toPromise,
	validateGreaterThanZero,
	validateInputMessage,
	validateIsInteger,
	validateMaxLength,
	validateMinDuration,
	validateMinLength,
	validateMinValue,
	validatePattern,
	validateProtocol,
	validateRequired
} from '../validators';

describe('toPromise', () => {
	it('should resolve if the result is another Promise', () => {
		expect.assertions(1);

		const result = Promise;

		return expect(
			toPromise(result.resolve(Promise.resolve('')))
		).resolves.toEqual('');
	});

	it('should resolve if the result is an empty string', () => {
		expect.assertions(1);

		return expect(toPromise('')).resolves.toEqual('');
	});

	it('should reject if the result is not valid', () => {
		expect.assertions(1);

		const result = 'errors';

		return expect(toPromise(result)).rejects.toEqual(result);
	});
});

describe('validateInputMessage', () => {
	it('should validate input message as not valid', () => {
		expect.assertions(1);

		const response = validateInputMessage('bbb')('aaa');

		return expect(response).toEqual('String does not match.');
	});

	it('should validate input message as valid', () => {
		expect.assertions(1);

		const response = validateInputMessage('aa')('aa');

		return expect(response).toEqual('');
	});
});

describe('validateMinDuration', () => {
	it('should validate min duration as not valid', () => {
		expect.assertions(1);

		const response = validateMinDuration('00:00:01')('00:00:00');

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate min duration as valid', () => {
		expect.assertions(1);

		const response = validateMinDuration('00:01:00')('00:10:00');

		return expect(response).resolves.toBe('');
	});
});

describe('validateMaxLength', () => {
	it('should validate max length as not valid', () => {
		expect.assertions(1);

		const response = validateMaxLength(2)('aaa');

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate max length as valid', () => {
		expect.assertions(1);

		const response = validateMaxLength(2)('aa');

		return expect(response).resolves.toBe('');
	});
});

describe('validateMinLength', () => {
	it('should validate min length as not valid', () => {
		expect.assertions(1);

		const response = validateMinLength(2)('a');

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate min length as valid', () => {
		expect.assertions(1);

		const response = validateMinLength(2)('aa');

		return expect(response).resolves.toBe('');
	});
});

describe('validateGreaterThanZero', () => {
	it('should validate value as invalid', () => {
		expect.assertions(1);

		const response = validateGreaterThanZero(0);

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate value as valid', () => {
		expect.assertions(1);

		const response = validateGreaterThanZero(0.01);

		return expect(response).resolves.toBe('');
	});
});

describe('validateIsInteger', () => {
	it('should validate value as invalid', () => {
		expect.assertions(1);

		const response = validateIsInteger(1.001);

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate value as valid', () => {
		expect.assertions(1);

		const response = validateIsInteger(123123);

		return expect(response).resolves.toBe('');
	});
});

describe('validateMinValue', () => {
	it('should validate min value as not valid', () => {
		expect.assertions(1);

		const response = validateMinValue(30)(10);

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate min value as valid', () => {
		expect.assertions(1);

		const response = validateMinValue(5)(10);

		return expect(response).resolves.toBe('');
	});
});

describe('validatePattern', () => {
	it('should validate a regex pattern as not valid', () => {
		expect.assertions(1);

		const message = 'can only be a number';

		const response = validatePattern(/^\d+$/, message)('a');

		return expect(response).rejects.toBe(message);
	});

	it('should validate a regex pattern as valid', () => {
		expect.assertions(1);

		const response = validatePattern(/^\d+$/, 'can only be a number')('1');

		return expect(response).resolves.toBe('');
	});
});

describe('validateProtocol', () => {
	it('should validate protocol as not valid', () => {
		expect.assertions(1);

		const response = validateProtocol('liferay.com');

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate protocol as valid', () => {
		expect.assertions(1);

		const response = validateProtocol('https://liferay.com');

		return expect(response).resolves.toBe('');
	});
});

describe('validateRequired', () => {
	it('should validate required as not valid', () => {
		expect.assertions(1);

		const response = validateRequired('');

		return expect(response).rejects.toMatchSnapshot();
	});

	it('should validate required as valid', () => {
		expect.assertions(1);

		const response = validateRequired('test');

		return expect(response).resolves.toMatchSnapshot();
	});

	it('should validate required as valid when validating array with value', () => {
		expect.assertions(1);

		const response = validateRequired(['test']);

		return expect(response).resolves.toMatchSnapshot();
	});

	it('should validate required as not valid if the value is a string with only spaces', () => {
		expect.assertions(1);

		const response = validateRequired('   ');

		return expect(response).rejects.toMatchSnapshot();
	});
});
