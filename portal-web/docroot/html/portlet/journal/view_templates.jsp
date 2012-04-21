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
	<aui:input name="deleteTemplateIds" type="hidden" />

	<liferay-ui:error exception="<%= RequiredTemplateException.class %>">
		<liferay-ui:message key="required-templates-could-not-be-deleted" />

		<liferay-ui:message key="they-are-referenced-by-web-contents" />
	</liferay-ui:error>

	<%
	TemplateSearch searchContainer = new TemplateSearch(renderRequest, portletURL);

	List headerNames = searchContainer.getHeaderNames();

	headerNames.add(StringPool.BLANK);

	searchContainer.setRowChecker(new RowChecker(renderResponse));
	%>

	<liferay-ui:search-form
		page="/html/portlet/journal/template_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
	TemplateSearchTerms searchTerms = (TemplateSearchTerms)searchContainer.getSearchTerms();

	searchTerms.setStructureIdComparator(StringPool.LIKE);
	%>

	<%@ include file="/html/portlet/journal/template_search_results.jspf" %>

	<div class="separator"><!-- --></div>

	<aui:button onClick='<%= renderResponse.getNamespace() + "deleteTemplates();" %>' value="delete" />

	<br /><br />

	<%
	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		JournalTemplate template = (JournalTemplate)results.get(i);

		template = template.toEscapedModel();

		ResultRow row = new ResultRow(template, template.getTemplateId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/journal/edit_template");
		rowURL.setParameter("redirect", currentURL);
		rowURL.setParameter("groupId", String.valueOf(template.getGroupId()));
		rowURL.setParameter("templateId", template.getTemplateId());

		row.setParameter("rowHREF", rowURL.toString());

		// Template id

		row.addText(template.getTemplateId(), rowURL);

		// Name

		row.addText(HtmlUtil.escape(template.getName(locale)), rowURL);

		// Description and image

		row.addJSP("/html/portlet/journal/template_description.jsp");

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/template_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />deleteTemplates',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-templates") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteTemplateIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_template" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>