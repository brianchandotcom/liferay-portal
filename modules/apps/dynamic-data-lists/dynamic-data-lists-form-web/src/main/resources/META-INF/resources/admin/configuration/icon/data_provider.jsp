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

<%@ include file="/admin/init.jsp" %>

<%
String taglibOnClick = renderResponse.getNamespace() + "openDataProviderView()";
Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
%>

<liferay-ui:icon
	message="data-providers"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>
	function <portlet:namespace />openDataProviderView() {
		Liferay.Util.openDDMDataProviderPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMDataProviderInstance.class.getName(), PortletProvider.Action.EDIT), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				dialog: {
					destroyOnHide: true
				},
				groupId: <%= scopeGroupId %>,
				refererPortletName: '<%= DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN %>',
				title: '<%= UnicodeLanguageUtil.get(request, "data-providers") %>'
			}
		);
	}
</aui:script>