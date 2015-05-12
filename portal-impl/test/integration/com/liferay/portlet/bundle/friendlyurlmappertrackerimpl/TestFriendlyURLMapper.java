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

package com.liferay.portlet.bundle.friendlyurlmappertrackerimpl;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.Router;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=FriendlyURLMapperTrackerImplTest",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestFriendlyURLMapper implements FriendlyURLMapper {

	@Override
	public String buildPath(LiferayPortletURL liferayPortletURL) {
		return null;
	}

	@Override
	public String getMapping() {
		return null;
	}

	@Override
	public String getPortletId() {
		return null;
	}

	@Override
	public Router getRouter() {
		return null;
	}

	@Override
	public boolean isCheckMappingWithPrefix() {
		return false;
	}

	@Override
	public boolean isPortletInstanceable() {
		return false;
	}

	@Override
	public void populateParams(
		String friendlyURLPath, Map<String, String[]> parameterMap,
		Map<String, Object> requestContext) {

		return;
	}

	@Override
	public void setMapping(String mapping) {
		return;
	}

	@Override
	public void setPortletId(String portletId) {
		return;
	}

	@Override
	public void setPortletInstanceable(boolean portletInstanceable) {
		return;
	}

	@Override
	public void setRouter(Router router) {
		return;
	}

}