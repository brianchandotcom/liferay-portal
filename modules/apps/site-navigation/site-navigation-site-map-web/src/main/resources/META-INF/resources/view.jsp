<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ddm:template-renderer
	className="<%= LayoutSet.class.getName() %>"
	contextObjects='<%=
		HashMapBuilder.<String, Object>put(
			"siteNavigationSiteMapDisplayContext", siteNavigationSiteMapDisplayContext
		).build()
	%>'
	displayStyle="<%= siteNavigationSiteMapPortletInstanceConfiguration.displayStyle() %>"
	displayStyleGroupId="<%= siteNavigationSiteMapDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= siteNavigationSiteMapDisplayContext.getRootLayouts() %>"
>
	<%= siteNavigationSiteMapDisplayContext.buildSiteMap() %>
</liferay-ddm:template-renderer>