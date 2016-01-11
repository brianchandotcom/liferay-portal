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

package com.liferay.staging.security.permission;

import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;

import java.util.Collection;
import java.util.Iterator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, property = {"service.ranking:Integer=1000"})
public class StagingPermissionCheckerFactory
	implements PermissionCheckerFactory {

	@Override
	public PermissionChecker create(User user) throws Exception {
		Collection<ServiceReference<PermissionCheckerFactory>>
			serviceReferences = _bundleContext.getServiceReferences(
				PermissionCheckerFactory.class,
				"(!(component.name=" +
					StagingPermissionCheckerFactory.class.getName() + "))");

		if (serviceReferences.isEmpty()) {
			return null;
		}

		Iterator<ServiceReference<PermissionCheckerFactory>> iterator =
			serviceReferences.iterator();

		ServiceReference<PermissionCheckerFactory> serviceReference =
			iterator.next();

		PermissionCheckerFactory permissionCheckerFactory =
			_bundleContext.getService(serviceReference);

		try {
			PermissionChecker permissionChecker =
				permissionCheckerFactory.create(user);

			return new StagingPermissionChecker(permissionChecker);
		}
		finally {
			_bundleContext.ungetService(serviceReference);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}