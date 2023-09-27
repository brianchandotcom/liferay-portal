/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.internal.activator;

import com.liferay.exportimport.changeset.ChangesetManager;
import com.liferay.exportimport.changeset.ChangesetManagerUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class ChangesetServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker<ChangesetManager, ChangesetManager>(
				bundleContext, ChangesetManager.class.getName(), null) {

				@Override
				public ChangesetManager addingService(
					ServiceReference<ChangesetManager> serviceReference) {

					ChangesetManager changesetManager =
						bundleContext.getService(serviceReference);

					ChangesetManagerUtil.setChangesetManager(changesetManager);

					return changesetManager;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<ChangesetManager, ChangesetManager> _serviceTracker;

}