/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.rss;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class BlogsGroupRSSRenderer extends BlogsBaseRSSRenderer {

	public BlogsGroupRSSRenderer(
		Group group, List<BlogsEntry> blogsEntries,
		HttpServletRequest request) {

		this(group, blogsEntries, request, false);
	}

	public BlogsGroupRSSRenderer(
		Group group, List<BlogsEntry> blogsEntries, HttpServletRequest request,
		boolean isScopeGroup) {

		super(blogsEntries, request);

		_group = group;
		_isScopeGroup = isScopeGroup;
		_plid = ParamUtil.getLong(getRequest(), "p_l_id");
	}

	@Override
	public String getFeedURL() throws PortalException, SystemException {
		if (_isScopeGroup) {
			return super.getFeedURL() + "p_l_id=" + _plid;
		}

		return PortalUtil.getLayoutFullURL(getThemeDisplay()) +
			Portal.FRIENDLY_URL_SEPARATOR + "blogs/rss";
	}

	@Override
	public String getRSSName() throws PortalException, SystemException {
		return _group.getDescriptiveName();
	}

	@Override
	protected String getEntryURL() throws PortalException, SystemException {
		return getFeedURL();
	}

	private Group _group;
	private boolean _isScopeGroup;
	private long _plid;

}