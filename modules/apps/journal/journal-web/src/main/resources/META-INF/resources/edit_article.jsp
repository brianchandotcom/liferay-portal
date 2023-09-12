<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<portlet:param name="ddmStructureId" value="<%= String.valueOf(journalEditArticleDisplayContext.getDDMStructureId()) %>" />
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
	<aui:input name="ddmStructureId" type="hidden" value="<%= journalEditArticleDisplayContext.getDDMStructureId() %>" />
	<aui:input name="ddmTemplateId" type="hidden" />
	<aui:input name="availableLocales" type="hidden" />
	<aui:input name="defaultLanguageId" type="hidden" value="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>" />
	<aui:input name="languageId" type="hidden" value="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-article">

		<%
		DDMStructure ddmStructure = journalEditArticleDisplayContext.getDDMStructure();
		%>

		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input cssClass="form-control-inline" defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>" label='<%= LanguageUtil.get(request, "name") %>' labelCssClass="sr-only" languagesDropdownDirection="down" localized="<%= true %>" name="titleMapAsXML" placeholder='<%= LanguageUtil.format(request, "untitled-x", HtmlUtil.escape(ddmStructure.getName(locale))) %>' required="<%= journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASS_NAME_ID_DEFAULT %>" selectedLanguageId="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>" type="text" wrapperCssClass="article-content-title mb-0" />
				</li>
				<li class="tbar-item">
					<div class="c-gap-3 form-group-sm journal-article-button-row tbar-section text-right">
						<c:choose>
							<c:when test="<%= journalEditArticleDisplayContext.isJournalArticleAutoSaveDraftEnabled() %>">
								<div class="align-items-center d-none mx-3 small" id="<portlet:namespace />savingChangesIndicator">
									<liferay-ui:message key="saving" />

									<span aria-hidden="true" class="d-inline-block loading-animation loading-animation-sm ml-2 my-0"></span>
								</div>

								<div class="align-items-center d-none mx-3 small text-success" id="<portlet:namespace />changesSavedIndicator">
									<liferay-ui:message key="saved" />

									<clay:icon
										cssClass="ml-2"
										symbol="check-circle"
									/>
								</div>
							</c:when>
							<c:otherwise>
								<clay:link
									borderless="<%= true %>"
									displayType="secondary"
									href="<%= journalEditArticleDisplayContext.getRedirect() %>"
									label="cancel"
									type="button"
								/>
							</c:otherwise>
						</c:choose>

						<c:if test="<%= journalEditArticleDisplayContext.getClassNameId() > JournalArticleConstants.CLASS_NAME_ID_DEFAULT %>">
							<portlet:actionURL name="/journal/reset_values_ddm_structure" var="resetValuesDDMStructureURL">
								<portlet:param name="mvcPath" value="/edit_data_definition.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(journalEditArticleDisplayContext.getGroupId()) %>" />
								<portlet:param name="articleId" value="<%= journalEditArticleDisplayContext.getArticleId() %>" />
								<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
							</portlet:actionURL>

							<clay:button
								data-url="<%= resetValuesDDMStructureURL %>"
								displayType="secondary"
								id='<%= liferayPortletResponse.getNamespace() + "resetValuesButton" %>'
								label="reset-values"
							/>
						</c:if>

						<c:if test="<%= journalEditArticleDisplayContext.hasSavePermission() %>">
							<c:if test="<%= !journalEditArticleDisplayContext.isJournalArticleAutoSaveDraftEnabled() && (journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASS_NAME_ID_DEFAULT) %>">
								<clay:button
									data-actionname='<%= ((article == null) || Validator.isNull(article.getArticleId())) ? "/journal/add_article" : "/journal/update_article" %>'
									displayType="secondary"
									id='<%= liferayPortletResponse.getNamespace() + "saveButton" %>'
									label="<%= journalEditArticleDisplayContext.getSaveButtonLabel() %>"
									type="submit"
								/>
							</c:if>

							<clay:button
								data-actionname="<%= Constants.PUBLISH %>"
								displayType="primary"
								id='<%= liferayPortletResponse.getNamespace() + "publishButton" %>'
								label="<%= journalEditArticleDisplayContext.getPublishButtonLabel() %>"
								type="submit"
							/>
						</c:if>

						<div role="tablist">
							<clay:button
								aria-controls='<%= liferayPortletResponse.getNamespace() + "contextualSidebarContainer" %>'
								aria-label='<%= LanguageUtil.get(request, "close-configuration-panel") %>'
								aria-selected="true"
								borderless="<%= true %>"
								cssClass="lfr-portal-tooltip"
								displayType="secondary"
								icon="cog"
								id='<%= liferayPortletResponse.getNamespace() + "contextualSidebarButton" %>'
								role="tab"
								small="<%= true %>"
								title="close-configuration-panel"
								type="button"
							/>
						</div>
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div aria-label="<%= LanguageUtil.get(request, "configuration-panel") %>" class="contextual-sidebar edit-article-sidebar sidebar-light sidebar-sm" id="<portlet:namespace />contextualSidebarContainer" role="tabpanel" tabindex="-1">
		<div class="overflow-hidden sidebar-body">
			<div class="sheet-row">
				<clay:tabs
					tabsItems="<%= journalEditArticleDisplayContext.getTabsItems() %>"
				>
					<clay:tabs-panel>
						<liferay-frontend:form-navigator
							fieldSetCssClass="panel-group-flush"
							formModelBean="<%= article %>"
							id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_JOURNAL %>"
							showButtons="<%= false %>"
						/>
					</clay:tabs-panel>

					<c:if test="<%= (article != null) && (journalEditArticleDisplayContext.getClassNameId() == JournalArticleConstants.CLASS_NAME_ID_DEFAULT) %>">
						<clay:tabs-panel>
							<liferay-layout:layout-classed-model-usages-view
								className="<%= JournalArticle.class.getName() %>"
								classPK="<%= article.getResourcePrimKey() %>"
							/>
						</clay:tabs-panel>
					</c:if>
				</clay:tabs>
			</div>
		</div>
	</div>

	<div class="contextual-sidebar-content">
		<clay:container-fluid
			cssClass="container-view"
		>
			<div class="article-content-content">
				<aui:model-context bean="<%= article %>" defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>" model="<%= JournalArticle.class %>" />

				<liferay-ui:error exception="<%= ArticleContentException.class %>" message="please-enter-valid-content" />
				<liferay-ui:error exception="<%= ArticleContentSizeException.class %>" message="you-have-exceeded-the-maximum-web-content-size-allowed" />
				<liferay-ui:error exception="<%= ArticleFriendlyURLException.class %>" message="you-must-define-a-friendly-url-for-the-default-language" />
				<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />

				<liferay-ui:error exception="<%= ArticleTitleException.class %>">
					<liferay-ui:message arguments="<%= LocaleUtil.toW3cLanguageId(journalEditArticleDisplayContext.getDefaultArticleLanguageId()) %>" key="please-enter-a-valid-title-for-the-default-language-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= ArticleTitleException.MustNotExceedMaximumLength.class %>">

					<%
					int titleMaxLength = ModelHintsUtil.getMaxLength(JournalArticleLocalization.class.getName(), "title");
					%>

					<liferay-ui:message arguments="<%= String.valueOf(titleMaxLength) %>" key="please-enter-a-title-with-fewer-than-x-characters" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= ArticleVersionException.class %>" message="another-user-has-made-changes-since-you-started-editing" />
				<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
				<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="a-file-with-that-name-already-exists" />

				<liferay-ui:error exception="<%= ExportImportContentValidationException.class %>">

					<%
					ExportImportContentValidationException eicve = (ExportImportContentValidationException)errorException;
					%>

					<c:choose>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.ARTICLE_NOT_FOUND %>">
							<liferay-ui:message key="unable-to-validate-referenced-web-content-article" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.FILE_ENTRY_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= new String[] {MapUtil.toString(eicve.getDlReferenceParameters()), eicve.getDlReference()} %>" key="unable-to-validate-referenced-document-because-it-cannot-be-found-with-the-following-parameters-x-when-analyzing-link-x" />
						</c:when>
						<c:when test="<%= eicve.getType() == ExportImportContentValidationException.JOURNAL_FEED_NOT_FOUND %>">
							<liferay-ui:message arguments="<%= eicve.getJournalArticleFeedURL() %>" key="unable-to-validate-referenced-journal-feed-because-it-cannot-be-found-with-url-x" />
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

					<%
					FileSizeException fileSizeException = (FileSizeException)errorException;
					%>

					<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(fileSizeException.getMaxSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= InvalidDDMStructureException.class %>" message="the-structure-you-selected-is-not-valid-for-this-folder" />

				<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
					<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(FileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= LocaleException.class %>">

					<%
					LocaleException le = (LocaleException)errorException;
					%>

					<c:if test="<%= le.getType() == LocaleException.TYPE_CONTENT %>">
						<liferay-ui:message arguments="<%= new String[] {StringUtil.merge(le.getSourceAvailableLanguageIds(), StringPool.COMMA_AND_SPACE), StringUtil.merge(le.getTargetAvailableLanguageIds(), StringPool.COMMA_AND_SPACE)} %>" key="the-default-language-x-does-not-match-the-portal's-available-languages-x" />
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

				<liferay-data-engine:data-layout-renderer
					containerId='<%= liferayPortletResponse.getNamespace() + "dataEngineLayoutRenderer" %>'
					contentType="journal"
					dataDefinitionId="<%= ddmStructure.getStructureId() %>"
					dataRecordValues="<%= journalEditArticleDisplayContext.getValues(ddmStructure) %>"
					defaultLanguageId="<%= journalEditArticleDisplayContext.getDefaultArticleLanguageId() %>"
					languageId="<%= journalEditArticleDisplayContext.getSelectedLanguageId() %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					persisted="<%= article != null %>"
					submittable="<%= false %>"
				/>

				<liferay-frontend:component
					componentId='<%= liferayPortletResponse.getNamespace() + "DataEngineLayoutRendererLanguageProxy" %>'
					context="<%= journalEditArticleDisplayContext.getDataEngineLayoutRendererComponentContext() %>"
					module="js/DataEngineLayoutRendererLanguageProxy.es"
					servletContext="<%= application %>"
				/>
			</div>
		</clay:container-fluid>
	</div>
</aui:form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "JournalPortletComponent" %>'
	context="<%= journalEditArticleDisplayContext.getComponentContext() %>"
	module="js/JournalPortlet.es"
	servletContext="<%= application %>"
/>

<%@ include file="/friendly_url_changed_message.jspf" %>