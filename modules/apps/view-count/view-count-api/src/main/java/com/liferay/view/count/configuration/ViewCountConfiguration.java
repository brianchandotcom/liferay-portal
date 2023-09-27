/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eric Yan
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "view-count-configuration-name-help",
	id = "com.liferay.view.count.configuration.ViewCountConfiguration",
	localization = "content/Language", name = "view-count-configuration-name"
)
@ProviderType
public interface ViewCountConfiguration {

	@Meta.AD(
		deflt = "true", description = "enabled-help[view-count]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "", description = "disabled-class-names-help",
		name = "disabled-class-names", required = false
	)
	public String[] disabledClassNames();

}