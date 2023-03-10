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

import com.liferay.message.boards.model.MBMessage;
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
	property = "indexer.class.name=com.liferay.message.boards.model.MBMessage",
	service = ModelDocumentContributor.class
)
public class MBMessageTextEmbeddingModelDocumentContributor
	extends BaseTextEmbeddingModelDocumentContributor<MBMessage>
	implements ModelDocumentContributor<MBMessage> {

	@Override
	public void contribute(Document document, MBMessage mbMessage) {
		if (Objects.equals(
				_searchEngineInformation.getVendorString(), "Solr")) {

			return;
		}

		addTextEmbeddings(
			mbMessage, _textEmbeddingRetriever::getTextEmbedding,
			mbMessage.getCompanyId(), document);
	}

	@Override
	protected String getText(MBMessage mbMessage) {
		return StringBundler.concat(
			mbMessage.getSubject(), StringPool.SPACE, mbMessage.getBody());
	}

	@Reference
	private SearchEngineInformation _searchEngineInformation;

	@Reference
	private TextEmbeddingRetriever _textEmbeddingRetriever;

}