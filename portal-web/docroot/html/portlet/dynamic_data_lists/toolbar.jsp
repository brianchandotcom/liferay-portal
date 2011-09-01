<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String toolbarItem = ParamUtil.getString(request, "toolbarItem", "view-all");
%>

<div class="lfr-portlet-toolbar">
	<portlet:renderURL var="viewEntriesURL">
		<portlet:param name="struts_action" value="/dynamic_data_lists/view" />
	</portlet:renderURL>

	<span class="lfr-toolbar-button view-button <%= toolbarItem.equals("view-all") ? "current" : StringPool.BLANK %>">
		<a href="<%= viewEntriesURL %>"><liferay-ui:message key="view-all" /></a>
	</span>

	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_RECORD_SET) %>">
		<portlet:renderURL var="addRecordSetURL">
			<portlet:param name="struts_action" value="/dynamic_data_lists/edit_record_set" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
		</portlet:renderURL>

		<span class="lfr-toolbar-button add-button <%= toolbarItem.equals("add") ? "current" : StringPool.BLANK %>">
			<a href="<%= addRecordSetURL %>"><liferay-ui:message key="add" /></a>
		</span>

		<span class="lfr-toolbar-button view-structures <%= toolbarItem.equals("manage-data-definitions") ? "current" : StringPool.BLANK %>">
			<a href="javascript:void(0);" id="<portlet:namespace />manageDDMStructuresLink"><liferay-ui:message key="manage-data-definitions" /></a>
		</span>
	</c:if>
</div>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />manageDDMStructuresLink').on('click', function() {
		Liferay.Util.openDDMPortlet(
			{
				dialog: {
					stack: false,
					width:820
				},
				storageType: '<%= PropsValues.DYNAMIC_DATA_LISTS_STORAGE_TYPE %>',
				structureName: 'data-definition',
				structureType: 'com.liferay.portlet.dynamicdatalists.model.DDLRecordSet',
				title: '<liferay-ui:message key="data-definitions" />'
			}
		);
	});
</aui:script>