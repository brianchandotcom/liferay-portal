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

import * as FormSupport from '../../../src/main/resources/META-INF/resources/js//components/FormRenderer/FormSupport.es';
import createElement from '../__mock__/createElement.es';
import mockPages from '../__mock__/mockPages.es';

let pages = null;

describe('FormSupport', () => {
	beforeEach(() => {
		pages = JSON.parse(JSON.stringify(mockPages));
	});

	afterEach(() => {
		pages = null;
	});

	it('add a new row to the pages and reorder', () => {
		const indexToAddRow = 0;
		const newRow = FormSupport.implAddRow(12, [
			{
				type: 'newRow'
			}
		]);
		const pageIndex = 0;

		expect(
			FormSupport.addRow(pages, indexToAddRow, pageIndex, newRow)
		).toMatchSnapshot();
	});

	it('returns an implementation of a row for the pages', () => {
		const row = [
			{
				spritemap: 'icons.svg',
				type: 'text'
			}
		];
		const size = 12;

		expect(FormSupport.implAddRow(size, row)).toEqual({
			columns: [
				{
					fields: [
						{
							spritemap: 'icons.svg',
							type: 'text'
						}
					],
					size: 12
				}
			]
		});
	});

	it('gets a specific field through the pages', () => {
		const indexColumn = 0;
		const indexPage = 0;
		const indexRow = 0;

		expect(
			FormSupport.getField(pages, indexPage, indexRow, indexColumn)
		).toMatchSnapshot();
	});

	it('add a new field to column to the pages', () => {
		const columnIndex = 1;
		const field = {
			spritemap: 'icons.svg',
			type: 'text'
		};
		const pageIndex = 0;
		const rowIndex = 0;

		expect(
			FormSupport.addFieldToColumn(
				pages,
				pageIndex,
				rowIndex,
				columnIndex,
				field
			)
		).toMatchSnapshot();
	});

	it('adds a new fields to column void', () => {
		const columnIndex = 2;
		const fields = [
			{
				spritemap: 'icons.svg',
				type: 'text'
			}
		];
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.setColumnFields(
				pages,
				pageIndex,
				rowIndex,
				columnIndex,
				fields
			)
		).toMatchSnapshot();
	});

	it('removes a column from pages and reorder', () => {
		const columnIndex = 1;
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.removeColumn(pages, pageIndex, rowIndex, columnIndex)
		).toMatchSnapshot();
	});

	it('removes a fields to column from pages', () => {
		const columnIndex = 1;
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.removeFields(pages, pageIndex, rowIndex, columnIndex)
		).toMatchSnapshot();
	});

	it('removes a row from pages and reorder', () => {
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.removeRow(pages, pageIndex, rowIndex)
		).toMatchSnapshot();
	});

	it('gets a column from pages', () => {
		const columnIndex = 1;
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.getColumn(pages, pageIndex, rowIndex, columnIndex)
		).toMatchSnapshot();
	});

	it('gets a row from pages', () => {
		const pageIndex = 0;
		const rowIndex = 1;

		expect(
			FormSupport.getRow(pages, pageIndex, rowIndex)
		).toMatchSnapshot();
	});

	it('returns true if there are fields in a row', () => {
		const pageIndex = 0;
		const rowIndex = 0;

		expect(
			FormSupport.rowHasFields(pages, pageIndex, rowIndex)
		).toBeTruthy();
	});

	it('returns false if there are fields in a row', () => {
		const pageIndex = 0;
		const rowIndex = 0;

		expect(
			FormSupport.rowHasFields(
				FormSupport.removeFields(pages, pageIndex, rowIndex, 0),
				pageIndex,
				rowIndex
			)
		).toBeFalsy();
	});

	it('extracts the location of the field through the element', () => {
		const element = createElement({
			attributes: [
				{
					key: 'data-ddm-field-column',
					value: 0
				},
				{
					key: 'data-ddm-field-row',
					value: 2
				},
				{
					key: 'data-ddm-field-page',
					value: 2
				}
			],
			tagname: 'div'
		});

		expect(FormSupport.getIndexes(element)).toEqual({
			columnIndex: 0,
			pageIndex: 2,
			rowIndex: 2
		});
	});

	it('extracts the location of the row through the element', () => {
		const element = createElement({
			attributes: [
				{
					key: 'data-ddm-field-column',
					value: 0
				},
				{
					key: 'data-ddm-field-row',
					value: 1
				},
				{
					key: 'data-ddm-field-page',
					value: 2
				}
			],
			tagname: 'div'
		});

		expect(FormSupport.getIndexes(element)).toEqual({
			columnIndex: 0,
			pageIndex: 2,
			rowIndex: 1
		});
	});

	it('updates a field', () => {
		const properties = {
			label: 'Foo',
			type: 'radio'
		};

		expect(
			FormSupport.updateField(pages, 'radioField', properties)
		).toMatchSnapshot();
	});
});
