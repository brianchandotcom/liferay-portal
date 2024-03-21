/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.web.internal.configuration.helper;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.security.script.management.configuration.ScriptManagementConfiguration;
import com.liferay.portal.security.script.management.configuration.helper.ScriptManagementConfigurationHelper;

import java.io.IOException;

import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

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

	@Override
	public boolean isScriptManagementConfigurationDefined()
		throws ConfigurationException {

		try {
			String filterString = StringBundler.concat(
				"(", ConfigurationAdmin.SERVICE_FACTORYPID, StringPool.EQUAL,
				ScriptManagementConfiguration.class.getName(), ")");

			if (_configurationAdmin.listConfigurations(filterString) != null) {
				return true;
			}

			return false;
		}
		catch (InvalidSyntaxException | IOException exception) {
			throw new ConfigurationException(exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_systemScriptManagementConfiguration =
			ConfigurableUtil.createConfigurable(
				ScriptManagementConfiguration.class, properties);
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	private volatile ScriptManagementConfiguration
		_systemScriptManagementConfiguration;

}