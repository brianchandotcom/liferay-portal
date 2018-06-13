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

import com.liferay.talend.resource.LiferayResourceProperties;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.presentation.Form;

/**
 * @author Zoltán Takács
 */
public abstract class LiferayConnectionResourceBaseProperties
	extends FixedConnectorsComponentProperties
	implements LiferayConnectionPropertiesProvider {

	public LiferayConnectionResourceBaseProperties(String name) {
		super(name);
	}

	/**
	 * This method returns the connection properties from the referenced
	 * connection component if it was specified by the user, otherwise the
	 * actual component's connection properties.
	 *
	 * @return LiferayConnectionProperties
	 */
	public LiferayConnectionProperties getEffectiveConnectionProperties() {
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

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return liferayConnectionProperties;
	}

	public Schema getSchema() {
		return liferayResourceProperties.mainSchemaProperties.schema.getValue();
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		for (Form childForm : liferayConnectionProperties.getForms()) {
			liferayConnectionProperties.refreshLayout(childForm);
		}

		for (Form childForm : liferayResourceProperties.getForms()) {
			liferayResourceProperties.refreshLayout(childForm);
		}
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(liferayConnectionProperties.getForm(Form.REFERENCE));

		mainForm.addRow(liferayResourceProperties.getForm(Form.REFERENCE));

		refreshLayout(mainForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		liferayResourceProperties = new LiferayResourceProperties(
			"liferayResourceProperties");

		liferayResourceProperties.liferayConnectionProperties =
			liferayConnectionProperties;

		liferayResourceProperties.setupProperties();
	}

	public LiferayConnectionProperties liferayConnectionProperties =
		new LiferayConnectionProperties("liferayConnectionProperties");
	public LiferayResourceProperties liferayResourceProperties;

	protected transient PropertyPathConnector mainConnector =
		new PropertyPathConnector(Connector.MAIN_NAME, "baseMain");
	protected transient Schema temporaryMainSchema =
		SchemaProperties.EMPTY_SCHEMA;

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionResourceBaseProperties.class);

	private static final long serialVersionUID = 4534371813009904L;

}