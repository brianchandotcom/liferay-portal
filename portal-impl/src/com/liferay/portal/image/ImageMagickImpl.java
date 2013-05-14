/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.portlet.PortletPreferences;

import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessExecutor;
import org.im4java.process.ProcessTask;

/**
 * @author Alexander Chow
 * @author Ivica Cardic
 */
@DoPrivileged
public class ImageMagickImpl implements ImageMagick {

	public static ImageMagickImpl getInstance() {
		return _instance;
	}

	@Override
	public Future<?> convert(List<String> arguments) throws Exception {
		if (!isEnabled()) {
			throw new IllegalStateException(
				"Cannot call \"convert\" when ImageMagick is disabled");
		}

		ProcessExecutor processExecutor = _getProcessExecutor();

		LiferayConvertCmd liferayConvertCmd = new LiferayConvertCmd();

		ProcessTask processTask = liferayConvertCmd.getProcessTask(
			_globalSearchPath, getResourceLimits(), arguments);

		processExecutor.execute(processTask);

		return processTask;
	}

	@Override
	public void destroy() {
		if (_processExecutor == null) {
			return;
		}

		synchronized (ProcessExecutor.class) {
			_processExecutor.shutdownNow();
		}

		_processExecutor = null;
	}

	@Override
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

	@Override
	public Properties getResourceLimitsProperties() throws Exception {
		Properties resourceLimitsProperties = PrefsPropsUtil.getProperties(
			PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);

		if (resourceLimitsProperties.isEmpty()) {
			resourceLimitsProperties = PropsUtil.getProperties(
				PropsKeys.IMAGEMAGICK_RESOURCE_LIMIT, true);
		}

		return resourceLimitsProperties;
	}

	@Override
	public String[] identify(List<String> arguments) throws Exception {
		if (!isEnabled()) {
			throw new IllegalStateException(
				"Cannot call \"identify\" when ImageMagick is disabled");
		}

		ProcessExecutor processExecutor = _getProcessExecutor();

		LiferayIdentifyCmd liferayIdentifyCmd = new LiferayIdentifyCmd();

		ArrayListOutputConsumer arrayListOutputConsumer =
			new ArrayListOutputConsumer();

		liferayIdentifyCmd.setOutputConsumer(arrayListOutputConsumer);

		ProcessTask processTask = liferayIdentifyCmd.getProcessTask(
			_globalSearchPath, getResourceLimits(), arguments);

		processExecutor.execute(processTask);

		processTask.get();

		List<String> output = arrayListOutputConsumer.getOutput();

		if (output != null) {
			return output.toArray(new String[output.size()]);
		}

		return new String[0];
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = false;

		try {
			enabled = PrefsPropsUtil.getBoolean(PropsKeys.IMAGEMAGICK_ENABLED);
		}
		catch (Exception e) {
			_log.warn(e, e);
		}

		if (!enabled && !_warned) {
			StringBundler sb = new StringBundler(7);

			sb.append("Liferay is not configured to use ImageMagick and ");
			sb.append("Ghostscript. For better quality document and image ");
			sb.append("previews, install ImageMagick and Ghostscript. Enable ");
			sb.append("ImageMagick in portal-ext.properties or in the Server ");
			sb.append("Administration section of the Control Panel at: ");
			sb.append("http://<server>/group/control_panel/manage/-/server/");
			sb.append("external-services");

			_log.warn(sb.toString());

			_warned = true;
		}

		return enabled;
	}

	@Override
	public void reset() {
		if (isEnabled()) {
			try {
				_globalSearchPath = getGlobalSearchPath();

				_resourceLimitsProperties = getResourceLimitsProperties();
			}
			catch (Exception e) {
				_log.error(e, e);
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

	private ProcessExecutor _getProcessExecutor() {
		if (_processExecutor != null) {
			return _processExecutor;
		}

		synchronized (ProcessExecutor.class) {
			if (_processExecutor == null) {
				_processExecutor = new ProcessExecutor();

				_processExecutor.setThreadFactory(
					new NamedThreadFactory(
						ImageMagickImpl.class.getName(), Thread.MIN_PRIORITY,
						ClassLoaderUtil.getPortalClassLoader()));
			}
		}

		return _processExecutor;
	}

	private static Log _log = LogFactoryUtil.getLog(ImageMagickImpl.class);

	private static ImageMagickImpl _instance = new ImageMagickImpl();

	private String _globalSearchPath;
	private volatile ProcessExecutor _processExecutor;
	private Properties _resourceLimitsProperties;
	private boolean _warned;

}