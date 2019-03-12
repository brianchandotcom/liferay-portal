/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowMetricsIndexer<T> {

	public void addDocument(T t) {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			getIndexName(), createDocument(t));

		indexDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(indexDocumentRequest);
	}

	public void deleteDocument(T t) {
		Document document = createDocument(t);

		document.addKeyword("deleted", true);

		_updateDocument(document);
	}

	public void updateDocument(T t) {
		Document document = createDocument(t);

		_updateDocument(document);
	}

	@Activate
	protected void activate() throws Exception {
		createIndex();
	}

	protected abstract Document createDocument(T t);

	protected void createIndex() throws PortalException {
		IndicesExistsIndexRequest indicesExistsIndexRequest =
			new IndicesExistsIndexRequest(getIndexName());

		IndicesExistsIndexResponse indicesExistsIndexResponse =
			searchEngineAdapter.execute(indicesExistsIndexRequest);

		if (indicesExistsIndexResponse.isExists()) {
			return;
		}

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(
			getIndexName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(getClass(), "/META-INF/search/mappings.json"));

		createIndexRequest.setSource(
			JSONUtil.put(
				"mappings",
				JSONUtil.put(getIndexType(), jsonObject.get(getIndexType()))
			).put(
				"settings",
				JSONFactoryUtil.createJSONObject(
					StringUtil.read(
						getClass(), "/META-INF/search/settings.json"))
			).toString());

		searchEngineAdapter.execute(createIndexRequest);

		populateIndex();
	}

	protected String digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	protected abstract String getIndexName();

	protected abstract String getIndexType();

	protected long getKaleoDefinitionId(long kaleoDefinitionVersionId) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.fetchKaleoDefinitionVersion(
				kaleoDefinitionVersionId);

		if (kaleoDefinitionVersion != null) {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionVersion.fetchKaleoDefinition();

			if (kaleoDefinition != null) {
				return kaleoDefinition.getKaleoDefinitionId();
			}
		}

		return 0;
	}

	protected abstract void populateIndex() throws PortalException;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _updateDocument(Document document) {
		UpdateDocumentRequest updateDocumentRequest = new UpdateDocumentRequest(
			getIndexName(), document.getUID(), document);

		updateDocumentRequest.setType(getIndexType());

		searchEngineAdapter.execute(updateDocumentRequest);
	}

}