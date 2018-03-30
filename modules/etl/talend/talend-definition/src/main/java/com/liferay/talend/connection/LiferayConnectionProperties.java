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

package com.liferay.talend.connection;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.utils.PropertiesUtils;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.service.Repository;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public static final String FORM_WIZARD = "Wizard";

	public LiferayConnectionProperties(String name) {
		super(name);
	}

	public void afterAnonymousLogin() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(FORM_WIZARD));
	}

	public ValidationResult afterFormFinishWizard(Repository<Properties> repo) {
		try (SandboxedInstance sandboxedInstance =
				getRuntimeSandboxedInstance()) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, this);

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validateConnection(this);

			if (validationResult.getStatus() != ValidationResult.Result.OK) {
				return validationResult;
			}

			repo.storeProperties(
				this, this.name.getValue(), _repositoryLocation, null);

			return ValidationResult.OK;
		}
		catch (Exception e) {
			return new ValidationResult(Result.ERROR, e.getMessage());
		}
	}

	public void afterReferencedComponent() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return this;
	}

	public String getReferencedComponentId() {
		return referencedComponent.componentInstanceId.getStringValue();
	}

	public LiferayConnectionProperties getReferencedConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			referencedComponent.getReference();

		if (liferayConnectionProperties != null) {
			return liferayConnectionProperties;
		}

		_log.error(
			"Connection has a reference to '{}' but the referenced Object is " +
				"null",
			getReferencedComponentId());

		return null;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String referencedComponentId = getReferencedComponentId();

		boolean useOtherConnection = false;

		if ((referencedComponentId != null) &&
			referencedComponentId.startsWith(
				TLiferayConnectionDefinition.COMPONENT_NAME)) {

			useOtherConnection = true;
		}

		String formName = form.getName();

		if (formName.equals(Form.MAIN) || formName.equals(FORM_WIZARD)) {
			PropertiesUtils.setHidden(form, endpoint, useOtherConnection);
			PropertiesUtils.setHidden(form, userId, useOtherConnection);
			PropertiesUtils.setHidden(form, password, useOtherConnection);
			PropertiesUtils.setHidden(form, anonymousLogin, useOtherConnection);

			if (!useOtherConnection && anonymousLogin.getValue()) {
				PropertiesUtils.setHidden(form, userId, true);
				PropertiesUtils.setHidden(form, password, true);
			}
		}
	}

	public LiferayConnectionProperties setRepositoryLocation(String location) {
		_repositoryLocation = location;

		return this;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Wizard form

		Form wizardForm = Form.create(this, FORM_WIZARD);

		wizardForm.addRow(name);

		wizardForm.addRow(endpoint);

		wizardForm.addRow(userId);

		wizardForm.addColumn(password);

		wizardForm.addRow(anonymousLogin);

		Widget testConnectionWidget = Widget.widget(testConnection);

		testConnectionWidget.setLongRunning(true);
		testConnectionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(testConnectionWidget);

		// Main form

		Form mainForm = Form.create(this, Form.MAIN);

		mainForm.addRow(endpoint);

		mainForm.addRow(userId);

		mainForm.addColumn(password);

		mainForm.addRow(anonymousLogin);

		// A form for a reference to a connection, used in a tLiferayInput
		// for example

		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget referencedComponentWidget = Widget.widget(referencedComponent);

		referencedComponentWidget.setWidgetType(
			Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(referencedComponentWidget);

		referenceForm.addRow(mainForm);

		refreshLayout(referenceForm);

		// Advanced form

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(connectTimeout);

		advancedForm.addColumn(readTimeout);

		advancedForm.addRow(itemsPerPage);

		advancedForm.addRow(followRedirects);

		advancedForm.addColumn(forceHttps);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		endpoint.setValue(_HOST);
		followRedirects.setValue(true);
		forceHttps.setValue(false);
		password.setValue("");
		userId.setValue("");
	}

	public ValidationResult validateTestConnection() {
		try {
			SandboxedInstance sandboxedInstance = getRuntimeSandboxedInstance();

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, this);

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			Form form = getForm(FORM_WIZARD);

			if (validationResult.getStatus() == ValidationResult.Result.OK) {
				form.setAllowFinish(true);
				form.setAllowForward(true);
			}
			else {
				form.setAllowForward(false);
			}

			return validationResult;
		}
		catch (Exception e) {
			return new ValidationResult(Result.ERROR, e.getMessage());
		}
	}

	public Property<Boolean> anonymousLogin = PropertyFactory.newBoolean(
		"anonymousLogin");
	public Property<Integer> connectTimeout = PropertyFactory.newInteger(
		"connectTimeout", _CONNECT_TIMEOUT);
	public Property<String> endpoint = PropertyFactory.newString("endpoint");
	public Property<Boolean> followRedirects = PropertyFactory.newBoolean(
		"followRedirects");
	public Property<Boolean> forceHttps = PropertyFactory.newBoolean(
		"forceHttps");
	public Property<Integer> itemsPerPage = PropertyFactory.newInteger(
		"itemsPerPage", _ITEMS_PER_PAGE);
	public Property<String> name = PropertyFactory.newString(
		"name").setRequired();
	public Property<String> password =
		PropertyFactory.newString("password").setFlags(
			EnumSet.of(
				Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING));
	public Property<Integer> readTimeout = PropertyFactory.newInteger(
		"readTimeout", _READ_TIMEOUT);
	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public PresentationItem testConnection = new PresentationItem(
		"testConnection", "Test Connection");
	public Property<String> userId = PropertyFactory.newString("userId");

	protected SandboxedInstance getRuntimeSandboxedInstance() {
		return LiferayBaseComponentDefinition.getSandboxedInstance(
			LiferayBaseComponentDefinition.RUNTIME_SOURCE_OR_SINK_CLASS_NAME);
	}

	private static final int _CONNECT_TIMEOUT = 30;

	private static final String _HOST = "\"https://apiosample.wedeploy.io\"";

	private static final int _ITEMS_PER_PAGE = 100;

	private static final int _READ_TIMEOUT = 60;

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

	private String _repositoryLocation;

}