import {PagesVisitor} from '../../../util/visitors.es';

const deleteLanguageId = (languageId, pages) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		field => {
			const {localizedValue} = field;
			const newLocalizedValue = {...localizedValue};

			delete newLocalizedValue[languageId];

			return {
				...field,
				localizedValue: newLocalizedValue
			};
		}
	);
};

export const handleLanguageIdDeleted = (focusedField, pages, languageId) => {
	if (focusedField.settingsContext) {
		focusedField = {
			...focusedField,
			settingsContext: {
				...focusedField.settingsContext,
				pages: deleteLanguageId(languageId, focusedField.settingsContext.pages)
			}
		};
	}

	const visitor = new PagesVisitor(pages);

	pages = visitor.mapPages(
		page => {
			const {localizedDescription, localizedTitle} = page;

			delete localizedDescription[languageId];
			delete localizedTitle[languageId];

			return {
				...page,
				localizedDescription,
				localizedTitle
			};
		}
	);

	visitor.setPages(pages);

	pages = visitor.mapFields(
		field => {
			const {settingsContext} = field;

			return {
				...field,
				settingsContext: {
					...settingsContext,
					pages: deleteLanguageId(languageId, settingsContext.pages)
				}
			};
		}
	);

	return {
		focusedField,
		pages
	};
};

export default handleLanguageIdDeleted;