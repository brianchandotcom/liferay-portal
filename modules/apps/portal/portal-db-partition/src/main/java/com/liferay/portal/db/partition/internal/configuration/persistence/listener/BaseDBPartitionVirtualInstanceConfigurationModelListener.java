/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
public abstract class BaseDBPartitionVirtualInstanceConfigurationModelListener
	implements ConfigurationModelListener {

	public BaseDBPartitionVirtualInstanceConfigurationModelListener(
		String pid) {

		_pid = pid;
	}

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			doPerformActions(properties);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception, getConfigurationClass(), getClass(), properties);
		}
		finally {
			_deleteConfiguration(pid);
		}
	}

	protected abstract void doPerformActions(
			Dictionary<String, Object> properties)
		throws Exception;

	protected abstract Class<?> getConfigurationClass();

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	private void _deleteConfiguration(String pid) {
		try {
			Configuration configuration = configurationAdmin.getConfiguration(
				pid, "?");

			if (configuration != null) {
				Files.deleteIfExists(
					Paths.get(
						PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR,
						_pid.concat(".config")));
			}
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDBPartitionVirtualInstanceConfigurationModelListener.class);

	private final String _pid;

}