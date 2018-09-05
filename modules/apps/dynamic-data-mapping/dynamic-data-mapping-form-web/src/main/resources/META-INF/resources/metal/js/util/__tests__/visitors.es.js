import {PagesVisitor} from '../visitors.es';
import mockPages from './__mock__/mockPages.es';

let visitor;

describe(
	'PagesVisitor',
	() => {
		beforeEach(
			() => {
				visitor = new PagesVisitor(mockPages);
			}
		);

		afterEach(
			() => {
				if (visitor) {
					visitor.dispose();
				}
			}
		);

		it(
			'should be able to change pages',
			() => {
				expect(
					visitor.mapPages(
						(page, index) => (
							{
								...page,
								title: `New title ${index}`
							}
						)
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should be able to change rows',
			() => {
				expect(
					visitor.mapRows(
						row => (
							{
								...row,
								columns: []
							}
						)
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should be able to change columns',
			() => {
				expect(
					visitor.mapColumns(
						column => (
							{
								...column,
								size: 6
							}
						)
					)
				).toMatchSnapshot();
			}
		);

		it(
			'should be able to change fields',
			() => {
				expect(
					visitor.mapFields(
						(field, index) => (
							{
								...field,
								label: `New label ${index}`
							}
						)
					)
				).toMatchSnapshot();
			}
		);
	}
);