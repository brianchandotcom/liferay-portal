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

package com.liferay.consumer.talend.tliferayinput;

import com.liferay.consumer.talend.LiferayBaseComponentDefinition;
import com.liferay.consumer.talend.connection.LiferayConnectionProperties;
import com.liferay.consumer.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.consumer.talend.exception.ExceptionUtils;
import com.liferay.consumer.talend.runtime.LiferaySourceOrSinkRuntime;

import java.io.IOException;

import java.util.Collections;
import java.util.Set;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.PropertyPathConnector;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputProperties
	extends LiferayConnectionResourceBaseProperties {

	public TLiferayInputProperties(String name) {
		super(name);
	}

	/**
	 * Refreshes form after "Guess Schema" button was processed
	 */
	public void afterGuessSchema() {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Selected resource URL: " + resource.resourceURL.getValue());
			_log.debug("Query string: " + queryString.getValue());
		}

		refreshLayout(getForm(Form.MAIN));
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = getForm(Form.MAIN);

		mainForm.addRow(queryString);

		Widget guessButton = Widget.widget(guessSchema);

		guessButton.setWidgetType(Widget.BUTTON_WIDGET_TYPE);
		guessButton.setLongRunning(true);

		mainForm.addRow(guessButton);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		queryString.setValue("");
	}

	public ValidationResult validateGuessSchema() {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCEORSINK_CLASS)) {

			LiferaySourceOrSinkRuntime ss =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			ValidationResult result = ss.initialize(
				null, _getEffectiveConnectionProperties());

			if (result.getStatus() == Result.ERROR) {
				return result;
			}

			result = ss.validate(null);

			if (result.getStatus() == ValidationResult.Result.OK) {
				try {
					Schema runtimeSchema = ss.guessSchema(
						resource.resourceURL.getValue());

					resource.main.schema.setValue(runtimeSchema);
				}
				catch (IOException ioe) {
					/* result = new ValidationResult(
						Result.ERROR,
						translations.getMessage(
							"error.validation.connection.endpoint",
							ioe.getMessage()));*/
					ExceptionUtils.exceptionToValidationResult(ioe);
				}
			}
			else {
				return result;
			}

			return result;
		}
	}

	public Property<String> queryString =
		PropertyFactory.newProperty("queryString"); //$NON-NLS-1$

	public transient PresentationItem guessSchema = new PresentationItem(
		"guessSchema", "Guess schema");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		if (outputConnectors) {
			return Collections.singleton(mainConnector);
		}

		return Collections.<PropertyPathConnector>emptySet();
	}

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
		TLiferayInputProperties.class);

	private static final long serialVersionUID = 8010931662185868407L;

}