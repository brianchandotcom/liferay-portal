/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.index.contributor.internal;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.tuning.blueprints.index.contributor.internal.constants.FieldNames;
import com.liferay.wiki.model.WikiPage;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"indexer.class.name=com.liferay.blogs.model.BlogsEntry",
		"indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		"indexer.class.name=com.liferay.journal.model.JournalArticle",
		"indexer.class.name=com.liferay.knowledge.base.model.KBArticle",
		"indexer.class.name=com.liferay.wiki.model.WikiPage"
	},
	service = IndexerPostProcessor.class
)
public class BlueprintsIndexerPostProcessor extends BaseIndexerPostProcessor {

	@Override
	public void postProcessDocument(Document document, Object object)
		throws Exception {

		_addContentLength(document);

		_addVersionCount(object, document);

		_addLocalizedContentLengths(document);
	}

	private void _addContentLength(Document document) {
		String content = document.get(Field.CONTENT);

		if (!Validator.isBlank(content)) {
			document.addNumber(_getLengthFieldName(null), content.length());
		}
	}

	private void _addLocalizedContentLengths(Document document) {
		long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		for (Locale locale : _language.getAvailableLocales(groupId)) {
			StringBundler sb = new StringBundler(3);

			sb.append(Field.CONTENT);
			sb.append(StringPool.UNDERLINE);
			sb.append(locale.toString());

			String content = document.get(sb.toString());

			if (Validator.isBlank(content)) {
				continue;
			}

			document.addNumber(_getLengthFieldName(locale), content.length());
		}
	}

	private void _addVersionCount(Object object, Document document) {
		Class<?> clazz = object.getClass();

		String className = clazz.getSimpleName();

		Double version = null;

		if (className.startsWith(DLFileEntry.class.getSimpleName())) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			version = GetterUtil.getDouble(dlFileEntry.getVersion());
		}
		else if (className.startsWith(JournalArticle.class.getSimpleName())) {
			JournalArticle journalArticle = (JournalArticle)object;

			version = journalArticle.getVersion();
		}
		else if (className.startsWith(WikiPage.class.getSimpleName())) {
			WikiPage wikiPage = (WikiPage)object;

			version = wikiPage.getVersion();
		}

		if (version != null) {
			document.addNumber(FieldNames.VERSION_COUNT, version);
		}
	}

	private String _getLengthFieldName(Locale locale) {
		StringBundler sb = new StringBundler(5);

		sb.append(Field.CONTENT);

		if (locale != null) {
			sb.append(StringPool.UNDERLINE);
			sb.append(locale.toString());
		}

		sb.append(StringPool.UNDERLINE);
		sb.append(FieldNames.CONTENT_LENGTH);

		return sb.toString();
	}

	@Reference
	private Language _language;

}