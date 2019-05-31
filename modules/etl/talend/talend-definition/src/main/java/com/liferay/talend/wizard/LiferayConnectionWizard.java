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

package com.liferay.talend.wizard;

import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferaySiteSelectorProperties;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.wizard.ComponentWizard;
import org.talend.components.api.wizard.ComponentWizardDefinition;
import org.talend.daikon.properties.presentation.Form;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionWizard extends ComponentWizard {

	public LiferayConnectionWizard(
		ComponentWizardDefinition componentWizardDefinition,
		String repositoryLocation) {

		super(componentWizardDefinition, repositoryLocation);

		connection = new LiferayConnectionProperties("connection");

		connection.init();

		addForm(connection.getForm(LiferayConnectionProperties.FORM_WIZARD));

		siteSelector = new LiferaySiteSelectorProperties("siteSelector");

		siteSelector.setConnection(connection);
		siteSelector.setRepositoryLocation(getRepositoryLocation());

		siteSelector.init();

		addForm(siteSelector.getForm(Form.MAIN));
	}

	public void setupProperties(
		LiferayConnectionProperties liferayConnectionProperties) {

		this.connection.setupProperties();

		this.connection.copyValuesFrom(liferayConnectionProperties);

		this.siteSelector.setConnection(liferayConnectionProperties);
	}

	public boolean supportsProperties(ComponentProperties componentProperties) {
		if (componentProperties instanceof LiferayConnectionProperties) {
			return true;
		}

		return false;
	}

	public LiferayConnectionProperties connection;
	public LiferaySiteSelectorProperties siteSelector;

}