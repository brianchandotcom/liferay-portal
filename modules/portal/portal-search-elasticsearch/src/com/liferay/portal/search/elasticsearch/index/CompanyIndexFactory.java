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

package com.liferay.portal.search.elasticsearch.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.util.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.ImmutableSettings;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"indexConfigFileName=/META-INF/index-settings.json",
		"typeMappings._default_=/META-INF/mappings/default-type-mappings.json",
		"typeMappings.KeywordQueryDocumentType=/META-INF/mappings/keyword-query-type-mappings.json",
		"typeMappings.LiferayDocumentType=/META-INF/mappings/liferay-type-mappings.json",
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

		if (Validator.isNotNull(_indexConfigFileName)) {
			ImmutableSettings.Builder builder =
				ImmutableSettings.settingsBuilder();

			Class<?> clazz = getClass();

			builder.classLoader(clazz.getClassLoader());

			builder.loadFromClasspath(_indexConfigFileName);

			createIndexRequestBuilder.setSettings(builder);
		}

		for (Map.Entry<String, String> entry : _typeMappings.entrySet()) {
			Class<?> clazz = getClass();

			String typeMapping = StringUtil.read(
				clazz.getClassLoader(), entry.getValue());

			createIndexRequestBuilder.addMapping(entry.getKey(), typeMapping);
		}

		Future<CreateIndexResponse> future =
			createIndexRequestBuilder.execute();

		CreateIndexResponse createIndexResponse = future.get();

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

		Future<DeleteIndexResponse> future =
			deleteIndexRequestBuilder.execute();

		DeleteIndexResponse deleteIndexResponse = future.get();

		LogUtil.logActionResponse(_log, deleteIndexResponse);
	}

	public void setIndexConfigFileName(String indexConfigFileName) {
		_indexConfigFileName = indexConfigFileName;
	}

	public void setTypeMappings(Map<String, String> typeMappings) {
		_typeMappings = typeMappings;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		setIndexConfigFileName(
			MapUtil.getString(properties, "indexConfigFileName"));

		Map<String, String> typeMappings = new HashMap<String, String>();

		for (String key : properties.keySet()) {
			if (key.startsWith(_PREFIX)) {
				String value = MapUtil.getString(properties, key);

				typeMappings.put(key.substring(_PREFIX.length()), value);
			}
		}

		setTypeMappings(typeMappings);
	}

	protected boolean hasIndex(
			IndicesAdminClient indicesAdminClient, long companyId)
		throws Exception {

		IndicesExistsRequestBuilder indicesExistsRequestBuilder =
			indicesAdminClient.prepareExists(String.valueOf(companyId));

		Future<IndicesExistsResponse> future =
			indicesExistsRequestBuilder.execute();

		IndicesExistsResponse indicesExistsResponse = future.get();

		return indicesExistsResponse.isExists();
	}

	private static final String _PREFIX = "typeMappings.";

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyIndexFactory.class);

	private String _indexConfigFileName;
	private Map<String, String> _typeMappings = new HashMap<String, String>();

}