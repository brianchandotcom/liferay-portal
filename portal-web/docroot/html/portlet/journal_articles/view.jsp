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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String articleId = ParamUtil.getString(request, "articleId");
double version = ParamUtil.getDouble(request, "version");
%>

<c:choose>
	<c:when test="<%= Validator.isNull(articleId) %>">

		<%
		if (Validator.isNull(type)) {
			type = null;
		}

		String status = "approved";

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("struts_action", "/journal_articles/view");

		if (pageUrl.equals("normal")) {
			portletURL.setWindowState(WindowState.NORMAL);
		}
		else {
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		PortletURL articleURL = PortletURLUtil.clone(portletURL, renderResponse);

		ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

		searchContainer.setDelta(pageDelta);
		searchContainer.setDeltaConfigurable(false);
		searchContainer.setEmptyResultsMessage("no-web-content-was-found-that-matched-the-specified-filters");
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setOrderByComparator(orderByComparator);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.clear();

		headerNames.add("title");
		headerNames.add("display-date");
		headerNames.add("author");

		searchContainer.setOrderableHeaders(null);

		ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

		searchTerms.setGroupId(groupId);
		searchTerms.setType(type);

		if (ddmStructure != null) {
			searchTerms.setStructureId(ddmStructure.getStructureKey());
		}

		searchTerms.setDisplayDateLT(new Date());
		searchTerms.setStatus(status);
		searchTerms.setVersion(version);
		searchTerms.setAdvancedSearch(true);

		boolean showNonindexable = false;

		List<JournalArticle> results = null;
		int total = 0;
		%>

		<%@ include file="/html/portlet/journal/article_search_results.jspf" %>

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			JournalArticle article = results.get(i);

			article = article.toEscapedModel();

			ResultRow row = new ResultRow(article, article.getArticleId() + EditArticleAction.VERSION_SEPARATOR + article.getVersion(), i);

			String rowHREF = null;

			if (pageUrl.equals("popUp")) {
				StringBundler sb = new StringBundler(7);

				sb.append(themeDisplay.getPathMain());
				sb.append("/journal_articles/view_article_content?groupId=");
				sb.append(article.getGroupId());
				sb.append("&articleId=");
				sb.append(article.getArticleId());
				sb.append("&version=");
				sb.append(article.getVersion());

				rowHREF = sb.toString();
			}
			else {
				articleURL.setParameter("returnToFullPageURL", currentURL);
				articleURL.setParameter("groupId", String.valueOf(article.getGroupId()));
				articleURL.setParameter("articleId", article.getArticleId());
				articleURL.setParameter("version", String.valueOf(article.getVersion()));

				if (pageUrl.equals("viewInContext")) {
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getId());

					String viewFullContentURLString = articleURL.toString();

					viewFullContentURLString = HttpUtil.setParameter(viewFullContentURLString, "redirect", currentURL);

					rowHREF = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, viewFullContentURLString);

					rowHREF = HttpUtil.setParameter(rowHREF, "redirect", currentURL);

					if (Validator.isNull(rowHREF)) {
						rowHREF = articleURL.toString();
					}
				}
				else {
					rowHREF = articleURL.toString();
				}
			}

			String target = null;

			if (pageUrl.equals("popUp")) {
				target = "_blank";
			}

			TextSearchEntry rowTextEntry = new TextSearchEntry();

			rowTextEntry.setHref(rowHREF);
			rowTextEntry.setName(article.getArticleId());
			rowTextEntry.setTarget(target);

			/*// Article id

			row.addText(rowTextEntry);

			// Version

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(String.valueOf(article.getVersion()));

			row.addText(rowTextEntry);*/

			// Title

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(article.getTitle(locale));

			row.addText(rowTextEntry);

			// Display date

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(dateFormatDateTime.format(article.getDisplayDate()));

			row.addText(rowTextEntry);

			// Author

			rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

			rowTextEntry.setName(PortalUtil.getUserName(article));

			row.addText(rowTextEntry);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:otherwise>

		<%
		String languageId = LanguageUtil.getLanguageId(request);
		int articlePage = ParamUtil.getInteger(renderRequest, "page", 1);
		String xmlRequest = PortletRequestUtil.toXML(renderRequest, renderResponse);

		JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(groupId, articleId, null, null, languageId, themeDisplay, articlePage, xmlRequest);

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getArticle(groupId, articleId, version);

			boolean expired = article.isExpired();

			if (!expired) {
				Date expirationDate = article.getExpirationDate();

				if ((expirationDate != null) && expirationDate.before(new Date())) {
					expired = true;
				}
			}
			%>

			<c:choose>
				<c:when test="<%= (articleDisplay != null) && !expired %>">
					<c:if test='<%= pageUrl.equals("normal") %>'>
						<portlet:renderURL var="backURL">
							<portlet:param name="struts_action" value="/journal_articles/view" />
							<portlet:param name="redirect" value="<%= redirect %>" />
						</portlet:renderURL>

						<liferay-ui:header
							backURL="<%= backURL %>"
							localizeTitle="<%= false %>"
							title="<%= article.getTitle(locale) %>"
						/>
					</c:if>

					<%
					AssetEntryServiceUtil.incrementViewCounter(JournalArticle.class.getName(), articleDisplay.getResourcePrimKey());
					%>

					<div class="journal-content-article">
						<%= RuntimePageUtil.processXML(request, response, articleDisplay.getContent()) %>
					</div>

					<c:if test="<%= articleDisplay.isPaginate() %>">

						<%
						PortletURL portletURL = renderResponse.createRenderURL();

						portletURL.setParameter("articleId", articleId);
						portletURL.setParameter("version", String.valueOf(version));
						%>

						<br />

						<liferay-ui:page-iterator
							cur="<%= articleDisplay.getCurrentPage() %>"
							curParam='<%= "page" %>'
							delta="<%= 1 %>"
							id="articleDisplayPages"
							maxPages="<%= 25 %>"
							total="<%= articleDisplay.getNumberOfPages() %>"
							type="article"
							url="<%= portletURL.toString() %>"
						/>

						<br />
					</c:if>
				</c:when>
				<c:otherwise>
					<div class="alert alert-error">
						<liferay-ui:message key="this-content-has-expired-or-you-do-not-have-the-required-permissions-to-access-it" />
					</div>
				</c:otherwise>
			</c:choose>

		<%
		} catch (NoSuchArticleException nsae) {
		%>

			<div class="alert alert-error">
				<%= LanguageUtil.get(pageContext, "the-selected-web-content-no-longer-exists") %>
			</div>

		<%
		}
		%>

	</c:otherwise>
</c:choose>