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

package com.liferay.consumer.talend.resource;

import com.liferay.consumer.talend.LiferayBaseComponentDefinition;
import com.liferay.consumer.talend.connection.LiferayConnectionProperties;
import com.liferay.consumer.talend.connection.LiferayProvideConnectionProperties;
import com.liferay.consumer.talend.exception.ExceptionUtils;
import com.liferay.consumer.talend.runtime.LiferaySourceOrSinkRuntime;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferayResourceProperties
	extends PropertiesImpl implements LiferayProvideConnectionProperties {

	public LiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterResourceURL() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource URL: " + resourceURL.getValue());
		}

		ValidationResultMutable vr =
			new ValidationResultMutable().setStatus(Result.OK);

		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCEORSINK_CLASS)) {

			LiferaySourceOrSinkRuntime ss =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			ss.initialize(null, _getEffectiveConnectionProperties());

			ValidationResult v = ss.validate(null);

			vr.setStatus(v.getStatus());
			vr.setMessage(v.getMessage());

			if (vr.getStatus() == ValidationResult.Result.OK) {
				try {
					Schema schema = ss.getEndpointSchema(
						null, resourceURL.getStringValue());

					main.schema.setValue(schema);
				}
				catch (IOException ioe) {
					v = ExceptionUtils.exceptionToValidationResult(ioe);

					vr.setStatus(v.getStatus());
					vr.setMessage(v.getMessage());
				}
			}
		}

		if (vr.getStatus() == ValidationResult.Result.ERROR) {
			resourceURL.setPossibleValues(Collections.emptyList());
		}

		return vr;
	}

	public ValidationResult beforeResourceURL() throws Exception {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCEORSINK_CLASS)) {

			resourceURL.setPossibleValues(Collections.emptyList());

			LiferaySourceOrSinkRuntime ss =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			ss.initialize(null, _getEffectiveConnectionProperties());

			ValidationResult vr = ss.validate(null);

			if (vr.getStatus() == ValidationResult.Result.OK) {
				try {
					List<NamedThing> moduleNames = ss.getSchemaNames(null);

					resourceURL.setPossibleNamedThingValues(moduleNames);
				}
				catch (Exception ex) {
					return ExceptionUtils.exceptionToValidationResult(ex);
				}
			}
			else {
				return vr;
			}

			return ValidationResult.OK;
		}
	}

	@Override
	public LiferayConnectionProperties getConnectionProperties() {
		return connection;
	}

	public void setSchemaListener(ISchemaListener schemaListener) {
		this.schemaListener = schemaListener;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form resourceSelectionForm = Form.create(this, Form.MAIN);

		Widget resourcesWidget = Widget.widget(resourceURL);

		resourcesWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addRow(resourcesWidget);

		refreshLayout(resourceSelectionForm);

		Form moduleRefForm = Form.create(this, Form.REFERENCE);

		Widget resourcesWidgetLong = Widget.widget(resourceURL);

		resourcesWidgetLong.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);
		resourcesWidgetLong.setLongRunning(true);

		moduleRefForm.addRow(resourcesWidgetLong);

		moduleRefForm.addRow(main.getForm(Form.REFERENCE));

		refreshLayout(moduleRefForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		resourceURL.setValue("");
	}

	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");

	public SchemaProperties main = new SchemaProperties("main") {

		public void afterSchema() {
			if (schemaListener != null) {
				schemaListener.afterSchema();
			}
		}

	};

	public StringProperty resourceURL =
		PropertyFactory.newString("resourceURL"); //$NON-NLS-1$

	public ISchemaListener schemaListener;

	private LiferayConnectionProperties _getEffectiveConnectionProperties() {
		LiferayConnectionProperties connProps = getConnectionProperties();

		if (connProps == null) {
			_log.error("LiferayConnectionProperties is null");
		}

		LiferayConnectionProperties referenceProps =
			connProps.getReferencedConnectionProperties();

		if (referenceProps != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Using a reference connection properties.");
				_log.debug("UserID: " + referenceProps.userId.getValue());
				_log.debug("Endpoint: " + referenceProps.endpoint.getValue());
			}

			return referenceProps;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("UserID: " + connProps.userId.getValue());
			_log.debug("Endpoint: " + connProps.endpoint.getValue());
		}

		return connProps;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}