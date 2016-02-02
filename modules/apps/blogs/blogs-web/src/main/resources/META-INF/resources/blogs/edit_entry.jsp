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
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String title = BeanParamUtil.getString(entry, request, "title");
String subtitle = BeanParamUtil.getString(entry, request, "subtitle");
String content = BeanParamUtil.getString(entry, request, "content");

String description = BeanParamUtil.getString(entry, request, "description");

boolean customAbstract = ParamUtil.getBoolean(request, "customAbstract", (entry != null) && Validator.isNotNull(entry.getDescription()) ? true : false);

if (!customAbstract) {
	description = StringUtil.shorten(content, pageAbstractLength);
}

boolean allowPingbacks = PropsValues.BLOGS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowPingbacks", true);
boolean allowTrackbacks = PropsValues.BLOGS_TRACKBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
String coverImageCaption = BeanParamUtil.getString(entry, request, "coverImageCaption");
long coverImageFileEntryId = BeanParamUtil.getLong(entry, request, "coverImageFileEntryId");
long smallImageFileEntryId = BeanParamUtil.getLong(entry, request, "smallImageFileEntryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((entry != null) ? entry.getTitle() : LanguageUtil.get(request, "new-blog-entry"));
%>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" />

<div class="container-fluid-1280">
	<aui:form action="<%= editEntryURL %>" cssClass="edit-entry" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
		<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

		<div class="entry-options">
			<c:if test="<%= (entry != null) && entry.isApproved() %>">
				<div class="status">
					<small class="text-capitalize text-muted">
						<%= WorkflowConstants.getStatusLabel(entry.getStatus()) %>

						<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - entry.getStatusDate().getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
					</small>
				</div>
			</c:if>
			<c:if test="<%= (entry == null) || !entry.isApproved() %>">
				<div class="save-status" id="<portlet:namespace />saveStatus"></div>
			</c:if>

			<aui:button cssClass="hide icon-monospaced" icon="icon-pencil" id="editIcon" />

			<aui:button cssClass="icon-monospaced" icon="icon-cog" id="settingsIcon" />
		</div>

		<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
		<liferay-ui:error exception="<%= EntryCoverImageCropException.class %>" message="an-error-occurred-while-cropping-the-cover-image" />
		<liferay-ui:error exception="<%= EntryDescriptionException.class %>" message="please-enter-a-valid-abstract" />
		<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />

		<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<%
		long uploadServletRequestImplMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		%>

		<liferay-ui:error exception="<%= FileSizeException.class %>">

			<%
			long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

			if (fileMaxSize == 0) {
				fileMaxSize = uploadServletRequestImplMaxSize;
			}
			%>

			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(uploadServletRequestImplMaxSize, locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:asset-categories-error />

		<liferay-ui:asset-tags-error />

		<aui:model-context bean="<%= entry %>" model="<%= BlogsEntry.class %>" />

		<%
		String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);
		%>

		<div class="<%= (entry != null) ? "entry-body " : StringPool.BLANK %> row">
			<div id="<portlet:namespace />editSection">
				<portlet:actionURL name="/blogs/upload_cover_image" var="uploadCoverImageURL" />

				<div class="lfr-blogs-cover-image-selector">

					<%
					String coverImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "coverImageSelectedItem";
					BlogsItemSelectorHelper blogsItemSelectorHelper = (BlogsItemSelectorHelper)request.getAttribute(BlogsWebKeys.BLOGS_ITEM_SELECTOR_HELPER);
					RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil.create(liferayPortletRequest);
					%>

					<liferay-item-selector:image-selector draggableImage="vertical" fileEntryId="<%= coverImageFileEntryId %>" itemSelectorEventName="<%= coverImageSelectedItemEventName %>" itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, coverImageSelectedItemEventName) %>" paramName="coverImageFileEntry" uploadURL="<%= uploadCoverImageURL %>" validExtensions='<%= StringUtil.merge(imageExtensions, ", ") %>' />
				</div>

				<aui:input name="coverImageCaption" type="hidden" />

				<div class="cover-image-caption <%= (coverImageFileEntryId == 0) ? "invisible" : "" %>">
					<small>
						<liferay-ui:input-editor contents="<%= coverImageCaption %>" editorName="alloyeditor" name="coverImageCaptionEditor" placeholder="caption" showSource="<%= false %>" />
					</small>
				</div>

				<div class="col-md-8 col-md-offset-2">
					<div class="entry-title">
						<h1><liferay-ui:input-editor contents="<%= HtmlUtil.escape(title) %>" editorName="alloyeditor" name="titleEditor" placeholder="title" showSource="<%= false %>" /></h1>
					</div>

					<aui:input name="title" type="hidden" />

					<div class="entry-subtitle">
						<h4><liferay-ui:input-editor contents="<%= HtmlUtil.escape(subtitle) %>" editorName="alloyeditor" name="subtitleEditor" placeholder="subtitle" showSource="<%= false %>" /> </h4>
					</div>

					<aui:input name="subtitle" type="hidden" />

					<div class="entry-content">
						<liferay-ui:input-editor contents="<%= content %>" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp") %>' name="contentEditor" onChangeMethod="OnChangeEditor" placeholder="content" />
					</div>

					<aui:input name="content" type="hidden" />
				</div>
			</div>

			<div class="col-md-8 col-md-offset-2 hide" id="<portlet:namespace />settingsSection">
				<div class="display-date-wrapper">
					<h3><liferay-ui:message key="display-date" /></h3>

					<aui:input label="" name="displayDate" />
				</div>

				<div class="entry-abstract-wrapper">
					<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">
						<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
					</liferay-ui:error>

					<liferay-ui:error exception="<%= EntrySmallImageScaleException.class %>">
						<liferay-ui:message key="an-error-occurred-while-scaling-the-abstract-image" />
					</liferay-ui:error>

					<h3><liferay-ui:message key="abstract" /></h3>

					<p class="explanation">
						<liferay-ui:message key="an-abstract-is-a-brief-summary-of-a-blog-entry" />
					</p>

					<div class="entry-abstract-options" id="<portlet:namespace />entryAbstractOptions">
						<aui:input checked="<%= !customAbstract %>" label='<%= LanguageUtil.format(request, "use-the-first-x-characters-of-the-entry-content", pageAbstractLength, false) %>' name="customAbstract" type="radio" value="<%= false %>" />

						<aui:input checked="<%= customAbstract %>" label="custom-abstract" name="customAbstract" type="radio" value="<%= true %>" />
					</div>

					<aui:fieldset cssClass="entry-abstract">
						<portlet:actionURL name="/blogs/upload_small_image" var="uploadSmallImageURL" />

						<div class="lfr-blogs-small-image-selector">

							<%
							String smallImageSelectedItemEventName = liferayPortletResponse.getNamespace() + "smallImageSelectedItem";
							%>

							<liferay-item-selector:image-selector fileEntryId="<%= smallImageFileEntryId %>" itemSelectorEventName="<%= smallImageSelectedItemEventName %>" itemSelectorURL="<%= blogsItemSelectorHelper.getItemSelectorURL(requestBackedPortletURLFactory, themeDisplay, smallImageSelectedItemEventName) %>" paramName="smallImageFileEntry" uploadURL="<%= uploadSmallImageURL %>" validExtensions='<%= StringUtil.merge(imageExtensions, ", ") %>' />
						</div>

						<div class="entry-description">
							<liferay-ui:input-editor contents="<%= HtmlUtil.escape(description) %>" cssClass='<%= customAbstract ? StringPool.BLANK : "readonly" %>' editorName="alloyeditor" name="descriptionEditor" onInitMethod="OnDescriptionEditorInit" placeholder="description" showSource="<%= false %>" />
						</div>

						<aui:input name="description" type="hidden" />
					</aui:fieldset>
				</div>

				<c:if test="<%= (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>">
					<div class="email-entry-updated-wrapper">
						<h3><liferay-ui:message key="email-notifications" /></h3>

						<p class="explanation">
							<liferay-ui:message key="comments-regarding-the-blog-entry-update" />
						</p>

						<%
						boolean sendEmailEntryUpdated = ParamUtil.getBoolean(request, "sendEmailEntryUpdated");
						%>

						<aui:input name="sendEmailEntryUpdated" type="checkbox" value="<%= sendEmailEntryUpdated %>" />

						<%
						String emailEntryUpdatedComment = ParamUtil.getString(request, "emailEntryUpdatedComment");
						%>

						<div id="<portlet:namespace />emailEntryUpdatedCommentWrapper">
							<aui:input label="" name="emailEntryUpdatedComment" type="textarea" value="<%= emailEntryUpdatedComment %>" />
						</div>
					</div>
				</c:if>

				<div class="categorization-wrapper">
					<h3><liferay-ui:message key="categorization" /></h3>

					<aui:input name="categories" type="assetCategories" />

					<aui:input name="tags" type="assetTags" />
				</div>

				<c:if test="<%= (entry == null) || (entry.getStatus() == WorkflowConstants.STATUS_DRAFT) %>">
					<aui:field-wrapper cssClass="permissions-wrapper">
						<h3><liferay-ui:message key="permissions" /></h3>

						<liferay-ui:input-permissions
							modelName="<%= BlogsEntry.class.getName() %>"
						/>
					</aui:field-wrapper>
				</c:if>

				<liferay-ui:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
					<div class="custom-attributes-wrapper">
						<h3><liferay-ui:message key="custom-fields" /></h3>

						<liferay-ui:custom-attribute-list
							className="<%= BlogsEntry.class.getName() %>"
							classPK="<%= entryId %>"
							editable="<%= true %>"
							label="<%= true %>"
						/>
					</div>
				</liferay-ui:custom-attributes-available>

				<div class="related-assets-wrapper">
					<h3><liferay-ui:message key="related-assets" /></h3>

					<liferay-ui:input-asset-links
						className="<%= BlogsEntry.class.getName() %>"
						classPK="<%= entryId %>"
					/>
				</div>

				<c:if test="<%= PropsValues.BLOGS_PINGBACK_ENABLED %>">
					<div class="pingback-enabled-wrapper">
						<h3><liferay-ui:message key="pingback" /></h3>

						<p class="explanation">
							<liferay-ui:message key="a-pingback-is-a-comment-that-is-created-when-you-link-to-another-blog-post-where-pingbacks-are-enabled" />
						</p>

						<aui:input helpMessage="to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" label="enabled" name="allowPingbacks" value="<%= allowPingbacks %>" />
					</div>
				</c:if>

				<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
					<div class="trackback-enabled-wrapper">
						<h3><liferay-ui:message key="trackbacks" /></h3>

						<aui:input helpMessage="to-allow-trackbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" name="allowTrackbacks" value="<%= allowTrackbacks %>" />

						<aui:input label="trackbacks-to-send" name="trackbacks" />

						<c:if test="<%= (entry != null) && Validator.isNotNull(entry.getTrackbacks()) %>">
							<aui:fieldset label="trackbacks-already-sent">

								<%
								int i = 0;

								for (String trackback : StringUtil.split(entry.getTrackbacks())) {
								%>

									<aui:input label="" name='<%= "trackback" + (i++) %>' title="" type="resource" value="<%= trackback %>" />

								<%
								}
								%>

							</aui:fieldset>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>

		<aui:fieldset cssClass="col-md-8 col-md-offset-2 entry-footer">

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

			<aui:button-row>

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

				<c:if test="<%= (entry != null) && entry.isApproved() && WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class.getName()) %>">
					<div class="alert alert-info">
						<liferay-ui:message arguments="<%= ResourceActionsUtil.getModelResource(locale, BlogsEntry.class.getName()) %>" key="this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again" translateArguments="<%= false %>" />
					</div>
				</c:if>

				<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

				<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

				<aui:button cssClass="btn-lg" href="<%= redirect %>" name="cancelButton" type="cancel" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</div>

