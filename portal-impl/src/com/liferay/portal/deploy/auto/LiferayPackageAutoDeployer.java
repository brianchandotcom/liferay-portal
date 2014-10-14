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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Ryan Park
 */
public class LiferayPackageAutoDeployer implements AutoDeployer {

	public LiferayPackageAutoDeployer() {
		String baseDir = null;

		try {
			baseDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
				PropsValues.AUTO_DEPLOY_DEPLOY_DIR);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_baseDir = baseDir;
	}

	@Override
	public int autoDeploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		ZipFile zipFile = null;

		try {
			File file = autoDeploymentContext.getFile();

			zipFile = new ZipFile(file);

			List<String> fileNames = new ArrayList<String>(zipFile.size());
			String propertiesString = null;

			Enumeration<? extends ZipEntry> enu = zipFile.entries();

			while (enu.hasMoreElements()) {
				ZipEntry zipEntry = enu.nextElement();

				String zipEntryFileName = zipEntry.getName();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Extracting " + zipEntryFileName + " from " +
							file.getName());
				}

				InputStream inputStream = zipFile.getInputStream(zipEntry);

				if (zipEntryFileName.equals("liferay-marketplace.properties")) {
					inputStream = zipFile.getInputStream(zipEntry);

					propertiesString = StringUtil.read(inputStream);
				}
				else {
					fileNames.add(zipEntryFileName);

					FileUtil.write(
						_baseDir + StringPool.SLASH + zipEntryFileName,
						inputStream);
				}
			}

			if (propertiesString != null) {
				Message message = new Message();

				message.put("command", "deploy");
				message.put("fileNames", fileNames);
				message.put("properties", propertiesString);

				MessageBusUtil.sendMessage(
					DestinationNames.MARKETPLACE, message);
			}

			return AutoDeployer.CODE_DEFAULT;
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
		finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	@Override
	public AutoDeployer cloneAutoDeployer() {
		return new LiferayPackageAutoDeployer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPackageAutoDeployer.class);

	private final String _baseDir;

}