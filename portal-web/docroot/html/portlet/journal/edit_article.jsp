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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2");

String redirect = ParamUtil.getString(request, "redirect");

// Make sure the redirect is correct. This is a workaround for a layout that
// has both the Journal and Journal Content portlets and the user edits an
// article through the Journal Content portlet and then hits cancel.

/*if (redirect.indexOf("p_p_id=" + PortletKeys.JOURNAL_CONTENT) != -1) {
	if (layoutTypePortlet.hasPortletId(PortletKeys.JOURNAL)) {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setPortletMode(PortletMode.VIEW);
		portletURL.setWindowState(WindowState.NORMAL);

		redirect = portletURL.toString();
	}
}*/

String backURL = ParamUtil.getString(request, "backURL");

String portletResource = ParamUtil.getString(request, "portletResource");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long folderId = ParamUtil.getLong(request, "folderId", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long classNameId = BeanParamUtil.getLong(article, request, "classNameId");
long classPK = BeanParamUtil.getLong(article, request, "classPK");

String articleId = BeanParamUtil.getString(article, request, "articleId");

double version = BeanParamUtil.getDouble(article, request, "version", JournalArticleConstants.VERSION_DEFAULT);

String structureId = BeanParamUtil.getString(article, request, "structureId");

DDMStructure ddmStructure = null;

long ddmStructureId = ParamUtil.getLong(request, "ddmStructureId");

if (ddmStructureId > 0) {
	try {
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(ddmStructureId);
	}
	catch (NoSuchStructureException nsse) {
	}
}
else if (Validator.isNotNull(structureId)) {
	try {
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), structureId, true);
	}
	catch (NoSuchStructureException nsse) {
	}
}

String templateId = BeanParamUtil.getString(article, request, "templateId");

DDMTemplate ddmTemplate = null;

long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

if (ddmTemplateId > 0) {
	try {
		ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(ddmTemplateId);
	}
	catch (NoSuchTemplateException nste) {
	}
}
else if (Validator.isNotNull(templateId)) {
	try {
		ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(groupId, PortalUtil.getClassNameId(DDMStructure.class), templateId, true);
	}
	catch (NoSuchStructureException nste) {
	}
}

if (ddmTemplate == null) {
	List<DDMTemplate> ddmTemplates = DDMTemplateServiceUtil.getTemplates(groupId, PortalUtil.getClassNameId(DDMStructure.class), ddmStructure.getStructureId(), true);

	if (!ddmTemplates.isEmpty()) {
		ddmTemplate = ddmTemplates.get(0);
	}
}

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

if (Validator.isNull(defaultLanguageId) || !LanguageUtil.isAvailableLocale(themeDisplay.getSiteGroupId(), defaultLanguageId)) {
	if (article != null) {
		defaultLanguageId = article.getDefaultLanguageId();
	}
	else {
		defaultLanguageId = LocaleUtil.toLanguageId(themeDisplay.getSiteDefaultLocale());
	}
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

String[] mainSections = PropsValues.JOURNAL_ARTICLE_FORM_ADD;

if (Validator.isNotNull(toLanguageId)) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_TRANSLATE;
}
else if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_DEFAULT_VALUES;
}
else if ((article != null) && (article.getId() > 0)) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_UPDATE;
}

String[][] categorySections = {mainSections};

request.setAttribute("edit_article.jsp-redirect", redirect);

request.setAttribute("edit_article.jsp-structure", ddmStructure);
request.setAttribute("edit_article.jsp-template", ddmTemplate);

request.setAttribute("edit_article.jsp-defaultLanguageId", defaultLanguageId);
request.setAttribute("edit_article.jsp-toLanguageId", toLanguageId);
%>

