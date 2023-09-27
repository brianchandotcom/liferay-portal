/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.search.spi.model.index.contributor;

import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "indexer.class.name=com.liferay.knowledge.base.model.KBArticle",
	service = ModelDocumentContributor.class
)
public class KBArticleTextEmbeddingModelDocumentContributor
	extends BaseTextEmbeddingModelDocumentContributor<KBArticle>
	implements ModelDocumentContributor<KBArticle> {

	@Override
	public void contribute(Document document, KBArticle kbArticle) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		addTextEmbeddings(
			kbArticle, _textEmbeddingRetriever::getTextEmbedding,
			kbArticle.getCompanyId(), document);
	}

	@Override
	protected String getText(KBArticle kbArticle) {
		return StringBundler.concat(
			kbArticle.getTitle(), StringPool.SPACE, kbArticle.getContent());
	}

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}