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

CPDefinition cpDefinition = cpAttachmentFileEntriesDisplayContext.getCPDefinition();

long cpDefinitionId = cpAttachmentFileEntriesDisplayContext.getCPDefinitionId();

String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-product-definition-images");

PortletURL portletURL = cpAttachmentFileEntriesDisplayContext.getPortletURL();

renderResponse.setTitle(cpDefinition.getTitle(languageId));

request.setAttribute("view.jsp-cpDefinition", cpDefinition);
request.setAttribute("view.jsp-cpType", cpAttachmentFileEntriesDisplayContext.getCPType());
request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-showSearch", true);
request.setAttribute("view.jsp-toolbarItem", toolbarItem);
%>

<liferay-util:include page="/definition_navbar.jsp" servletContext="<%= application %>" />

<liferay-portlet:renderURL var="addAttachmentFileEntryURL">
	<portlet:param name="mvcRenderCommandName" value="editCPAttachmentFileEntry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="cpDefinitionId" value="<%= String.valueOf(cpDefinitionId) %>" />
</liferay-portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-single-attachment") %>' url="<%= addAttachmentFileEntryURL.toString() %>" />
	<liferay-frontend:add-menu-item id="addAttachmentFileEntry" title='<%= LanguageUtil.get(request, "add-multiple-attachments") %>' url="javascript:;" />
</liferay-frontend:add-menu>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addAttachmentFileEntry').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'addCPAttachmentFileEntry',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {

								$('#<portlet:namespace />cpOptionIds').val(selectedItems);

								var addCPDefinitionOptionRelFm = $('#<portlet:namespace />addCPDefinitionOptionRelFm');

								submitForm(addCPDefinitionOptionRelFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpDefinition.getTitle(languageId) %>" key="add-new-option-to-x" />',
					url: '<%= cpAttachmentFileEntriesDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>