import * as FormSupport from '../../Form/FormSupport.es';
import {PagesVisitor} from '../../../util/visitors.es';

const handleFieldClicked = (state, event) => {
	const {columnIndex, pageIndex, rowIndex} = event;
	const {pages} = state;

	const fieldProperties = FormSupport.getField(
		pages,
		pageIndex,
		rowIndex,
		columnIndex
	);
	const {settingsContext} = fieldProperties;
	const visitor = new PagesVisitor(settingsContext.pages);

	const focusedField = {
		...fieldProperties,
		columnIndex,
		pageIndex,
		rowIndex,
		settingsContext: {
			...settingsContext,
			pages: visitor.mapFields(field => {
				const {fieldName} = field;

				if (fieldName === 'name') {
					field.visible = true;
				} else if (fieldName === 'label') {
					field.type = 'text';
				} else if (fieldName === 'validation') {
					field = {
						...field,
						validation: {
							...field.validation,
							fieldName: fieldProperties.fieldName
						}
					};
				}

				return field;
			})
		}
	};

	return {
		focusedField: {
			...focusedField,
			columnIndex,
			originalContext: focusedField,
			pageIndex,
			rowIndex
		}
	};
};

export default handleFieldClicked;
