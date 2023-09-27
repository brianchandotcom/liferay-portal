<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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