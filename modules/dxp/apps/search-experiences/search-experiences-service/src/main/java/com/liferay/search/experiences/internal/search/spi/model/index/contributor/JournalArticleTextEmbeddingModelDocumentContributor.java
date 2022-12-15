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

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.ml.text.embedding.TextEmbeddingRetriever;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	enabled = false,
	property = "indexer.class.name=com.liferay.journal.model.JournalArticle",
	service = ModelDocumentContributor.class
)
public class JournalArticleTextEmbeddingModelDocumentContributor
	extends BaseTextEmbeddingModelDocumentContributor
	implements ModelDocumentContributor<JournalArticle> {

	@Override
	public void contribute(Document document, JournalArticle journalArticle) {
		if (!isAddTextEmbedding(JournalArticle.class) ||
			(journalArticle.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		List<String> languageIds = Arrays.asList(
			semanticSearchConfiguration.languageIds());

		for (Locale locale :
				_language.getCompanyAvailableLocales(
					journalArticle.getCompanyId())) {

			String languageId = LocaleUtil.toLanguageId(locale);

			if (!languageIds.contains(languageId)) {
				continue;
			}

			addTextEmbedding(
				document, languageId,
				getTextEmbedding(
					_textEmbeddingRetriever::getTextEmbedding,
					StringBundler.concat(
						journalArticle.getTitle(languageId, true),
						StringPool.SPACE,
						_getArticleContent(journalArticle, languageId))));
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		semanticSearchConfiguration = ConfigurableUtil.createConfigurable(
			SemanticSearchConfiguration.class, properties);
	}

	private String _getArticleContent(
		JournalArticle journalArticle, String languageId) {

		DDMFormValues ddmFormValues = journalArticle.getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"content");

		if (ddmFormFieldValues.isEmpty()) {
			return StringPool.BLANK;
		}

		DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(0);

		Value value = ddmFormFieldValue.getValue();

		return value.getString(_language.getLocale(languageId));
	}

	@Reference
	private Language _language;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}