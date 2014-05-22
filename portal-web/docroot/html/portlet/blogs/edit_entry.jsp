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

<%@ include file="/html/portlet/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String content = BeanParamUtil.getString(entry, request, "content");
boolean allowPingbacks = PropsValues.BLOGS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowPingbacks", true);
boolean allowTrackbacks = PropsValues.BLOGS_TRACKBACK_ENABLED && BeanParamUtil.getBoolean(entry, request, "allowTrackbacks", true);
boolean smallImage = BeanParamUtil.getBoolean(entry, request, "smallImage");

boolean preview = ParamUtil.getBoolean(request, "preview");
boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);
%>

<c:if test="<%= showHeader %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= (entry == null) %>"
		title='<%= (entry == null) ? "new-blog-entry" : entry.getTitle() %>'
	/>
</c:if>

<portlet:actionURL var="editEntryURL">
	<portlet:param name="struts_action" value="/blogs/edit_entry" />
</portlet:actionURL>

<aui:form action="<%= editEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit="event.preventDefault();">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="entryId" type="hidden" value="<%= entryId %>" />
	<aui:input name="preview" type="hidden" value="<%= false %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
	<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />

	<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileSizeException.class %>">

		<%
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}
		%>

		<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= entry %>" model="<%= BlogsEntry.class %>" />

	<c:if test="<%= (entry == null) || !entry.isApproved() %>">
		<div class="save-status" id="<portlet:namespace />saveStatus"></div>
	</c:if>

	<c:if test="<%= entry != null %>">
		<aui:workflow-status id="<%= String.valueOf(entry.getEntryId()) %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= entry.getStatus() %>" />
	</c:if>

	<aui:fieldset>
		<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="title" />

		<aui:input name="deckTitle" />

		<aui:input name="displayDate" />

		<c:if test="<%= preview %>">

			<%
			if (entry == null) {
				entry = new BlogsEntryImpl();
			}

			entry.setContent(content);
			%>

			<liferay-ui:message key="preview" />:

			<div class="preview">
				<%= entry.getContent() %>
			</div>

			<br />
		</c:if>

		<aui:field-wrapper label="content">
			<liferay-ui:input-editor editorImpl="<%= EDITOR_WYSIWYG_IMPL_KEY %>" />

			<aui:input name="content" type="hidden" />
		</aui:field-wrapper>

		<liferay-ui:custom-attributes-available className="<%= BlogsEntry.class.getName() %>">
			<liferay-ui:custom-attribute-list
				className="<%= BlogsEntry.class.getName() %>"
				classPK="<%= entryId %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</liferay-ui:custom-attributes-available>

		<c:if test="<%= PropsValues.BLOGS_PINGBACK_ENABLED %>">
			<aui:input helpMessage="to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" name="allowPingbacks" value="<%= allowPingbacks %>" />
		</c:if>

		<c:if test="<%= PropsValues.BLOGS_TRACKBACK_ENABLED %>">
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
		</c:if>

		<c:if test="<%= (entry == null) || (entry.getStatus() == WorkflowConstants.STATUS_DRAFT) %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= BlogsEntry.class.getName() %>"
				/>
			</aui:field-wrapper>
		</c:if>

		<br />

		<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="blogsEntryAbstractPanel" persistState="<%= true %>" title="abstract">
			<liferay-ui:error exception="<%= EntrySmallImageNameException.class %>">

				<%
				String[] imageExtensions = PrefsPropsUtil.getStringArray(PropsKeys.BLOGS_IMAGE_EXTENSIONS, StringPool.COMMA);
				%>

				<liferay-ui:message key="image-names-must-end-with-one-of-the-following-extensions" /> <%= StringUtil.merge(imageExtensions, ", ") %>.
			</liferay-ui:error>

			<liferay-ui:error exception="<%= EntrySmallImageSizeException.class %>">

				<%
				long imageMaxSize = PrefsPropsUtil.getLong(PropsKeys.BLOGS_IMAGE_SMALL_MAX_SIZE);
				%>

				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(imageMaxSize, locale) %>" key="please-enter-a-small-image-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<aui:fieldset>
				<aui:input label="description" name="description" />

				<div id="<portlet:namespace />smallImageContainer">
					<div class="lfr-blogs-small-image-header">
						<aui:input label="use-small-image" name="smallImage" />
					</div>

					<div class="lfr-blogs-small-image-content toggler-content-collapsed">
						<aui:row>
							<c:if test="<%= smallImage && (entry != null) %>">
								<aui:col width="<%= 50 %>">
									<img alt="<liferay-ui:message key="preview" />" class="lfr-blogs-small-image-preview" src="<%= Validator.isNotNull(entry.getSmallImageURL()) ? entry.getSmallImageURL() : themeDisplay.getPathImage() + "/template?img_id=" + entry.getSmallImageId() + "&t=" + WebServerServletTokenUtil.getToken(entry.getSmallImageId()) %>" />
								</aui:col>
							</c:if>

							<aui:col width="<%= (smallImage && (entry != null)) ? 50 : 100 %>">
								<aui:fieldset>
									<aui:input cssClass="lfr-blogs-small-image-type" inlineField="<%= true %>" label="small-image-url" name="type" type="radio" />

									<aui:input cssClass="lfr-blogs-small-image-value" inlineField="<%= true %>" label="" name="smallImageURL" title="small-image-url" />
								</aui:fieldset>

								<aui:fieldset>
									<aui:input cssClass="lfr-blogs-small-image-type" inlineField="<%= true %>" label="small-image" name="type" type="radio" />

									<aui:input cssClass="lfr-blogs-small-image-value" inlineField="<%= true %>" label="" name="smallFile" title="small-image-file" type="file" />
								</aui:fieldset>
							</aui:col>
						</aui:row>
					</div>
				</div>
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="blogsEntryCategorizationPanel" persistState="<%= true %>" title="categorization">
			<aui:fieldset>
				<aui:input name="categories" type="assetCategories" />

				<aui:input name="tags" type="assetTags" />
			</aui:fieldset>
		</liferay-ui:panel>

		<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="blogsEntryAssetLinksPanel" persistState="<%= true %>" title="related-assets">
			<aui:fieldset>
				<liferay-ui:input-asset-links
					className="<%= BlogsEntry.class.getName() %>"
					classPK="<%= entryId %>"
				/>
			</aui:fieldset>
		</liferay-ui:panel>

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
					<%= LanguageUtil.format(pageContext, "this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again", ResourceActionsUtil.getModelResource(locale, BlogsEntry.class.getName()), false) %>
				</div>
			</c:if>

			<aui:button name="saveButton" onClick='<%= renderResponse.getNamespace() + "saveEntry(true, false);" %>' primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

			<c:if test="<%= (entry == null) || entry.isDraft() || preview %>">
				<aui:button name="previewButton" onClick='<%= renderResponse.getNamespace() + "previewEntry();" %>' value="preview" />
			</c:if>

			<aui:button disabled="<%= pending %>" name="publishButton" onClick='<%= renderResponse.getNamespace() + "saveEntry(false, false);" %>' type="submit" value="<%= publishButtonLabel %>" />

			<aui:button href="<%= redirect %>" name="cancelButton" type="cancel" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>

