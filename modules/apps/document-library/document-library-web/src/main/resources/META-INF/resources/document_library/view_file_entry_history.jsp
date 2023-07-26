<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		searchContainer="<%= dlViewEntryHistoryDisplayContext.getSearchContainer() %>"
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
		/>
	</liferay-ui:search-container>
</div>