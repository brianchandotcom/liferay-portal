/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.web.internal.configuration.helper;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.security.script.management.configuration.ScriptManagementConfiguration;
import com.liferay.portal.security.script.management.configuration.helper.ScriptManagementConfigurationHelper;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Feliphe Marinho
 */
@Component(
	configurationPid = "com.liferay.portal.security.script.management.configuration.ScriptManagementConfiguration",
	service = ScriptManagementConfigurationHelper.class
)
public class ScriptManagementConfigurationHelperImpl
	implements ScriptManagementConfigurationHelper {

	@Override
	public boolean isAllowScriptContentBeExecutedOrIncluded() {
		return _systemScriptManagementConfiguration.
			allowScriptContentBeExecutedOrIncluded();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemScriptManagementConfiguration =
			ConfigurableUtil.createConfigurable(
				ScriptManagementConfiguration.class, properties);
	}

	private volatile ScriptManagementConfiguration
		_systemScriptManagementConfiguration;

}