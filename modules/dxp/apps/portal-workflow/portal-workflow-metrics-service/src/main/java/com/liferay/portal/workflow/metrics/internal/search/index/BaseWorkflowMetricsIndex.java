/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import java.util.ArrayList;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowMetricsIndex implements WorkflowMetricsIndex {

	@Override
	public boolean createIndex(long companyId) throws PortalException {
		if (!searchCapabilities.isWorkflowMetricsSupported() ||
			exists(companyId)) {

			return false;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			getIndexName(companyId));

		createIndexRequest.setMappings(
			_readJSON(getIndexType() + "-mappings.json"));
		createIndexRequest.setSettings(_readJSON("settings.json"));

		try {
			searchEngineAdapter.execute(createIndexRequest);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return true;
	}

	@Override
	public boolean exists(long companyId) {
		if (!searchCapabilities.isWorkflowMetricsSupported()) {
			return false;
		}

		ArrayList<String> indexNames = portalCache.get(companyId);

		if ((indexNames != null) &&
			indexNames.contains(getIndexName(companyId))) {

			return true;
		}

		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(getIndexName(companyId));

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		if (indicesExistsIndexResponse.isExists()) {
			if (indexNames == null) {
				indexNames = new ArrayList<>();
			}

			indexNames.add(getIndexName(companyId));

			portalCache.put(companyId, indexNames);
		}

		return indicesExistsIndexResponse.isExists();
	}

	@Override
	public boolean removeIndex(long companyId) throws PortalException {
		if (!searchCapabilities.isWorkflowMetricsSupported() ||
			!exists(companyId)) {

			return false;
		}

		searchEngineAdapter.execute(
			new DeleteIndexRequest(getIndexName(companyId)));

		ArrayList<String> indexNames = portalCache.get(companyId);

		if (indexNames == null) {
			return true;
		}

		indexNames.remove(getIndexName(companyId));

		portalCache.put(companyId, indexNames);

		return true;
	}

	@Activate
	protected void activate() {
		if (portalCache != null) {
			return;
		}

		portalCache =
			(PortalCache<Long, ArrayList<String>>)multiVMPool.getPortalCache(
				BaseWorkflowMetricsIndex.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		multiVMPool.removePortalCache(BaseWorkflowMetricsIndex.class.getName());
	}

	@Reference
	protected MultiVMPool multiVMPool;

	protected PortalCache<Long, ArrayList<String>> portalCache;

	@Reference
	protected SearchCapabilities searchCapabilities;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private String _readJSON(String fileName) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(getClass(), "/META-INF/search/" + fileName));

			return jsonObject.toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowMetricsIndex.class);

}