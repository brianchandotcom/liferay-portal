/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.activator;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class SamlServicePersistenceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		Filter filter = context.createFilter(
			"(&(objectClass=" + ModuleServiceLifecycle.class.getName() + ")" +
				ModuleServiceLifecycle.DATABASE_INITIALIZED + ")");

		_serviceTracker = new ServiceTracker<Object, Object>(
			context, filter, null) {

			@Override
			public Object addingService(ServiceReference<Object> reference) {
				try {
					SamlServiceModuleRelease samlServiceModuleRelease =
						new SamlServiceModuleRelease();

					samlServiceModuleRelease.upgrade();

					return null;
				}
				catch (UpgradeException ue) {
					throw new RuntimeException(ue);
				}
			}

		};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<Object, Object> _serviceTracker;

	private static class SamlServiceModuleRelease
		extends BaseUpgradeServiceModuleRelease {

		@Override
		protected String getNamespace() {
			return "Saml";
		}

		@Override
		protected String getNewBundleSymbolicName() {
			return "com.liferay.saml.persistence.service";
		}

		@Override
		protected String getOldBundleSymbolicName() {
			return "saml-portlet";
		}

	}

}