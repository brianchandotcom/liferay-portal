<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:if test="<%= !layout.isTypeControlPanel() && !windowState.equals(LiferayWindowState.EXCLUSIVE) %>">
	<liferay-util:include page="/tabs1.jsp" servletContext="<%= application %>">
		<liferay-util:param name="tabs1" value="setup" />
	</liferay-util:include>
</c:if>

<div class="cadmin portlet-configuration-setup">

	<%
	ConfigurationAction configurationAction = (ConfigurationAction)request.getAttribute(WebKeys.CONFIGURATION_ACTION);

	if (configurationAction != null) {
		configurationAction.include(portletConfig, request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
	}
	%>

</div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"portletId", selPortlet.getPortletName()
		).build()
	%>'
	module="js/EditConfigurationEventHandler"
/>