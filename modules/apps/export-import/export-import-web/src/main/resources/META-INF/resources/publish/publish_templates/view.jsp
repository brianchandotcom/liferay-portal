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
ExportImportPublishTemplatesDisplayContext exportImportPublishTemplatesDisplayContext = new ExportImportPublishTemplatesDisplayContext(request, liferayPortletResponse, renderRequest);

SearchContainer<ExportImportConfiguration> exportImportConfigurationSearchContainer = exportImportPublishTemplatesDisplayContext.getSearchContainer();
%>

<div class="export-dialog-tree">
	<clay:container-fluid>
		<div class="alert alert-info">
			<liferay-ui:message key="publish-templates-can-be-administered-in-the-control-menu" />
		</div>

		<liferay-portlet:renderURL varImpl="searchURL">
			<portlet:param name="mvcRenderCommandName" value="/export_import/publish_layouts" />
			<portlet:param name="publishConfigurationButtons" value="saved" />
		</liferay-portlet:renderURL>

		<clay:management-toolbar
			clearResultsURL="<%=
				PortletURLBuilder.create(
					PortletURLUtil.clone(currentURLObj, renderResponse)
				).setKeywords(
					StringPool.BLANK
				).buildString()
			%>"
			itemsTotal="<%= exportImportConfigurationSearchContainer.getTotal() %>"
			searchActionURL="<%= searchURL.toString() %>"
			selectable="<%= false %>"
		/>

		<aui:form action="<%= exportImportPublishTemplatesDisplayContext.getPortletURL() %>">
			<liferay-ui:search-container
				searchContainer="<%= exportImportConfigurationSearchContainer %>"
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
						<portlet:param name="mvcRenderCommandName" value="/export_import/publish_layouts" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= exportImportPublishTemplatesDisplayContext.isLocalPublishing() ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="exportImportConfigurationId" value="<%= String.valueOf(exportImportConfiguration.getExportImportConfigurationId()) %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(exportImportPublishTemplatesDisplayContext.getGroupId()) %>" />
						<portlet:param name="privateLayout" value='<%= String.valueOf(ParamUtil.getBoolean(request, "privateLayout")) %>' />
					</liferay-portlet:renderURL>

					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="name"
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

					<liferay-ui:search-container-column-jsp
						align="right"
						cssClass="entry-action"
						path="/publish/publish_templates/actions.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</aui:form>
	</clay:container-fluid>
</div>