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

<%@ include file="/init.jsp" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String portletId = portletDisplay.getId();

if (Validator.isNotNull(portletResource)) {
	portletId = portletResource;
	portletName = portletResource;
}

BlogsGroupServiceSettings blogsGroupServiceSettings = BlogsGroupServiceSettings.getInstance(scopeGroupId);

SettingsFactory settingsFactory = SettingsFactoryUtil.getSettingsFactory();

BlogsPortletInstanceConfiguration blogsPortletInstanceConfiguration = settingsFactory.getSettings(BlogsPortletInstanceConfiguration.class, new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getId()));
BlogsPortletInstanceSettingsHelper blogsPortletInstanceSettingsHelper = new BlogsPortletInstanceSettingsHelper(request, blogsPortletInstanceConfiguration);
com.liferay.blogs.settings.BlogsGroupServiceSettings rssBlogsGroupServiceSettings = settingsFactory.getSettings(com.liferay.blogs.settings.BlogsGroupServiceSettings.class, new GroupServiceSettingsLocator(themeDisplay.getSiteGroupId(), BlogsConstants.SERVICE_NAME));

int pageAbstractLength = PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH;

boolean showEditEntryPermissions = true;
%>

<%@ include file="/blogs/init-ext.jsp" %>