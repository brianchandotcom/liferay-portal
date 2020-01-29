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
	<aui:input name="folderId" type="hidden" value="<%= journalEditArticleDisplayContext.getFolderId() %>" />
	<aui:input name="classNameId" type="hidden" value="<%= journalEditArticleDisplayContext.getClassNameId() %>" />
	<aui:input name="classPK" type="hidden" value="<%= journalEditArticleDisplayContext.getClassPK() %>" />
	<aui:input name="articleId" type="hidden" value="<%= journalEditArticleDisplayContext.getArticleId() %>" />
	<aui:input name="version" type="hidden" value="<%= ((article == null) || article.isNew()) ? journalEditArticleDisplayContext.getVersion() : article.getVersion() %>" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="ddmStructureId" type="hidden" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="languageId" type="hidden" value="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">
		<div class="container-fluid container-fluid-max-xl">
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">

					<%
					DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
					%>

					<aui:input autoFocus="<%= (article == null) || article.isNew() %>" cssClass="form-control-inline" defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>" label="" localized="<%= true %>" name="titleMapAsXML" placeholder='<%= LanguageUtil.format(request, "untitled-x", HtmlUtil.escape(ddmStructure.getName(locale))) %>' selectedLanguageId="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>" type="text" wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="journal-article-button-row tbar-section text-right">
						<aui:button cssClass="btn-outline-borderless btn-outline-secondary btn-sm mr-3" href="<%= journalEditArticleDisplayContext.getRedirect() %>" type="cancel" />

						<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() > JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
							<portlet:actionURL name="/journal/reset_values_ddm_structure" var="resetValuesDDMStructureURL">
								<portlet:param name="mvcPath" value="/edit_ddm_structure.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(journalEditArticleDisplayContext.getGroupId()) %>" />
								<portlet:param name="articleId" value="<%= journalEditArticleDisplayContext.getArticleId() %>" />
								<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
							</portlet:actionURL>

							<aui:button cssClass="btn-secondary btn-sm mr-3" data-url="<%= resetValuesDDMStructureURL %>" name="resetValuesButton" value="reset-values" />
						</c:if>

						<c:if test="<%= journalEditArticleDisplayContext.hasSavePermission() %>">
							<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
								<aui:button cssClass="btn-sm mr-3" data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "/journal/add_article" : "/journal/update_article" %>' name="saveButton" primary="<%= false %>" type="submit" value="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>" />
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
						<liferay-layout:layout-classed-model-usages-view
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
				<aui:model-context bean="<%= article %>" defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>" model="<%= JournalArticle.class %>" />

				<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
				<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />
				<liferay-ui:error exception="<%= ArticleFriendlyURLException.class %>" message="you-must-define-a-friendly-url-for-default-language" />
				<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />
				<liferay-ui:error exception="<%= ArticleTitleException.class %>" message="please-enter-a-valid-title" />

				<liferay-ui:error exception="<%= ArticleTitleException.MustNotExceedMaximumLength.class %>">

					<%
					int titleMaxLength = ModelHintsUtil.getMaxLength(JournalArticleLocalization.class.getName(), "title");
					%>

					<liferay-ui:message arguments="<%= String.valueOf(titleMaxLength) %>" key="please-enter-a-title-with-fewer-than-x-characters" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing-please-copy-your-changes-and-try-again" />
				<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
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
							<liferay-ui:message arguments="<%= new String[] {MapUtil.toString(eicve.getDlReferenceParameters()), eicve.getDlReference()} %>" key="unable-to-validate-referenced-document-because-it-cannot-be-found-with-the-following-parameters-x-when-analyzing-link-x" />
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

				<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-structure-you-selected-is-not-valid-for-this-folder" />

				<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
					<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= LocaleException.class %>">

					<%
					LocaleException le = (LocaleException)errorException;
					%>

					<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
						<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLocales(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLocales(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
					</c:if>
				</liferay-ui:error>

				<liferay-ui:error exception="<%= NoSuchFileEntryException.class %>" message="the-content-references-a-missing-file-entry" />
				<liferay-ui:error exception="<%= NoSuchImageException.class %>" message="please-select-an-existing-small-image" />

				<liferay-ui:error exception="<%= NoSuchLayoutException.class %>">

					<%
					NoSuchLayoutException nsle = (NoSuchLayoutException)errorException;

					String message = nsle.getMessage();
					%>

					<c:choose>
						<c:when test="<%= Objects.equals(message, JournalArticleConstants.DISPLAY_PAGE) %>">
							<liferay-ui:message key="please-select-an-existing-display-page-template" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="the-content-references-a-missing-page" />
						</c:otherwise>
					</c:choose>
				</liferay-ui:error>

				<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="please-select-an-existing-structure" />
				<liferay-ui:error exception="<%= NoSuchTemplateException.class %>" message="please-select-an-existing-template" />
				<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

				<%
				JournalItemSelectorHelper journalItemSelectorHelper = new JournalItemSelectorHelper(article, journalDisplayContext.getFolder(), renderRequest, renderResponse);

				long classNameId = ParamUtil.getLong(request, "classNameId");
				%>

				<div class="article-content-content">
					<c:choose>
						<c:when test="<%= journalDisplayContext.useDataEngineEditor() %>">
							<liferay-data-engine:data-layout-renderer
								containerId="reportId"
								dataDefinitionId="<%= ddmStructure.getStructureId() %>"
								dataRecordValues="<%= journalEditArticleDisplayContext.getValues(ddmStructure) %>"
								namespace="<%= renderResponse.getNamespace() %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ddm:html
								checkRequired="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>"
								classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
								classPK="<%= ddmStructure.getStructureId() %>"
								ddmFormValues="<%= journalEditArticleDisplayContext.getDDMFormValues(ddmStructure) %>"
								defaultEditLocale="<%= LocaleUtil.fromLanguageId(journalEditArticleDisplayContext.getSelectedLanguageId()) %>"
								defaultLocale="<%= LocaleUtil.fromLanguageId(journalEditArticleDisplayContext.getDefaultArticleLanguageId()) %>"
								documentLibrarySelectorURL="<%= String.valueOf(journalItemSelectorHelper.getDocumentLibrarySelectorURL()) %>"
								groupId="<%= journalEditArticleDisplayContext.getGroupId() %>"
								ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>"
								imageSelectorURL="<%= String.valueOf(journalItemSelectorHelper.getImageSelectorURL()) %>"
								requestedLocale="<%= locale %>"
							/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= renderResponse.getNamespace() + "JournalPortletComponent" %>'
	module="js/JournalPortlet.es"
	servletContext="<%= application %>"
/>