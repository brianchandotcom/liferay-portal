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
int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/portal_instances/view"
).buildPortletURL();
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= new PortalInstancesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse) %>"
	propsTransformer="js/PortalInstancesManagementToolbarPropsTransformer"
	propsTransformerServletContext="<%= application %>"
/>

<portlet:renderURL var="redirectURL">
	<portlet:param name="mvcRenderCommandName" value="/portal_instances/view" />
	<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
</portlet:renderURL>

<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
	<aui:input name="className" type="hidden" />

	<%@ include file="/instances.jspf" %>
</aui:form>