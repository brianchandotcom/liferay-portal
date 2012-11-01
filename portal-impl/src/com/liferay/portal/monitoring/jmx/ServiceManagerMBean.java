/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.monitoring.jmx;

import com.liferay.portal.kernel.exception.SystemException;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface ServiceManagerMBean {

	public void addMonitoredClass(String className);

	public void addMonitoredMethod(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	public long getErrorCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	public long getMaxTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	public long getMinTime(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	public Set<String> getMonitoredClasses();

	public Set<MethodSignature> getMonitoredMethods();

	public long getRequestCount(
			String className, String methodName, String[] parameterTypes)
		throws SystemException;

	public boolean isActive();

	public boolean isPermissiveMode();

	public void setActive(boolean active);

	public void setPermissiveMode(boolean permissiveMode);

}