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

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticleConstants.VERSION_DEFAULT);

String ddmStructureKey = ParamUtil.getString(request, "ddmStructureKey");

if (Validator.isNull(ddmStructureKey) && (article != null)) {
	ddmStructureKey = article.getDDMStructureKey();
}

DDMStructure ddmStructure = null;

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

if (ddmStructureId > 0) {
	ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(ddmStructureId);
}
else if (Validator.isNotNull(ddmStructureKey)) {
	ddmStructure = DDMStructureLocalServiceUtil.fetchStructure((article != null) ? article.getGroupId() : themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey, true);
}

String ddmTemplateKey = ParamUtil.getString(request, "ddmTemplateKey");

if (Validator.isNull(ddmTemplateKey) && (article != null) && Objects.equals(article.getDDMStructureKey(), ddmStructureKey)) {
	ddmTemplateKey = article.getDDMTemplateKey();
}

DDMTemplate ddmTemplate = null;

long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

if (ddmTemplateId > 0) {
	ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(ddmTemplateId);
}
else if (Validator.isNotNull(ddmTemplateKey)) {
	ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate((article != null) ? article.getGroupId() : themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmTemplateKey, true);
}

if (ddmTemplate == null) {
	List<DDMTemplate> ddmTemplates = DDMTemplateServiceUtil.getTemplates(company.getCompanyId(), ddmStructure.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId(), PortalUtil.getClassNameId(JournalArticle.class), true, WorkflowConstants.STATUS_APPROVED);

	if (!ddmTemplates.isEmpty()) {
		ddmTemplate = ddmTemplates.get(0);
	}
}

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

<liferay-frontend:edit-form
	action="<%= editArticleActionURL %>"
	enctype="multipart/form-data"
	method="post"
	name="fm1"
	onSubmit="event.preventDefault();"
>
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
	<aui:input name="hideDefaultSuccessMessage" type="hidden" value="<%= journalEditArticleDisplayContext.isHideDefaultSuccessMessage() || (journalEditArticleDisplayContext.getClassNameId() == PortalUtil.getClassNameId(DDMStructure.class)) %>" />
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
	<aui:input name="changeStructure" type="hidden" />
	<aui:input name="ddmStructureId" type="hidden" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="languageId" type="hidden" value="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<liferay-frontend:edit-form-body>
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
					<liferay-ui:message arguments="<%= new String[] {MapUtil.toString(eicve.getDlReferenceParameters()), eicve.getDlReference()} %>" key="unable-to-validate-referenced-document-because-it-cannot-be-found-with-the-following-parameters-x-when-analyzing-link-x" />
				</c:when>
				<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_GROUP_NOT_FOUND %>">
					<liferay-ui:message arguments="<%= new String[] {eicve.getLayoutURL(), eicve.getGroupFriendlyURL()} %>" key="unable-to-validate-referenced-page-with-url-x-because-the-page-group-with-url-x-cannot-be-found" />
				</c:when>
				<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_NOT_FOUND %>">
					<liferay-ui:message arguments="<%= MapUtil.toString(eicve.getLayoutReferenceParameters()) %>" key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-the-following-parameters-x" />
				</c:when>
				<c:when test="<%= eicve.getType() == ExportImportContentValidationException.LAYOUT_WITH_URL_NOT_FOUND %>">
					<liferay-ui:message arguments="<%= eicve.getLayoutURL() %>" key="unable-to-validate-referenced-page-because-it-cannot-be-found-with-url-x" />
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

		<c:if test="<%= (article != null) && !article.isNew() && (journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
			<liferay-frontend:info-bar>
				<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />
			</liferay-frontend:info-bar>
		</c:if>

		<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
			<c:if test="<%= journalEditArticleDisplayContext.isApproved() %>">
				<div class="alert alert-info">
					<liferay-ui:message key="a-new-version-is-created-automatically-if-this-content-is-modified" />
				</div>
			</c:if>

			<c:if test="<%= journalEditArticleDisplayContext.isPending() %>">
				<div class="alert alert-info">
					<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
				</div>
			</c:if>
		</c:if>

		<c:if test="<%= journalWebConfiguration.changeableDefaultLanguage() %>">
			<soy:component-renderer
				context="<%= journalEditArticleDisplayContext.getChangeDefaultLanguageSoyContext() %>"
				module="js/ChangeDefaultLanguage.es"
				templateNamespace="com.liferay.journal.web.ChangeDefaultLanguage.render"
			/>
		</c:if>

		<liferay-frontend:form-navigator
			formModelBean="<%= article %>"
			id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL %>"
			showButtons="<%= false %>"
		/>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<div class="journal-article-button-row">
			<c:if test="<%= journalEditArticleDisplayContext.hasSavePermission() %>">
				<aui:button data-actionname="<%= Constants.PUBLISH %>" disabled="<%= journalEditArticleDisplayContext.isPending() %>" name="publishButton" type="submit" value="<%= journalEditArticleDisplayContext.getPublishButtonLabel() %>" />

				<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
					<aui:button data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "addArticle" : "updateArticle" %>' name="saveButton" primary="<%= false %>" type="submit" value="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>" />
				</c:if>
			</c:if>

			<aui:button href="<%= journalEditArticleDisplayContext.getRedirect() %>" type="cancel" />
		</div>
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" var="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/preview_article_content.jsp" />

	<c:if test="<%= article != null %>">
		<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
		<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		<portlet:param name="ddmTemplateKey" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : article.getDDMTemplateKey() %>" />
	</c:if>
</liferay-portlet:renderURL>

<aui:script use="liferay-portlet-journal">
	var journal = new Liferay.Portlet.Journal(
		{
			article: {
				defaultLanguageId: '<%= journalDisplayContext.getDefaultLanguageId() %>',
				editUrl: '<%= journalEditArticleDisplayContext.getEditArticleURL() %>',
				id: '<%= (article != null) ? HtmlUtil.escape(journalEditArticleDisplayContext.getArticleId()) : StringPool.BLANK %>',

				<c:if test="<%= (article != null) && !article.isNew() %>">
					previewUrl: '<%= HtmlUtil.escapeJS(previewArticleContentURL.toString()) %>',
					titleMap: <%= journalDisplayContext.getTitleMapAsJSON() %>,
				</c:if>

				title: '<%= (article != null) ? HtmlUtil.escapeJS(article.getTitle(locale)) : StringPool.BLANK %>'
			},
			namespace: '<portlet:namespace />',
			'strings.addTemplate': '<liferay-ui:message key="please-add-a-template-to-render-this-structure" />',
			'strings.saveAsDraftBeforePreview': '<liferay-ui:message key="in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft" />'
		}
	);

	var onDestroyPortlet = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			journal.destroy();

			Liferay.detach('destroyPortlet', onDestroyPortlet);
		}
	};

	Liferay.on('destroyPortlet', onDestroyPortlet);
</aui:script>