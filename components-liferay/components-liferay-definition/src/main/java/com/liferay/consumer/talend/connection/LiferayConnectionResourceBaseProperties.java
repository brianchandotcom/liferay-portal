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

import com.liferay.consumer.talend.resource.LiferayResourceProperties;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.daikon.properties.presentation.Form;

/**
 * @author Zoltán Takács
 */
public abstract class LiferayConnectionResourceBaseProperties
	extends FixedConnectorsComponentProperties
	implements LiferayProvideConnectionProperties {

	public LiferayConnectionResourceBaseProperties(String name) {
		super(name);
	}

	@Override
	public LiferayConnectionProperties getConnectionProperties() {
		return connection;
	}

	public Schema getSchema() {
		return resource.main.schema.getValue();
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		for (Form childForm : connection.getForms()) {
			connection.refreshLayout(childForm);
		}
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(connection.getForm(Form.REFERENCE));
		mainForm.addRow(resource.getForm(Form.REFERENCE));
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		resource = new LiferayResourceProperties("resource");

		resource.connection = connection;
	}

	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection"); //$NON-NLS-1$

	public LiferayResourceProperties resource;

	protected transient PropertyPathConnector mainConnector =
		new PropertyPathConnector(Connector.MAIN_NAME, "resource.main");

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionResourceBaseProperties.class);

	private static final long serialVersionUID = 4534371813009904L;

}