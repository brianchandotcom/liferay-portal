/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;

import java.util.Collections;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch.core.DeleteRequest;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.UpdateRequest;

/**
 * @author Petteri Karttunen
 */
public class OpenSearchDocumentRequestTranslatorUtil {

	public static DeleteRequest translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteRequest.Builder builder = new DeleteRequest.Builder();

		builder.id(deleteDocumentRequest.getUid());
		builder.index(deleteDocumentRequest.getIndexName());

		if (deleteDocumentRequest.isRefresh()) {
			builder.refresh(Refresh.True);
		}

		return builder.build();
	}

	public static GetRequest translate(GetDocumentRequest getDocumentRequest) {
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

	public static IndexRequest<JsonData> translate(
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

	public static UpdateRequest<JsonData, JsonData> translate(
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

	private static final ScriptTranslator _scriptTranslator =
		new ScriptTranslator();

}