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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

List<Portlet> embeddedPortlets = Collections.emptyList();

if (selLayout.isSupportsEmbeddedPortlets()) {
	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	embeddedPortlets = selLayoutTypePortlet.getEmbeddedPortlets();
}

RowChecker rowChecker = new RowChecker(liferayPortletResponse);

rowChecker.setRowIds("removeEmbeddedPortletIds");
%>

<c:choose>
	<c:when test="<%= selLayout.isLayoutPrototypeLinkActive() %>">

		<%
		rowChecker = null;
		%>

		<div class="alert alert-info">
			<liferay-ui:message key="layout-inherits-from-a-prototype-portlets-cannot-be-manipulated" />
		</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-warning">
			<liferay-ui:message key="warning-preferences-of-selected-portlets-will-be-reset-or-deleted" />
		</div>
	</c:otherwise>
</c:choose>

<liferay-ui:search-container
	deltaConfigurable="<%= false %>"
	rowChecker="<%= rowChecker %>"
>
	<liferay-ui:search-container-results results="<%= embeddedPortlets %>" />

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Portlet"
		escapedModel="<%= true %>"
		keyProperty="portletId"
		modelVar="portlet"
	>
		<liferay-ui:search-container-column-text
			name="portlet-id"
			property="portletId"
		/>

		<liferay-ui:search-container-column-text
			name="title"
		>
			<%= PortalUtil.getPortletTitle(portlet, application, locale) %>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="status"
		>
			<c:choose>
				<c:when test="<%= !portlet.isActive() %>">
					<liferay-ui:message key="inactive" />
				</c:when>
				<c:when test="<%= !portlet.isReady() %>">
					<liferay-ui:message arguments="portlet" key="is-not-ready" />
				</c:when>
				<c:when test="<%= portlet.isUndeployedPortlet() %>">
					<liferay-ui:message key="undeployed" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="active" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="none" />
</liferay-ui:search-container>