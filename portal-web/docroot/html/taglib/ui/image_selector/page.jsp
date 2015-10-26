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
long maxFileSize = GetterUtil.getLong(request.getAttribute("liferay-ui:image-selector:maxFileSize"));
String paramName = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:paramName"));
String uploadURL = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:uploadURL"));
String validExtensions = GetterUtil.getString((String)request.getAttribute("liferay-ui:image-selector:validExtensions"));

String cropRegion = ParamUtil.getString(request, paramName + "CropRegion");

String imageURL = null;

if (fileEntryId != 0) {
	FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

	imageURL = DLUtil.getPreviewURL(fileEntry, fileEntry.getFileVersion(), themeDisplay, StringPool.BLANK);
}
%>

<div class="drop-zone <%= fileEntryId == 0 ? "drop-enabled" : StringPool.BLANK %> <%= !draggableImage.equals("none") ? "draggable-image " + draggableImage : StringPool.BLANK %> taglib-image-selector" id="<%= randomNamespace %>taglibImageSelector">
	<aui:input name='<%= paramName + "Id" %>' type="hidden" value="<%= fileEntryId %>" />
	<aui:input name='<%= paramName + "CropRegion" %>' type="hidden" value="<%= cropRegion %>" />

	<div class="image-wrapper">
		<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="current-image" />" class="current-image <%= Validator.isNull(imageURL) ? "hide" : StringPool.BLANK %>" id="<%= randomNamespace %>image" src="<%= HtmlUtil.escape(Validator.isNotNull(imageURL) ? imageURL : StringPool.BLANK) %>" />
	</div>

	<liferay-util:buffer var="selectFileLink">
		<a class="browse-image btn btn-primary" href="javascript:;" id="<%= randomNamespace + "browseImage" %>"><liferay-ui:message key="select-file" /></a>
	</liferay-util:buffer>

	<div class="browse-image-controls <%= (fileEntryId != 0) ? "hide" : StringPool.BLANK %>">
		<div class="drag-drop-label">
			<liferay-ui:message arguments="<%= selectFileLink %>" key="drag-and-drop-to-upload-or-x" />
		</div>

		<div class="file-validation-info">
			<c:if test="<%= Validator.isNotNull(validExtensions) %>">
				<strong><%= validExtensions %></strong>
			</c:if>

			<c:if test="<%= maxFileSize != 0 %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(maxFileSize, locale) %>" key="maximum-size-x" />
			</c:if>
		</div>
	</div>

	<i class="glyphicon glyphicon-ok"></i>

	<liferay-ui:drop-here-info message="drop-files-here" />

	<div class="error-wrapper hide">
		<aui:alert closeable="<%= true %>" id='<%= randomNamespace + "errorAlert" %>' type="danger">
			<span class="error-message"></span>

			<%= selectFileLink %>
		</aui:alert>
	</div>

	<div class="progress-wrapper">
		<p class="file-name"></p>

		<div class="progressbar"></div>

		<p class="progress-data size"></p>

		<aui:button cssClass="btn-primary" id='<%= randomNamespace + "cancelUpload" %>' useNamespace="<%= false %>" value="cancel" />
	</div>

	<div class="change-image-controls <%= (fileEntryId != 0) ? StringPool.BLANK : "hide" %>">
		<aui:button cssClass="browse-image icon-monospaced" icon="icon-refresh" />

		<aui:button cssClass="icon-monospaced" icon="icon-trash" id='<%= randomNamespace + "removeImage" %>' useNamespace="<%= false %>" />
	</div>
</div>

<%
PortletURL itemSelectorURL = liferayPortletResponse.createRenderURL(PortletKeys.ITEM_SELECTOR);

itemSelectorURL.setParameter("criteria", "com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion,com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion,com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion");
itemSelectorURL.setParameter("itemSelectedEventName", randomNamespace + "selectImage");

JSONObject itemSelectorJSONParamJSONObject = JSONFactoryUtil.createJSONObject();

itemSelectorJSONParamJSONObject.put("desiredItemSelectorReturnTypes", "com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.UploadableFileReturnType");

JSONObject itemSelectorUploadParamJSONObject = JSONFactoryUtil.createJSONObject();

itemSelectorUploadParamJSONObject.put("desiredItemSelectorReturnTypes", "com.liferay.item.selector.criteria.UploadableFileReturnType");

itemSelectorURL.setParameter("0_json", itemSelectorJSONParamJSONObject.toString());
itemSelectorURL.setParameter("1_json", itemSelectorJSONParamJSONObject.toString());

PortletURL uploadItemSelectorCriterionUploadURL = liferayPortletResponse.createActionURL(PortletKeys.BLOGS);

uploadItemSelectorCriterionUploadURL.setParameter(ActionRequest.ACTION_NAME, "/blogs/upload_cover_image");

itemSelectorUploadParamJSONObject.put("repositoryName", LanguageUtil.get(locale, "blog-images"));
itemSelectorUploadParamJSONObject.put("URL", uploadItemSelectorCriterionUploadURL.toString());

itemSelectorURL.setParameter("2_json", itemSelectorUploadParamJSONObject.toString());

itemSelectorURL.setPortletMode(PortletMode.VIEW);
itemSelectorURL.setWindowState(LiferayWindowState.POP_UP);

String modules = "liferay-image-selector";

if (!draggableImage.equals("none")) {
	modules += ",liferay-cover-cropper";
}
%>

<aui:script use="<%= modules %>">
	var imageSelector = new Liferay.ImageSelector(
		{
			errorNode: '#<%= randomNamespace + "errorAlert" %>',
			fileEntryImageNode: '#<%= randomNamespace %>image',
			itemSelectorURL: '<%= itemSelectorURL.toString() %>',
			maxFileSize: <%= maxFileSize %>,
			namespace: '<%= randomNamespace %>',
			paramName: '<portlet:namespace /><%= paramName %>',
			rootNode: '#<%= randomNamespace %>taglibImageSelector',
			uploadURL: '<%= uploadURL %>',
			validExtensions: '<%= validExtensions %>'
		}
	);

	<c:if test='<%= !draggableImage.equals("none") %>'>
		imageSelector.plug(
			Liferay.CoverCropper,
			{
				direction: '<%= draggableImage %>',
				imageContainerSelector: '.image-wrapper',
				imageSelector: '#<%= randomNamespace %>image'
			}
		);
	</c:if>

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			imageSelector.destroy();

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>