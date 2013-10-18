<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.EntryContentException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageNameException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageSizeException" %><%@
page import="com.liferay.portlet.blogs.EntryTitleException" %><%@
page import="com.liferay.portlet.blogs.NoSuchEntryException" %><%@
page import="com.liferay.portlet.blogs.model.impl.BlogsEntryImpl" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsEntryPermission" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsPermission" %><%@
page import="com.liferay.portlet.blogs.util.BlogsUtil" %><%@
page import="com.liferay.util.RSSUtil" %>

<%
int pageDelta = GetterUtil.getInteger(portletPreferences.getValue("pageDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String displayStyle = portletPreferences.getValue("displayStyle", BlogsUtil.DISPLAY_STYLE_FULL_CONTENT);
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());
int pageAbstractLength = PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH;
boolean enableFlags = GetterUtil.getBoolean(portletPreferences.getValue("enableFlags", null), true);
boolean enableRelatedAssets = GetterUtil.getBoolean(portletPreferences.getValue("enableRelatedAssets", null), true);
boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null), true);
boolean enableComments = PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED && GetterUtil.getBoolean(portletPreferences.getValue("enableComments", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null), true);
boolean enableSocialBookmarks = GetterUtil.getBoolean(portletPreferences.getValue("enableSocialBookmarks", null), true);
String socialBookmarksDisplayStyle = portletPreferences.getValue("socialBookmarksDisplayStyle", "horizontal");
String socialBookmarksDisplayPosition = portletPreferences.getValue("socialBookmarksDisplayPosition", "bottom");
String socialBookmarksTypes = portletPreferences.getValue("socialBookmarksTypes", PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES));

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean(portletPreferences.getValue("enableRss", null), true);
int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);

boolean showSearch = true;
boolean showEditEntryPermissions = true;

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/blogs/init-ext.jsp" %>