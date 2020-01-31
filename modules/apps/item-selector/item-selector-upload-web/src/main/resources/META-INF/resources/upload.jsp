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

ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver = itemSelectorUploadViewDisplayContext.getItemSelectorReturnTypeResolver();

Class<?> itemSelectorReturnTypeClass = itemSelectorReturnTypeResolver.getItemSelectorReturnTypeClass();

String uploadURL = itemSelectorUploadViewDisplayContext.getURL();

String namespace = itemSelectorUploadViewDisplayContext.getNamespace();

if (Validator.isNotNull(namespace)) {
	uploadURL = HttpUtil.addParameter(uploadURL, namespace + "returnType", itemSelectorReturnTypeClass.getName());
}

Map<String, Object> context = new HashMap<>();

context.put("closeCaption", itemSelectorUploadViewDisplayContext.getTitle(locale));
context.put("eventName", itemSelectorUploadViewDisplayContext.getItemSelectedEventName());
context.put("maxFileSize", itemSelectorUploadViewDisplayContext.getMaxFileSize());
context.put("rootNode", "#itemSelectorUploadContainer");
context.put("uploadItemReturnType", HtmlUtil.escapeAttribute(itemSelectorReturnTypeClass.getName()));
context.put("uploadItemURL", uploadURL);
context.put("validExtensions", ArrayUtil.isEmpty(itemSelectorUploadViewDisplayContext.getExtensions()) ? "*" : StringUtil.merge(itemSelectorUploadViewDisplayContext.getExtensions()));
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

	<div class="item-selector-preview-container"></div>
</div>

<liferay-frontend:component
	context="<%= context %>"
	module="js/index.es"
/>