<portlet:actionURL name="/blogs/edit_entry" var="editEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="ajax" value="true" />
</portlet:actionURL>

<aui:script>
	function <portlet:namespace />OnChangeEditor(html) {
		var blogs = Liferay.component('<portlet:namespace />Blogs');

		if (blogs) {
			blogs.setDescription(html);
		}
	}

	function <portlet:namespace />OnDescriptionEditorInit() {
		<c:if test="<%= !customAbstract %>">
			document.getElementById('<portlet:namespace />descriptionEditor').setAttribute('contenteditable', false);
		</c:if>
	}

	<c:if test="<%= (entry != null) && blogsGroupServiceSettings.isEmailEntryUpdatedEnabled() %>">
		Liferay.Util.toggleBoxes('<portlet:namespace />sendEmailEntryUpdated', '<portlet:namespace />emailEntryUpdatedCommentWrapper');
	</c:if>
</aui:script>

<aui:script use="liferay-blogs">
	var blogs = Liferay.component(
		'<portlet:namespace />Blogs',
		new Liferay.Blogs(
			{
				constants: {
					'ACTION_PUBLISH': '<%= WorkflowConstants.ACTION_PUBLISH %>',
					'ACTION_SAVE_DRAFT': '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>',
					'ADD': '<%= Constants.ADD %>',
					'CMD': '<%= Constants.CMD %>',
					'STATUS_DRAFT': '<%= WorkflowConstants.STATUS_DRAFT %>',
					'UPDATE': '<%= Constants.UPDATE %>'
				},
				descriptionLength: '<%= pageAbstractLength %>',
				editEntryURL: '<%= editEntryURL %>',

				<c:if test="<%= entry != null %>">
					entry: {
						content: '<%= UnicodeFormatter.toString(content) %>',
						customDescription: <%= customAbstract %>,
						description: '<%= UnicodeFormatter.toString(description) %>',
						pending: <%= entry.isPending() %>,
						status: '<%= entry.getStatus() %>',
						subtitle: '<%= UnicodeFormatter.toString(subtitle) %>',
						title: '<%= UnicodeFormatter.toString(title) %>',
						userId: '<%= entry.getUserId() %>'
					},
				</c:if>

				namespace: '<portlet:namespace />'
			}
		)
	);

	var clearSaveDraftHandle = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			blogs.destroy();

			Liferay.detach('destroyPortlet', clearSaveDraftHandle);
		}
	};

	Liferay.on('destroyPortlet', clearSaveDraftHandle);
</aui:script>

<%
if (entry != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("mvcRenderCommandName", "/blogs/view_entry");
	portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

	PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-entry"), currentURL);
}
%>