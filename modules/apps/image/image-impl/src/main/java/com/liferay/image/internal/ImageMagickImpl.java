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

package com.liferay.image.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.portlet.PortletPreferences;

import org.im4java.process.ArrayListOutputConsumer;
import org.im4java.process.ProcessExecutor;
import org.im4java.process.ProcessTask;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alexander Chow
 * @author Ivica Cardic
 */
@Component(immediate = true, service = ImageMagick.class)
public class ImageMagickImpl implements ImageMagick {

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
		PortletPreferences preferences = PrefsPropsUtil.getPreferences(true);

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
			return output.toArray(new String[0]);
		}

		return new String[0];
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = false;

		try {
			enabled = PrefsPropsUtil.getBoolean(PropsKeys.IMAGEMAGICK_ENABLED);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		if (!enabled && !_warned && _log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Liferay is not configured to use ImageMagick and ",
					"Ghostscript. For better quality document and image ",
					"previews, install ImageMagick and Ghostscript. Enable ",
					"ImageMagick in portal-ext.properties or in the Server ",
					"Administration section of the Control Panel at: ",
					"http://<server>/group/control_panel/manage/-/server",
					"/external-services."));

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
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	protected LinkedList<String> getResourceLimits() {
		LinkedList<String> resourceLimits = new LinkedList<>();

		if (_resourceLimitsProperties == null) {
			return resourceLimits;
		}

		for (Map.Entry<Object, Object> entry :
				_resourceLimitsProperties.entrySet()) {

			String value = (String)entry.getValue();

			if (Validator.isNull(value)) {
				continue;
			}

			resourceLimits.add("-limit");
			resourceLimits.add((String)entry.getKey());
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
						PortalClassLoaderUtil.getClassLoader()));
			}
		}

		return _processExecutor;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageMagickImpl.class);

	private String _globalSearchPath;
	private volatile ProcessExecutor _processExecutor;
	private Properties _resourceLimitsProperties;
	private boolean _warned;

}