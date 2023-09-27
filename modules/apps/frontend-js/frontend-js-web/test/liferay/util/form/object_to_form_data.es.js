/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import objectToFormData from '../../../../src/main/resources/META-INF/resources/liferay/util/form/object_to_form_data.es';

describe('Liferay.Util.objectToFormData', () => {
	describe('for plain objects', () => {
		it('converts the object string entries into string FormData entries', () => {
			const body = {
				value1: 'value1',
				value2: 'value2',
			};

			const formData = objectToFormData(body);

			expect(formData.get('value1')).toEqual('value1');
			expect(formData.get('value2')).toEqual('value2');
		});

		it('converts the object boolean entries into string FormData entries', () => {
			const body = {
				value1: true,
				value2: false,
			};

			const formData = objectToFormData(body);

			expect(formData.get('value1')).toEqual('true');
			expect(formData.get('value2')).toEqual('false');
		});

		it('converts the object number entries into string FormData entries', () => {
			const body = {
				value1: 1,
				value2: -1,
			};

			const formData = objectToFormData(body);

			expect(formData.get('value1')).toEqual('1');
			expect(formData.get('value2')).toEqual('-1');
		});

		it('converts the object File entries into File FormData entries', () => {
			const body = {
				value1: new File([''], ''),
			};

			const formData = objectToFormData(body);

			expect(formData.get('value1')).toEqual(body.value1);
		});
	});

	describe('for objects with array values', () => {
		it('generates a grouped field matching the key of the array', () => {
			const body = {
				array: ['value1', 'value2'],
			};

			const formData = objectToFormData(body);

			const arrayField = formData.getAll('array');

			expect(arrayField).toEqual(body.array);
		});
	});

	describe('for objects with object values', () => {
		it('transforms an object with complex values into a FormData element', () => {
			const body = {
				objectValue: {
					arrayValue: ['arrayValue1', 'arrayValue2'],
					objectValue: {
						stringValue: 'objectValue.stringValue',
					},
					stringValue: 'stringValue',
				},
			};

			const formData = objectToFormData(body);

			expect(formData.getAll('objectValue[arrayValue]')).toEqual(
				body.objectValue.arrayValue
			);
			expect(
				formData.get('objectValue[objectValue][stringValue]')
			).toEqual(body.objectValue.objectValue.stringValue);
			expect(formData.get('objectValue[stringValue]')).toEqual(
				body.objectValue.stringValue
			);
		});
	});
});
