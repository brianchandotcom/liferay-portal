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

package com.liferay.portal.cluster.single.internal.profile;

import com.liferay.portal.cluster.single.internal.SingleClusterExecutor;
import com.liferay.portal.cluster.single.internal.SingleClusterLink;
import com.liferay.portal.cluster.single.internal.SingleClusterMasterExecutor;
import com.liferay.portal.profile.gatekeeper.BaseDSModuleProfile;
import com.liferay.portal.profile.gatekeeper.Profile;

import java.util.Collections;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = Profile.class)
public class DSModuleProfile extends BaseDSModuleProfile {

	@Activate
	public void activate(ComponentContext componentContext) {
		init(
			componentContext,
			Collections.singleton(Profile.CE_PORTAL_PROFILE_NAME),
			SingleClusterExecutor.class.getName(),
			SingleClusterLink.class.getName(),
			SingleClusterMasterExecutor.class.getName());
	}

}