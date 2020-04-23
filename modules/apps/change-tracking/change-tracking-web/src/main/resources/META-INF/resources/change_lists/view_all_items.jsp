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

<%@ include file="/change_lists/init.jsp" %>

<%
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

SearchContainer<CTEntry> searchContainer = viewChangesDisplayContext.getSearchContainer();

ViewChangesManagementToolbarDisplayContext viewChangesManagementToolbarDisplayContext = new ViewChangesManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, searchContainer);
%>

<clay:management-toolbar
	displayContext="<%= viewChangesManagementToolbarDisplayContext %>"
	id="viewChangesManagementToolbar"
/>

<clay:container-fluid>
	<liferay-ui:search-container
		cssClass="change-lists-changes-table change-lists-table"
		searchContainer="<%= searchContainer %>"
		var="reviewChangesSearchContainer"
	>
		<liferay-ui:search-container-row
			className="com.liferay.change.tracking.model.CTEntry"
			escapedModel="<%= true %>"
			keyProperty="ctEntryId"
			modelVar="ctEntry"
		>
			<liferay-ui:search-container-column-text>
				<span class="lfr-portal-tooltip" title="<%= HtmlUtil.escape(ctEntry.getUserName()) %>">
					<liferay-ui:user-portrait
						userId="<%= ctEntry.getUserId() %>"
					/>
				</span>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="change"
			>
				<div class="change-list-name">
					<%= ctDisplayRendererRegistry.getTypeName(ctEntry, locale) %>
				</div>

				<div class="change-list-description">
					<%= ctDisplayRendererRegistry.getEntryTitle(ctEntry, request) %>
				</div>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smaller"
				name="last-modified"
			>

				<%
				Date modifiedDate = ctEntry.getModifiedDate();
				%>

				<liferay-ui:message arguments="<%= LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true) %>" key="x-ago" translateArguments="<%= false %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/change_lists/ct_entry_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			searchContainer="<%= searchContainer %>"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>