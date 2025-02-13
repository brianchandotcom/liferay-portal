<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ContentSectionDisplayContext contentSectionDisplayContext = (ContentSectionDisplayContext)request.getAttribute(ContentSectionDisplayContext.class.getName());
%>

<div class="cms-section">
	<frontend-data-set:headless-display
		apiURL="<%= contentSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= contentSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= contentSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= contentSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= contentSectionDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.CONTENT_SECTION %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</div>