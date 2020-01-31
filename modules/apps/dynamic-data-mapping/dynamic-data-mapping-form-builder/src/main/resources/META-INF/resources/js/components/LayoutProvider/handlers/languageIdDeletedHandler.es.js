/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {PagesVisitor} from 'dynamic-data-mapping-form-renderer';

const deleteLanguageId = (languageId, pages) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(field => {
		const {localizedValue} = field;
		const newLocalizedValue = {...localizedValue};

		delete newLocalizedValue[languageId];

		return {
			...field,
			localizedValue: newLocalizedValue
		};
	});
};

export const handleLanguageIdDeleted = (focusedField, pages, languageId) => {
	if (focusedField.settingsContext) {
		focusedField = {
			...focusedField,
			settingsContext: {
				...focusedField.settingsContext,
				pages: deleteLanguageId(
					languageId,
					focusedField.settingsContext.pages
				)
			}
		};
	}

	const visitor = new PagesVisitor(pages);

	pages = visitor.mapPages(page => {
		const {localizedDescription, localizedTitle} = page;

		delete localizedDescription[languageId];
		delete localizedTitle[languageId];

		return {
			...page,
			localizedDescription,
			localizedTitle
		};
	});

	visitor.setPages(pages);

	pages = visitor.mapFields(field => {
		const {settingsContext} = field;

		return {
			...field,
			settingsContext: {
				...settingsContext,
				pages: deleteLanguageId(languageId, settingsContext.pages)
			}
		};
	});

	return {
		focusedField,
		pages
	};
};

export default handleLanguageIdDeleted;
