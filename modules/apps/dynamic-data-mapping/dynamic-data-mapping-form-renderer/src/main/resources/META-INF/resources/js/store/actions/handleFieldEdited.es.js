import {evaluate} from '../../util/evaluation.es';
import {PagesVisitor} from '../../util/visitors.es';

export default (evaluatorContext, properties) => {
	const {fieldInstance, value} = properties;
	const {evaluable, fieldName} = fieldInstance;
	const {editingLanguageId, pages} = evaluatorContext;
	const pageVisitor = new PagesVisitor(pages);

	const editedPages = pageVisitor.mapFields(field => {
		if (field.name === fieldInstance.name) {
			field = {
				...field,
				localizedValue: {
					...field.localizedValue,
					[editingLanguageId]: value
				},
				value
			};
		} else if (field.nestedFields) {
			field = {
				...field,
				nestedFields: field.nestedFields.map(nestedField => {
					if (nestedField.name === fieldInstance.name) {
						nestedField = {
							...nestedField,
							localizedValue: {
								...field.localizedValue,
								[editingLanguageId]: value
							},
							value
						};
					}

					return nestedField;
				})
			};
		}

		return field;
	});

	let promise = Promise.resolve(editedPages);

	if (evaluable) {
		promise = evaluate(fieldName, {
			...evaluatorContext,
			pages: editedPages
		});
	}

	return promise;
};
