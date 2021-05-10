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

package com.liferay.talend.properties.connection;

import com.liferay.talend.LiferayDefinition;
import com.liferay.talend.internal.oas.LiferayOASSource;
import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.ui.UIKeys;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferayConnectionProperties extends ComponentPropertiesImpl {

	public LiferayConnectionProperties(String name) {
		super(name);
	}

	public void afterLoginType() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(UIKeys.FORM_WIZARD));
	}

	public void afterReferencedComponent() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public int getConnectTimeout() {
		return _getValue(connectTimeout);
	}

	public LiferayConnectionProperties
		getEffectiveLiferayConnectionProperties() {

		LiferayConnectionProperties liferayConnectionProperties =
			referencedComponent.getReference();

		if (liferayConnectionProperties != null) {
			return liferayConnectionProperties;
		}

		return this;
	}

	public String getHostURL() {
		return _getValue(hostURL);
	}

	public int getItemsPerPage() {
		return _getValue(itemsPerPage);
	}

	public String getOAuthClientId() {
		return _getValue(oAuthAuthorizationProperties.oauthClientId);
	}

	public String getOAuthClientSecret() {
		return _getValue(oAuthAuthorizationProperties.oauthClientSecret);
	}

	public String getPassword() {
		return _getValue(basicAuthorizationProperties.password);
	}

	public int getReadTimeout() {
		return _getValue(readTimeout);
	}

	public String getReferencedComponentId() {
		return referencedComponent.componentInstanceId.getStringValue();
	}

	public String getUserId() {
		return _getValue(basicAuthorizationProperties.userId);
	}

	public boolean isBasicAuthorization() {
		if (loginType.getValue() == LoginType.BASIC) {
			return true;
		}

		return false;
	}

	public boolean isFollowRedirects() {
		return _getValue(followRedirects);
	}

	public boolean isForceHttps() {
		return _getValue(forceHttps);
	}

	public boolean isOAuth2Authorization() {
		if (loginType.getValue() == LoginType.OAUTH2) {
			return true;
		}

		return false;
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String referencedComponentId = getReferencedComponentId();

		boolean hidden = false;

		if ((referencedComponentId != null) &&
			referencedComponentId.startsWith(
				TLiferayConnectionDefinition.COMPONENT_NAME)) {

			hidden = true;
		}

		Widget widget = form.getWidget(hostURL.getName());

		if (widget != null) {
			widget.setHidden(hidden);
		}

		widget = form.getWidget(loginType.getName());

		if (widget != null) {
			widget.setHidden(hidden);
		}

		Form basicAuthorizationPropertiesForm =
			basicAuthorizationProperties.getForm(
				UIKeys.FORM_BASIC_AUTHORIZATION);

		basicAuthorizationPropertiesForm.setHidden(hidden);

		Form oAuthAuthorizationPropertiesForm =
			oAuthAuthorizationProperties.getForm(
				UIKeys.FORM_OAUTH_AUTHORIZATION);

		oAuthAuthorizationPropertiesForm.setHidden(hidden);

		if (hidden) {
			return;
		}

		if (!isBasicAuthorization()) {
			basicAuthorizationPropertiesForm.setVisible(false);
			oAuthAuthorizationPropertiesForm.setVisible(true);
		}
		else {
			basicAuthorizationPropertiesForm.setVisible(true);
			oAuthAuthorizationPropertiesForm.setVisible(false);
		}

		if (_logger.isTraceEnabled()) {
			_logger.trace("Refreshed " + System.identityHashCode(this));
		}
	}

	@Override
	public void setupLayout() {
		Form wizardForm = _createForm(this, UIKeys.FORM_WIZARD);

		_addAuthorizationProps(wizardForm);

		Widget testConnectionWidget = _createWidget(
			testConnection, true, Widget.BUTTON_WIDGET_TYPE);

		Widget advancedFormWizardWidget = _createWidget(
			advanced, Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(advancedFormWizardWidget);

		wizardForm.addColumn(testConnectionWidget);

		Form mainForm = _createForm(this, Form.MAIN);

		_addAuthorizationProps(mainForm);

		Form referenceForm = _createForm(this, Form.REFERENCE);

		_addAuthorizationProps(referenceForm);

		refreshLayout(referenceForm);

		advanced.setFormtoShow(
			_createAdvancedForm(
				this, connectTimeout, readTimeout, itemsPerPage,
				followRedirects, forceHttps));
	}

	@Override
	public void setupProperties() {
		hostURL.setValue(_LIFERAY_URL);

		followRedirects.setValue(true);

		forceHttps.setValue(false);

		loginType.setValue(LoginType.BASIC);

		name.setRequired();
	}

	public ValidationResult validateTestConnection() {
		LiferayOASSource liferayOASSource =
			LiferayDefinition.getLiferayOASSource(
				getEffectiveLiferayConnectionProperties());

		Form form = getForm(UIKeys.FORM_WIZARD);

		if (!liferayOASSource.isValid()) {
			form.setAllowForward(false);

			return liferayOASSource.getValidationResult();
		}

		form.setAllowFinish(true);
		form.setAllowForward(true);

		return liferayOASSource.getValidationResult();
	}

	public PresentationItem advanced = new PresentationItem("advanced");
	public BasicAuthorizationProperties basicAuthorizationProperties =
		new BasicAuthorizationProperties("basicAuthorizationProperties");
	public Property<Integer> connectTimeout = PropertyFactory.newInteger(
		"connectTimeout", _CONNECT_TIMEOUT);
	public Property<Boolean> followRedirects = PropertyFactory.newBoolean(
		"followRedirects");
	public Property<Boolean> forceHttps = PropertyFactory.newBoolean(
		"forceHttps");
	public Property<String> hostURL = PropertyFactory.newString("hostURL");
	public Property<Integer> itemsPerPage = PropertyFactory.newInteger(
		"itemsPerPage", _ITEMS_PER_PAGE);
	public Property<LoginType> loginType = PropertyFactory.newEnum(
		"loginType", LoginType.class);
	public Property<String> name = PropertyFactory.newString("name");
	public OAuthAuthorizationProperties oAuthAuthorizationProperties =
		new OAuthAuthorizationProperties("oAuthAuthorizationProperties");
	public Property<Integer> readTimeout = PropertyFactory.newInteger(
		"readTimeout", _READ_TIMEOUT);
	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public PresentationItem testConnection = new PresentationItem(
		"testConnection");

	public enum LoginType {

		BASIC("Basic Authentication"), OAUTH2("OAuth2 Authorization");

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

		if (Objects.equals(form.getName(), UIKeys.FORM_WIZARD)) {
			loginWidget.setDeemphasize(true);
		}

		form.addRow(loginWidget);

		Form basicAuthorizationPropertiesForm =
			basicAuthorizationProperties.getForm(
				UIKeys.FORM_BASIC_AUTHORIZATION);

		form.addRow(basicAuthorizationPropertiesForm);

		Form oAuthAuthorizationPropertiesForm =
			oAuthAuthorizationProperties.getForm(
				UIKeys.FORM_OAUTH_AUTHORIZATION);

		form.addRow(oAuthAuthorizationPropertiesForm);
	}

	private Form _createAdvancedForm(
		Properties properties, Property<?>... props) {

		Form advancedForm = new Form(properties, Form.ADVANCED);

		if ((props == null) || (props.length == 0)) {
			return advancedForm;
		}

		for (Property<?> property : props) {
			advancedForm.addRow(property);
		}

		return advancedForm;
	}

	private Form _createForm(Properties properties, String formName) {
		Form form = new Form(properties, formName);

		if (Objects.equals(formName, UIKeys.FORM_WIZARD)) {
			form.addRow(name);
		}
		else if (Objects.equals(formName, Form.MAIN)) {
			Widget horizontalSpace = Widget.widget(
				new PresentationItem("horizontalSpace"));

			horizontalSpace.setWidgetType(Widget.DEFAULT_WIDGET_TYPE);
			horizontalSpace.setHidden(true);

			form.addRow(horizontalSpace);
		}
		else if (Objects.equals(formName, Form.REFERENCE)) {
			Widget referencedComponentWidget = Widget.widget(
				referencedComponent);

			referencedComponentWidget.setWidgetType(
				Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

			form.addRow(referencedComponentWidget);
		}

		form.addRow(hostURL);

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

	private <T> T _getValue(Property<T> property) {
		return property.getValue();
	}

	private static final int _CONNECT_TIMEOUT = 30;

	private static final int _ITEMS_PER_PAGE = 100;

	private static final String _LIFERAY_URL = "\"http://localhost:8080\"";

	private static final int _READ_TIMEOUT = 60;

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

}