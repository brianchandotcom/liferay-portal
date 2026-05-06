<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewContentSitesDisplayContext viewContentSitesDisplayContext = (ViewContentSitesDisplayContext)request.getAttribute(ViewContentSitesDisplayContext.class.getName());
%>

<frontend-data-set:headless-display
	apiURL="<%= viewContentSitesDisplayContext.getAPIURL() %>"
	bulkActionDropdownItems="<%= viewContentSitesDisplayContext.getBulkActionDropdownItems() %>"
	creationMenu="<%= viewContentSitesDisplayContext.getCreationMenu() %>"
	emptyState="<%= viewContentSitesDisplayContext.getEmptyState() %>"
	fdsActionDropdownItems="<%= viewContentSitesDisplayContext.getFDSActionDropdownItems() %>"
	id="<%= ContentSiteGeneratorFDSNames.CONTENT_SITE_GENERATOR %>"
	itemsPerPage="<%= 20 %>"
	selectedItemsKey="id"
	selectionType="multiple"
	style="fluid"
/>