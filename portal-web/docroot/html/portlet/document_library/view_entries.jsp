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
String navigation = ParamUtil.getString(request, "navigation", "home");

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

String dlFileEntryTypeName = LanguageUtil.get(pageContext, "basic-document");

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (categoryId > 0) || Validator.isNotNull(tagName);

String displayStyle = ParamUtil.getString(request, "displayStyle");

DLEntryListDisplayContext dlEntriesListDisplayContext = new DLEntryListDisplayContext(request, dlPortletInstanceSettings);

String[] displayViews = dlPortletInstanceSettings.getDisplayViews();

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(request, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", displayStyle);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("displayStyle", String.valueOf(displayStyle));

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", dlPortletInstanceSettings.getEntriesPerPage());

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", entryEnd / (entryEnd - entryStart), entryEnd - entryStart, portletURL, null, null);

List<String> headerNames = new ArrayList<String>();

String[] entryColumns = dlEntriesListDisplayContext.getEntryColumns();

for (String headerName : entryColumns) {
	if (headerName.equals("action")) {
		headerName = StringPool.BLANK;
	}
	else if (headerName.equals("name")) {
		headerName = "title";
	}

	headerNames.add(headerName);
}

searchContainer.setHeaderNames(headerNames);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

searchContainer.setRowChecker(entriesChecker);

Map<String, String> orderableHeaders = new HashMap<String, String>();

orderableHeaders.put("title", "title");
orderableHeaders.put("size", "size");
orderableHeaders.put("create-date", "creationDate");
orderableHeaders.put("modified-date", "modifiedDate");
orderableHeaders.put("downloads", "downloads");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNull(orderByCol)) {
	orderByCol = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", StringPool.BLANK);
	orderByType = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", "asc");
}
else {
	boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

	if (saveOrderBy) {
		portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
		portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
	}
}

OrderByComparator orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

searchContainer.setOrderableHeaders(orderableHeaders);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByComparator(orderByComparator);
searchContainer.setOrderByJS("javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'orderKey', 'orderByType');");
searchContainer.setOrderByType(orderByType);

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
	searchContext.setEnd(entryEnd);

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

	searchContext.setStart(entryStart);

	Hits hits = indexer.search(searchContext);

	total = hits.getLength();

	searchContainer.setTotal(total);

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

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, searchContainer);

			assetEntryQuery.setEnd(entryEnd);
			assetEntryQuery.setExcludeZeroViewCount(false);
			assetEntryQuery.setStart(entryStart);

			total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

			searchContainer.setTotal(total);

			if (total <= entryStart) {
				entryStart = (searchContainer.getCur() - 1) * searchContainer.getDelta();
				entryEnd = entryStart + searchContainer.getDelta();

				assetEntryQuery.setEnd(entryEnd);
				assetEntryQuery.setStart(entryStart);
			}

			results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
		}
		else {
			total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, false);

			searchContainer.setTotal(total);

			if (total <= entryStart) {
				entryStart = (searchContainer.getCur() - 1) * searchContainer.getDelta();
				entryEnd = entryStart + searchContainer.getDelta();
			}

			results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, false, entryStart, entryEnd, searchContainer.getOrderByComparator());
		}
	}
	else if (navigation.equals("mine") || navigation.equals("recent")) {
		long groupFileEntriesUserId = 0;

		if (navigation.equals("mine") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();

			status = WorkflowConstants.STATUS_ANY;
		}

		total = DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupFileEntriesUserId, folderId, null, status);

		searchContainer.setTotal(total);

		if (total <= entryStart) {
			entryStart = (searchContainer.getCur() - 1) * searchContainer.getDelta();
			entryEnd = entryStart + searchContainer.getDelta();
		}

		results = DLAppServiceUtil.getGroupFileEntries(repositoryId, groupFileEntriesUserId, folderId, null, status, entryStart, entryEnd, searchContainer.getOrderByComparator());
	}
}

searchContainer.setResults(results);

request.setAttribute("view.jsp-total", String.valueOf(total));

request.setAttribute("view_entries.jsp-entryStart", String.valueOf(searchContainer.getStart()));
request.setAttribute("view_entries.jsp-entryEnd", String.valueOf(searchContainer.getEnd()));
%>

<div class="subscribe-action">
	<c:if test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.SUBSCRIBE) && ((folder == null) || folder.isSupportsSubscribing()) && DLUtil.getEmailFileEntryAnyEventEnabled(dlSettings) %>">

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
							iconCssClass="icon-ok-sign"
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
	<div class="entries-empty alert alert-info">
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

