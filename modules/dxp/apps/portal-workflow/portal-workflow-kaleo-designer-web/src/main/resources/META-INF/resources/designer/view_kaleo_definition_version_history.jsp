<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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