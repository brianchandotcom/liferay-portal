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
CategoryCPAttachmentFileEntriesDisplayContext categoryCPAttachmentFileEntriesDisplayContext = (CategoryCPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPAttachmentFileEntry cpAttachmentFileEntry = categoryCPAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

long categoryId = ParamUtil.getLong(request, "categoryId");

long fileEntryId = BeanParamUtil.getLong(cpAttachmentFileEntry, request, "fileEntryId");
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<aui:model-context bean="<%= cpAttachmentFileEntry %>" model="<%= CPAttachmentFileEntry.class %>" />

<liferay-ui:error exception="<%= CPAttachmentFileEntryFileEntryIdException.class %>" message="please-select-an-existing-file" />

<portlet:actionURL name="uploadTempCategoryAttachment" var="uploadCoverImageURL">
	<portlet:param name="categoryId" value="<%= String.valueOf(categoryId) %>" />
</portlet:actionURL>

<div class="lfr-attachment-cover-image-selector">
	<liferay-item-selector:image-selector
		draggableImage="vertical"
		fileEntryId="<%= fileEntryId %>"
		itemSelectorEventName='<%= "addCategoryCPAttachmentFileEntry" %>'
		itemSelectorURL="<%= categoryCPAttachmentFileEntriesDisplayContext.getItemSelectorUrl() %>"
		maxFileSize="<%= categoryCPAttachmentFileEntriesDisplayContext.getImageMaxSize() %>"
		paramName="fileEntry"
		uploadURL="<%= uploadCoverImageURL %>"
		validExtensions='<%= StringUtil.merge(categoryCPAttachmentFileEntriesDisplayContext.getImageExtensions(), ", ") %>'
	/>
</div>

<aui:input name="title" />

<aui:input name="priority" />