/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.settings.internal.util;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Iván Zaera
 */
public class ConfigurationPidUtil {

	public static String getConfigurationPid(Class<?> configurationBeanClass) {
		Meta.OCD ocd = configurationBeanClass.getAnnotation(Meta.OCD.class);

		if (ocd == null) {
			throw new IllegalArgumentException(
				"Invalid configuration bean class: " +
					configurationBeanClass.getName());
		}

		return ocd.id();
	}

}