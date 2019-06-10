import {PagesVisitor} from '../../util/visitors.es';

export default (pages, properties) => {
	const {fieldInstance} = properties;
	const pageVisitor = new PagesVisitor(pages);

	return Promise.resolve(
		pageVisitor.mapFields(field => {
			const matches = field.name === fieldInstance.name;

			return {
				...field,
				displayErrors: field.displayErrors || matches,
				focused: matches ? false : field.focused
			};
		})
	);
};
