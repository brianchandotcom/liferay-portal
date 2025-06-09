<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AllSpacesSectionDisplayContext allSpacesSectionDisplayContext = (AllSpacesSectionDisplayContext)request.getAttribute(AllSpacesSectionDisplayContext.class.getName());
%>

<div class="cms-section custom-empty-state">
	<frontend-data-set:headless-display
		additionalProps="<%= allSpacesSectionDisplayContext.getAdditionalProps() %>"
		apiURL="<%= allSpacesSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= allSpacesSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= allSpacesSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= allSpacesSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= allSpacesSectionDisplayContext.getFDSActionDropdownItems() %>"
		formName="fm"
		id="<%= CMSSiteInitializerFDSNames.ALL_SPACES_SECTION %>"
		itemsPerPage="<%= 10 %>"
		propsTransformer="{AllSpacesFDSPropsTransformer} from site-cms-site-initializer"
		selectedItemsKey="id"
		selectionType="multiple"
		style="fluid"
	/>
</div>