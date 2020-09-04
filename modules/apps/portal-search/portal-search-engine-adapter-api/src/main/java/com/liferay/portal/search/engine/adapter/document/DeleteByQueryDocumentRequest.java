/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;
import com.liferay.portal.search.query.Query;

/**
 * @author Michael C. Han
 */
public class DeleteByQueryDocumentRequest
	extends CrossClusterRequest
	implements DocumentRequest<DeleteByQueryDocumentResponse> {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by
	 *             DeleteByQueryDocumentRequest.DeleteByQueryDocumentRequest(
	 *             Query, String...)
	 */
	@Deprecated
	public DeleteByQueryDocumentRequest(
		com.liferay.portal.kernel.search.Query legacyQuery,
		String... indexNames) {

		_legacyQuery = legacyQuery;
		_indexNames = indexNames;

		_query = null;
	}

	public DeleteByQueryDocumentRequest(Query query, String... indexNames) {
		_query = query;
		_indexNames = indexNames;

		_legacyQuery = null;
	}

	@Override
	public DeleteByQueryDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Query getQuery() {
		return _query;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by getQuery
	 */
	@Deprecated
	public com.liferay.portal.kernel.search.Query getQuery72() {
		return _legacyQuery;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public boolean isWaitForCompletion() {
		return _waitForCompletion;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setWaitForCompletion(boolean waitForCompletion) {
		_waitForCompletion = waitForCompletion;
	}

	private final String[] _indexNames;
	private final com.liferay.portal.kernel.search.Query _legacyQuery;
	private final Query _query;
	private boolean _refresh;
	private boolean _waitForCompletion;

}