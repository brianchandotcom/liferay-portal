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

<%@ include file="/import/init.jsp" %>

<%
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");
String navigation = ParamUtil.getString(request, "navigation", "all");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", orderByType);
}
else {
	orderByCol = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-col", "create-date");
	orderByType = portalPreferences.getValue(PortletKeys.BACKGROUND_TASK, "entries-order-by-type", "desc");
}

String searchContainerId = "importLayoutProcesses";

GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);
%>

<c:if test="<%= StagingUtil.isChangeTrackingEnabled(company.getCompanyId()) %>">
	<liferay-staging:alert
		dismissible="<%= true %>"
		type="WARNING"
	>
		<liferay-ui:message key='<%= LanguageUtil.get(request, "export-import-change-lists-warning") %>' />
	</liferay-staging:alert>
</c:if>

<c:choose>
	<c:when test="<%= !GroupPermissionUtil.contains(permissionChecker, groupDisplayContextHelper.getGroupId(), ActionKeys.EXPORT_IMPORT_LAYOUTS) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
		</div>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/import/navigation.jsp" servletContext="<%= application %>" />

		<liferay-util:include page="/toolbar.jsp" servletContext="<%= application %>">
			<liferay-util:param name="mvcRenderCommandName" value="importLayoutsView" />
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<liferay-util:param name="displayStyle" value="<%= displayStyle %>" />
			<liferay-util:param name="navigation" value="<%= navigation %>" />
			<liferay-util:param name="orderByCol" value="<%= orderByCol %>" />
			<liferay-util:param name="orderByType" value="<%= orderByType %>" />
			<liferay-util:param name="searchContainerId" value="<%= searchContainerId %>" />
		</liferay-util:include>

		<liferay-util:include page="/import/processes_list/view.jsp" servletContext="<%= application %>">
			<liferay-util:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
			<liferay-util:param name="displayStyle" value="<%= displayStyle %>" />
			<liferay-util:param name="navigation" value="<%= navigation %>" />
			<liferay-util:param name="orderByCol" value="<%= orderByCol %>" />
			<liferay-util:param name="orderByType" value="<%= orderByType %>" />
			<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<liferay-util:param name="searchContainerId" value="<%= searchContainerId %>" />
		</liferay-util:include>
	</c:otherwise>
</c:choose>

<aui:script use="liferay-export-import-export-import">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="importLayouts" var="importProcessesURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_CUR_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_CUR_PARAM) %>" />
		<portlet:param name="<%= SearchContainer.DEFAULT_DELTA_PARAM %>" value="<%= ParamUtil.getString(request, SearchContainer.DEFAULT_DELTA_PARAM) %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
		<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
		<portlet:param name="displayStyle" value="<%= displayStyle %>" />
		<portlet:param name="navigation" value="<%= navigation %>" />
		<portlet:param name="orderByCol" value="<%= orderByCol %>" />
		<portlet:param name="orderByType" value="<%= orderByType %>" />
		<portlet:param name="searchContainerId" value="<%= searchContainerId %>" />
	</liferay-portlet:resourceURL>

	var exportImport = new Liferay.ExportImport({
		incompleteProcessMessageNode:
			'#<portlet:namespace />incompleteProcessMessage',
		locale: '<%= locale.toLanguageTag() %>',
		namespace: '<portlet:namespace />',
		processesNode: '#importProcessesSearchContainer',
		processesResourceURL:
			'<%= HtmlUtil.escapeJS(importProcessesURL.toString()) %>',
		timeZoneOffset: <%= timeZoneOffset %>
	});

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			exportImport.destroy();

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>