<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

HeadlessBuilderWebDisplayContext headlessBuilderWebDisplayContext = (HeadlessBuilderWebDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "edit-api-application"));
%>

<react:component
	module="js/components/EditAPIApplication"
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