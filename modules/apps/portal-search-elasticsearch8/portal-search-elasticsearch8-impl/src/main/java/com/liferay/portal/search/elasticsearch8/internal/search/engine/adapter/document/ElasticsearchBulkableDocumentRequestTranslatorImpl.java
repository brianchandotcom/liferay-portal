/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.bulk.UpdateAction;
import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.elasticsearch8.internal.script.ScriptTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import java.util.Collections;

import org.elasticsearch.action.get.GetRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = ElasticsearchBulkableDocumentRequestTranslator.class
)
public class ElasticsearchBulkableDocumentRequestTranslatorImpl
	extends BaseDocumentRequestTranslator
	implements ElasticsearchBulkableDocumentRequestTranslator {

	@Override
	public DeleteOperation translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteOperation.Builder builder = new DeleteOperation.Builder();

		builder.id(deleteDocumentRequest.getUid());
		builder.index(deleteDocumentRequest.getIndexName());

		return builder.build();
	}

	@Override
	public GetRequest translate(GetDocumentRequest getDocumentRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IndexOperation translate(IndexDocumentRequest indexDocumentRequest) {
		IndexOperation.Builder<JsonData> builder =
			new IndexOperation.Builder<JsonData>();

		builder.document(
			getDocument(
				indexDocumentRequest.getDocument(),
				indexDocumentRequest.getDocument71()));
		builder.id(getUid(indexDocumentRequest));
		builder.index(indexDocumentRequest.getIndexName());

		return builder.build();
	}

	@Override
	public UpdateOperation translate(
		UpdateDocumentRequest updateDocumentRequest) {

		UpdateOperation.Builder builder = new UpdateOperation.Builder();

		UpdateAction.Builder updateActionBuilder = new UpdateAction.Builder();

		if (updateDocumentRequest.isUpsert()) {
			updateActionBuilder.docAsUpsert(true);
		}

		if (updateDocumentRequest.getScript() != null) {
			updateActionBuilder.script(
				_scriptTranslator.translate(updateDocumentRequest.getScript()));
		}
		else {
			updateActionBuilder.doc(
				getDocument(
					updateDocumentRequest.getDocument(),
					updateDocumentRequest.getDocument71()));
		}

		if (updateDocumentRequest.isScriptedUpsert()) {
			updateActionBuilder.upsert(Collections.emptyMap());
		}

		builder.action(updateActionBuilder.build());
		builder.id(getUid(updateDocumentRequest));
		builder.index(updateDocumentRequest.getIndexName());

		return builder.build();
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}