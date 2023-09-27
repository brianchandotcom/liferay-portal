<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/tabs/init.jsp" %>

<%
String errorSection = (String)request.getAttribute(WebKeys.ERROR_SECTION);
%>

<aui:script>
	Liferay.Portal.Tabs.show('<%= namespace + param %>', <%= namesJS %>, '<%= UnicodeFormatter.toString((errorSection == null) ? value : errorSection) %>');
</aui:script>