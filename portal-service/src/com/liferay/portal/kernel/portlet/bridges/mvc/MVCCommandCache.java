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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sergio González
 */
public class MVCCommandCache {

	public MVCCommandCache(
		MVCCommand emptyMVCCommand, String packagePrefix, String portletName,
		String mvcCommandClassName, String mvcCommandPostFix) {

		_emptyMVCCommand = emptyMVCCommand;
		_mvcComandPostFix = mvcCommandPostFix;

		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(javax.portlet.name=" + portletName +")(mvc.command.name=*)" +
				"(objectClass=" + mvcCommandClassName + "))");

		_serviceTracker = registry.trackServices(
			filter, new MVCCommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public MVCCommand getMVCCommand(String mvcCommandName) {
		String className = null;

		try {
			MVCCommand mvcCommand = _mvcCommandCache.get(mvcCommandName);

			if (mvcCommand != null) {
				return mvcCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return _emptyMVCCommand;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(mvcCommandName.charAt(0)));
			sb.append(mvcCommandName.substring(1));
			sb.append(_mvcComandPostFix);

			className = sb.toString();

			mvcCommand = (MVCCommand)InstanceFactory.newInstance(className);

			_mvcCommandCache.put(mvcCommandName, mvcCommand);

			return mvcCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate MVCCommand " + className);
			}

			_mvcCommandCache.put(mvcCommandName, _emptyMVCCommand);

			return _emptyMVCCommand;
		}
	}

	public List<? extends MVCCommand> getMVCCommands(String key) {
		List<MVCCommand> mvcCommands = _mvcCommands.get(key);

		if (mvcCommands != null) {
			return mvcCommands;
		}

		mvcCommands = new ArrayList<>();

		String[] mvcCommandNames = StringUtil.split(key);

		for (String mvcCommandName : mvcCommandNames) {
			MVCCommand mvcCommand = getMVCCommand(mvcCommandName);

			if (mvcCommand != _emptyMVCCommand) {
				mvcCommands.add(mvcCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find MVCCommand " + key);
				}
			}
		}

		_mvcCommands.put(key, mvcCommands);

		return mvcCommands;
	}

	public boolean isEmpty() {
		return _mvcCommandCache.isEmpty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MVCCommandCache.class);

	private final MVCCommand _emptyMVCCommand;
	private final String _mvcComandPostFix;
	private final Map<String, MVCCommand> _mvcCommandCache =
		new ConcurrentHashMap<>();
	private final Map<String, List<MVCCommand>> _mvcCommands =
		new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private final ServiceTracker<MVCCommand, MVCCommand> _serviceTracker;

	private class MVCCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MVCCommand, MVCCommand> {

		@Override
		public MVCCommand addingService(
			ServiceReference<MVCCommand> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MVCCommand mvcCommand = registry.getService(serviceReference);

			List<String> mvcCommandNames = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			for (String mvcCommandName : mvcCommandNames) {
				_mvcCommandCache.put(mvcCommandName, mvcCommand);
			}

			return mvcCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<MVCCommand> serviceReference,
			MVCCommand mvcCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<MVCCommand> serviceReference,
			MVCCommand mvcCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> mvcCommandNames = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			for (String mvcCommandName : mvcCommandNames) {
				_mvcCommandCache.remove(mvcCommandName);

				for (List<MVCCommand> mvcCommands : _mvcCommands.values()) {
					mvcCommands.remove(mvcCommand);
				}
			}
		}

	}

}