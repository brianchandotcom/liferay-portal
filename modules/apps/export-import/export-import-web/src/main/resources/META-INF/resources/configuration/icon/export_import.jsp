<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportImport',
		() => {
			Liferay.Portlet.openModal({
				iframeBodyCssClass: '',
				namespace: '<portlet:namespace />',
				onClose: function () {
					Liferay.Portlet.refresh(
						'#p_p_id_<%= portletDisplay.getId() %>_'
					);
				},
				portletSelector: '#p_p_id_<%= portletDisplay.getId() %>_',
				portletId: '<%= portletDisplay.getId() %>',
				title: '<liferay-ui:message key="export-import" />',
				url:
					'<%= HtmlUtil.escapeJS(portletDisplay.getURLExportImport()) %>',
			});
		}
	);
</aui:script>