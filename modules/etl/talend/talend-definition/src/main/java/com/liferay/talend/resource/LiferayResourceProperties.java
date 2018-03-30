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

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.utils.PropertiesUtils;

import java.io.IOException;

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
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferayResourceProperties
	extends PropertiesImpl implements LiferayConnectionPropertiesProvider {

	public LiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterResourceURL() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource URL: " + resourceURL.getValue());
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(Result.OK);

		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setMessage(validationResult.getMessage());
			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					Schema schema =
						liferaySourceOrSinkRuntime.getEndpointSchema(
							null, resourceURL.getStringValue());

					main.schema.setValue(schema);
				}
				catch (IOException ioe) {
					validationResult =
						ExceptionUtils.exceptionToValidationResult(ioe);

					validationResultMutable.setMessage(
						validationResult.getMessage());
					validationResultMutable.setStatus(
						validationResult.getStatus());
				}
			}
		}

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			resourceURL.setValue("");
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public void afterUseWebSiteRelatedResource() {
		resourceURL.setValue("");

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public void afterWebSiteURL() {
		resourceURL.setValue("");

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public ValidationResult beforeResourceURL() throws Exception {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			if (validationResult.getStatus() == ValidationResult.Result.OK) {
				try {
					List<NamedThing> moduleNames = null;

					if (useWebSiteRelatedResource.getValue()) {
						moduleNames =
							liferaySourceOrSinkRuntime.getResourceList(
								webSiteURL.getValue());
					}
					else {
						moduleNames = liferaySourceOrSinkRuntime.getSchemaNames(
							null);
					}

					resourceURL.setPossibleNamedThingValues(moduleNames);
				}
				catch (Exception e) {
					return ExceptionUtils.exceptionToValidationResult(e);
				}
			}
			else {
				return validationResult;
			}

			return ValidationResult.OK;
		}
	}

	public ValidationResult beforeWebSiteURL() {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			if (validationResult.getStatus() == ValidationResult.Result.OK) {
				try {
					List<NamedThing> webSites =
						liferaySourceOrSinkRuntime.getAvailableWebSites();

					webSiteURL.setPossibleNamedThingValues(webSites);
				}
				catch (Exception e) {
					return ExceptionUtils.exceptionToValidationResult(e);
				}
			}
			else {
				return validationResult;
			}

			return ValidationResult.OK;
		}
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String formName = form.getName();

		if (formName.equals(Form.MAIN) || formName.equals(Form.REFERENCE)) {
			PropertiesUtils.setHidden(
				form, webSiteURL, !useWebSiteRelatedResource.getValue());
		}
	}

	public void setSchemaListener(ISchemaListener schemaListener) {
		this.schemaListener = schemaListener;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Main form

		Form resourceSelectionForm = Form.create(this, Form.MAIN);

		resourceSelectionForm.addRow(useWebSiteRelatedResource);

		Widget webSitesWidget = Widget.widget(webSiteURL);

		webSitesWidget.setCallAfter(true);
		webSitesWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addColumn(webSitesWidget);

		Widget resourcesWidget = Widget.widget(resourceURL);

		resourcesWidget.setCallAfter(true);
		resourcesWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addRow(resourcesWidget);

		refreshLayout(resourceSelectionForm);

		// Reference form

		Form referenceForm = Form.create(this, Form.REFERENCE);

		referenceForm.addRow(useWebSiteRelatedResource);

		Widget webSitesReferenceWidget = Widget.widget(webSiteURL);

		webSitesReferenceWidget.setCallAfter(true);
		webSitesReferenceWidget.setLongRunning(true);
		webSitesReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addColumn(webSitesReferenceWidget);

		Widget resourcesReferenceWidget = Widget.widget(resourceURL);

		resourcesReferenceWidget.setCallAfter(true);
		resourcesReferenceWidget.setLongRunning(true);
		resourcesReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(resourcesReferenceWidget);

		referenceForm.addRow(main.getForm(Form.REFERENCE));

		refreshLayout(referenceForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		resourceURL.setValue("");
		useWebSiteRelatedResource.setValue(false);
		webSiteURL.setValue("");
	}

	public LiferayConnectionProperties connection;

	public SchemaProperties main = new SchemaProperties("main") {

		@SuppressWarnings("unused")
		public void afterSchema() {
			if (schemaListener != null) {
				schemaListener.afterSchema();
			}
		}

	};

	public StringProperty resourceURL = PropertyFactory.newString(
		"resourceURL");
	public ISchemaListener schemaListener;
	public Property<Boolean> useWebSiteRelatedResource =
		PropertyFactory.newBoolean("useWebSiteRelatedResource");
	public StringProperty webSiteURL = PropertyFactory.newString("webSiteURL");

	private LiferayConnectionProperties _getEffectiveConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			getLiferayConnectionProperties();

		if (liferayConnectionProperties == null) {
			_log.error("LiferayConnectionProperties is null");
		}

		LiferayConnectionProperties referencedLiferayConnectionProperties =
			liferayConnectionProperties.getReferencedConnectionProperties();

		if (referencedLiferayConnectionProperties != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Using a reference connection properties.");
				_log.debug(
					"User ID: " +
						referencedLiferayConnectionProperties.userId.
							getValue());
				_log.debug(
					"Endpoint: " +
						referencedLiferayConnectionProperties.endpoint.
							getValue());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"User ID: " + liferayConnectionProperties.userId.getValue());
			_log.debug(
				"Endpoint: " + liferayConnectionProperties.endpoint.getValue());
		}

		return liferayConnectionProperties;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}