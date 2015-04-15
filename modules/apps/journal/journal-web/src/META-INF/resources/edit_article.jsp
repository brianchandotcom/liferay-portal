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
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL");

String portletResource = ParamUtil.getString(request, "portletResource");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

boolean changeStructure = GetterUtil.getBoolean(ParamUtil.getString(request, "changeStructure"));

JournalArticle article = ActionUtil.getArticle(request);

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
	List<DDMTemplate> ddmTemplates = DDMTemplateServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId(), true);

	if (!ddmTemplates.isEmpty()) {
		ddmTemplate = ddmTemplates.get(0);
	}
}

String defaultLanguageId = LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale());

if (article != null) {
	defaultLanguageId = LocalizationUtil.getDefaultLanguageId(article.getContent(), LocaleUtil.getSiteDefault());
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

String[] mainSections = JournalWebConfigurationValues.JOURNAL_ARTICLE_FORM_ADD;

if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
	mainSections = JournalWebConfigurationValues.JOURNAL_ARTICLE_FORM_DEFAULT_VALUES;
}
else if ((article != null) && (article.getId() > 0)) {
	mainSections = JournalWebConfigurationValues.JOURNAL_ARTICLE_FORM_UPDATE;
}

String[][] categorySections = {mainSections};

request.setAttribute("edit_article.jsp-redirect", redirect);

request.setAttribute("edit_article.jsp-structure", ddmStructure);
request.setAttribute("edit_article.jsp-template", ddmTemplate);

request.setAttribute("edit_article.jsp-defaultLanguageId", defaultLanguageId);

request.setAttribute("edit_article.jsp-changeStructure", changeStructure);
%>

<div class="article-form <%= ((article != null) && !article.isNew()) ? "article-form-edit" : "article-form-add" %>">
	<c:if test="<%= showHeader %>">
		<liferay-util:include page="/article_header.jsp" servletContext="<%= application %>" />
	</c:if>

	<aui:form enctype="multipart/form-data" method="post" name="fm2">
		<input name="groupId" type="hidden" value="" />
		<input name="articleId" type="hidden" value="" />
		<input name="version" type="hidden" value="" />
		<input name="title" type="hidden" value="" />
		<input name="xml" type="hidden" value="" />
	</aui:form>

	<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcPath" value="/edit_article.jsp" />
	</portlet:actionURL>

	<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="mvcPath" value="/edit_article.jsp" />
	</portlet:renderURL>

	<aui:form action="<%= editArticleActionURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm1" onSubmit="event.preventDefault();">
		<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" />
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
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

		<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />
		<liferay-ui:error exception="<%= DuplicateFileException.class %>" message="a-file-with-that-name-already-exists" />

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

		<div class="journal-article-wrapper" id="<portlet:namespace />journalArticleWrapper">
			<div class="journal-article-wrapper-content">
				<c:if test="<%= (article != null) && !article.isNew() %>">
					<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />

					<liferay-util:include page="/article_toolbar.jsp" servletContext="<%= application %>" />
				</c:if>

				<liferay-util:buffer var="htmlTop">
					<c:if test="<%= article != null %>">
						<div class="article-info">
							<div class="float-container">
								<c:if test="<%= article.isSmallImage() %>">
									<img alt="" class="article-image" src="<%= HtmlUtil.escape(article.getArticleImageURL(themeDisplay)) %>" width="150" />
								</c:if>

								<c:if test="<%= !article.isNew() %>">
									<span class="article-name"><%= HtmlUtil.escape(article.getTitle(locale)) %></span>
								</c:if>
							</div>
						</div>
					</c:if>
				</liferay-util:buffer>

				<liferay-util:buffer var="htmlBottom">

					<%
					boolean approved = false;
					boolean pending = false;

					long inheritedWorkflowDDMStructuresFolderId = JournalFolderLocalServiceUtil.getInheritedWorkflowFolderId(folderId);

					boolean workflowEnabled = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalFolder.class.getName(), folderId, ddmStructure.getStructureId()) || WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalFolder.class.getName(), inheritedWorkflowDDMStructuresFolderId, JournalArticleConstants.DDM_STRUCTURE_ID_ALL);

					if ((article != null) && (version > 0)) {
						approved = article.isApproved();

						 if (workflowEnabled) {
							pending = article.isPending();
						}
					}
					%>

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

						if ((article == null) || article.isDraft() || article.isApproved()) {
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
							<aui:button data-actionname="<%= Constants.PUBLISH %>" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

							<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
								<aui:button data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "addArticle" : "updateArticle" %>' name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
							</c:if>
						</c:if>

						<aui:button href="<%= redirect %>" type="cancel" />
					</aui:button-row>
				</liferay-util:buffer>

				<liferay-ui:form-navigator
					categoryNames="<%= _CATEGORY_NAMES %>"
					categorySections="<%= categorySections %>"
					formName="fm1"
					htmlBottom="<%= htmlBottom %>"
					htmlTop="<%= htmlTop %>"
					jspPath="/article/"
					showButtons="<%= false %>"
				/>
			</div>
		</div>
	</aui:form>
</div>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" var="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/preview_article_content.jsp" />

	<c:if test="<%= (article != null) %>">
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
					<liferay-security:permissionsURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"
						modelResource="<%= JournalArticle.class.getName() %>"
						modelResourceDescription="<%= HtmlUtil.escape(article.getTitle(locale)) %>"
						resourceGroupId="<%= String.valueOf(article.getGroupId()) %>"
						resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
						var="permissionsURL"
					/>

					permissionsUrl: '<%= permissionsURL %>',
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

<c:if test='<%= (article != null) && SessionMessages.contains(renderRequest, "previewRequested") %>'>
	<aui:script use="liferay-journal-preview">
		Liferay.fire(
			'previewArticle',
			{
				title: '<%= HtmlUtil.escapeJS(article.getTitle(locale)) %>',
				uri: '<%= HtmlUtil.escapeJS(previewArticleContentURL.toString()) %>'
			}
		);
	</aui:script>
</c:if>

<%!
private static final String[] _CATEGORY_NAMES = {""};
%>