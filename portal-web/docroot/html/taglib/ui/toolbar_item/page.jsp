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

<%@ include file="/html/taglib/ui/toolbar_item/init.jsp" %>

<%
ToolbarItem toolbarItem = (ToolbarItem)request.getAttribute("liferay-ui:toolbar-item:toolbarItem");
String var = (String)request.getAttribute("liferay-ui:toolbar-item:var");
%>

<c:choose>
	<c:when test="<%= toolbarItem instanceof JavaScriptToolbarItem %>">

		<%
		JavaScriptToolbarItem javaScriptToolbarItem = (JavaScriptToolbarItem)toolbarItem;
		%>

		<%= var %>.push(
			{
				icon: '<%= javaScriptToolbarItem.getIcon() %>',
				label: '<%= UnicodeFormatter.toString(javaScriptToolbarItem.getLabel()) %>',
				on: {
					click: function(event) {
						<%= javaScriptToolbarItem.getOnClick() %>
					}
				}
			}
		);

		<c:if test="<%= Validator.isNotNull(javaScriptToolbarItem.getJavaScript()) %>">
			<%= javaScriptToolbarItem.getJavaScript() %>
		</c:if>
	</c:when>
	<c:when test="<%= toolbarItem instanceof URLToolbarItem %>">

		<%
		URLToolbarItem urlToolbarItem = (URLToolbarItem)toolbarItem;
		%>

		<%= var %>.push(
			{
				icon: '<%= urlToolbarItem.getIcon() %>',
				label: '<%= UnicodeFormatter.toString(urlToolbarItem.getLabel()) %>',
				on: {
					click: function(event) {
						window.open('<%= urlToolbarItem.getURL() %>', '<%= urlToolbarItem.getTarget() %>');
					}
				}
			}
		);
	</c:when>
</c:choose>