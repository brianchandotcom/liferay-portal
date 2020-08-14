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

<%@ include file="/document_library/init.jsp" %>

<%
long folderId = ParamUtil.getLong(request, "folderId");

DLViewMoreMenuItemsDisplayContext dlViewMoreMenuItemsDisplayContext = new DLViewMoreMenuItemsDisplayContext(folderId, renderRequest, renderResponse);
%>

<clay:navigation-bar
	navigationItems="<%= dlViewMoreMenuItemsDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	clearResultsURL="<%= dlViewMoreMenuItemsDisplayContext.getClearResultsURL() %>"
	componentId="dlViewMoreMenuItemsManagementToolbar"
	disabled="<%= dlViewMoreMenuItemsDisplayContext.getTotalItems() == 0 %>"
	itemsTotal="<%= dlViewMoreMenuItemsDisplayContext.getTotalItems() %>"
	searchActionURL="<%= dlViewMoreMenuItemsDisplayContext.getSearchActionURL() %>"
	searchFormName="fm"
	selectable="<%= false %>"
/>

<aui:form cssClass="container-fluid-1280" name="addMenuItemFm">
	<liferay-ui:search-container
		searchContainer="<%= dlViewMoreMenuItemsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.document.library.kernel.model.DLFileEntryType"
			escapedModel="<%= true %>"
			keyProperty="fileEntryTypeId"
			modelVar="fileEntryType"
		>

			<%
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"fileEntryTypeId", String.valueOf(fileEntryType.getFileEntryTypeId())
			).build();
			%>

			<liferay-ui:search-container-column-text
				name="name"
			>
				<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
					<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>
				</aui:a>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="scope"
				value="<%= dlViewMoreMenuItemsDisplayContext.getDLFileEntryTypeScopeName(fileEntryType, locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(fileEntryType.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= fileEntryType.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />addMenuItemFm').delegate(
		'click',
		function (event) {
			Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(dlViewMoreMenuItemsDisplayContext.getEventName()) %>',
				{
					fileEntryTypeId: event.currentTarget.attr(
						'data-fileEntryTypeId'
					),
				}
			);
		},
		'.selector-button'
	);
</aui:script>