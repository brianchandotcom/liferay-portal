<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewTasksSectionDisplayContext viewTasksSectionDisplayContext = (ViewTasksSectionDisplayContext)request.getAttribute(ViewTasksSectionDisplayContext.class.getName());
%>

<div>
	<react:component
		module="{TasksQuickFilters} from site-cmp-site-initializer"
		props="<%= viewTasksSectionDisplayContext.getTasksQuickFiltersProperties() %>"
	/>
</div>

<div class="cms-section custom-empty-state">
	<frontend-data-set:headless-display
		apiURL="<%= viewTasksSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= viewTasksSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= viewTasksSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= viewTasksSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= viewTasksSectionDisplayContext.getFDSActionDropdownItems() %>"
		fdsFilters="<%= viewTasksSectionDisplayContext.getFDSFilters() %>"
		formName="fm"
		id="<%= CMPSiteInitializerFDSNames.CMP_TASK %>"
		itemsPerPage="<%= 20 %>"
		propsTransformer="{TasksFDSPropsTransformer} from site-cmp-site-initializer"
		selectedItemsKey="embedded.id"
	/>
</div>