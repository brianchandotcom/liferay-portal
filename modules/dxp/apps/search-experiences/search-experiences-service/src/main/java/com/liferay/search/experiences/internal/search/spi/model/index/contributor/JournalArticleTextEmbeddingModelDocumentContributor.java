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
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
	extends BaseTextEmbeddingModelDocumentContributor<JournalArticle>
	implements ModelDocumentContributor<JournalArticle> {

	@Override
	public void contribute(Document document, JournalArticle journalArticle) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		try {
			if (!_journalArticleLocalService.isLatestVersion(
					journalArticle.getGroupId(), journalArticle.getArticleId(),
					journalArticle.getVersion(),
					WorkflowConstants.STATUS_APPROVED)) {

				return;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return;
		}

		addLocalizedTextEmbeddings(
			journalArticle, _textEmbeddingRetriever::getTextEmbedding,
			journalArticle.getCompanyId(), document);
	}

	@Override
	protected String getText(JournalArticle journalArticle, String languageId) {
		return StringBundler.concat(
			journalArticle.getTitle(languageId, true), StringPool.SPACE,
			_getArticleContent(journalArticle, languageId));
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

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleTextEmbeddingModelDocumentContributor.class);

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private Language _language;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}