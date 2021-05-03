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

package com.liferay.portal.search.tuning.blueprints.misspellings.web.internal.index;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;
import com.liferay.portal.search.tuning.blueprints.misspellings.index.name.MisspellingsIndexName;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = MisspellingsIndexWriter.class)
public class MisspellingsIndexWriterImpl implements MisspellingsIndexWriter {

	@Override
	public String create(
		MisspellingsIndexName misspellingsIndexName,
		MisspellingSet misspellingSet) {

		IndexDocumentRequest documentRequest = new IndexDocumentRequest(
			misspellingsIndexName.getIndexName(),
			_misspellingsToDocumentTranslator.translate(misspellingSet));

		documentRequest.setRefresh(true);

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(documentRequest);

		return indexDocumentResponse.getUid();
	}

	@Override
	public void remove(MisspellingsIndexName misspellingsIndexName, String id) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			misspellingsIndexName.getIndexName(), id);

		deleteDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	@Override
	public void update(
		MisspellingsIndexName misspellingsIndexName,
		MisspellingSet misspellingSet) {

		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			misspellingsIndexName.getIndexName(),
			misspellingSet.getMisspellingSetId(),
			_misspellingsToDocumentTranslator.translate(misspellingSet));

		updateDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(updateDocumentRequest);
	}

	@Reference
	private MisspellingSetToDocumentTranslator
		_misspellingsToDocumentTranslator;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}