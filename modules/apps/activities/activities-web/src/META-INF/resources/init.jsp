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

<%@ taglib uri="/META-INF/c.tld" prefix="c" %>
<%@ taglib uri="/META-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/META-INF/fn.tld" prefix="fn" %>
<%@ taglib uri="/META-INF/sql.tld" prefix="sql" %>
<%@ taglib uri="/META-INF/x.tld" prefix="x" %>
<%@ taglib uri="/META-INF/liferay-portlet_2_0.tld" prefix="portlet" %>
<%@ taglib uri="/META-INF/aui.tld" prefix="aui" %>
<%@ taglib uri="/META-INF/liferay-ddm.tld" prefix="liferay-ddm" %>
<%@ taglib uri="/META-INF/liferay-portlet-ext.tld" prefix="liferay-portlet" %>
<%@ taglib uri="/META-INF/liferay-security.tld" prefix="liferay-security" %>
<%@ taglib uri="/META-INF/liferay-staging.tld" prefix="liferay-staging" %>
<%@ taglib uri="/META-INF/liferay-theme.tld" prefix="liferay-theme" %>
<%@ taglib uri="/META-INF/liferay-ui.tld" prefix="liferay-ui" %>
<%@ taglib uri="/META-INF/liferay-util.tld" prefix="liferay-util" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.model.Group" %><%@
page import="com.liferay.portal.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.social.model.SocialActivity" %><%@
page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.util.RSSUtil" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.ResourceURL" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
int max = GetterUtil.getInteger(portletPreferences.getValue("max", "10"));

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean(portletPreferences.getValue("enableRss", null), true);
int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_DEFAULT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
%>

<%@ include file="/init-ext.jsp" %>