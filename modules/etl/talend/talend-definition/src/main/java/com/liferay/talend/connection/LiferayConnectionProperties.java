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
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.runtime.ValidatedSoSSandboxRuntime;
import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.utils.PropertiesUtils;
import com.liferay.talend.utils.URIUtils;

import java.net.URL;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.Properties;
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

	public void afterLoginType() {
		if (_log.isInfoEnabled()) {
			_log.info("Authorization method: " + loginType.getValue());
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(FORM_WIZARD));
	}

	public void afterReferencedComponent() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public void afterSiteId() {
		if (_log.isDebugEnabled()) {
			_log.debug("Site ID: " + siteId.getValue());
		}

		try {
			siteName.setValue(
				siteId.getPossibleValuesDisplayName(siteId.getValue()));
		}
		catch (TalendRuntimeException tre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine the site name with site ID: " +
						siteId.getValue());
			}
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public ValidationResult beforeSiteId() {
		ValidatedSoSSandboxRuntime validatedSoSSandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getReferencedConnectionProperties());

		ValidationResultMutable validationResultMutable =
			validatedSoSSandboxRuntime.getValidationResultMutable();

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			return validationResultMutable;
		}

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime();

		try {
			List<NamedThing> webSites =
				liferaySourceOrSinkRuntime.getAvailableWebSites();

			if (webSites.isEmpty()) {
				validationResultMutable.setMessage(
					i18nMessages.getMessage("error.validation.sites"));
				validationResultMutable.setStatus(
					ValidationResult.Result.ERROR);
			}

			siteId.setPossibleNamedThingValues(webSites);
		}
		catch (Exception e) {
			_log.error("Unable to get sites", e);

			return ExceptionUtils.exceptionToValidationResult(e);
		}

		return null;
	}

	public String getApplicationBaseHref() {
		URL openAPISpecURL = URIUtils.toURL(apiSpecURL.getValue());
		URL serverURL = getServerURL();
		String jaxRSAppBase = URIUtils.extractJaxRSAppBasePathSegment(
			openAPISpecURL);

		String serverHref = serverURL.toExternalForm();

		return serverHref.concat(jaxRSAppBase);
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

		if (getReferencedComponentId() != null) {
			_log.error(
				"Connection has a reference to '{}' but the referenced " +
					"Object is null",
				getReferencedComponentId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Fall back to the actual instance " +
					"LiferayConnectionProperties for the runtime environment");
		}

		return getLiferayConnectionProperties();
	}

	public URL getServerURL() {
		URL apiSpecURL = URIUtils.toURL(this.apiSpecURL.getValue());

		return URIUtils.extractServerURL(apiSpecURL);
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

		PropertiesUtils.setHidden(form, anonymousLogin, useOtherConnection);
		PropertiesUtils.setHidden(form, apiSpecURL, useOtherConnection);
		PropertiesUtils.setHidden(form, loginType, useOtherConnection);
		PropertiesUtils.setHidden(form, password, useOtherConnection);
		PropertiesUtils.setHidden(form, userId, useOtherConnection);
		PropertiesUtils.setHidden(form, siteName, useOtherConnection);
		PropertiesUtils.setHidden(form, siteId, useOtherConnection);

		if (!useOtherConnection && anonymousLogin.getValue()) {
			PropertiesUtils.setHidden(form, userId, true);
			PropertiesUtils.setHidden(form, password, true);
		}
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Wizard form

		Form wizardForm = _createForm(this, FORM_WIZARD);

		_addAuthorizationProps(wizardForm);

		Widget testConnectionWidget = _createWidget(
			testConnection, true, Widget.BUTTON_WIDGET_TYPE);

		Widget advancedFormWizardWidget = _createWidget(
			advanced, Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(advancedFormWizardWidget);

		wizardForm.addColumn(testConnectionWidget);

		// Main form

		Form mainForm = _createForm(this, Form.MAIN);

		_addAuthorizationProps(mainForm);

		_addSiteWidget(mainForm);

		// A form for a reference to a connection, used in a tLiferayInput
		// for example

		Form referenceForm = _createForm(this, Form.REFERENCE);

		_addAuthorizationProps(referenceForm);

		_addSiteWidget(referenceForm);

		refreshLayout(referenceForm);

		// Advanced form

		advanced.setFormtoShow(
			_createAdvancedForm(
				this, connectTimeout, readTimeout, itemsPerPage,
				followRedirects, forceHttps));
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		apiSpecURL.setValue(_COMMERCE_CATALOG_OAS_URL);
		followRedirects.setValue(true);
		forceHttps.setValue(false);
		loginType.setValue(LoginType.BASIC);
		password.setValue(_PASSWORD);
		userId.setValue(_USER_ID);
		siteName.setValue("");
		siteId.setValue("");
	}

	public ValidationResult validateTestConnection() {
		ValidatedSoSSandboxRuntime sandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getReferencedConnectionProperties());

		ValidationResultMutable validationResultMutable =
			sandboxRuntime.getValidationResultMutable();

		Form form = getForm(FORM_WIZARD);

		if (validationResultMutable.getStatus() == ValidationResult.Result.OK) {
			form.setAllowFinish(true);
			form.setAllowForward(true);
		}
		else {
			form.setAllowForward(false);
		}

		return validationResultMutable;
	}

	public PresentationItem advanced = new PresentationItem("advanced");
	public Property<Boolean> anonymousLogin = PropertyFactory.newBoolean(
		"anonymousLogin");
	public Property<String> apiSpecURL = PropertyFactory.newString(
		"apiSpecURL");
	public Property<Integer> connectTimeout = PropertyFactory.newInteger(
		"connectTimeout", _CONNECT_TIMEOUT);
	public Property<Boolean> followRedirects = PropertyFactory.newBoolean(
		"followRedirects");
	public Property<Boolean> forceHttps = PropertyFactory.newBoolean(
		"forceHttps");
	public Property<Integer> itemsPerPage = PropertyFactory.newInteger(
		"itemsPerPage", _ITEMS_PER_PAGE);
	public Property<LoginType> loginType = PropertyFactory.newEnum(
		"loginType", LoginType.class
	).setRequired();
	public Property<String> name = PropertyFactory.newString(
		"name"
	).setRequired();
	public Property<String> password = PropertyFactory.newString(
		"password"
	).setFlags(
		EnumSet.of(Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING)
	);
	public Property<Integer> readTimeout = PropertyFactory.newInteger(
		"readTimeout", _READ_TIMEOUT);
	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public StringProperty siteId = new StringProperty("siteId");
	public Property<String> siteName = PropertyFactory.newString("siteName");
	public PresentationItem testConnection = new PresentationItem(
		"testConnection");
	public Property<String> userId = PropertyFactory.newString("userId");

	public enum LoginType {

		BASIC("Basic Authentication");

		public String getDescription() {
			return _description;
		}

		private LoginType(String description) {
			_description = description;
		}

		private final String _description;

	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayConnectionProperties.class);
	}

	private void _addAuthorizationProps(Form form) {
		Widget loginWidget = _createWidget(
			loginType, Widget.ENUMERATION_WIDGET_TYPE);

		if (Objects.equals(form.getName(), FORM_WIZARD)) {
			loginWidget.setDeemphasize(true);
		}

		form.addRow(loginWidget);

		form.addRow(apiSpecURL);
		form.addRow(anonymousLogin);
		form.addRow(userId);

		form.addColumn(password);
	}

	private void _addSiteWidget(Form form) {
		Widget siteIdReferenceWidget = _createWidget(
			siteId, true, Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		siteIdReferenceWidget.setCallAfter(true);

		form.addRow(siteIdReferenceWidget);

		Widget siteNameReferenceWidget = Widget.widget(siteName);

		siteNameReferenceWidget.setReadonly(true);

		form.addColumn(siteNameReferenceWidget);
	}

	private Form _createAdvancedForm(Properties properties, Property... props) {
		Form advancedForm = new Form(properties, Form.ADVANCED);

		if ((props == null) || (props.length == 0)) {
			return advancedForm;
		}

		for (Property property : props) {
			advancedForm.addRow(property);
		}

		return advancedForm;
	}

	private Form _createForm(Properties properties, String formName) {
		Form form = new Form(properties, formName);

		if (Objects.equals(formName, FORM_WIZARD)) {
			form.addRow(name);
		}

		if (Objects.equals(formName, Form.REFERENCE)) {
			Widget referencedComponentWidget = Widget.widget(
				referencedComponent);

			referencedComponentWidget.setWidgetType(
				Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

			form.addRow(referencedComponentWidget);
		}

		return form;
	}

	private Widget _createWidget(
		NamedThing namedThing, boolean longRunning, String widgetType) {

		Widget widget = Widget.widget(namedThing);

		widget.setLongRunning(longRunning);
		widget.setWidgetType(widgetType);

		return widget;
	}

	private Widget _createWidget(NamedThing namedThing, String widgetType) {
		return _createWidget(namedThing, false, widgetType);
	}

	private static final String _COMMERCE_CATALOG_OAS_URL =
		"\"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json\"";

	private static final int _CONNECT_TIMEOUT = 30;

	private static final int _ITEMS_PER_PAGE = 100;

	private static final String _PASSWORD = "test";

	private static final int _READ_TIMEOUT = 60;

	private static final String _USER_ID = "test@liferay.com";

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

}