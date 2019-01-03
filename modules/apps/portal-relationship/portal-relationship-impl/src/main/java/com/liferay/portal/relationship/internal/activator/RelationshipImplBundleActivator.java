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

package com.liferay.portal.relationship.internal.activator;

import com.liferay.portal.relationship.RelationshipManager;
import com.liferay.portal.relationship.RelationshipManagerUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Máté Thurzó
 */
public class RelationshipImplBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_serviceTracker =
			new ServiceTracker<RelationshipManager, RelationshipManager>(
				bundleContext, RelationshipManager.class.getName(), null) {

				@Override
				public RelationshipManager addingService(
					ServiceReference<RelationshipManager> serviceReference) {

					RelationshipManager relationshipManager =
						bundleContext.getService(serviceReference);

					RelationshipManagerUtil.setRelationshipManager(
						relationshipManager);

					return relationshipManager;
				}

			};

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceTracker.close();
	}

	private ServiceTracker<RelationshipManager, RelationshipManager>
		_serviceTracker;

}