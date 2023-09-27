<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);
%>

<liferay-ui:search-container
	cssClass="lfr-sidebar-list-group-workflow sidebar-list-group"
	id="kaleoDefinitionVersions"
>
	<liferay-ui:search-container-results
		results="<%= kaleoDesignerDisplayContext.getKaleoDefinitionVersions(kaleoDefinitionVersion) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"
	>
		<liferay-ui:search-container-column-jsp
			cssClass="autofit-col-expand"
			path="/designer/kaleo_definition_version_history_info.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			path="/designer/kaleo_definition_version_history_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
	/>
</liferay-ui:search-container>