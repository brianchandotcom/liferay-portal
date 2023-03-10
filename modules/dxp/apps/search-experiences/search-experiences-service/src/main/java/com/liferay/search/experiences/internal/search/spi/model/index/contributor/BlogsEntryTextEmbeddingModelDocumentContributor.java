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

import com.liferay.blogs.model.BlogsEntry;
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
	property = "indexer.class.name=com.liferay.blogs.model.BlogsEntry",
	service = ModelDocumentContributor.class
)
public class BlogsEntryTextEmbeddingModelDocumentContributor
	extends BaseTextEmbeddingModelDocumentContributor<BlogsEntry>
	implements ModelDocumentContributor<BlogsEntry> {

	@Override
	public void contribute(Document document, BlogsEntry blogsEntry) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		addTextEmbeddings(
			blogsEntry, _textEmbeddingRetriever::getTextEmbedding,
			blogsEntry.getCompanyId(), document);
	}

	@Override
	protected String getText(BlogsEntry blogsEntry) {
		return StringBundler.concat(
			blogsEntry.getTitle(), StringPool.SPACE, blogsEntry.getContent());
	}

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}