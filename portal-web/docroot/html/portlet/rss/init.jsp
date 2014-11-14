<%--
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
--%>

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portlet.rss.context.RSSDisplayContext" %><%@
page import="com.liferay.portlet.rss.context.RSSFeedContext" %><%@
page import="com.liferay.portlet.rss.context.RSSFeedEntryDisplayContext" %><%@
page import="com.liferay.portlet.rss.util.RSSUtil" %>

<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %><%@
page import="com.sun.syndication.feed.synd.SyndFeed" %><%@
page import="com.sun.syndication.feed.synd.SyndImage" %>

<%
RSSDisplayContext rssDisplayContext = new RSSDisplayContext(request, portletPreferences);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/rss/init-ext.jsp" %>