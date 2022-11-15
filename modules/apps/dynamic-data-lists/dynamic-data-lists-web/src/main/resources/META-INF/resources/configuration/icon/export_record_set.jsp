<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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