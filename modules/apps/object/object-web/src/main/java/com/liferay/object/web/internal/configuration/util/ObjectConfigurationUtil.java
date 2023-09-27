/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.configuration.util;

import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	configurationPid = "com.liferay.object.configuration.ObjectConfiguration",
	service = {}
)
public class ObjectConfigurationUtil {

	public static int maximumFileSizeForGuestUsers() {
		return _objectConfiguration.maximumFileSizeForGuestUsers();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
	}

	private static volatile ObjectConfiguration _objectConfiguration;

}