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
String navigation = ParamUtil.getString(request, "navigation", "home");

String currentFolder = ParamUtil.getString(request, "curFolder");
String deltaFolder = ParamUtil.getString(request, "deltaFolder");

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

EntriesMover entriesMover = new EntriesMover(scopeGroupId);

String orderByCol = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByType"));

boolean orderByModel = false;

if (navigation.equals("home")) {
	orderByModel = true;
}

OrderByComparator<?> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType, orderByModel);

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
			total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, true);

			dlSearchContainer.setTotal(total);

			results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, true, dlSearchContainer.getStart(), dlSearchContainer.getEnd(), dlSearchContainer.getOrderByComparator());
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

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (folderId != rootFolderId)) {
	String redirect = ParamUtil.getString(request, "redirect");

	if (Validator.isNotNull(redirect)) {
		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(redirect);
	}

	Folder folder = DLAppServiceUtil.getFolder(folderId);

	renderResponse.setTitle(folder.getName());
}
%>

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

	<%
	String searchContainerId = ParamUtil.getString(request, "searchContainerId");
	%>

	<liferay-ui:search-container
		id="<%= searchContainerId %>"
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
			cssClass="app-view-entry-taglib entry-display-style"
			modelVar="result"
		>

			<%@ include file="/document_library/cast_result.jspf" %>

			<c:choose>
				<c:when test="<%= fileEntry != null %>">

					<%
					FileVersion latestFileVersion = fileEntry.getFileVersion();

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						latestFileVersion = fileEntry.getLatestFileVersion();
					}

					DLViewFileVersionDisplayContext dlViewFileVersionDisplayContext = null;

					if (fileShortcut == null) {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileEntry.getFileVersion());

						row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
					}
					else {
						dlViewFileVersionDisplayContext = dlDisplayContextProvider.getDLViewFileVersionDisplayContext(request, response, fileShortcut);

						row.setPrimaryKey(String.valueOf(fileShortcut.getFileShortcutId()));
					}

					boolean draggable = false;

					if (DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						draggable = true;

						if (Validator.isNull(dlSearchContainer.getRowChecker())) {
							dlSearchContainer.setRowChecker(entriesChecker);
						}

						if (Validator.isNull(dlSearchContainer.getRowMover())) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					Map<String, Object> rowData = new HashMap<String, Object>();

					rowData.put("draggable", draggable);
					rowData.put("title", fileEntry.getTitle());

					row.setData(rowData);
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<c:choose>
								<c:when test="<%= fileShortcut != null %>">
									<liferay-ui:search-container-column-icon
										icon="icon-share-alt"
										toggleRowChecker="<%= true %>"
									/>
								</c:when>
								<c:otherwise>
									<liferay-ui:search-container-column-image
										src="<%= DLUtil.getThumbnailSrc(fileEntry, latestFileVersion, themeDisplay) %>"
										toggleRowChecker="<%= true %>"
									/>
								</c:otherwise>
							</c:choose>

							<liferay-ui:search-container-column-jsp
								colspan="2"
								path="/document_library/view_file_entry_descriptive.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/document_library/file_entry_action.jsp"
							/>
						</c:when>
						<c:when test='<%= displayStyle.equals("icon") %>'>

							<%
							row.setCssClass("col-md-2 col-sm-4 col-xs-6");
							%>

							<liferay-ui:search-container-column-text>

								<%
								PortletURL rowURL = liferayPortletResponse.createRenderURL();

								rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
								rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
								rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
								%>

								<liferay-frontend:vertical-card
									actionJsp="/document_library/file_entry_action.jsp"
									actionJspServletContext="<%= application %>"
									cssClass="entry-display-style"
									imageUrl="<%= DLUtil.getThumbnailSrc(fileEntry, latestFileVersion, themeDisplay) %>"
									resultRow="<%= row %>"
									rowChecker="<%= entriesChecker %>"
									title="<%= latestFileVersion.getTitle() %>"
									url="<%= rowURL != null ? rowURL.toString() : null %>"
								>
									<liferay-frontend:vertical-card-sticker-bottom>
										<div class="sticker sticker-bottom <%= dlViewFileVersionDisplayContext.getCssClassFileMimeType() %>">
											<%= StringUtil.upperCase(latestFileVersion.getExtension()) %>
										</div>
									</liferay-frontend:vertical-card-sticker-bottom>

									<liferay-frontend:vertical-card-header>
										<%= LanguageUtil.format(request, "x-ago-by-x", new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - latestFileVersion.getCreateDate().getTime(), true), HtmlUtil.escape(latestFileVersion.getUserName())}, false) %>
									</liferay-frontend:vertical-card-header>

									<liferay-frontend:vertical-card-footer>
										<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= latestFileVersion.getStatus() %>" />
									</liferay-frontend:vertical-card-footer>
								</liferay-frontend:vertical-card>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>

							<%
							String[] entryColumns = dlPortletInstanceSettingsHelper.getEntryColumns();
							%>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>
								<liferay-ui:search-container-column-text
									name="title"
								>

									<%
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("mvcRenderCommandName", "/document_library/view_file_entry");
									rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
									rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
									%>

									<liferay-ui:app-view-entry
										displayStyle="list"
										iconCssClass="<%= assetRenderer.getIconCssClass() %>"
										locked="<%= fileEntry.isCheckedOut() %>"
										showCheckbox="<%= true %>"
										title="<%= latestFileVersion.getTitle() %>"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "size") %>'>
								<liferay-ui:search-container-column-text
									name="size"
									value="<%= TextFormatter.formatStorageSize(latestFileVersion.getSize(), locale) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "status") %>'>
								<liferay-ui:search-container-column-status
									name="status"
									status="<%= latestFileVersion.getStatus() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "downloads") %>'>
								<liferay-ui:search-container-column-text
									name="downloads"
									value="<%= String.valueOf(fileEntry.getReadCount()) %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "create-date") %>'>
								<liferay-ui:search-container-column-date
									name="create-date"
									value="<%= fileEntry.getCreateDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "modified-date") %>'>
								<liferay-ui:search-container-column-date
									name="modified-date"
									value="<%= latestFileVersion.getModifiedDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
								<liferay-ui:search-container-column-jsp
									cssClass="entry-action"
									path="/document_library/file_entry_action.jsp"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>

					<%
					Map<String, Object> rowData = new HashMap<String, Object>();

					boolean draggable = false;

					if (DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE)) {
						draggable = true;

						if (Validator.isNull(dlSearchContainer.getRowChecker())) {
							dlSearchContainer.setRowChecker(entriesChecker);
						}

						if (Validator.isNull(dlSearchContainer.getRowMover())) {
							dlSearchContainer.setRowMover(entriesMover);
						}
					}

					rowData.put("draggable", draggable);

					rowData.put("folder", true);
					rowData.put("folder-id", curFolder.getFolderId());
					rowData.put("title", curFolder.getName());

					row.setData(rowData);

					row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("descriptive") %>'>
							<liferay-ui:search-container-column-icon
								icon='<%= curFolder.isMountPoint() ? "icon-hdd" : "icon-folder-close" %>'
								toggleRowChecker="<%= true %>"
							/>

							<liferay-ui:search-container-column-jsp
								colspan="2"
								path="/document_library/view_folder_descriptive.jsp"
							/>

							<liferay-ui:search-container-column-jsp
								path="/document_library/folder_action.jsp"
							/>
						</c:when>
						<c:when test='<%= displayStyle.equals("icon") %>'>

							<%
							row.setCssClass("col-md-3 col-sm-4 col-xs-12");

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("mvcRenderCommandName", "/document_library/view");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
							%>

							<liferay-ui:search-container-column-text colspan="<%= 2 %>">
								<liferay-frontend:horizontal-card
									actionJsp="/document_library/folder_action.jsp"
									actionJspServletContext="<%= application %>"
									icon='<%= curFolder.isMountPoint() ? "icon-hdd" : "icon-folder-close-alt" %>'
									imageCSSClass="icon-monospaced"
									resultRow="<%= row %>"
									rowChecker="<%= entriesChecker %>"
									text="<%= HtmlUtil.escape(curFolder.getName()) %>"
									url="<%= rowURL.toString() %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>
								<liferay-ui:search-container-column-text
									name="title"
								>

									<%
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("mvcRenderCommandName", "/document_library/view");
									rowURL.setParameter("redirect", currentURL);
									rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
									%>

									<liferay-ui:app-view-entry
										displayStyle="<%= displayStyle %>"
										folder="<%= true %>"
										iconCssClass="<%= assetRenderer.getIconCssClass() %>"
										showCheckbox="<%= false %>"
										title="<%= curFolder.getName() %>"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "size") %>'>
								<liferay-ui:search-container-column-text
									name="size"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "status") %>'>
								<liferay-ui:search-container-column-text
									name="status"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "downloads") %>'>
								<liferay-ui:search-container-column-text
									name="downloads"
									value="--"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "create-date") %>'>
								<liferay-ui:search-container-column-date
									name="create-date"
									value="<%= curFolder.getCreateDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "modified-date") %>'>
								<liferay-ui:search-container-column-date
									name="modified-date"
									value="<%= curFolder.getModifiedDate() %>"
								/>
							</c:if>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "action") %>'>
								<liferay-ui:search-container-column-jsp
									cssClass="entry-action"
									path="/document_library/folder_action.jsp"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new DLResultRowSplitter() %>" searchContainer="<%= dlSearchContainer %>" />
	</liferay-ui:search-container>
</div>

<%@ include file="/document_library/version_details.jspf" %>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_document_library_web.document_library.view_entries_jsp");
%>