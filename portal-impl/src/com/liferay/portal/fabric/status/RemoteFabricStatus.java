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

import com.liferay.portal.fabric.status.JMXProxyUtil.ProcessCallableExecutor;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.management.PlatformManagedObject;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.management.ObjectName;

/**
 * @author Shuyang Zhou
 */
public class RemoteFabricStatus implements FabricStatus {

	public RemoteFabricStatus(ProcessCallableExecutor processCallableExecutor) {
		this.processCallableExecutor = processCallableExecutor;
	}

	@Override
	public AdvancedOperatingSystemMXBean getAdvancedOperatingSystemMXBean() {
		OperatingSystemMXBean operatingSystemMXBean =
			ManagementFactory.getOperatingSystemMXBean();

		return JMXProxyUtil.newProxy(
			operatingSystemMXBean.getObjectName(),
			AdvancedOperatingSystemMXBean.class, processCallableExecutor);
	}

	@Override
	public List<BufferPoolMXBean> getBufferPoolMXBeans() {
		return getPlatformMXBeans(
			BufferPoolMXBean.class, processCallableExecutor);
	}

	@Override
	public ClassLoadingMXBean getClassLoadingMXBean() {
		ClassLoadingMXBean classLoadingMXBean =
			ManagementFactory.getClassLoadingMXBean();

		return JMXProxyUtil.newProxy(
			classLoadingMXBean.getObjectName(), ClassLoadingMXBean.class,
			processCallableExecutor);
	}

	@Override
	public CompilationMXBean getCompilationMXBean() {
		CompilationMXBean compilationMXBean =
			ManagementFactory.getCompilationMXBean();

		return JMXProxyUtil.newProxy(
			compilationMXBean.getObjectName(), CompilationMXBean.class,
			processCallableExecutor);
	}

	@Override
	public List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
		return getPlatformMXBeans(
			GarbageCollectorMXBean.class, processCallableExecutor);
	}

	@Override
	public List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
		return getPlatformMXBeans(
			MemoryManagerMXBean.class, processCallableExecutor);
	}

	@Override
	public MemoryMXBean getMemoryMXBean() {
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

		return JMXProxyUtil.newProxy(
			memoryMXBean.getObjectName(), MemoryMXBean.class,
			processCallableExecutor);
	}

	@Override
	public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
		return getPlatformMXBeans(
			MemoryPoolMXBean.class, processCallableExecutor);
	}

	@Override
	public PlatformLoggingMXBean getPlatformLoggingMXBean() {
		PlatformLoggingMXBean platformLoggingMXBean =
			ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class);

		return JMXProxyUtil.newProxy(
			platformLoggingMXBean.getObjectName(), PlatformLoggingMXBean.class,
			processCallableExecutor);
	}

	@Override
	public RuntimeMXBean getRuntimeMXBean() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

		return JMXProxyUtil.newProxy(
			runtimeMXBean.getObjectName(), RuntimeMXBean.class,
			processCallableExecutor);
	}

	@Override
	public ThreadMXBean getThreadMXBean() {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

		return JMXProxyUtil.newProxy(
			threadMXBean.getObjectName(), ThreadMXBean.class,
			processCallableExecutor);
	}

	protected static <T extends PlatformManagedObject> List<T>
		getPlatformMXBeans(
			Class<T> mxBeanInterface,
			ProcessCallableExecutor processCallableExecutor) {

		List<T> list = new ArrayList<T>();

		try {
			Future<ArrayList<ObjectName>> future =
				processCallableExecutor.execute(
					new GetPlatformMXBeanObjectNamesProcessCallable(
						mxBeanInterface));

			for (ObjectName objectName : future.get()) {
				list.add(
					JMXProxyUtil.newProxy(
						objectName, mxBeanInterface, processCallableExecutor));
			}
		}
		catch (Exception e) {
			return ReflectionUtil.throwException(e);
		}

		return list;
	}

	protected final ProcessCallableExecutor processCallableExecutor;

	protected static class GetPlatformMXBeanObjectNamesProcessCallable
		implements ProcessCallable<ArrayList<ObjectName>> {

		public GetPlatformMXBeanObjectNamesProcessCallable(
			Class<? extends PlatformManagedObject> clazz) {

			_clazz = clazz;
		}

		@Override
		public ArrayList<ObjectName> call() {
			ArrayList<ObjectName> objectNames = new ArrayList<ObjectName>();

			for (PlatformManagedObject platformManagedObject :
					ManagementFactory.getPlatformMXBeans(_clazz)) {

				objectNames.add(platformManagedObject.getObjectName());
			}

			return objectNames;
		}

		private static final long serialVersionUID = 1L;

		private final Class<? extends PlatformManagedObject> _clazz;

	}

}