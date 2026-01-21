/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.document;

import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;

import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

/**
 * @author Adam Brandizzi
 */
public interface ElasticsearchBulkableDocumentRequestTranslator {

	public DeleteOperation translate(
		DeleteDocumentRequest deleteDocumentRequest);

	public Object translate(GetDocumentRequest getDocumentRequest);

	public IndexOperation translate(IndexDocumentRequest indexDocumentRequest);

	public UpdateOperation translate(
		UpdateDocumentRequest updateDocumentRequest);

}