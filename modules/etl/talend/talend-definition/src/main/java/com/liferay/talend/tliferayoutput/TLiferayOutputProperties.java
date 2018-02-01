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

package com.liferay.talend.tliferayoutput;

import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.daikon.properties.presentation.Form;

/**
 * @author Zoltán Takács
 */
public class TLiferayOutputProperties
	extends LiferayConnectionResourceBaseProperties {

	public TLiferayOutputProperties(String name) {
		super(name);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = getForm(Form.MAIN);
	}

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		Set<PropertyPathConnector> connectors = new HashSet<>();

		if (!outputConnectors) {
			connectors.add(flowConnector);
			connectors.add(rejectConnector);

			return connectors;
		}

		return Collections.<PropertyPathConnector>emptySet();
	}

	protected transient PropertyPathConnector flowConnector =
		new PropertyPathConnector(Connector.MAIN_NAME, "schemaFlow");
	protected transient PropertyPathConnector rejectConnector =
		new PropertyPathConnector(Connector.REJECT_NAME, "schemaReject");

}