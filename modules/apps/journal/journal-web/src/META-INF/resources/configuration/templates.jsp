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
String taglibOnClick = renderResponse.getNamespace() + "openTemplatesView()";
%>

<liferay-ui:icon
	iconCssClass="icon-list-alt"
	message="templates"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
	%>

	function <portlet:namespace />openTemplatesView() {
		Liferay.Util.openDDMPortlet(
			{
				basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.VIEW), themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
				classNameId: '<%= PortalUtil.getClassNameId(DDMStructure.class) %>',
				dialog: {
					destroyOnHide: true
				},
				groupId: <%= scopeGroupId %>,
				mvcPath: '/view_template.jsp',
				refererPortletName: '<%= JournalPortletKeys.JOURNAL %>',
				refererWebDAVToken: '<%= WebDAVUtil.getStorageToken(portlet) %>',
				showAncestorScopes: true,
				showHeader: false,
				resourceClassNameId: '<%= PortalUtil.getClassNameId(JournalArticle.class) %>',
				title: '<%= UnicodeLanguageUtil.get(request, "templates") %>'
			}
		);
	}
</aui:script>