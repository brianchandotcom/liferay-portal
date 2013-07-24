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

package com.liferay.portlet.rss;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.ResourceRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Sierra Andrés
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public abstract class DefaultRSSRenderer implements RSSRenderer {

	public DefaultRSSRenderer(HttpServletRequest request) {
		this.request = request;

		themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public DefaultRSSRenderer(ResourceRequest resourceRequest) {
		this(PortalUtil.getHttpServletRequest(resourceRequest));
	}

	@Override
	public String getAlternateURL() throws PortalException, SystemException {
		return PortalUtil.getLayoutFullURL(themeDisplay);
	}

	@Override
	public abstract String getFeedURL() throws PortalException, SystemException;

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	@Override
	public Date getPublicationDate() throws PortalException, SystemException {
		return new Date();
	}

	@Override
	public String getRSSDescription() throws PortalException, SystemException {
		return getRSSName();
	}

	@Override
	public String getRSSFeedType() throws PortalException, SystemException {
		return RSSUtil.getFeedType(getRSSFormat(), getRSSVersion());
	}

	@Override
	public abstract String getRSSName() throws PortalException, SystemException;

	@Override
	public abstract void populateFeedEntries(
			List<? super SyndEntry> syndEntries)
		throws PortalException, SystemException;

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	protected String getRSSFormat() throws PortalException, SystemException {
		return ParamUtil.getString(request, "type", RSSUtil.FORMAT_DEFAULT);
	}

	/**
	 * @throws PortalException
	 * @throws SystemException
	 */
	protected double getRSSVersion() throws PortalException, SystemException {
		return ParamUtil.getDouble(request, "version", RSSUtil.VERSION_DEFAULT);
	}

	protected HttpServletRequest request;
	protected ThemeDisplay themeDisplay;

}