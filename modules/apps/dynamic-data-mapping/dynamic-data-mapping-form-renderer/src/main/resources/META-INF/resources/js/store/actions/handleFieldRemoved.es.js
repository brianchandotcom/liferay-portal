import {PagesVisitor} from '../../util/visitors.es';

export default (pages, name) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapColumns(column => {
		return {
			...column,
			fields: column.fields.filter(field => field.name !== name)
		};
	});
};
