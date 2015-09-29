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

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">

	<%
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);

	boolean hasAddLayoutSetPrototypePermission = PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE);
	%>

	<liferay-portlet:renderURL varImpl="addSiteURL">
		<portlet:param name="mvcPath" value="/edit_site.jsp" />
	</liferay-portlet:renderURL>

	<liferay-frontend:add-menu>
		<c:choose>
			<c:when test="<%= layoutSetPrototypes.isEmpty() && !hasAddLayoutSetPrototypePermission %>">
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addSiteURL.toString() %>" />
			</c:when>
			<c:otherwise>
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "blank-site") %>' url="<%= addSiteURL.toString() %>" />

				<%
				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
					addSiteURL.setParameter("layoutSetPrototypeId", String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()));
				%>

					<liferay-frontend:add-menu-item title="<%= HtmlUtil.escape(layoutSetPrototype.getName(locale)) %>" url="<%= addSiteURL.toString() %>" />

				<%
				}
				%>

				<c:if test="<%= hasAddLayoutSetPrototypePermission %>">
					<portlet:renderURL var="viewSitesURL" />

					<%
					Map<String, String> anchorData = new HashMap<>();

					anchorData.put("navigation", Boolean.TRUE.toString());

					PortletURL manageSiteTemplateURL = PortletProviderUtil.getPortletURL(request, LayoutSetPrototype.class.getName(), PortletProvider.Action.VIEW);

					manageSiteTemplateURL.setParameter("redirect", viewSitesURL);
					manageSiteTemplateURL.setParameter("backURL", viewSitesURL);
					%>

					<liferay-frontend:add-menu-item anchorData="<%= anchorData %>" title='<%= LanguageUtil.get(request, "manage-site-template") %>' url="<%= manageSiteTemplateURL.toString() %>" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</liferay-frontend:add-menu>
</c:if>