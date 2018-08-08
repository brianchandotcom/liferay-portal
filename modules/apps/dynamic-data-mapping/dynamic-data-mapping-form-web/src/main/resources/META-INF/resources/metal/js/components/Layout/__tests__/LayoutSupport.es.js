import Context from './__mock__/mockContext.es';
import createElement from './__mock__/createElement.es';
import LayoutSupport from '../LayoutSupport.es';

let context = null;

describe('LayoutSupport', () => {
	beforeEach(() => {
		context = JSON.parse(JSON.stringify(Context));
	});

	afterEach(() => {
		context = null;
	});

	it('add a new row to the context and reorder', () => {
		const indexToAddRow = 0;
		const pageIndex = 0;
		const newRow = LayoutSupport.implAddRow(12, [
			{
				type: 'newRow',
			},
		]);

		expect(
			LayoutSupport.addRow(context, indexToAddRow, pageIndex, newRow)
		).toMatchSnapshot();
	});

	it('should return an implementation of a row for the context', () => {
		const size = 12;
		const row = [
			{
				type: 'text',
				spritemap: 'icons.svg',
			},
		];

		expect(LayoutSupport.implAddRow(size, row)).toEqual({
			columns: [
				{
					fields: [
						{
							spritemap: 'icons.svg',
							type: 'text',
						},
					],
					size: 12,
				},
			],
		});
	});

	it('add a new field to column to the context', () => {
		const indexColumn = 1;
		const indexPage = 0;
		const indexRow = 0;
		const field = {
			type: 'text',
			spritemap: 'icons.svg',
		};

		expect(
			LayoutSupport.addFieldToColumn(
				context,
				indexPage,
				indexRow,
				indexColumn,
				field
			)
		).toMatchSnapshot();
	});

	it('should not add an empty object to the column when the field is not passed', () => {
		const indexColumn = 1;
		const indexPage = 0;
		const indexRow = 0;

		expect(
			LayoutSupport.addFieldToColumn(
				context,
				indexPage,
				indexRow,
				indexColumn
			)
		).toMatchSnapshot();
	});

	it('should add a new fields to column void', () => {
		const indexColumn = 2;
		const indexPage = 0;
		const indexRow = 1;
		const fields = [
			{
				type: 'text',
				spritemap: 'icons.svg',
			},
		];

		expect(
			LayoutSupport.addFields(
				context,
				indexPage,
				indexRow,
				indexColumn,
				fields
			)
		).toMatchSnapshot();
	});

	it('should not add new fields to the column as a way to remove', () => {
		const indexColumn = 2;
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.addFields(context, indexPage, indexRow, indexColumn)
		).toMatchSnapshot();
	});

	it('should remove a column from context and reorder', () => {
		const indexColumn = 1;
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.removeColumn(
				context,
				indexPage,
				indexRow,
				indexColumn
			)
		).toMatchSnapshot();
	});

	it('should remove a fields to column from context', () => {
		const indexColumn = 1;
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.removeFields(
				context,
				indexPage,
				indexRow,
				indexColumn
			)
		).toMatchSnapshot();
	});

	it('should remove a row from context and reorder', () => {
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.removeRow(context, indexPage, indexRow)
		).toMatchSnapshot();
	});

	it('should get a column from context', () => {
		const indexColumn = 1;
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.getColumn(context, indexPage, indexRow, indexColumn)
		).toMatchSnapshot();
	});

	it('should get a row from context', () => {
		const indexPage = 0;
		const indexRow = 1;

		expect(
			LayoutSupport.getRow(context, indexPage, indexRow)
		).toMatchSnapshot();
	});

	it('should return true if there are fields in the context line', () => {
		const indexPage = 0;
		const indexRow = 0;

		expect(
			LayoutSupport.hasFieldsRow(context, indexPage, indexRow)
		).toBeTruthy();
	});

	it('should return false if there are fields in the context line', () => {
		const indexPage = 0;
		const indexRow = 0;

		expect(
			LayoutSupport.hasFieldsRow(
				LayoutSupport.removeFields(context, indexPage, indexRow, 0),
				indexPage,
				indexRow
			)
		).toBeFalsy();
	});

	it('should extract the location of the field through the element', () => {
		const element = createElement({
			tagname: 'div',
			attributes: [
				{
					key: 'data-ddm-field-column',
					value: 0,
				},
				{
					key: 'data-ddm-field-row',
					value: 2,
				},
				{
					key: 'data-ddm-field-page',
					value: 2,
				},
			],
		});

		expect(LayoutSupport.getIndexes(element)).toEqual({
			indexColumn: 0,
			indexPage: 2,
			indexRow: 2,
		});
	});

	it('should extract the location of the row through the element', () => {
		const element = createElement({
			tagname: 'div',
			attributes: [
				{
					key: 'data-ddm-field-row',
					value: 1,
				},
				{
					key: 'data-ddm-field-page',
					value: 2,
				},
			],
		});

		expect(LayoutSupport.getIndexes(element)).toEqual({
			indexColumn: false,
			indexPage: 2,
			indexRow: 1,
		});
	});

	it('should replace column fields', () => {
		const indexPage = 0;
		const indexRow = 0;
		const indexColumn = 0;
		const newField = [
			{
				type: 'radio',
				label: 'Foo',
			},
		];

		expect(
			LayoutSupport.changeFieldsFromColumn(
				context,
				indexPage,
				indexRow,
				indexColumn,
				newField
			)
		).toMatchSnapshot();
	});
});
