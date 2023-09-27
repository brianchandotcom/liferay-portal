/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.component.blacklist.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(
	category = "module-container", liferayLearnMessageKey = "general",
	liferayLearnMessageResource = "portal-component-blacklist-impl"
)
@Meta.OCD(
	id = "com.liferay.portal.component.blacklist.internal.configuration.ComponentBlacklistConfiguration",
	localization = "content/Language",
	name = "component-blacklist-configuration-name"
)
public interface ComponentBlacklistConfiguration {

	@Meta.AD(
		deflt = "", description = "blacklist-component-names-help",
		name = "blacklist-component-names", required = false
	)
	public String[] blacklistComponentNames();

}