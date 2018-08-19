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
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
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
				null, getEffectiveLiferayConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setMessage(validationResult.getMessage());
			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					URI resourceURI = URIUtils.setPaginationLimitOnURL(
						resourceURL.getValue(), 1);

					String resourceCollectionType =
						liferaySourceOrSinkRuntime.getResourceCollectionType(
							resourceURI.toString());

					resource.setValue(resourceCollectionType);

					Schema schema =
						liferaySourceOrSinkRuntime.getResourceSchemaByType(
							resourceCollectionType);

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
				catch (NoSuchElementException nsee) {
					validationResultMutable.setMessage(
						i18nMessages.getMessage(
							"error.validation.resourceType"));
					validationResultMutable.setStatus(Result.ERROR);
				}
			}
		}

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			resource.setValue("");
			resourceURL.setValue("");
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public ValidationResult beforeResourceURL() throws Exception {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, getEffectiveLiferayConnectionProperties());

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());

			LiferayConnectionProperties liferayConnectionProperties =
				getEffectiveLiferayConnectionProperties();

			if (validationResultMutable.getStatus() == Result.OK) {
				try {
					List<NamedThing> resourceNames = null;

					if (liferayConnectionProperties.siteFilter.getValue()) {
						resourceNames =
							liferaySourceOrSinkRuntime.getResourceList(
								liferayConnectionProperties.webSiteURL.
									getValue());
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

					resourceURL.setPossibleNamedThingValues(resourceNames);
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
		return connection;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
	}

	public void setSchemaListener(ISchemaListener schemaListener) {
		this.schemaListener = schemaListener;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Special property settings

		resourceURL.setRequired();

		// Forms

		_setupMainForm();
		_setupReferenceForm();
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		condition.setValue("");
		resource.setValue("");
		resourceURL.setValue("");
	}

	public ValidationResult validateValidateCondition() {
		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(Result.OK);

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveLiferayConnectionProperties();

		String endpointUrl = liferayConnectionProperties.endpoint.getValue();

		try {
			URIUtils.addQueryConditionToURL(endpointUrl, condition.getValue());
		}
		catch (Exception exception) {
			return ExceptionUtils.exceptionToValidationResult(exception);
		}

		return validationResultMutable;
	}

	public Property<String> condition = PropertyFactory.newString("condition");
	public LiferayConnectionProperties connection;

	public SchemaProperties main = new SchemaProperties("main") {

		@SuppressWarnings("unused")
		public void afterSchema() {
			if (schemaListener != null) {
				schemaListener.afterSchema();
			}
		}

	};

	public Property<String> resource = PropertyFactory.newString(
		"resource").setRequired();
	public StringProperty resourceURL = PropertyFactory.newString(
		"resourceURL");
	public ISchemaListener schemaListener;
	public transient PresentationItem validateCondition = new PresentationItem(
		"validateCondition");

	protected LiferayConnectionProperties
		getEffectiveLiferayConnectionProperties() {

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

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayResourceProperties.class);
	}

	private void _setupMainForm() {
		Form resourceSelectionForm = Form.create(this, Form.MAIN);

		Widget resourceURLWidget = Widget.widget(resourceURL);

		resourceURLWidget.setCallAfter(true);
		resourceURLWidget.setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		resourceSelectionForm.addRow(resourceURLWidget);

		Widget resourceWidget = Widget.widget(resource);

		resourceWidget.setReadonly(true);

		resourceSelectionForm.addColumn(resourceWidget);

		resourceSelectionForm.addRow(condition);

		Widget validateConditionWidget = Widget.widget(validateCondition);

		validateConditionWidget.setLongRunning(true);
		validateConditionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		resourceSelectionForm.addColumn(validateConditionWidget);

		refreshLayout(resourceSelectionForm);
	}

	private void _setupReferenceForm() {
		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget resourceURLReferenceWidget = Widget.widget(resourceURL);

		resourceURLReferenceWidget.setCallAfter(true);
		resourceURLReferenceWidget.setLongRunning(true);
		resourceURLReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(resourceURLReferenceWidget);

		Widget resourceReferenceWidget = Widget.widget(resource);

		resourceReferenceWidget.setReadonly(true);

		referenceForm.addColumn(resourceReferenceWidget);

		referenceForm.addRow(condition);

		Widget validateConditionReferenceWidget = Widget.widget(
			validateCondition);

		validateConditionReferenceWidget.setLongRunning(true);
		validateConditionReferenceWidget.setWidgetType(
			Widget.BUTTON_WIDGET_TYPE);

		referenceForm.addColumn(validateConditionReferenceWidget);

		referenceForm.addRow(main.getForm(Form.REFERENCE));

		refreshLayout(referenceForm);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}