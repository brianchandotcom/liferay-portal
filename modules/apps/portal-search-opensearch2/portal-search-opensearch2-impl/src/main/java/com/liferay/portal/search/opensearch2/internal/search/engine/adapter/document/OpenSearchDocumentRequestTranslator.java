/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.DeleteRequest;
import org.opensearch.client.opensearch.core.GetRequest;
import org.opensearch.client.opensearch.core.IndexRequest;
import org.opensearch.client.opensearch.core.UpdateRequest;

/**
 * @author Petteri Karttunen
 */
public interface OpenSearchDocumentRequestTranslator {

	public DeleteRequest translate(DeleteDocumentRequest deleteDocumentRequest);

	public GetRequest translate(GetDocumentRequest getDocumentRequest);

	public IndexRequest<JsonData> translate(
		IndexDocumentRequest indexDocumentRequest);

	public UpdateRequest<JsonData, JsonData> translate(
		UpdateDocumentRequest updateDocumentRequest);

}