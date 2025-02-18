<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FilesSectionDisplayContext filesSectionDisplayContext = (FilesSectionDisplayContext)request.getAttribute(FilesSectionDisplayContext.class.getName());
%>

<div class="cms-section">
	<frontend-data-set:headless-display
		apiURL="<%= filesSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= filesSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= filesSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= filesSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= filesSectionDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.FILES_SECTION %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</div>