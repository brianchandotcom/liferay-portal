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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.rss.web.internal.configuration.RSSPortletInstanceConfiguration" %><%@
page import="com.liferay.rss.web.internal.configuration.RSSWebCacheConfiguration" %><%@
page import="com.liferay.rss.web.internal.display.context.RSSDisplayContext" %><%@
page import="com.liferay.rss.web.internal.util.RSSFeed" %><%@
page import="com.liferay.rss.web.internal.util.RSSFeedEntry" %>

<%@ page import="com.sun.syndication.feed.synd.SyndEntry" %><%@
page import="com.sun.syndication.feed.synd.SyndFeed" %><%@
page import="com.sun.syndication.feed.synd.SyndImage" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.Enumeration" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.ValidatorException" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
RSSWebCacheConfiguration rssWebCacheConfiguration = (RSSWebCacheConfiguration)request.getAttribute(RSSWebCacheConfiguration.class.getName());

RSSDisplayContext rssDisplayContext = new RSSDisplayContext(request, rssWebCacheConfiguration);

RSSPortletInstanceConfiguration rssPortletInstanceConfiguration = rssDisplayContext.getRSSPortletInstanceConfiguration();

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>