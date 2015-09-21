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
SearchContainer searchContainer = (SearchContainer)request.getAttribute(WebKeys.SEARCH_CONTAINER);

String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav" searchContainer="<%= searchContainer %>">
		<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, DDLActionKeys.ADD_RECORD_SET) %>">
			<portlet:renderURL var="addRecordSetURL">
				<portlet:param name="mvcPath" value="/edit_record_set.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addRecordSetURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />

			<aui:nav-item anchorId="manageDDMStructuresLink" iconCssClass="icon-cog" label="manage-data-definitions" selected='<%= toolbarItem.equals("manage-data-definitions") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search>
		<liferay-util:include page="/record_set_search.jsp" servletContext="<%= application %>" />
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, DDLActionKeys.ADD_RECORD_SET) %>">
	<aui:script>
		AUI.$('#<portlet:namespace />manageDDMStructuresLink').on(
			'click',
			function() {
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
						title: '<%= UnicodeLanguageUtil.get(request, "data-definitions") %>'
					}
				);
			}
		);
	</aui:script>
</c:if>