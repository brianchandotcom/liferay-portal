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
String redirect = ParamUtil.getString(request, "redirect");

String portletResource = ParamUtil.getString(request, "portletResource");
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String title = BeanParamUtil.getString(entry, request, "title");
String subtitle = BeanParamUtil.getString(entry, request, "subtitle");
String content = BeanParamUtil.getString(entry, request, "content");
String urlTitle = BeanParamUtil.getString(entry, request, "urlTitle");

String description = BeanParamUtil.getString(entry, request, "description");

boolean customAbstract = ParamUtil.getBoolean(request, "customAbstract", (entry != null) && Validator.isNotNull(entry.getDescription()) ? true : false);

if (!customAbstract) {
	description = StringUtil.shorten(content, PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH);
}

boolean allowPingbacks = PropsValues.BLOGS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowPingbacks", true);
boolean allowTrackbacks = PropsValues.BLOGS_TRACKBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
String coverImageCaption = BeanParamUtil.getString(entry, request, "coverImageCaption", LanguageUtil.get(request, "caption"));
long coverImageFileEntryId = BeanParamUtil.getLong(entry, request, "coverImageFileEntryId");
long smallImageFileEntryId = BeanParamUtil.getLong(entry, request, "smallImageFileEntryId");

BlogsFileUploadsConfiguration blogsFileUploadsConfiguration = ConfigurationProviderUtil.getSystemConfiguration(BlogsFileUploadsConfiguration.class);
BlogsGroupServiceSettings blogsGroupServiceSettings = BlogsGroupServiceSettings.getInstance(scopeGroupId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((entry != null) ? BlogsEntryUtil.getDisplayTitle(resourceBundle, entry) : LanguageUtil.get(request, "new-blog-entry"));
%>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" />

<clay:container-fluid
	cssClass="container-form-lg entry-body"
>
	<aui:form action="<%= editEntryURL %>" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="portletResource" type="hidden" value="<%= portletResource %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<div class="lfr-form-content">
			<liferay-ui:error exception="<%= DuplicateFriendlyURLEntryException.class %>" message="the-url-title-is-already-in-use-please-enter-a-unique-url-title" />
			<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
			<liferay-ui:error exception="<%= EntryCoverImageCropException.class %>" message="an-error-occurred-while-cropping-the-cover-image" />

			<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(blogsFileUploadsConfiguration.imageExtensions()) %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= EntryDescriptionException.class %>" message="please-enter-a-valid-abstract" />
			<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />
			<liferay-ui:error exception="<%= EntryUrlTitleException.class %>" message="please-enter-a-valid-url-title" />

			<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
				<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
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

			<%
			BlogsItemSelectorHelper blogsItemSelectorHelper = (BlogsItemSelectorHelper)request.getAttribute(BlogsWebKeys.BLOGS_ITEM_SELECTOR_HELPER);
			RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest);
			%>

			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<portlet:actionURL name="/blogs/upload_cover_image" var="uploadCoverImageURL" />

					<div class="lfr-blogs-cover-image-selector">

						<%
						String coverImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "coverImageSelectedItem";
						%>

						<liferay-item-selector:image-selector
							draggableImage="vertical"
							fileEntryId="<%= coverImageFileEntryId %>"
							itemSelectorEventName="<%= coverImageSelectedItemEventName %>"
							itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, coverImageSelectedItemEventName) %>"
							maxFileSize="<%= blogsFileUploadsConfiguration.imageMaxSize() %>"
							paramName="coverImageFileEntry"
							uploadURL="<%= uploadCoverImageURL %>"
							validExtensions="<%= StringUtil.merge(blogsFileUploadsConfiguration.imageExtensions()) %>"
						/>
					</div>

					<aui:input name="coverImageCaption" type="hidden" />

					<clay:col
						cssClass="mx-md-auto"
						md="8"
					>
						<div class="cover-image-caption <%= (coverImageFileEntryId == 0) ? "invisible" : "" %>">
							<small>
								<liferay-editor:editor
									contents="<%= coverImageCaption %>"
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

							<aui:input autoSize="<%= true %>" cssClass="form-control-edit form-control-edit-title form-control-unstyled" label="" maxlength="<%= String.valueOf(titleMaxLength) %>" name="title" placeholder='<%= LanguageUtil.get(request, "title") + " *" %>' required="<%= true %>" showRequiredLabel="<%= true %>" type="textarea" value="<%= HtmlUtil.escape(title) %>" />
						</div>

						<div class="entry-subtitle">
							<aui:input autoSize="<%= true %>" cssClass="form-control-edit form-control-edit-subtitle form-control-unstyled" label="" name="subtitle" placeholder='<%= LanguageUtil.get(request, "subtitle") %>' type="textarea" />
						</div>

						<div class="entry-content form-group">
							<liferay-editor:editor
								contents="<%= content %>"
								editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp") %>'
								name="contentEditor"
								onChangeMethod="onChangeContentEditor"
								placeholder="content"
								required="<%= true %>"
							>
								<aui:validator name="required" />
							</liferay-editor:editor>
						</div>

						<aui:input name="content" type="hidden" />
					</clay:col>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="categorization">
					<liferay-asset:asset-categories-selector
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entryId %>"
						visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
					/>

					<liferay-asset:asset-tags-selector
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entryId %>"
					/>
				</aui:fieldset>

				<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="related-assets">
					<liferay-asset:input-asset-links
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entryId %>"
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
							automaticURL = Validator.isNull(urlTitle);
						}
						else {
							String uniqueUrlTitle = BlogsEntryLocalServiceUtil.getUniqueUrlTitle(entry);

							automaticURL = uniqueUrlTitle.equals(urlTitle);
						}
						%>

						<label><liferay-ui:message key="url" /></label>

						<div class="form-group" id="<portlet:namespace />urlOptions">
							<aui:input checked="<%= automaticURL %>" helpMessage="the-url-will-be-based-on-the-entry-title" label="automatic" name="automaticURL" type="radio" value="<%= true %>" />

							<aui:input checked="<%= !automaticURL %>" label="custom" name="automaticURL" type="radio" value="<%= false %>" />
						</div>

						<liferay-friendly-url:input
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entryId %>"
							disabled="<%= automaticURL %>"
							inputAddon='<%= StringUtil.shorten("/-/" + portlet.getFriendlyURLMapping(), 40) + StringPool.SLASH %>'
							localizable="<%= false %>"
							name="urlTitle"
						/>
					</div>

					<div class="clearfix form-group">
						<label><liferay-ui:message key="abstract" /> <liferay-ui:icon-help message="an-abstract-is-a-brief-summary-of-a-blog-entry" /></label>

						<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
							<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(blogsFileUploadsConfiguration.imageExtensions()) %>.
						</liferay-ui:error>

						<liferay-ui:error exception="<%= EntrySmallImageScaleException.class %>">
							<liferay-ui:message key="an-error-occurred-while-scaling-the-abstract-image" />
						</liferay-ui:error>

						<div class="form-group" id="<portlet:namespace />entryAbstractOptions">
							<aui:input checked="<%= !customAbstract %>" label='<%= LanguageUtil.format(request, "use-the-first-x-characters-of-the-entry-content", PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH, false) %>' name="customAbstract" type="radio" value="<%= false %>" />

							<aui:input checked="<%= customAbstract %>" label="custom-abstract" name="customAbstract" type="radio" value="<%= true %>" />
						</div>

						<div class="entry-description form-group">
							<aui:input disabled="<%= !customAbstract %>" label="description" name="description" type="text" value="<%= description %>">
								<aui:validator name="required" />
							</aui:input>
						</div>

						<portlet:actionURL name="/blogs/upload_small_image" var="uploadSmallImageURL" />

						<div class="clearfix">
							<label class="control-label"><liferay-ui:message key="small-image" /></label>
						</div>

						<div class="lfr-blogs-small-image-selector">
							<c:if test="<%= entry != null %>">
								<aui:input name="smallImageURL" type="hidden" value="<%= entry.getSmallImageURL() %>" />
							</c:if>

							<%
							String smallImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "smallImageSelectedItem";
							%>

							<liferay-item-selector:image-selector
								fileEntryId="<%= smallImageFileEntryId %>"
								itemSelectorEventName="<%= smallImageSelectedItemEventName %>"
								itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, smallImageSelectedItemEventName) %>"
								maxFileSize="<%= blogsFileUploadsConfiguration.imageMaxSize() %>"
								paramName="smallImageFileEntry"
								uploadURL="<%= uploadSmallImageURL %>"
								validExtensions="<%= StringUtil.merge(blogsFileUploadsConfiguration.imageExtensions()) %>"
							/>
						</div>
					</div>

					<aui:input label="display-date" name="displayDate" />

					<c:if test="<%= (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>">
						<aui:input helpMessage="comments-regarding-the-blog-entry-update" inlineLabel="right" label="send-email-entry-updated" labelCssClass="simple-toggle-switch" name="sendEmailEntryUpdated" type="toggle-switch" value='<%= ParamUtil.getBoolean(request, "sendEmailEntryUpdated") %>' />

						<div id="<portlet:namespace />emailEntryUpdatedCommentWrapper">
							<aui:input label="" name="emailEntryUpdatedComment" type="textarea" value='<%= ParamUtil.getString(request, "emailEntryUpdatedComment") %>' />
						</div>
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_PINGBACK_ENABLED %>">
						<aui:input helpMessage='<%= LanguageUtil.get(resourceBundle, "a-pingback-is-a-comment-that-is-created-when-you-link-to-another-blog-post-where-pingbacks-are-enabled") + " " + LanguageUtil.get(resourceBundle, "to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled") %>' inlineLabel="right" label="allow-pingbacks" labelCssClass="simple-toggle-switch" name="allowPingbacks" type="toggle-switch" value="<%= allowPingbacks %>" />
					</c:if>

					<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
						<aui:input helpMessage="to-allow-trackbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" inlineLabel="right" label="allow-trackbacks" labelCssClass="simple-toggle-switch" name="allowTrackbacks" type="toggle-switch" value="<%= allowTrackbacks %>" />

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
							classPK="<%= entryId %>"
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
							<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
						</div>
					</div>
				</div>
			</aui:fieldset-group>
		</div>
	</aui:form>
