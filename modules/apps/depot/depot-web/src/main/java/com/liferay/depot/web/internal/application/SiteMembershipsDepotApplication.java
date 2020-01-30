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

package com.liferay.depot.web.internal.application;

import com.liferay.depot.application.DepotApplication;
import com.liferay.site.memberships.constants.SiteMembershipsPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = DepotApplication.class)
public class SiteMembershipsDepotApplication implements DepotApplication {

	@Override
	public String getPortletId() {
		return SiteMembershipsPortletKeys.SITE_MEMBERSHIPS_ADMIN;
	}

	@Override
	public boolean isCustomizable() {
		return false;
	}

}