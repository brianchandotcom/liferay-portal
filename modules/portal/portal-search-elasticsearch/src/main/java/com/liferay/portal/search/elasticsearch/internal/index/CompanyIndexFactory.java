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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.internal.util.LogUtil;
import com.liferay.portal.search.elasticsearch.settings.IndexSettingsContributor;

import java.io.InputStream;

import java.util.HashMap;
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
	immediate = true,
	property = {
		"indexConfigFileName=" + IndexSettingsConstants.FILE,
		"typeMappings." + LiferayTypeMappingsConstants.TYPE + "=" + LiferayTypeMappingsConstants.FILE,
		"typeMappings.KeywordQueryDocumentType=/META-INF/mappings/keyword-query-type-mappings.json",
		"typeMappings.SpellCheckDocumentType=/META-INF/mappings/spellcheck-type-mappings.json"
	}
)
public class CompanyIndexFactory implements IndexFactory {

	@Override
	public void createIndices(AdminClient adminClient, long companyId)
		throws Exception {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		if (hasIndex(indicesAdminClient, companyId)) {
			return;
		}

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(String.valueOf(companyId));

		addMappings(createIndexRequestBuilder);
		setSettings(createIndexRequestBuilder);

		CreateIndexResponse createIndexResponse =
			createIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, createIndexResponse);
	}

	@Override
	public void deleteIndices(AdminClient adminClient, long companyId)
		throws Exception {

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		if (!hasIndex(indicesAdminClient, companyId)) {
			return;
		}

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(String.valueOf(companyId));

		DeleteIndexResponse deleteIndexResponse =
			deleteIndexRequestBuilder.get();

		LogUtil.logActionResponse(_log, deleteIndexResponse);
	}

	public void setIndexConfigFileName(String indexConfigFileName) {
		_indexConfigFileName = indexConfigFileName;
	}

	public void setTypeMappings(Map<String, String> typeMappings) {
		_typeMappings = typeMappings;
	}

	protected static Map<String, String> getTypeMappings(
		Map<String, Object> properties) {

		Map<String, String> typeMappings = new HashMap<>();

		for (String key : properties.keySet()) {
			if (key.startsWith(_PREFIX)) {
				String value = MapUtil.getString(properties, key);

				typeMappings.put(key.substring(_PREFIX.length()), value);
			}
		}

		return typeMappings;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		ElasticsearchConfiguration elasticsearchConfiguration =
			Configurable.createConfigurable(
				ElasticsearchConfiguration.class, properties);

		setAdditionalIndexConfigurations(
			elasticsearchConfiguration.additionalIndexConfigurations());
		setIndexConfigFileName(
			MapUtil.getString(properties, "indexConfigFileName"));

		Map<String, String> typeMappings = getTypeMappings(properties);

		setTypeMappings(typeMappings);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.add(indexSettingsContributor);
	}

	protected void addMappings(
			CreateIndexRequestBuilder createIndexRequestBuilder)
		throws Exception {

		for (Map.Entry<String, String> entry : _typeMappings.entrySet()) {
			Class<?> clazz = getClass();

			String typeMapping = StringUtil.read(
				clazz.getClassLoader(), entry.getValue());

			createIndexRequestBuilder.addMapping(entry.getKey(), typeMapping);
		}
	}

	protected boolean hasIndex(
			IndicesAdminClient indicesAdminClient, long companyId)
		throws Exception {

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(String.valueOf(companyId));

		IndicesExistsResponse indicesExistsResponse =
			indicesExistsRequestBuilder.get();

		return indicesExistsResponse.isExists();
	}

	protected void loadAdditionalIndexConfigurations(Builder builder) {
		if (ArrayUtil.isEmpty(_additionalIndexConfigurations)) {
			return;
		}

		String source = StringUtil.merge(
			_additionalIndexConfigurations, StringPool.NEW_LINE);

		builder.loadFromSource(source);
	}

	protected void loadIndexConfigFile(Settings.Builder builder) {
		if (Validator.isNull(_indexConfigFileName)) {
			return;
		}

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			_indexConfigFileName);

		builder.loadFromStream(_indexConfigFileName, inputStream);
	}

	protected void loadIndexSettingsContributors(Settings.Builder builder) {
		for (IndexSettingsContributor indexSettingsContributor :
				_indexSettingsContributors) {

			indexSettingsContributor.populate(builder);
		}
	}

	protected void removeIndexSettingsContributor(
		IndexSettingsContributor indexSettingsContributor) {

		_indexSettingsContributors.remove(indexSettingsContributor);
	}

	protected void setAdditionalIndexConfigurations(
		String[] additionalIndexConfigurations) {

		_additionalIndexConfigurations = additionalIndexConfigurations;
	}

	protected void setSettings(
		CreateIndexRequestBuilder createIndexRequestBuilder) {

		Settings.Builder builder = Settings.settingsBuilder();

		loadIndexConfigFile(builder);

		loadAdditionalIndexConfigurations(builder);

		loadIndexSettingsContributors(builder);

		createIndexRequestBuilder.setSettings(builder);
	}

	private static final String _PREFIX = "typeMappings.";

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	private String[] _additionalIndexConfigurations;
	private String _indexConfigFileName;
	private final Set<IndexSettingsContributor> _indexSettingsContributors =
		new ConcurrentSkipListSet<>();
	private Map<String, String> _typeMappings = new HashMap<>();

}