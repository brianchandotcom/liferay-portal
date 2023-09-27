/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.document.conversion.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "connectors")
@Meta.OCD(
	id = "com.liferay.document.library.document.conversion.internal.configuration.OpenOfficeConfiguration",
	localization = "content/Language", name = "openoffice-configuration-name"
)
public interface OpenOfficeConfiguration {

	@Meta.AD(deflt = "true", name = "cache-enabled", required = false)
	public boolean cacheEnabled();

	@Meta.AD(
		deflt = "false", description = "openoffice-server-enabled-help",
		name = "server-enabled", required = false
	)
	public boolean serverEnabled();

	@Meta.AD(deflt = "127.0.0.1", name = "server-host", required = false)
	public String serverHost();

	@Meta.AD(deflt = "8100", name = "server-port", required = false)
	public int serverPort();

}