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
DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

DateSearchEntry dateSearchEntry = new DateSearchEntry();

List<DDLRecordVersion> recordVersions = DDLRecordVersionServiceUtil.getRecordVersions(record.getRecordId());

for (DDLRecordVersion recordVersion : recordVersions) {
	dateSearchEntry.setDate(recordVersion.getCreateDate());

	request.setAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION, recordVersion);
%>

	<div>
		<ul class="sidebar-header-actions">
			<li>
				<liferay-util:include page="/record_version_action.jsp" servletContext="<%= application %>" />
			</li>
		</ul>

		<h4><liferay-ui:message arguments="<%= recordVersion.getVersion() %>" key="version-x" /></h4>

		<p>
			<small class="text-muted">
				<liferay-ui:message key="author" />: <%= HtmlUtil.escape(recordVersion.getUserName()) %>
			</small>
		</p>

		<p>
			<small class="text-muted">
				<liferay-ui:message key="create-date" />: <%= dateSearchEntry.getName(request) %>
			</small>
		</p>
	</div>

<%
}
%>