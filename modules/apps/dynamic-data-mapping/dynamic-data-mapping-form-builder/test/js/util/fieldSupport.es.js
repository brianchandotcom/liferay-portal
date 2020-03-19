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

import {isFieldSetChild} from '../../../src/main/resources/META-INF/resources/js/util/fieldSupport.es';

describe('Field Support Utilities', () => {
	describe('isFieldSetChild', () => {
		it('should return true when a field is a child of a FieldSet', () => {
			const pages = [
				{
					rows: [
						{
							columns: [
								{
									fields: [
										{
											fieldName: 'myFieldSet',
											type: 'section',
											dataDefinitionId: 123,
											nestedFields: [
												{
													fieldName: 'fieldSetChild',
												},
											],
										},
										{
											fieldName: 'notAFieldSet',
										},
										{
											fieldName: 'otherFieldset',
											type: 'section',
											nestedFields: [
												{
													fieldName: 'sectionChild',
												},
											],
										},
									],
								},
							],
						},
					],
				},
			];

			expect(isFieldSetChild(pages, 'fieldSetChild')).toBe(true);
			expect(isFieldSetChild(pages, 'sectionChild')).toBe(false);
			expect(isFieldSetChild(pages, 'notAFieldSet')).toBe(false);
			expect(isFieldSetChild(pages, 'myFieldSet')).toBe(false);
		});
	});
});
