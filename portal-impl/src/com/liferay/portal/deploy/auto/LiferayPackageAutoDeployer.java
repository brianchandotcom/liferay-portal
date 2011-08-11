/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Ryan Park
 */
public class LiferayPackageAutoDeployer implements AutoDeployer {

	public LiferayPackageAutoDeployer() {
		try {
			baseDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void autoDeploy(String file) throws AutoDeployException {
		try {
			ZipFile zipFile = new ZipFile(
				new File(baseDir + StringPool.SLASH + file));

			Enumeration<? extends ZipEntry> enu = zipFile.entries();

			while (enu.hasMoreElements()) {
				ZipEntry entry = enu.nextElement();

				String fileName = entry.getName();

				if (!fileName.endsWith(".war") && !fileName.endsWith(".xml") &&
					!fileName.endsWith(".zip")) {

					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info("Extracting " + fileName + " from " + file);
				}

				InputStream inputStream = null;

				try {
					inputStream = zipFile.getInputStream(entry);

					FileUtil.write(
						baseDir + StringPool.SLASH + fileName, inputStream);
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	protected String baseDir;

	private static Log _log = LogFactoryUtil.getLog(
		LiferayPackageAutoDeployer.class);

}