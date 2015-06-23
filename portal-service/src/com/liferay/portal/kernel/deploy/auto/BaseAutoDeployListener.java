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

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipFile;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public abstract class BaseAutoDeployListener implements AutoDeployListener {

	public boolean isExtPlugin(File file) {
		Matcher matcher = _extPluginPattern.matcher(file.getName());

		return matcher.find();
	}

	public boolean isHookPlugin(File file) throws AutoDeployException {
		Matcher matcher = _hookPluginPattern.matcher(file.getName());

		if (matcher.find() &&
			isMatchingFile(file, "WEB-INF/liferay-hook.xml", false) &&
			!isMatchingFile(file, "WEB-INF/liferay-portlet.xml", false)) {

			return true;
		}

		return false;
	}

	public boolean isLayoutTemplatePlugin(File file)
		throws AutoDeployException {

		if (isMatchingFile(file, "WEB-INF/liferay-layout-templates.xml") &&
			!isThemePlugin(file)) {

			return true;
		}

		return false;
	}

	public boolean isLiferayPackage(File file) {
		String fileName = file.getName();

		if (fileName.endsWith(".lpkg")) {
			return true;
		}

		return false;
	}

	public boolean isMatchingFile(File file, String checkXmlFile)
		throws AutoDeployException {

		return isMatchingFile(file, checkXmlFile, true);
	}

	public boolean isMatchingFile(
			File file, String checkXmlFile, boolean checkFileExtension)
		throws AutoDeployException {

		if (checkFileExtension && !isMatchingFileExtension(file)) {
			return false;
		}

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file);

			if (zipFile.getEntry(checkXmlFile) == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						file.getPath() + " does not have " + checkXmlFile);
				}

				return false;
			}
			else {
				return true;
			}
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe);
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

	public boolean isMatchingFileExtension(File file) {
		return isMatchingFileExtension(file, ".war", ".zip");
	}

	public boolean isMatchingFileExtension(File file, String ... extensions) {
		String fileName = file.getName();

		fileName = StringUtil.toLowerCase(fileName);

		for (String extension : extensions) {
			if (fileName.endsWith(extension)) {
				if (_log.isDebugEnabled()) {
					_log.debug(file.getPath() + " has a matching extension");
				}

				return true;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(file.getPath() + " does not have a matching extension");
		}

		return false;
	}

	public boolean isThemePlugin(File file) throws AutoDeployException {
		if (isMatchingFile(file, "WEB-INF/liferay-look-and-feel.xml")) {
			return true;
		}

		String fileName = file.getName();

		Matcher matcher = _themePluginPattern.matcher(fileName);

		if (matcher.find() &&
			isMatchingFile(
				file, "WEB-INF/liferay-plugin-package.properties", false)) {

			return true;
		}

		return false;
	}

	public boolean isWebPlugin(File file) throws AutoDeployException {
		Matcher matcher = _webPluginPattern.matcher(file.getName());

		if (matcher.find() &&
			isMatchingFile(
				file, "WEB-INF/liferay-plugin-package.properties", false)) {

			return true;
		}

		return false;
	}

	protected boolean isJarFile(File file) {
		return isMatchingFileExtension(file, ".jar");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAutoDeployListener.class);

	private static final Pattern _extPluginPattern = Pattern.compile(
		"-(E|e)xt[-0-9.]*\\+?\\.(war|zip)$");
	private static final Pattern _hookPluginPattern = Pattern.compile(
		"-(H|h)ook[-0-9.]*\\+?\\.(war|zip)$");
	private static final Pattern _themePluginPattern = Pattern.compile(
		"-(T|t)heme[-0-9.]*\\+?\\.(war|zip)$");
	private static final Pattern _webPluginPattern = Pattern.compile(
		"-(W|w)eb[-0-9.]*\\+?\\.(war|zip)$");

}