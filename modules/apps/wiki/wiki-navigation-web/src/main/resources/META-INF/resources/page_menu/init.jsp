<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long selNodeId = PrefsParamUtil.getLong(portletPreferences, request, "selNodeId");
String selTitle = PrefsParamUtil.getString(portletPreferences, request, "selTitle");

WikiPage wikiPage = null;

if ((selNodeId > 0) && Validator.isNotNull(selTitle)) {
	try {
		wikiPage = WikiPageServiceUtil.getPage(selNodeId, selTitle);
	}
	catch (Exception e) {
	}
}
%>