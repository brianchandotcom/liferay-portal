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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
boolean emailFileEntryAnyEventEnabled = dlSettings.isEmailFileEntryAddedEnabled() || dlSettings.isEmailFileEntryUpdatedEnabled();

String navigation = ParamUtil.getString(request, "navigation", "home");

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

DLEntryListDisplayContext dlEntriesListDisplayContext = new DLEntryListDisplayContext(request, dlPortletInstanceSettings);

String displayStyle = GetterUtil.getString((String)request.getAttribute("view.jsp-displayStyle"));

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("folderId", String.valueOf(folderId));

SearchContainer dlSearchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, null, null);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

dlSearchContainer.setRowChecker(entriesChecker);

String orderByCol = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view.jsp-orderByType"));

OrderByComparator<?> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

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
			long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcut.class.getName())};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, dlSearchContainer);

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
						<portlet:actionURL var="unsubscribeURL">
							<portlet:param name="struts_action" value='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' />
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
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value='<%= (fileEntryTypeId == DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) ? "/document_library/edit_folder" : "/document_library/edit_file_entry_type" %>' />
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
		<c:when test='<%= !displayStyle.equals("list") %>'>

			<%
			for (Object result : results) {
			%>

				<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

				<c:choose>
					<c:when test="<%= fileEntry != null %>">
						<c:choose>
							<c:when test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

								<%
								PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

								tempRowURL.setParameter("struts_action", "/document_library/view_file_entry");
								tempRowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
								tempRowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

								request.setAttribute("view_entries.jsp-fileEntry", fileEntry);
								request.setAttribute("view_entries.jsp-fileShortcut", fileShortcut);

								request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
								%>

								<c:choose>
									<c:when test='<%= displayStyle.equals("icon") %>'>
										<liferay-util:include page="/html/portlet/document_library/view_file_entry_icon.jsp" />
									</c:when>
									<c:otherwise>
										<liferay-util:include page="/html/portlet/document_library/view_file_entry_descriptive.jsp" />
									</c:otherwise>
								</c:choose>
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

						if (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), status, true) > 0) {
							folderImage = "folder_full_document";
						}

						PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

						tempRowURL.setParameter("struts_action", "/document_library/view");
						tempRowURL.setParameter("redirect", currentURL);
						tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

						request.setAttribute("view_entries.jsp-folder", curFolder);
						request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
						request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));

						request.setAttribute("view_entries.jsp-folderImage", folderImage);

						request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
						%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>
								<liferay-util:include page="/html/portlet/document_library/view_folder_icon.jsp" />
							</c:when>

							<c:otherwise>
								<liferay-util:include page="/html/portlet/document_library/view_folder_descriptive.jsp" />
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>

			<%
			}
			%>

		</c:when>
		<c:otherwise>

			<%
			String[] entryColumns = dlEntriesListDisplayContext.getEntryColumns();
			%>

			<liferay-ui:search-container
				searchContainer="<%= dlSearchContainer %>"
				totalVar="dlSearchContainerTotal"
			>
				<liferay-ui:search-container-results
					results="<%= results %>"
					resultsVar="dlSearchContainerResults"
					total="<%= total %>"
				/>

				<liferay-ui:search-container-row
					className="Object"
					modelVar="result"
				>

					<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

					<c:choose>
						<c:when test="<%= fileEntry != null %>">

							<%
							FileVersion latestFileVersion = fileEntry.getFileVersion();

							if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
								latestFileVersion = fileEntry.getLatestFileVersion();
							}

							if (fileShortcut == null) {
								row.setPrimaryKey(String.valueOf(fileEntry.getFileEntryId()));
							}
							else {
								row.setPrimaryKey(String.valueOf(fileShortcut.getFileShortcutId()));
							}

							row.setClassName("app-view-entry-taglib entry-display-style selectable");

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("draggable", DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE));
							data.put("title", fileEntry.getTitle());

							row.setData(data);
							%>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>
								<liferay-ui:search-container-column-text
									name="title"
								>

									<%
									AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

									AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/document_library/view_file_entry");
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
									path="/html/portlet/document_library/file_entry_action.jsp"
								/>
							</c:if>
						</c:when>
						<c:otherwise>

							<%
							row.setPrimaryKey(String.valueOf(curFolder.getPrimaryKey()));

							row.setClassName("app-view-entry-taglib entry-display-style selectable");

							Map<String, Object> data = new HashMap<String, Object>();

							data.put("draggable", DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
							data.put("folder", true);
							data.put("folder-id", curFolder.getFolderId());
							data.put("title", curFolder.getName());

							row.setData(data);
							%>

							<c:if test='<%= ArrayUtil.contains(entryColumns, "name") %>'>
								<liferay-ui:search-container-column-text
									name="title"
								>

									<%
									data = new HashMap<String, Object>();

									data.put("folder", true);
									data.put("folder-id", curFolder.getFolderId());

									AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

									AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());

									PortletURL rowURL = liferayPortletResponse.createRenderURL();

									rowURL.setParameter("struts_action", "/document_library/view");
									rowURL.setParameter("redirect", currentURL);
									rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
									%>

									<liferay-ui:app-view-entry
										data="<%= data %>"
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
									path="/html/portlet/document_library/folder_action.jsp"
								/>
							</c:if>
						</c:otherwise>
					</c:choose>

				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= dlSearchContainer %>" />
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