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

<%@ include file="/admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDLRecordSet ddlRecordSet = (DDLRecordSet)row.getObject();

DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

DDMStructureVersion ddmStructureVersion = ddmStructure.getStructureVersion();

String rowURL = (String)request.getAttribute("rowURL");
%>

<liferay-ui:app-view-entry
	actionJsp="/admin/record_set_action.jsp"
	actionJspServletContext="<%= application %>"
	author="<%= ddlRecordSet.getUserName() %>"
	createDate="<%= ddlRecordSet.getCreateDate() %>"
	description="<%= HtmlUtil.escape(ddlRecordSet.getDescription(locale)) %>"
	displayStyle="descriptive"
	groupId="<%= ddlRecordSet.getGroupId() %>"
	latestApprovedVersion="<%= ddmStructureVersion.getVersion() %>"
	latestApprovedVersionAuthor="<%= ddmStructureVersion.getUserName() %>"
	markupView="lexicon"
	modifiedDate="<%= ddlRecordSet.getModifiedDate() %>"
	rowCheckerId="<%= String.valueOf(ddlRecordSet.getRecordSetId()) %>"
	rowCheckerName="<%= DDLRecordSet.class.getSimpleName() %>"
	showCheckbox="<%= false %>"
	thumbnailDivStyle="height: 146px; width: 146px;"
	thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
	thumbnailStyle="max-height: 128px; max-width: 128px;"
	title="<%= HtmlUtil.escape(ddlRecordSet.getName(locale)) %>"
	url="<%= rowURL %>"
	version="<%= ddmStructureVersion.getVersion() %>"
/>