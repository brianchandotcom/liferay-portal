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
String redirect = ParamUtil.getString(request, "redirect");

DDLRecord record = (DDLRecord)request.getAttribute(DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

DDLRecordSet recordSet = record.getRecordSet();

DDMStructure ddmStructure = recordSet.getDDMStructure();

long formDDMTemplateId = ParamUtil.getLong(request, "formDDMTemplateId");

PortletURL redirectURL = renderResponse.createRenderURL();

redirectURL.setParameter("mvcPath", "/edit_record.jsp");
redirectURL.setParameter("redirect", redirect);
redirectURL.setParameter("recordId", String.valueOf(record.getRecordId()));
redirectURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_record_history.jsp");
portletURL.setParameter("redirect", redirectURL.toString());
portletURL.setParameter("recordId", String.valueOf(record.getRecordId()));

if (ddlDisplayContext.isAdminPortlet()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirectURL.toString());

	renderResponse.setTitle(LanguageUtil.format(request, "x-history", ddmStructure.getName(locale), false));
}
%>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="fm">

	<%
	SearchContainer searchContainer = new SearchContainer(renderRequest, portletURL, new ArrayList(), null);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add("id");
	headerNames.add("version");
	headerNames.add("status");
	headerNames.add("author");
	headerNames.add(StringPool.BLANK);

	int total = DDLRecordVersionServiceUtil.getRecordVersionsCount(record.getRecordId());

	searchContainer.setTotal(total);

	List<DDLRecordVersion> results = DDLRecordVersionServiceUtil.getRecordVersions(record.getRecordId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		DDLRecordVersion recordVersion = results.get(i);

		recordVersion = recordVersion.toEscapedModel();

		ResultRow row = new ResultRow(recordVersion, recordVersion.getRecordVersionId(), i);

		row.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("mvcPath", "/view_record.jsp");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("recordId", String.valueOf(recordVersion.getRecordId()));
		rowURL.setParameter("version", recordVersion.getVersion());
		rowURL.setParameter("formDDMTemplateId", String.valueOf(formDDMTemplateId));

		// Record version id

		row.addText(String.valueOf(recordVersion.getRecordVersionId()), rowURL);

		// Version

		row.addText(recordVersion.getVersion(), rowURL);

		// Status

		row.addStatus(recordVersion.getStatus(), recordVersion.getStatusByUserId(), recordVersion.getStatusDate(), rowURL);

		// Author

		row.addText(PortalUtil.getUserName(recordVersion), rowURL);

		// Action

		row.addJSP("/record_version_action.jsp", "entry-action", application, request, response);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= searchContainer %>" />
</aui:form>