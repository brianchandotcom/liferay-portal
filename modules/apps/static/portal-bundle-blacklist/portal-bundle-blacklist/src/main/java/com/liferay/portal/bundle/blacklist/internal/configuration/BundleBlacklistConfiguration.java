/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bundle.blacklist.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matthew Tambara
 */
@ExtendedObjectClassDefinition(
	category = "module-container", liferayLearnMessageKey = "general",
	liferayLearnMessageResource = "portal-bundle-blacklist-impl"
)
@Meta.OCD(
	id = "com.liferay.portal.bundle.blacklist.internal.configuration.BundleBlacklistConfiguration",
	localization = "content/Language",
	name = "portal-bundle-blacklist-service-configuration-name"
)
public interface BundleBlacklistConfiguration {

	@Meta.AD(
		deflt = "", name = "blacklist-bundle-symbolic-names", required = false
	)
	public String[] blacklistBundleSymbolicNames();

}