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

package com.liferay.site.admin.web.internal.asset;

import com.liferay.asset.kernel.model.BaseAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ricardo Couso
 */
public class SiteAssetRenderer extends BaseAssetRenderer<Group> {

	public SiteAssetRenderer(Group group) {
		if (group.isSite() || group.isStagingGroup()) {
			_siteGroup = group;
		}
		else {
			throw new IllegalArgumentException(
				"Only site groups are supported");
		}
	}

	@Override
	public Group getAssetObject() {
		return _siteGroup;
	}

	@Override
	public String getClassName() {
		return Group.class.getName();
	}

	@Override
	public long getClassPK() {
		return _siteGroup.getPrimaryKey();
	}

	@Override
	public long getGroupId() {
		return _siteGroup.getGroupId();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		try {
			return _siteGroup.getDescriptiveName(
				PortalUtil.getLocale(portletRequest));
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get summary for group " + _siteGroup.getGroupId(),
				pe);
		}

		return null;
	}

	@Override
	public String getTitle(Locale locale) {
		try {
			return _siteGroup.getDescriptiveName(locale);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get descriptive name for group " +
					_siteGroup.getGroupId(),
				pe);
		}

		return null;
	}

	@Override
	public long getUserId() {
		return _siteGroup.getCreatorUserId();
	}

	@Override
	public String getUserName() {
		return null;
	}

	@Override
	public String getUuid() {
		return _siteGroup.getUuid();
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteAssetRenderer.class);

	private final Group _siteGroup;

}