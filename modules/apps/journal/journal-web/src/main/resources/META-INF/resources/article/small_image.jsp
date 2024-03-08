<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = (JournalEditArticleDisplayContext)request.getAttribute(JournalEditArticleDisplayContext.class.getName());
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="featured-image"
/>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<%
JournalFileUploadsConfiguration journalFileUploadsConfiguration = (JournalFileUploadsConfiguration)request.getAttribute(JournalFileUploadsConfiguration.class.getName());
%>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">
	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= HtmlUtil.escape(StringUtil.merge(journalFileUploadsConfiguration.imageExtensions(), ", ")) %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>">
	<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(journalFileUploadsConfiguration.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<div>
	<react:component
		module="{SmallImage} from journal-web"
		props="<%= journalEditArticleDisplayContext.getProps() %>"
	/>
</div>