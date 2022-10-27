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

<%@ include file="/blogs/init.jsp" %>

<%
BlogsEditEntryDisplayContext blogsEditEntryDisplayContext = (BlogsEditEntryDisplayContext)request.getAttribute(BlogsEditEntryDisplayContext.class.getName());

BlogsEntry entry = blogsEditEntryDisplayContext.getBlogsEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(blogsEditEntryDisplayContext.getRedirect());

renderResponse.setTitle(blogsEditEntryDisplayContext.getPageTitle(resourceBundle));
%>

<clay:container-fluid
	cssClass="container-form-lg entry-body"
>
	<aui:form action="<%= blogsEditEntryDisplayContext.getEditEntryURL() %>" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= blogsEditEntryDisplayContext.getReferringPortletResource() %>" />
		<aui:input name="entryId" type="hidden" value="<%= blogsEditEntryDisplayContext.getEntryId() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<div class="lfr-form-content">
			<liferay-ui:error exception="<%= DuplicateFriendlyURLEntryException.class %>" message="the-url-title-is-already-in-use-please-enter-a-unique-url-title" />
			<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
			<liferay-ui:error exception="<%= EntryCoverImageCropException.class %>" message="an-error-occurred-while-cropping-the-cover-image" />

			<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(blogsEditEntryDisplayContext.getImageExtensions()) %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= EntryDescriptionException.class %>" message="please-enter-a-valid-abstract" />
			<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
			<liferay-ui:error exception="<%= EntryUrlTitleException.class %>" message="please-enter-a-valid-url-title" />

			<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
				<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(FileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileSizeException.class %>">

				<%
				FileSizeException fileSizeException = (FileSizeException)errorException;
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(fileSizeException.getMaxSize(), locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= ImageResolutionException.class %>">
				<liferay-ui:message arguments="<%= new String[] {String.valueOf(PropsValues.IMAGE_TOOL_IMAGE_MAX_HEIGHT), String.valueOf(PropsValues.IMAGE_TOOL_IMAGE_MAX_WIDTH)} %>" key="image-dimensions-exceed-max-dimensions-x-high-x-wide" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
				<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(UploadServletRequestConfigurationHelperUtil.getMaxSize(), locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-asset:asset-categories-error />

			<liferay-asset:asset-tags-error />

			<aui:model-context bean="<%= entry %>" model="<%= BlogsEntry.class %>" />

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="lfr-blogs-cover-image-selector">
						<liferay-item-selector:image-selector
							draggableImage="vertical"
							fileEntryId="<%= blogsEditEntryDisplayContext.getCoverImageFileEntryId() %>"
							itemSelectorEventName="<%= blogsEditEntryDisplayContext.getCoverImageItemSelectorEventName() %>"
							itemSelectorURL="<%= blogsEditEntryDisplayContext.getCoverImageItemSelectorURL() %>"
							maxFileSize="<%= blogsEditEntryDisplayContext.getImageMaxSize() %>"
							paramName="coverImageFileEntry"
							uploadURL="<%= blogsEditEntryDisplayContext.getUploadCoverImageURL() %>"
							validExtensions="<%= StringUtil.merge(blogsEditEntryDisplayContext.getImageExtensions()) %>"
						/>
					</div>

					<aui:input name="coverImageCaption" type="hidden" />

					<clay:col
						cssClass="mx-md-auto"
						md="8"
					>
						<div class="cover-image-caption <%= (blogsEditEntryDisplayContext.getCoverImageFileEntryId() == 0) ? "invisible" : "" %>">
							<small>
								<liferay-editor:editor
									contents="<%= blogsEditEntryDisplayContext.getCoverImageCaption() %>"
									editorName="ballooneditor"
									name="coverImageCaptionEditor"
									placeholder="caption"
								/>
							</small>
						</div>
					</clay:col>

					<clay:col
						cssClass="mx-md-auto"
						md="8"
					>
						<div class="entry-title form-group">

							<%
							int titleMaxLength = ModelHintsUtil.getMaxLength(BlogsEntry.class.getName(), "title");
							%>

							<aui:input autoSize="<%= true %>" cssClass="form-control-edit form-control-edit-title form-control-unstyled" label="" maxlength="<%= String.valueOf(titleMaxLength) %>" name="title" placeholder='<%= LanguageUtil.get(request, "title") + " *" %>' required="<%= true %>" showRequiredLabel="<%= true %>" type="textarea" value="<%= HtmlUtil.escape(blogsEditEntryDisplayContext.getTitle()) %>" />
						</div>

						<div class="entry-subtitle">
							<aui:input autoSize="<%= true %>" cssClass="form-control-edit form-control-edit-subtitle form-control-unstyled" label="" name="subtitle" placeholder='<%= LanguageUtil.get(request, "subtitle") %>' type="textarea" />
						</div>

						<div class="entry-content form-group">
							<liferay-editor:editor
								contents="<%= blogsEditEntryDisplayContext.getContent() %>"
								editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp") %>'
								name="contentEditor"
								onChangeMethod="onChangeContentEditor"
								placeholder="content"
								required="<%= true %>"
							>
								<aui:validator errorMessage='<%= LanguageUtil.format(resourceBundle, "the-x-field-is-required", "content", true) %>' name="required" />
							</liferay-editor:editor>
						</div>

						<aui:input name="content" type="hidden" />
					</clay:col>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
					<liferay-asset:asset-categories-selector
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= blogsEditEntryDisplayContext.getEntryId() %>"
						visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
					/>

					<liferay-asset:asset-tags-selector
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= blogsEditEntryDisplayContext.getEntryId() %>"
					/>

					<c:if test="<%= blogsEditEntryDisplayContext.isAutoTaggingEnabled() %>">
						<clay:checkbox
							checked="<%= blogsEditEntryDisplayContext.isUpdateAutoTags() %>"
							id='<%= liferayPortletResponse.getNamespace() + "updateAutoTags" %>'
							label='<%= LanguageUtil.get(request, "update-auto-tags") %>'
							name='<%= liferayPortletResponse.getNamespace() + "updateAutoTags" %>'
						/>

						<div class="ml-4">
							<small class="text-secondary">
								<liferay-ui:message key="update-auto-tags-help" />
							</small>
						</div>
					</c:if>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
					<liferay-asset:input-asset-links
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= blogsEditEntryDisplayContext.getEntryId() %>"
					/>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="configuration">

					<%
					Portlet portlet = PortletLocalServiceUtil.getPortletById(BlogsPortletKeys.BLOGS);
					%>

					<div class="clearfix form-group">

						<%
						boolean automaticURL;

						if (entry == null) {
							automaticURL = Validator.isNull(blogsEditEntryDisplayContext.getURLTitle());
						}
						else {
							String uniqueUrlTitle = BlogsEntryLocalServiceUtil.getUniqueUrlTitle(entry);

							automaticURL = uniqueUrlTitle.equals(blogsEditEntryDisplayContext.getURLTitle());
						}
						%>

						<label><liferay-ui:message key="url" /></label>

						<div class="form-group" id="<portlet:namespace />urlOptions">
							<aui:input checked="<%= automaticURL %>" helpMessage="the-url-will-be-based-on-the-entry-title" label="automatic" name="automaticURL" type="radio" value="<%= true %>" />

							<aui:input checked="<%= !automaticURL %>" label="custom" name="automaticURL" type="radio" value="<%= false %>" />
						</div>

						<liferay-friendly-url:input
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= blogsEditEntryDisplayContext.getEntryId() %>"
							disabled="<%= automaticURL %>"
							inputAddon='<%= StringUtil.shorten("/-/" + portlet.getFriendlyURLMapping(), 40) + StringPool.SLASH %>'
							localizable="<%= false %>"
							name="urlTitle"
						/>
					</div>

					<div class="clearfix form-group">
						<label><liferay-ui:message key="abstract" /> <liferay-ui:icon-help message="an-abstract-is-a-brief-summary-of-a-blog-entry" /></label>

						<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
							<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(blogsEditEntryDisplayContext.getImageExtensions()) %>.
						</liferay-ui:error>

						<liferay-ui:error exception="<%= EntrySmallImageScaleException.class %>">
							<liferay-ui:message key="an-error-occurred-while-scaling-the-abstract-image" />
						</liferay-ui:error>

						<div class="form-group" id="<portlet:namespace />entryAbstractOptions">
							<aui:input checked="<%= !blogsEditEntryDisplayContext.isCustomAbstract() %>" label='<%= LanguageUtil.format(request, "use-the-first-x-characters-of-the-entry-content", PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH, false) %>' name="customAbstract" type="radio" value="<%= false %>" />

							<aui:input checked="<%= blogsEditEntryDisplayContext.isCustomAbstract() %>" label="custom-abstract" name="customAbstract" type="radio" value="<%= true %>" />
						</div>

						<div class="entry-description form-group">
							<aui:input disabled="<%= !blogsEditEntryDisplayContext.isCustomAbstract() %>" label="description" name="description" type="text" value="<%= blogsEditEntryDisplayContext.getDescription() %>">
								<aui:validator name="required" />
							</aui:input>
						</div>

						<div class="clearfix">
							<label class="control-label"><liferay-ui:message key="small-image" /></label>
						</div>

						<div class="lfr-blogs-small-image-selector">
							<c:if test="<%= entry != null %>">
								<aui:input name="smallImageURL" type="hidden" value="<%= entry.getSmallImageURL() %>" />
							</c:if>

							<liferay-item-selector:image-selector
								fileEntryId="<%= blogsEditEntryDisplayContext.getSmallImageFileEntryId() %>"
								itemSelectorEventName="<%= blogsEditEntryDisplayContext.getSmallImageItemSelectorEventName() %>"
								itemSelectorURL="<%= blogsEditEntryDisplayContext.getSmallImageItemSelectorURL() %>"
								maxFileSize="<%= blogsEditEntryDisplayContext.getImageMaxSize() %>"
								paramName="smallImageFileEntry"
								uploadURL="<%= blogsEditEntryDisplayContext.getUploadSmallImageURL() %>"
								validExtensions="<%= StringUtil.merge(blogsEditEntryDisplayContext.getImageExtensions()) %>"
							/>
						</div>
					</div>

					<aui:input label="display-date" name="displayDate" />

					<c:if test="<%= blogsEditEntryDisplayContext.isEmailEntryUpdatedEnabled() %>">
						<aui:input helpMessage="comments-regarding-the-blog-entry-update" inlineLabel="right" label="send-email-entry-updated" labelCssClass="simple-toggle-switch" name="sendEmailEntryUpdated" type="toggle-switch" value='<%= ParamUtil.getBoolean(request, "sendEmailEntryUpdated") %>' />

						<div id="<portlet:namespace />emailEntryUpdatedCommentWrapper">
							<aui:input label="" name="emailEntryUpdatedComment" type="textarea" value='<%= ParamUtil.getString(request, "emailEntryUpdatedComment") %>' />
						</div>
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_PINGBACK_ENABLED %>">
						<aui:input helpMessage='<%= LanguageUtil.get(resourceBundle, "a-pingback-is-a-comment-that-is-created-when-you-link-to-another-blog-post-where-pingbacks-are-enabled") + " " + LanguageUtil.get(resourceBundle, "to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled") %>' inlineLabel="right" label="allow-pingbacks" labelCssClass="simple-toggle-switch" name="allowPingbacks" type="toggle-switch" value="<%= blogsEditEntryDisplayContext.isAllowPingbacks() %>" />
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
						<aui:input helpMessage="to-allow-trackbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" inlineLabel="right" label="allow-trackbacks" labelCssClass="simple-toggle-switch" name="allowTrackbacks" type="toggle-switch" value="<%= blogsEditEntryDisplayContext.isAllowTrackbacks() %>" />

						<aui:input label="trackbacks-to-send" name="trackbacks" />

						<c:if test="<%= (entry != null) && Validator.isNotNull(entry.getTrackbacks()) %>">

							<%
							int i = 0;

							for (String trackback : StringUtil.split(entry.getTrackbacks())) {
							%>

								<aui:input label="" name='<%= "trackback" + i++ %>' title="" type="resource" value="<%= trackback %>" />

							<%
							}
							%>

						</c:if>
					</c:if>
				</aui:fieldset>

				<%
				Group scopeGroup = themeDisplay.getScopeGroup();
				%>

				<c:if test="<%= !scopeGroup.isCompany() %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="display-page">
						<liferay-asset:select-asset-display-page
							classNameId="<%= PortalUtil.getClassNameId(BlogsEntry.class) %>"
							classPK="<%= (entry != null) ? entry.getEntryId() : 0 %>"
							groupId="<%= scopeGroupId %>"
							showPortletLayouts="<%= false %>"
							showViewInContextLink="<%= true %>"
						/>
					</aui:fieldset>
				</c:if>

				<liferay-expando:custom-attributes-available
					className="<%= BlogsEntry.class.getName() %>"
				>
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="custom-fields">
						<liferay-expando:custom-attribute-list
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= blogsEditEntryDisplayContext.getEntryId() %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</aui:fieldset>
				</liferay-expando:custom-attributes-available>

				<c:if test="<%= (entry == null) || (entry.getStatus() == WorkflowConstants.STATUS_DRAFT) %>">
					<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
						<liferay-ui:input-permissions
							modelName="<%= BlogsEntry.class.getName() %>"
						/>
					</aui:fieldset>
				</c:if>

				<%
				boolean pending = false;

				if (entry != null) {
					pending = entry.isPending();
				}
				%>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>

				<c:if test="<%= (entry != null) && entry.isApproved() && WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class.getName()) %>">
					<div class="alert alert-info">
						<liferay-ui:message arguments="<%= ResourceActionsUtil.getModelResource(locale, BlogsEntry.class.getName()) %>" key="this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again" translateArguments="<%= false %>" />
					</div>
				</c:if>

				<div class="blog-article-button-row sheet-footer">

					<%
					String saveButtonLabel = "save";

					if ((entry == null) || entry.isDraft() || entry.isApproved()) {
						saveButtonLabel = "save-as-draft";
					}

					String publishButtonLabel = "publish";

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, BlogsEntry.class.getName())) {
						publishButtonLabel = "submit-for-publication";
					}
					%>

					<div class="btn-group">
						<div class="btn-group-item">
							<aui:button disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />
						</div>

						<div class="btn-group-item">
							<aui:button name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />
						</div>

						<div class="btn-group-item">
							<aui:button href="<%= blogsEditEntryDisplayContext.getRedirect() %>" name="cancelButton" type="cancel" />
						</div>
					</div>
				</div>
			</aui:fieldset-group>
		</div>
	</aui:form>
</clay:container-fluid>

<liferay-frontend:component
	context="<%= blogsEditEntryDisplayContext.getTaglibContext() %>"
	module="blogs/js/blogs"
	servletContext="<%= application %>"
/>

<%
if (entry != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, BlogsEntryUtil.getDisplayTitle(resourceBundle, entry), blogsEditEntryDisplayContext.getViewEntryURL());

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-entry"), currentURL);
}
%>