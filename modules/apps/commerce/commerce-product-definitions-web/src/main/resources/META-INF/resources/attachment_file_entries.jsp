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

PortletURL portletURL = cpAttachmentFileEntriesDisplayContext.getPortletURL();

Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"cpDefinitionId", String.valueOf(cpAttachmentFileEntriesDisplayContext.getCPDefinitionId())
).build();
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpAttachmentFileEntriesDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<aui:form action="<%= portletURL.toString() %>" cssClass="pt-4" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:error exception="<%= NoSuchSkuContributorCPDefinitionOptionRelException.class %>" message="there-are-no-options-set-as-sku-contributor" />

		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "images") %>'
		>
			<clay:data-set-display
				contextParams="<%= contextParams %>"
				creationMenu="<%= cpAttachmentFileEntriesDisplayContext.getCreationMenu(CPAttachmentFileEntryConstants.TYPE_IMAGE) %>"
				dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_IMAGES %>"
				formId="fm"
				id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_IMAGES %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= portletURL %>"
				selectedItemsKey="cpattachmentFileEntryId"
			/>
		</commerce-ui:panel>

		<commerce-ui:panel
			bodyClasses="p-0"
			title='<%= LanguageUtil.get(request, "attachments") %>'
		>
			<clay:data-set-display
				contextParams="<%= contextParams %>"
				creationMenu="<%= cpAttachmentFileEntriesDisplayContext.getCreationMenu(CPAttachmentFileEntryConstants.TYPE_OTHER) %>"
				dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ATTACHMENTS %>"
				formId="fm"
				id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ATTACHMENTS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= portletURL %>"
				selectedItemsKey="cpattachmentFileEntryId"
			/>
		</commerce-ui:panel>
	</aui:form>
</c:if>