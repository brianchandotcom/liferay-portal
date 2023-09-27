/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = SynonymSetIndexReader.class)
public class SynonymSetIndexReaderImpl implements SynonymSetIndexReader {

	@Override
	public SynonymSet fetch(
		SynonymSetIndexName synonymSetIndexName, String id) {

		Document document = _getDocument(synonymSetIndexName, id);

		if (document == null) {
			return null;
		}

		return translate(document, id);
	}

	@Override
	public boolean isExists(SynonymSetIndexName synonymSetIndexName) {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(synonymSetIndexName.getIndexName());

		indicesExistsIndexRequest.setPreferLocalCluster(false);

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			_searchEngineAdapter.execute(indicesExistsIndexRequest);

		return indicesExistsIndexResponse.isExists();
	}

	@Override
	public List<SynonymSet> search(SynonymSetIndexName synonymSetIndexName) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(synonymSetIndexName.getIndexName());
		searchSearchRequest.setPreferLocalCluster(false);
		searchSearchRequest.setSize(_SIZE);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		return _documentToSynonymSetTranslator.translateAll(
			searchSearchResponse.getSearchHits());
	}

	protected SynonymSet translate(Document document, String id) {
		return _documentToSynonymSetTranslator.translate(document, id);
	}

	private Document _getDocument(
		SynonymSetIndexName synonymSetIndexName, String id) {

		if (Validator.isNull(id)) {
			return null;
		}

		GetDocumentRequest getDocumentRequest = new GetDocumentRequest(
			synonymSetIndexName.getIndexName(), id);

		getDocumentRequest.setFetchSource(true);
		getDocumentRequest.setFetchSourceInclude(StringPool.STAR);
		getDocumentRequest.setPreferLocalCluster(false);

		GetDocumentResponse getDocumentResponse = _searchEngineAdapter.execute(
			getDocumentRequest);

		if (!getDocumentResponse.isExists()) {
			return null;
		}

		return getDocumentResponse.getDocument();
	}

	private static final int _SIZE = 10000;

	@Reference
	private DocumentToSynonymSetTranslator _documentToSynonymSetTranslator;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}