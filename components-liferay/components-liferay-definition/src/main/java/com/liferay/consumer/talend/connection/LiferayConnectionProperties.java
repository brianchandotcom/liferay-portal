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

package com.liferay.consumer.talend.connection;

import com.liferay.consumer.talend.LiferayBaseComponentDefinition;
import com.liferay.consumer.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.consumer.talend.tliferayconnection.TLiferayConnectionDefinition;

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
	implements LiferayProvideConnectionProperties {

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

			LiferaySourceOrSinkRuntime sos =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			sos.initialize(null, this);

			ValidationResult vr = sos.validateConnection(this);

			if (vr.getStatus() != ValidationResult.Result.OK) {
				return vr;
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
	public LiferayConnectionProperties getConnectionProperties() {
		return this;
	}

	public String getReferencedComponentId() {
		return referencedComponent.componentInstanceId.getStringValue();
	}

	public LiferayConnectionProperties getReferencedConnectionProperties() {
		LiferayConnectionProperties refProps =
			referencedComponent.getReference();

		if (refProps != null) {
			return refProps;
		}

		_log.error(
			"Connection has a reference to `{}` but the referenced Object is " +
				"null!",
			getReferencedComponentId());

		return null;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String refComponentIdValue = getReferencedComponentId();

		boolean useOtherConnection = false;

		if ((refComponentIdValue != null) &&
			refComponentIdValue.startsWith(
				TLiferayConnectionDefinition.COMPONENT_NAME)) {

			useOtherConnection = true;
		}

		if (form.getName().equals(Form.MAIN) ||
			form.getName().equals(FORM_WIZARD)) {

			form.getWidget(endpoint.getName()).setHidden(useOtherConnection);
			form.getWidget(userId.getName()).setHidden(useOtherConnection);
			form.getWidget(password.getName()).setHidden(useOtherConnection);
			form.getWidget(
				anonymousLogin.getName()).setHidden(useOtherConnection);

			if (!useOtherConnection && anonymousLogin.getValue()) {
				form.getWidget(userId.getName()).setHidden(true);
				form.getWidget(password.getName()).setHidden(true);
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

		//Wizard
		Form wizardForm = Form.create(this, FORM_WIZARD);

		wizardForm.addRow(name);
		wizardForm.addRow(endpoint);
		wizardForm.addRow(userId);
		wizardForm.addColumn(password);
		wizardForm.addRow(anonymousLogin);

		Widget testConn = Widget.widget(testConnection);

		testConn.setLongRunning(true);
		testConn.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(testConn);

		//Main form
		Form mainForm = Form.create(this, Form.MAIN);

		mainForm.addRow(endpoint);
		mainForm.addRow(userId);
		mainForm.addColumn(password);
		mainForm.addRow(anonymousLogin);

		// A form for a reference to a connection, used in a tLiferayInput
		// for example

		Form refForm = Form.create(this, Form.REFERENCE);

		Widget compListWidget = Widget.widget(referencedComponent);

		compListWidget.setWidgetType(Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

		refForm.addRow(compListWidget);

		refForm.addRow(mainForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		endpoint.setValue(_DEFAULT_HOST);
		userId.setValue("");
		password.setValue("");
	}

	public ValidationResult validateTestConnection() {
		try {
			SandboxedInstance sandboxedInstance = getRuntimeSandboxedInstance();

			LiferaySourceOrSinkRuntime sos =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			sos.initialize(null, this);

			ValidationResult vr = sos.validate(null);

			if (vr.getStatus() == ValidationResult.Result.OK) {
				getForm(FORM_WIZARD).setAllowForward(true);
				getForm(FORM_WIZARD).setAllowFinish(true);
			}
			else {
				getForm(FORM_WIZARD).setAllowForward(false);
			}

			return vr;
		}
		catch (Exception e) {
			return new ValidationResult(Result.ERROR, e.getMessage());
		}
	}

	public Property<Boolean> anonymousLogin = PropertyFactory.newBoolean(
		"anonymousLogin"); //$NON-NLS-1$

	public Property<String> endpoint =
		PropertyFactory.newString("endpoint"); //$NON-NLS-1$

	public Property<String> name = PropertyFactory.newString(
		"name").setRequired(); //$NON-NLS-1$

	public Property<String> password =
		PropertyFactory.newString("password").setFlags(
			EnumSet.of(
				Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING));

	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public PresentationItem testConnection = new PresentationItem(
		"testConnection", "Test connection");
	public Property<String> userId = PropertyFactory.newString("userId");

	protected SandboxedInstance getRuntimeSandboxedInstance() {
		return LiferayBaseComponentDefinition.getSandboxedInstance(
			LiferayBaseComponentDefinition.RUNTIME_SOURCEORSINK_CLASS);
	}

	private static final String _DEFAULT_HOST =
		"\"https://apiosample.wedeploy.io\"";

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

	private String _repositoryLocation;

}