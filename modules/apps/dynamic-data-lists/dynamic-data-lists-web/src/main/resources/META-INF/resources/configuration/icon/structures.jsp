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

<liferay-ui:icon
	message="manage-data-definitions"
	onClick='<%= renderResponse.getNamespace() + "manageDDMStructuresLink();" %>'
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />manageDDMStructuresLink() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMStructure.class.getName(), PortletProvider.Action.EDIT), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				groupId: <%= scopeGroupId %>,

				<%
				Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
				%>

				refererPortletName: '<%= portlet.getPortletName() %>',
				refererWebDAVToken: '<%= WebDAVUtil.getStorageToken(portlet) %>',
				showAncestorScopes: true,
				title: '<%= UnicodeLanguageUtil.get(request, "data-definitions") %>'
			}
		);
	}
</aui:script>