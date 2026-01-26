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

/**
 * @author Michael C. Han
 */
public class ElasticsearchBulkableDocumentRequestTranslatorUtil {

	public static DeleteOperation translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteOperation.Builder builder = new DeleteOperation.Builder();

		builder.id(deleteDocumentRequest.getUid());
		builder.index(deleteDocumentRequest.getIndexName());

		return builder.build();
	}

	public static GetRequest translate(GetDocumentRequest getDocumentRequest) {
		throw new UnsupportedOperationException();
	}

	public static IndexOperation<JsonData> translate(
		IndexDocumentRequest indexDocumentRequest) {

		IndexOperation.Builder<JsonData> builder =
			new IndexOperation.Builder<>();

		builder.document(
			DocumentRequestTranslatorUtil.getDocument(
				indexDocumentRequest.getDocument(),
				indexDocumentRequest.getDocument71()));
		builder.id(DocumentRequestTranslatorUtil.getUid(indexDocumentRequest));
		builder.index(indexDocumentRequest.getIndexName());

		return builder.build();
	}

	public static UpdateOperation translate(
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
				DocumentRequestTranslatorUtil.getDocument(
					updateDocumentRequest.getDocument(),
					updateDocumentRequest.getDocument71()));
		}

		if (updateDocumentRequest.isScriptedUpsert()) {
			updateActionBuilder.upsert(Collections.emptyMap());
		}

		builder.action(updateActionBuilder.build());
		builder.id(DocumentRequestTranslatorUtil.getUid(updateDocumentRequest));
		builder.index(updateDocumentRequest.getIndexName());

		return builder.build();
	}

	private static final ScriptTranslator _scriptTranslator =
		new ScriptTranslator();

}