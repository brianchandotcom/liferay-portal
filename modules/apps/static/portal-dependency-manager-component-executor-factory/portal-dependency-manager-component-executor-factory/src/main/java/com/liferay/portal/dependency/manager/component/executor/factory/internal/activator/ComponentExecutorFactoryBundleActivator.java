/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dependency.manager.component.executor.factory.internal.activator;

import com.liferay.portal.dependency.manager.component.executor.factory.internal.DependencyManagerSyncImpl;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSync;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
public class ComponentExecutorFactoryBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		long syncTimeout = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.DEPENDENCY_MANAGER_SYNC_TIMEOUT), 60);

		_dependencyManagerSyncServiceRegistration =
			bundleContext.registerService(
				DependencyManagerSync.class,
				new DependencyManagerSyncImpl(syncTimeout), null);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_dependencyManagerSyncServiceRegistration.unregister();
	}

	private ServiceRegistration<DependencyManagerSync>
		_dependencyManagerSyncServiceRegistration;

}