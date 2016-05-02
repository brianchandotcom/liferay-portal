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

package com.liferay.lcs.management;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Riccardo Ferrari
 */
public interface MBeanServerService {

	public MBeanServer getMBeanServer();

	public Object getObjectNameAttribute(
			ObjectName objectName, String attributeName)
		throws Exception;

	public Map<String, Object> getObjectNameAttributes(
			ObjectName objectName, String[] attributeNames)
		throws Exception;

	public Set<ObjectName> getObjectNames(
			ObjectName objectName, List<String> attributeNames)
		throws Exception;

	public Map<String, Map<String, Object>> getObjectNamesAttributes(
			Set<ObjectName> objectNames, String[] attributeNames,
			MapKeyStrategy mapKeyStrategy)
		throws Exception;

}