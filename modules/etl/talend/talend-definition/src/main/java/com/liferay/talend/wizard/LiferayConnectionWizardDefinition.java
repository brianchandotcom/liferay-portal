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
import org.talend.components.api.wizard.AbstractComponentWizardDefintion;
import org.talend.components.api.wizard.ComponentWizard;
import org.talend.components.api.wizard.WizardImageType;
import org.talend.daikon.definition.DefinitionImageType;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionWizardDefinition
	extends AbstractComponentWizardDefintion {

	public static final String COMPONENT_WIZARD_NAME = "liferay"; //$NON-NLS-1$

	@Override
	public ComponentWizard createWizard(
		ComponentProperties properties, String location) {

		LiferayConnectionWizard wizard = (LiferayConnectionWizard)createWizard(
			location);

		wizard.setupProperties((LiferayConnectionProperties)properties);

		return wizard;
	}

	@Override
	public ComponentWizard createWizard(String location) {
		return new LiferayConnectionWizard(this, location);
	}

	@Override
	public String getIconKey() {
		return null;
	}

	@Override
	public String getImagePath(DefinitionImageType type) {
		switch (type) {
			case TREE_ICON_16X16:
				return "liferay-16x16.png"; //$NON-NLS-1$
			case WIZARD_BANNER_75X66:
				return "liferay-66x66.png"; //$NON-NLS-1$
			default:
		}

		return null;
	}

	@Override
	public String getName() {
		return COMPONENT_WIZARD_NAME;
	}

	/**
	 * @deprecated @see org.talend.components.api.wizard.
	 * ComponentWizardDefinition#getPngImagePath(
	 * WizardImageType)
	 */
	@Deprecated
	@Override
	public String getPngImagePath(WizardImageType imageType) {
		switch (imageType) {
			case TREE_ICON_16X16:
				return getImagePath(DefinitionImageType.TREE_ICON_16X16);
			case WIZARD_BANNER_75X66:
				return getImagePath(DefinitionImageType.WIZARD_BANNER_75X66);
			default:
		}

		return null;
	}

	@Override
	public boolean isTopLevel() {
		return true;
	}

	@Override
	public boolean supportsProperties(
		Class<? extends ComponentProperties> propertiesClass) {

		return propertiesClass.isAssignableFrom(
			LiferayConnectionProperties.class);
	}

}