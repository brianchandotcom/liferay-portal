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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");

Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

List<Portlet> embeddedPortlets = Collections.emptyList();

if (selLayout.isSupportsEmbeddedPortlets()) {
	LayoutTypePortlet selLayoutTypePortlet = (LayoutTypePortlet)selLayout.getLayoutType();

	embeddedPortlets = selLayoutTypePortlet.getEmbeddedPortlets();
}

RowChecker rowChecker = new RowChecker(liferayPortletResponse);

if (selLayout.isLayoutPrototypeLinkActive()) {
	rowChecker = null;
}

rowChecker.setRowIds("removeEmbeddedPortletIds");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/embedded_portlets.jsp");
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="embedded-portlets" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, renderResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<div class="text-muted">
		<c:choose>
			<c:when test="<%= selLayout.isLayoutPrototypeLinkActive() %>">
				<liferay-ui:message key="layout-inherits-from-a-prototype-portlets-cannot-be-manipulated" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="warning-preferences-of-selected-portlets-will-be-reset-or-deleted" />
			</c:otherwise>
		</c:choose>
	</div>

	<liferay-ui:search-container
		deltaConfigurable="<%= false %>"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= rowChecker %>"
	>
		<liferay-ui:search-container-results results="<%= embeddedPortlets %>" />

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Portlet"
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

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				path="/embedded_portlets_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" type="none" />
	</liferay-ui:search-container>
</div>