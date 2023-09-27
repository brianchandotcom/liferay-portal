<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />delivery',
		() => {
			Liferay.Portlet.openModal({
				namespace: '<portlet:namespace />',
				portletSelector: '#p_p_id_<%= portletDisplay.getId() %>_',
				portletId: '<%= portletDisplay.getId() %>',
				title: '<liferay-ui:message key="configuration" />',
				url:
					'<%=
						HtmlUtil.escapeJS(
							PortletURLBuilder.create(
								PortletURLFactoryUtil.create(renderRequest, NotificationsPortletKeys.NOTIFICATIONS, PortletRequest.RENDER_PHASE)
							).setMVCPath(
								"/notifications/configuration.jsp"
							).setWindowState(
								LiferayWindowState.POP_UP
							).buildString())
			%>',
			});
		}
	);
</aui:script>