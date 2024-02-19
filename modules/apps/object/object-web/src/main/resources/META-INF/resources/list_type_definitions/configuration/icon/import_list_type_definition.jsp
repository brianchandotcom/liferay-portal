<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		module="{ModalImport} from object-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"apiURL", "/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/"
			).put(
				"importURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/list_type_definitions/import_list_type_definition"
				).setRedirect(
					currentURL
				).buildString()
			).put(
				"JSONInputId", "listTypeDefinitionJSON"
			).put(
				"modalImportKey", "listTypeDefinition"
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
		'<portlet:namespace />importListTypeDefinition',
		() => {
			Liferay.componentReady('<portlet:namespace />importModal').then(
				(importModal) => {
					importModal.open();
				}
			);
		}
	);
</aui:script>