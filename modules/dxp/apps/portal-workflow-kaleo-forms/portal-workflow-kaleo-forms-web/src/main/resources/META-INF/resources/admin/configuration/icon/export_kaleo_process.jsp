<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
long kaleoProcessId = ParamUtil.getLong(request, liferayPortletResponse.getNamespace() + "kaleoProcessId");
%>

<portlet:resourceURL id="kaleoProcess" var="exportURL">
	<portlet:param name="kaleoProcessId" value="<%= String.valueOf(kaleoProcessId) %>" />
</portlet:resourceURL>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportKaleoProcess',
		() => {
			<portlet:namespace />exportKaleoProcess('<%= exportURL %>');
		}
	);
</aui:script>