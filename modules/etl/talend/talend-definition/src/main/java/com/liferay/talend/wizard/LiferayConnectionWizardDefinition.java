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
@SuppressWarnings("deprecation")
public class LiferayConnectionWizardDefinition
	extends AbstractComponentWizardDefintion {

	public static final String COMPONENT_WIZARD_NAME =
		"LiferayConnectionWizard";

	@Override
	public ComponentWizard createWizard(
		ComponentProperties componentProperties, String location) {

		LiferayConnectionWizard liferayConnectionWizard =
			(LiferayConnectionWizard)createWizard(location);

		liferayConnectionWizard.setupProperties(
			(LiferayConnectionProperties)componentProperties);

		return liferayConnectionWizard;
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
	public String getImagePath(DefinitionImageType definitionImageType) {
		if (definitionImageType == DefinitionImageType.TREE_ICON_16X16) {
			return "LiferayWizard_icon_16x16.png";
		}
		else if (definitionImageType ==
					 DefinitionImageType.WIZARD_BANNER_75X66) {

			return "LiferayWizard_banner_75x66.png";
		}

		return null;
	}

	@Override
	public String getName() {
		return COMPONENT_WIZARD_NAME;
	}

	/**
	 * @see org.talend.components.api.wizard.ComponentWizardDefinition#getPngImagePath(
	 *      WizardImageType)
	 */
	@Override
	public String getPngImagePath(WizardImageType wizardImageType) {
		if (wizardImageType == WizardImageType.TREE_ICON_16X16) {
			return getImagePath(DefinitionImageType.TREE_ICON_16X16);
		}
		else if (wizardImageType == WizardImageType.WIZARD_BANNER_75X66) {
			return getImagePath(DefinitionImageType.WIZARD_BANNER_75X66);
		}

		return null;
	}

	/**
	 * PropertiesTestUtils reports this as an error, but it is not.
	 *
	 * @see https://github.com/Talend/daikon/blob/98c09e0a25ff59a8d361c171e0ad1c1866176bc5/daikon/src/test/java/org/talend/daikon/properties/test/PropertiesTestUtils.java#L119-L121
	 */
	@Override
	public Class getPropertiesClass() {
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