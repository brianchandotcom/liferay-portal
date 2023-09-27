<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ClientExtensionAdminDisplayContext clientExtensionAdminDisplayContext = (ClientExtensionAdminDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.CLIENT_EXTENSION_ADMIN_DISPLAY_CONTEXT);
%>

<div>
	<react:component
		module="js/components/ModalImportClientExtensionEntry"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"importClientExtensionEntryURL", clientExtensionAdminDisplayContext.getImportClientExtensionEntryURL()
			).put(
				"successURL", clientExtensionAdminDisplayContext.getImportClientExtensionEntrySuccessURL()
			).build()
		%>'
	/>
</div>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />importClientExtensionEntry',
		() => {
			Liferay.componentReady(
				'<portlet:namespace />importClientExtensionEntryModal'
			).then((importClientExtensionEntryModal) => {
				importClientExtensionEntryModal.open();
			});
		}
	);
</aui:script>