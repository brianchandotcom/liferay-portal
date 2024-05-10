<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMDataProviderInstance ddmDataProviderInstance = (DDMDataProviderInstance)row.getObject();
%>

<div class="clamp-container">
	<div class="h4 text-truncate">
		<aui:a href="<%= (String)request.getAttribute(WebKeys.SEARCH_ENTRY_HREF) %>">
			<%= HtmlUtil.escape(ddmDataProviderInstance.getName(locale)) %>
		</aui:a>
	</div>

	<div class="h5 text-default">
		<div class="text-truncate">
			<%= HtmlUtil.escape(ddmDataProviderInstance.getDescription(locale)) %>
		</div>
	</div>

	<div class="h5 text-default">
		<span class="data-provider-instance-id">
			<liferay-ui:message key="id" />: <%= ddmDataProviderInstance.getDataProviderInstanceId() %>
		</span>
		<span class="data-provider-instance-modified-date">

			<%
			DateSearchEntry dateSearchEntry = new DateSearchEntry();

			dateSearchEntry.setDate(ddmDataProviderInstance.getModifiedDate());
			%>

			<liferay-ui:message key="modified-date" />: <%= dateSearchEntry.getName(request) %>
		</span>
	</div>
</div>