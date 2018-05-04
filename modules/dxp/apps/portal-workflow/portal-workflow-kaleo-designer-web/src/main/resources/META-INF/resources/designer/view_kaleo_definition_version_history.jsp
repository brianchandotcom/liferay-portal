<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/designer/init.jsp" %>

<%
KaleoDefinitionVersion kaleoDefinitionVersion = (KaleoDefinitionVersion)request.getAttribute(KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION);
%>

<liferay-ui:search-container
	id="kaleoDefinitionVersions"
>
	<liferay-ui:search-container-results
		results="<%= kaleoDesignerDisplayContext.getKaleoDefinitionVersions(kaleoDefinitionVersion) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"
	>
		<liferay-ui:search-container-column-jsp
			cssClass="lfr-version-column"
			path="/designer/kaleo_definition_version_history_info.jsp"
		/>

		<liferay-ui:search-container-column-jsp
			path="/designer/kaleo_definition_version_history_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="list"
		markupView="lexicon"
	/>
</liferay-ui:search-container>