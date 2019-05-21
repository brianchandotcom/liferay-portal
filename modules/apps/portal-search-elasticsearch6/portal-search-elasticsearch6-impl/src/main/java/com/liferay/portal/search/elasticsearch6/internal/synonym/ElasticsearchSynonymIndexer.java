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

package com.liferay.portal.search.elasticsearch6.internal.synonym;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;
import com.liferay.portal.search.synonym.SynonymException;
import com.liferay.portal.search.synonym.SynonymIndexer;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequestBuilder;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = SynonymIndexer.class
)
public class ElasticsearchSynonymIndexer implements SynonymIndexer {

	@Override
	public String[] getSynonymSets(long companyId, String filterName) {
		return getSynonymSets(
			indexNameBuilder.getIndexName(companyId), filterName);
	}

	@Override
	public String[] getSynonymSets(String indexName, String filterName) {
		Settings settings = getSettings(indexName, filterName);

		List<String> synonyms = settings.getAsList(
			getSynonymSetsListPath(filterName));

		return synonyms.toArray(new String[0]);
	}

	@Override
	public void updateSynonymSets(
			long companyId, String filterName, String[] synonymSets)
		throws SynonymException {

		updateSynonymSets(
			indexNameBuilder.getIndexName(companyId), filterName, synonymSets);
	}

	@Override
	public void updateSynonymSets(
			String indexName, String filterName, String[] synonymSets)
		throws SynonymException {

		Settings settings = buildSettings(filterName, synonymSets);

		try {
			doUpdateSynonymSets(indexName, settings);
		}
		catch (Exception e) {
			throw new SynonymException(e);
		}
	}

	protected Settings buildSettings(String filterName, String[] synonymSets) {
		Settings.Builder builder = Settings.builder();

		builder.putList(getSynonymSetsListPath(filterName), synonymSets);

		return builder.build();
	}

	protected void closeIndex(
			IndicesAdminClient indicesAdminClient, String indexName)
		throws ExecutionException, InterruptedException {

		CloseIndexRequest closeIndexRequest = new CloseIndexRequest(indexName);

		ActionFuture<AcknowledgedResponse> closeIndexResponseActionFuture =
			indicesAdminClient.close(closeIndexRequest);

		closeIndexResponseActionFuture.get();
	}

	protected void doUpdateSynonymSets(String indexName, Settings settings)
		throws ExecutionException, InterruptedException {

		IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

		try {
			closeIndex(indicesAdminClient, indexName);

			updateSettings(indicesAdminClient, indexName, settings);
		}
		finally {
			openIndex(indicesAdminClient, indexName);
		}
	}

	protected IndicesAdminClient getIndicesAdminClient() {
		Client client = elasticsearchClientResolver.getClient();

		AdminClient adminClient = client.admin();

		return adminClient.indices();
	}

	protected Settings getSettings(String indexName, String filterName) {
		IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

		GetSettingsRequestBuilder getSettingsRequestBuilder =
			indicesAdminClient.prepareGetSettings(indexName);

		getSettingsRequestBuilder.setNames(getSynonymSetsListPath(filterName));

		GetSettingsResponse getSettingsResponse =
			getSettingsRequestBuilder.get();

		ImmutableOpenMap<String, Settings> indexToSettings =
			getSettingsResponse.getIndexToSettings();

		return indexToSettings.get(indexName);
	}

	protected String getSynonymSetsListPath(String filterName) {
		return "index.analysis.filter." + filterName + ".synonyms";
	}

	protected void openIndex(
			IndicesAdminClient indicesAdminClient, String indexName)
		throws ExecutionException, InterruptedException {

		OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);

		ActionFuture<OpenIndexResponse> openIndexResponseActionFuture =
			indicesAdminClient.open(openIndexRequest);

		openIndexResponseActionFuture.get();
	}

	protected void updateSettings(
		IndicesAdminClient indicesAdminClient, String indexName,
		Settings settings) {

		UpdateSettingsRequestBuilder updateSettingsRequestBuilder =
			indicesAdminClient.prepareUpdateSettings(indexName);

		updateSettingsRequestBuilder.setSettings(settings);

		updateSettingsRequestBuilder.get();
	}

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected JSONFactory jsonFactory;

}