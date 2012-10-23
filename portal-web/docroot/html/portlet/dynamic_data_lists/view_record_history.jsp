<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

DDLRecord record = (DDLRecord)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_RECORD);

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_record_history");
portletURL.setParameter("backURL", backURL);
portletURL.setParameter("recordId", String.valueOf(record.getRecordId()));
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	title="record-history"
/>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">

	<%
	SearchContainer searchContainer = new SearchContainer(renderRequest, portletURL, new ArrayList(), null);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add("id");
	headerNames.add("version");
	headerNames.add("status");
	headerNames.add("author");
	headerNames.add(StringPool.BLANK);

	int total = DDLRecordLocalServiceUtil.getRecordVersionsCount(record.getRecordId());

	searchContainer.setTotal(total);

	List<DDLRecordVersion> results = DDLRecordLocalServiceUtil.getRecordVersions(record.getRecordId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		DDLRecordVersion recordVersion = results.get(i);

		recordVersion = recordVersion.toEscapedModel();

		ResultRow row = new ResultRow(recordVersion, recordVersion.getRecordVersionId(), i);

		row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/dynamic_data_lists/view_record");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("recordId", String.valueOf(recordVersion.getRecordId()));
		rowURL.setParameter("version", String.valueOf(recordVersion.getVersion()));
		rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

		// Record version id

		row.addText(String.valueOf(recordVersion.getRecordVersionId()), rowURL);

		// Version

		row.addText(recordVersion.getVersion(), rowURL);

		// Status

		row.addText(WorkflowConstants.toLabel(recordVersion.getStatus()), rowURL);

		// Author

		row.addText(PortalUtil.getUserName(recordVersion), rowURL);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/dynamic_data_lists/record_version_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>