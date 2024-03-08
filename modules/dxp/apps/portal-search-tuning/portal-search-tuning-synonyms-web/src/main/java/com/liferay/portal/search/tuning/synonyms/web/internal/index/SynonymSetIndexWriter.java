/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

/**
 * @author Adam Brandizzi
 */
public class SynonymSetIndexWriter {

	public SynonymSetIndexWriter(
		DocumentBuilderFactory documentBuilderFactory,
		SearchEngineAdapter searchEngineAdapter) {

		_documentBuilderFactory = documentBuilderFactory;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public String create(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		IndexDocumentRequest documentRequest = new IndexDocumentRequest(
			synonymSetIndexName.getIndexName(),
			SynonymSetToDocumentTranslatorUtil.translate(
				_documentBuilderFactory, synonymSet));

		documentRequest.setRefresh(true);

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(documentRequest);

		return indexDocumentResponse.getUid();
	}

	public void remove(SynonymSetIndexName synonymSetIndexName, String id) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			synonymSetIndexName.getIndexName(), id);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public void update(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			synonymSetIndexName.getIndexName(),
			synonymSet.getSynonymSetDocumentId(),
			SynonymSetToDocumentTranslatorUtil.translate(
				_documentBuilderFactory, synonymSet));

		indexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final SearchEngineAdapter _searchEngineAdapter;

}