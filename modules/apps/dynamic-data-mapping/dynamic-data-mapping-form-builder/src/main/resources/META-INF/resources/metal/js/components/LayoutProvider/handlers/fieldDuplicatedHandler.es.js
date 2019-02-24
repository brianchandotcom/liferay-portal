import {FormSupport} from '../../Form/index.es';
import {generateFieldName} from '../util/fields.es';
import {PagesVisitor} from '../../../util/visitors.es';
import {sub} from '../../../util/strings.es';

const handleFieldDuplicated = (state, event) => {
	const {columnIndex, pageIndex, rowIndex} = event;
	const {locale, pages} = state;
	const field = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);
	const label = sub(
		Liferay.Language.get('copy-of-x'),
		[field.label]
	);
	const newFieldName = generateFieldName(pages, label);
	const visitor = new PagesVisitor(field.settingsContext.pages);

	const duplicatedField = {
		...field,
		fieldName: newFieldName,
		label,
		name: newFieldName,
		settingsContext: {
			...field.settingsContext,
			pages: visitor.mapFields(
				field => {
					if (field.fieldName === 'name') {
						field = {
							...field,
							value: newFieldName
						};
					}
					else if (field.fieldName === 'label') {
						field = {
							...field,
							localizedValue: {
								...field.localizedValue,
								[locale]: label
							},
							value: label
						};
					}
					return {
						...field
					};
				}
			)
		}
	};
	const newRowIndex = rowIndex + 1;

	const newPages = FormSupport.addRow(pages, newRowIndex, pageIndex);

	FormSupport.addFieldToColumn(newPages, pageIndex, newRowIndex, columnIndex, duplicatedField);

	return {
		focusedField: {
			...duplicatedField,
			columnIndex,
			pageIndex,
			rowIndex: newRowIndex
		},
		pages: newPages
	};
};

export default handleFieldDuplicated;