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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.exception.ComponentException;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;
import org.talend.daikon.properties.service.Repository;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferaySiteSelectorProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public LiferaySiteSelectorProperties(String name) {
		super(name);
	}

	public ValidationResult afterFormFinishMain(Repository<Properties> repo) {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, this);

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validateConnection(connection);

			validationResultMutable.setMessage(validationResult.getMessage());
			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResult.getStatus() != ValidationResult.Result.OK) {
				return validationResultMutable;
			}

			ArrayList<SimpleNamedThing> webSiteURLStoredValues =
				(ArrayList<SimpleNamedThing>)wizardWebSiteURL.getStoredValue();

			if ((webSiteURLStoredValues != null) &&
				!webSiteURLStoredValues.isEmpty()) {

				connection.siteFilter.setValue(true);
				SimpleNamedThing webSiteURLSimpleNamedThing =
					webSiteURLStoredValues.get(0);

				connection.webSite.setValue(
					webSiteURLSimpleNamedThing.getDisplayName());

				connection.webSiteURL.setValue(
					webSiteURLSimpleNamedThing.getName());
			}

			repo.storeProperties(
				connection, connection.name.getValue(), repositoryLocation,
				null);

			return validationResultMutable;
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to save the metadata", e);
			}

			return ExceptionUtils.exceptionToValidationResult(e);
		}
	}

	public void beforeFormPresentMain() throws Exception {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, connection);

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					_sites = liferaySourceOrSinkRuntime.getAvailableWebSites();

					if (_sites.isEmpty()) {
						validationResultMutable.setMessage(
							i18nMessages.getMessage(
								"error.validation.websites"));
						validationResultMutable.setStatus(
							ValidationResult.Result.ERROR);
					}

					wizardWebSiteURL.setPossibleNamedThingValues(_sites);

					connection.webSiteURL.setPossibleNamedThingValues(_sites);

					getForm(Form.MAIN).setAllowBack(true);
					getForm(Form.MAIN).setAllowFinish(true);
				}
				catch (Exception e) {
					throw new ComponentException(e);
				}
			}
		}
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection;
	}

	public String getRepositoryLocation() {
		return repositoryLocation;
	}

	public LiferaySiteSelectorProperties setConnection(
		LiferayConnectionProperties connection) {

		this.connection = connection;

		return this;
	}

	public LiferaySiteSelectorProperties setRepositoryLocation(
		String location) {

		repositoryLocation = location;

		return this;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form siteForm = Form.create(this, Form.MAIN);

		Widget webSiteURLWizardWidget = Widget.widget(wizardWebSiteURL);

		webSiteURLWizardWidget.setWidgetType(
			Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		siteForm.addRow(webSiteURLWizardWidget);

		refreshLayout(siteForm);
	}

	public volatile LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public StringProperty wizardWebSiteURL = PropertyFactory.newString(
		"wizardWebSiteURL");

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferaySiteSelectorProperties.class);
	}

	/**
	 * This must be named <code>repositoryLocation</code> since Talend uses
	 * reflection to get a field named this. See <a
	 * href="https://github.com/Talend/tdi-studio-se/blob/125a8144597e5d5faa1f7001ce345cdfd6dc1fe3/main/plugins/org.talend.repository.generic/src/main/java/org/talend/repository/generic/ui/GenericConnWizard.java#L111">here</a>
	 * for more information.
	 */
	protected String repositoryLocation;

	private static final Logger _log = LoggerFactory.getLogger(
		LiferaySiteSelectorProperties.class);

	private List<NamedThing> _sites;

}