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

<%@ include file="/export/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);

PortletURL exportProcessesURL = PortalUtil.getControlPanelPortletURL(request, ExportImportPortletKeys.EXPORT, PortletRequest.RENDER_PHASE);

exportProcessesURL.setParameter("mvcPath", "/export/view.jsp");

portletDisplay.setURLBack(exportProcessesURL.toString());

renderResponse.setTitle(LanguageUtil.get(request, "export-templates"));
%>

<liferay-staging:defineObjects />

<%
if (liveGroup == null) {
	liveGroup = group;
	liveGroupId = groupId;
}
%>

<c:if test="<%= StagingUtil.isChangeTrackingEnabled(company.getCompanyId()) %>">
	<liferay-staging:alert
		dismissible="<%= true %>"
		type="WARNING"
	>
		<liferay-ui:message key='<%= LanguageUtil.get(request, "export-import-change-lists-warning") %>' />
	</liferay-staging:alert>
</c:if>

<liferay-util:include page="/export/export_templates/navigation.jsp" servletContext="<%= application %>" />

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcRenderCommandName" value="viewExportConfigurations" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
	<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
</liferay-portlet:renderURL>

<portlet:actionURL name="editExportConfiguration" var="restoreTrashEntriesURL">
	<portlet:param name="mvcRenderCommandName" value="viewExportConfigurations" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-trash:undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<%
ExportTemplatesToolbarDisplayContext exportTemplatesToolbarDisplayContext = new ExportTemplatesToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, liveGroupId, company, portletURL);
%>

<clay:management-toolbar
	displayContext="<%= exportTemplatesToolbarDisplayContext %>"
	searchFormName="searchFm"
	selectable="false"
	showCreationMenu="<%= !StagingUtil.isChangeTrackingEnabled(company.getCompanyId()) %>"
	showSearch="<%= true %>"
/>

<div class="container-fluid-1280">
	<aui:form action="<%= portletURL %>">
		<liferay-ui:search-container
			searchContainer="<%= exportTemplatesToolbarDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.exportimport.kernel.model.ExportImportConfiguration"
				keyProperty="exportImportConfigurationId"
				modelVar="exportImportConfiguration"
			>
				<liferay-ui:search-container-column-text
					cssClass="export-configuration-user-column"
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
					<portlet:param name="mvcRenderCommandName" value="editExportConfiguration" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
					<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
					<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
					<portlet:param name="liveGroupId" value="<%= String.valueOf(liveGroupId) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				</liferay-portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(exportImportConfiguration.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="description"
					value="<%= HtmlUtil.escape(exportImportConfiguration.getDescription()) %>"
				/>

				<liferay-ui:search-container-column-date
					name="create-date"
					value="<%= exportImportConfiguration.getCreateDate() %>"
				/>

				<%
				request.setAttribute("view.jsp-groupId", groupId);
				request.setAttribute("view.jsp-liveGroupId", liveGroupId);
				request.setAttribute("view.jsp-privateLayout", privateLayout);
				%>

				<liferay-ui:search-container-column-jsp
					path="/export/export_templates/actions.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>