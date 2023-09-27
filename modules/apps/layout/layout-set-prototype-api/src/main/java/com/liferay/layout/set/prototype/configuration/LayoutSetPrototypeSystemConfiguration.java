/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Vendel Toreki
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "layout-set-prototype-system-configuration-description",
	id = "com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeSystemConfiguration",
	localization = "content/Language",
	name = "layout-set-prototype-system-configuration-name"
)
public interface LayoutSetPrototypeSystemConfiguration {

	@Meta.AD(
		deflt = "group", description = "import-task-isolation-help",
		name = "import-task-isolation", optionLabels = {"Group", "Company"},
		optionValues = {"group", "company"}, required = false
	)
	public String importTaskIsolation();

}