</clay:container-fluid>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="ajax" value="<%= Boolean.TRUE.toString() %>" />
</portlet:actionURL>

<%
Map<String, Object> taglibContext = HashMapBuilder.<String, Object>put(
	"constants",
	HashMapBuilder.<String, Object>put(
		"ACTION_PUBLISH", WorkflowConstants.ACTION_PUBLISH
	).put(
		"ACTION_SAVE_DRAFT", WorkflowConstants.ACTION_SAVE_DRAFT
	).put(
		"ADD", Constants.ADD
	).put(
		"CMD", Constants.CMD
	).put(
		"STATUS_DRAFT", WorkflowConstants.STATUS_DRAFT
	).put(
		"UPDATE", Constants.UPDATE
	).build()
).put(
	"descriptionLength", PropsValues.BLOGS_PAGE_ABSTRACT_LENGTH
).put(
	"editEntryURL", editEntryURL
).put(
	"emailEntryUpdatedEnabled", (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled()
).build();

if (entry != null) {
	taglibContext.put(
		"entry",
		HashMapBuilder.<String, Object>put(
			"content", UnicodeFormatter.toString(content)
		).put(
			"customDescription", customAbstract
		).put(
			"description", description
		).put(
			"pending", entry.isPending()
		).put(
			"status", entry.getStatus()
		).put(
			"subtitle", subtitle
		).put(
			"title", title
		).put(
			"userId", entry.getUserId()
		).build());
}
%>

<liferay-frontend:component
	context="<%= taglibContext %>"
	module="blogs/js/blogs"
	servletContext="<%= application %>"
/>

<%
if (entry != null) {
	PortalUtil.addPortletBreadcrumbEntry(
		request, BlogsEntryUtil.getDisplayTitle(resourceBundle, entry),
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCRenderCommandName(
			"/blogs/view_entry"
		).setParameter(
			"entryId", entry.getEntryId()
		).buildString());

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-entry"), currentURL);
}
%>