import mockPages from 'mock/mockPages.es';
import {PagesVisitor} from 'source/util/visitors.es';

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

	it('should not multate the fields of the original array', () => {
		const newPages = visitor.mapFields((field, index) => {
			if (field.fieldName == 'radio') {
				field.fieldName = 'liferay';
			}

			return field;
		});

		expect(mockPages).not.toBe(newPages);
	});

	it('should be able to change pages', () => {
		expect(
			visitor.mapPages((page, index) => ({
				...page,
				title: `New title ${index}`
			}))
		).toMatchSnapshot();
	});

	it('should be able to change rows', () => {
		expect(
			visitor.mapRows(row => ({
				...row,
				columns: []
			}))
		).toMatchSnapshot();
	});

	it('should be able to change columns', () => {
		expect(
			visitor.mapColumns(column => ({
				...column,
				size: 6
			}))
		).toMatchSnapshot();
	});

	it('should be able to change fields', () => {
		expect(
			visitor.mapFields((field, index) => ({
				...field,
				label: `New label ${index}`
			}))
		).toMatchSnapshot();
	});
});
