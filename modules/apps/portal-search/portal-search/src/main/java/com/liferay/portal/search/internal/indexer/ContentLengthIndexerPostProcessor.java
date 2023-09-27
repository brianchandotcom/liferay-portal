/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	property = {
		"indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		"indexer.class.name=com.liferay.journal.model.JournalArticle",
		"indexer.class.name=com.liferay.knowledge.base.model.KBArticle",
		"indexer.class.name=com.liferay.wiki.model.WikiPage"
	},
	service = IndexerPostProcessor.class
)
public class ContentLengthIndexerPostProcessor
	extends BaseIndexerPostProcessor {

	@Override
	public void postProcessDocument(Document document, Object object)
		throws Exception {

		String content = document.get(Field.CONTENT);

		if (!Validator.isBlank(content)) {
			document.addNumber("contentLength", content.length());
		}

		long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		for (Locale locale : _language.getAvailableLocales(groupId)) {
			String localizedContent = document.get(
				StringBundler.concat(
					Field.CONTENT, StringPool.UNDERLINE,
					_language.getLanguageId(locale)));

			if (Validator.isBlank(localizedContent)) {
				continue;
			}

			document.addNumber(
				"contentLength_" + _language.getLanguageId(locale),
				localizedContent.length());
		}
	}

	@Reference
	private Language _language;

}