<aui:script>
	var <portlet:namespace />saveDraftIntervalId = null;
	var <portlet:namespace />oldTitle = null;
	var <portlet:namespace />oldContent = null;

	function <portlet:namespace />clearSaveDraftIntervalId() {
		if (<portlet:namespace />saveDraftIntervalId != null) {
			clearInterval(<portlet:namespace />saveDraftIntervalId);
		}
	}

	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm.<portlet:namespace />title.value + ' ' + window.<portlet:namespace />editor.getHTML();
	}

	function <portlet:namespace />initEditor() {
		return '<%= UnicodeFormatter.toString(content) %>';
	}

	function <portlet:namespace />previewEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (entry == null) ? Constants.ADD : Constants.UPDATE %>';
		document.<portlet:namespace />fm.<portlet:namespace />preview.value = 'true';
		document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>';

		if (window.<portlet:namespace />editor) {
			document.<portlet:namespace />fm.<portlet:namespace />content.value = window.<portlet:namespace />editor.getHTML();
		}

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveEntry',
		function(draft, ajax) {
			var A = AUI();

			var title = document.<portlet:namespace />fm.<portlet:namespace />title.value;
			var content = window.<portlet:namespace />editor.getHTML();

			var publishButton = A.one('#<portlet:namespace />publishButton');
			var cancelButton = A.one('#<portlet:namespace />cancelButton');

			var saveStatus = A.one('#<portlet:namespace />saveStatus');
			var saveText = '<%= UnicodeLanguageUtil.format(pageContext, ((entry != null) && entry.isPending()) ? "entry-saved-at-x" : "draft-saved-at-x", "[TIME]", false) %>';

			if (draft && ajax) {
				if ((title == '') || (content == '')) {
					return;
				}

				if ((<portlet:namespace />oldTitle == title) &&
					(<portlet:namespace />oldContent == content)) {

					return;
				}

				<portlet:namespace />oldTitle = title;
				<portlet:namespace />oldContent = content;

				var url = '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="struts_action" value="/blogs/edit_entry" /><portlet:param name="ajax" value="true" /><portlet:param name="preview" value="false" /></portlet:actionURL>';

				var data = {
					<portlet:namespace />assetTagNames: document.<portlet:namespace />fm.<portlet:namespace />assetTagNames.value,
					<portlet:namespace /><%= Constants.CMD %>: '<%= Constants.ADD %>',
					<portlet:namespace />content: content,
					<portlet:namespace />displayDateAmPm: document.<portlet:namespace />fm.<portlet:namespace />displayDateAmPm.value,
					<portlet:namespace />displayDateDay: document.<portlet:namespace />fm.<portlet:namespace />displayDateDay.value,
					<portlet:namespace />displayDateHour: document.<portlet:namespace />fm.<portlet:namespace />displayDateHour.value,
					<portlet:namespace />displayDateMinute: document.<portlet:namespace />fm.<portlet:namespace />displayDateMinute.value,
					<portlet:namespace />displayDateMonth: document.<portlet:namespace />fm.<portlet:namespace />displayDateMonth.value,
					<portlet:namespace />displayDateYear: document.<portlet:namespace />fm.<portlet:namespace />displayDateYear.value,
					<portlet:namespace />entryId: document.<portlet:namespace />fm.<portlet:namespace />entryId.value,
					<portlet:namespace />redirect: document.<portlet:namespace />fm.<portlet:namespace />redirect.value,
					<portlet:namespace />referringPortletResource: document.<portlet:namespace />fm.<portlet:namespace />referringPortletResource.value,
					<portlet:namespace />title: title,
					<portlet:namespace />workflowAction: <%= WorkflowConstants.ACTION_SAVE_DRAFT %>
				};

				var customAttributes = A.one(document.<portlet:namespace />fm).all('[name^=<portlet:namespace />ExpandoAttribute]');

				customAttributes.each(
					function(item, index, collection) {
						data[item.attr('name')] = item.val();
					}
				);

				A.io.request(
					url,
					{
						data: data,
						dataType: 'JSON',
						on: {
							failure: function() {
								if (saveStatus) {
									saveStatus.attr('className', 'alert alert-danger save-status');
									saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "could-not-save-draft-to-the-server") %>');
								}
							},
							start: function() {
								Liferay.Util.toggleDisabled(publishButton, true);

								if (saveStatus) {
									saveStatus.attr('className', 'alert alert-info save-status pending');
									saveStatus.html('<%= UnicodeLanguageUtil.get(pageContext, "saving-draft") %>');
								}
							},
							success: function(event, id, obj) {
								var instance = this;

								var message = instance.get('responseData');

								if (message) {
									document.<portlet:namespace />fm.<portlet:namespace />entryId.value = message.entryId;
									document.<portlet:namespace />fm.<portlet:namespace />redirect.value = message.redirect;

									var tabs1BackButton = A.one('#<portlet:namespace />tabs1TabsBack');

									if (tabs1BackButton) {
										tabs1BackButton.attr('href', message.redirect);
									}

									if (cancelButton) {
										cancelButton.detach('click');

										cancelButton.on(
											'click',
											function() {
												location.href = message.redirect;
											}
										);
									}

									var now = saveText.replace(/\[TIME\]/gim, (new Date()).toString());

									if (saveStatus) {
										saveStatus.attr('className', 'alert alert-success save-status');
										saveStatus.html(now);
									}
								}
								else {
									saveStatus.hide();
								}

								Liferay.Util.toggleDisabled(publishButton, false);
							}
						}
					}
				);
			}
			else {
				<portlet:namespace />clearSaveDraftIntervalId();

				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '<%= (entry == null) ? Constants.ADD : Constants.UPDATE %>';
				document.<portlet:namespace />fm.<portlet:namespace />content.value = content;

				if (draft) {
					document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_SAVE_DRAFT %>;
				}
				else {
					document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_PUBLISH %>;
				}

				submitForm(document.<portlet:namespace />fm);
			}
		},
		['aui-io']
	);

	var clearSaveDraftHandle = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			<portlet:namespace />clearSaveDraftIntervalId();

			Liferay.detach('destroyPortlet', clearSaveDraftHandle);
		}
	};

	Liferay.on('destroyPortlet', clearSaveDraftHandle);
