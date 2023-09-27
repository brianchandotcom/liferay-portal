/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.search.spi.model.index.contributor;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 */
@Component(
	property = "indexer.class.name=com.liferay.blogs.model.BlogsEntry",
	service = ModelDocumentContributor.class
)
public class BlogsEntryModelDocumentContributor
	implements ModelDocumentContributor<BlogsEntry> {

	@Override
	public void contribute(Document document, BlogsEntry blogsEntry) {
		document.addText(Field.CAPTION, blogsEntry.getCoverImageCaption());

		String content = _htmlParser.extractText(blogsEntry.getContent());

		document.addText(Field.CONTENT, content);

		document.addText(Field.DESCRIPTION, blogsEntry.getDescription());
		document.addDate(Field.DISPLAY_DATE, blogsEntry.getDisplayDate());
		document.addDate(Field.MODIFIED_DATE, blogsEntry.getModifiedDate());
		document.addText(Field.SUBTITLE, blogsEntry.getSubtitle());
		document.addText(Field.TITLE, blogsEntry.getTitle());
		document.addKeywordSortable("urlTitle", blogsEntry.getUrlTitle());

		for (Locale locale :
				_language.getAvailableLocales(blogsEntry.getGroupId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			document.addText(
				_localization.getLocalizedName(Field.CONTENT, languageId),
				content);
			document.addText(
				_localization.getLocalizedName(Field.TITLE, languageId),
				blogsEntry.getTitle());
		}
	}

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}