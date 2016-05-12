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

package com.liferay.portal.cache.single.internal.activator;

import com.liferay.portal.cache.single.internal.bootstrap.SinglePortalCacheBootstrapLoaderFactory;
import com.liferay.portal.cache.single.internal.distribution.SinglePortalCacheReplicatorFactory;
import com.liferay.portal.profile.activator.ProfileActivator;

import java.util.Collections;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class GatekeeperActivator {

	@Activate
	public void activate(ComponentContext componentContext) {
		_profileActivator.activateByProfiles(
			componentContext,
			Collections.singleton(ProfileActivator.CE_PROFILE),
			SinglePortalCacheBootstrapLoaderFactory.class.getName(),
			SinglePortalCacheReplicatorFactory.class.getName());
	}

	@Reference
	private ProfileActivator _profileActivator;

}