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

package com.liferay.staging.security.internal.permission;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, property = {"service.ranking:Integer=1000"})
@ProviderType
public class StagingPermissionCheckerFactory
	implements PermissionCheckerFactory {

	@Override
	public PermissionChecker create(User user) throws Exception {
		PermissionCheckerFactory permissionCheckerFactory =
			_serviceTracker.getService();

		return new StagingPermissionChecker(
			permissionCheckerFactory.create(user),
			_persistedModelLocalServiceRegistry);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext, bundleContext.createFilter(_FILTER_STRING), null);

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private static final String _FILTER_STRING =
		"(&(objectClass=" + PermissionCheckerFactory.class.getName() + ")" +
			"(!(component.name=" +
				StagingPermissionCheckerFactory.class.getName() + ")))";

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	private ServiceTracker<PermissionCheckerFactory, PermissionCheckerFactory>
		_serviceTracker;

}