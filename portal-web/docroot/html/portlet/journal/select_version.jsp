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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

double sourceVersion = ParamUtil.getDouble(request, "sourceVersion");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_version");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
portletURL.setParameter("articleId", article.getArticleId());
portletURL.setParameter("sourceVersion", String.valueOf(sourceVersion));
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectVersionFm">
	<liferay-ui:search-container
		id="journalArticleVersionSearchContainer"
		iteratorURL="<%= portletURL %>"
		total="<%= JournalArticleLocalServiceUtil.getArticlesCount(article.getGroupId(), article.getArticleId()) %>"
	>
		<liferay-ui:search-container-results
			results="<%= JournalArticleLocalServiceUtil.getArticles(article.getGroupId(), article.getArticleId(), searchContainer.getStart(), searchContainer.getEnd(), new ArticleVersionComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.journal.model.JournalArticle"
			modelVar="curArticle"
		>
			<liferay-ui:search-container-column-text
				name="version"
				value="<%= String.valueOf(curArticle.getVersion()) %>"
			/>

			<liferay-ui:search-container-column-date
				name="date"
				value="<%= curArticle.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-text
				name=""
			>
				<c:if test="<%= sourceVersion != curArticle.getVersion() %>">

					<%
					double curSourceVersion = sourceVersion;
					double curTargetVersion = curArticle.getVersion();

					if (curTargetVersion < curSourceVersion) {
						double tempVersion = curTargetVersion;

						curTargetVersion = curSourceVersion;
						curSourceVersion = tempVersion;
					}

					PortletURL compareVersionsURL = renderResponse.createRenderURL();

					compareVersionsURL.setParameter("struts_action", "/journal/compare_versions");
					compareVersionsURL.setParameter("groupId", String.valueOf(article.getGroupId()));
					compareVersionsURL.setParameter("articleId", article.getArticleId());
					compareVersionsURL.setParameter("sourceVersion", String.valueOf(curSourceVersion));
					compareVersionsURL.setParameter("targetVersion", String.valueOf(curTargetVersion));
					%>

					<aui:button cssClass="selector-button" href="<%= compareVersionsURL.toString() %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>