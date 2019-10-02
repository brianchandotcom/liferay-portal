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
	<div class="drop-enabled drop-zone item-selector upload-view">
		<div id="uploadDescription">
			<c:if test="<%= !BrowserSnifferUtil.isMobile(request) %>">
				<p>
					<strong><liferay-ui:message arguments="<%= itemSelectorUploadViewDisplayContext.getRepositoryName() %>" key="drag-and-drop-to-upload-to-x-or" /></strong>
				</p>
			</c:if>

			<p>
				<input accept="<%= ArrayUtil.isEmpty(itemSelectorUploadViewDisplayContext.getExtensions()) ? "*" : StringUtil.merge(itemSelectorUploadViewDisplayContext.getExtensions()) %>" class="input-file" id="<portlet:namespace />inputFile" type="file" />

				<label class="btn btn-secondary" for="<portlet:namespace />inputFile"><liferay-ui:message key="select-file" /></label>
			</p>
		</div>
	</div>

	<liferay-ui:drop-here-info
		message="drop-files-here"
	/>
</div>

<aui:script use="liferay-item-selector-repository-entry-browser">

	<%
	ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver = itemSelectorUploadViewDisplayContext.getItemSelectorReturnTypeResolver();

	Class<?> itemSelectorReturnTypeClass = itemSelectorReturnTypeResolver.getItemSelectorReturnTypeClass();

	String uploadURL = itemSelectorUploadViewDisplayContext.getURL();

	String namespace = itemSelectorUploadViewDisplayContext.getNamespace();

	if (Validator.isNotNull(namespace)) {
		uploadURL = HttpUtil.addParameter(uploadURL, namespace + "returnType", itemSelectorReturnTypeClass.getName());
	}
	%>

	new Liferay.ItemSelectorRepositoryEntryBrowser({
		closeCaption:
			'<%= itemSelectorUploadViewDisplayContext.getTitle(locale) %>',
		maxFileSize: '<%= itemSelectorUploadViewDisplayContext.getMaxFileSize() %>',
		on: {
			selectedItem: function(event) {
				Liferay.Util.getOpener().Liferay.fire(
					'<%= itemSelectorUploadViewDisplayContext.getItemSelectedEventName() %>',
					event
				);
			}
		},
		rootNode: '#itemSelectorUploadContainer',
		uploadItemReturnType:
			'<%= HtmlUtil.escapeAttribute(itemSelectorReturnTypeClass.getName()) %>',
		uploadItemURL: '<%= uploadURL.toString() %>',
		validExtensions:
			'<%= ArrayUtil.isEmpty(itemSelectorUploadViewDisplayContext.getExtensions()) ? "*" : StringUtil.merge(itemSelectorUploadViewDisplayContext.getExtensions()) %>'
	});
</aui:script>