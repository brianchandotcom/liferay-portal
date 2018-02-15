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

package com.liferay.portal.search.elasticsearch.internal.index;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch.settings.IndexSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.IndexSettingsHelper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true
)
public class CompanyIndexFactory implements IndexFactory {

	@Override
	public void createIndices(AdminClient adminClient, long companyId)
		throws Exception {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		String indexName = getIndexName(companyId);

		if (hasIndex(indicesAdminClient, indexName)) {
			return;
		}

		createIndex(indexName, indicesAdminClient);
	}

	@Override
	public void deleteIndices(AdminClient adminClient, long companyId)
		throws Exception {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		String indexName = getIndexName(companyId);

		if (!hasIndex(indicesAdminClient, indexName)) {
			return;
		}

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(indexName);

		DeleteIndexResponse deleteIndexResponse =
			deleteIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, deleteIndexResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ElasticsearchConfiguration elasticsearchConfiguration =
			ConfigurableUtil.createConfigurable(
				ElasticsearchConfiguration.class, properties);

		setAdditionalIndexConfigurations(
			elasticsearchConfiguration.additionalIndexConfigurations());
		setAdditionalTypeMappings(
			elasticsearchConfiguration.additionalTypeMappings());
		setOverrideTypeMappings(
			elasticsearchConfiguration.overrideTypeMappings());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeIndexSettingsContributor"
	)
	protected void addIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.add(indexSettingsContributor);
	}

	protected void addLiferayDocumentTypeMappings(
		CreateIndexRequestBuilder createIndexRequestBuilder,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(_overrideTypeMappings)) {
			liferayDocumentTypeFactory.createLiferayDocumentTypeMappings(
				createIndexRequestBuilder, _overrideTypeMappings);
		}
		else {
			liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
				createIndexRequestBuilder);
		}
	}

	protected void createIndex(
			String indexName, IndicesAdminClient indicesAdminClient)
		throws Exception {

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(indexName);

		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			new LiferayDocumentTypeFactory(indicesAdminClient, jsonFactory);

		setSettings(createIndexRequestBuilder, liferayDocumentTypeFactory);

		addLiferayDocumentTypeMappings(
			createIndexRequestBuilder, liferayDocumentTypeFactory);

		CreateIndexResponse createIndexResponse =
			createIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, createIndexResponse);

		updateLiferayDocumentType(indexName, liferayDocumentTypeFactory);
	}

	protected String getIndexName(long companyId) {
		return indexNameBuilder.getIndexName(companyId);
	}

	protected boolean hasIndex(
			IndicesAdminClient indicesAdminClient, String indexName)
		throws Exception {

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(indexName);

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestBuilder.get();

		return indicesExistsResponse.isExists();
	}

	protected void loadAdditionalIndexConfigurations(Builder builder) {
		if (Validator.isNull(_additionalIndexConfigurations)) {
			return;
		}

		builder.loadFromSource(_additionalIndexConfigurations);
	}

	protected void loadAdditionalTypeMappings(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNull(_additionalTypeMappings)) {
			return;
		}

		liferayDocumentTypeFactory.addTypeMappings(
			indexName, _additionalTypeMappings);
	}

	protected void loadIndexSettingsContributors(
		final Settings.Builder builder) {

		IndexSettingsHelper indexSettingsHelper = new IndexSettingsHelper() {

			@Override
			public void put(String setting, String value) {
				builder.put(setting, value);
			}

		};

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributors) {

			indexSettingsContributor.populate(indexSettingsHelper);
		}
	}

	protected void loadTypeMappingsContributors(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributors) {

			indexSettingsContributor.contribute(liferayDocumentTypeFactory);

			indexSettingsContributor.contribute(
				indexName, liferayDocumentTypeFactory);
		}
	}

	protected void removeIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.remove(indexSettingsContributor);
	}

	protected void setAdditionalIndexConfigurations(
		String additionalIndexConfigurations) {

		_additionalIndexConfigurations = additionalIndexConfigurations;
	}

	protected void setAdditionalTypeMappings(String additionalTypeMappings) {
		_additionalTypeMappings = additionalTypeMappings;
	}

	protected void setOverrideTypeMappings(String overrideTypeMappings) {
		_overrideTypeMappings = overrideTypeMappings;
	}

	protected void setSettings(
		CreateIndexRequestBuilder createIndexRequestBuilder,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		Settings.Builder builder = Settings.settingsBuilder();

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);

		loadAdditionalIndexConfigurations(builder);

		loadIndexSettingsContributors(builder);

		createIndexRequestBuilder.setSettings(builder);
	}

	protected void updateLiferayDocumentType(
		String indexName,
		LiferayDocumentTypeFactory liferayDocumentTypeFactory) {

		if (Validator.isNotNull(_overrideTypeMappings)) {
			return;
		}

		loadAdditionalTypeMappings(indexName, liferayDocumentTypeFactory);

		loadTypeMappingsContributors(indexName, liferayDocumentTypeFactory);

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(indexName);
	}

	@Reference
	protected IndexNameBuilder indexNameBuilder;

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	private volatile String _additionalIndexConfigurations;
	private String _additionalTypeMappings;
	private final Set<IndexSettingsContributor> _indexSettingsContributors =
		new ConcurrentSkipListSet<>();
	private String _overrideTypeMappings;

}