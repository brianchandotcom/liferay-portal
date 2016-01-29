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

String portletResource = ParamUtil.getString(request, "portletResource");

long referringPlid = ParamUtil.getLong(request, "referringPlid");
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

boolean changeStructure = GetterUtil.getBoolean(ParamUtil.getString(request, "changeStructure"));

JournalArticle article = journalDisplayContext.getArticle();

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long folderId = BeanParamUtil.getLong(article, request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");
long classPK = BeanParamUtil.getLong(article, request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");

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
	ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), ddmStructureKey, true);
}

String ddmTemplateKey = ParamUtil.getString(request, "ddmTemplateKey");

if (Validator.isNull(ddmTemplateKey) && (article != null)) {
	ddmTemplateKey = article.getDDMTemplateKey();
}

DDMTemplate ddmTemplate = null;

long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

if (ddmTemplateId > 0) {
	ddmTemplate = DDMTemplateLocalServiceUtil.fetchDDMTemplate(ddmTemplateId);
}
else if (Validator.isNotNull(ddmTemplateKey)) {
	ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmTemplateKey, true);
}

if (ddmTemplate == null) {
	List<DDMTemplate> ddmTemplates = DDMTemplateServiceUtil.getTemplates(company.getCompanyId(), groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId(), PortalUtil.getClassNameId(JournalArticle.class), true, WorkflowConstants.STATUS_APPROVED);

	if (!ddmTemplates.isEmpty()) {
		ddmTemplate = ddmTemplates.get(0);
	}
}

String defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault());

boolean changeableDefaultLanguage = journalWebConfiguration.changeableDefaultLanguage();

if (article != null) {
	String articleDefaultLanguageId = LocalizationUtil.getDefaultLanguageId(article.getContent(), LocaleUtil.getSiteDefault());

	if (!Validator.equals(defaultLanguageId, articleDefaultLanguageId)) {
		changeableDefaultLanguage = true;
	}

	defaultLanguageId = articleDefaultLanguageId;
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

request.setAttribute("edit_article.jsp-redirect", redirect);

request.setAttribute("edit_article.jsp-structure", ddmStructure);
request.setAttribute("edit_article.jsp-template", ddmTemplate);

request.setAttribute("edit_article.jsp-defaultLanguageId", defaultLanguageId);

request.setAttribute("edit_article.jsp-changeStructure", changeStructure);
%>

<c:if test="<%= showHeader %>">

	<%
	portletDisplay.setShowBackIcon(true);

	if (Validator.isNotNull(redirect)) {
		portletDisplay.setURLBack(redirect);
	}
	else if ((classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) && (article != null)) {
		PortletURL backURL = liferayPortletResponse.createRenderURL();

		backURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		backURL.setParameter("folderId", String.valueOf(article.getFolderId()));

		portletDisplay.setURLBack(backURL.toString());
	}

	String title = StringPool.BLANK;

	if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
		title = LanguageUtil.get(request, "structure-default-values");
	}
	else if ((article != null) && !article.isNew()) {
		title = article.getTitle(locale);
	}
	else {
		title = LanguageUtil.get(request, "new-web-content");
	}

	renderResponse.setTitle(title);
	%>

</c:if>

<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />
<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

<liferay-ui:error exception="<%= FileSizeException.class %>">

	<%
	long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

	if (fileMaxSize == 0) {
		fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
	}
	%>

	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
	<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
</liferay-ui:error>

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<c:if test="<%= (article != null) && !article.isNew() && (classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
	<div class="panel text-center">
		<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />
	</div>
</c:if>

<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
</portlet:actionURL>

<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
</portlet:renderURL>

