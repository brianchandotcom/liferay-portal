/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import deepClone from '../../../src/main/resources/META-INF/resources/liferay/util/deepClone';

describe('deepClone', () => {
	describe('primitives', () => {
		it('returns null as is', () => {
			const result = deepClone(null);
			expect(result).toBeNull();
		});

		it('returns undefined as is', () => {
			const result = deepClone(undefined);
			expect(result).toBeUndefined();
		});

		it('returns numbers as is', () => {
			expect(deepClone(42)).toBe(42);
			expect(deepClone(0)).toBe(0);
			expect(deepClone(-100)).toBe(-100);
			expect(deepClone(3.14)).toBe(3.14);
		});

		it('returns strings as is', () => {
			expect(deepClone('hello')).toBe('hello');
			expect(deepClone('')).toBe('');
		});

		it('returns booleans as is', () => {
			expect(deepClone(true)).toBe(true);
			expect(deepClone(false)).toBe(false);
		});
	});

	describe('functions', () => {
		it('returns functions by reference', () => {
			const fn = () => 'test';
			const result = deepClone(fn);
			expect(result).toBe(fn);
		});

		it('clones objects with function properties by reference', () => {
			const fn = () => 'test';
			const object = {method: fn, value: 10};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result.method).toBe(fn);
			expect(result.value).toBe(10);
		});
	});

	describe('arrays', () => {
		it('clones simple arrays', () => {
			const array = [1, 2, 3];
			const result = deepClone(array);

			expect(result).not.toBe(array);
			expect(result).toEqual([1, 2, 3]);
		});

		it('clones nested arrays', () => {
			const array = [1, [2, 3], [4, [5, 6]]];
			const result = deepClone(array);

			expect(result).not.toBe(array);
			expect(result).toEqual([1, [2, 3], [4, [5, 6]]]);
			expect(result[1]).not.toBe(array[1]);
			expect(result[2]).not.toBe(array[2]);
		});

		it('clones arrays with mixed types', () => {
			const fn = () => 'test';
			const array = [1, 'two', true, null, fn, {key: 'value'}];
			const result = deepClone(array);

			expect(result).not.toBe(array);
			expect(result[0]).toBe(1);
			expect(result[1]).toBe('two');
			expect(result[2]).toBe(true);
			expect(result[3]).toBeNull();
			expect(result[4]).toBe(fn);
			expect(result[5]).toEqual({key: 'value'});
			expect(result[5]).not.toBe(array[5]);
		});
	});

	describe('objects', () => {
		it('clones simple objects', () => {
			const object = {a: 1, b: 2};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result).toEqual({a: 1, b: 2});
		});

		it('clones nested objects', () => {
			const object = {
				level1: {
					level2: {
						level3: 'deep',
					},
				},
			};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result).toEqual(object);
			expect(result.level1).not.toBe(object.level1);
			expect(result.level1.level2).not.toBe(object.level1.level2);
		});

		it('clones objects with various property types', () => {
			const fn = () => 'test';
			const object = {
				array: [1, 2, 3],
				bool: true,
				method: fn,
				nested: {key: 'value'},
				nil: null,
				num: 42,
				str: 'hello',
			};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result.array).toEqual([1, 2, 3]);
			expect(result.array).not.toBe(object.array);
			expect(result.bool).toBe(true);
			expect(result.method).toBe(fn);
			expect(result.nested).toEqual({key: 'value'});
			expect(result.nested).not.toBe(object.nested);
			expect(result.nil).toBeNull();
			expect(result.num).toBe(42);
			expect(result.str).toBe('hello');
		});
	});

	describe('Date objects', () => {
		it('clones Date objects', () => {
			const date = new Date('2024-01-01T00:00:00.000Z');
			const result = deepClone(date);

			expect(result).not.toBe(date);
			expect(result).toBeInstanceOf(Date);
			expect(result.getTime()).toBe(date.getTime());
		});

		it('clones objects containing Date properties', () => {
			const date = new Date('2024-01-01T00:00:00.000Z');
			const object = {timestamp: date, value: 10};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result.timestamp).not.toBe(date);
			expect(result.timestamp).toBeInstanceOf(Date);
			expect(result.timestamp.getTime()).toBe(date.getTime());
		});
	});

	describe('RegExp objects', () => {
		it('clones RegExp objects', () => {
			const regex = /test/gi;
			const result = deepClone(regex);

			expect(result).not.toBe(regex);
			expect(result).toBeInstanceOf(RegExp);
			expect(result.source).toBe('test');
			expect(result.flags).toBe('gi');
		});

		it('clones RegExp with different flags', () => {
			const regex = /pattern/my;
			const result = deepClone(regex);

			expect(result).not.toBe(regex);
			expect(result.source).toBe('pattern');
			expect(result.flags).toBe('my');
		});

		it('clones objects containing RegExp properties', () => {
			const object = {pattern: /test/i, value: 10};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result.pattern).not.toBe(object.pattern);
			expect(result.pattern).toBeInstanceOf(RegExp);
			expect(result.pattern.source).toBe('test');
			expect(result.pattern.flags).toBe('i');
		});
	});

	describe('complex nested structures', () => {
		it('clones complex nested structures with all types', () => {
			const fn = (x: number) => x * 2;
			const date = new Date('2024-01-01');
			const regex = /test/gi;

			const complex = {
				array: [1, 2, {nested: 'value'}],
				bool: true,
				date,
				fn,
				nil: null,
				num: 42,
				object: {
					deep: {
						deeper: {
							deepest: 'bottom',
						},
					},
				},
				regex,
				str: 'hello',
				undef: undefined,
			};

			const result = deepClone(complex);

			expect(result).not.toBe(complex);
			expect(result.num).toBe(42);
			expect(result.str).toBe('hello');
			expect(result.bool).toBe(true);
			expect(result.nil).toBeNull();
			expect(result.undef).toBeUndefined();
			expect(result.fn).toBe(fn);
			expect(result.date).not.toBe(date);
			expect(result.date.getTime()).toBe(date.getTime());
			expect(result.regex).not.toBe(regex);
			expect(result.regex.source).toBe('test');
			expect(result.array).not.toBe(complex.array);
			expect(result.array[2]).not.toBe(complex.array[2]);
			expect(result.object.deep.deeper.deepest).toBe('bottom');
			expect(result.object.deep).not.toBe(complex.object.deep);
		});
	});

	describe('edge cases', () => {
		it('handles empty objects', () => {
			const object = {};
			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result).toEqual({});
		});

		it('handles empty arrays', () => {
			const array: any[] = [];
			const result = deepClone(array);

			expect(result).not.toBe(array);
			expect(result).toEqual([]);
		});

		it('only clones own properties', () => {
			const proto = {inherited: 'value'};
			const object = Object.create(proto);
			object.own = 'property';

			const result = deepClone(object);

			expect(result).not.toBe(object);
			expect(result.own).toBe('property');
			expect(Object.prototype.hasOwnProperty.call(result, 'own')).toBe(
				true
			);
			expect(
				Object.prototype.hasOwnProperty.call(result, 'inherited')
			).toBe(false);
		});
	});
});
