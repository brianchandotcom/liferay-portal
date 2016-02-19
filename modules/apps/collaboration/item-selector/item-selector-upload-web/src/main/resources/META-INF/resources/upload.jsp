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

<%
ItemSelectorUploadViewDisplayContext itemSelectorUploadViewDisplayContext = (ItemSelectorUploadViewDisplayContext)request.getAttribute(ItemSelectorUploadView.ITEM_SELECTOR_UPLOAD_VIEW_DISPLAY_CONTEXT);
%>

<div class="container-fluid-1280 lfr-item-viewer" id="itemSelectorUploadContainer">
	<div class="drop-enabled drop-zone upload-view">
		<div id="uploadDescription">
			<p>
				<strong><liferay-ui:message arguments="<%= itemSelectorUploadViewDisplayContext.getRepositoryName() %>" key="drag-and-drop-to-upload-to-x-or" /></strong>
			</p>

			<p>
				<label class="btn btn-default" for="<portlet:namespace />inputFile"><liferay-ui:message key="select-file" /></label>

				<input class="hide" id="<portlet:namespace />inputFile" type="file" />
			</p>
		</div>
	</div>

	<liferay-ui:drop-here-info message="drop-files-here" />
</div>

<aui:script use="liferay-item-selector-repository-entry-browser">
	new Liferay.ItemSelectorRepositoryEntryBrowser(
		{
			closeCaption: '<%= itemSelectorUploadViewDisplayContext.getTitle(locale) %>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectorUploadViewDisplayContext.getItemSelectedEventName() %>', event);
				}
			},
			rootNode: '#itemSelectorUploadContainer',
			uploadItemReturnType: '<%= HtmlUtil.escapeAttribute(UploadableFileReturnType.class.getName()) %>',
			uploadItemUrl: '<%= itemSelectorUploadViewDisplayContext.getURL() %>'
		}
	);
</aui:script>