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

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class ResourceConnectionFactory {

	public static ResourceConnection newResourceConnection(
		EtcdUtil.Node node, ResourceMonitor resourceMonitor) {

		String key = node.getKey();

		ResourceConnection resourceConnection = _resourceConnections.get(key);

		if (resourceConnection == null) {
			_resourceConnections.put(
				key, new ResourceConnection(node, resourceMonitor));
		}

		return _resourceConnections.get(key);
	}

	public static ResourceConnection newResourceConnection(
		String name, ResourceMonitor resourceMonitor) {

		String key = resourceMonitor.getKey() + "/" + name;

		ResourceConnection resourceConnection = _resourceConnections.get(key);

		if (resourceConnection == null) {
			_resourceConnections.put(
				key, new ResourceConnection(name, resourceMonitor));
		}

		return _resourceConnections.get(key);
	}

	private static final Map<String, ResourceConnection> _resourceConnections =
		new HashMap<>();

}