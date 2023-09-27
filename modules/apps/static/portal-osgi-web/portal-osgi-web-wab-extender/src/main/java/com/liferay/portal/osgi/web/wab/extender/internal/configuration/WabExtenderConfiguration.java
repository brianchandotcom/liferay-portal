/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera
 */
@ExtendedObjectClassDefinition(category = "module-container")
@Meta.OCD(
	id = "com.liferay.portal.osgi.web.wab.extender.internal.configuration.WabExtenderConfiguration",
	localization = "content/Language", name = "wab-extender-configuration-name"
)
public interface WabExtenderConfiguration {

	@Meta.AD(deflt = "60000", name = "stop-timeout", required = false)
	public long stopTimeout();

}