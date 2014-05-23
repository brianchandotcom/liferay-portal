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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
String returnToFullPageURL = ParamUtil.getString(request, "returnToFullPageURL");

if (Validator.isNotNull(returnToFullPageURL)) {
	portletDisplay.setURLBack(returnToFullPageURL);
}

boolean print = ParamUtil.getString(request, "viewMode").equals(Constants.PRINT);

request.setAttribute("view.jsp-results", new ArrayList());
request.setAttribute("view.jsp-assetEntryIndex", new Integer(0));
request.setAttribute("view.jsp-show", Boolean.TRUE);
request.setAttribute("view.jsp-print", new Boolean(print));
%>

<div>
	<liferay-util:include page="/html/portlet/asset_publisher/display/full_content.jsp" />
</div>

<liferay-util:include page="/html/portlet/asset_publisher/asset_html_metadata.jsp" />

<%
String title = (String)request.getAttribute("view.jsp-title");

PortalUtil.addPortletBreadcrumbEntry(request, title, currentURL);
%>