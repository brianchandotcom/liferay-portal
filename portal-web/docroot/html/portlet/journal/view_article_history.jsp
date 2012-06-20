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
String tabs1 = ParamUtil.getString(request, "tabs1", "web-content");

String redirect = ParamUtil.getString(request, "redirect");
String originalRedirect = ParamUtil.getString(request, "originalRedirect");

String orderByCol = ParamUtil.getString(request, "orderByCol");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<div class="portlet-msg-error">
			<%= LanguageUtil.get(pageContext, "the-selected-web-content-no-longer-exists") %>
		</div>
	</c:when>
	<c:otherwise>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/journal/view_article_history");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("originalRedirect", originalRedirect);
		portletURL.setParameter("articleId", article.getArticleId());
		%>

		<liferay-util:include page="/html/portlet/journal/article_header.jsp" />

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="originalRedirect" type="hidden" value="<%= originalRedirect %>" />
			<aui:input name="articleId" type="hidden" value="<%= article.getArticleId() %>" />
			<aui:input name="articleIds" type="hidden" />
			<aui:input name="expireArticleIds" type="hidden" />

			<%
			ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

			List headerNames = searchContainer.getHeaderNames();

			headerNames.add(2, "version");
			headerNames.add(3, "status");
			headerNames.add(StringPool.BLANK);

			Map<String, String> orderableHeaders = searchContainer.getOrderableHeaders();

			orderableHeaders.put("version", "version");

			if (Validator.isNull(orderByCol)) {
				searchContainer.setOrderByCol("version");
			}

			searchContainer.setRowChecker(new RowChecker(renderResponse));

			ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

			searchTerms.setAdvancedSearch(true);
			searchTerms.setArticleId(article.getArticleId());

			List<JournalArticle> results = JournalArticleServiceUtil.getArticlesByArticleId(searchTerms.getGroupId(), searchTerms.getArticleId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

			searchContainer.setResults(results);

			int total = JournalArticleServiceUtil.getArticlesCountByArticleId(searchTerms.getGroupId(), searchTerms.getArticleId());

			searchContainer.setTotal(total);
			%>


			<c:if test="<%= !results.isEmpty() %>">
				<aui:button-row>

					<%
					String taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.EXPIRE + "'});";
					%>

					<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
						<aui:button onClick="<%= taglibOnClick %>" value="expire" />
					</c:if>

					<%
					taglibOnClick = "Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.DELETE_VERSIONS + "'});";
					%>

					<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
						<aui:button onClick="<%= taglibOnClick %>" value="delete" />
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

				row.addText(HtmlUtil.escape(PortalUtil.getUserName(articleVersion.getUserId(), articleVersion.getUserName())), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/journal/article_version_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</aui:form>
	</c:otherwise>
</c:choose>