<aui:form action="<%= editArticleActionURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm1" onSubmit="event.preventDefault();">
	<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
	<aui:input name="referringPlid" type="hidden" value="<%= referringPlid %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layout.isPrivateLayout() %>" />
	<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
	<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
	<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
	<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
	<aui:input name="articleIds" type="hidden" value="<%= articleId + JournalPortlet.VERSION_SEPARATOR + version %>" />
	<aui:input name="version" type="hidden" value="<%= ((article == null) || article.isNew()) ? version : article.getVersion() %>" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="changeStructure" type="hidden" />
	<aui:input name="ddmStructureId" type="hidden" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<%
	DDMFormValues ddmFormValues = journalDisplayContext.getDDMFormValues(ddmStructure);

	Locale[] availableLocales = new Locale[] {LocaleUtil.fromLanguageId(defaultLanguageId)};

	if (ddmFormValues != null) {
		Set<Locale> availableLocalesSet = ddmFormValues.getAvailableLocales();

		availableLocales = availableLocalesSet.toArray(new Locale[availableLocalesSet.size()]);
	}
	%>

	<aui:translation-manager
		availableLocales="<%= availableLocales %>"
		changeableDefaultLanguage="<%= changeableDefaultLanguage %>"
		defaultLanguageId="<%= defaultLanguageId %>"
		id="translationManager"
	/>

	<%
	boolean approved = false;
	boolean pending = false;

	long inheritedWorkflowDDMStructuresFolderId = JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(folderId);

	boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalFolder.class.getName(), folderId, ddmStructure.getStructureId()) || WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalFolder.class.getName(), inheritedWorkflowDDMStructuresFolderId, ddmStructure.getStructureId()) || WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalFolder.class.getName(), inheritedWorkflowDDMStructuresFolderId, JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

	if ((article != null) && (version > 0)) {
		approved = article.isApproved();

		 if (workflowEnabled) {
			pending = article.isPending();
		}
	}
	%>

	<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
		<c:if test="<%= approved %>">
			<div class="alert alert-info">
				<liferay-ui:message key="a-new-version-is-created-automatically-if-this-content-is-modified" />
			</div>
		</c:if>

		<c:if test="<%= pending %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>
	</c:if>

	<liferay-ui:form-navigator
		formModelBean="<%= article %>"
		formName="fm1"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL %>"
		markupView="lexicon"
		showButtons="<%= false %>"
	/>

	<aui:button-row cssClass="journal-article-button-row">

		<%
		boolean hasSavePermission = false;

		if ((article != null) && !article.isNew()) {
			hasSavePermission = JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE);
		}
		else {
			hasSavePermission = JournalFolderPermission.contains(permissionChecker, groupId, folderId, ActionKeys.ADD_ARTICLE);
		}

		String saveButtonLabel = "save";

		if ((article == null) || article.isApproved() || article.isDraft() || article.isExpired()) {
			saveButtonLabel = "save-as-draft";
		}

		String publishButtonLabel = "publish";

		if (workflowEnabled) {
			publishButtonLabel = "submit-for-publication";
		}

		if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
			publishButtonLabel = "save";
		}
		%>

		<c:if test="<%= hasSavePermission %>">
			<aui:button cssClass="btn-lg" data-actionname="<%= Constants.PUBLISH %>" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
				<aui:button cssClass="btn-lg" data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "addArticle" : "updateArticle" %>' name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
			</c:if>
		</c:if>

		<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" var="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/preview_article_content.jsp" />

	<c:if test="<%= article != null %>">
		<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
		<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		<portlet:param name="ddmTemplateKey" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : article.getDDMTemplateKey() %>" />
	</c:if>
</liferay-portlet:renderURL>

<portlet:renderURL var="editArticleURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="version" value="<%= String.valueOf(version) %>" />
</portlet:renderURL>

<aui:script use="liferay-portlet-journal">
	new Liferay.Portlet.Journal(
		{
			article: {
				editUrl: '<%= editArticleURL %>',
				id: '<%= (article != null) ? HtmlUtil.escape(articleId) : StringPool.BLANK %>',

				<c:if test="<%= (article != null) && !article.isNew() %>">
					previewUrl: '<%= HtmlUtil.escapeJS(previewArticleContentURL.toString()) %>',
				</c:if>

				title: '<%= (article != null) ? HtmlUtil.escapeJS(article.getTitle(locale)) : StringPool.BLANK %>'
			},
			namespace: '<portlet:namespace />',
			'strings.addTemplate': '<liferay-ui:message key="please-add-a-template-to-render-this-structure" />',
			'strings.saveAsDraftBeforePreview': '<liferay-ui:message key="in-order-to-preview-your-changes,-the-web-content-is-saved-as-a-draft" />'
		}
	);
</aui:script>