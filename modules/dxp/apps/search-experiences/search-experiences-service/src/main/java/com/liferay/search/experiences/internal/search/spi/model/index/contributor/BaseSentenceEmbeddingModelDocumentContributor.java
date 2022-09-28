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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.configuration.SentenceTransformerConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseSentenceEmbeddingModelDocumentContributor {

	protected void addSentenceEmbedding(
		Document document, String languageId, Double[] sentenceEmbedding) {

		if (sentenceEmbedding.length == 0) {
			return;
		}

		_addSentenceEmbeddingField(document, languageId, sentenceEmbedding);
	}

	protected void addSentenceEmbeddingForAvailableLanguages(
		long companyId, Document document, Double[] sentenceEmbedding) {

		if (sentenceEmbedding.length == 0) {
			return;
		}

		List<String> languageIds = Arrays.asList(
			sentenceTransformerConfiguration.languageIds());

		for (Locale locale :
				LanguageUtil.getCompanyAvailableLocales(companyId)) {

			String languageId = LocaleUtil.toLanguageId(locale);

			if (!languageIds.contains(languageId)) {
				continue;
			}

			_addSentenceEmbeddingField(document, languageId, sentenceEmbedding);
		}
	}

	protected Double[] getEmbedding(
		Function<String, Double[]> function, String text) {

		try {
			return function.apply(text);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return new Double[0];
	}

	protected boolean isAddSentenceEmbedding(Class<?> clazz) {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) &&
			sentenceTransformerConfiguration.enabled() &&
			ArrayUtil.contains(
				sentenceTransformerConfiguration.entryClassNames(),
				clazz.getName(), true)) {

			return true;
		}

		return false;
	}

	protected volatile SentenceTransformerConfiguration
		sentenceTransformerConfiguration;

	private void _addSentenceEmbeddingField(
		Document document, String languageId, Double[] sentenceEmbedding) {

		Field embeddingField = new Field(_getFieldName(languageId));

		embeddingField.setValues(ArrayUtil.toStringArray(sentenceEmbedding));
		embeddingField.setNumeric(true);
		embeddingField.setNumericClass(Double.class);
		embeddingField.setTokenized(false);

		document.add(embeddingField);
	}

	private String _getFieldName(String languageId) {
		return StringBundler.concat(
			_TEXT_EMBEDDING_FIELD, StringPool.UNDERLINE,
			sentenceTransformerConfiguration.embeddingVectorDimensions() +
				StringPool.UNDERLINE,
			languageId);
	}

	private static final String _TEXT_EMBEDDING_FIELD = "text_embedding";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSentenceEmbeddingModelDocumentContributor.class);

}