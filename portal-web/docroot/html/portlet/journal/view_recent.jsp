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

<%= LanguageUtil.format(pageContext, "this-page-displays-the-last-x-web-content,-structures,-and-templates-that-you-accessed", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>

<br /><br />

<table class="lfr-table" width="100%">
<tr>
	<td class="lfr-top" width="33%">
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
			<tr class="portlet-section-header results-header" style="font-size: x-small; font-weight: bold;">
				<td colspan="2">
					<%= LanguageUtil.format(pageContext, "last-x-web-content", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
				</td>
			</tr>

			<%
			Stack recentArticles = JournalUtil.getRecentArticles(renderRequest);

			int recentArticlesSize = recentArticles.size();

			for (int i = recentArticlesSize - 1; i >= 0; i--) {
				JournalArticle article = (JournalArticle)recentArticles.get(i);

				article = article.toEscapedModel();

				String className = "portlet-section-body results-row";
				String classHoverName = "portlet-section-body-hover results-row hover";

				if (MathUtil.isEven(i)) {
					className = "portlet-section-alternate results-row alt";
					classHoverName = "portlet-section-alternate-hover results-row alt hover";
				}
			%>

				<portlet:renderURL var="editArticleURL">
					<portlet:param name="struts_action" value="/journal/edit_article" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
					<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
					<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
				</portlet:renderURL>

				<tr class="<%= className %>" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';" style="font-size: x-small;">
					<td>
						<aui:a href="<%= editArticleURL %>"><%= article.getArticleId() %></aui:a>
					</td>
					<td>
						<aui:a href="<%= editArticleURL %>"><%= article.getTitle(locale) %></aui:a>
					</td>
				</tr>

			<%
			}
			%>

		</table>
	</td>
	<td class="lfr-top" width="33%">
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
			<tr class="portlet-section-header results-header" style="font-size: x-small; font-weight: bold;">
				<td colspan="2">
					<%= LanguageUtil.format(pageContext, "last-x-structures", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
				</td>
			</tr>

			<%
			Stack recentStructures = JournalUtil.getRecentStructures(renderRequest);

			int recentStructuresSize = recentStructures.size();

			for (int i = recentStructuresSize - 1; i >= 0; i--) {
				DDMStructure ddmStructure = (DDMStructure)recentStructures.get(i);

				ddmStructure = ddmStructure.toEscapedModel();

				String className = "portlet-section-body results-row";
				String classHoverName = "portlet-section-body-hover results-row hover";

				if (MathUtil.isEven(i)) {
					className = "portlet-section-alternate results-row alt";
					classHoverName = "portlet-section-alternate-hover results-row alt hover";
				}
			%>

				<tr class="<%= className %>" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';" style="font-size: x-small;">
					<td>
						<%= ddmStructure.getName(locale) %>
					</td>
				</tr>

			<%
			}
			%>

		</table>
	</td>
	<td class="lfr-top" width="33%">
		<table border="0" cellpadding="4" cellspacing="0" width="100%">
			<tr class="portlet-section-header results-header" style="font-size: x-small; font-weight: bold;">
				<td colspan="2">
					<%= LanguageUtil.format(pageContext, "last-x-templates", String.valueOf(JournalUtil.MAX_STACK_SIZE), false) %>
				</td>
			</tr>

			<%
			Stack recentTemplates = JournalUtil.getRecentTemplates(renderRequest);

			int recentTemplatesSize = recentTemplates.size();

			for (int i = recentTemplatesSize - 1; i >= 0; i--) {
				DDMTemplate ddmTemplate = (DDMTemplate)recentTemplates.get(i);

				ddmTemplate = ddmTemplate.toEscapedModel();

				String className = "portlet-section-body results-row";
				String classHoverName = "portlet-section-body-hover results-row hover";

				if (MathUtil.isEven(recentTemplatesSize - i - 1)) {
					className = "portlet-section-alternate results-row alt";
					classHoverName = "portlet-section-alternate-hover results-row alt hover";
				}
			%>

				<tr class="<%= className %>" onMouseEnter="this.className = '<%= classHoverName %>';" onMouseLeave="this.className = '<%= className %>';" style="font-size: x-small;">
					<td>
						<%= ddmTemplate.getName(locale) %>
					</td>
				</tr>

			<%
			}
			%>

		</table>
	</td>
</tr>
</table>