<%
for (int i = 0; i < results.size(); i++) {
	Object result = results.get(i);
%>

	<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

	<c:choose>
		<c:when test="<%= fileEntry != null %>">
			<c:choose>
				<c:when test='<%= !displayStyle.equals("list") %>'>
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
								<img alt="<liferay-ui:message key="error" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>

				<c:otherwise>

					<%
					FileVersion latestFileVersion = fileEntry.getFileVersion();

					if ((user.getUserId() == fileEntry.getUserId()) || permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE)) {
						latestFileVersion = fileEntry.getLatestFileVersion();
					}
					%>

					<liferay-util:buffer var="fileEntryTitle">

						<%
						PortletURL rowURL = liferayPortletResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/document_library/view_file_entry");
						rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
						rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));
						%>

						<liferay-ui:app-view-entry
							displayStyle="list"
							locked="<%= fileEntry.isCheckedOut() %>"
							showCheckbox="<%= true %>"
							thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/file_system/small/" + DLUtil.getFileIcon(fileEntry.getExtension()) + ".png" %>'
							title="<%= latestFileVersion.getTitle() %>"
							url="<%= rowURL.toString() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = null;

					if (fileShortcut == null) {
						row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);
					}
					else {
						row = new ResultRow(fileShortcut, fileShortcut.getFileShortcutId(), i);
					}

					row.setClassName("app-view-entry-taglib entry-display-style selectable");

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("draggable", DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) || DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE));
					data.put("title", fileEntry.getTitle());

					row.setData(data);

					for (String columnName : entryColumns) {
						if (columnName.equals("action")) {
							row.addJSP("/html/portlet/document_library/file_entry_action.jsp");
						}

						if (columnName.equals("create-date")) {
							row.addDate(fileEntry.getCreateDate());
						}

						if (columnName.equals("downloads")) {
							row.addText(String.valueOf(fileEntry.getReadCount()));
						}

						if (columnName.equals("modified-date")) {
							row.addDate(latestFileVersion.getModifiedDate());
						}

						if (columnName.equals("name")) {
							TextSearchEntry fileEntryTitleSearchEntry = new TextSearchEntry();

							fileEntryTitleSearchEntry.setName(fileEntryTitle);

							row.addSearchEntry(fileEntryTitleSearchEntry);
						}

						if (columnName.equals("size")) {
							row.addText(TextFormatter.formatStorageSize(latestFileVersion.getSize(), locale));
						}

						if (columnName.equals("status")) {
							row.addStatus(latestFileVersion.getStatus(), latestFileVersion.getStatusByUserId(), latestFileVersion.getStatusDate());
						}
					}

					resultRows.add(row);
					%>

				</c:otherwise>
			</c:choose>
		</c:when>

		<c:when test="<%= curFolder != null %>">
			<c:choose>
				<c:when test='<%= !displayStyle.equals("list") %>'>

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
				<c:otherwise>
					<liferay-util:buffer var="folderTitle">

						<%
						String folderImage = "folder_empty";

						if (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(curFolder.getRepositoryId(), curFolder.getFolderId(), status, true) > 0) {
							folderImage = "folder_full_document";
						}

						Map<String, Object> data = new HashMap<String, Object>();

						data.put("folder", true);
						data.put("folder-id", curFolder.getFolderId());

						PortletURL rowURL = liferayPortletResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/document_library/view");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
						%>

						<liferay-ui:app-view-entry
							data="<%= data %>"
							displayStyle="list"
							folder="<%= true %>"
							showCheckbox="<%= false %>"
							thumbnailSrc='<%= themeDisplay.getPathThemeImages() + "/common/" + folderImage + ".png" %>'
							title="<%= curFolder.getName() %>"
							url="<%= rowURL.toString() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey(), i);

					row.setClassName("app-view-entry-taglib entry-display-style selectable");

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("draggable", DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || DLFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
					data.put("folder", true);
					data.put("folder-id", curFolder.getFolderId());
					data.put("title", curFolder.getName());

					row.setData(data);

					for (String columnName : entryColumns) {
						if (columnName.equals("action")) {
							row.addJSP("/html/portlet/document_library/folder_action.jsp");
						}

						if (columnName.equals("create-date")) {
							row.addDate(curFolder.getCreateDate());
						}

						if (columnName.equals("downloads")) {
							row.addText("--");
						}

						if (columnName.equals("modified-date")) {
							row.addDate(curFolder.getModifiedDate());
						}

						if (columnName.equals("name")) {
							TextSearchEntry folderTitleSearchEntry = new TextSearchEntry();

							folderTitleSearchEntry.setName(folderTitle);

							row.addSearchEntry(folderTitleSearchEntry);
						}

						if (columnName.equals("size")) {
							row.addText("--");
						}

						if (columnName.equals("status")) {
							row.addText("--");
						}
					}

					resultRows.add(row);
					%>

				</c:otherwise>
			</c:choose>
		</c:when>
	</c:choose>

<%
}
%>

<c:if test='<%= displayStyle.equals("list") %>'>
	<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
</c:if>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			pagination: {
				name: 'entryPagination',
				state: {
					page: <%= (total == 0) ? 0 : searchContainer.getCur() %>,
					rowsPerPage: <%= searchContainer.getDelta() %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.view_entries_jsp");
%>