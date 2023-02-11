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

<%@ include file="/init.jsp" %>

<%
JournalArticleDisplay articleDisplay = (JournalArticleDisplay)request.getAttribute(WebKeys.JOURNAL_ARTICLE_DISPLAY);

String extension = ParamUtil.getString(request, "extension");
String viewMode = ParamUtil.getString(request, "viewMode");
%>

<c:if test="<%= !viewMode.equals(Constants.PRINT) %>">
	<clay:content-col
		cssClass="export-action p-1 user-tool-asset-addon-entry"
	>
		<portlet:resourceURL id="exportArticle" var="exportArticleURL">
			<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
			<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
			<portlet:param name="targetExtension" value="<%= extension %>" />
		</portlet:resourceURL>

		<clay:link
			aria-label='<%= LanguageUtil.format(request, "download-x-as-x", new Object[] {HtmlUtil.escape(articleDisplay.getTitle()), StringUtil.toUpperCase(HtmlUtil.escape(extension))}) %>'
			borderless="<%= true %>"
			displayType="secondary"
			href="<%= exportArticleURL.toString() %>"
			label="<%= StringUtil.toUpperCase(HtmlUtil.escape(extension)) %>"
			monospaced="<%= true %>"
			small="<%= true %>"
			type="button"
		/>
	</clay:content-col>
</c:if>