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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.blogs.model.BlogsEntry" %><%@
page import="com.liferay.blogs.model.BlogsStatsUser" %><%@
page import="com.liferay.blogs.service.BlogsEntryServiceUtil" %><%@
page import="com.liferay.blogs.service.BlogsStatsUserLocalServiceUtil" %><%@
page import="com.liferay.blogs.util.comparator.EntryModifiedDateComparator" %><%@
page import="com.liferay.blogs.util.comparator.StatsUserLastPostDateComparator" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Organization" %><%@
page import="com.liferay.portal.kernel.model.User" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.OrganizationLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.UserLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String selectionMethod = portletPreferences.getValue("selectionMethod", "users");
long organizationId = GetterUtil.getLong(portletPreferences.getValue("organizationId", "0"));
String displayStyle = portletPreferences.getValue("displayStyle", "user-name-and-image");
int max = GetterUtil.getInteger(portletPreferences.getValue("max", "10"));

if (organizationId == 0) {
	Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

	if (group.isOrganization()) {
		organizationId = group.getOrganizationId();
	}
}

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>