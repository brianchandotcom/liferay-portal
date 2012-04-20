/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.image;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.SystemEnv;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.log.Log4jLogFactoryImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.log4j.Log4JUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 */
public class ImageMagickImpl implements ImageMagick {

	public static ImageMagickImpl getInstance() {
		return _instance;
	}

	public void convert(List<String> arguments, boolean fork) throws Exception {
		if (!isEnabled()) {
			throw new IllegalStateException(
				"Cannot call \"convert\" when ImageMagick is disabled");
		}

		if (fork) {
			ProcessCallable<String[]> processCallable =
				new ImageMagickProcessCallable(
					ServerDetector.getServerId(),
					PropsUtil.get(PropsKeys.LIFERAY_HOME),
					Log4JUtil.getCustomLogSettings(), _globalSearchPath,
					getResourceLimits(), arguments, true);

			Future<String[]> future = ProcessExecutor.execute(
				ClassPathUtil.getPortalClassPath(), processCallable);

			future.get();
		}
		else {
			LiferayConvertCmd.run(
				_globalSearchPath, getResourceLimits(), arguments);
		}
	}

	public String getGlobalSearchPath() throws Exception {
		PortletPreferences preferences = PrefsPropsUtil.getPreferences();

		String globalSearchPath = preferences.getValue(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, null);

		if (Validator.isNotNull(globalSearchPath)) {
			return globalSearchPath;
		}

		String filterName = null;

		if (OSDetector.isApple()) {
			filterName = "apple";
		}
		else if (OSDetector.isWindows()) {
			filterName = "windows";
		}
		else {
			filterName = "unix";
		}

		return PropsUtil.get(
			PropsKeys.IMAGEMAGICK_GLOBAL_SEARCH_PATH, new Filter(filterName));
	}

	public Properties getResourceLimitsProperties() throws Exception {
		Properties resourceLimitsProperties = PrefsPropsUtil.getProperties(
			PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);

		if (resourceLimitsProperties.isEmpty()) {
			resourceLimitsProperties = PropsUtil.getProperties(
				PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);
		}

		return resourceLimitsProperties;
	}

	public String[] identify(List<String> arguments, boolean fork)
		throws Exception {

		if (!isEnabled()) {
			throw new IllegalStateException(
				"Cannot call \"identify\" when ImageMagick is disabled");
		}

		if (fork) {
			ProcessCallable<String[]> processCallable =
				new ImageMagickProcessCallable(
					ServerDetector.getServerId(),
					PropsUtil.get(PropsKeys.LIFERAY_HOME),
					Log4JUtil.getCustomLogSettings(), _globalSearchPath,
					getResourceLimits(), arguments, false);

			Future<String[]> future = ProcessExecutor.execute(
				ClassPathUtil.getPortalClassPath(), processCallable);

			return future.get();
		}
		else {
			return LiferayIdentifyCmd.run(
				_globalSearchPath, getResourceLimits(), arguments);
		}
	}

	public boolean isEnabled() {
		try {
			return PrefsPropsUtil.getBoolean(PropsKeys.IMAGEMAGICK_ENABLED);
		}
		catch (Exception e) {
			_log.warn(e, e);
		}

		return false;
	}

	public void reset() {
		if (isEnabled()) {
			try {
				_globalSearchPath = getGlobalSearchPath();

				_resourceLimitsProperties = getResourceLimitsProperties();
			}
			catch (Exception e) {
				_log.warn(e, e);
			}
		}
	}

	protected LinkedList<String> getResourceLimits() {
		LinkedList<String> resourceLimits = new LinkedList<String>();

		if (_resourceLimitsProperties == null) {
			return resourceLimits;
		}

		for (Object key : _resourceLimitsProperties.keySet()) {
			String value = (String)_resourceLimitsProperties.get(key);

			if (Validator.isNull(value)) {
				continue;
			}

			resourceLimits.add("-limit");
			resourceLimits.add((String)key);
			resourceLimits.add(value);
		}

		return resourceLimits;
	}

	private static Log _log = LogFactoryUtil.getLog(ImageMagickImpl.class);

	private static ImageMagickImpl _instance = new ImageMagickImpl();

	private String _globalSearchPath;
	private Properties _resourceLimitsProperties;

	private static class ImageMagickProcessCallable
		implements ProcessCallable<String[]> {

		public ImageMagickProcessCallable(
			String serverId, String liferayHome,
			Map<String, String> customLogSettings, String globalSearchPath,
			List<String> resourceLimits,
			List<String> commandArguments, boolean convert) {

			_serverId = serverId;
			_liferayHome = liferayHome;
			_customLogSettings = customLogSettings;
			_globalSearchPath = globalSearchPath;
			_commandArguments = commandArguments;
			_resourceLimits = resourceLimits;
			_convert = convert;
		}

		public String[] call() throws ProcessException {
			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			Properties systemProperties = System.getProperties();

			SystemEnv.setProperties(systemProperties);

			Log4JUtil.initLog4J(
				_serverId, _liferayHome, classLoader, new Log4jLogFactoryImpl(),
				_customLogSettings);

			try {
				if (_convert) {
					LiferayConvertCmd.run(
						_globalSearchPath, _resourceLimits, _commandArguments);
				}
				else {
					return LiferayIdentifyCmd.run(
						_globalSearchPath, _resourceLimits, _commandArguments);
				}
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		private List<String> _commandArguments;
		private boolean _convert;
		private Map<String, String> _customLogSettings;
		private String _globalSearchPath;
		private String _liferayHome;
		private List<String> _resourceLimits;
		private String _serverId;

	}

}