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