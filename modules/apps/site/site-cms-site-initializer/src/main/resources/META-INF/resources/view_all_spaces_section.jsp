<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewAllSpacesSectionDisplayContext viewAllSpacesSectionDisplayContext = (ViewAllSpacesSectionDisplayContext)request.getAttribute(ViewAllSpacesSectionDisplayContext.class.getName());
%>

<div class="cms-section custom-empty-state">
	<frontend-data-set:headless-display
		additionalProps="<%= viewAllSpacesSectionDisplayContext.getAdditionalProps() %>"
		apiURL="<%= viewAllSpacesSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= viewAllSpacesSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= viewAllSpacesSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= viewAllSpacesSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= viewAllSpacesSectionDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.ALL_SPACES_SECTION %>"
		itemsPerPage="<%= 10 %>"
		propsTransformer="{AllSpacesFDSPropsTransformer} from site-cms-site-initializer"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</div>