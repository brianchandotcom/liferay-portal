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

<div>
	<react:component
		module="js/components/ModalImportListTypeDefinition"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"importListTypeDefinitionURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/list_type_definitions/import_list_type_definition"
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
	function <portlet:namespace />openImportListTypeDefinitionModal() {}

	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />importListTypeDefinition',
		() => {
			Liferay.componentReady(
				'<portlet:namespace />importListTypeDefinitionModal'
			).then((importListTypeDefinitionModal) => {
				importListTypeDefinitionModal.open();
			});
		}
	);
</aui:script>