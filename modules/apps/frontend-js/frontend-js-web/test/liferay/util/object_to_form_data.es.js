'use strict';

import objectToFormData from '../../../src/main/resources/META-INF/resources/liferay/util/object_to_form_data.es';

describe(
	'Liferay.Util.objectToFormData',
	() => {
		describe(
			'for plain objects',
			() => {
				it(
					'should convert the object string entries into string FormData entries',
					() => {
						const body = {
							value1: 'value1',
							value2: 'value2'
						};

						const formData = objectToFormData(body);

						expect(formData.get('value1')).toEqual('value1');
						expect(formData.get('value2')).toEqual('value2');
					}
				);

				it(
					'should convert the object boolean entries into string FormData entries',
					() => {
						const body = {
							value1: true,
							value2: false
						};

						const formData = objectToFormData(body);

						expect(formData.get('value1')).toEqual('true');
						expect(formData.get('value2')).toEqual('false');
					}
				);

				it(
					'should convert the object number entries into string FormData entries',
					() => {
						const body = {
							value1: 1,
							value2: -1
						};

						const formData = objectToFormData(body);

						expect(formData.get('value1')).toEqual('1');
						expect(formData.get('value2')).toEqual('-1');
					}
				);

				it(
					'should convert the object File entries into File FormData entries',
					() => {
						const body = {
							value1: new File([''], '')
						};

						const formData = objectToFormData(body);

						expect(formData.get('value1')).toEqual(body.value1);
					}
				);
			}
		);

		describe(
			'for objects with array values',
			() => {
				it(
					'should generate a grouped field matching the key of the array',
					() => {
						const body = {
							array: [
								'value1',
								'value2'
							]
						};

						const formData = objectToFormData(body);

						const arrayField = formData.getAll('array');

						expect(arrayField).toEqual(body.array);
					}
				);
			}
		);

		describe(
			'for objects with object values',
			() => {
				it(
					'should transform an object with complex values into a FormData element',
					() => {
						const body = {
							objectValue: {
								arrayValue: [
									'arrayValue1',
									'arrayValue2'
								],
								objectValue: {
									stringValue: 'objectValue.stringValue'
								},
								stringValue: 'stringValue'
							}
						};

						const formData = objectToFormData(body);

						expect(formData.getAll('objectValue[arrayValue]')).toEqual(body.objectValue.arrayValue);
						expect(formData.get('objectValue[objectValue][stringValue]')).toEqual(body.objectValue.objectValue.stringValue);
						expect(formData.get('objectValue[stringValue]')).toEqual(body.objectValue.stringValue);
					}
				);
			}
		);
	}
);