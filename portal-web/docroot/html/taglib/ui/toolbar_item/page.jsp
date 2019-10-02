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
%>

<c:choose>
	<c:when test="<%= toolbarItem instanceof JavaScriptToolbarItem %>">

		<%
		JavaScriptToolbarItem javaScriptToolbarItem = (JavaScriptToolbarItem)toolbarItem;
		%>

		<aui:a cssClass="btn btn-secondary" href="javascript:;" onClick="<%= javaScriptToolbarItem.getOnClick() %>">
			<c:if test="<%= Validator.isNotNull(javaScriptToolbarItem.getIcon()) %>">
				<aui:icon image="<%= javaScriptToolbarItem.getIcon() %>" markupView="lexicon" />
			</c:if>

			<%= javaScriptToolbarItem.getLabel() %>
		</aui:a>

		<c:if test="<%= Validator.isNotNull(javaScriptToolbarItem.getJavaScript()) %>">
			<script>
				<%= javaScriptToolbarItem.getJavaScript() %>
			</script>
		</c:if>
	</c:when>
	<c:when test="<%= toolbarItem instanceof URLToolbarItem %>">

		<%
		URLToolbarItem urlToolbarItem = (URLToolbarItem)toolbarItem;
		%>

		<aui:a cssClass="btn btn-secondary" data="<%= urlToolbarItem.getData() %>" href="<%= urlToolbarItem.getURL() %>" target="<%= urlToolbarItem.getTarget() %>">
			<c:if test="<%= Validator.isNotNull(urlToolbarItem.getIcon()) %>">
				<aui:icon image="<%= urlToolbarItem.getIcon() %>" markupView="lexicon" />
			</c:if>

			<%= urlToolbarItem.getLabel() %>
		</aui:a>
	</c:when>
</c:choose>