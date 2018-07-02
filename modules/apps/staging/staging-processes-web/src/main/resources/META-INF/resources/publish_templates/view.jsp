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
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
String layoutSetBranchName = ParamUtil.getString(request, "layoutSetBranchName");

portletDisplay.setShowBackIcon(true);

PortletURL stagingProcessesURL = PortalUtil.getControlPanelPortletURL(request, StagingProcessesPortletKeys.STAGING_PROCESSES, PortletRequest.RENDER_PHASE);

stagingProcessesURL.setParameter("mvcPath", "/view.jsp");

portletDisplay.setURLBack(stagingProcessesURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "publish-templates"));
%>

<portlet:actionURL name="editPublishConfiguration" var="restoreTrashEntriesURL">
	<portlet:param name="mvcRenderCommandName" value="viewPublishConfigurations" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcRenderCommandName" value="viewPublishConfigurations" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
	<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</liferay-portlet:renderURL>

<%
StagingProcessesWebPublishTemplatesToolbarDisplayContext stagingProcessesWebPublishTemplatesToolbarDisplayContext = new StagingProcessesWebPublishTemplatesToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, pageContext, portletURL);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= publishTemplatesDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= stagingProcessesWebPublishTemplatesToolbarDisplayContext %>"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showCreationMenu="<%= true %>"
	showSearch="<%= true %>"
/>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<aui:form action="<%= portletURL %>">
		<liferay-ui:search-container
			searchContainer="<%= stagingProcessesWebPublishTemplatesToolbarDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.exportimport.kernel.model.ExportImportConfiguration"
				keyProperty="exportImportConfigurationId"
				modelVar="exportImportConfiguration"
			>
				<liferay-ui:search-container-column-text
					cssClass="background-task-user-column"
					name="user"
				>
					<liferay-ui:user-display
						displayStyle="3"
						showUserDetails="<%= false %>"
						showUserName="<%= false %>"
						userId="<%= exportImportConfiguration.getUserId() %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
					<portlet:param name="layoutSetBranchName" value="<%= layoutSetBranchName %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="description"
					value="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>"
				/>

				<liferay-ui:search-container-column-date
					name="create-date"
					value="<%= exportImportConfiguration.getCreateDate() %>"
				/>

				<%
				request.setAttribute("view.jsp-layoutSetBranchId", layoutSetBranchId);
				request.setAttribute("view.jsp-layoutSetBranchName", layoutSetBranchName);
				%>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/publish_templates/actions.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>