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
public class ActionCommandCache {

	public static final String ACTION_PACKAGE_NAME = "action.package.prefix";

	public static final ActionCommand EMPTY = new ActionCommand() {

		@Override
		public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse) {

			return false;
		}

	};

	public ActionCommandCache(String packagePrefix, String portletName) {
		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(action.command.name=*)(javax.portlet.name=" + portletName +
				")(objectClass=" + ActionCommand.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new ActionCommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public ActionCommand getActionCommand(String actionCommandName) {
		String className = null;

		try {
			ActionCommand actionCommand = _actionCommandCache.get(
				actionCommandName);

			if (actionCommand != null) {
				return actionCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return EMPTY;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(actionCommandName.charAt(0)));
			sb.append(actionCommandName.substring(1));
			sb.append(_ACTION_COMMAND_POSTFIX);

			className = sb.toString();

			actionCommand = (ActionCommand)InstanceFactory.newInstance(
				className);

			_actionCommandCache.put(actionCommandName, actionCommand);

			return actionCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate ActionCommand " + className);
			}

			_actionCommandCache.put(actionCommandName, EMPTY);

			return EMPTY;
		}
	}

	public List<ActionCommand> getActionCommandChain(
		String actionCommandChain) {

		List<ActionCommand> actionCommands = _actionCommandChainCache.get(
			actionCommandChain);

		if (actionCommands != null) {
			return actionCommands;
		}

		actionCommands = new ArrayList<ActionCommand>();

		String[] actionCommandNames = StringUtil.split(actionCommandChain);

		for (String actionCommandName : actionCommandNames) {
			ActionCommand actionCommand = getActionCommand(actionCommandName);

			if (actionCommand != EMPTY) {
				actionCommands.add(actionCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find ActionCommand " + actionCommandChain);
				}
			}
		}

		_actionCommandChainCache.put(actionCommandChain, actionCommands);

		return actionCommands;
	}

	public boolean isEmpty() {
		return _actionCommandCache.isEmpty();
	}

	private static final String _ACTION_COMMAND_POSTFIX = "ActionCommand";

	private static final Log _log = LogFactoryUtil.getLog(
		ActionCommandCache.class);

	private final Map<String, ActionCommand> _actionCommandCache =
		new ConcurrentHashMap<String, ActionCommand>();
	private final Map<String, List<ActionCommand>> _actionCommandChainCache =
		new ConcurrentHashMap<String, List<ActionCommand>>();
	private final String _packagePrefix;
	private final ServiceTracker<ActionCommand, ActionCommand> _serviceTracker;

	private class ActionCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ActionCommand, ActionCommand> {

		@Override
		public ActionCommand addingService(
			ServiceReference<ActionCommand> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ActionCommand actionCommand = registry.getService(serviceReference);

			String actionCommandName = (String)serviceReference.getProperty(
				"action.command.name");

			_actionCommandCache.put(actionCommandName, actionCommand);

			return actionCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<ActionCommand> serviceReference,
			ActionCommand actionCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<ActionCommand> serviceReference,
			ActionCommand actionCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String actionCommandName = (String)serviceReference.getProperty(
				"action.command.name");

			_actionCommandCache.remove(actionCommandName);

			for (List<ActionCommand> actionCommands :
					_actionCommandChainCache.values()) {

				actionCommands.remove(actionCommand);
			}
		}

	}

}