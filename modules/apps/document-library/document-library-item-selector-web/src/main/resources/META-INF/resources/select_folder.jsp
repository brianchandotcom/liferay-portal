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
DLSelectFolderDisplayContext dlSelectFolderDisplayContext = (DLSelectFolderDisplayContext)request.getAttribute(DLSelectFolderDisplayContext.class.getName());

DLBreadcrumbUtil.addPortletBreadcrumbEntries(dlSelectFolderDisplayContext.getFolder(), ParamUtil.getString(request, "displayStyle"), request, dlSelectFolderDisplayContext.getIteratorPortletURL(liferayPortletResponse));
%>

<clay:container-fluid>
	<aui:form method="post" name="selectFolderFm">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<aui:button-row>
			<c:if test="<%= dlSelectFolderDisplayContext.hasAddFolderPermission() %>">
				<aui:button href="<%= String.valueOf(dlSelectFolderDisplayContext.getAddFolderPortletURL()) %>" value="add-folder" />
			</c:if>

			<aui:button cssClass="selector-button" data="<%= dlSelectFolderDisplayContext.getSelectorButtonData() %>" disabled="<%= dlSelectFolderDisplayContext.isSelectButtonDisabled() %>" value="select-this-folder" />
		</aui:button-row>

		<liferay-ui:search-container
			cssClass="pb-6"
			iteratorURL="<%= dlSelectFolderDisplayContext.getIteratorPortletURL(liferayPortletResponse) %>"
			total="<%= dlSelectFolderDisplayContext.getFoldersCount() %>"
		>
			<liferay-ui:search-container-results
				results="<%= dlSelectFolderDisplayContext.getFolders(searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.Folder"
				keyProperty="folderId"
				modelVar="curFolder"
				rowVar="row"
			>

				<%
				int folderFileEntriesCount = dlSelectFolderDisplayContext.getFolderFileEntriesCount(curFolder);
				int folderFoldersCount = dlSelectFolderDisplayContext.getFolderFoldersCount(curFolder);
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-cell-minw-200 table-title"
					name="folder"
				>
					<liferay-ui:icon
						icon="<%= dlSelectFolderDisplayContext.getIconCssClass(curFolder) %>"
						label="<%= true %>"
						localizeMessage="<%= false %>"
						markupView="lexicon"
						message="<%= HtmlUtil.escape(curFolder.getName()) %>"
						url="<%= String.valueOf(dlSelectFolderDisplayContext.getRowPortletURL(curFolder, liferayPortletResponse)) %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= dlSelectFolderDisplayContext.getRowPortletURL(curFolder, liferayPortletResponse) %>"
					name="folders"
					value="<%= String.valueOf(folderFoldersCount) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= dlSelectFolderDisplayContext.getRowPortletURL(curFolder, liferayPortletResponse) %>"
					name="documents"
					value="<%= String.valueOf(folderFileEntriesCount) %>"
				/>

				<liferay-ui:search-container-column-text>
					<aui:button cssClass="selector-button" data="<%= dlSelectFolderDisplayContext.getSelectorButtonData(curFolder) %>" disabled="<%= dlSelectFolderDisplayContext.isSelectButtonDisabled(curFolder.getFolderId()) %>" value="select" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</clay:container-fluid>