<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long siteNavigationMenuItemId = ParamUtil.getLong(request, "siteNavigationMenuItemId");

SiteNavigationMenuItem siteNavigationMenuItem = SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(siteNavigationMenuItemId);
%>

<c:if test="<%= siteNavigationMenuItem != null %>">

	<%
	String redirect = ParamUtil.getString(request, "redirect");

	SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());
	%>

	<portlet:actionURL name="/site_navigation_admin/edit_site_navigation_menu_item" var="editSiteNavigationMenuItemURL" />

	<aui:form action="<%= editSiteNavigationMenuItemURL %>">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationMenuItem.getSiteNavigationMenuId() %>" />
		<aui:input name="siteNavigationMenuItemId" type="hidden" value="<%= siteNavigationMenuItem.getSiteNavigationMenuItemId() %>" />
		<aui:input name="parentSiteNavigationMenuItemId" type="hidden" value="<%= siteNavigationMenuItem.getParentSiteNavigationMenuItemId() %>" />

		<%
		siteNavigationMenuItemType.renderEditPage(request, PipingServletResponseFactory.createPipingServletResponse(pageContext), siteNavigationMenuItem);
		%>

		<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), SiteNavigationMenuItem.class.getName(), siteNavigationMenuItemId, null) %>">
			<liferay-expando:custom-attribute-list
				className="<%= SiteNavigationMenuItem.class.getName() %>"
				classPK="<%= siteNavigationMenuItemId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</c:if>

		<clay:button
			block="<%= true %>"
			label="save"
			type="submit"
		/>
	</aui:form>
</c:if>