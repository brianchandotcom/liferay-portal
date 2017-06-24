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

long assetCategoryId = ParamUtil.getLong(request, "assetCategoryId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpAttachmentFileEntry == null) ? LanguageUtil.get(request, "add-image") : cpAttachmentFileEntry.getTitle(languageId));
%>

<portlet:actionURL name="editAssetCategoryCPAttachmentFileEntry" var="editAssetCategoryCPAttachmentFileEntryActionURL" />

<aui:form action="<%= editAssetCategoryCPAttachmentFileEntryActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="assetCategoryId" type="hidden" value="<%= assetCategoryId %>" />
	<aui:input name="cpAttachmentFileEntryId" type="hidden" value="<%= (cpAttachmentFileEntry == null) ? 0 : cpAttachmentFileEntry.getCPAttachmentFileEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= cpAttachmentFileEntry %>"
			id="<%= CategoryCPAttachmentFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_CP_ATTACHMENT_FILE_ENTRY %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>