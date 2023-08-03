/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.component.actor.sync.internal;

import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(service = {})
public class ServiceComponentActorSync {

	@Activate
	protected void activate(ComponentContext componentContext) {
		BundleContext bundleContext = componentContext.getBundleContext();

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				String componentName =
					ServiceComponentActorGateKeeper.class.getName();

				CountDownLatch countDownLatch = new CountDownLatch(1);

				ServiceListener serviceListener = serviceEvent -> {
					if (serviceEvent.getType() == ServiceEvent.REGISTERED) {
						countDownLatch.countDown();
					}
				};

				try {
					bundleContext.addServiceListener(
						serviceListener,
						"(&(objectClass=" + componentName + "))");

					componentContext.enableComponent(componentName);

					while (true) {
						if (countDownLatch.await(1, TimeUnit.MINUTES)) {
							break;
						}

						if (_log.isWarnEnabled()) {
							_log.warn(
								"Waiting on tasks in SCR component actor " +
									"thread to be finished.");
						}
					}
				}
				catch (Exception exception) {
					_log.error(
						"Unable to sync SCR component actor thread", exception);
				}
				finally {
					bundleContext.removeServiceListener(serviceListener);

					componentContext.disableComponent(componentName);

					componentContext.disableComponent(
						ServiceComponentActorSync.class.getName());
				}

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentActorSync.class);

}