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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeLoader {

	public File getFileStorage() {
		return _fileStorage;
	}

	public String getServletContextName() {
		return _servletContextName;
	}

	public String getThemesPath() {
		return _themesPath;
	}

	public synchronized void loadThemes() {
		if (_log.isInfoEnabled()) {
			_log.info("Loading themes in " + _fileStorage);
		}

		File[] files = _fileStorage.listFiles();

		if (files == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"There are no directories to process for " + _fileStorage);
			}

			return;
		}

		for (File directory : files) {
			if (_log.isDebugEnabled()) {
				_log.debug("Process directory " + directory);
			}

			File liferayLookAndFeelXML = new File(
				directory + "/liferay-look-and-feel.xml");

			if (liferayLookAndFeelXML.exists()) {
				String lastModifiedKey = liferayLookAndFeelXML.toString();

				Long prevLastModified = _lastModifiedMap.get(lastModifiedKey);

				long lastModified = liferayLookAndFeelXML.lastModified();

				if ((prevLastModified == null) ||
					(prevLastModified.longValue() < lastModified)) {

					registerTheme(liferayLookAndFeelXML);

					_lastModifiedMap.put(lastModifiedKey, lastModified);
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Do not refresh " + liferayLookAndFeelXML +
								" because it is has not been modified");
					}
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(liferayLookAndFeelXML + " does not exist");
				}
			}
		}
	}

	protected ThemeLoader(
		String servletContextName, ServletContext servletContext,
		String[] xmls) {

		_servletContextName = servletContextName;
		_servletContext = servletContext;

		boolean loadFromServletContext = true;
		File fileStorage = null;
		String themesPath = null;

		try {
			Document doc = UnsecureSAXReaderUtil.read(xmls[0], true);

			Element root = doc.getRootElement();

			themesPath = GetterUtil.getString(
				root.elementText("themes-path"), "/themes");

			String fileStorageValue = PropsValues.THEME_LOADER_STORAGE_PATH;

			fileStorageValue = GetterUtil.getString(
				root.elementText("file-storage"), fileStorageValue);

			if (Validator.isNotNull(fileStorageValue)) {
				fileStorage = new File(fileStorageValue);
				loadFromServletContext = false;
			}
			else {
				fileStorage = new File(servletContext.getRealPath(themesPath));
				loadFromServletContext = true;
			}

			if (!fileStorage.exists()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"File storage " + fileStorage + " does not exist");
				}

				if (!fileStorage.mkdirs()) {
					_log.error(
						"Unable to create theme loader file storage at " +
							fileStorage);
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_loadFromServletContext = loadFromServletContext;
		_fileStorage = fileStorage;
		_themesPath = themesPath;

		loadThemes();
	}

	protected void destroy() {
	}

	protected void registerTheme(File liferayLookAndFeelXML) {
		if (_log.isDebugEnabled()) {
			_log.debug("Registering " + liferayLookAndFeelXML);
		}

		try {
			String content = FileUtil.read(liferayLookAndFeelXML);

			ThemeLocalServiceUtil.init(
				_servletContextName, _servletContext, _themesPath,
				_loadFromServletContext, new String[] {content}, null);
		}
		catch (Exception e) {
			_log.error(
				"Error registering theme " + liferayLookAndFeelXML.toString(),
				e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ThemeLoader.class);

	private final File _fileStorage;
	private final Map<String, Long> _lastModifiedMap = new HashMap<>();
	private final boolean _loadFromServletContext;
	private final ServletContext _servletContext;
	private final String _servletContextName;
	private final String _themesPath;

}