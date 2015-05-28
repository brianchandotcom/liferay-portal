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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<aui:button cssClass="close" name="closePanelEdit" value="&times;" />

<c:if test="<%= themeDisplay.isShowSiteAdministrationIcon() %>">

	<%
	long selPlid= ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
	%>

	<liferay-portlet:renderURL plid="<%= PortalUtil.getControlPanelPlid(company.getCompanyId()) %>" portletName="<%= PortletKeys.GROUP_PAGES %>" varImpl="editPageURL" windowState="<%= WindowState.NORMAL.toString() %>">
		<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
		<portlet:param name="tabs1" value='<%= layout.isPrivateLayout() ? "private-pages" : "public-pages" %>' />
		<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getLiveGroupId()) %>" />
		<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
		<portlet:param name="treeId" value="layoutsTree" />
		<portlet:param name="viewLayout" value="true" />
	</liferay-portlet:renderURL>

	<%
	String editPageURLString = HttpUtil.setParameter(editPageURL.toString(), "controlPanelCategory", "current_site");

	editPageURLString = HttpUtil.setParameter(editPageURLString, "doAsGroupId", String.valueOf(groupDisplayContextHelper.getLiveGroupId()));
	editPageURLString = HttpUtil.setParameter(editPageURLString, "refererPlid", String.valueOf(selPlid));
	%>

	<aui:button-row>
		<aui:button href="<%= editPageURLString %>" primary="<%= true %>" value="edit-page" />
	</aui:button-row>
</c:if>