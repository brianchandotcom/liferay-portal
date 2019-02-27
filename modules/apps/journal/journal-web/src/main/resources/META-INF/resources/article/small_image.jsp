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
	value="small_image"
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

<div id="<portlet:namespace />smallImageContainer">
	<aui:select cssClass="lfr-journal-small-image-header lfr-journal-small-image-type" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageSource" value="<%= smallImageSource %>">
		<aui:option label="no-image" value="none" />
		<aui:option label="from-url" value="url" />
		<aui:option label="from-your-computer" value="file" />
	</aui:select>

	<div class="lfr-journal-small-image-content-url <%= Objects.equals(smallImageSource, "url") ? "" : "hide" %>">
		<liferay-frontend:fieldset>
			<aui:input cssClass="lfr-journal-small-image-value" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallImageURL" title="small-image-url" />
		</liferay-frontend:fieldset>
	</div>

	<div class="lfr-journal-small-image-content-file <%= Objects.equals(smallImageSource, "file") ? "" : "hide" %>">
		<liferay-frontend:fieldset>
			<aui:input cssClass="lfr-journal-small-image-value" ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>" label="" name="smallFile" type="file" />
		</liferay-frontend:fieldset>
	</div>
</div>

<aui:script use="aui-base">
	var container = A.one('#<portlet:namespace />smallImageContainer');

	var selectSmallImageType = function(index) {
		if (index === 1) {
			A.one('.lfr-journal-small-image-content-url').show();
			A.one('.lfr-journal-small-image-content-file').hide();
		}
		else if (index === 2) {
			A.one('.lfr-journal-small-image-content-url').hide();
			A.one('.lfr-journal-small-image-content-file').show();
		}
		else {
			A.one('.lfr-journal-small-image-content-url').hide();
			A.one('.lfr-journal-small-image-content-file').hide();
		}
	};

	container.delegate(
		'change',
		function(event) {
			var index = event.currentTarget.getDOMNode().selectedIndex;

			selectSmallImageType(index);
		},
		'.lfr-journal-small-image-type'
	);
</aui:script>