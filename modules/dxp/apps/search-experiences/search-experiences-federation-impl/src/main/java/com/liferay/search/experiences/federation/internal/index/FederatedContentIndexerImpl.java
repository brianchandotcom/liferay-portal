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

package com.liferay.search.experiences.federation.internal.index;

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	enabled = false, immediate = true, service = FederatedContentIndexer.class
)
public class FederatedContentIndexerImpl implements FederatedContentIndexer {

	@Override
	public void index(Document document) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			FederatedContentIndexDefinition.INDEX_NAME, document);

		indexDocumentRequest.setType(FederatedContentIndexDefinition.TYPE_NAME);

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

}