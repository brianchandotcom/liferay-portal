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
		module="js/components/ModalImportObjectDefinition"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"importObjectDefinitionURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/object_definitions/import_object_definition"
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
	function <portlet:namespace />openImportObjectDefinitionModal() {}

	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />importObjectDefinition',
		() => {
			Liferay.componentReady(
				'<portlet:namespace />importObjectDefinitionModal'
			).then((importObjectDefinitionModal) => {
				importObjectDefinitionModal.open();
			});
		}
	);
</aui:script>