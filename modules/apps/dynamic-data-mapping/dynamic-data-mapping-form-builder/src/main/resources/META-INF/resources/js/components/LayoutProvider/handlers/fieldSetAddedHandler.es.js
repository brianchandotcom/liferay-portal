import {generateFieldName} from '../util/fields.es';
import {PagesVisitor} from '../../../util/visitors.es';

const handleFieldSetAdded = (props, state, event) => {
	const {fieldSetPages, target} = event;
	const {pages} = state;
	const {pageIndex, rowIndex} = target;

	const visitor = new PagesVisitor(fieldSetPages);

	const newFieldsetPages = visitor.mapFields(
		field => {
			const name = generateFieldName(pages, field.fieldName);

			const settingsContextVisitor = new PagesVisitor(field.settingsContext.pages);

			return {
				...field,
				fieldName: name,
				settingsContext: {
					...field.settingsContext,
					pages: settingsContextVisitor.mapFields(
						settingsContextField => {
							if (settingsContextField.fieldName === 'name') {
								settingsContextField = {
									...settingsContextField,
									value: name
								};
							}

							return settingsContextField;
						}
					)
				}
			};
		}
	);

	const rows = newFieldsetPages[0].rows;

	for (let i = rows.length - 1; i >= 0; i--) {
		pages[pageIndex].rows.splice(rowIndex, 0, rows[i]);
	}

	return {pages};
};

export default handleFieldSetAdded;