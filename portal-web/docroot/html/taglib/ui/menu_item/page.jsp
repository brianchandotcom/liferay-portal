<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/menu_item/init.jsp" %>

<%
MenuItem menuItem = (MenuItem)request.getAttribute("liferay-ui:menu_item:menuItem");
%>

<c:choose>
	<c:when test="<%= menuItem instanceof DeleteMenuItem %>">

		<%
		DeleteMenuItem deleteMenuItem = (DeleteMenuItem)menuItem;
		%>

		<liferay-ui:icon-delete
			message="<%= HtmlUtil.escape(deleteMenuItem.getLabel()) %>"
			trash="<%= deleteMenuItem.isTrash() %>"
			url="<%= deleteMenuItem.getURL() %>"
		/>
	</c:when>
	<c:when test="<%= menuItem instanceof JavaScriptMenuItem %>">

		<%
		JavaScriptMenuItem javaScriptMenuItem = (JavaScriptMenuItem)menuItem;
		%>

		<liferay-ui:icon
			data="<%= javaScriptMenuItem.getData() %>"
			iconCssClass="<%= javaScriptMenuItem.getIcon() %>"
			message="<%= HtmlUtil.escape(javaScriptMenuItem.getLabel()) %>"
			onClick="<%= javaScriptMenuItem.getOnClick() %>"
			url="javascript:void(0);"
		/>

		<c:if test="<%= Validator.isNotNull(javaScriptMenuItem.getJavaScript()) %>">
			<aui:script>
				<%= javaScriptMenuItem.getJavaScript() %>
			</aui:script>
		</c:if>
	</c:when>
	<c:when test="<%= menuItem instanceof URLMenuItem %>">

		<%
		URLMenuItem urlMenuItem = (URLMenuItem)menuItem;
		%>

		<liferay-ui:icon
			data="<%= urlMenuItem.getData() %>"
			iconCssClass="<%= urlMenuItem.getIcon() %>"
			message="<%= HtmlUtil.escape(urlMenuItem.getLabel()) %>"
			method="<%= urlMenuItem.getMethod() %>"
			target="<%= urlMenuItem.getTarget() %>"
			url="<%= urlMenuItem.getURL() %>"
			useDialog="<%= urlMenuItem.isUseDialog() %>"
		/>
	</c:when>
</c:choose>