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
CPDefinition cpDefinition = (CPDefinition)request.getAttribute("view.jsp-cpDefinition");
CPType cpType = (CPType)request.getAttribute("view.jsp-cpType");
PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
boolean showSearch = GetterUtil.get(request.getAttribute("view.jsp-showSearch"), false);
String toolbarItem = GetterUtil.getString(request.getAttribute("view.jsp-toolbarItem"), "");

long cpDefinitionId = 0;

if (cpDefinition != null) {
	cpDefinitionId = cpDefinition.getCPDefinitionId();
}
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<liferay-portlet:renderURL varImpl="viewProductDefinitionDetailsURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
			<portlet:param name="mvcRenderCommandName" value="editProductDefinition" />
			<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
			<portlet:param name="toolbarItem" value="view-product-definition-details" />
		</liferay-portlet:renderURL>

		<aui:nav-item
			href="<%= viewProductDefinitionDetailsURL.toString() %>"
			label='<%= LanguageUtil.get(request, "details") %>'
			selected='<%= toolbarItem.equals("view-product-definition-details") %>'
		/>

		<c:if test="<%= cpDefinition != null %>">
			<c:if test="<%= (cpType != null) && Validator.isNotNull(cpType.getCPDefinitionEditUrl(cpDefinitionId, request)) %>">
				<aui:nav-item
					href="<%= cpType.getCPDefinitionEditUrl(cpDefinitionId, request) %>"
					label="<%= cpType.getLabel(locale) %>"
					selected="<%= toolbarItem.equals(cpType.getName()) %>"
				/>
			</c:if>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionImagesURL">
				<portlet:param name="mvcRenderCommandName" value="viewAttachmentFileEntries" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="type" value="<%= String.valueOf(CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-images" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionImagesURL.toString() %>"
				label='<%= LanguageUtil.get(request, "images") %>'
				selected='<%= toolbarItem.equals("view-product-definition-images") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionOptionRelsURL">
				<portlet:param name="mvcRenderCommandName" value="viewProductDefinitionOptionRels" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-options" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionOptionRelsURL.toString() %>"
				label='<%= LanguageUtil.get(request, "options") %>'
				selected='<%= toolbarItem.equals("view-product-definition-options") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionSpecificationOptionValuesURL">
				<portlet:param name="mvcRenderCommandName" value="viewProductDefinitionSpecificationOptionValues" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-specification-options" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionSpecificationOptionValuesURL.toString() %>"
				label='<%= LanguageUtil.get(request, "specification-options") %>'
				selected='<%= toolbarItem.equals("view-product-definition-specification-options") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionInstancesURL">
				<portlet:param name="mvcRenderCommandName" value="viewProductInstances" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-instances" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionInstancesURL.toString() %>"
				label='<%= LanguageUtil.get(request, "skus") %>'
				selected='<%= toolbarItem.equals("view-product-instances") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionAttachmentsURL">
				<portlet:param name="mvcRenderCommandName" value="viewAttachmentFileEntries" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="type" value="<%= String.valueOf(CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_OTHER) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-attachments" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionAttachmentsURL.toString() %>"
				label='<%= LanguageUtil.get(request, "attachments") %>'
				selected='<%= toolbarItem.equals("view-product-definition-attachments") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionRelatedProductsURL">
				<portlet:param name="mvcRenderCommandName" value="viewCPDefinitionLinks" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-related-products" />
				<portlet:param name="type" value="<%= String.valueOf(CPConstants.DEFINITION_LINK_TYPE_RELATED) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionRelatedProductsURL.toString() %>"
				label='<%= LanguageUtil.get(request, "related-products") %>'
				selected='<%= toolbarItem.equals("view-product-definition-related-products") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionUpSellProductsURL">
				<portlet:param name="mvcRenderCommandName" value="viewCPDefinitionLinks" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-up-sell-products" />
				<portlet:param name="type" value="<%= String.valueOf(CPConstants.DEFINITION_LINK_TYPE_UP_SELL) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionUpSellProductsURL.toString() %>"
				label='<%= LanguageUtil.get(request, "up-sell-products") %>'
				selected='<%= toolbarItem.equals("view-product-definition-up-sell-products") %>'
			/>

			<liferay-portlet:renderURL varImpl="viewProductDefinitionCrossSellProductsURL">
				<portlet:param name="mvcRenderCommandName" value="viewCPDefinitionLinks" />
				<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
				<portlet:param name="toolbarItem" value="view-product-definition-cross-sell-products" />
				<portlet:param name="type" value="<%= String.valueOf(CPConstants.DEFINITION_LINK_TYPE_CROSS_SELL) %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item
				href="<%= viewProductDefinitionCrossSellProductsURL.toString() %>"
				label='<%= LanguageUtil.get(request, "cross-sell-products") %>'
				selected='<%= toolbarItem.equals("view-product-definition-cross-sell-products") %>'
			/>
		</c:if>
	</aui:nav>

	<c:if test="<%= showSearch %>">
		<aui:form action="<%= portletURL.toString() %>" name="searchFm">
			<aui:nav-bar-search>
				<liferay-ui:input-search markupView="lexicon" />
			</aui:nav-bar-search>
		</aui:form>
	</c:if>
</aui:nav-bar>