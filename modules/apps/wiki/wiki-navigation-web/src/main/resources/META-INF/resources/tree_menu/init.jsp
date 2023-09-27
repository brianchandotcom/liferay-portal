<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
int depth = PrefsParamUtil.getInteger(portletPreferences, request, "depth", WikiNavigationConstants.DEPTH_ALL);

long selNodeId = PrefsParamUtil.getLong(portletPreferences, request, "selNodeId");

WikiGroupServiceConfiguration wikiGroupServiceConfiguration = (WikiGroupServiceConfiguration)request.getAttribute(WikiGroupServiceConfiguration.class.getName());

if (selNodeId <= 0) {
	try {
		WikiNode node = WikiNodeServiceUtil.getNode(themeDisplay.getScopeGroupId(), wikiGroupServiceConfiguration.initialNodeName());

		selNodeId = node.getNodeId();
	}
	catch (Exception e) {
	}
}

themeDisplay.setThemeJsBarebone(false);
%>