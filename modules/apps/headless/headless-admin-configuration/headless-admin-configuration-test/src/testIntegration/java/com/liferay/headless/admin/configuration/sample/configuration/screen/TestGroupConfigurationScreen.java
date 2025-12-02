/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.sample.configuration.screen;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thiago Buarque
 */
@Component(service = ConfigurationScreen.class)
public class TestGroupConfigurationScreen extends BaseConfigurationScreen {

	@Override
	public Dictionary<String, Object> exportProperties(Serializable scopePK) {
		return _groupsProperties.get((long)scopePK);
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.GROUP.getValue();
	}

	@Override
	public void importProperties(
			Dictionary<String, Object> properties, Serializable scopePK)
		throws Exception {

		_groupsProperties.put((long)scopePK, properties);
	}

	private final Map<Long, Dictionary<String, Object>> _groupsProperties =
		new HashMap<>();

}