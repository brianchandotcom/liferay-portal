/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.configuration.sample.configuration.screen;

import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.Serializable;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thiago Buarque
 */
@Component(service = ConfigurationScreen.class)
public class TestSystemConfigurationScreen extends BaseConfigurationScreen {

	@Override
	public Dictionary<String, Object> exportProperties(Serializable scopePK) {
		return _properties;
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.SYSTEM.getValue();
	}

	@Override
	public void importProperties(
			Dictionary<String, Object> properties, Serializable scopePK)
		throws Exception {

		_properties = properties;
	}

	private Dictionary<String, Object> _properties = new HashMapDictionary<>();

}