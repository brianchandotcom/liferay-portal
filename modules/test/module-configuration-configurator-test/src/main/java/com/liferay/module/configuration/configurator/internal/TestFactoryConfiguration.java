/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.module.configuration.configurator.internal;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Tina Tian
 */
@Meta.OCD(
	factory = true,
	id = "com.liferay.module.configuration.configurator.internal.TestFactoryConfiguration"
)
public interface TestFactoryConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "", name = "type", required = false)
	public String type();

}