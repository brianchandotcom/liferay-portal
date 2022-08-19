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

ItemSelectorReturnTypeResolver<?, ?> itemSelectorReturnTypeResolver = itemSelectorUploadViewDisplayContext.getItemSelectorReturnTypeResolver();

Class<?> itemSelectorReturnTypeClass = itemSelectorReturnTypeResolver.getItemSelectorReturnTypeClass();

String uploadURL = itemSelectorUploadViewDisplayContext.getURL();

String namespace = itemSelectorUploadViewDisplayContext.getNamespace();

if (Validator.isNotNull(namespace)) {
	uploadURL = HttpComponentsUtil.addParameter(uploadURL, namespace + "returnType", itemSelectorReturnTypeClass.getName());
}
%>

<clay:container-fluid
	cssClass="lfr-item-viewer"
	id="itemSelectorUploadContainer"
>
	<div class="dropzone-wrapper dropzone-wrapper-search-container-empty">
		<div class="dropzone dropzone-disabled"><span aria-hidden="true" class="loading-animation loading-animation-sm"></span></div>

		<react:component
			module="js/index.es"
			props='<%=
				HashMapBuilder.<String, Object>put(
					"closeCaption", itemSelectorUploadViewDisplayContext.getTitle(locale)
				).put(
					"editImageURL", uploadURL
				).put(
					"eventName", itemSelectorUploadViewDisplayContext.getItemSelectedEventName()
				).put(
					"maxFileSize", itemSelectorUploadViewDisplayContext.getMaxFileSize()
				).put(
					"rootNode", "#itemSelectorUploadContainer"
				).put(
					"uploadItemReturnType", HtmlUtil.escapeAttribute(itemSelectorReturnTypeClass.getName())
				).put(
					"uploadItemURL", uploadURL
				).put(
					"validExtensions", StringUtil.merge(itemSelectorUploadViewDisplayContext.getExtensions())
				).build()
			%>'
		/>
	</div>
</clay:container-fluid>