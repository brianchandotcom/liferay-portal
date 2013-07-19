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

package com.liferay.portlet.activities.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.RSSUtil;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLink;
import com.sun.syndication.feed.synd.SyndLinkImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 * @author Eduardo Garcia
 */
public class RSSAction extends com.liferay.portal.struts.RSSAction {

	@Override
	protected byte[] getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		String feedTitle = ParamUtil.getString(resourceRequest, "feedTitle");
		String format = ParamUtil.getString(
			resourceRequest, "type", RSSUtil.FORMAT_DEFAULT);
		double version = ParamUtil.getDouble(
			resourceRequest, "version", RSSUtil.VERSION_DEFAULT);
		String displayStyle = ParamUtil.getString(
			resourceRequest, "displayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);

		int max = ParamUtil.getInteger(
			resourceRequest, "max", SearchContainer.DEFAULT_DELTA);

		List<SocialActivity> activities = getActivities(resourceRequest, max);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		String rss = exportToRSS(
			resourceRequest, feedTitle, null, format, version, displayStyle,
			activities, serviceContext);

		return rss.getBytes(StringPool.UTF8);
	}

}