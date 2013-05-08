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

<%= LanguageUtil.format(pageContext, "this-page-displays-the-last-x-web-content,-structures,-and-templates-that-you-accessed", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>

<br /><br />

<table class="lfr-table" width="100%">
<tr>
	<td class="lfr-top" width="33%">
		<table class="table table-bordered table-hover table-striped">
			<thead class="table-columns">
			<tr>
				<td class="table-cell" colspan="2">
					<%= LanguageUtil.format(pageContext, "last-x-web-content", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
				</td>
			</tr>
			</thead>

			<tbody class="table-data">

				<%
				Stack recentArticles = JournalUtil.getRecentArticles(renderRequest);

				int recentArticlesSize = recentArticles.size();

				for (int i = recentArticlesSize - 1; i >= 0; i--) {
					JournalArticle article = (JournalArticle)recentArticles.get(i);

					article = article.toEscapedModel();
				%>

					<portlet:renderURL var="editArticleURL">
						<portlet:param name="struts_action" value="/journal/edit_article" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
						<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
						<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
					</portlet:renderURL>

				<tr>
					<td class="table-cell">
						<aui:a href="<%= editArticleURL %>"><%= article.getArticleId() %></aui:a>
					</td>
					<td class="table-cell">
						<aui:a href="<%= editArticleURL %>"><%= article.getTitle(locale) %></aui:a>
					</td>
				</tr>

			<%
			}
			%>

			</tbody>
		</table>
	</td>
	<td width="33%">
		<table class="table table-bordered table-hover table-striped">
			<thead class="table-columns">
				<tr>
					<td class="table-cell" colspan="2">
						<%= LanguageUtil.format(pageContext, "last-x-structures", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
					</td>
				</tr>
			</thead>

			<tbody class="table-data">

			<%
			Stack recentDDMStructures = JournalUtil.getRecentDDMStructures(renderRequest);

			int recentDDMStructuresSize = recentDDMStructures.size();

			for (int i = recentDDMStructuresSize - 1; i >= 0; i--) {
				DDMStructure ddmStructure = (DDMStructure)recentDDMStructures.get(i);

				ddmStructure = ddmStructure.toEscapedModel();
			%>

				<tr>
					<td class="table-cell">
						<%= ddmStructure.getName(locale) %>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
		</table>
	</td>
	<td width="33%">
		<table class="table table-bordered table-hover table-striped">
			<thead class="table-columns">
				<tr>
					<td class="table-cell" colspan="2">
						<%= LanguageUtil.format(pageContext, "last-x-templates", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
					</td>
				</tr>
			</thead>

			<tbody class="table-data">

			<%
			Stack recentDDMTemplates = JournalUtil.getRecentDDMTemplates(renderRequest);

			int recentDDMTemplatesSize = recentDDMTemplates.size();

			for (int i = recentDDMTemplatesSize - 1; i >= 0; i--) {
				DDMTemplate ddmTemplate = (DDMTemplate)recentDDMTemplates.get(i);

				ddmTemplate = ddmTemplate.toEscapedModel();
			%>

				<tr>
					<td class="table-cell">
						<%= ddmTemplate.getName(locale) %>
					</td>
				</tr>

			<%
			}
			%>

		</tbody>
		</table>
	</td>
</tr>
</table>