import Context from './__mock__/mockContext.es';
import createElement from './__mock__/createElement.es';
import LayoutSupport from '../LayoutSupport.es';

let context = null;

describe(
	'LayoutSupport',
	() => {
		beforeEach(
			() => {
				context = JSON.parse(JSON.stringify(Context));
			}
		);

		afterEach(
			() => {
				context = null;
			}
		);

		it(
			'add a new row to the context and reorder',
			() => {
				const indexToAddRow = 0;
				const newRow = LayoutSupport.implAddRow(
					12,
					[
						{
							type: 'newRow'
						}
					]
				);
				const pageIndex = 0;

				expect(
					LayoutSupport.addRow(context, indexToAddRow, pageIndex, newRow)
				).toMatchSnapshot();
			}
		);

		it(
			'should return an implementation of a row for the context',
			() => {
				const row = [
					{
						spritemap: 'icons.svg',
						type: 'text'
					}
				];
				const size = 12;

				expect(
					LayoutSupport.implAddRow(size, row)
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
			'should get a specific field through the context',
			() => {
				const indexColumn = 0;
				const indexPage = 0;
				const indexRow = 0;

				expect(
					LayoutSupport.getField(
						context,
						indexPage,
						indexRow,
						indexColumn
					)
				).toMatchSnapshot();
			}
		);

		it(
			'add a new field to column to the context',
			() => {
				const columnIndex = 1;
				const field = {
					spritemap: 'icons.svg',
					type: 'text'
				};
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					LayoutSupport.addFieldToColumn(
						context,
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
					LayoutSupport.addFieldToColumn(
						context,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toMatchSnapshot();
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
					LayoutSupport.setColumnFields(
						context,
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
					LayoutSupport.setColumnFields(context, pageIndex, rowIndex, columnIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should remove a column from context and reorder',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					LayoutSupport.removeColumn(
						context,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should remove a fields to column from context',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					LayoutSupport.removeFields(
						context,
						pageIndex,
						rowIndex,
						columnIndex
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should remove a row from context and reorder',
			() => {
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					LayoutSupport.removeRow(context, pageIndex, rowIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should get a column from context',
			() => {
				const columnIndex = 1;
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					LayoutSupport.getColumn(context, pageIndex, rowIndex, columnIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should get a row from context',
			() => {
				const pageIndex = 0;
				const rowIndex = 1;

				expect(
					LayoutSupport.getRow(context, pageIndex, rowIndex)
				).toMatchSnapshot();
			}
		);

		it(
			'should return true if there are fields in the context line',
			() => {
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					LayoutSupport.hasFieldsRow(context, pageIndex, rowIndex)
				).toBeTruthy();
			}
		);

		it(
			'should return false if there are fields in the context line',
			() => {
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					LayoutSupport.hasFieldsRow(
						LayoutSupport.removeFields(context, pageIndex, rowIndex, 0),
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
					LayoutSupport.getIndexes(element)
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
					LayoutSupport.getIndexes(element)
				).toEqual(
					{
						columnIndex: false,
						pageIndex: 2,
						rowIndex: 1
					}
				);
			}
		);

		it(
			'should replace column fields',
			() => {
				const columnIndex = 0;
				const newField = [
					{
						label: 'Foo',
						type: 'radio'
					}
				];
				const pageIndex = 0;
				const rowIndex = 0;

				expect(
					LayoutSupport.changeFieldsFromColumn(
						context,
						pageIndex,
						rowIndex,
						columnIndex,
						newField
					)
				).toMatchSnapshot();
			}
		);
	}
);