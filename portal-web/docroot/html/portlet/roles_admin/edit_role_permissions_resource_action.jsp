<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/roles_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

String target = (String)objArray[3];

String targetId = target.replace(".", "");

Boolean supportsFilterByGroup = (Boolean)objArray[5];
%>

<c:if test="<%= supportsFilterByGroup %>">
	<portlet:renderURL var="selectCommunityURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="struts_action" value="/roles_admin/select_site" />
		<portlet:param name="includeCompany" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="includeUserPersonalSite" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="target" value="<%= target %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		id="<%= targetId %>"
		image="add"
		label="<%= true %>"
		message="limit-scope"
		url="javascript:;"
	/>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace /><%= targetId %>').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							zIndex: Liferay.zIndex.WINDOW + 2,
							width: 600
						},
						id: '<portlet:namespace />selectGroup<%= targetId %>',
						title: '<%= UnicodeLanguageUtil.format(pageContext, "select-x", "site") %>',
						uri: '<%= selectCommunityURL.toString() %>'
					}
				);
			}
		);
	</aui:script>
</c:if>