</aui:script>

<aui:script use="aui-base">
	var cancelButton = A.one('#<portlet:namespace />cancelButton');

	if (cancelButton) {
		cancelButton.on(
			'click',
			function() {
				<portlet:namespace />clearSaveDraftIntervalId();

				location.href = '<%= HtmlUtil.escapeJS(PortalUtil.escapeRedirect(redirect)) %>';
			}
		);
	}

	<c:if test="<%= (entry == null) || ((entry.getUserId() == user.getUserId()) && (entry.getStatus() == WorkflowConstants.STATUS_DRAFT)) %>">
		<portlet:namespace />saveDraftIntervalId = setInterval('<portlet:namespace />saveEntry(true, true)', 30000);
		<portlet:namespace />oldTitle = document.<portlet:namespace />fm.<portlet:namespace />title.value;
		<portlet:namespace />oldContent = <portlet:namespace />initEditor();
	</c:if>
</aui:script>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />smallImageContainer');

	var types = container.all('.lfr-blogs-small-image-type');
	var values = container.all('.lfr-blogs-small-image-value');

	var selectSmallImageType = function(index) {
		types.attr('checked', false);

		types.item(index).attr('checked', true);

		values.attr('disabled', true);

		values.item(index).attr('disabled', false);
	};

	container.delegate(
		'change',
		function(event) {
			var index = types.indexOf(event.currentTarget);

			selectSmallImageType(index);
		},
		'.lfr-blogs-small-image-type'
	);

	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />smallImageContainer .lfr-blogs-small-image-content',
			expanded: <%= smallImage %>,
			header: '#<portlet:namespace />smallImageContainer .lfr-blogs-small-image-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />smallImage').attr('checked', expanded);

					if (expanded) {
						types.each(
							function(item, index, collection) {
								if (item.get('checked')) {
									values.item(index).attr('disabled', false);
								}
							}
						);
					}
					else {
						values.attr('disabled', true);
					}
				}
			}
		}
	);

	selectSmallImageType('<%= (entry != null) && Validator.isNotNull(entry.getSmallImageURL()) ? 0 : 1 %>');
</aui:script>

<%
if (entry != null) {
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/blogs/view_entry");
	portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

	PortalUtil.addPortletBreadcrumbEntry(request, entry.getTitle(), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-entry"), currentURL);
}
%>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.blogs.edit_entry.jsp";
%>