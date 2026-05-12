<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewWorkflowTasksSectionDisplayContext viewWorkflowTasksSectionDisplayContext = (ViewWorkflowTasksSectionDisplayContext)request.getAttribute(ViewWorkflowTasksSectionDisplayContext.class.getName());
%>

<div class="cms-section custom-empty-state">
	<frontend-data-set:headless-display
		additionalProps="<%= viewWorkflowTasksSectionDisplayContext.getAdditionalProps() %>"
		apiURL="<%= viewWorkflowTasksSectionDisplayContext.getAPIURL() %>"
		bulkActionDropdownItems="<%= viewWorkflowTasksSectionDisplayContext.getBulkActionDropdownItems() %>"
		creationMenu="<%= viewWorkflowTasksSectionDisplayContext.getCreationMenu() %>"
		emptyState="<%= viewWorkflowTasksSectionDisplayContext.getEmptyState() %>"
		fdsActionDropdownItems="<%= viewWorkflowTasksSectionDisplayContext.getFDSActionDropdownItems() %>"
		fdsFilters="<%= viewWorkflowTasksSectionDisplayContext.getFDSFilters() %>"
		formName="fm"
		id="<%= CMPSiteInitializerFDSNames.CMP_WORKFLOW_TASKS %>"
		itemsPerPage="<%= 20 %>"
		propsTransformer="{WorkflowTasksFDSPropsTransformer} from site-cmp-site-initializer"
		selectedItemsKey="embedded.id"
		selectionType="multiple"
	/>
</div>