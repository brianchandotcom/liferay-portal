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

<div class="server-admin-tabs">
	<aui:fieldset>
		<liferay-ui:panel-container extended="<%= true %>" id="uploadPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="adminDocumentLibraryPanel" markupView="lexicon" persistState="<%= true %>" title="documents-and-media">
				<aui:input cssClass="lfr-input-text-container" label="maximum-thumbnail-height" name="dlFileEntryThumbnailMaxHeight" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT) %>" />

				<aui:input cssClass="lfr-input-text-container" label="maximum-thumbnail-width" name="dlFileEntryThumbnailMaxWidth" type="text" value="<%= PrefsPropsUtil.getLong(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH) %>" />
			</liferay-ui:panel>
		</liferay-ui:panel-container>
	</aui:fieldset>
</div>

<aui:button-row>
	<aui:button cssClass="btn-lg save-server-button" data-cmd="updateFileUploads" value="save" />
</aui:button-row>