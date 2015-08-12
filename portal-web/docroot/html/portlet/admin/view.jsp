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

<%@ include file="/html/portlet/admin/init.jsp" %>

<c:choose>
	<c:when test="<%= permissionChecker.isOmniadmin() %>">

		<%
		int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
		int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/admin/view");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("tabs2", tabs2);
		%>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="struts_action" value="/admin/view" />
			<portlet:param name="tabs1" value="<%= tabs1 %>" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="cur" value="<%= String.valueOf(cur) %>" />
		</portlet:renderURL>

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="tabs1" type="hidden" value="<%= tabs1 %>" />
			<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
			<aui:input name="className" type="hidden" />

			<c:if test="<%= showTabs1 %>">
				<liferay-ui:tabs
					names="server,instances"
					url="<%= portletURL.toString() %>"
				/>
			</c:if>

			<c:choose>
				<c:when test='<%= tabs1.equals("server") %>'>
					<liferay-util:include page="/html/portlet/admin/server.jsp" />

					<aui:script use="liferay-admin">
						new Liferay.Portlet.Admin(
							{
								form: document.<portlet:namespace />fm,
								namespace: '<portlet:namespace />',
								url: '<portlet:actionURL><portlet:param name="mvcRenderCommandName" value="/admin/edit_server" /></portlet:actionURL>'
							}
						);
					</aui:script>
				</c:when>
			</c:choose>
		</aui:form>

		<portlet:renderURL var="redirectURL">
			<portlet:param name="struts_action" value="/admin/view" />
			<portlet:param name="tabs1" value="<%= tabs1 %>" />
			<portlet:param name="tabs2" value="<%= tabs2 %>" />
			<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= String.valueOf(cur) %>" />
			<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= String.valueOf(delta) %>" />
		</portlet:renderURL>

		<portlet:actionURL name="/admin/edit_server" var="editServerURL" />

		<aui:script>
			AUI.$('#<portlet:namespace />fm').on(
				'click',
				'.save-server-button',
				function(event) {
					var currentTarget = AUI.$(event.currentTarget);

					var form = document.<portlet:namespace />fm;

					form.<portlet:namespace /><%= Constants.CMD %>.value = currentTarget.data('cmd');
					form.<portlet:namespace />redirect.value = '<%= redirectURL %>';

					var className = currentTarget.data('classname');

					if (className) {
						form.<portlet:namespace />className.value = className;
					}

					submitForm(form, '<%= editServerURL %>');
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portal/portlet_access_denied.jsp" />
	</c:otherwise>
</c:choose>