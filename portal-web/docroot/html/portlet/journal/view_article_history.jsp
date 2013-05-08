<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "web-content");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

String orderByCol = ParamUtil.getString(request, "orderByCol");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<div class="alert alert-error">
			<%= LanguageUtil.get(pageContext, "the-selected-web-content-no-longer-exists") %>
		</div>
	</c:when>
	<c:otherwise>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/journal/view_article_history");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("referringPortletResource", referringPortletResource);
		portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		portletURL.setParameter("articleId", article.getArticleId());
		%>

		<liferay-util:include page="/html/portlet/journal/article_header.jsp" />

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
			<aui:input name="groupId" type="hidden" />
			<aui:input name="articleId" type="hidden" value="<%= article.getArticleId() %>" />
			<aui:input name="articleIds" type="hidden" />
			<aui:input name="expireArticleIds" type="hidden" />

			<%
			ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

			List headerNames = searchContainer.getHeaderNames();

			headerNames.add(2, "version");

			Map<String, String> orderableHeaders = searchContainer.getOrderableHeaders();

			orderableHeaders.put("version", "version");

			if (Validator.isNull(orderByCol)) {
				searchContainer.setOrderByCol("version");
			}

			searchContainer.setRowChecker(new RowChecker(renderResponse));

			ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

			searchTerms.setAdvancedSearch(true);
			searchTerms.setArticleId(article.getArticleId());

			int total = JournalArticleServiceUtil.getArticlesCountByArticleId(searchTerms.getGroupId(), searchTerms.getArticleId());

			searchContainer.setTotal(total);

			List<JournalArticle> results = JournalArticleServiceUtil.getArticlesByArticleId(searchTerms.getGroupId(), searchTerms.getArticleId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);
			%>

			<c:if test="<%= !results.isEmpty() %>">
				<aui:button-row>
					<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "expireArticles();" %>' value="expire" />
					</c:if>

					<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
						<aui:button onClick='<%= renderResponse.getNamespace() + "deleteArticles();" %>' value="delete" />
					</c:if>
				</aui:button-row>
			</c:if>

			<%
			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				JournalArticle articleVersion = results.get(i);

				articleVersion = articleVersion.toEscapedModel();

				ResultRow row = new ResultRow(articleVersion, articleVersion.getArticleId() + EditArticleAction.VERSION_SEPARATOR + articleVersion.getVersion(), i);

				PortletURL rowURL = null;

				// Article id

				row.addText(articleVersion.getArticleId(), rowURL);

				// Title

				row.addText(articleVersion.getTitle(locale), rowURL);

				// Version

				row.addText(String.valueOf(articleVersion.getVersion()), rowURL);

				// Status

				String status = WorkflowConstants.toLabel(articleVersion.getStatus());

				row.addText(LanguageUtil.get(pageContext, status), rowURL);

				// Modified date

				row.addText(dateFormatDateTime.format(articleVersion.getModifiedDate()), rowURL);

				// Display date

				row.addText(dateFormatDateTime.format(articleVersion.getDisplayDate()), rowURL);

				// Author

				row.addText(PortalUtil.getUserName(articleVersion), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/article_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</aui:form>

		<aui:script>
			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
				Liferay.provide(
					window,
					'<portlet:namespace />deleteArticles',
					function() {
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-version") %>')) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
							document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
							document.<portlet:namespace />fm.<portlet:namespace />articleId.value = "";
							document.<portlet:namespace />fm.<portlet:namespace />articleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
							submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
						}
					},
					['liferay-util-list-fields']
				);
			</c:if>

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
				Liferay.provide(
					window,
					'<portlet:namespace />expireArticles',
					function() {
						if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-expire-the-selected-version") %>')) {
							document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
							document.<portlet:namespace />fm.<portlet:namespace />groupId.value = "<%= scopeGroupId %>";
							document.<portlet:namespace />fm.<portlet:namespace />articleId.value = "";
							document.<portlet:namespace />fm.<portlet:namespace />expireArticleIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
							submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>");
						}
					},
					['liferay-util-list-fields']
				);
			</c:if>
		</aui:script>
	</c:otherwise>
</c:choose>