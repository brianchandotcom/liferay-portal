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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
String toolbarItem = ParamUtil.getString(request, "toolbarItem");
%>

<aui:nav-bar>
	<aui:nav>
		<portlet:renderURL var="viewRecordsURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/view" />
		</portlet:renderURL>

		<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_RECORD_SET) %>">
			<portlet:renderURL var="addRecordSetURL">
				<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addRecordSetURL %>" iconCssClass="icon-plus" label="add" selected='<%= toolbarItem.equals("add") %>' />

			<aui:nav-item anchorId="manageDDMStructuresLink" iconCssClass="icon-cog" label="manage-data-definitions" selected='<%= toolbarItem.equals("manage-data-definitions") %>' />
		</c:if>
	</aui:nav>

	<aui:nav-bar-search cssClass="pull-right" file="/html/portlet/dynamic_data_lists/record_set_search.jsp" />
</aui:nav-bar>

<c:if test="<%= DDLPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_RECORD_SET) %>">
	<aui:script use="aui-base">
			A.one('#<portlet:namespace />manageDDMStructuresLink').on('click', function() {
				Liferay.Util.openDDMPortlet(
					{
						basePortletURL: '<%= PortletURLFactoryUtil.create(request, PortletKeys.DYNAMIC_DATA_MAPPING, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>',
						dialog: {
							destroyOnHide: true
						},
						groupId: <%= scopeGroupId %>,

						<%
						Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());
						%>

						refererPortletName: '<%= portlet.getPortletName() %>',
						refererWebDAVToken: '<%= portlet.getWebDAVStorageToken() %>',
						title: '<%= UnicodeLanguageUtil.get(pageContext, "data-definitions") %>'
					}
				);
			});
	</aui:script>
</c:if>