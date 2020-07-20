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
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<c:if test="<%= layout != null %>">
			<liferay-util:html-top>
				<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/product-navigation-control-menu-web/css/App.css") %>" rel="stylesheet" />
			</liferay-util:html-top>

			<%
			AddContentPanelDisplayContext addContentPanelDisplayContext = new AddContentPanelDisplayContext(request, liferayPortletRequest, liferayPortletResponse);
			%>

			<c:if test="<%= addContentPanelDisplayContext.showAddPanel() %>">
				<div class="add-content-menu" data-qa-id="addPanelBody" id="<portlet:namespace />addPanelContainer">
					<react:component
						module="js/AddPanel"
						props="<%= addContentPanelDisplayContext.getAddContentPanelData() %>"
					/>
				</div>
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>