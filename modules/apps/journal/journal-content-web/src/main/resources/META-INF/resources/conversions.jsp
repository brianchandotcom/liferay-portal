<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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