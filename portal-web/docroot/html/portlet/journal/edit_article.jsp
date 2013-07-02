<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

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
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(scopeGroupId, PortalUtil.getClassNameId(JournalArticle.class), structureId);
	}
	catch (NoSuchStructureException nsse) {
		ddmStructure = DDMStructureLocalServiceUtil.getStructure(themeDisplay.getCompanyGroupId(), PortalUtil.getClassNameId(JournalArticle.class), structureId);
	}
}

DDMTemplate ddmTemplate = null;

long ddmTemplateId = ParamUtil.getLong(request, "ddmTemplateId");

if (ddmTemplateId > 0) {
	try {
		ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(ddmTemplateId);
	}
	catch (NoSuchTemplateException nste) {
	}
}

String languageId = LanguageUtil.getLanguageId(request);

String defaultLanguageId = ParamUtil.getString(request, "defaultLanguageId");

String toLanguageId = ParamUtil.getString(request, "toLanguageId");

if (Validator.isNotNull(toLanguageId)) {
	languageId = toLanguageId;
}

if ((article == null) && (Validator.isNull(defaultLanguageId) || !LanguageUtil.isAvailableLocale(defaultLanguageId))) {
	if (LanguageUtil.isAvailableLocale(languageId)) {
		defaultLanguageId = languageId;
	}
	else {
		defaultLanguageId = LocaleUtil.toLanguageId(LocaleUtil.getDefault());
	}
}
else {
	if (Validator.isNull(defaultLanguageId) || !LanguageUtil.isAvailableLocale(defaultLanguageId)) {
		defaultLanguageId = article.getDefaultLocale();
	}
}

String[] mainSections = PropsValues.JOURNAL_ARTICLE_FORM_ADD;

if (Validator.isNotNull(toLanguageId)) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_TRANSLATE;
}
else if ((article != null) && (article.getId() > 0)) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_UPDATE;
}
else if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
	mainSections = PropsValues.JOURNAL_ARTICLE_FORM_DEFAULT_VALUES;
}

String[][] categorySections = {mainSections};

request.setAttribute("edit_article.jsp-redirect", redirect);

request.setAttribute("edit_article.jsp-structure", ddmStructure);
request.setAttribute("edit_article.jsp-template", ddmTemplate);

request.setAttribute("edit_article.jsp-languageId", languageId);
request.setAttribute("edit_article.jsp-defaultLanguageId", defaultLanguageId);
request.setAttribute("edit_article.jsp-toLanguageId", toLanguageId);
%>

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

<aui:form action="<%= editArticleActionURL %>" cssClass="lfr-dynamic-form" enctype="multipart/form-data" method="post" name="fm1" onSubmit='<%= renderResponse.getNamespace() + "submitForm(event);" %>'>
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
	<aui:input name="languageId" type="hidden" value="<%= languageId %>" />
	<aui:input id="articleContent" name="content" type="hidden" />
	<aui:input name="articleURL" type="hidden" value="<%= editArticleRenderURL %>" />
	<aui:input name="ddmStructureId" type="hidden" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-article-content-size-allowed" />

	<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

	<table class="lfr-table" id="<portlet:namespace />journalArticleWrapper" width="100%">
	<tr>
		<td class="lfr-top">
			<c:if test="<%= Validator.isNull(toLanguageId) %>">
				<c:if test="<%= article != null %>">
					<aui:workflow-status id="<%= String.valueOf(article.getArticleId()) %>" status="<%= article.getStatus() %>" version="<%= String.valueOf(article.getVersion()) %>" />
				</c:if>

				<liferay-util:include page="/html/portlet/journal/article_toolbar.jsp" />
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

				if ((article != null) && (version > 0)) {
					approved = article.isApproved();

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, JournalArticle.class.getName())) {
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
						hasSavePermission = JournalPermission.contains(permissionChecker, groupId, ActionKeys.ADD_ARTICLE);
					}

					String saveButtonLabel = "save";

					if ((article == null) || article.isDraft() || article.isApproved()) {
						saveButtonLabel = "save-as-draft";
					}

					String publishButtonLabel = "publish";

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), groupId, JournalArticle.class.getName())) {
						publishButtonLabel = "submit-for-publication";
					}

					if (classNameId > JournalArticleConstants.CLASSNAME_ID_DEFAULT) {
						publishButtonLabel = "save";
					}
					%>

					<c:choose>
						<c:when test="<%= Validator.isNull(toLanguageId) %>">
							<c:if test="<%= hasSavePermission %>">
								<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
									<aui:button name="saveButton" onClick='<%= renderResponse.getNamespace() + "saveArticle()" %>' primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
								</c:if>

								<aui:button disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "publishArticle()" %>' type="submit" value="<%= publishButtonLabel %>" />
							</c:if>
						</c:when>
						<c:otherwise>
							<aui:button name="translateButton" onClick='<%= renderResponse.getNamespace() + "translateArticle()" %>' type="submit" value="save" />

							<%
							String[] translations = article.getAvailableLocales();
							%>

							<aui:button disabled="<%= languageId.equals(defaultLanguageId) || !ArrayUtil.contains(translations, languageId) %>" name="removeArticleLocaleButton" onClick='<%= renderResponse.getNamespace() + "removeArticleLocale();" %>' value="remove-translation" />
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
		</td>
	</tr>
	</table>
