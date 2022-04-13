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

<%@ include file="/init.jsp" %>

<liferay-ui:icon
	icon="download"
	iconCssClass="dropdown-item-indicator-start"
	id="importDataDefinitionIcon"
	markupView="lexicon"
	message="import-structure"
	onClick='<%= liferayPortletResponse.getNamespace() + "openImportDataDefinitionModal();" %>'
	url="javascript:;"
/>

<div>
	<react:component
		module="js/modals/ImportDataDefinitionModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"importDataDefinitionURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/journal/import_data_definition"
				).setRedirect(
					currentURL
				).buildString()
			).put(
				"nameMaxLength", ModelHintsConstants.TEXT_MAX_LENGTH
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />openImportDataDefinitionModal() {
		Liferay.componentReady(
			'<portlet:namespace />importDataDefinitionModal'
		).then((importDataDefinitionModal) => {
			importDataDefinitionModal.open();
		});
	}
</aui:script>