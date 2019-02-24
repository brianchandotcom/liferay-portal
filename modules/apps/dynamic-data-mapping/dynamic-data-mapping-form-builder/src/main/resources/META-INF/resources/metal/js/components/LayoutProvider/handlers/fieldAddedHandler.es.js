import {FormSupport} from '../../Form/index.es';
import {generateInstanceId} from '../../../util/fieldSupport.es';

const handleFieldAdded = (props, state, event) => {
	const {focusedField, target} = event;
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

	if (FormSupport.rowHasFields(pages, pageIndex, rowIndex)) {
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