</aui:form>

<aui:script>
	var <portlet:namespace />documentLibraryInput = null;
	var <portlet:namespace />imageGalleryInput = null;

	function <portlet:namespace />deleteArticle() {
		<c:choose>
			<c:when test="<%= (article != null) && article.isDraft() %>">
				var confirmationMessage = '<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-discard-this-draft") %>';
			</c:when>
			<c:otherwise>
				var confirmationMessage = '<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-this-article-version") %>';
			</c:otherwise>
		</c:choose>

		if (confirm(confirmationMessage)) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />expireArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.EXPIRE %>";
		submitForm(document.<portlet:namespace />fm1);
	}

	function <portlet:namespace />publishArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.PUBLISH %>";
	}

	function <portlet:namespace />removeArticleLocale() {
		if (confirm("<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-deactivate-this-language") %>")) {
			document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.DELETE_TRANSLATION %>";
			document.<portlet:namespace />fm1.<portlet:namespace />redirect.value = "<portlet:renderURL><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="struts_action" value="/journal/edit_article" /><portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" /><portlet:param name="articleId" value="<%= articleId %>" /><portlet:param name="version" value="<%= String.valueOf(version) %>" /></portlet:renderURL>&<portlet:namespace />languageId=<%= HtmlUtil.escapeJS(defaultLanguageId) %>";
			submitForm(document.<portlet:namespace />fm1);
		}
	}

	function <portlet:namespace />saveArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (article != null) ? Constants.UPDATE : Constants.ADD %>";
	}

	function <portlet:namespace />selectDocumentLibrary(url) {
		document.getElementById(<portlet:namespace />documentLibraryInput).value = url;
	}

	function <portlet:namespace />selectImageGallery(url) {
		document.getElementById(<portlet:namespace />imageGalleryInput).value = url;
	}

	function <portlet:namespace />submitForm(event) {
		event.preventDefault();

		if (window.<portlet:namespace />journalPortlet) {
			var cmd = document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value;

			if (cmd === '<%= Constants.TRANSLATE %>') {
				window.<portlet:namespace />journalPortlet.translateArticle();
			}
			else {
				window.<portlet:namespace />journalPortlet.saveArticle(cmd);
			}
		}
	}

	function <portlet:namespace />translateArticle() {
		document.<portlet:namespace />fm1.<portlet:namespace /><%= Constants.CMD %>.value = "<%= Constants.TRANSLATE %>";
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />title);
			</c:when>
			<c:otherwise>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace /><%= (article == null) ? "newArticleId" : "title" %>);
			</c:otherwise>
		</c:choose>
	</c:if>
</aui:script>

<%!
private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}

private static final String[] _CATEGORY_NAMES = {""};
%>