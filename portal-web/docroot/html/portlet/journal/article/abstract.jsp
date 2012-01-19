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
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

boolean smallImage = BeanParamUtil.getBoolean(article, request, "smallImage");
String smallImageURL = BeanParamUtil.getString(article, request, "smallImageURL");

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");
String toLanguageId = (String)request.getAttribute("edit_article.jsp-toLanguageId");
%>

<liferay-ui:error-marker key="errorSection" value="abstract" />

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="abstract" /></h3>

<liferay-ui:error exception="<%= ArticleSmallImageNameException.class %>">

	<%
	String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS, StringPool.COMMA);
	%>

	<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
</liferay-ui:error>

<liferay-ui:error exception="<%= ArticleSmallImageSizeException.class %>">

	<%
	long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE) / 1024;
	%>

	<liferay-ui:message arguments="<%= imageMaxSize %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" />
</liferay-ui:error>

<aui:fieldset>
	<aui:input label="summary" languageId="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" name="description" />

	<c:if test="<%= Validator.isNull(toLanguageId) %>">
		<aui:input inlineLabel="right" label="use-small-image" name="smallImage" />

		<aui:input label="small-image-url" name="smallImageURL" />

		<span style="font-size: xx-small;">-- <%= LanguageUtil.get(pageContext, "or").toUpperCase() %> --</span>

		<aui:input cssClass="lfr-input-text-container" label="small-image" name="smallFile" type="file" />
	</c:if>
</aui:fieldset>