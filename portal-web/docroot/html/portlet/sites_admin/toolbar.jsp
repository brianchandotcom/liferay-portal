<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewSitesURL">
		<portlet:param name="struts_action" value="/sites_admin/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewSitesURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">

		<%
		List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
		%>

		<liferay-portlet:renderURL varImpl="addSiteURL">
			<portlet:param name="struts_action" value="/sites_admin/edit_site" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<c:choose>
			<c:when test="<%= layoutSetPrototypes.isEmpty() && !PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE) %>">
				<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
					<a href="<%= addSiteURL %>"><liferay-ui:message key="add" /></a>
				</span>
			</c:when>
			<c:otherwise>
				<liferay-ui:icon-menu align="left" cssClass='<%= "lfr-toolbar-button add-button " + (toolbarItem.equals("add") ? "current" : StringPool.BLANK) %>' direction="down" extended="<%= false %>" icon='<%= themeDisplay.getPathThemeImages() + "/common/add.png" %>' message="add">

					<%
					addSiteURL.setParameter("showPrototypes", "0");
					%>

					<liferay-ui:icon
						image="site_icon"
						message="blank-site"
						method="get"
						url='<%= addSiteURL.toString() %>'
					/>

					<%
					addSiteURL.setParameter("showPrototypes", "1");

					for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
						addSiteURL.setParameter("layoutSetPrototypeId", String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()));
					%>

						<liferay-ui:icon
							image="site_icon"
							message="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>"
							method="get"
							url='<%= addSiteURL.toString() %>'
						/>

					<%
					}
					%>

					<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE) %>">
						<liferay-portlet:renderURL varImpl="manageSiteTemplateURL" portletName="<%= PortletKeys.LAYOUT_SET_PROTOTYPE %>">
							<portlet:param name="struts_action" value="/layout_set_prototypes/view" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
							<portlet:param name="backURL" value="<%= currentURL %>" />
						</liferay-portlet:renderURL>

						<liferay-ui:icon
							cssClass="highlited"
							image="configuration"
							message="manage-site-template"
							method="get"
							url="<%= manageSiteTemplateURL.toString() %>"
						/>
					</c:if>
				</liferay-ui:icon-menu>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>