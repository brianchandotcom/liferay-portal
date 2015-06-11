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

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Sergio González
 */
public class ResourceCommandCache {

	public static final ResourceCommand EMPTY = new ResourceCommand() {

		@Override
		public boolean processCommand(
			ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) {

			return false;
		}

	};

	public static final String RESOURCE_PACKAGE_NAME =
		"resource.package.prefix";

	public ResourceCommandCache(String packagePrefix, String portletName) {
		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(resource.command.name=*)(javax.portlet.name=" + portletName +
				")(objectClass=" + ResourceCommand.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new ResourceCommandServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void close() {
		_serviceTracker.close();
	}

	public ResourceCommand getResourceCommand(String resourceCommandName) {
		String className = null;

		try {
			ResourceCommand resourceCommand = _resourceCommandCache.get(
				resourceCommandName);

			if (resourceCommand != null) {
				return resourceCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return EMPTY;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(resourceCommandName.charAt(0)));
			sb.append(resourceCommandName.substring(1));
			sb.append(_RESOURCE_COMMAND_POSTFIX);

			className = sb.toString();

			resourceCommand = (ResourceCommand)InstanceFactory.newInstance(
				className);

			_resourceCommandCache.put(resourceCommandName, resourceCommand);

			return resourceCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate ResourceCommand " + className);
			}

			_resourceCommandCache.put(resourceCommandName, EMPTY);

			return EMPTY;
		}
	}

	public List<ResourceCommand> getResourceCommandChain(
		String resourceCommandChain) {

		List<ResourceCommand> resourceCommands = _resourceCommandChainCache.get(
			resourceCommandChain);

		if (resourceCommands != null) {
			return resourceCommands;
		}

		resourceCommands = new ArrayList<>();

		String[] resourceCommandNames = StringUtil.split(resourceCommandChain);

		for (String resourceCommandName : resourceCommandNames) {
			ResourceCommand resourceCommand = getResourceCommand(
				resourceCommandName);

			if (resourceCommand != EMPTY) {
				resourceCommands.add(resourceCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to find ResourceCommand " +
							resourceCommandChain);
				}
			}
		}

		_resourceCommandChainCache.put(resourceCommandChain, resourceCommands);

		return resourceCommands;
	}

	public boolean isEmpty() {
		return _resourceCommandCache.isEmpty();
	}

	private static final String _RESOURCE_COMMAND_POSTFIX = "ResourceCommand";

	private static final Log _log = LogFactoryUtil.getLog(
		ResourceCommandCache.class);

	private final String _packagePrefix;
	private final Map<String, ResourceCommand> _resourceCommandCache =
		new ConcurrentHashMap<>();
	private final Map<String, List<ResourceCommand>>
		_resourceCommandChainCache = new ConcurrentHashMap<>();
	private final ServiceTracker<ResourceCommand, ResourceCommand>
		_serviceTracker;

	private class ResourceCommandServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<ResourceCommand, ResourceCommand> {

		@Override
		public ResourceCommand addingService(
			ServiceReference<ResourceCommand> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ResourceCommand resourceCommand = registry.getService(
				serviceReference);

			String resourceCommandName = (String)serviceReference.getProperty(
				"resource.command.name");

			_resourceCommandCache.put(resourceCommandName, resourceCommand);

			return resourceCommand;
		}

		@Override
		public void modifiedService(
			ServiceReference<ResourceCommand> serviceReference,
			ResourceCommand resourceCommand) {
		}

		@Override
		public void removedService(
			ServiceReference<ResourceCommand> serviceReference,
			ResourceCommand resourceCommand) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String resourceCommandName = (String)serviceReference.getProperty(
				"resource.command.name");

			_resourceCommandCache.remove(resourceCommandName);

			for (List<ResourceCommand> resourceCommands :
					_resourceCommandChainCache.values()) {

				resourceCommands.remove(resourceCommand);
			}
		}

	}

}