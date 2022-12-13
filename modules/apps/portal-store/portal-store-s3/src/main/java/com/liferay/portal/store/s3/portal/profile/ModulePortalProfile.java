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

package com.liferay.portal.store.s3.portal.profile;

import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.portal.store.s3.AbortedMultipartUploadCleanerMessageListener;
import com.liferay.portal.store.s3.S3Store;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Hai Yu
 */
@Component(service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		List<String> supportedPortalProfileNames = null;

		String storeClassName = PropsValues.DL_STORE_IMPL;

		if (storeClassName.equals(S3Store.class.getName())) {
			supportedPortalProfileNames = new ArrayList<>();

			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_CE);
			supportedPortalProfileNames.add(
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}
		else {
			supportedPortalProfileNames = Collections.emptyList();
		}

		init(
			componentContext, supportedPortalProfileNames,
			AbortedMultipartUploadCleanerMessageListener.class.getName());
	}

}