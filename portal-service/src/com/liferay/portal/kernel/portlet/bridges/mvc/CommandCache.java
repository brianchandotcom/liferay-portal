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
public class CommandCache<T> {

	public CommandCache(
		T emptyCommand, String packagePrefix, String portletName,
		String commandFieldName, String commandClassName,
		String commandPostFix) {

		_emptyCommand = emptyCommand;
		_comandPostFix = commandPostFix;
		_commandFieldName = commandFieldName;

		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(" + commandFieldName + "=*)(javax.portlet.name=" + portletName +
				")(objectClass=" + commandClassName + "))");

		_serviceTracker = registry.trackServices(
			filter, new CommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public T getCommand(String commandName) {
		String className = null;

		try {
			T command = (T)_commandCache.get(commandName);

			if (command != null) {
				return command;
			}

			if (Validator.isNull(_packagePrefix)) {
				return _emptyCommand;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(commandName.charAt(0)));
			sb.append(commandName.substring(1));
			sb.append(_comandPostFix);

			className = sb.toString();

			command = (T)InstanceFactory.newInstance(className);

			_commandCache.put(commandName, command);

			return command;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate Command " + className);
			}

			_commandCache.put(commandName, _emptyCommand);

			return _emptyCommand;
		}
	}

	public List<T> getCommandChain(String commandChain) {
		List<T> commands = _commandChainCache.get(commandChain);

		if (commands != null) {
			return commands;
		}

		commands = new ArrayList<>();

		String[] commandNames = StringUtil.split(commandChain);

		for (String commandName : commandNames) {
			T command = getCommand(commandName);

			if (command != _emptyCommand) {
				commands.add(command);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find Command " + commandChain);
				}
			}
		}

		_commandChainCache.put(commandChain, commands);

		return commands;
	}

	public boolean isEmpty() {
		return _commandCache.isEmpty();
	}

	private static final Log _log = LogFactoryUtil.getLog(CommandCache.class);

	private final String _comandPostFix;
	private final Map<String, T> _commandCache = new ConcurrentHashMap<>();
	private final Map<String, List<T>> _commandChainCache =
		new ConcurrentHashMap<>();
	private final String _commandFieldName;
	private final T _emptyCommand;
	private final String _packagePrefix;
	private final ServiceTracker<T, T> _serviceTracker;

	private class CommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<T, T> {

		@Override
		public T addingService(ServiceReference<T> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			T command = registry.getService(serviceReference);

			List<String> commandNames = StringPlus.asList(
				serviceReference.getProperty(_commandFieldName));

			for (String commandName : commandNames) {
				_commandCache.put(commandName, command);
			}

			return command;
		}

		@Override
		public void modifiedService(
			ServiceReference<T> serviceReference, T command) {
		}

		@Override
		public void removedService(
			ServiceReference<T> serviceReference, T command) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String commandName = (String)serviceReference.getProperty(
				_commandFieldName);

			_commandCache.remove(commandName);

			for (List<T> commands : _commandChainCache.values()) {
				commands.remove(command);
			}
		}

	}

}