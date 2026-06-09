/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.profile.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@ExtendedObjectClassDefinition(category = "keymanager")
@Meta.OCD(
	id = "com.liferay.portal.security.key.internal.profile.configuration.KeyManagerGlobalConfiguration",
	localization = "content/Language",
	name = "key-manager-global-configuration-name"
)
public interface KeyManagerGlobalConfiguration {

	@Meta.AD(deflt = "custom", name = "active-profile-id", required = false)
	public String activeProfileId();

}