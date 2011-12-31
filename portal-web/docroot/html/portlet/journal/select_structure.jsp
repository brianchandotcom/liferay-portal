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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/journal/select_structure" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">

	<%
	DynamicRenderRequest dynamicRenderRequest = new DynamicRenderRequest(renderRequest);

	dynamicRenderRequest.setParameter("groupId", String.valueOf(groupId));

	StructureSearch searchContainer = new StructureSearch(renderRequest, 10, portletURL);
	%>

	<liferay-ui:header
		title="structures"
	/>

	<liferay-ui:search-form
		page="/html/portlet/journal/structure_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	StructureSearchTerms searchTerms = (StructureSearchTerms)searchContainer.getSearchTerms();
	%>

	<%@ include file="/html/portlet/journal/structure_search_results.jspf" %>

	<div class="separator"><!-- --></div>

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalStructure structure = (JournalStructure)results.get(i);

		structure = structure.toEscapedModel();

		ResultRow row = new ResultRow(structure, structure.getId(), i);

		StringBundler sb = new StringBundler(7);

		sb.append("javascript:Liferay.Util.getOpener().");
		sb.append(renderResponse.getNamespace());
		sb.append("selectStructure('");
		sb.append(structure.getStructureId());
		sb.append("', '");
		sb.append(structure.getName(locale));
		sb.append("', Liferay.Util.getWindow());");

		String rowHREF = sb.toString();

		// Structure id

		row.addText(structure.getStructureId(), rowHREF);

		// Name

		row.addText(HtmlUtil.escape(structure.getName(locale)), rowHREF);

		// Description

		if (Validator.isNotNull(structure.getDescription(locale))) {
			row.addText(HtmlUtil.escape(structure.getDescription(locale)), rowHREF);
		}
		else {
			row.addText(StringPool.BLANK);
		}

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />searchStructureId);
</aui:script>