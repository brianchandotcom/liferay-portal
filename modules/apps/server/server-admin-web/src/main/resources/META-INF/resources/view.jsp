<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
%>

<portlet:renderURL var="redirectURL">
	<portlet:param name="mvcRenderCommandName" value="/server_admin/view" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
	<portlet:param name="delta" value="<%= String.valueOf(delta) %>" />
	<portlet:param name="keywords" value='<%= ParamUtil.getString(request, "keywords") %>' />
</portlet:renderURL>

<aui:form
	action='<%=
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/server_admin/view"
		).setTabs1(
			tabs1
		).setTabs2(
			tabs2
		).buildString()
	%>'
	method="post"
	name="fm"
>
	<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

	<liferay-util:include page="/server.jsp" servletContext="<%= application %>" />
</aui:form>

<portlet:renderURL var="redirectURL">
	<portlet:param name="mvcRenderCommandName" value="/server_admin/view" />
	<portlet:param name="tabs1" value="<%= tabs1 %>" />
	<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= String.valueOf(cur) %>" />
	<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= String.valueOf(delta) %>" />
</portlet:renderURL>

<portlet:actionURL name="/server_admin/edit_server" var="url" />

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"redirectURL", redirectURL
		).put(
			"url", url
		).build()
	%>'
	module="js/main"
/>