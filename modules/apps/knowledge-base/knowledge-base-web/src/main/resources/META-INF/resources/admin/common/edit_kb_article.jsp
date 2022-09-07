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

<c:if test="<%= editKBArticleDisplayContext.isNavigationBarVisible() %>">
	<div class="management-bar management-bar-light navbar navbar-expand-md">
		<clay:container-fluid>
			<ul class="m-auto navbar-nav"></ul>

			<ul class="middle navbar-nav">
				<li class="nav-item">
					<aui:workflow-status id="<%= String.valueOf(editKBArticleDisplayContext.getResourcePrimKey()) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= editKBArticleDisplayContext.getKBArticleStatus() %>" version="<%= editKBArticleDisplayContext.getKBArticleVersion() %>" />
				</li>
			</ul>

			<ul class="end m-auto navbar-nav"></ul>
		</clay:container-fluid>
	</div>
</c:if>

<c:if test="<%= !editKBArticleDisplayContext.isHeaderVisible() %>">
	<liferay-ui:header
		backURL="<%= editKBArticleDisplayContext.getRedirect() %>"
		localizeTitle="<%= false %>"
		title="<%= editKBArticleDisplayContext.getHeaderTitle() %>"
	/>
</c:if>

<div <%= editKBArticleDisplayContext.getFormCssClass() %>>
	<aui:form action="<%= editKBArticleDisplayContext.getUpdateKBArticleURL() %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= editKBArticleDisplayContext.getRedirect() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

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
					<h1 class="kb-title">
						<aui:input autocomplete="off" label='<%= LanguageUtil.get(request, "title") %>' name="title" required="<%= true %>" type="text" value="<%= HtmlUtil.escape(editKBArticleDisplayContext.getKBArticleTitle()) %>" />
					</h1>

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
					<aui:input cssClass="input-medium" data-custom-url="<%= false %>" disabled="<%= editKBArticleDisplayContext.isURLTitleDisabled() %>" helpMessage='<%= LanguageUtil.format(request, "for-example-x", "<em>/introduction-to-service-builder</em>") %>' ignoreRequestValue="<%= true %>" label="friendly-url" name="urlTitle" placeholder="sample-article-url-title" prefix="<%= editKBArticleDisplayContext.getURLTitlePrefix() %>" type="text" value="<%= editKBArticleDisplayContext.getKBArticleURLTitle() %>" />

					<c:if test="<%= editKBArticleDisplayContext.isKBArticleDescriptionEnabled() %>">
						<aui:input name="description" />
					</c:if>

					<c:if test="<%= editKBArticleDisplayContext.isSourceURLEnabled() %>">
						<aui:input label="source-url" name="sourceURL" />
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
						/>
					</aui:fieldset>
				</c:if>

				<div class="kb-submit-buttons sheet-footer">
					<aui:button disabled="<%= editKBArticleDisplayContext.isPending() %>" name="publishButton" type="submit" value="<%= editKBArticleDisplayContext.getPublishButtonLabel() %>" />

					<aui:button primary="<%= false %>" type="submit" value="<%= editKBArticleDisplayContext.getSaveButtonLabel() %>" />

					<aui:button href="<%= editKBArticleDisplayContext.getRedirect() %>" type="cancel" />
				</div>
			</aui:fieldset-group>
		</div>
	</aui:form>
</div>

<script>
	<c:if test="<%= editKBArticleDisplayContext.getKBArticle() == null %>">
		var titleInput = document.getElementById('<portlet:namespace />title');
		var urlTitleInput = document.getElementById('<portlet:namespace />urlTitle');

		titleInput.addEventListener('input', (event) => {
			var customUrl = urlTitleInput.dataset.customUrl;

			if (customUrl === 'false') {
				var title = event.target.value;

				urlTitleInput.value = Liferay.Util.normalizeFriendlyURL(title);
			}
		});

		urlTitleInput.addEventListener('input', (event) => {
			event.currentTarget.dataset.customUrl = urlTitleInput.value !== '';
		});
	</c:if>

	document
		.getElementById('<portlet:namespace />publishButton')
		.addEventListener('click', () => {
			var workflowActionInput = document.getElementById(
				'<portlet:namespace />workflowAction'
			);

			if (workflowActionInput) {
				workflowActionInput.value =
					'<%= WorkflowConstants.ACTION_PUBLISH %>';
			}

			<c:if test="<%= editKBArticleDisplayContext.getKBArticle() == null %>">
				var customUrl = urlTitleInput.dataset.customUrl;

				if (customUrl === 'false') {
					urlTitleInput.value = '';
				}
			</c:if>
		});

	var form = document.getElementById('<portlet:namespace />fm');

	var updateMultipleKBArticleAttachments = function () {
		var selectedFileNameContainer = document.getElementById(
			'<portlet:namespace />selectedFileNameContainer'
		);
		var buffer = [];
		var filesChecked = form.querySelectorAll(
			'input[name=<portlet:namespace />selectUploadedFile]:checked'
		);

		for (var i = 0; i < filesChecked.length; i++) {
			buffer.push(
				'<input id="<portlet:namespace />selectedFileName' +
					i +
					'" name="<portlet:namespace />selectedFileName" type="hidden" value="' +
					filesChecked[i].value +
					'" />'
			);
		}

		selectedFileNameContainer.innerHTML = buffer.join('');
	};

	form.addEventListener('submit', () => {
		document.getElementById(
			'<portlet:namespace />content'
		).value = window.<portlet:namespace />contentEditor.getHTML();
		updateMultipleKBArticleAttachments();
	});
</script>