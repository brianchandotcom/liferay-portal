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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Group" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String portletId = LayoutAdminPortletKeys.GROUP_PAGES;

Group group = themeDisplay.getScopeGroup();

if (group.isLayoutPrototype()) {
	portletId = LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE;
}

PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(request, portletId, PortletRequest.RENDER_PHASE);

editPageURL.setParameter("backURL", PortalUtil.getCurrentURL(request));
editPageURL.setParameter("groupId", String.valueOf(layout.getGroupId()));
editPageURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
editPageURL.setParameter("privateLayout", String.valueOf(layout.isPrivateLayout()));
%>

<li class="control-menu-nav-item">
	<a class="control-menu-icon lfr-portal-tooltip" data-qa-id="editLayout" data-title="<%= HtmlUtil.escape(LanguageUtil.get(resourceBundle, "configure-page")) %>" href="<%= editPageURL.toString() %>">
		<aui:icon cssClass="icon-monospaced" image="cog" markupView="lexicon" />
	</a>
</li>

<liferay-ui:success key="layoutUpdated" message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-succesfully") %>' targetNode="#controlMenuAlertsContainer" />