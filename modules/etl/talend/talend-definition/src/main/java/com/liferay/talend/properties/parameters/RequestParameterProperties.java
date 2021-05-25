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

package com.liferay.talend.properties.parameters;

import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.TypeLiteral;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class RequestParameterProperties extends ComponentPropertiesImpl {

	public RequestParameterProperties(String name) {
		super(name);
	}

	public void addParameters(List<OASParameter> oasParameters) {
		if (oasParameters.isEmpty()) {
			return;
		}

		List<String> parameterLocations = parameterLocationColumn.getValue();
		List<String> parameterNames = parameterNameColumn.getValue();
		List<String> parameterValues = parameterValueColumn.getValue();

		for (OASParameter oasParameter : oasParameters) {
			String name = oasParameter.getName();

			if (Objects.equals(name, "page") ||
				Objects.equals(name, "pageSize")) {

				continue;
			}

			if (oasParameter.isRequired() || oasParameter.isLocationPath()) {
				name = name + "*";
			}

			parameterNames.add(name);

			OASParameter.Location location = oasParameter.getLocation();

			parameterLocations.add(StringUtil.toLowerCase(location.toString()));

			parameterValues.add("");
		}
	}

	public List<RequestParameter> getProxyRequestParameters() {
		List<RequestParameter> requestParameters = getRequestParameters();

		Stream<RequestParameter> stream = requestParameters.stream();

		return stream.filter(
			requestParameter -> {
				if (Objects.equals(requestParameter.getLocation(), "header") &&
					(Objects.equals(
						requestParameter.getName(), "proxyIdentityId") ||
					 Objects.equals(
						 requestParameter.getName(), "proxyIdentitySecret"))) {

					return true;
				}

				return false;
			}
		).collect(
			Collectors.toList()
		);
	}

	public List<RequestParameter> getRequestParameters() {
		if (isEmpty()) {
			return Collections.emptyList();
		}

		List<String> parameterLocations = parameterLocationColumn.getValue();
		List<String> parameterNames = parameterNameColumn.getValue();
		List<String> parameterValues = parameterValueColumn.getValue();

		List<RequestParameter> requestParameters = new ArrayList<>();

		for (int i = 0; i < parameterLocations.size(); i++) {
			String parameterValue = parameterValues.get(i);

			if (StringUtil.isEmpty(parameterValue)) {
				continue;
			}

			requestParameters.add(
				new RequestParameter(
					parameterLocations.get(i), parameterNames.get(i),
					parameterValue.trim()));
		}

		return requestParameters;
	}

	public boolean isEmpty() {
		List<String> parameterValues = parameterValueColumn.getValue();

		if (parameterValues.isEmpty()) {
			return true;
		}

		for (String parameterValue : parameterValues) {
			if (!StringUtil.isEmpty(parameterValue)) {
				return false;
			}
		}

		return true;
	}

	public void removeAll() {
		parameterLocationColumn.setValue(new ArrayList<>());
		parameterNameColumn.setValue(new ArrayList<>());
		parameterValueColumn.setValue(new ArrayList<>());
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addColumn(parameterLocationColumn);
		mainForm.addColumn(parameterNameColumn);
		mainForm.addColumn(parameterValueColumn);
	}

	@Override
	public void setupProperties() {
		parameterLocationColumn.setTaggedValue(_ADD_QUOTES, true);
		parameterLocationColumn.setValue(new ArrayList<>());

		parameterNameColumn.setTaggedValue(_ADD_QUOTES, true);
		parameterNameColumn.setValue(new ArrayList<>());

		parameterValueColumn.setTaggedValue(_ADD_QUOTES, false);
		parameterValueColumn.setValue(new ArrayList<>());
	}

	public Property<List<String>> parameterLocationColumn =
		PropertyFactory.newProperty(
			_LIST_STRING_TYPE, "parameterLocationColumn");
	public Property<List<String>> parameterNameColumn =
		PropertyFactory.newProperty(_LIST_STRING_TYPE, "parameterNameColumn");
	public Property<List<String>> parameterValueColumn =
		PropertyFactory.newProperty(_LIST_STRING_TYPE, "parameterValueColumn");

	private static final String _ADD_QUOTES = "ADD_QUOTES";

	private static final TypeLiteral<List<String>> _LIST_STRING_TYPE =
		new TypeLiteral<List<String>>() {
		};

	private static final long serialVersionUID = 3473102423403696544L;

}