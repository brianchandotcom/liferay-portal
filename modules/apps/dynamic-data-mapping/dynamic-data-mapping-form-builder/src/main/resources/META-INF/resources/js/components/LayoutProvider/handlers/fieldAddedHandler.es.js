import * as FormSupport from '../../Form/FormSupport.es';
import {
	generateInstanceId,
	getFieldProperties
} from '../../../util/fieldSupport.es';

const handleFieldAdded = (props, state, event) => {
	const {addedToPlaceholder, focusedField, target} = event;
	const {fieldName, name, settingsContext} = focusedField;
	const {pageIndex, rowIndex} = target;
	const {defaultLanguageId, editingLanguageId, spritemap} = props;
	let {pages} = state;
	let {columnIndex} = target;

	const fieldProperties = {
		...getFieldProperties(
			settingsContext,
			defaultLanguageId,
			editingLanguageId
		),
		fieldName,
		instanceId: generateInstanceId(8),
		name,
		settingsContext,
		spritemap,
		type: name
	};

	if (addedToPlaceholder) {
		pages = FormSupport.addRow(pages, rowIndex, pageIndex);

		columnIndex = 0;
	}

	return {
		focusedField: {
			...fieldProperties,
			columnIndex,
			originalContext: fieldProperties,
			pageIndex,
			rowIndex
		},
		pages: FormSupport.addFieldToColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex,
			fieldProperties
		)
	};
};

export default handleFieldAdded;
