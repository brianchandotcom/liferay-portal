import createElement from './__mock__/createElement.es';
import FormSupport from '../FormSupport.es';
import mockPages from './__mock__/mockPages.es';

let pages = null;

describe(
	'FormSupport',
	() => {
		beforeEach(
			() => {
				pages = JSON.parse(JSON.stringify(mockPages));
			}
		);

		afterEach(
			() => {
				pages = null;
			}
		);

		it(
			'add a new row to the pages and reorder',
			() => {
				const indexToAddRow = 0;
				const newRow = FormSupport.implAddRow(
					12,
					[
						{
							type: 'newRow'
						}
					]
				);
				const pageIndex = 0;

				expect(
					FormSupport.addRow(pages, indexToAddRow, pageIndex, newRow)
				).toMatchSnapshot();
			}
		);

		it(
			'should return an implementation of a row for the pages',
			() => {
				const row = [
					{
						spritemap: 'icons.svg',
						type: 'text'
					}
				];
				const size = 12;

				expect(
					FormSupport.implAddRow(size, row)
				).toEqual(
					{
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
					}
				);
			}
		);

		it(
			'should get a specific field through the pages',
			() => {
				const indexColumn = 0;
				const indexPage = 0;
				const indexRow = 0;

				expect(
					FormSupport.getField(
						pages,
						indexPage,
						indexRow,
						indexColumn
					)
				).toMatchSnapshot();
			}
		);

		it(
			'add a new field to column to the pages',
			() => {
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
			}
		);

		it(
			'should not add an empty object to the column when the field is not passed',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					() => FormSupport.addFieldToColumn(
						pages,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toThrowError();
			}
		);

		it(
			'should add a new fields to column void',
			() => {
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
			}
		);

		it(
			'should not add new fields to the column as a way to remove',
			() => {
				const columnIndex = 2;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					() => FormSupport.setColumnFields(pages, pageIndex, rowIndex, columnIndex)
				).toThrowError();
			}
		);

		it(
			'should remove a column from pages and reorder',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					FormSupport.removeColumn(
						pages,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should remove a fields to column from pages',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					FormSupport.removeFields(
						pages,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should remove a row from pages and reorder',
			() => {
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					FormSupport.removeRow(pages, pageIndex, rowIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should get a column from pages',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					FormSupport.getColumn(pages, pageIndex, rowIndex, columnIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should get a row from pages',
			() => {
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					FormSupport.getRow(pages, pageIndex, rowIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should return true if there are fields in the pages line',
			() => {
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					FormSupport.rowHasFields(pages, pageIndex, rowIndex)
				).toBeTruthy();
			}
		);

		it(
			'should return false if there are fields in the pages line',
			() => {
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					FormSupport.rowHasFields(
						FormSupport.removeFields(pages, pageIndex, rowIndex, 0),
						pageIndex,
						rowIndex
					)
				).toBeFalsy();
			}
		);

		it(
			'should extract the location of the field through the element',
			() => {
				const element = createElement(
					{
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
					}
				);

				expect(
					FormSupport.getIndexes(element)
				).toEqual(
					{
						columnIndex: 0,
						pageIndex: 2,
						rowIndex: 2
					}
				);
			}
		);

		it(
			'should extract the location of the row through the element',
			() => {
				const element = createElement(
					{
						attributes: [
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
					}
				);

				expect(
					FormSupport.getIndexes(element)
				).toEqual(
					{
						columnIndex: 0,
						pageIndex: 2,
						rowIndex: 1
					}
				);
			}
		);

		it(
			'should update a field',
			() => {
				const properties = {
					label: 'Foo',
					type: 'radio'
				};

				expect(
					FormSupport.updateField(
						pages,
						'radioField',
						properties
					)
				).toMatchSnapshot();
			}
		);
	}
);