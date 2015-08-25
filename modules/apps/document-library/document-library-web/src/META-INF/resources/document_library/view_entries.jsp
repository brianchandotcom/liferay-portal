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
boolean emailFileEntryAnyEventEnabled = dlGroupServiceSettings.isEmailFileEntryAddedEnabled() || dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled();

String navigation = ParamUtil.getString(request, "navigation", "home");

String currentFolder = ParamUtil.getString(request, "curFolder");
String deltaFolder = ParamUtil.getString(request, "deltaFolder");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String dlFileEntryTypeName = LanguageUtil.get(request, "basic-document");

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (categoryId > 0) || Validator.isNotNull(tagName);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/document_library/view");
portletURL.setParameter("curFolder", currentFolder);
portletURL.setParameter("deltaFolder", deltaFolder);
portletURL.setParameter("folderId", String.valueOf(folderId));

SearchContainer dlSearchContainer = new SearchContainer(liferayPortletRequest, null, null, "curEntry", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

String orderByCol = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByType"));

OrderByComparator<?> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

if (navigation.equals("recent")) {
	orderByComparator = new RepositoryModelModifiedDateComparator();
}

dlSearchContainer.setOrderByCol(orderByCol);
dlSearchContainer.setOrderByComparator(orderByComparator);
dlSearchContainer.setOrderByType(orderByType);

List results = null;
int total = 0;

if (fileEntryTypeId >= 0) {
	Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntryConstants.getClassName());

	if (fileEntryTypeId > 0) {
		DLFileEntryType dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		dlFileEntryTypeName = dlFileEntryType.getName(locale);
	}

	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setAttribute("paginationType", "none");
	searchContext.setEnd(dlSearchContainer.getEnd());

	if (orderByCol.equals("creationDate")) {
		orderByCol = "createDate";
	}
	else if (orderByCol.equals("readCount")) {
		orderByCol = "downloads";
	}
	else if (orderByCol.equals("modifiedDate")) {
		orderByCol = "modified";
	}

	Sort sort = new Sort(orderByCol, !StringUtil.equalsIgnoreCase(orderByType, "asc"));

	searchContext.setSorts(sort);

	searchContext.setStart(dlSearchContainer.getStart());

	Hits hits = indexer.search(searchContext);

	total = hits.getLength();

	dlSearchContainer.setTotal(total);

	Document[] docs = hits.getDocs();

	results = new ArrayList(docs.length);

	for (Document doc : docs) {
		long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

		FileEntry fileEntry = null;

		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Documents and Media search index is stale and contains file entry {" + fileEntryId + "}");
			}

			continue;
		}

		results.add(fileEntry);
	}
}
else {
	if (navigation.equals("home")) {
		if (useAssetEntryQuery) {
			long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcutConstants.getClassName())};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, dlSearchContainer);

			assetEntryQuery.setEnablePermissions(true);
			assetEntryQuery.setExcludeZeroViewCount(false);

			total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

			dlSearchContainer.setTotal(total);

			results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
		}
		else {
			total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, false);

			dlSearchContainer.setTotal(total);

			results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, false, dlSearchContainer.getStart(), dlSearchContainer.getEnd(), dlSearchContainer.getOrderByComparator());
		}
	}
	else if (navigation.equals("mine") || navigation.equals("recent")) {
		long groupFileEntriesUserId = 0;

		if (navigation.equals("mine") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();

			status = WorkflowConstants.STATUS_ANY;
		}

		total = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupFileEntriesUserId, folderId, null, status);

		dlSearchContainer.setTotal(total);

		results = DLAppServiceUtil.getGroupFileEntries(repositoryId, groupFileEntriesUserId, folderId, null, status, dlSearchContainer.getStart(), dlSearchContainer.getEnd(), dlSearchContainer.getOrderByComparator());
	}
}

dlSearchContainer.setResults(results);
%>

<div class="subscribe-action">
	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.SUBSCRIBE) && ((folder == null) || folder.isSupportsSubscribing()) && emailFileEntryAnyEventEnabled %>">

		<%
		boolean subscribed = false;
		boolean unsubscribable = true;

		if (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {
			subscribed = DLUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

			if (subscribed) {
				if (!DLUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId, false)) {
					unsubscribable = false;
				}
			}
		}
		else {
			subscribed = DLUtil.isSubscribedToFileEntryType(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), fileEntryTypeId);
		}
		%>

		<c:choose>
			<c:when test="<%= subscribed %>">
				<c:choose>
					<c:when test="<%= unsubscribable %>">
						<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' var="unsubscribeURL">
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />

							<c:choose>
								<c:when test="<%= fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>">
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
								</c:when>
								<c:otherwise>
									<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
								</c:otherwise>
							</c:choose>
						</portlet:actionURL>

						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="unsubscribe"
							url="<%= unsubscribeURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="subscribed-to-a-parent-folder"
						/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' var="subscribeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />

					<c:choose>
						<c:when test="<%= fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL %>">
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</c:when>
						<c:otherwise>
							<portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" />
						</c:otherwise>
					</c:choose>
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>

