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

<%@ include file="/entries/init.jsp" %>

<%
PortletURL editLayoutURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.RENDER_PHASE);

editLayoutURL.setParameter("mvcPath", "/edit_content_layout.jsp");
editLayoutURL.setParameter("redirect", PortalUtil.getCurrentURL(request));
editLayoutURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
editLayoutURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
%>

<li class="control-menu-nav-item">
	<a class="control-menu-icon lfr-portal-tooltip product-menu-toggle sidenav-toggler" data-title="<%= LanguageUtil.get(request, "edit") %>" href="<%= editLayoutURL.toString() %>">
		<aui:icon cssClass="icon-monospaced" image="pencil" markupView="lexicon" />
	</a>
</li>