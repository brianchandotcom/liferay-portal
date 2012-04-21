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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view_structures");
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />

	<aui:input name="groupId" type="hidden" />
	<aui:input name="deleteStructureIds" type="hidden" />

	<liferay-ui:error exception="<%= RequiredStructureException.class %>">
		<liferay-ui:message key="required-structures-could-not-be-deleted" />

		<%
		RequiredStructureException rse = (RequiredStructureException)errorException;
		%>

		<c:if test="<%= rse.getType() == RequiredStructureException.REFERENCED_STRUCTURE %>">
			<liferay-ui:message key="they-are-referenced-by-other-structures" />
		</c:if>

		<c:if test="<%= rse.getType() == RequiredStructureException.REFERENCED_TEMPLATE %>">
			<liferay-ui:message key="they-are-referenced-by-templates" />
		</c:if>

		<c:if test="<%= rse.getType() == RequiredStructureException.REFERENCED_WEB_CONTENT %>">
			<liferay-ui:message key="they-are-referenced-by-web-contents" />
		</c:if>
	</liferay-ui:error>

	<%
	StructureSearch searchContainer = new StructureSearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);

	searchContainer.setRowChecker(new RowChecker(renderResponse));
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/structure_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	StructureSearchTerms searchTerms = (StructureSearchTerms)searchContainer.getSearchTerms();
	%>

	<%@ include file="/html/portlet/journal/structure_search_results.jspf" %>

	<div class="separator"><!-- --></div>

	<aui:button onClick='<%= renderResponse.getNamespace() + "deleteStructures();" %>' value="delete" />

	<br /><br />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalStructure structure = (JournalStructure)results.get(i);

		structure = structure.toEscapedModel();

		ResultRow row = new ResultRow(structure, structure.getStructureId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/journal/edit_structure");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("groupId", String.valueOf(structure.getGroupId()));
		rowURL.setParameter("structureId", structure.getStructureId());

		// Structure id

		row.addText(structure.getStructureId(), rowURL);

		// Name

		row.addText(HtmlUtil.escape(structure.getName(locale)), rowURL);

		// Description

		row.addText(HtmlUtil.escape(structure.getDescription(locale)), rowURL);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/structure_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />deleteStructures',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-structures") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteStructureIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_structure" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>