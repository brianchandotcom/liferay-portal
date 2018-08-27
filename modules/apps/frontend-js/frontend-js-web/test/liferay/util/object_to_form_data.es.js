'use strict';

import objectToFormData from '../../../src/main/resources/META-INF/resources/liferay/util/object_to_form_data.es';

describe(
	'objectToFormData: should transform a object in FormData element',
	() => {
		it(
			'Object with string values',
			() => {

				const sampleBody = {
					fieldA: 'valueA',
					fieldB: 'valueB'
				};

				const resultFormData = objectToFormData(sampleBody);

				expect(resultFormData.get('fieldA'))
					.toBe(sampleBody.fieldA);
				expect(resultFormData.get('fieldB'))
					.toBe(sampleBody.fieldB);
			}
		);

		it(
			'Object with complex values',
			() => {

				const sampleFile = new File([''], '');

				const sampleBody = {
					obj: {
						prop: 'property value'
					},
					arr: [
						'one',
						'two',
						'three',
					],
					file: sampleFile,
					arrFiles: [
						sampleFile,
						sampleFile,
					]
				};

				const resultFormData = objectToFormData(sampleBody);

				expect(resultFormData.getAll('arr'))
					.toEqual(sampleBody.arr);

				const iteratorFormData = resultFormData
					.entries();

				expect(iteratorFormData.next().value)
					.toEqual(['obj[prop]', sampleBody.obj.prop]);

				expect(iteratorFormData.next().value)
					.toEqual(['arr', sampleBody.arr[0]]);
				expect(iteratorFormData.next().value)
					.toEqual(['arr', sampleBody.arr[1]]);
				expect(iteratorFormData.next().value)
					.toEqual(['arr', sampleBody.arr[2]]);

				expect(iteratorFormData.next().value)
					.toEqual(['file', sampleFile]);

				expect(iteratorFormData.next().value)
					.toEqual(['arrFiles', sampleFile]);
				expect(iteratorFormData.next().value)
					.toEqual(['arrFiles', sampleFile]);
			}
		);
	}
)