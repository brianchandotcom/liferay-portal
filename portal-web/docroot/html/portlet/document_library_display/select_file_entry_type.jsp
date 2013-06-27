<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long repositoryId = ParamUtil.getLong(request, "repositoryId");
long folderId = ParamUtil.getLong(request, "folderId");

List<DLFileEntryType> fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getSiteAndCompanyGroupIds(themeDisplay), folderId, true);
%>

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= fileEntryTypes %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
		escapedModel="<%= true %>"
		keyProperty="fileEntryTypeId"
		modelVar="fileEntryType"
	>
		<liferay-ui:search-container-column-text name="name">
			<a class="select-file-entry-type" data-rowId="<%= fileEntryType.getFileEntryTypeId() %>" href="javascript:;"><%= HtmlUtil.escape(fileEntryType.getName(locale)) %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-portlet-url,liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />dlFileEntryTypesSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var portletURL = Liferay.PortletURL.createURL('<%= PortletURLFactoryUtil.create(request, portletId, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>');

			portletURL.setParameter('<%= Constants.CMD %>', '<%= Constants.ADD %>');
			portletURL.setParameter('backURL', '<%= HtmlUtil.escape(backURL) %>');
			portletURL.setParameter('fileEntryTypeId', link.getAttribute('data-rowId'));
			portletURL.setParameter('folderId', '<%= folderId %>');
			portletURL.setParameter('redirect', '<%= HtmlUtil.escape(redirect) %>');
			portletURL.setParameter('repositoryId', '<%= repositoryId %>');
			portletURL.setParameter('struts_action', '/document_library/edit_file_entry');

			Liferay.Util.getOpener().location.href = portletURL.toString();

			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />selectDocumentType'
				}
			);
		},
		'.select-file-entry-type'
	);
</aui:script>