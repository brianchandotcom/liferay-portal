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

package com.liferay.portal.jmx;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

/**
 * @author Michael C. Han
 */
public interface MBeanRegistry {

	public MBeanServer getMBeanServer();

	public ObjectName getObjectName(String objectNameCacheKey);

	public ObjectInstance register(
			String objectNameCacheKey, Object object, ObjectName objectName)
		throws InstanceAlreadyExistsException, MBeanRegistrationException,
			   NotCompliantMBeanException;

	public void replace(
			String objectCacheKey, Object object, ObjectName objectName)
		throws Exception;

	public void unregister(
			String objectNameCacheKey, ObjectName defaultObjectName)
		throws InstanceNotFoundException, MBeanRegistrationException;

}