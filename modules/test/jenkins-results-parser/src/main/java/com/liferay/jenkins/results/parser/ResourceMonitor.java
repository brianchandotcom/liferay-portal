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

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface ResourceMonitor {

	public String getEtcdServerURL();

	public String getKey();

	public Integer getMaxResourceConnections();

	public String getName();

	public String getNewConnectionName();

	public List<ResourceConnection> getResourceConnectionQueue();

	public String getType();

	public void printResourceConnectionQueue();

	public void signal(String connectionName);

	public void wait(String connectionName);

}