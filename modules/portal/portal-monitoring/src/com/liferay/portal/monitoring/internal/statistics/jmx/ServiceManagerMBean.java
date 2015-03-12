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

package com.liferay.portal.monitoring.internal.statistics.jmx;

import com.liferay.portal.kernel.monitoring.MethodSignature;

import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface ServiceManagerMBean {

	public void addMonitoredClass(String className);

	public void addMonitoredMethod(
		String className, String methodName, String[] parameterTypes);

	public long getErrorCount(
		String className, String methodName, String[] parameterTypes);

	public long getMaxTime(
		String className, String methodName, String[] parameterTypes);

	public long getMinTime(
		String className, String methodName, String[] parameterTypes);

	public Set<String> getMonitoredClasses();

	public Set<MethodSignature> getMonitoredMethods();

	public long getRequestCount(
		String className, String methodName, String[] parameterTypes);

	public boolean isActive();

	public boolean isPermissiveMode();

	public void setActive(boolean active);

	public void setPermissiveMode(boolean permissiveMode);

}