/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.opensearch2.internal.script.ScriptTranslator;

import java.util.Collections;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.bulk.DeleteOperation;
import org.opensearch.client.opensearch.core.bulk.IndexOperation;
import org.opensearch.client.opensearch.core.bulk.UpdateOperation;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(
	property = "search.engine.impl=OpenSearch",
	service = OpenSearchBulkableDocumentRequestTranslator.class
)
public class OpenSearchBulkableDocumentRequestTranslatorImpl
	extends BaseDocumentRequestTranslator
	implements OpenSearchBulkableDocumentRequestTranslator {

	@Override
	public DeleteOperation translate(
		DeleteDocumentRequest deleteDocumentRequest) {

		DeleteOperation.Builder builder = new DeleteOperation.Builder();

		builder.id(deleteDocumentRequest.getUid());
		builder.index(deleteDocumentRequest.getIndexName());

		return builder.build();
	}

	@Override
	public Object translate(GetDocumentRequest getDocumentRequest) {
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

		builder.id(getUid(updateDocumentRequest));

		if (updateDocumentRequest.isUpsert()) {
			builder.docAsUpsert(true);
		}

		builder.index(updateDocumentRequest.getIndexName());

		if (updateDocumentRequest.getScript() != null) {
			builder.script(
				_scriptTranslator.translate(updateDocumentRequest.getScript()));
		}
		else {
			builder.document(
				getDocument(
					updateDocumentRequest.getDocument(),
					updateDocumentRequest.getDocument71()));
		}

		if (updateDocumentRequest.isScriptedUpsert()) {
			builder.upsert(Collections.emptyMap());
		}

		return builder.build();
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}