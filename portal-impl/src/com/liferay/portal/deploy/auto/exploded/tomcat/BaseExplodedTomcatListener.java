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

package com.liferay.portal.deploy.auto.exploded.tomcat;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.configuration.SystemConfiguration;

/**
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public abstract class BaseExplodedTomcatListener implements AutoDeployListener {

	public void copyContextFile(File file) throws AutoDeployException {
		try {
			String tomcatConfDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_TOMCAT_CONF_DIR,
				PropsValues.AUTO_DEPLOY_TOMCAT_CONF_DIR);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Copying file " + file.getPath() + " to " + tomcatConfDir);
			}

			FileUtil.copyFile(
				file, new File(tomcatConfDir + "/" + file.getName()));
		}
		catch (Exception e) {
			throw new AutoDeployException(e.getMessage());
		}
	}

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		return deploy(file);
	}

	public File getDocBaseDir(File file, String checkXmlFile)
		throws AutoDeployException {

		if (!isMatchingFileExtension(file)) {
			return null;
		}

		String docBase = null;

		try {
			String content = FileUtil.read(file);

			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			docBase = rootElement.attributeValue("docBase");

			docBase = String.valueOf(
				PropertyConverter.interpolate(docBase, _systemConfiguration));
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}

		if (Validator.isNull(docBase)) {
			if (_log.isDebugEnabled()) {
				_log.debug(file.getPath() + " does not have a docBase defined");
			}

			return null;
		}

		File docBaseDir = new File(docBase);

		if (!docBaseDir.exists()) {
			if (_log.isDebugEnabled()) {
				_log.debug(docBase + " does not exist");
			}

			return null;
		}

		if (!docBaseDir.isDirectory()) {
			if (_log.isDebugEnabled()) {
				_log.debug(docBase + " is not a directory");
			}

			return null;
		}

		if (!FileUtil.exists(docBase + "/" + checkXmlFile)) {
			if (_log.isDebugEnabled()) {
				_log.debug(docBase + " does not have " + checkXmlFile);
			}

			return null;
		}

		return docBaseDir;
	}

	public boolean isMatchingFileExtension(File file) {
		if (file.getName().endsWith(".xml")) {
			if (_log.isDebugEnabled()) {
				_log.debug(file.getPath() + " has a matching extension");
			}

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(file.getPath() + " does not have a matching extension");
		}

		return false;
	}

	protected abstract int deploy(File file) throws AutoDeployException;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseExplodedTomcatListener.class);

	private static final SystemConfiguration _systemConfiguration =
		new SystemConfiguration();

}