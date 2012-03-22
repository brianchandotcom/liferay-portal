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

portletURL.setParameter("struts_action", "/journal/view");

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

List headerNames = searchContainer.getHeaderNames();

headerNames.add(2, "status");
headerNames.add(StringPool.BLANK);

searchContainer.setRowChecker(new RowChecker(renderResponse));

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();

boolean showAddArticleButton = JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_ARTICLE);
%>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<c:if test="<%= showAddArticleButton %>">
		<div class="portlet-msg-info">

			<%
			JournalStructure structure = JournalStructureLocalServiceUtil.getStructure(scopeGroupId, displayTerms.getStructureId());
			%>

			<liferay-ui:message arguments="<%= structure.getName(locale) %>" key="showing-content-filtered-by-structure-x" /> (<a href="javascript:<portlet:namespace />addArticle();"><liferay-ui:message key="add-new-web-content" /></a>)
		</div>
	</c:if>
</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">
	<aui:input name="<%= displayTerms.TEMPLATE_ID %>" type="hidden" value="<%= displayTerms.getTemplateId() %>" />

	<c:if test="<%= showAddArticleButton %>">
		<div class="portlet-msg-info">

			<%
			JournalTemplate template = JournalTemplateLocalServiceUtil.getTemplate(scopeGroupId, displayTerms.getTemplateId());
			%>

			<liferay-ui:message arguments="<%= template.getName(locale) %>" key="showing-content-filtered-by-template-x" /> (<a href="javascript:<portlet:namespace />addArticle();"><liferay-ui:message key="add-new-web-content" /></a>)
		</div>
	</c:if>
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.JOURNAL) && !((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getStructureId()) || Validator.isNotNull(displayTerms.getTemplateId()))) %>">
	<aui:input name="groupId" type="hidden" />
</c:if>

<liferay-ui:search-form
	page="/html/portlet/journal/article_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

searchTerms.setVersion(-1);

List<JournalArticle> results = null;
%>

<c:choose>
	<c:when test="<%= PropsValues.JOURNAL_ARTICLES_SEARCH_WITH_INDEX %>">
		<%@ include file="/html/portlet/journal/article_search_results_index.jspf" %>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portlet/journal/article_search_results_database.jspf" %>
	</c:otherwise>
</c:choose>

<div class="separator article-separator"><!-- --></div>

<c:if test="<%= !results.isEmpty() %>">
	<aui:button-row>
		<aui:button cssClass="expire-articles-button" onClick='<%= renderResponse.getNamespace() + "expireArticles();" %>' value="expire" />

		<aui:button cssClass="delete-articles-button" onClick='<%= renderResponse.getNamespace() + "deleteArticles();" %>' value="delete" />
	</aui:button-row>

	<br /><br />
</c:if>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalArticle article = results.get(i);

	ResultRow row = new ResultRow(article, article.getArticleId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/journal/edit_article");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("originalRedirect", currentURL);
	rowURL.setParameter("groupId", String.valueOf(article.getGroupId()));
	rowURL.setParameter("articleId", article.getArticleId());
%>

	<%@ include file="/html/portlet/journal/article_columns.jspf" %>

<%

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />deleteArticles',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-web-content") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />deleteArticleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />expireArticles',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-expire-the-selected-web-content") %>')) {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
				document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
				document.<portlet:namespace />fm.<portlet:namespace />expireArticleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script use="aui-base">
	var buttons = A.all('.delete-articles-button, .expire-articles-button');

	if (buttons.size()) {
		var toggleDisabled = function(disabled) {
			buttons.all(':button').attr('disabled', disabled);

			buttons.toggleClass('aui-button-disabled', disabled);
		};

		var resultsGrid = A.one('.results-grid');

		if (resultsGrid) {
			resultsGrid.delegate(
				'click',
				function(event) {
					var disabled = (resultsGrid.one(':checked') == null);

					toggleDisabled(disabled);
				},
				':checkbox'
			);
		}

		toggleDisabled(true);
	}
</aui:script>