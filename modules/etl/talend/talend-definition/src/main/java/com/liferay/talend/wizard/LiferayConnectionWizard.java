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

import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.wizard.ComponentWizard;
import org.talend.components.api.wizard.ComponentWizardDefinition;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionWizard extends ComponentWizard {

	public LiferayConnectionWizard(
		ComponentWizardDefinition componentWizardDefinition,
		String repositoryLocation) {

		super(componentWizardDefinition, repositoryLocation);

		liferayConnectionProperties = new LiferayConnectionProperties(
			"connection");

		liferayConnectionProperties.init();

		liferayConnectionProperties.setRepositoryLocation(repositoryLocation);

		addForm(
			liferayConnectionProperties.getForm(
				LiferayConnectionProperties.FORM_WIZARD));
	}

	public void setupProperties(
		LiferayConnectionProperties liferayConnectionProperties) {

		liferayConnectionProperties.setupProperties();

		liferayConnectionProperties.copyValuesFrom(liferayConnectionProperties);
	}

	public boolean supportsProperties(ComponentProperties componentProperties) {
		if (componentProperties instanceof LiferayConnectionProperties) {
			return true;
		}

		return false;
	}

	protected LiferayConnectionProperties liferayConnectionProperties;

}