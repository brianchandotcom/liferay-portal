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

import {PagesVisitor} from '../../../src/main/resources/META-INF/resources/js/util/visitors.es';
import mockPages from '../__mock__/mockPages.es';

let visitor;

describe('PagesVisitor', () => {
	beforeEach(() => {
		visitor = new PagesVisitor(mockPages);
	});

	afterEach(() => {
		if (visitor) {
			visitor.dispose();
		}
	});

	it('does not multate the fields of the original array', () => {
		const newPages = visitor.mapFields(field => {
			if (field.fieldName == 'radio') {
				field.fieldName = 'liferay';
			}

			return field;
		});

		expect(mockPages).not.toBe(newPages);
	});

	it('is able to change pages', () => {
		expect(
			visitor.mapPages((page, index) => ({
				...page,
				title: `New title ${index}`
			}))
		).toMatchSnapshot();
	});

	it('is able to change rows', () => {
		expect(
			visitor.mapRows(row => ({
				...row,
				columns: []
			}))
		).toMatchSnapshot();
	});

	it('is able to change columns', () => {
		expect(
			visitor.mapColumns(column => ({
				...column,
				size: 6
			}))
		).toMatchSnapshot();
	});

	it('is able to change fields', () => {
		expect(
			visitor.mapFields((field, index) => ({
				...field,
				label: `New label ${index}`
			}))
		).toMatchSnapshot();
	});
});
