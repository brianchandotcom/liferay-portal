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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.script.Script;

/**
 * @author Michael C. Han
 */
public class UpdateByQueryDocumentRequest
	extends CrossClusterRequest
	implements DocumentRequest<UpdateByQueryDocumentResponse> {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by
	 *             UpdateByQueryDocumentRequest.UpdateByQueryDocumentRequest(
	 *             Query, Query, String...)
	 */
	@Deprecated
	public UpdateByQueryDocumentRequest(
		com.liferay.portal.kernel.search.Query query,
		JSONObject scriptJSONObject, String... indexNames) {

		_query = query;
		_scriptJSONObject = scriptJSONObject;
		_indexNames = indexNames;

		_portalSearchQuery = null;
		_script = null;
	}

	public UpdateByQueryDocumentRequest(
		Query portalSearchQuery, Script script, String... indexNames) {

		_portalSearchQuery = portalSearchQuery;
		_script = script;
		_indexNames = indexNames;

		_query = null;
		_scriptJSONObject = null;
	}

	@Override
	public UpdateByQueryDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeDocumentRequest(this);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public Query getPortalSearchQuery() {
		return _portalSearchQuery;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getPortalSearchQuery()}
	 */
	@Deprecated
	public com.liferay.portal.kernel.search.Query getQuery() {
		return _query;
	}

	public Script getScript() {
		return _script;
	}

	public JSONObject getScriptJSONObject() {
		return _scriptJSONObject;
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
	private final Query _portalSearchQuery;
	private final com.liferay.portal.kernel.search.Query _query;
	private boolean _refresh;
	private final Script _script;
	private final JSONObject _scriptJSONObject;
	private boolean _waitForCompletion;

}