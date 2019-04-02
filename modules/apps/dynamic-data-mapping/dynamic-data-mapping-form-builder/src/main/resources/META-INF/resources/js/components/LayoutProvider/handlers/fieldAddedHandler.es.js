import * as FormSupport from '../../Form/FormSupport.es';
import {generateInstanceId} from '../../../util/fieldSupport.es';

const handleFieldAdded = (props, state, event) => {
	const {addedToPlaceholder, focusedField, target} = event;
	const {fieldName, name, settingsContext} = focusedField;
	const {pageIndex, rowIndex} = target;
	const {editingLanguageId, spritemap} = props;
	let {pages} = state;
	let {columnIndex} = target;

	const fieldProperties = {
		...FormSupport.getFieldProperties(settingsContext, editingLanguageId),
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

	pages = FormSupport.addFieldToColumn(pages, target.pageIndex, target.rowIndex, target.columnIndex, fieldProperties);

	return {
		focusedField: {
			...fieldProperties,
			columnIndex,
			originalContext: fieldProperties,
			pageIndex,
			rowIndex
		},
		pages
	};
};

export default handleFieldAdded;