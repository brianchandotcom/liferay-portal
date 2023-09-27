/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

import java.util.function.Consumer;

/**
 * @author Bryan Engler
 */
public class GetDocumentRequest
	extends CrossClusterRequest
	implements BulkableDocumentRequest<GetDocumentRequest>,
			   DocumentRequest<GetDocumentResponse> {

	public GetDocumentRequest(String indexName, String id) {
		_indexName = indexName;
		_id = id;

		setPreferLocalCluster(true);
	}

	@Override
	public void accept(Consumer<GetDocumentRequest> consumer) {
		consumer.accept(this);
	}

	@Override
	public GetDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public String[] getFetchSourceExcludes() {
		return _fetchSourceExclude;
	}

	public String[] getFetchSourceIncludes() {
		return _fetchSourceInclude;
	}

	public String getId() {
		return _id;
	}

	public String getIndexName() {
		return _indexName;
	}

	public String[] getStoredFields() {
		return _storedFields;
	}

	public String getType() {
		return _type;
	}

	public boolean isFetchSource() {
		return _fetchSource;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public void setFetchSource(boolean fetchSource) {
		_fetchSource = fetchSource;
	}

	public void setFetchSourceExclude(String... fetchSourceExclude) {
		_fetchSourceExclude = fetchSourceExclude;
	}

	public void setFetchSourceInclude(String... fetchSourceInclude) {
		_fetchSourceInclude = fetchSourceInclude;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setStoredFields(String... storedFields) {
		_storedFields = storedFields;
	}

	public void setType(String type) {
		_type = type;
	}

	private boolean _fetchSource;
	private String[] _fetchSourceExclude;
	private String[] _fetchSourceInclude;
	private final String _id;
	private final String _indexName;
	private boolean _refresh;
	private String[] _storedFields;
	private String _type;

}