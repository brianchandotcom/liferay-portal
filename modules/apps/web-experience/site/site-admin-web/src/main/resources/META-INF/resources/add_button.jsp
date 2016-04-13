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
	Group group = siteAdminDisplayContext.getGroup();
	%>

	<liferay-portlet:renderURL varImpl="addSiteURL">
		<portlet:param name="mvcPath" value="/edit_site.jsp" />

		<c:if test="<%= (group != null) && siteAdminDisplayContext.hasAddChildSitePermission(group) %>">
			<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
		</c:if>
	</liferay-portlet:renderURL>

	<%
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<liferay-frontend:add-menu>
		<c:choose>
			<c:when test="<%= layoutSetPrototypes.isEmpty() %>">
				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addSiteURL.toString() %>" />
			</c:when>
			<c:otherwise>

				<%
				addSiteURL.setParameter("showPrototypes", "0");
				%>

				<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "blank-site") %>' url="<%= addSiteURL.toString() %>" />

				<%
				addSiteURL.setParameter("showPrototypes", "1");

				for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
					addSiteURL.setParameter("layoutSetPrototypeId", String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()));
				%>

					<liferay-frontend:add-menu-item title="<%= layoutSetPrototype.getName(locale) %>" url="<%= addSiteURL.toString() %>" />

				<%
				}
				%>

			</c:otherwise>
		</c:choose>
	</liferay-frontend:add-menu>
</c:if>