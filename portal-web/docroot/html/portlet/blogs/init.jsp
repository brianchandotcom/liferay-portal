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

<%@ page import="com.liferay.portal.service.SubscriptionLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.BlogsPortletInstanceSettings" %><%@
page import="com.liferay.portlet.blogs.BlogsSettings" %><%@
page import="com.liferay.portlet.blogs.EntryContentException" %><%@
page import="com.liferay.portlet.blogs.EntryDescriptionException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageNameException" %><%@
page import="com.liferay.portlet.blogs.EntrySmallImageSizeException" %><%@
page import="com.liferay.portlet.blogs.EntryTitleException" %><%@
page import="com.liferay.portlet.blogs.NoSuchEntryException" %><%@
page import="com.liferay.portlet.blogs.model.impl.BlogsEntryImpl" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.BlogsEntryServiceUtil" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsEntryPermission" %><%@
page import="com.liferay.portlet.blogs.service.permission.BlogsPermission" %><%@
page import="com.liferay.portlet.blogs.util.BlogsConstants" %><%@
page import="com.liferay.portlet.blogs.util.BlogsUtil" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String portletId = portletDisplay.getId();

if (portletId.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	portletId = portletResource;
	portletName = portletResource;
}

BlogsPortletInstanceSettings blogsPortletInstanceSettings = BlogsPortletInstanceSettings.getInstance(layout, portletId);
BlogsSettings blogsSettings = BlogsSettings.getInstance(scopeGroupId);

int pageAbstractLength = PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH;

boolean showSearch = true;
boolean showEditEntryPermissions = true;

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<%@ include file="/html/portlet/blogs/init-ext.jsp" %>