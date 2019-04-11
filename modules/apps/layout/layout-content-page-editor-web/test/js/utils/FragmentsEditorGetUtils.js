import {deepClone} from '../../../src/main/resources/META-INF/resources/js/utils/FragmentsEditorGetUtils.es';

describe(
	'deepClone ',
	() => {
		test('deep clone of nested objects and arrays', () => {
			const objectToClone = {
				deep: [
					{
						key: 'value',
					}
				]
			};

			const newObject = deepClone(objectToClone);

			expect(newObject).toEqual(objectToClone);
			expect(newObject).not.toBe(objectToClone);
			expect(newObject.deep).not.toBe(objectToClone.deep);
			expect(newObject.deep[0]).not.toBe(objectToClone.deep[0]);
		});

		test('deep clone for a string has no effect', () => {
			const objectToClone = "test-string";

			const newObject = deepClone(objectToClone);

			expect(newObject).toEqual(objectToClone);
			expect(newObject).toBe(objectToClone);
		});

		test('deep clone for a number has no effect', () => {
			const objectToClone = 1985;

			const newObject = deepClone(objectToClone);

			expect(newObject).toEqual(objectToClone);
			expect(newObject).toBe(objectToClone);
		});

		test('deep clone for null has no effect', () => {
			const objectToClone = null;

			const newObject = deepClone(objectToClone);

			expect(newObject).toEqual(objectToClone);
			expect(newObject).toBe(objectToClone);
		});

		test('deep clone for undefined has no effect', () => {
			const objectToClone = undefined;

			const newObject = deepClone(objectToClone);

			expect(newObject).toEqual(objectToClone);
			expect(newObject).toBe(objectToClone);
		});
	}
)