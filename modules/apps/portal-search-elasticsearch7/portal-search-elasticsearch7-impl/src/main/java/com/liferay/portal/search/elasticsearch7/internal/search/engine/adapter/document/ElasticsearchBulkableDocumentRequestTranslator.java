/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequestTranslator;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.GetDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;

/**
 * @author Adam Brandizzi
 */
public interface ElasticsearchBulkableDocumentRequestTranslator
	extends BulkableDocumentRequestTranslator {

	public DeleteRequest translate(DeleteDocumentRequest deleteDocumentRequest);

	public GetRequest translate(GetDocumentRequest getDocumentRequest);

	public IndexRequest translate(IndexDocumentRequest indexDocumentRequest);

	public UpdateRequest translate(UpdateDocumentRequest updateDocumentRequest);

}