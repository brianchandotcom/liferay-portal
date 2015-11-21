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

<%@ include file="/init.jsp" %>

<aui:fieldset label="configure-the-file-upload-settings">
	<liferay-ui:panel-container extended="<%= true %>" id="adminGeneralUploadPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminGeneralUploadPanel" persistState="<%= true %>" title="general">
			<aui:input cssClass="lfr-input-text-container" label="overall-maximum-upload-request-size" name="uploadServletRequestImplMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label="temporary-storage-directory" name="uploadServletRequestImplTempDir" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR, StringPool.BLANK) %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<liferay-ui:panel-container extended="<%= true %>" id="adminDocumentLibraryPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminDocumentLibraryPanel" persistState="<%= true %>" title="documents-and-media">
			<aui:input cssClass="lfr-input-text-container" label="maximum-file-size" name="dlFileMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" helpMessage="maximum-previewable-file-size-help" label="maximum-previewable-file-size" name="dlFileEntryPreviewableProcessorMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_PREVIEWABLE_PROCESSOR_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label="maximum-thumbnail-height" name="dlFileEntryThumbnailMaxHeight" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT) %>" />

			<aui:input cssClass="lfr-input-text-container" label="maximum-thumbnail-width" name="dlFileEntryThumbnailMaxWidth" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) %>" />

			<aui:input cssClass="lfr-input-text-container" label="allowed-file-extensions" name="dlFileExtensions" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.DL_FILE_EXTENSIONS) %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<liferay-ui:panel-container extended="<%= true %>" id="adminWebContentImagesPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminWebContentImagesPanel" persistState="<%= true %>" title="web-content-images">
			<aui:input cssClass="lfr-input-text-container" label="maximum-file-size" name="journalImageSmallMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label="allowed-file-extensions" name="journalImageExtensions" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.JOURNAL_IMAGE_EXTENSIONS) %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<liferay-ui:panel-container extended="<%= true %>" id="adminShoppingCartImagesPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminShoppingCartImagesPanel" persistState="<%= true %>" title="shopping-cart-images">
			<aui:input cssClass="lfr-input-text-container" label='<%= LanguageUtil.get(request, "maximum-file-size") + "(" + LanguageUtil.get(request, "large-image") + ")" %>' name="shoppingImageLargeMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_LARGE_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label='<%= LanguageUtil.get(request, "maximum-file-size") + "(" + LanguageUtil.get(request, "medium-image") + ")" %>' name="shoppingImageMediumMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_MEDIUM_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label='<%= LanguageUtil.get(request, "maximum-file-size") + "(" + LanguageUtil.get(request, "small-image") + ")" %>' name="shoppingImageSmallMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.SHOPPING_IMAGE_SMALL_MAX_SIZE) %>" />

			<aui:input cssClass="lfr-input-text-container" label="allowed-file-extensions" name="shoppingImageExtensions" type="text" value="<%= PrefsPropsUtil.getString(PropsKeys.SHOPPING_IMAGE_EXTENSIONS) %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<liferay-ui:panel-container extended="<%= true %>" id="adminUserImagesPanelContainer" persistState="<%= true %>">
		<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminUserImagesPanel" persistState="<%= true %>" title="user-images">
			<aui:input cssClass="lfr-input-text-container" label="maximum-file-size" name="usersImageMaxSize" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.USERS_IMAGE_MAX_SIZE) %>" />
		</liferay-ui:panel>
	</liferay-ui:panel-container>
</aui:fieldset>

<aui:button-row>
	<aui:button cssClass="save-server-button" data-cmd="updateFileUploads" value="save" />
</aui:button-row>