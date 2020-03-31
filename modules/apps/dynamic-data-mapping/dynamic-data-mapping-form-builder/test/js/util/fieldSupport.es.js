/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {FIELD_TYPE_FIELDSET} from '../../../src/main/resources/META-INF/resources/js/util/constants.es';
import {isFieldSetChild} from '../../../src/main/resources/META-INF/resources/js/util/fieldSupport.es';

describe('Field Support Utilities', () => {
	describe('isFieldSetChild', () => {
		it('returns true when a field is a child of a FieldSet', () => {
			const pages = [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											dataDefinitionId: 123,
											fieldName: 'myFieldSet',
											nestedFields: [
												{
													fieldName: 'fieldSetChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
										{
											fieldName: 'notAFieldSet',
										},
										{
											fieldName: 'otherFieldset',
											nestedFields: [
												{
													fieldName: 'sectionChild',
												},
											],
											type: FIELD_TYPE_FIELDSET,
										},
									],
								},
							],
						},
					],
				},
			];

			expect(isFieldSetChild(pages, 'fieldSetChild')).toBe(true);
			expect(isFieldSetChild(pages, 'myFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'notAFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'sectionChild')).toBe(false);
		});
	});
});
