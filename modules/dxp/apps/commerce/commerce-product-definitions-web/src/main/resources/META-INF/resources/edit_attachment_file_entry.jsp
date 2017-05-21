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
String toolbarItem = ParamUtil.getString(request, "toolbarItem");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("toolbarItem", toolbarItem);
portletURL.setParameter("mvcRenderCommandName", "editCPAttachmentFileEntry");

CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpAttachmentFileEntriesDisplayContext.getCPDefinition();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

CPAttachmentFileEntry cpAttachmentFileEntry = cpAttachmentFileEntriesDisplayContext.getCPAttachmentFileEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle((cpDefinition == null) ? LanguageUtil.get(request, "add-single-attachment") : cpDefinition.getTitle(languageId));
%>

<portlet:actionURL name="editProductDefinitionOptionRel" var="editProductDefinitionOptionRelActionURL" />

<div class="container-fluid-1280 entry-body">
	<aui:form action="<%= editProductDefinitionOptionRelActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

		<div class="lfr-form-content">
			<liferay-ui:form-navigator
				backURL="<%= backURL %>"
				formModelBean="<%= cpAttachmentFileEntry %>"
				id="<%= CPAttachmentFileEntryFormNavigatorConstants.FORM_NAVIGATOR_ID_CP_ATTACHMENT_FILE_ENTRY %>"
				markupView="lexicon"
			/>
		</div>
	</aui:form>
</div>