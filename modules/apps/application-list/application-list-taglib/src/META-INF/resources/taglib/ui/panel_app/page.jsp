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

PortletURL portletURL = panelApp.getPortletURL(request);
%>

<c:if test="<%= portletURL != null %>">

	<%
	String ppid = themeDisplay.getPpid();
	%>

	<li
		aria-selected="<%= ppid.equals(panelApp.getPortletId()) ? "true" : StringPool.BLANK %>"
		class="<%= ppid.equals(panelApp.getPortletId()) ? "selected-portlet" : StringPool.BLANK %>"
		role="presentation"
	>

		<%
		Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), panelApp.getPortletId());
		%>

		<aui:a
			ariaRole="menuitem"
			href="<%= portletURL.toString() %>"
			id='<%= "portlet_" + panelApp.getPortletId() %>'
			label="<%= PortalUtil.getPortletTitle(portlet, application, locale) %>"
		/>
	</li>
</c:if>