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

long groupId = BeanParamUtil.getLong(article, request, "groupId", scopeGroupId);

long classNameId = ParamUtil.getLong(request, "classNameId");

String newArticleId = ParamUtil.getString(request, "newArticleId");

DDMStructure ddmStructure = (DDMStructure)request.getAttribute("edit_article.jsp-structure");

DDMTemplate ddmTemplate = (DDMTemplate)request.getAttribute("edit_article.jsp-template");

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

boolean changeStructure = GetterUtil.getBoolean(request.getAttribute("edit_article.jsp-changeStructure"));

long folderId = journalDisplayContext.getFolderId();

boolean searchRestriction = false;

if (journalDisplayContext.getRestrictionType() == JournalFolderConstants.RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW) {
	searchRestriction = true;
}

if (!searchRestriction) {
	folderId = JournalFolderLocalServiceUtil.getOverridedDDMStructuresFolderId(folderId);

	searchRestriction = folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="content"
/>

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= defaultLanguageId %>" model="<%= JournalArticle.class %>" />

<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
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
<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-structure-you-selected-is-not-valid-for-this-folder" />

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
			<liferay-ui:message key="please-select-an-existing-display-page" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message key="the-content-references-a-missing-page" />
		</c:otherwise>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= NoSuchStructureException.class %>" message="please-select-an-existing-structure" />
<liferay-ui:error exception="<%= NoSuchTemplateException.class %>" message="please-select-an-existing-template" />
<liferay-ui:error exception="<%= StorageFieldRequiredException.class %>" message="please-fill-out-all-required-fields" />

<aui:fieldset>
	<aui:input autoFocus="<%= true %>" label="title" localized="<%= true %>" name="titleMapAsXML" type="text" wrapperCssClass="article-content-title">
		<c:if test="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>">
			<aui:validator name="required" />
		</c:if>
	</aui:input>

	<c:if test="<%= (article == null) || article.isNew() %>">
		<c:choose>
			<c:when test="<%= journalWebConfiguration.journalArticleForceAutogenerateId() || (classNameId != JournalArticleConstants.CLASSNAME_ID_DEFAULT) %>">
				<aui:input name="newArticleId" type="hidden" />
				<aui:input name="autoArticleId" type="hidden" value="<%= true %>" />
			</c:when>
			<c:otherwise>
				<aui:input field="articleId" fieldParam="newArticleId" label="id" name="newArticleId" value="<%= newArticleId %>" />

				<aui:input label="autogenerate-id" name="autoArticleId" type="checkbox" />
			</c:otherwise>
		</c:choose>
	</c:if>

	<div class="article-content-description">
		<liferay-ui:input-localized
			cssClass="form-control"
			editorName="alloyeditor"
			formName="fm"
			ignoreRequestValue="<%= changeStructure %>"
			name="descriptionMapAsXML"
			placeholder="description"
			type="editor"
			xml="<%= article != null ? article.getDescriptionMapAsXML() : StringPool.BLANK %>"
		/>
	</div>

	<div class="article-content-content">
		<liferay-ddm:html
			checkRequired="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>"
			classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
			classPK="<%= ddmStructure.getStructureId() %>"
			ddmFormValues="<%= journalDisplayContext.getDDMFormValues(ddmStructure) %>"
			ignoreRequestValue="<%= changeStructure %>"
			requestedLocale="<%= LocaleUtil.fromLanguageId(defaultLanguageId) %>"
		/>
	</div>

	<aui:input label="searchable" name="indexable" type="toggle-switch" value="<%= (article != null) ? article.isIndexable() : true %>" />
</aui:fieldset>

<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.EDIT) %>" var="editStructureURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/edit_structure.jsp" />
	<portlet:param name="navigationStartsOn" value="<%= DDMNavigationHelper.EDIT_STRUCTURE %>" />
	<portlet:param name="closeRedirect" value="<%= currentURL %>" />
	<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
	<portlet:param name="refererPortletName" value="<%= JournalPortletKeys.JOURNAL %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="classNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(DDMStructure.class)) %>" />
	<portlet:param name="classPK" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/edit_template.jsp" />
	<portlet:param name="navigationStartsOn" value="<%= DDMNavigationHelper.EDIT_TEMPLATE %>" />
	<portlet:param name="closeRedirect" value="<%= currentURL %>" />
	<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
	<portlet:param name="refererPortletName" value="<%= JournalPortletKeys.JOURNAL %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="classNameId" value="<%= String.valueOf(classNameId) %>" />
	<portlet:param name="templateId" value="<%= (ddmTemplate != null) ? String.valueOf(ddmTemplate.getTemplateId()) : StringPool.BLANK %>" />
	<portlet:param name="showCacheableInput" value="<%= Boolean.TRUE.toString() %>" />
</liferay-portlet:renderURL>

<aui:script use="liferay-journal-content">
	new Liferay.Portlet.JournalContent(
		{
			'ddm.basePortletURL': '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.VIEW), PortletRequest.RENDER_PHASE) %>',
			'ddm.classNameId': '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
			'ddm.classPK': <%= ddmStructure.getPrimaryKey() %>,
			'ddm.groupId': <%= groupId %>,
			'ddm.refererPortletName': '<%= JournalPortletKeys.JOURNAL + ".selectStructure" %>',
			'ddm.resourceClassNameId': '<%= ddmStructure.getClassNameId() %>',
			'ddm.searchRestriction': <%= searchRestriction %>,
			'ddm.searchRestrictionClassNameId': <%= ClassNameLocalServiceUtil.getClassNameId(JournalFolder.class) %>,
			'ddm.searchRestrictionClassPK': <%= folderId %>,
			'ddm.templateId': <%= (ddmTemplate != null) ? ddmTemplate.getTemplateId() : 0 %>,
			editStructure: '#<portlet:namespace />editDDMStructure',
			editTemplate: '#<portlet:namespace />editDDMTemplate',
			namespace: '<portlet:namespace />',
			selectStructure: '#<portlet:namespace />selectStructure',
			selectTemplate: '#<portlet:namespace />selectTemplate',
			'strings.draft': '<liferay-ui:message key="draft" />',
			'strings.editStructure': '<liferay-ui:message key="editing-the-current-structure-deletes-all-unsaved-content" />',
			'strings.editTemplate': '<liferay-ui:message key="editing-the-current-template-deletes-all-unsaved-content" />',
			'urls.editStructure': '<%= editStructureURL %>',
			'urls.editTemplate': '<%= editTemplateURL %>'
		}
	);

	Liferay.Util.disableToggleBoxes('<portlet:namespace />autoArticleId', '<portlet:namespace />newArticleId', true);
</aui:script>