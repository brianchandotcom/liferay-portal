/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Mariano Álvaro Sáiz
 */
public abstract class BaseVirtualInstanceOperation {

	public void onVirtualInstance(
		Callable<Company> callable, Map<String, Object> properties) {

		try {
			Company company = callable.call();

			if (company != null) {
				_deleteConfiguration(
					_PORTAL_INSTANCES_CONFIGURATION_PID + "~" +
						company.getWebId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to perform operation over virtual instance", exception);
		}
		finally {
			_deleteConfiguration((String)properties.get("service.pid"));
		}
	}

	private void _deleteConfiguration(String pid) {
		try {
			Files.deleteIfExists(
				Paths.get(
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
					pid.concat(".config")));
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private static final String _PORTAL_INSTANCES_CONFIGURATION_PID =
		"com.liferay.portal.instances.internal.configuration." +
			"PortalInstancesConfiguration";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseVirtualInstanceOperation.class);

}