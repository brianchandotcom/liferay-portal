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
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.util.List;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
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

	public ValidationResult afterResourceStringProperty() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource: " + resourceStringProperty.getValue());
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
							null, resourceStringProperty.getStringValue());

					mainSchemaProperties.schema.setValue(schema);
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

			resourceStringProperty.setValue("");
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public void afterSiteFilterProperty() {
		resourceStringProperty.setValue("");

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public void afterWebSiteStringProperty() {
		resourceStringProperty.setValue("");

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public ValidationResult beforeResourceStringProperty() throws Exception {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() == Result.OK) {
				try {
					List<NamedThing> resourceNames = null;

					if (siteFilterProperty.getValue()) {
						resourceNames =
							liferaySourceOrSinkRuntime.getResourceList(
								webSiteStringProperty.getValue());
					}
					else {
						resourceNames =
							liferaySourceOrSinkRuntime.getSchemaNames(null);
					}

					if (resourceNames.isEmpty()) {
						validationResultMutable.setMessage(
							i18nMessages.getMessage(
								"error.validation.resources"));
						validationResultMutable.setStatus(Result.ERROR);
					}

					resourceStringProperty.setPossibleNamedThingValues(
						resourceNames);
				}
				catch (Exception e) {
					return ExceptionUtils.exceptionToValidationResult(e);
				}
			}

			return validationResultMutable;
		}
	}

	public ValidationResult beforeWebSiteStringProperty() {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() == Result.OK) {
				try {
					List<NamedThing> webSites =
						liferaySourceOrSinkRuntime.getAvailableWebSites();

					if (webSites.isEmpty()) {
						validationResultMutable.setMessage(
							i18nMessages.getMessage(
								"error.validation.websites"));
						validationResultMutable.setStatus(Result.ERROR);
					}

					webSiteStringProperty.setPossibleNamedThingValues(webSites);
				}
				catch (Exception e) {
					return ExceptionUtils.exceptionToValidationResult(e);
				}
			}

			return validationResultMutable;
		}
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return liferayConnectionProperties;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String formName = form.getName();

		if (formName.equals(Form.MAIN) || formName.equals(Form.REFERENCE)) {
			PropertiesUtils.setHidden(
				form, webSiteStringProperty, !siteFilterProperty.getValue());
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

		resourceSelectionForm.addRow(siteFilterProperty);

		Widget webSitesWidget = Widget.widget(webSiteStringProperty);

		webSitesWidget.setCallAfter(true);
		webSitesWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addRow(webSitesWidget);

		Widget resourcesWidget = Widget.widget(resourceStringProperty);

		resourcesWidget.setCallAfter(true);
		resourcesWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addRow(resourcesWidget);

		resourceSelectionForm.addRow(conditionProperty);

		Widget validateConditionWidget = Widget.widget(
			validateConditionPresentationItem);

		validateConditionWidget.setLongRunning(true);
		validateConditionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		resourceSelectionForm.addColumn(validateConditionWidget);

		refreshLayout(resourceSelectionForm);

		// Reference form

		Form referenceForm = Form.create(this, Form.REFERENCE);

		referenceForm.addRow(siteFilterProperty);

		Widget webSitesReferenceWidget = Widget.widget(webSiteStringProperty);

		webSitesReferenceWidget.setCallAfter(true);
		webSitesReferenceWidget.setLongRunning(true);
		webSitesReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(webSitesReferenceWidget);

		Widget resourcesReferenceWidget = Widget.widget(resourceStringProperty);

		resourcesReferenceWidget.setCallAfter(true);
		resourcesReferenceWidget.setLongRunning(true);
		resourcesReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(resourcesReferenceWidget);

		referenceForm.addRow(conditionProperty);

		Widget validateConditionReferenceWidget = Widget.widget(
			validateConditionPresentationItem);

		validateConditionReferenceWidget.setLongRunning(true);
		validateConditionReferenceWidget.setWidgetType(
			Widget.BUTTON_WIDGET_TYPE);

		referenceForm.addColumn(validateConditionReferenceWidget);

		referenceForm.addRow(mainSchemaProperties.getForm(Form.REFERENCE));

		refreshLayout(referenceForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		conditionProperty.setValue("");
		resourceStringProperty.setValue("");
		siteFilterProperty.setValue(false);
		webSiteStringProperty.setValue("");
	}

	public ValidationResult validateValidateConditionPresentationItem() {
		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(Result.OK);

		String endpointUrl =
			liferayConnectionProperties.endpointProperty.getValue();

		try {
			URIUtils.addQueryConditionToURL(
				endpointUrl, conditionProperty.getValue());
		}
		catch (Exception exception) {
			return ExceptionUtils.exceptionToValidationResult(exception);
		}

		return validationResultMutable;
	}

	public Property<String> conditionProperty = PropertyFactory.newString(
		"conditionProperty");
	public LiferayConnectionProperties liferayConnectionProperties;

	public SchemaProperties mainSchemaProperties =
		new SchemaProperties("mainSchemaProperties") {

			@SuppressWarnings("unused")
			public void afterSchema() {
				if (schemaListener != null) {
					schemaListener.afterSchema();
				}
			}

		};

	public StringProperty resourceStringProperty = PropertyFactory.newString(
		"resourceStringProperty");
	public ISchemaListener schemaListener;
	public Property<Boolean> siteFilterProperty = PropertyFactory.newBoolean(
		"siteFilterProperty");
	public transient PresentationItem validateConditionPresentationItem =
		new PresentationItem(
			"validateConditionPresentationItem", "Validate Condition");
	public StringProperty webSiteStringProperty = PropertyFactory.newString(
		"webSiteStringProperty");

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			LiferayResourceProperties.class);

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
				_log.debug("Using a reference connection properties");
				_log.debug(
					"User ID: " +
						referencedLiferayConnectionProperties.userIdProperty.
							getValue());
				_log.debug(
					"Endpoint: " +
						referencedLiferayConnectionProperties.endpointProperty.
							getValue());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"User ID: " +
					liferayConnectionProperties.userIdProperty.getValue());
			_log.debug(
				"Endpoint: " +
					liferayConnectionProperties.endpointProperty.getValue());
		}

		return liferayConnectionProperties;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}