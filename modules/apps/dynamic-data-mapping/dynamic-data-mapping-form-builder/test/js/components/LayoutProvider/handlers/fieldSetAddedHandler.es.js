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

import mockPages from '../../../__mock__/mockPages.es';
import handleFieldSetAdded from '../../../src/main/resources/META-INF/resources/js/components/LayoutProvider/handlers/fieldSetAddedHandler.es';

describe('LayoutProvider/handlers/fieldSetAddedHandler', () => {
	describe('handleFieldSetAdded(props, state, event)', () => {
		it('inserts the fieldset page to the current page', () => {
			const event = {
				fieldSetPage: [
					{
						rows: [
							{
								columns: [
									{
										fields: [
											{
												fieldName: 'field1',
												settingsContext: {
													pages: []
												}
											}
										]
									}
								]
							}
						]
					}
				],
				target: {
					pageIndex: 0,
					rowIndex: 1
				}
			};
			const state = {
				pages: mockPages
			};

			const newState = handleFieldSetAdded({}, state, event);

			expect(
				newState.pages[0].rows[1].columns[0].fields[0].fieldName
			).toEqual('field1');
		});
	});
});