<c:if test="<%= results.isEmpty() %>">
	<div class="alert alert-info entries-empty">
		<c:choose>
			<c:when test="<%= (fileEntryTypeId >= 0) %>">
				<liferay-ui:message arguments="<%= HtmlUtil.escape(dlFileEntryTypeName) %>" key="there-are-no-documents-or-media-files-of-type-x" translateArguments="<%= false %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-documents-or-media-files-in-this-folder" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<div class="document-container" id="<portlet:namespace />entriesContainer">
	<c:choose>
		<c:when test='<%= displayStyle.equals("icon") %>'>

			<%
			for (Object result : results) {
			%>

				<%@ include file="/document_library/cast_result.jspf" %>

				<c:choose>
					<c:when test="<%= fileEntry != null %>">
						<c:choose>
							<c:when test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

								<%
								PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

								tempRowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
								tempRowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
								tempRowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

								request.setAttribute("view_entries.jsp-fileEntry", fileEntry);
								request.setAttribute("view_entries.jsp-fileShortcut", fileShortcut);

								request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
								%>

								<liferay-util:include page="/document_library/view_file_entry_icon.jsp" servletContext="<%= application %>" />
							</c:when>
							<c:otherwise>
								<div style="float: left; margin: 100px 10px 0px;">
									<i class="icon-ban-circle"></i>
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>

					<c:when test="<%= curFolder != null %>">

						<%
						String folderImage = "folder_empty_document";

						if (PropsValues.DL_FOLDER_ICON_CHECK_COUNT && (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), status, true) > 0)) {
							folderImage = "folder_full_document";
						}

						PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

						tempRowURL.setParameter("mvcRenderCommandName", "/document_library/view");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));

						request.setAttribute("view_entries.jsp-folderImage", folderImage);

						request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
						%>

						<liferay-util:include page="/document_library/view_folder_icon.jsp" servletContext="<%= application %>" />
					</c:when>
				</c:choose>

			<%
			}
			%>

		</c:when>
		<c:otherwise>

			<%
			String[] entryColumns = dlPortletInstanceSettingsHelper.getEntryColumns();
			%>

			<liferay-ui:search-container
				searchContainer="<%= dlSearchContainer %>"
				total="<%= total %>"
				totalVar="dlSearchContainerTotal"
			>
				<liferay-ui:search-container-results
					results="<%= results %>"
					resultsVar="dlSearchContainerResults"
				/>

				<liferay-ui:search-container-row
					className="Object"
					cssClass="app-view-entry-taglib entry-display-style selectable"
					modelVar="result"
				>

					<%@ include file="/document_library/cast_result.jspf" %>

					<c:choose>
						<c:when test="<%= fileEntry != null %>">

							<%
							if (fileShortcut == null) {
								row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
							}
							else {
								row.setPrimaryKey(String.valueOf(fileShortcut.getFileShortcutId()));
							}

							boolean draggable = false;

							if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
								draggable = true;

								if (Validator.isNull(dlSearchContainer.getRowChecker())) {
									dlSearchContainer.setRowChecker(entriesChecker);
								}
							}

							Map<String, Object> rowData = new HashMap<String, Object>();

							rowData.put("draggable", draggable);
							rowData.put("title", fileEntry.getTitle());

							row.setData(rowData);
							%>

							<c:choose>
								<c:when test='<%= displayStyle.equals("descriptive") %>'>
									<%@ include file="/document_library/entry_columns_descriptive.jspf" %>
								</c:when>
								<c:otherwise>
									<%@ include file="/document_library/entry_columns_list.jspf" %>
								</c:otherwise>
							</c:choose>

						</c:when>
						<c:otherwise>

							<%
							boolean draggable = false;

							if (DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE)) {
								draggable = true;

								if (Validator.isNull(dlSearchContainer.getRowChecker())) {
									dlSearchContainer.setRowChecker(entriesChecker);
								}
							}

							Map<String, Object> rowData = new HashMap<String, Object>();

							rowData.put("draggable", draggable);
							rowData.put("folder", true);
							rowData.put("folder-id", curFolder.getFolderId());
							rowData.put("title", curFolder.getName());

							row.setData(rowData);
							row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
							%>

							<c:choose>
								<c:when test='<%= displayStyle.equals("descriptive") %>'>
									<%@ include file="/document_library/folder_columns_descriptive.jspf" %>
								</c:when>
								<c:otherwise>
									<%@ include file="/document_library/folder_columns_list.jspf" %>
								</c:otherwise>
							</c:choose>

						</c:otherwise>
					</c:choose>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle='<%= displayStyle.equals("descriptive") ? displayStyle : null %>' paginate="<%= false %>" searchContainer="<%= dlSearchContainer %>" view="lexicon" />
			</liferay-ui:search-container>
		</c:otherwise>
	</c:choose>
</div>

<div class="document-entries-pagination">
	<liferay-ui:search-paginator searchContainer="<%= dlSearchContainer %>" />
</div>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.view_entries_jsp");
%>