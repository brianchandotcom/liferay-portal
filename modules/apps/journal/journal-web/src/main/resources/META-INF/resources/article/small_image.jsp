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
JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

String smallImageSource = journalEditArticleDisplayContext.getSmallImageSource();
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
	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(journalFileUploadsConfiguration.smallImageMaxSize(), locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:select ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageSource" value="<%= smallImageSource %>" wrapperCssClass="mb-3">
	<aui:option label="no-image" value="none" />
	<aui:option label="from-url" value="url" />
	<aui:option label="from-your-computer" value="file" />
</aui:select>

<div class="<%= Objects.equals(smallImageSource, "url") ? "" : "hide" %>" id="<portlet:namespace/>smallImageURLContainer">
	<aui:input ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageURL" title="small-image-url" wrapperCssClass="mb-3" />
</div>

<div class="<%= Objects.equals(smallImageSource, "file") ? "" : "hide" %>" id="<portlet:namespace/>smallFileContainer">
	<aui:input ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallFile" type="file" wrapperCssClass="mb-3" />
</div>

<aui:script>
	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />smallImageSource',
		'url',
		'<portlet:namespace />smallImageURLContainer'
	);
	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />smallImageSource',
		'file',
		'<portlet:namespace />smallFileContainer'
	);
</aui:script>