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

package com.liferay.portal.instances.action.contributor;

import com.liferay.portal.kernel.model.Company;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Pei-Jung Lan
 */
@ProviderType
public interface PortalInstanceActionContributor {

	public String getMessage(PortletRequest portletRequest);

	public String getURL(
		Company company, PortletRequest portletRequest,
		PortletResponse portletResponse);

	public boolean isShow(Company company, PortletRequest portletRequest);

}