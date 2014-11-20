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

String draggableImage = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:draggableImage"), "none");
long fileEntryId = GetterUtil.getLong(request.getAttribute("liferay-ui:image-selector:fileEntryId"));
String paramName = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:paramName"));
String uploadURL = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:uploadURL"));
String validExtensions = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:validExtensions"));

String imageURL = null;

if (fileEntryId != 0) {
	FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

	imageURL = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
}
%>

<div class="taglib-image-selector <%= fileEntryId == 0 ? "drop-enabled" : StringPool.BLANK %> <%= !draggableImage.equals("none") ? "draggable-image " + draggableImage : StringPool.BLANK %>" id="<%= randomNamespace %>taglibImageSelector">
	<aui:input name='<%= paramName + "Id" %>' type="hidden" value="<%= fileEntryId %>" />
	<aui:input name='<%= paramName + "CropRegion" %>' type="hidden" />

	<div class="image-wrapper">
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="current-image <%= Validator.isNull(imageURL) ? "hide" : StringPool.BLANK %>" id="<%= randomNamespace %>image" src="<%= HtmlUtil.escape(Validator.isNotNull(imageURL) ? imageURL : StringPool.BLANK) %>" />
	</div>

	<div class="browse-image-controls <%= (fileEntryId != 0) ? "hide" : StringPool.BLANK %>">
		<div class="drag-drop-label">
			<liferay-ui:message arguments="<%= validExtensions %>" key="drag-and-drop-images" />

			<c:if test="<%= Validator.isNotNull(validExtensions) %>">
				(<%= validExtensions %>)
			</c:if>
		</div>

		<a class="browse-image" href="javascript:;" id="<%= randomNamespace + "browseImage" %>"><liferay-ui:message key="browse" /></a>
	</div>

	<div class="change-image-controls <%= (fileEntryId != 0) ? StringPool.BLANK : "hide" %>">
		<aui:button cssClass="btn-primary" value="change-picture" />

		<aui:button icon="icon-trash" id='<%= randomNamespace + "removeImage" %>' useNamespace="<%= false %>" />
	</div>
</div>

<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_SELECTOR %>" varImpl="documentSelectorURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="struts_action" value="/document_selector/view" />
	<portlet:param name="tabs1Names" value="documents" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	<portlet:param name="checkContentDisplayPage" value="true" />
	<portlet:param name="eventName" value='<%= randomNamespace + "selectImage" %>' />
</liferay-portlet:renderURL>

<%
String modules = "liferay-image-selector";

if (!draggableImage.equals("none")) {
	modules += ",liferay-cover-cropper";
}
%>

<aui:script use="<%= modules %>">
	var imageSelector = new Liferay.ImageSelector(
		{
			documentSelectorURL: '<%= documentSelectorURL.toString() %>',
			namespace: '<%= randomNamespace %>',
			paramName: '<portlet:namespace /><%= paramName %>',
			rootNode: '#<%= randomNamespace %>taglibImageSelector',
			uploadURL: '<%= uploadURL %>'
		}
	);

	<c:if test='<%= !draggableImage.equals("none") %>'>
		imageSelector.plug(
			Liferay.CoverCropper,
			{
				direction: '<%= draggableImage %>',
				imageContainerSelector: '.image-wrapper',
				imageSelector: '#image'
			}
		);
	</c:if>
</aui:script>