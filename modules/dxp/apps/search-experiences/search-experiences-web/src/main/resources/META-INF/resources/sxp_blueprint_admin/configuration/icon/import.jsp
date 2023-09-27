<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<div>
	<react:component
		module="sxp_blueprint_admin/js/view_sxp_blueprints/ImportSXPBlueprintModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"portletNamespace", liferayPortletResponse.getNamespace()
			).put(
				"redirectURL", currentURL
			).build()
		%>'
	/>
</div>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />importModal',
		() => {
			Liferay.componentReady('<portlet:namespace />importModal').then(
				(importModal) => {
					importModal.open();
				}
			);
		}
	);
</aui:script>