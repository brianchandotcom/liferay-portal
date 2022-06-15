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
CPAttachmentFileEntriesDisplayContext cpAttachmentFileEntriesDisplayContext = (CPAttachmentFileEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"cpDefinitionId", String.valueOf(cpAttachmentFileEntriesDisplayContext.getCPDefinitionId())
).build();
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpAttachmentFileEntriesDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionAttachmentsActionURL" />

	<aui:form action="<%= editProductDefinitionAttachmentsActionURL %>" cssClass="pt-4" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpAttachmentFileEntriesDisplayContext.getCPDefinitionId() %>" />
		<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

		<liferay-ui:error exception="<%= NoSuchSkuContributorCPDefinitionOptionRelException.class %>" message="there-are-no-options-set-as-sku-contributor" />

		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "images") %>'
		>
			<frontend-data-set:classic-display
				contextParams="<%= contextParams %>"
				creationMenu="<%= cpAttachmentFileEntriesDisplayContext.getCreationMenu(CPAttachmentFileEntryConstants.TYPE_IMAGE) %>"
				dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_IMAGES %>"
				formName="fm"
				id="<%= CommerceProductFDSNames.PRODUCT_IMAGES %>"
				itemsPerPage="<%= 10 %>"
				selectedItemsKey="cpattachmentFileEntryId"
			/>
		</commerce-ui:panel>

		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "attachments") %>'
		>
			<frontend-data-set:classic-display
				contextParams="<%= contextParams %>"
				creationMenu="<%= cpAttachmentFileEntriesDisplayContext.getCreationMenu(CPAttachmentFileEntryConstants.TYPE_OTHER) %>"
				dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_ATTACHMENTS %>"
				formName="fm"
				id="<%= CommerceProductFDSNames.PRODUCT_ATTACHMENTS %>"
				itemsPerPage="<%= 10 %>"
				selectedItemsKey="cpattachmentFileEntryId"
			/>
		</commerce-ui:panel>
	</aui:form>
</c:if>