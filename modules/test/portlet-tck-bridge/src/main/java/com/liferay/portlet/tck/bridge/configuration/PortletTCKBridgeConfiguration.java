/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.tck.bridge.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Shuyang Zhou
 * @author Vernon Singleton
 * @author Kyle Stiemann
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portlet.tck.bridge.configuration.PortletTCKBridgeConfiguration",
	localization = "content/Language",
	name = "portlet-tck-bridge-configuration-name"
)
public interface PortletTCKBridgeConfiguration {

	@Meta.AD(deflt = "")
	public String configFile();

}