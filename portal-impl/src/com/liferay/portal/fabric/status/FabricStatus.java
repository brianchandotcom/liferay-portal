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

package com.liferay.portal.fabric.status;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public interface FabricStatus {

	public AdvancedOperatingSystemMXBean getAdvancedOperatingSystemMXBean();

	public List<BufferPoolMXBean> getBufferPoolMXBeans();

	public ClassLoadingMXBean getClassLoadingMXBean();

	public CompilationMXBean getCompilationMXBean();

	public List<GarbageCollectorMXBean> getGarbageCollectorMXBeans();

	public List<MemoryManagerMXBean> getMemoryManagerMXBeans();

	public MemoryMXBean getMemoryMXBean();

	public List<MemoryPoolMXBean> getMemoryPoolMXBeans();

	public PlatformLoggingMXBean getPlatformLoggingMXBean();

	public RuntimeMXBean getRuntimeMXBean();

	public ThreadMXBean getThreadMXBean();

}