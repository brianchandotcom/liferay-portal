<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long recordSetId = ParamUtil.getLong(request, liferayPortletResponse.getNamespace() + "recordSetId");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/dynamic_data_lists/export_record_set" var="exportRecordSetURL">
	<portlet:param name="recordSetId" value="<%= String.valueOf(recordSetId) %>" />
</liferay-portlet:resourceURL>

<aui:script>
	Liferay.Util.setPortletConfigurationIconAction(
		'<portlet:namespace />exportRecordSet',
		() => {
			<portlet:namespace />exportRecordSet('<%= exportRecordSetURL %>');
		}
	);
</aui:script>