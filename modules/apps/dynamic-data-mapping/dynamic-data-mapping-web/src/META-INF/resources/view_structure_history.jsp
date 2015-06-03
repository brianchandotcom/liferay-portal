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
		 
long structureId = ParamUtil.getLong(request, "structureId");

DDMStructure structure = DDMStructureServiceUtil.getStructure(structureId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view_structure_history.jsp");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("structureId", String.valueOf(structureId));

PortletURL backURL = renderResponse.createRenderURL();
backURL.setParameter("mvcPath", "/edit_structure.jsp");
backURL.setParameter("redirect", currentURL);
backURL.setParameter("classNameId", String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)));
backURL.setParameter("classPK", String.valueOf(structure.getStructureId()));
%>

<liferay-ui:header
	backURL="<%= backURL.toString() %>"
	showBackURL="<%= true %>"
	title='<%= LanguageUtil.format(request, "x-history", structure.getName(locale), false) %>'
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

	int total = DDMStructureVersionServiceUtil.getStructureVersionsCount(structureId);

	searchContainer.setTotal(total);

	List<DDMStructureVersion> results = DDMStructureVersionServiceUtil.getStructureVersions(structureId, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		DDMStructureVersion structureVersion = results.get(i);

		structureVersion = structureVersion.toEscapedModel();

		ResultRow row = new ResultRow(structureVersion, structureVersion.getStructureVersionId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("mvcPath", "/view_structure.jsp");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("structureVersionId", String.valueOf(structureVersion.getStructureVersionId()));

		// Structure version id

		row.addText(String.valueOf(structureVersion.getStructureVersionId()), rowURL);

		// Version

		row.addText(structureVersion.getVersion(), rowURL);

		// Status

		row.addStatus(structureVersion.getStatus(), structureVersion.getStatusByUserId(), structureVersion.getStatusDate(), rowURL);

		// Author

		row.addText(PortalUtil.getUserName(structureVersion), rowURL);

		// Action

		row.addJSP("/structure_version_action.jsp", "entry-action", application, request, response);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>