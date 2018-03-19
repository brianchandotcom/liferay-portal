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

package org.talend.components.liferay;

import aQute.bnd.annotation.component.Component;

import com.google.auto.service.AutoService;

import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.tliferayinput.TLiferayInputDefinition;
import com.liferay.talend.tliferayoutput.TLiferayOutputDefinition;
import com.liferay.talend.wizard.LiferayConnectionWizardDefinition;

import org.talend.components.api.AbstractComponentFamilyDefinition;
import org.talend.components.api.ComponentInstaller;
import org.talend.components.api.Constants;

/**
 * Install all of the definitions provided for the tLiferayInput family of
 * components.
 *
 * @author Zoltán Takács
 */
@AutoService(ComponentInstaller.class)
@Component(
	name = Constants.COMPONENT_INSTALLER_PREFIX + LiferayFamilyDefinition.NAME,
	provide = ComponentInstaller.class
)
public class LiferayFamilyDefinition
	extends AbstractComponentFamilyDefinition implements ComponentInstaller {

	public static final String NAME = "Liferay";

	public LiferayFamilyDefinition() {
		super(
			NAME, new TLiferayConnectionDefinition(),
			new TLiferayInputDefinition(), new TLiferayOutputDefinition(),
			new LiferayConnectionWizardDefinition());
	}

	@Override
	public void install(ComponentFrameworkContext componentFrameworkContext) {
		componentFrameworkContext.registerComponentFamilyDefinition(this);
	}

}