<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<portlet:renderURL var="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<liferay-ui:header
	backURL="<%= portletURL.toString() %>"
	title="error"
/>

<liferay-ui:error exception="<%= NoSuchGroupException.class %>" message="the-site-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchModelException.class %>" message="the-asset-could-not-be-found" />

<liferay-ui:error-principal />