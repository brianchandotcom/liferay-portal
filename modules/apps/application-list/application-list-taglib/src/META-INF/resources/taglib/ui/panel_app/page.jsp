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

<%@ include file="/taglib/ui/panel_app/init.jsp" %>

<%
PanelApp panelApp = (PanelApp)request.getAttribute("application-list-ui:panel-app:panelApp");
PanelCategory panelCategory = (PanelCategory)request.getAttribute("application-list-ui:panel-app:panelCategory");

String ppid = themeDisplay.getPpid();
%>

<li
	<%= ppid.equals(panelApp.getPortletId()) ? "class='selected-portlet'" : StringPool.BLANK %>
	data-search="<%= HtmlUtil.escape(panelCategory.getLabel(themeDisplay.getLocale()) + "-" + panelApp.getLabel(themeDisplay.getLocale())) %>"
	role="presentation"
>

	<%
	PortletURL portletURL = PortletURLFactoryUtil.create(request, panelApp.getPortletId(), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

	portletURL.setWindowState(WindowState.MAXIMIZED);
	%>

	<liferay-portlet:icon-portlet
		ariaRole="menuitem"
		id='<%= "portlet_" + panelApp.getPortletId() %>'
		label="<%= true %>"
		portlet="<%= PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), panelApp.getPortletId()) %>"
		url="<%= portletURL.toString() %>"
	/>
</li>