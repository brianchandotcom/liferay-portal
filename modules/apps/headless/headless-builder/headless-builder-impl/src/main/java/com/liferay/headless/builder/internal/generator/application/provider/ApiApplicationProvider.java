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

package com.liferay.headless.builder.internal.generator.application.provider;

import com.liferay.headless.builder.internal.generator.application.ApiApplication;
import com.liferay.headless.builder.internal.generator.application.Endpoint;
import com.liferay.headless.builder.internal.generator.application.Property;
import com.liferay.headless.builder.internal.generator.application.Schema;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiApplicationModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiEndpointModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ApiSchemaModel;
import com.liferay.headless.builder.internal.generator.consumer.model.ModelProvider;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ApiApplicationProvider.class)
public class ApiApplicationProvider {

	public ApiApplication getApiApplication(String apiApplicationERC)
		throws Exception {

		ApiApplicationModel apiApplicationModel = _modelProvider.getModel(
			apiApplicationERC, ApiApplicationModel.class);

		return _getApiApplication(apiApplicationModel);
	}

	public ApiApplication getApiApplicationByBaseURL(
			String apiApplicationBaseURL)
		throws Exception {

		ApiApplicationModel apiApplicationModel =
			_modelProvider.getModelByApiApplicationBaseURL(
				apiApplicationBaseURL, ApiApplicationModel.class);

		return _getApiApplication(apiApplicationModel);
	}

	private ApiApplication _getApiApplication(
			ApiApplicationModel apiApplicationModel)
		throws Exception {

		ApiApplication.Builder builder = new ApiApplication.Builder();

		List<Schema> schemas = _getSchemas(
			apiApplicationModel.getExternalReferenceCode());

		return builder.setBaseURL(
			apiApplicationModel.getBaseURL()
		).setCompanyId(
			apiApplicationModel.getCompanyId()
		).setDescription(
			apiApplicationModel.getDescription()
		).setEndpoints(
			_getEndpoints(
				apiApplicationModel.getExternalReferenceCode(), schemas)
		).setOsgiJaxRsName(
			apiApplicationModel.getOsgiJaxRsName()
		).setSchemas(
			schemas
		).setTitle(
			apiApplicationModel.getTitle()
		).setVersion(
			apiApplicationModel.getVersion()
		).build();
	}

	private List<Endpoint> _getEndpoints(
			String apiApplicationERC, List<Schema> schemas)
		throws Exception {

		return TransformUtil.transform(
			_modelProvider.getModels(apiApplicationERC, ApiEndpointModel.class),
			apiEndpointModel -> new Endpoint.Builder(
			).setMethod(
				apiEndpointModel.getMethod()
			).setPath(
				apiEndpointModel.getPath()
			).setRequestSchema(
				_getSchema(apiEndpointModel.getRequestSchemaERC(), schemas)
			).setResponseSchema(
				_getSchema(apiEndpointModel.getResponseSchemaERC(), schemas)
			).setScope(
				apiEndpointModel.getScope()
			).build());
	}

	private Schema _getSchema(
		String externalReferenceCode, List<Schema> schemas) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		for (Schema schema : schemas) {
			if (StringUtil.equals(
					schema.getExternalReferenceCode(), externalReferenceCode)) {

				return schema;
			}
		}

		throw new IllegalStateException(
			"The schema with external reference code " + externalReferenceCode +
				" is not defined");
	}

	private List<Schema> _getSchemas(String apiApplicationERC)
		throws Exception {

		return TransformUtil.transform(
			_modelProvider.getModels(apiApplicationERC, ApiSchemaModel.class),
			apiSchemaModel -> new Schema.Builder(
			).setDescription(
				apiSchemaModel.getDescription()
			).setExternalReferenceCode(
				apiSchemaModel.getExternalReferenceCode()
			).setName(
				apiSchemaModel.getName()
			).setProperties(
				TransformUtil.transform(
					apiSchemaModel.getApiPropertyModels(),
					apiSchemaProperty -> new Property.Builder(
					).setDescription(
						apiSchemaProperty.getDescription()
					).setName(
						apiSchemaProperty.getName()
					).setType(
						apiSchemaProperty.getType()
					).build())
			).build());
	}

	@Reference
	private ModelProvider _modelProvider;

}