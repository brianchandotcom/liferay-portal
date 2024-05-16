<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();

DateSearchEntry dateSearchEntry = new DateSearchEntry();

dateSearchEntry.setDate(ddmStructure.getModifiedDate());
%>

<div class="clamp-container">
	<h4 class="text-truncate">
		<aui:a cssClass="form-instance-name" href="<%= (String)request.getAttribute(WebKeys.SEARCH_ENTRY_HREF) %>">
			<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>
		</aui:a>
	</h4>

	<h5 class="text-default">
		<div class="form-instance-description text-truncate">
			<%= HtmlUtil.escape(ddmStructure.getDescription(locale)) %>
		</div>
	</h5>

	<h5 class="text-default">
		<span class="form-instance-id">
			<liferay-ui:message key="id" />: <%= ddmStructure.getStructureId() %>
		</span>
		<span class="form-instance-modified-date">
			<liferay-ui:message key="modified-date" />: <%= dateSearchEntry.getName(request) %>
		</span>
	</h5>
</div>