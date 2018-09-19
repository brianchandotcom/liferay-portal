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

<%@ include file="/add_content_layout/init.jsp" %>

<%
String portletNamespace = PortalUtil.getPortletNamespace(ContentLayoutPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

PortletURL addPanelURL = PortletURLFactoryUtil.create(request, ContentLayoutPortletKeys.CONTENT_PAGE_EDITOR_PORTLET, PortletRequest.RENDER_PHASE);

addPanelURL.setParameter("mvcPath", "/add_content_layout_panel.jsp");
addPanelURL.setParameter("stateMaximized", String.valueOf(themeDisplay.isStateMaximized()));
addPanelURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<li class="control-menu-nav-item">
	<a class="control-menu-icon lfr-portal-tooltip product-menu-toggle sidenav-toggler" data-content="body" data-open-class="open-admin-panel" data-qa-id="add" data-target="#<%= portletNamespace %>addContentLayoutPanelId" data-title="<%= HtmlUtil.escape(LanguageUtil.get(request, "add")) %>" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" data-url="<%= addPanelURL.toString() %>" href="javascript:;" id="<%= portletNamespace %>addContentLayoutToggleId">
		<aui:icon cssClass="icon-monospaced" image="plus" markupView="lexicon" />
	</a>
</li>