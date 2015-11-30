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

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">

		<%
		int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
		int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/server_admin/view");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("tabs2", tabs2);
		%>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="mvcRenderCommandName" value="/server_admin/view" />
			<portlet:param name="tabs1" value="<%= tabs1 %>" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
		</portlet:renderURL>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />

			<c:if test="<%= showTabs1 %>">
				<liferay-ui:tabs
					names="server,instances"
					url="<%= portletURL.toString() %>"
				/>
			</c:if>

			<c:choose>
				<c:when test='<%= tabs1.equals("server") %>'>
					<liferay-util:include page="/server.jsp" servletContext="<%= application %>" />
				</c:when>
			</c:choose>
		</aui:form>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="mvcRenderCommandName" value="/server_admin/view" />
			<portlet:param name="tabs1" value="<%= tabs1 %>" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= String.valueOf(cur) %>" />
			<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= String.valueOf(delta) %>" />
		</portlet:renderURL>

		<aui:script use="liferay-admin">
			new Liferay.Portlet.Admin(
				{
					form: document.<portlet:namespace />fm,
					namespace: '<portlet:namespace />',
					redirectUrl: '<%= redirectURL %>',
					submitButton: '.save-server-button',
					url: '<portlet:actionURL name="/server_admin/edit_server" />'
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/portlet_access_denied.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>