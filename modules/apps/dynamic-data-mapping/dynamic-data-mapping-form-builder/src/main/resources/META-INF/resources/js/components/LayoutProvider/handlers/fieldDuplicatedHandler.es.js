import * as FormSupport from '../../Form/FormSupport.es';
import {generateFieldName, getFieldLocalizedValue} from '../util/fields.es';
import {PagesVisitor} from '../../../util/visitors.es';
import {sub} from '../../../util/strings.es';

const handleFieldDuplicated = (state, editingLanguageId, event) => {
	const {columnIndex, pageIndex, rowIndex} = event.indexes;
	const {pages} = state;

	const field = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);

	const localizedLabel = getFieldLocalizedValue(
		field.settingsContext.pages,
		'label',
		editingLanguageId
	);

	const label = sub(Liferay.Language.get('copy-of-x'), [localizedLabel]);
	const newFieldName = generateFieldName(pages, label);
	const visitor = new PagesVisitor(field.settingsContext.pages);

	const duplicatedField = {
		...field,
		fieldName: newFieldName,
		label,
		name: newFieldName,
		settingsContext: {
			...field.settingsContext,
			pages: visitor.mapFields(field => {
				if (field.fieldName === 'name') {
					field = {
						...field,
						value: newFieldName
					};
				} else if (field.fieldName === 'label') {
					field = {
						...field,
						localizedValue: {
							...field.localizedValue,
							[editingLanguageId]: label
						},
						value: label
					};
				}
				return {
					...field
				};
			})
		}
	};
	const newRowIndex = rowIndex + 1;

	const newRow = FormSupport.implAddRow(12, [duplicatedField]);

	return {
		focusedField: {
			...duplicatedField,
			columnIndex,
			pageIndex,
			rowIndex: newRowIndex
		},
		pages: FormSupport.addRow(pages, newRowIndex, pageIndex, newRow)
	};
};

export default handleFieldDuplicated;
