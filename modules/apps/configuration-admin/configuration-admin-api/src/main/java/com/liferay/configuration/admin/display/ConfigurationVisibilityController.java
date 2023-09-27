/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.display;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

/**
 * @author Drew Brokke
 */
public interface ConfigurationVisibilityController {

	public default String getKey() {
		Class<? extends ConfigurationVisibilityController> clazz = getClass();

		return clazz.getName();
	}

	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK);

}