<div class="article-form <%= ((article != null) && !article.isNew()) ? "article-form-edit" : "article-form-add" %>">
	<c:if test="<%= showHeader %>">
		<liferay-util:include page="/html/portlet/journal/article_header.jsp" />
	</c:if>

	<aui:form enctype="multipart/form-data" method="post" name="fm2">
		<input name="groupId" type="hidden" value="" />
		<input name="articleId" type="hidden" value="" />
		<input name="version" type="hidden" value="" />
		<input name="title" type="hidden" value="" />
		<input name="xml" type="hidden" value="" />
	</aui:form>

	<portlet:actionURL var="editArticleActionURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="struts_action" value="/journal/edit_article" />
	</portlet:actionURL>

	<portlet:renderURL var="editArticleRenderURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
		<portlet:param name="struts_action" value="/journal/edit_article" />
	</portlet:renderURL>

	<aui:form action="<%= editArticleActionURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm1">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= layout.isPrivateLayout() %>" />
		<aui:input name="folderId" type="hidden" value="<%= folderId %>" />
		<aui:input name="classNameId" type="hidden" value="<%= classNameId %>" />
		<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
		<aui:input name="articleId" type="hidden" value="<%= articleId %>" />
		<aui:input name="articleIds" type="hidden" value="<%= articleId + EditArticleAction.VERSION_SEPARATOR + version %>" />
		<aui:input name="version" type="hidden" value="<%= ((article == null) || article.isNew()) ? version : article.getVersion() %>" />
		<aui:input name="languageId" type="hidden" value="<%= Validator.isNotNull(toLanguageId) ? toLanguageId : defaultLanguageId %>" />
		<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
		<aui:input name="ddmStructureId" type="hidden" />
		<aui:input name="ddmTemplateId" type="hidden" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />

		<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

		<div class="journal-article-wrapper" id="<portlet:namespace />journalArticleWrapper">
			<div class="journal-article-wrapper-content">
				<c:if test="<%= Validator.isNull(toLanguageId) %>">
					<c:if test="<%= (article != null) && !article.isNew() %>">
						<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />

						<div class="article-toolbar toolbar" id="<portlet:namespace />articleToolbar">
							<div class="btn-group">
								<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
									<aui:button data-title='<%= LanguageUtil.get(pageContext, "in-order-to-preview-your-changes,-the-web-content-will-be-saved-as-a-draft") %>' icon="icon-search" name="basicPreviewButton" value="basic-preview" />
								</c:if>

								<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.PERMISSIONS) %>">
									<aui:button icon="icon-lock" name="articlePermissionsButton" value="permissions" />
								</c:if>

								<portlet:renderURL var="viewHistoryURL">
									<portlet:param name="struts_action" value="/journal/view_article_history" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
									<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
								</portlet:renderURL>

								<aui:button href="<%= viewHistoryURL %>" icon="icon-time" name="articleHistoryButton" value="view-history" />
							</div>
						</div>
					</c:if>
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
							<liferay-ui:message key="a-new-version-will-be-created-automatically-if-this-content-is-modified" />
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

						if (article != null) {
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

						<c:choose>
							<c:when test="<%= Validator.isNull(toLanguageId) %>">
								<c:if test="<%= hasSavePermission %>">
									<aui:button data-cmd="<%= Constants.PUBLISH %>" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

									<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
										<aui:button data-cmd="<%= ((article == null) || Validator.isNull(article.getArticleId())) ? Constants.ADD : Constants.UPDATE %>" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
									</c:if>
								</c:if>
							</c:when>
							<c:otherwise>
								<aui:button data-cmd="<%= Constants.TRANSLATE %>" name="translateButton" type="submit" value="save" />

								<%
								String[] translations = article.getAvailableLanguageIds();
								%>

								<aui:button data-cmd="<%= Constants.DELETE_TRANSLATION %>" disabled="<%= toLanguageId.equals(defaultLanguageId) || !ArrayUtil.contains(translations, toLanguageId) %>" name="removeArticleLocaleButton" value="remove-translation" />
							</c:otherwise>
						</c:choose>
						<aui:button href="<%= redirect %>" type="cancel" />
					</aui:button-row>
				</liferay-util:buffer>

				<c:choose>
					<c:when test="<%= Validator.isNull(toLanguageId) %>">
						<liferay-ui:form-navigator
							categoryNames="<%= _CATEGORY_NAMES %>"
							categorySections="<%= categorySections %>"
							formName="fm1"
							htmlBottom="<%= htmlBottom %>"
							htmlTop="<%= htmlTop %>"
							jspPath="/html/portlet/journal/article/"
							showButtons="<%= false %>"
						/>
					</c:when>
					<c:otherwise>

						<%
						for (String section : mainSections) {
						%>

							<div class="form-section">
								<liferay-util:include page='<%= "/html/portlet/journal/article/" + _getSectionJsp(section) + ".jsp" %>' />
							</div>

						<%
						}
						%>

						<%= htmlBottom %>

					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</aui:form>
</div>

<liferay-portlet:renderURL plid="<%= JournalUtil.getPreviewPlid(article, themeDisplay) %>" var="previewArticleContentURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/journal/preview_article_content" />

	<c:if test="<%= (article != null) %>">
		<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
		<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
		<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
		<portlet:param name="ddmTemplateKey" value="<%= (ddmTemplate != null) ? ddmTemplate.getTemplateKey() : article.getTemplateId() %>" />
	</c:if>
</liferay-portlet:renderURL>

<portlet:renderURL var="editArticleURL">
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="struts_action" value="/journal/edit_article" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
	<portlet:param name="version" value="<%= String.valueOf(version) %>" />
</portlet:renderURL>

<aui:script use="liferay-portlet-journal">
	new Liferay.Portlet.Journal(
		{
			article: {
				defaultLanguageId: '<%= HtmlUtil.escapeJS(defaultLanguageId) %>',
				editUrl: '<%= editArticleURL %>',
				id: '<%= (article != null) ? HtmlUtil.escape(articleId) : StringPool.BLANK %>',

				<c:if test="<%= (article != null) && !article.isNew() %>">
					<liferay-security:permissionsURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"
						modelResource="<%= JournalArticle.class.getName() %>"
						modelResourceDescription="<%= article.getTitle(locale) %>"
						resourcePrimKey="<%= String.valueOf(article.getResourcePrimKey()) %>"
						var="permissionsURL"
					/>

					permissionsUrl: '<%= permissionsURL %>',
					previewUrl: '<%= HtmlUtil.escapeJS(previewArticleContentURL.toString()) %>',
				</c:if>

				title: '<%= (article != null) ? HtmlUtil.escapeJS(article.getTitle(locale)) : StringPool.BLANK %>'
			},

			<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
				focusFieldId: '<%= (article != null || PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID) ? "#title" : "#newArticleId" %>',
			</c:if>

			namespace: '<portlet:namespace />'
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
private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}

private static final String[] _CATEGORY_NAMES = {""};
%>