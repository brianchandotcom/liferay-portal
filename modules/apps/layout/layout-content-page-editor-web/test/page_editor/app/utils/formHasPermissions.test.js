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

import {formIsRestricted} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/formIsRestricted';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			formTypes: [
				{
					isRestricted: false,
					label: 'Form Type 1',
					value: '11111',
				},
				{
					isRestricted: true,
					label: 'Form Type 2',
					value: '22222',
				},
			],
		},
	})
);

describe('formIsRestricted', () => {
	it('checks if the item mapped to the form has permissions', () => {
		expect(
			formIsRestricted({
				config: {
					classNameId: '11111',
					classTypeId: '0',
				},
				itemId: 'form-1',
				type: 'form',
			})
		).toBe(false);
		expect(
			formIsRestricted({
				config: {
					classNameId: '22222',
					classTypeId: '0',
				},
				itemId: 'form-1',
				type: 'form',
			})
		).toBe(true);
	});
});
