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

long classNameId = ParamUtil.getLong(request, "classNameId");
%>

<aui:model-context bean="<%= article %>" defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultLanguageId() %>" model="<%= JournalArticle.class %>" />

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

<liferay-frontend:fieldset>

	<%
	JournalItemSelectorHelper journalItemSelectorHelper = new JournalItemSelectorHelper(article, journalDisplayContext.getFolder(), renderRequest, renderResponse);

	DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
	%>

	<div class="article-content-content">
		<liferay-ddm:html
			checkRequired="<%= classNameId == JournalArticleConstants.CLASSNAME_ID_DEFAULT %>"
			classNameId="<%= PortalUtil.getClassNameId(DDMStructure.class) %>"
			classPK="<%= ddmStructure.getStructureId() %>"
			ddmFormValues="<%= journalEditArticleDisplayContext.getDDMFormValues(ddmStructure) %>"
			defaultEditLocale="<%= LocaleUtil.fromLanguageId(journalEditArticleDisplayContext.getDefaultLanguageId()) %>"
			documentLibrarySelectorURL="<%= String.valueOf(journalItemSelectorHelper.getDocumentLibrarySelectorURL()) %>"
			groupId="<%= journalEditArticleDisplayContext.getGroupId() %>"
			ignoreRequestValue="<%= journalEditArticleDisplayContext.isChangeStructure() %>"
			imageSelectorURL="<%= String.valueOf(journalItemSelectorHelper.getImageSelectorURL()) %>"
			requestedLocale="<%= locale %>"
		/>
	</div>
</liferay-frontend:fieldset>