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

<%@ include file="/control/menu/init.jsp" %>

<%
Layout curLayout = layout;

if (curLayout.getClassNameId() == PortalUtil.getClassNameId(Layout.class)) {
	curLayout = LayoutLocalServiceUtil.fetchLayout(curLayout.getClassPK());
}

PortletURL editPageURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.RENDER_PHASE);

editPageURL.setParameter("mvcRenderCommandName", "/layout/edit_layout");
editPageURL.setParameter("redirect", currentURL);
editPageURL.setParameter("backURL", currentURL);

if (curLayout.isSystem()) {
	editPageURL.setParameter("portletResource", LayoutPageTemplateAdminPortletKeys.LAYOUT_PAGE_TEMPLATES);
}

editPageURL.setParameter("groupId", String.valueOf(curLayout.getGroupId()));
editPageURL.setParameter("selPlid", String.valueOf(curLayout.getPlid()));
editPageURL.setParameter("privateLayout", String.valueOf(curLayout.isPrivateLayout()));

String title = HtmlUtil.escape(LanguageUtil.get(resourceBundle, "configure-page"));
%>

<li class="control-menu-nav-item">
	<a aria-label="<%= title %>" class="control-menu-icon lfr-portal-tooltip" data-qa-id="editLayout" data-title="<%= title %>" href="<%= editPageURL %>">
		<aui:icon cssClass="icon-monospaced" image="cog" markupView="lexicon" />
	</a>
</li>

<liferay-ui:success key="layoutUpdated" message="the-page-was-updated-succesfully" targetNode="#controlMenuAlertsContainer" />