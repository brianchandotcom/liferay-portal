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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;
import com.liferay.wiki.model.WikiPage;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = false,
	property = "indexer.class.name=com.liferay.wiki.model.WikiPage",
	service = ModelDocumentContributor.class
)
public class WikiPageTextEmbeddingModelDocumentContributor
	extends BaseTextEmbeddingModelDocumentContributor<WikiPage>
	implements ModelDocumentContributor<WikiPage> {

	@Override
	public void contribute(Document document, WikiPage wikiPage) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		addTextEmbeddings(
			wikiPage, _textEmbeddingRetriever::getTextEmbedding,
			wikiPage.getCompanyId(), document);
	}

	@Override
	protected String getText(WikiPage wikiPage) {
		return StringBundler.concat(
			wikiPage.getTitle(), StringPool.SPACE, wikiPage.getContent());
	}

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}