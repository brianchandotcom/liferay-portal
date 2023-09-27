/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = SynonymSetIndexWriter.class)
public class SynonymSetIndexWriterImpl implements SynonymSetIndexWriter {

	@Override
	public String create(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		IndexDocumentRequest documentRequest = new IndexDocumentRequest(
			synonymSetIndexName.getIndexName(),
			_synonymSetToDocumentTranslator.translate(synonymSet));

		documentRequest.setRefresh(true);

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(documentRequest);

		return indexDocumentResponse.getUid();
	}

	@Override
	public void remove(SynonymSetIndexName synonymSetIndexName, String id) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			synonymSetIndexName.getIndexName(), id);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void update(
		SynonymSetIndexName synonymSetIndexName, SynonymSet synonymSet) {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			synonymSetIndexName.getIndexName(),
			synonymSet.getSynonymSetDocumentId(),
			_synonymSetToDocumentTranslator.translate(synonymSet));

		indexDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SynonymSetToDocumentTranslator _synonymSetToDocumentTranslator;

}