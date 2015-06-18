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
public class MVCCommandCache<T> {

	public MVCCommandCache(
		T emptyMVCCommand, String packagePrefix, String portletName,
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

	public T getMVCCommand(String mvcCommandName) {
		String className = null;

		try {
			T mvcCommand = (T)_mvcCommandCache.get(mvcCommandName);

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

			mvcCommand = (T)InstanceFactory.newInstance(className);

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

	public List<T> getMVCCommandChain(String mvcCommandChain) {
		List<T> mvcCommands = _mvcCommandChainCache.get(mvcCommandChain);

		if (mvcCommands != null) {
			return mvcCommands;
		}

		mvcCommands = new ArrayList<>();

		String[] mvcCommandNames = StringUtil.split(mvcCommandChain);

		for (String mvcCommandName : mvcCommandNames) {
			T mvcCommand = getMVCCommand(mvcCommandName);

			if (mvcCommand != _emptyMVCCommand) {
				mvcCommands.add(mvcCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find MVCCommand " + mvcCommandChain);
				}
			}
		}

		_mvcCommandChainCache.put(mvcCommandChain, mvcCommands);

		return mvcCommands;
	}

	public boolean isEmpty() {
		return _mvcCommandCache.isEmpty();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MVCCommandCache.class);

	private final T _emptyMVCCommand;
	private final String _mvcComandPostFix;
	private final Map<String, T> _mvcCommandCache = new ConcurrentHashMap<>();
	private final Map<String, List<T>> _mvcCommandChainCache =
		new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private final ServiceTracker<T, T> _serviceTracker;

	private class MVCCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<T, T> {

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			T mvcCommand = registry.getService(serviceReference);

			List<String> mvcCommandNames = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			for (String mvcCommandName : mvcCommandNames) {
				_mvcCommandCache.put(mvcCommandName, mvcCommand);
			}

			return mvcCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T mvcCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T mvcCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String mvcCommandName = (String)serviceReference.getProperty(
				"mvc.command.name");

			_mvcCommandCache.remove(mvcCommandName);

			for (List<T> mvcCommands : _mvcCommandChainCache.values()) {
				mvcCommands.remove(mvcCommand);
			}
		}

	}

}