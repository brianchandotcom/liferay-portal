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
import com.liferay.talend.runtime.ValidatedSoSSandboxRuntime;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Zoltán Takács
 */
public class LiferayResourceProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public LiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterEndpoint() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Endpoint: " + endpoint.getValue());
		}

		ValidatedSoSSandboxRuntime validatedSoSSandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getEffectiveLiferayConnectionProperties());

		ValidationResultMutable validationResultMutable =
			validatedSoSSandboxRuntime.getValidationResultMutable();

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			return validationResultMutable;
		}

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime();

		try {
			Schema endpointSchema =
				liferaySourceOrSinkRuntime.getEndpointSchema(
					endpoint.getValue(), HttpMethod.GET);

			main.schema.setValue(endpointSchema);
		}
		catch (IOException | TalendRuntimeException e) {
			validationResultMutable.setMessage(
				i18nMessages.getMessage("error.validation.schema"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_log.error("Unable to generate schema", e);
		}

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			endpoint.setValue(null);
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public ValidationResult beforeEndpoint() throws Exception {
		ValidatedSoSSandboxRuntime validatedSoSSandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getEffectiveLiferayConnectionProperties());

		ValidationResultMutable validationResultMutable =
			validatedSoSSandboxRuntime.getValidationResultMutable();

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			return validationResultMutable;
		}

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime();

		try {
			Set<String> endpoints = liferaySourceOrSinkRuntime.getEndpointList(
				HttpMethod.GET);

			if (endpoints.isEmpty()) {
				validationResultMutable.setMessage(
					i18nMessages.getMessage("error.validation.resources"));
				validationResultMutable.setStatus(
					ValidationResult.Result.ERROR);

				return validationResultMutable;
			}

			List<NamedThing> endpointsNamedThing = new ArrayList<>();

			endpoints.forEach(
				endpoint -> endpointsNamedThing.add(
					new SimpleNamedThing(endpoint, endpoint)));

			endpoint.setPossibleNamedThingValues(endpointsNamedThing);
		}
		catch (Exception e) {
			return ExceptionUtils.exceptionToValidationResult(e);
		}

		return null;
	}

	public URI getEndpointURI() {
		String applicationBaseHref = connection.getApplicationBaseHref();

		String endpointHref = applicationBaseHref.concat(endpoint.getValue());

		UriBuilder uriBuilder = UriBuilder.fromPath(endpointHref);

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveLiferayConnectionProperties();

		uriBuilder.resolveTemplate(
			_SITE_ID, liferayConnectionProperties.siteId.getValue());

		return uriBuilder.build();
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection;
	}

	public void setSchemaListener(ISchemaListener schemaListener) {
		this.schemaListener = schemaListener;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Special property settings

		endpoint.setRequired();

		// Forms

		_setupMainForm();
		_setupReferenceForm();
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		condition.setValue("");
		endpoint.setValue(null);
	}

	public ValidationResult validateValidateCondition() {
		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable();

		validationResultMutable.setStatus(ValidationResult.Result.OK);

		LiferayConnectionProperties liferayConnectionProperties =
			getEffectiveLiferayConnectionProperties();

		String endpointUrl = liferayConnectionProperties.apiSpecURL.getValue();

		try {
			URIUtils.addQueryConditionToURL(endpointUrl, condition.getValue());
		}
		catch (Exception exception) {
			return ExceptionUtils.exceptionToValidationResult(exception);
		}

		return validationResultMutable;
	}

	public Property<String> condition = PropertyFactory.newString("condition");
	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public StringProperty endpoint = new StringProperty("endpoint");

	public SchemaProperties main = new SchemaProperties("main") {

		@SuppressWarnings("unused")
		public void afterSchema() {
			if (schemaListener != null) {
				schemaListener.afterSchema();
			}
		}

	};

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
					"API Spec URL: " +
						referencedLiferayConnectionProperties.apiSpecURL.
							getValue());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"User ID: " + liferayConnectionProperties.userId.getValue());
			_log.debug(
				"API Spec URL: " +
					liferayConnectionProperties.apiSpecURL.getValue());
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
		Form endpointSelectionForm = Form.create(this, Form.MAIN);

		Widget endpointPropertyWidget = Widget.widget(endpoint);

		endpointPropertyWidget.setCallAfter(true);
		endpointPropertyWidget.setWidgetType(
			Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		endpointSelectionForm.addRow(endpointPropertyWidget);

		endpointSelectionForm.addRow(condition);

		Widget validateConditionWidget = Widget.widget(validateCondition);

		validateConditionWidget.setLongRunning(true);
		validateConditionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		endpointSelectionForm.addColumn(validateConditionWidget);

		refreshLayout(endpointSelectionForm);
	}

	private void _setupReferenceForm() {
		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget endpointReferenceWidget = Widget.widget(endpoint);

		endpointReferenceWidget.setCallAfter(true);
		endpointReferenceWidget.setLongRunning(true);
		endpointReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(endpointReferenceWidget);

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

	private static final String _SITE_ID = "siteId";

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private static final long serialVersionUID = 6834821457406101745L;

}