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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 */
public class MVCActionCommandCache {

	public static final String ACTION_PACKAGE_NAME = "action.package.prefix";

	public static final MVCActionCommand EMPTY = new MVCActionCommand() {

		@Override
		public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse) {

			return false;
		}

	};

	public MVCActionCommandCache(String packagePrefix, String portletName) {
		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(mvc.command.name=*)(javax.portlet.name=" + portletName +
				")(objectClass=" + MVCActionCommand.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new MVCActionCommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public MVCActionCommand getMVCActionCommand(String mvcActionCommandName) {
		String className = null;

		try {
			MVCActionCommand mvcActionCommand = _mvcActionCommandCache.get(
				mvcActionCommandName);

			if (mvcActionCommand != null) {
				return mvcActionCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return EMPTY;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(mvcActionCommandName.charAt(0)));
			sb.append(mvcActionCommandName.substring(1));
			sb.append(_ACTION_COMMAND_POSTFIX);

			className = sb.toString();

			mvcActionCommand = (MVCActionCommand)InstanceFactory.newInstance(
				className);

			_mvcActionCommandCache.put(mvcActionCommandName, mvcActionCommand);

			return mvcActionCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to instantiate MVCActionCommand " + className);
			}

			_mvcActionCommandCache.put(mvcActionCommandName, EMPTY);

			return EMPTY;
		}
	}

	public List<MVCActionCommand> getMVCActionCommandChain(
		String mvcActionCommandChain) {

		List<MVCActionCommand> mvcActionCommands =
			_mvcActionCommandChainCache.get(mvcActionCommandChain);

		if (mvcActionCommands != null) {
			return mvcActionCommands;
		}

		mvcActionCommands = new ArrayList<>();

		String[] mvcActionCommandNames = StringUtil.split(
			mvcActionCommandChain);

		for (String mvcActionCommandName : mvcActionCommandNames) {
			MVCActionCommand mvcActionCommand = getMVCActionCommand(
				mvcActionCommandName);

			if (mvcActionCommand != EMPTY) {
				mvcActionCommands.add(mvcActionCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find MVCActionCommand " +
							mvcActionCommandChain);
				}
			}
		}

		_mvcActionCommandChainCache.put(
			mvcActionCommandChain, mvcActionCommands);

		return mvcActionCommands;
	}

	public boolean isEmpty() {
		return _mvcActionCommandCache.isEmpty();
	}

	private static final String _ACTION_COMMAND_POSTFIX = "MVCActionCommand";

	private static final Log _log = LogFactoryUtil.getLog(
		MVCActionCommandCache.class);

	private final Map<String, MVCActionCommand> _mvcActionCommandCache =
		new ConcurrentHashMap<>();
	private final Map<String, List<MVCActionCommand>>
		_mvcActionCommandChainCache = new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private final ServiceTracker<MVCActionCommand, MVCActionCommand>
		_serviceTracker;

	private class MVCActionCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<MVCActionCommand, MVCActionCommand> {

		@Override
		public MVCActionCommand addingService(
			ServiceReference<MVCActionCommand> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			MVCActionCommand mvcActionCommand = registry.getService(
				serviceReference);

			String commandName = (String)serviceReference.getProperty(
				"mvc.command.name");

			_mvcActionCommandCache.put(commandName, mvcActionCommand);

			return mvcActionCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<MVCActionCommand> serviceReference,
			MVCActionCommand mvcActionCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<MVCActionCommand> serviceReference,
			MVCActionCommand mvcActionCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String commandName = (String)serviceReference.getProperty(
				"mvc.command.name");

			_mvcActionCommandCache.remove(commandName);

			for (List<MVCActionCommand> mvcActionCommands :
					_mvcActionCommandChainCache.values()) {

				mvcActionCommands.remove(mvcActionCommand);
			}
		}

	}

}