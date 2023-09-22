<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setBeta(true);

HeadlessBuilderWebDisplayContext headlessBuilderWebDisplayContext = (HeadlessBuilderWebDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<react:component
	module="js/components/ViewAPIApplications"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"apiURLPaths", headlessBuilderWebDisplayContext.getAPIURLPaths()
		).put(
			"basePath", HeadlessBuilderConstants.BASE_PATH
		).put(
			"editURL", headlessBuilderWebDisplayContext.getEditorURL()
		).put(
			"portletId", headlessBuilderWebDisplayContext.getPortletId()
		).build()
	%>'
/>