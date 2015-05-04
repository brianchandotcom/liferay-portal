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
String itemSelectedCallback = (String)request.getAttribute(DLItemSelectorView.ITEM_SELECTED_CALLBACK);
PortletURL portletURL = (PortletURL)request.getAttribute(DLItemSelectorView.PORTLET_URL);
DLItemSelectorCriterion dlItemSelectorCriterion = (DLItemSelectorCriterion)request.getAttribute(DLItemSelectorView.DL_ITEM_SELECTOR_CRITERION);

long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);

long folderId = ParamUtil.getLong(request, "folderId", dlItemSelectorCriterion.getFolderId());

long repositoryId = scopeGroupId;

Folder folder = null;

if (folderId > 0) {
	folder = DLAppLocalServiceUtil.getFolder(folderId);
}

if (folder != null) {
	groupId = folder.getGroupId();

	repositoryId = folder.getRepositoryId();

	Group group = GroupLocalServiceUtil.fetchGroup(groupId);

	if ((group != null) && !group.isStagedRemotely() && group.isStagingGroup() && !group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {
		group = group.getLiveGroup();

		groupId = group.getGroupId();
	}

	PortletURL breadcrumbURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	breadcrumbURL.setParameter("folderId", String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

	PortalUtil.addPortletBreadcrumbEntry(request, themeDisplay.translate("home"), breadcrumbURL.toString());

	breadcrumbURL.setParameter("folderId", String.valueOf(folderId));

	DLUtil.addPortletBreadcrumbEntries(folder, request, breadcrumbURL);
}
%>

<c:if test="<%= dlItemSelectorCriterion.isShowGroupsSelector() %>">
	<liferay-util:include page="/group_selector.jsp" servletContext="<%= application %>">
		<liferay-util:param name="tabs1" value="documents" />
	</liferay-util:include>
</c:if>

<aui:form method="post" name="selectDocumentFm">

	<%
	FileEntrySearch fileEntrySearchContainer = new FileEntrySearch(renderRequest, portletURL);

	FileEntryDisplayTerms displayTerms = (FileEntryDisplayTerms)fileEntrySearchContainer.getDisplayTerms();

	displayTerms.setSelectedGroupId(groupId);
	%>

	<aui:nav-bar>
		<aui:nav cssClass="navbar-nav">
			<aui:nav-item
				dropdown="<%= true %>"
				iconCssClass="icon-plus"
				label="add"
			>
				<c:if test="<%= DLFolderPermission.contains(permissionChecker, groupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<liferay-portlet:renderURL portletName="<%= PortletKeys.DOCUMENT_LIBRARY %>" var="addFolderURL">
						<portlet:param name="struts_action" value="/document_library/edit_folder" />
						<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
						<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
					</liferay-portlet:renderURL>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());
					%>

					<aui:nav-item
						href="<%= addFolderURL %>"
						iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
						label='<%= (folder != null) ? "subfolder" : "folder" %>'
					/>
				</c:if>

				<c:if test="<%= DLFolderPermission.contains(permissionChecker, groupId, folderId, ActionKeys.ADD_DOCUMENT) %>">

					<%
					List<DLFileEntryType> fileEntryTypes = Collections.emptyList();

					if ((folder == null) || folder.isSupportsMetadata()) {
						fileEntryTypes = DLFileEntryTypeLocalServiceUtil.getFolderFileEntryTypes(PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), folderId, true);
					}
					%>

					<c:if test="<%= fileEntryTypes.isEmpty() %>">
						<liferay-portlet:renderURL var="editFileEntryURL">
							<portlet:param name="mvcPath" value="/add_file_entry.jsp" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="type" value="<%= dlItemSelectorCriterion.getType() %>" />
						</liferay-portlet:renderURL>

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<aui:nav-item
							href="<%= editFileEntryURL %>"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							label="basic-document"
						/>
					</c:if>

					<%
					for (DLFileEntryType fileEntryType : fileEntryTypes) {
					%>

						<liferay-portlet:renderURL var="addFileEntryTypeURL">
							<portlet:param name="mvcPath" value="/add_file_entry.jsp" />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD %>" />
							<portlet:param name="redirect" value="<%= portletURL.toString() %>" />
							<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryType.getFileEntryTypeId()) %>" />
							<portlet:param name="type" value="<%= dlItemSelectorCriterion.getType() %>" />
						</liferay-portlet:renderURL>

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());
						%>

						<aui:nav-item
							href="<%= addFileEntryTypeURL %>"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							label="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>"
						/>

					<%
					}
					%>

				</c:if>
			</aui:nav-item>
		</aui:nav>

		<aui:nav-bar-search searchContainer="<%= fileEntrySearchContainer %>">
			<liferay-ui:search-toggle
				buttonLabel="search"
				displayTerms="<%= displayTerms %>"
				id="toggle_id_asset_search"
				>
				<aui:fieldset>
					<aui:input inlineField="<%= true %>" name="keywords" size="20" type="text" value="" />

					<aui:select inlineField="<%= true %>" label="scope" name="<%= FileEntryDisplayTerms.SELECTED_GROUP_ID %>" showEmptyOption="<%= false %>">
						<aui:option label="global" selected="<%= displayTerms.getSelectedGroupId() == themeDisplay.getCompanyGroupId() %>" value="<%= themeDisplay.getCompanyGroupId() %>" />
						<aui:option label="<%= themeDisplay.getScopeGroupName() %>" selected="<%= displayTerms.getSelectedGroupId() == scopeGroupId %>" value="<%= scopeGroupId %>" />
					</aui:select>
				</aui:fieldset>
			</liferay-ui:search-toggle>
		</aui:nav-bar-search>
	</aui:nav-bar>

	<c:if test="<%= Validator.isNull(displayTerms.getKeywords()) %>">
		<liferay-ui:header
			title="folders"
		/>

		<liferay-ui:breadcrumb showCurrentGroup="<%= false %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-folders"
			iteratorURL="<%= portletURL %>"
		>
			<liferay-ui:search-container-results
				results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="<%= DLAppServiceUtil.getFoldersCount(repositoryId, folderId) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.Folder"
				keyProperty="folderId"
				modelVar="curFolder"
			>

				<%
				PortletURL rowURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
				%>

				<liferay-ui:search-container-column-text
					href="<%= rowURL %>"
					name="folder"
				>

					<%
					AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

					AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
					%>

					<liferay-ui:icon
						iconCssClass="<%= assetRenderer.getIconCssClass() %>"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(curFolder.getName()) %>"
					/>
				</liferay-ui:search-container-column-text>

				<%
				int foldersCount = 0;

				try {
					foldersCount = DLAppServiceUtil.getFoldersCount(curFolder.getRepositoryId(), curFolder.getFolderId());
				}
				catch (Exception e) {
				}

				int fileEntriesCount = 0;

				try {
					fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), Arrays.asList(curFolder.getFolderId()), WorkflowConstants.STATUS_APPROVED);
				}
				catch (Exception e) {
				}
				%>

				<liferay-ui:search-container-column-text
					name="num-of-folders"
					value="<%= String.valueOf(foldersCount) %>"
				/>

				<liferay-ui:search-container-column-text
					name="num-of-documents"
					value="<%= String.valueOf(fileEntriesCount) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</c:if>

	<liferay-ui:header
		backURL="<%= portletURL.toString() %>"
		showBackURL="<%= Validator.isNotNull(displayTerms.getKeywords()) %>"
		title="documents"
	/>

	<%
	PortletURL iteratorURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

	iteratorURL.setParameter("folderId", String.valueOf(folderId));
	%>

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-documents-in-this-folder"
		iteratorURL="<%= iteratorURL %>"
	>

		<%
		String keywords = ParamUtil.getString(request, "keywords");

		String[] mimeTypes = dlItemSelectorCriterion.getMimeTypes();

		if (Validator.isNotNull(keywords)) {
			SearchContext searchContext = SearchContextFactory.getInstance(request);

			searchContext.setAttribute("groupId", groupId);
			searchContext.setAttribute("mimeTypes", mimeTypes);
			searchContext.setAttribute("paginationType", "regular");

			int entryEnd = ParamUtil.getInteger(request, "entryEnd", GetterUtil.getInteger(PropsUtil.get(PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA), 20));

			searchContext.setEnd(entryEnd);

			searchContext.setFolderIds(new long[]{folderId});
			searchContext.setGroupIds(new long[]{groupId});
			searchContext.setIncludeFolders(false);

			searchContext.setKeywords(keywords);

			searchContext.setScopeStrict(false);

			int entryStart = ParamUtil.getInteger(request, "entryStart");

			searchContext.setStart(entryStart);

			Hits hits = DLAppServiceUtil.search(repositoryId, searchContext);

			searchContainer.setTotal(hits.getLength());

			searchContainer.setResults(DLUtil.getFileEntries(hits));
		}
		else {
			searchContainer.setTotal(DLAppServiceUtil.getFileEntriesCount(repositoryId, folderId, mimeTypes));

			searchContainer.setResults(DLAppServiceUtil.getFileEntries(repositoryId, folderId, mimeTypes, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()));
		}
		%>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
		>
			<liferay-ui:search-container-column-text
				name="document"
			>
				<img align="left" alt="" src="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>" style="<%= DLUtil.getThumbnailStyle() %>" />
				<%= HtmlUtil.escape(fileEntry.getTitle()) %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="size"
				value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
			/>

			<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get(PropsKeys.BUFFERED_INCREMENT_ENABLED, new Filter("DLFileEntry"))) %>'>
				<liferay-ui:search-container-column-text
					name="downloads"
					value="<%= String.valueOf(fileEntry.getReadCount()) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="locked"
				value='<%= fileEntry.isCheckedOut() ? "yes" : "no" %>'
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("fileEntryid", fileEntry.getFileEntryId());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	var container = $('#<portlet:namespace />selectDocumentFm');

	var selectorButtons = container.find('.selector-button');

	container.on(
		'click',
		'.selector-button',
		function(event) {
			var fileEntryId = event.target.getAttribute('data-fileEntryId');

			<%= itemSelectedCallback %>('<%= FileEntry.class.getName() %>', fileEntryId);
		}
	);
</aui:script>