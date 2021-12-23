/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.configuration.persistence.internal.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.file.install.constants.FileInstallConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.Dictionary;
import java.util.Enumeration;

import org.apache.felix.cm.PersistenceManager;

/**
 * @author Preston Crary
 */
public class ConfigurationUpgradeStepFactoryImpl
	implements ConfigurationUpgradeStepFactory {

	public ConfigurationUpgradeStepFactoryImpl(
		PersistenceManager persistenceManager) {

		_persistenceManager = persistenceManager;
	}

	@Override
	public UpgradeStep createUpgradeStep(String oldPid, String newPid) {
		return dbProcessContext -> {
			try {
				Enumeration<Dictionary<String, String>> enumeration =
					_persistenceManager.getDictionaries();

				while (enumeration.hasMoreElements()) {
					Dictionary<String, String> dictionary =
						enumeration.nextElement();

					String oldServicePid = dictionary.get("service.pid");

					if (!oldPid.equals(oldServicePid)) {
						if (!oldPid.equals(
								dictionary.get("service.factoryPid"))) {

							continue;
						}

						dictionary.put("service.factoryPid", newPid);
					}

					String newServicePid = StringUtil.replace(
						oldServicePid, oldPid, newPid);

					dictionary.put("service.pid", newServicePid);

					String fileName = dictionary.get(
						FileInstallConstants.FELIX_FILE_INSTALL_FILENAME);

					if (fileName != null) {
						dictionary.put(
							FileInstallConstants.FELIX_FILE_INSTALL_FILENAME,
							StringUtil.replace(fileName, oldPid, newPid));
					}

					_persistenceManager.store(newServicePid, dictionary);

					_persistenceManager.delete(oldServicePid);
				}

				File configResourcesDir = new File(
					PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR);

				for (File file : configResourcesDir.listFiles()) {
					String fileName = file.getName();

					if (fileName.equals(oldPid.concat(".cfg")) ||
						fileName.equals(oldPid.concat(".config")) ||
						fileName.startsWith(oldPid.concat(StringPool.DASH))) {

						File newFile = new File(
							StringUtil.replace(file.getPath(), oldPid, newPid));

						if (newFile.exists()) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									StringBundler.concat(
										"Unable to rename ",
										file.getAbsolutePath(), " to ",
										newFile.getAbsolutePath(),
										" because the file already exists"));
							}

							continue;
						}

						Files.move(file.toPath(), newFile.toPath());
					}
				}
			}
			catch (IOException ioException) {
				throw new UpgradeException(ioException);
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationUpgradeStepFactoryImpl.class);

	private final PersistenceManager _persistenceManager;

}