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

package com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.test;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.callback.SynchronousDestinationTestCallback;
import com.liferay.portal.workflow.kaleo.runtime.constants.KaleoRuntimeDestinationNames;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.dependency.ServiceDependencyManager;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.Description;

/**
 * @author Inácio Nery
 */
public class WorkflowTaskEnvironmentConfigTestCallback
	extends SynchronousDestinationTestCallback {

	public static final WorkflowTaskEnvironmentConfigTestCallback INSTANCE =
		new WorkflowTaskEnvironmentConfigTestCallback();

	@Override
	public SyncHandler beforeClass(Description description) throws Throwable {
		Class<?> testClass = description.getTestClass();

		Sync sync = testClass.getAnnotation(Sync.class);

		if (sync != null) {
			return _createSyncHandler(sync);
		}

		boolean hasSyncedMethod = false;

		for (Method method : testClass.getMethods()) {
			if ((method.getAnnotation(Sync.class) != null) &&
				(method.getAnnotation(Test.class) != null)) {

				hasSyncedMethod = true;

				break;
			}
		}

		if (!hasSyncedMethod) {
			throw new AssertionError(
				testClass.getName() + " uses " +
					SynchronousDestinationTestRule.class.getName() +
						" without any usage of " + Sync.class.getName());
		}

		return null;
	}

	private WorkflowTaskEnvironmentConfigTestCallback() {
	}

	private SyncHandler _createSyncHandler(Sync sync) {
		WorkflowTaskSyncHandler syncHandler = new WorkflowTaskSyncHandler();

		syncHandler.setForceSync(ProxyModeThreadLocal.isForceSync());
		syncHandler.setSync(sync);

		syncHandler.enableSync();

		return syncHandler;
	}

	private class WorkflowTaskSyncHandler
		extends SynchronousDestinationTestCallback.SyncHandler {

		@Override
		public void enableSync() {
			ServiceDependencyManager serviceDependencyManager =
				new ServiceDependencyManager();

			Filter kaleoGraphWalkerFilter = _registerDestinationFilter(
				KaleoRuntimeDestinationNames.KALEO_GRAPH_WALKER);

			serviceDependencyManager.registerDependencies(
				kaleoGraphWalkerFilter);

			serviceDependencyManager.waitForDependencies();

			replaceDestination(KaleoRuntimeDestinationNames.KALEO_GRAPH_WALKER);
		}

		private Filter _registerDestinationFilter(String destinationName) {
			Registry registry = RegistryUtil.getRegistry();

			return registry.getFilter(
				"(&(destination.name=" + destinationName + ")(objectClass=" +
					Destination.class.getName() + "))");
		}

	}

}