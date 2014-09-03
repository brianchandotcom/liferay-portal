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

<%@ include file="/html/taglib/ui/image_selector/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_image_selector") + StringPool.UNDERLINE;

long fileEntryId = GetterUtil.getLong(request.getAttribute("liferay-ui:image-selector:fileEntryId"));
String paramName = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:paramName"));
String validExtensions = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:validExtensions"));

String imageURL = null;

if (fileEntryId != 0) {
	FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

	imageURL = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
}
%>

<div class="taglib-image-selector <%= fileEntryId == 0 ? "drag-drop" : StringPool.BLANK %>" id="<%= randomNamespace %>taglibImageSelector">
	<div class="taglib-image-selector-image-wrapper">
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="<%= Validator.isNull(imageURL) ? "hide" : StringPool.BLANK %>" id="<%= randomNamespace %>image" src="<%= HtmlUtil.escape(imageURL) %>" />

		<div class="browse-image-controls <%= (fileEntryId != 0) ? "hide" : StringPool.BLANK %>">
			<div class="drag-drop-label">
				<liferay-ui:message arguments="<%= validExtensions %>" key="drag-drop-images" />

				<c:if test="<%= Validator.isNotNull(validExtensions) %>">
					(<%= validExtensions %>)
				</c:if>
			</div>

			<a class="browse-image" href="javascript:;" id="<%= randomNamespace + "browseImage" %>"><liferay-ui:message key="browse" /></a>
		</div>

		<div class="change-image-controls <%= (fileEntryId != 0) ? StringPool.BLANK : "hide" %>">
			<aui:button cssClass="btn btn-default browse-image" icon="icon-picture" value="change" />

			<aui:button cssClass="btn btn-default" icon="icon-remove" id='<%= randomNamespace + "removeImage" %>' value="delete" />
		</div>
	</div>

	<aui:input name="<%= paramName %>" type="hidden" value="<%= fileEntryId %>" />
</div>

<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_SELECTOR %>" varImpl="documentSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/document_selector/view" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	<portlet:param name="checkContentDisplayPage" value="true" />
	<portlet:param name="tabs1Names" value="documents" />
	<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectImage" %>' />
</liferay-portlet:renderURL>

<aui:script use="aui-base">
	var taglibImageSelector = A.one('#<%= randomNamespace %>taglibImageSelector');

	taglibImageSelector.delegate(
		'click',
		function(event) {
			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true
					},
					eventName: '<portlet:namespace />selectImage',
					id: '<portlet:namespace />selectImage',
					title: '<%= LanguageUtil.get(locale, "select-image") %>',
					uri: '<%= documentSelectorURL.toString() %>'
				},
				function(event) {
					var fileEntryId = taglibImageSelector.one('#<portlet:namespace /><%= paramName %>');

					fileEntryId.val(event.fileentryid);

					var image = taglibImageSelector.one('#<%= randomNamespace %>image');

					image.setAttribute('src', event.url);

					image.show();

					var browseImageControls = taglibImageSelector.one('.browse-image-controls');

					browseImageControls.hide()

					var changeImageControls = taglibImageSelector.one('.change-image-controls');

					changeImageControls.show();

					taglibImageSelector.removeClass('drag-drop');
				}
			);
		},
		'.browse-image'
	);

	var removeImageNode = taglibImageSelector.one('#<%= randomNamespace %>removeImage');

	removeImageNode.on(
		'click',
		function(event) {
			var fileEntryId = taglibImageSelector.one('#<portlet:namespace /><%= paramName %>');

			fileEntryId.val(0);

			var image = taglibImageSelector.one('#<%= randomNamespace %>image');

			image.setAttribute('src', '');

			image.hide();

			var browseImageControls = taglibImageSelector.one('.browse-image-controls');

			browseImageControls.show()

			var changeImageControls = taglibImageSelector.one('.change-image-controls');

			changeImageControls.hide();

			taglibImageSelector.addClass('drag-drop');
		}
	);
</aui:script>