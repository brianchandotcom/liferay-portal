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
DLViewEntryHistoryDisplayContext dlViewEntryHistoryDisplayContext = (DLViewEntryHistoryDisplayContext)request.getAttribute(DLViewEntryHistoryDisplayContext.class.getName());

FileEntry fileEntry = dlViewEntryHistoryDisplayContext.getFileEntry();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(dlViewEntryHistoryDisplayContext.getBackURL());

renderResponse.setTitle(fileEntry.getTitle());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= dlViewEntryHistoryDisplayContext.getNavigationItems() %>"
/>

<div class="container-fluid container-fluid-max-xl">
	<liferay-ui:search-container
		id="articleVersions"
		searchContainer="<%= dlViewEntryHistoryDisplayContext.getFileVersionSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileVersion"
			modelVar="fileVersion"
		>
			<liferay-ui:search-container-column-text
				name="id"
				value="<%= String.valueOf(fileVersion.getFileVersionId()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				name="title"
				value="<%= HtmlUtil.escape(fileVersion.getTitle()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-100"
				name="version"
			/>

			<liferay-ui:search-container-column-status
				name="status"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				name="modified-date"
				property="modifiedDate"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-100"
				name="author"
				value="<%= HtmlUtil.escape(fileVersion.getStatusByUserName()) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				DLViewFileEntryHistoryDisplayContext dlViewFileEntryHistoryDisplayContext = dlDisplayContextProvider.getDLViewFileEntryHistoryDisplayContext(request, response, fileVersion);
				%>

				<clay:dropdown-actions
					aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
					dropdownItems="<%= dlViewFileEntryHistoryDisplayContext.getActionDropdownItems() %>"
					propsTransformer="document_library/js/DLFileEntryDropdownPropsTransformer"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
			paginate="<%= false %>"
		/>
	</liferay-ui:search-container>
</div>