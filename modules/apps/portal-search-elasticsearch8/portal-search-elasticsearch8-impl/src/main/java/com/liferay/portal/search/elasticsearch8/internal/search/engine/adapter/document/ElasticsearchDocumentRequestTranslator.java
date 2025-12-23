/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

/**
 * @author Petteri Karttunen
 */
public interface ElasticsearchDocumentRequestTranslator {

	public DeleteRequest translate(DeleteDocumentRequest deleteDocumentRequest);

	public GetRequest translate(GetDocumentRequest getDocumentRequest);

	public IndexRequest<JsonData> translate(
		IndexDocumentRequest indexDocumentRequest);

	public UpdateRequest<JsonData, JsonData> translate(
		UpdateDocumentRequest updateDocumentRequest);

}