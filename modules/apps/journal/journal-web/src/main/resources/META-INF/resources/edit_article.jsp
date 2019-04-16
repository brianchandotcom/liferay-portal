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
%>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
	<portlet:param name="ddmStructureKey" value="<%= journalEditArticleDisplayContext.getDDMStructureKey() %>" />
</portlet:actionURL>

<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
</portlet:renderURL>

<aui:form action="<%= editArticleActionURL %>" cssClass="edit-article-form" enctype="multipart/form-data" method="post" name="fm1" onSubmit="event.preventDefault();">
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
	<aui:input name="hideDefaultSuccessMessage" type="hidden" value="<%= journalEditArticleDisplayContext.getClassNameId() == PortalUtil.getClassNameId(DDMStructure.class) %>" />
	<aui:input name="redirect" type="hidden" value="<%= journalEditArticleDisplayContext.getRedirect() %>" />
	<aui:input name="portletResource" type="hidden" value="<%= journalEditArticleDisplayContext.getPortletResource() %>" />
	<aui:input name="refererPlid" type="hidden" value="<%= journalEditArticleDisplayContext.getRefererPlid() %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= journalEditArticleDisplayContext.getReferringPortletResource() %>" />
	<aui:input name="groupId" type="hidden" value="<%= journalEditArticleDisplayContext.getGroupId() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layout.isPrivateLayout() %>" />
	<aui:input name="folderId" type="hidden" value="<%= journalEditArticleDisplayContext.getFolderId() %>" />
	<aui:input name="classNameId" type="hidden" value="<%= journalEditArticleDisplayContext.getClassNameId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= journalEditArticleDisplayContext.getClassPK() %>" />
	<aui:input name="articleId" type="hidden" value="<%= journalEditArticleDisplayContext.getArticleId() %>" />
	<aui:input name="articleIds" type="hidden" value="<%= journalEditArticleDisplayContext.getArticleId() + JournalPortlet.VERSION_SEPARATOR + journalEditArticleDisplayContext.getVersion() %>" />
	<aui:input name="version" type="hidden" value="<%= ((article == null) || article.isNew()) ? journalEditArticleDisplayContext.getVersion() : article.getVersion() %>" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="changeDDMStructure" type="hidden" />
	<aui:input name="ddmStructureId" type="hidden" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">

					<%
					DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
					%>

					<aui:input cssClass="form-control-inline" label="" localized="<%= true %>" name="titleMapAsXML" placeholder='<%= LanguageUtil.format(request, "untitled-x", HtmlUtil.escape(ddmStructure.getName(locale))) %>' type="text" wrapperCssClass="article-content-title mb-0" />
				</li>

				<c:if test="<%= journalWebConfiguration.changeableDefaultLanguage() %>">
					<li class="tbar-item">
						<div class="tbar-section">
							<soy:component-renderer
								context="<%= journalEditArticleDisplayContext.getChangeDefaultLanguageSoyContext() %>"
								module="js/ChangeDefaultLanguage.es"
								templateNamespace="com.liferay.journal.web.ChangeDefaultLanguage.render"
							/>
						</div>
					</li>
				</c:if>

				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<a class="btn btn-outline-borderless btn-outline-secondary btn-sm mr-3" href="<%= journalEditArticleDisplayContext.getRedirect() %>">
							<liferay-ui:message key="cancel" />
						</a>

						<c:if test="<%= journalEditArticleDisplayContext.hasSavePermission() %>">
							<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
								<aui:button cssClass="btn-sm mr-3" data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "addArticle" : "updateArticle" %>' name="saveButton" primary="<%= false %>" type="submit" value="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>" />
							</c:if>

							<aui:button cssClass="btn-sm mr-3" data-actionname="<%= Constants.PUBLISH %>" disabled="<%= journalEditArticleDisplayContext.isPending() %>" name="publishButton" type="submit" value="<%= journalEditArticleDisplayContext.getPublishButtonLabel() %>" />
						</c:if>

						<clay:button
							icon="cog"
							id='<%= renderResponse.getNamespace() + "contextualSidebarButton" %>'
							monospaced="<%= true %>"
							size="sm"
							style="borderless"
						/>
					</div>
				</li>
			</ul>
		</div>
	</nav>

	<div class="contextual-sidebar edit-article-sidebar sidebar-light sidebar-sm" id="<portlet:namespace />contextualSidebarContainer">
		<div class="sidebar-body">

			<%
			String tabs1Names = "properties,usages";

			if ((article == null) || (journalEditArticleDisplayContext.getClassNameId() != JournalArticleConstants.CLASSNAME_ID_DEFAULT)) {
				tabs1Names = "properties";
			}
			%>

			<liferay-ui:tabs
				names="<%= tabs1Names %>"
				param="tabs1"
				refresh="<%= false %>"
			>
				<liferay-ui:section>
					<liferay-frontend:form-navigator
						fieldSetCssClass="panel-group-flush"
						formModelBean="<%= article %>"
						id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL %>"
						showButtons="<%= false %>"
					/>
				</liferay-ui:section>

				<c:if test="<%= (article != null) && (journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
					<liferay-ui:section>
						<liferay-asset:asset-view-usages
							className="<%= JournalArticle.class.getName() %>"
							classPK="<%= article.getResourcePrimKey() %>"
						/>
					</liferay-ui:section>
				</c:if>
			</liferay-ui:tabs>
		</div>
	</div>

	<div class="contextual-sidebar-content">
		<div class="container-fluid container-fluid-max-xl container-view">
			<div class="sheet sheet-lg">
				<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />
				<liferay-ui:error exception="<%= ArticleFriendlyURLException.class %>" message="you-must-define-a-friendly-url-for-default-language" />
				<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

				<liferay-ui:error exception="<%= ExportImportContentValidationException.class %>">

					<%
					ExportImportContentValidationException eicve = (ExportImportContentValidationException)errorException;
					%>

					<c:choose>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.ARTICLE_NOT_FOUND %>">
							<liferay-ui:message key="unable-to-validate-referenced-journal-article" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.FILE_ENTRY_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= new String[] {MapUtil.toString(eicve.getDlReferenceParameters())} %>" key="unable-to-validate-referenced-file-entry-because-it-cannot-be-found-with-the-following-parameters-x" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_GROUP_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= new String[] {eicve.getLayoutURL(), eicve.getGroupFriendlyURL()} %>" key="unable-to-validate-referenced-page-with-url-x-because-the-page-group-with-url-x-cannot-be-found" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= new String[] {MapUtil.toString(eicve.getLayoutReferenceParameters())} %>" key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-the-following-parameters-x" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_WITH_URL_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= new String[] {eicve.getLayoutURL()} %>" key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-url-x" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="an-unexpected-error-occurred" />
						</c:otherwise>
					</c:choose>
				</liferay-ui:error>

				<liferay-ui:error exception="<%= FileSizeException.class %>">
					<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(DLValidatorUtil.getMaxAllowableSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
					<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-util:include page="/article/content.jsp" servletContext="<%= application %>" />
			</div>
		</div>
	</div>
</aui:form>

<aui:script use="liferay-portlet-journal">
	new Liferay.Portlet.Journal(
		{
			article: {
				editUrl: '<%= journalEditArticleDisplayContext.getEditArticleURL() %>',
				id: '<%= (article != null) ? HtmlUtil.escape(journalEditArticleDisplayContext.getArticleId()) : StringPool.BLANK %>',
				title: '<%= (article != null) ? HtmlUtil.escapeJS(article.getTitle(locale)) : StringPool.BLANK %>'
			},
			namespace: '<portlet:namespace />',
			'strings.saveAsDraftBeforePreview': '<liferay-ui:message key="in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft" />'
		}
	);

	var contextualSidebarContainer = document.getElementById('<portlet:namespace />contextualSidebarContainer');
	var contextualSidebarButton = document.getElementById('<portlet:namespace />contextualSidebarButton');

	if (contextualSidebarContainer && (window.innerWidth > Liferay.BREAKPOINTS.PHONE)) {
		contextualSidebarContainer.classList.add('contextual-sidebar-visible');
	}

	if (contextualSidebarButton) {
		contextualSidebarButton.addEventListener(
			'click',
			function(event) {
				if (contextualSidebarContainer.classList.contains('contextual-sidebar-visible')) {
					contextualSidebarContainer.classList.remove('contextual-sidebar-visible');

				}
				else {
					contextualSidebarContainer.classList.add('contextual-sidebar-visible');
				}
			}
		);
	}
</aui:script>