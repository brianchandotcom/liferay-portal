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

package com.liferay.talend.resource;

import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.common.util.StringUtil;

import java.net.URI;

import java.util.Set;

import javax.json.JsonObject;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Zoltán Takács
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LiferayInputResourceProperties
	extends BaseLiferayResourceProperties {

	public LiferayInputResourceProperties(String name) {
		super(name);
	}

	public void afterParametersTable() {
		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Parameters: " + parametersTable.valueColumnName.getValue());
		}
	}

	public void setupLayout() {
		super.setupLayout();

		Form referenceForm = getForm(Form.REFERENCE);

		referenceForm.addRow(includeFields);
		referenceForm.addRow(includeFieldsParameters);
	}

	public Property<String> includeFields = new StringProperty("includeFields");
	public Property<String> includeFieldsParameters = new StringProperty(
		"includeFieldsParameters");

	@Override
	protected URI buildEndpointURI(UriBuilder uriBuilder) {
		_addNestedFields(uriBuilder);
		_addNestedFieldsParameters(uriBuilder);

		return super.buildEndpointURI(uriBuilder);
	}

	@Override
	protected ValidationResult doAfterEndpoint(OASSource oasSource) {
		if (_logger.isDebugEnabled()) {
			_logger.debug("Endpoint: " + endpoint.getValue());
		}

		JsonObject oasJsonObject = oasSource.getOASJsonObject();

		try {
			SchemaBuilder schemaBuilder = new SchemaBuilder();

			Schema endpointSchema = schemaBuilder.inferSchema(
				endpoint.getValue(), OASConstants.OPERATION_GET, oasJsonObject);

			main.schema.setValue(endpointSchema);
		}
		catch (TalendRuntimeException tre) {
			_logger.error("Unable to generate schema", tre);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				getI18nMessage("error.validation.schema"));
		}

		OASExplorer oasExplorer = new OASExplorer();

		populateParametersTable(
			oasExplorer.getParameters(
				endpoint.getValue(), OASConstants.OPERATION_GET,
				oasJsonObject));

		return ValidationResult.OK;
	}

	@Override
	protected Set<String> getEndpoints(OASSource oasSource) {
		OASExplorer oasExplorer = new OASExplorer();

		return oasExplorer.getEndpointList(
			oasSource.getOASJsonObject(), OASConstants.OPERATION_GET);
	}

	private void _addNestedFields(UriBuilder uriBuilder) {
		String includeFieldsValue = includeFields.getValue();

		if (!StringUtil.isEmpty(includeFieldsValue)) {
			uriBuilder.queryParam("nestedFields", includeFieldsValue);
		}
	}

	private void _addNestedFieldsParameters(UriBuilder uriBuilder) {
		String includeFieldsParametersValue =
			includeFieldsParameters.getValue();

		if (!StringUtil.isEmpty(includeFieldsParametersValue)) {
			String[] includeFieldsParameters =
				includeFieldsParametersValue.split(",");

			for (String includeFieldsParameter : includeFieldsParameters) {
				String[] parameterNameValue = includeFieldsParameter.split("=");

				uriBuilder.queryParam(
					parameterNameValue[0], parameterNameValue[1]);
			}
		}
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayInputResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}