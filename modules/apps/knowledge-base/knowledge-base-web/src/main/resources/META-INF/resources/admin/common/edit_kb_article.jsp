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

<%@ include file="/admin/common/init.jsp" %>

<%
EditKBArticleDisplayContext editKBArticleDisplayContext = new EditKBArticleDisplayContext(kbGroupServiceConfiguration, liferayPortletRequest, liferayPortletResponse, portletConfig);

if (editKBArticleDisplayContext.isPortletTitleBasedNavigation()) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(editKBArticleDisplayContext.getRedirect());

	renderResponse.setTitle(editKBArticleDisplayContext.getHeaderTitle());
}
%>

<c:if test="<%= !editKBArticleDisplayContext.isHeaderVisible() %>">
	<liferay-ui:header
		backURL="<%= editKBArticleDisplayContext.getRedirect() %>"
		localizeTitle="<%= false %>"
		title="<%= editKBArticleDisplayContext.getHeaderTitle() %>"
	/>
</c:if>

<aui:form action="<%= editKBArticleDisplayContext.getUpdateKBArticleURL() %>" cssClass="edit-knowledge-base-article-form" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= editKBArticleDisplayContext.getRedirect() %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

	<nav class="component-tbar subnav-tbar-light tbar tbar-knowledge-base-edit-article">
		<clay:container-fluid>
			<ul class="tbar-nav">
				<li class="tbar-item tbar-item-expand">
					<aui:input autocomplete="off" cssClass="form-control-inline" label="" name="title" placeholder='<%= LanguageUtil.format(request, "untitled-x", "article") %>' required="<%= true %>" type="text" value="<%= HtmlUtil.escape(editKBArticleDisplayContext.getKBArticleTitle()) %>" wrapperCssClass="mb-0" />
				</li>
				<li class="tbar-item">
					<div class="tbar-section text-right">
						<clay:link
							borderless="<%= true %>"
							cssClass="mr-3"
							displayType="secondary"
							href="<%= editKBArticleDisplayContext.getRedirect() %>"
							label="cancel"
							small="<%= true %>"
							type="button"
						/>

						<clay:button
							cssClass="mr-3"
							displayType="secondary"
							label="<%= editKBArticleDisplayContext.getSaveButtonLabel() %>"
							small="<%= true %>"
							type="submit"
						/>

						<clay:button
							cssClass="mr-3"
							disabled="<%= editKBArticleDisplayContext.isPending() %>"
							displayType="primary"
							id='<%= liferayPortletResponse.getNamespace() + "publishButton" %>'
							label="<%= editKBArticleDisplayContext.getPublishButtonLabel() %>"
							name="publishButton"
							small="<%= true %>"
							type="submit"
						/>

						<clay:button
							borderless="<%= true %>"
							icon="cog"
							id='<%= liferayPortletResponse.getNamespace() + "contextualSidebarButton" %>'
							small="<%= true %>"
							title='<%= LanguageUtil.get(request, "configuration") %>'
							type="button"
						/>
					</div>
				</li>
			</ul>
		</clay:container-fluid>
	</nav>

	<div class="contextual-sidebar contextual-sidebar-visible sidebar-light sidebar-sm" id="<portlet:namespace />contextualSidebarContainer">
		<div class="sidebar-body">
			<liferay-ui:tabs
				names="properties"
				param="tabs1"
				refresh="<%= false %>"
			>
				<liferay-ui:section>
					<liferay-expando:custom-attributes-available
						className="<%= KBArticle.class.getName() %>"
					>
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
							<liferay-expando:custom-attribute-list
								className="<%= KBArticle.class.getName() %>"
								classPK="<%= editKBArticleDisplayContext.getKBArticleId() %>"
								editable="<%= true %>"
								label="<%= true %>"
							/>
						</aui:fieldset>
					</liferay-expando:custom-attributes-available>

					<c:if test="<%= editKBArticleDisplayContext.isNavigationBarVisible() %>">
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="basic-information">
							<p>
								<strong><liferay-ui:message key="version" /></strong>: <%= editKBArticleDisplayContext.getKBArticleVersion() %>

								<clay:label
									cssClass="ml-2 text-uppercase"
									displayType="<%= WorkflowConstants.getStatusStyle(editKBArticleDisplayContext.getKBArticleStatus()) %>"
									label="<%= WorkflowConstants.getStatusLabel(editKBArticleDisplayContext.getKBArticleStatus()) %>"
								/>
							</p>

							<p>
								<strong><liferay-ui:message key="id" /></strong>: <%= String.valueOf(editKBArticleDisplayContext.getResourcePrimKey()) %>
							</p>
						</aui:fieldset>
					</c:if>

					<c:if test='<%= GetterUtil.getBoolean(com.liferay.portal.kernel.util.PropsUtil.get("feature.flag.LPS-125653")) %>'>
						<liferay-frontend:fieldset
							collapsed="<%= true %>"
							collapsible="<%= true %>"
							label="display-page"
						>
							<liferay-asset:select-asset-display-page
								classNameId="<%= PortalUtil.getClassNameId(KBArticle.class) %>"
								classPK="<%= editKBArticleDisplayContext.getKBArticleId() %>"
								groupId="<%= scopeGroupId %>"
								showViewInContextLink="<%= true %>"
							/>
						</liferay-frontend:fieldset>
					</c:if>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
						<liferay-asset:asset-categories-selector
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= editKBArticleDisplayContext.getKBArticleClassPK() %>"
							visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
						/>

						<liferay-asset:asset-tags-selector
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= editKBArticleDisplayContext.getKBArticleClassPK() %>"
						/>
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
						<liferay-asset:input-asset-links
							className="<%= KBArticle.class.getName() %>"
							classPK="<%= editKBArticleDisplayContext.getKBArticleClassPK() %>"
						/>
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="configuration">
						<aui:field-wrapper cssClass="form-group" disabled="<%= editKBArticleDisplayContext.isURLTitleDisabled() %>" helpMessage='<%= LanguageUtil.format(request, "for-example-x", "<em>/introduction-to-service-builder</em>") %>' label="friendly-url" name="urlTitle">
							<span class="form-text"><%= editKBArticleDisplayContext.getURLTitlePrefix() %></span>

							<aui:input cssClass="input-medium" data-custom-url="<%= false %>" disabled="<%= editKBArticleDisplayContext.isURLTitleDisabled() %>" ignoreRequestValue="<%= true %>" label="" name="urlTitle" placeholder="sample-article-url-title" type="text" value="<%= editKBArticleDisplayContext.getKBArticleURLTitle() %>" />
						</aui:field-wrapper>

						<c:if test="<%= editKBArticleDisplayContext.isKBArticleDescriptionEnabled() %>">
							<aui:input name="description" value="<%= editKBArticleDisplayContext.getKBArticleDescription() %>" />
						</c:if>

						<c:if test="<%= editKBArticleDisplayContext.isSourceURLEnabled() %>">
							<aui:input label="source-url" name="sourceURL" value="<%= editKBArticleDisplayContext.getKBArticleSourceURL() %>" />
						</c:if>

						<c:if test="<%= editKBArticleDisplayContext.hasKBArticleSections() %>">
							<aui:model-context bean="<%= null %>" model="<%= KBArticle.class %>" />

							<aui:select ignoreRequestValue="<%= true %>" multiple="<%= true %>" name="sections">

								<%
								Map<String, String> availableSections = editKBArticleDisplayContext.getAvailableKBArticleSections();

								for (Map.Entry<String, String> entry : availableSections.entrySet()) {
								%>

									<aui:option label="<%= HtmlUtil.escape(entry.getKey()) %>" selected="<%= editKBArticleDisplayContext.isKBArticleSectionSelected(entry.getValue()) %>" value="<%= HtmlUtil.escape(entry.getValue()) %>" />

								<%
								}
								%>

							</aui:select>

							<aui:model-context bean="<%= editKBArticleDisplayContext.getKBArticle() %>" model="<%= KBArticle.class %>" />
						</c:if>
					</aui:fieldset>

					<c:if test="<%= editKBArticleDisplayContext.getKBArticle() == null %>">
						<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
							<liferay-ui:input-permissions
								modelName="<%= KBArticle.class.getName() %>"
								reverse="<%= true %>"
							/>
						</aui:fieldset>
					</c:if>
				</liferay-ui:section>
			</liferay-ui:tabs>
		</div>
	</div>

	<div class="contextual-sidebar-content">
		<div <%= editKBArticleDisplayContext.getFormCssClass() %>>
			<div class="lfr-form-content">
				<c:if test="<%= editKBArticleDisplayContext.isWorkflowStatusVisible() %>">
					<div class="text-center">
						<aui:workflow-status id="<%= String.valueOf(editKBArticleDisplayContext.getResourcePrimKey()) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= editKBArticleDisplayContext.getKBArticleStatus() %>" version="<%= editKBArticleDisplayContext.getKBArticleVersion() %>" />
					</div>
				</c:if>

				<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />
				<liferay-ui:error exception="<%= KBArticleStatusException.class %>" message="this-article-cannot-be-published-because-its-parent-has-not-been-published" />
				<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotBeDuplicate.class %>" message="please-enter-a-unique-friendly-url" />

				<liferay-ui:error exception="<%= FileSizeException.class %>">

					<%
					FileSizeException fileSizeException = (FileSizeException)errorException;
					%>

					<liferay-ui:message arguments="<%= fileSizeException.getMaxSize() / 1024 %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotContainInvalidCharacters.class %>" message="please-enter-a-friendly-url-that-starts-with-a-slash-and-contains-alphanumeric-characters-dashes-and-underscores" />

				<liferay-ui:error exception="<%= KBArticleUrlTitleException.MustNotExceedMaximumSize.class %>">

					<%
					int friendlyURLMaxLength = ModelHintsUtil.getMaxLength(KBArticle.class.getName(), "urlTitle");
					%>

					<liferay-ui:message arguments="<%= String.valueOf(friendlyURLMaxLength) %>" key="please-enter-a-friendly-url-with-fewer-than-x-characters" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= KBArticleContentException.class %>">
					<liferay-ui:message arguments='<%= ModelHintsUtil.getMaxLength(KBArticle.class.getName(), "urlTitle") %>' key="please-enter-valid-content" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= KBArticleSourceURLException.class %>" message="please-enter-a-valid-source-url" />
				<liferay-ui:error exception="<%= KBArticleTitleException.class %>" message="please-enter-a-valid-title" />
				<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="the-document-could-not-be-found" />

				<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
					<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(UploadServletRequestConfigurationHelperUtil.getMaxSize(), locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
				</liferay-ui:error>

				<liferay-asset:asset-categories-error />

				<liferay-asset:asset-tags-error />

				<c:choose>
					<c:when test="<%= editKBArticleDisplayContext.isApproved() %>">
						<div class="alert alert-info">
							<liferay-ui:message key="a-new-version-is-created-automatically-if-this-content-is-modified" />
						</div>
					</c:when>
					<c:when test="<%= editKBArticleDisplayContext.isPending() %>">
						<div class="alert alert-info">
							<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
						</div>
					</c:when>
				</c:choose>

				<aui:model-context bean="<%= editKBArticleDisplayContext.getKBArticle() %>" model="<%= KBArticle.class %>" />

				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<div class="kb-entity-body">
							<liferay-editor:editor
								contents="<%= editKBArticleDisplayContext.getContent() %>"
								editorName="<%= kbGroupServiceConfiguration.getEditorName() %>"
								fileBrowserParams='<%=
									HashMapBuilder.put(
										"resourcePrimKey", String.valueOf(editKBArticleDisplayContext.getResourcePrimKey())
									).build()
								%>'
								name="contentEditor"
								placeholder="content"
								required="<%= true %>"
							>
								<aui:validator name="required" />
							</liferay-editor:editor>

							<aui:input name="content" type="hidden" />
						</div>
					</aui:fieldset>

					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="attachments">
						<div id="<portlet:namespace />attachments">
							<liferay-util:include page="/admin/common/attachments.jsp" servletContext="<%= application %>" />
						</div>
					</aui:fieldset>
				</aui:fieldset-group>
			</div>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"kbArticle", editKBArticleDisplayContext.getKBArticle()
		).put(
			"publishAction", WorkflowConstants.ACTION_PUBLISH
		).build()
	%>'
	module="admin/js/EditKBArticle"
/>