<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

DateSearchEntry dateSearchEntry = new DateSearchEntry();

for (DDLRecordVersion recordVersion : DDLRecordVersionServiceUtil.getRecordVersions(record.getRecordId())) {
	dateSearchEntry.setDate(recordVersion.getCreateDate());

	request.setAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_VERSION, recordVersion);
%>

	<ul class="list-group sidebar-list-group">
		<li class="list-group-item list-group-item-flex">
			<clay:content-col
				expand="<%= true %>"
			>
				<h4 class="list-group-title">
					<liferay-ui:message arguments="<%= recordVersion.getVersion() %>" key="version-x" />
				</h4>

				<p class="list-group-subtitle">
					<liferay-ui:message key="author" />: <%= HtmlUtil.escape(recordVersion.getUserName()) %>
				</p>

				<p class="list-group-subtext">
					<liferay-ui:message key="create-date" />: <%= dateSearchEntry.getName(request) %>
				</p>
			</clay:content-col>

			<clay:content-col>
				<ul class="autofit-padded-no-gutters autofit-row">
					<li class="autofit-col">
						<liferay-util:include page="/record_version_action.jsp" servletContext="<%= application %>" />
					</li>
				</ul>
			</clay:content-col>
		</li>
	</ul>

<%
}
%>