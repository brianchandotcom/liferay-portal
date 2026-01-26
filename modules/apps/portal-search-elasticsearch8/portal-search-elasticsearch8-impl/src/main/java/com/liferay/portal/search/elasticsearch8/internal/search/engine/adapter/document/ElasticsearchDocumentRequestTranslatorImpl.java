/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.script.ScriptTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = ElasticsearchDocumentRequestTranslator.class
)
public class ElasticsearchDocumentRequestTranslatorImpl
	implements ElasticsearchDocumentRequestTranslator {

	@Override
	public DeleteRequest translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteRequest.Builder builder = new DeleteRequest.Builder();

		builder.id(deleteDocumentRequest.getUid());
		builder.index(deleteDocumentRequest.getIndexName());

		if (deleteDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		return builder.build();
	}

	@Override
	public GetRequest translate(GetDocumentRequest getDocumentRequest) {
		GetRequest.Builder builder = new GetRequest.Builder();

		builder.id(getDocumentRequest.getId());
		builder.index(getDocumentRequest.getIndexName());
		builder.refresh(getDocumentRequest.isRefresh());
		builder.source(
			source -> source.fetch(getDocumentRequest.isFetchSource()));
		builder.sourceExcludes(
			ListUtil.fromArray(getDocumentRequest.getFetchSourceExcludes()));
		builder.sourceIncludes(
			ListUtil.fromArray(getDocumentRequest.getFetchSourceIncludes()));
		builder.storedFields(
			ListUtil.fromArray(getDocumentRequest.getStoredFields()));

		return builder.build();
	}

	@Override
	public IndexRequest<JsonData> translate(
		IndexDocumentRequest indexDocumentRequest) {

		IndexRequest.Builder<JsonData> builder = new IndexRequest.Builder<>();

		builder.document(
			DocumentRequestTranslatorUtil.getDocument(
				indexDocumentRequest.getDocument(),
				indexDocumentRequest.getDocument71()));
		builder.id(DocumentRequestTranslatorUtil.getUid(indexDocumentRequest));
		builder.index(indexDocumentRequest.getIndexName());

		if (indexDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		return builder.build();
	}

	@Override
	public UpdateRequest<JsonData, JsonData> translate(
		UpdateDocumentRequest updateDocumentRequest) {

		UpdateRequest.Builder<JsonData, JsonData> builder =
			new UpdateRequest.Builder<>();

		if (updateDocumentRequest.isUpsert()) {
			builder.docAsUpsert(true);
		}

		builder.id(DocumentRequestTranslatorUtil.getUid(updateDocumentRequest));
		builder.index(updateDocumentRequest.getIndexName());

		if (updateDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		if (updateDocumentRequest.getScript() != null) {
			builder.script(
				_scriptTranslator.translate(updateDocumentRequest.getScript()));
		}
		else {
			builder.doc(
				DocumentRequestTranslatorUtil.getDocument(
					updateDocumentRequest.getDocument(),
					updateDocumentRequest.getDocument71()));
		}

		if (updateDocumentRequest.isScriptedUpsert()) {
			builder.scriptedUpsert(true);
			builder.upsert(JsonData.of(Collections.emptyMap()));
		}

		return builder.build();
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}