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

package com.liferay.consumer.talend.wizard;

import com.liferay.consumer.talend.connection.LiferayConnectionProperties;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.wizard.ComponentWizard;
import org.talend.components.api.wizard.ComponentWizardDefinition;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionWizard extends ComponentWizard {

	public LiferayConnectionWizard(
		ComponentWizardDefinition definition, String repositoryLocation) {

		super(definition, repositoryLocation);

		connProperties = new LiferayConnectionProperties("connection");

		connProperties.init();
		connProperties.setRepositoryLocation(repositoryLocation);

		addForm(
			connProperties.getForm(LiferayConnectionProperties.FORM_WIZARD));
	}

	public void setupProperties(LiferayConnectionProperties properties) {
		connProperties.setupProperties();
		connProperties.copyValuesFrom(properties);
	}

	public boolean supportsProperties(ComponentProperties properties) {
		return properties instanceof LiferayConnectionProperties;
	}

	protected LiferayConnectionProperties connProperties;

}