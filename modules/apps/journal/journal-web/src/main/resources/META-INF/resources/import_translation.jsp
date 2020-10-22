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
String redirect = ParamUtil.getString(request, "redirect");

JournalArticle article = journalDisplayContext.getArticle();

JournalEditArticleDisplayContext journalEditArticleDisplayContext = new JournalEditArticleDisplayContext(request, liferayPortletResponse, article);

String articleResourcePrimKey = String.valueOf(article.getResourcePrimKey());
String articleTitle = article.getTitle();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(LanguageUtil.get(resourceBundle, "import-translation"));
%>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/translation-web/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<portlet:actionURL name="/journal/import_translation" var="importTranslationURL">
	<portlet:param name="articleResourcePrimKey" value="<%= articleResourcePrimKey %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
	<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
	<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
</portlet:actionURL>

<div class="translation">
	<aui:form action="<%= importTranslationURL %>" cssClass="translation-import" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<nav class="component-tbar subnav-tbar-light tbar">
			<clay:container-fluid>
				<ul class="tbar-nav">
					<li class="tbar-item tbar-item-expand">
						<div class="pl-2 tbar-section text-left">
							<h2 class="h4 text-truncate-inline" title="<%= HtmlUtil.escapeAttribute(articleTitle) %>">
								<span class="text-truncate"><%= HtmlUtil.escape(articleTitle) %></span>
							</h2>
						</div>
					</li>
					<li class="tbar-item">
						<div class="metadata-type-button-row tbar-section text-right">
							<aui:button cssClass="btn-sm mr-3" href="<%= redirect %>" type="cancel" />

							<aui:button cssClass="btn-sm mr-3" id="saveDraftBtn" primary="<%= false %>" type="submit" value="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>" />

							<aui:button cssClass="btn-sm mr-3" disabled="<%= journalEditArticleDisplayContext.isPending() %>" id="submitBtnId" primary="<%= true %>" type="submit" value="<%= journalEditArticleDisplayContext.getPublishButtonLabel() %>" />
						</div>
					</li>
				</ul>
			</clay:container-fluid>
		</nav>

		<clay:container-fluid
			cssClass="container-view"
		>
			<clay:sheet
				cssClass="translation-import-body-form"
			>
				<liferay-ui:error exception="<%= XLIFFFileException.MustBeSupportedLanguage.class %>" message="the-xliff-file-has-an-unavailable-language-translation" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustBeValid.class %>" message="the-file-is-an-invalid-xliff-file" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustBeWellFormed.class %>" message="the-xliff-file-does-not-have-all-needed-fields" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustHaveCorrectEncoding.class %>" message="the-translation-file-has-an-incorrect-encoding.the-supported-encoding-format-is-utf-8" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustHaveValidId.class %>" message="the-translation-file-does-not-correspond-to-this-web-content" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustHaveValidParameter.class %>" message="the-xliff-file-has-invalid-parameters" />
				<liferay-ui:error exception="<%= XLIFFFileException.MustNotHaveMoreThanOne.class %>" message="the-xliff-file-is-invalid" />

				<div>
					<react:component
						module="js/ImportTranslation.es"
						props='<%=
							HashMapBuilder.<String, Object>put(
								"saveDraftBtnId", liferayPortletResponse.getNamespace() + "saveDraftBtn"
							).put(
								"submitBtnId", liferayPortletResponse.getNamespace() + "submitBtnId"
							).put(
								"worflowPending", journalEditArticleDisplayContext.isPending()
							).build()
						%>'
					/>
				</div>
			</clay:sheet>
		</clay:container-fluid>
	</aui:form>
</div>

<script>
	var saveDraftBtn = document.getElementById('<portlet:namespace />saveDraftBtn');

	saveDraftBtn.addEventListener('click', function () {
		var workflowActionInput = document.getElementById(
			'<portlet:namespace />workflowAction'
		);

		workflowActionInput.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';
	});
</script>