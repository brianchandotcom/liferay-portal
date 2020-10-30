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

package com.liferay.portal.bundle.blacklist.internal.activator;

import com.liferay.portal.bundle.blacklist.internal.configuration.BundleBlacklistConfiguration;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.dao.db.DBContext;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.io.OutputStream;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Alberto Chaparro
 */
public class BundleBlacklistImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationUpgradeStepFactory> serviceReference =
			bundleContext.getServiceReference(
				ConfigurationUpgradeStepFactory.class);

		ConfigurationUpgradeStepFactory configurationUpgradeStepFactory =
			bundleContext.getService(serviceReference);

		UpgradeStep upgradeStep =
			configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.bundle.blacklist.internal." +
					"BundleBlacklistConfiguration",
				BundleBlacklistConfiguration.class.getName());

		upgradeStep.upgrade(
			new DBProcessContext() {

				@Override
				public DBContext getDBContext() {
					return new DBContext();
				}

				@Override
				public OutputStream getOutputStream() {
					return null;
				}

			});
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}

}