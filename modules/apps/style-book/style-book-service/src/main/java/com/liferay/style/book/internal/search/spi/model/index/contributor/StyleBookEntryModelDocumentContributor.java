/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.search.spi.model.index.contributor;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.style.book.internal.search.StyleBookEntryField;
import com.liferay.style.book.model.StyleBookEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "indexer.class.name=com.liferay.style.book.model.StyleBookEntry",
	service = ModelDocumentContributor.class
)
public class StyleBookEntryModelDocumentContributor
	implements ModelDocumentContributor<StyleBookEntry> {

	@Override
	public void contribute(Document document, StyleBookEntry styleBookEntry) {
		document.addText(Field.NAME, styleBookEntry.getName());
		document.addText(Field.TITLE, styleBookEntry.getName());
		document.addKeyword(
			StyleBookEntryField.DEFAULT_STYLE_BOOK_ENTRY,
			styleBookEntry.isDefaultStyleBookEntry());
		document.addKeyword(StyleBookEntryField.HEAD, styleBookEntry.isHead());
		document.addKeyword(
			StyleBookEntryField.STYLE_BOOK_ENTRY_KEY,
			styleBookEntry.getStyleBookEntryKey());
		document.addKeyword(
			StyleBookEntryField.THEME_ID, styleBookEntry.getThemeId());
	}

}