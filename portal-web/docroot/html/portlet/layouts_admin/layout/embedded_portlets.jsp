<%@ page import="java.util.Comparator" %>

<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
List<Portlet> embeddedPortlets = (List<Portlet>)request.getAttribute("edit_pages.jsp-embeddedPortlets");

PortletResponse portletResponse = renderResponse;

if (portletResponse == null) {
	portletResponse = resourceResponse;
}

RowChecker rowChecker = new RowChecker(portletResponse);

rowChecker.setRowIds("removeEmbeddedPortletIds");
%>

<h3><liferay-ui:message key="embedded-portlets" /></h3>

<c:if test="<%= selLayout.isLayoutPrototypeLinkActive() %>">

	<%
	rowChecker = null;
	%>

	<div class="portlet-msg-info">
		<liferay-ui:message key="layout-inherits-from-a-prototype-portlets-cant-be-manipulated" />
	</div>
</c:if>

<c:if test="<%= (rowChecker != null) %>">
	<div class="portlet-msg-alert">
		<liferay-ui:message key="warning-selected-portlets-will-be-removed" />
	</div>
</c:if>

<liferay-ui:search-container
	deltaConfigurable="<%= false %>"
	rowChecker="<%= rowChecker %>"
>

	<liferay-ui:search-container-results results="<%= embeddedPortlets %>" />

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Portlet"
		escapedModel="<%= true %>"
		keyProperty="portletId"
		modelVar="embeddedPortlet"
	>

		<liferay-ui:search-container-column-text
			name="portlet-id"
			property="portletId"
		/>

		<liferay-ui:search-container-column-text
			name="title"
			buffer="sb"
		>

			<%
			String rootPortletId = PortletConstants.getRootPortletId(embeddedPortlet.getPortletId());

			String portletTitle = PortalUtil.getPortletTitle(rootPortletId, themeDisplay.getLocale());

			if (embeddedPortlet.getPortletApp().isWARFile()) {
				String servletCtxName = embeddedPortlet.getPortletApp().getServletContextName();

				ServletContext servletContext = ServletContextPool.get(servletCtxName);

				portletTitle = PortalUtil.getPortletTitle(embeddedPortlet, servletContext, themeDisplay.getLocale());
			}

			sb.append(portletTitle);
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			name="status"
			buffer="sb"
		>

			<%
			String portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "active");

			if (embeddedPortlet.isUndeployedPortlet()) {
				portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "undeployed");
			}
			else if (!embeddedPortlet.isReady()) {
				portletStatus = LanguageUtil.format(themeDisplay.getLocale(), "is-not-ready", new Object[]{"portlet"});
			}
			else if (!embeddedPortlet.isActive()) {
				portletStatus = LanguageUtil.get(themeDisplay.getLocale(), "inactive");
			}

			sb.append(portletStatus);
			%>

		</liferay-ui:search-container-column-text>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="none" />
</liferay-ui:search-container>