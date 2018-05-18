<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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