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

package com.liferay.headless.builder.internal.application.provider;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 * @authot Carlos Correa
 * @author Alejandro Tardín
 */
@Component(service = APIApplicationProvider.class)
public class APIApplicationProviderImpl implements APIApplicationProvider {

	@Override
	public APIApplication getAPIApplication(String baseURL, long companyId)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryHelper.getObjectEntry(
			companyId, "baseURL eq '" + baseURL + "'", "MSOD_API_APPLICATION");

		if (objectEntry == null) {
			throw new NotFoundException(
				"APIApplication with baseURL " + baseURL + " not found.");
		}

		List<APIApplication.Schema> schemas = _getSchemas(
			objectEntry, companyId);

		List<APIApplication.Endpoint> endpoints = _getEndpoints(
			objectEntry.getExternalReferenceCode(), companyId, schemas);

		Map<String, Object> properties = objectEntry.getProperties();

		return new APIApplication() {

			@Override
			public String getBaseURL() {
				return (String)properties.get("baseURL");
			}

			@Override
			public long getCompanyId() {
				return companyId;
			}

			@Override
			public String getDescription() {
				return (String)properties.get("description");
			}

			@Override
			public List<Endpoint> getEndpoints() {
				return endpoints;
			}

			@Override
			public String getOSGiJaxRsName() {
				return getTitle() + getCompanyId();
			}

			@Override
			public List<Schema> getSchemas() {
				return schemas;
			}

			@Override
			public String getTitle() {
				return (String)properties.get("title");
			}

			@Override
			public String getVersion() {
				return (String)properties.get("version");
			}

		};
	}

	private List<APIApplication.Endpoint> _getEndpoints(
			String apiApplicationExternalReferenceCode, long companyId,
			List<APIApplication.Schema> schemas)
		throws Exception {

		return TransformUtil.transform(
			_objectEntryHelper.getObjectEntries(
				companyId,
				"apiApplicationToAPIEndpoints/externalReferenceCode eq '" +
					apiApplicationExternalReferenceCode + "'",
				"MSOD_API_ENDPOINT"),
			objectEntry -> {
				Map<String, Object> properties = objectEntry.getProperties();

				return new APIApplication.Endpoint() {

					@Override
					public Http.Method getMethod() {
						ListEntry listEntry = (ListEntry)properties.get(
							"httpMethod");

						return Http.Method.valueOf(
							StringUtil.toUpperCase(listEntry.getKey()));
					}

					@Override
					public String getPath() {
						return (String)properties.get("path");
					}

					@Override
					public APIApplication.Schema getRequestSchema() {
						return _getSchema(
							(String)properties.get(
								"r_requestAPISchemaToAPIEndpoints_c_" +
									"apiSchemaERC"),
							schemas);
					}

					@Override
					public APIApplication.Schema getResponseSchema() {
						return _getSchema(
							(String)properties.get(
								"r_responseAPISchemaToAPIEndpoints_c_" +
									"apiSchemaERC"),
							schemas);
					}

					@Override
					public Scope getScope() {
						ListEntry listEntry = (ListEntry)properties.get(
							"scope");

						return Scope.valueOf(
							StringUtil.toUpperCase(listEntry.getKey()));
					}

				};
			});
	}

	private List<APIApplication.Property> _getProperties(
		Map<String, Object> schemaProperties, long companyId) {

		return TransformUtil.transformToList(
			(ObjectEntry[])schemaProperties.get("apiSchemaToAPIProperties"),
			propertyObjectEntry -> {
				Map<String, Object> properties =
					propertyObjectEntry.getProperties();

				String mainObjectDefinitionERC = MapUtil.getString(
					schemaProperties, "mainObjectDefinitionERC");

				properties.put(
					"mainObjectDefinitionERC", mainObjectDefinitionERC);

				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						getObjectDefinitionByExternalReferenceCode(
							mainObjectDefinitionERC, companyId);

				ObjectField objectField =
					_objectFieldLocalService.getObjectField(
						(String)properties.get("objectFieldERC"),
						objectDefinition.getObjectDefinitionId());

				return new APIApplication.Property() {

					@Override
					public String getDescription() {
						return (String)properties.get("description");
					}

					@Override
					public String getName() {
						return (String)properties.get("name");
					}

					@Override
					public String getType() {
						return objectField.getBusinessType();
					}

				};
			});
	}

	private APIApplication.Schema _getSchema(
		String externalReferenceCode, List<APIApplication.Schema> schemas) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		for (APIApplication.Schema schema : schemas) {
			if (StringUtil.equals(
					schema.getExternalReferenceCode(), externalReferenceCode)) {

				return schema;
			}
		}

		throw new IllegalStateException(
			"The schema with external reference code " + externalReferenceCode +
				" is not defined");
	}

	private List<APIApplication.Schema> _getSchemas(
			ObjectEntry apiApplicationObjectEntry, long companyId)
		throws Exception {

		return TransformUtil.transform(
			_objectEntryHelper.getObjectEntries(
				companyId,
				"apiApplicationToAPISchemas/externalReferenceCode eq '" +
					apiApplicationObjectEntry.getExternalReferenceCode() + "'",
				Arrays.asList("apiSchemaToAPIProperties"), "MSOD_API_SCHEMA"),
			objectEntry -> {
				Map<String, Object> properties = objectEntry.getProperties();

				return new APIApplication.Schema() {

					@Override
					public String getDescription() {
						return (String)properties.get("description");
					}

					@Override
					public String getExternalReferenceCode() {
						return objectEntry.getExternalReferenceCode();
					}

					@Override
					public String getName() {
						return (String)properties.get("name");
					}

					@Override
					public List<APIApplication.Property> getProperties() {
						return _getProperties(properties, companyId);
					}

				};
			});
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}