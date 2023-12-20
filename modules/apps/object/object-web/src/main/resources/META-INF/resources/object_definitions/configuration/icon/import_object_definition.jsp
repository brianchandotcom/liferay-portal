<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		module="js/components/ModalImport/ModalImport"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", "/o/object-admin/v1.0/object-definitions/by-external-reference-code/"
			).put(
				"importURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/object_definitions/import_object_definition"
				).setRedirect(
					currentURL
				).buildString()
			).put(
				"JSONInputId", "objectDefinitionJSON"
			).put(
				"modalImportKey", "objectDefinition"
			).put(
				"nameMaxLength", ModelHintsConstants.TEXT_MAX_LENGTH
			).put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).build()
		%>'
	/>
</div>

<aui:script>
	function <portlet:namespace />openImportModal() {}

	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />importObjectDefinition',
		() => {
			Liferay.componentReady('<portlet:namespace />importModal').then(
				(importModal) => {
					importModal.open();
				}
			);
		}
	);
</aui:script>