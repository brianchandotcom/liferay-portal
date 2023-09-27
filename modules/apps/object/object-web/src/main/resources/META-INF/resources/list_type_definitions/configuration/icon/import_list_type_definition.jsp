<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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