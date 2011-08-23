<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId");

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

String navigation = ParamUtil.getString(request, "navigation","documents-home");

long categoryId = ParamUtil.getLong(request, "categoryId");
String tagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = (categoryId > 0) || Validator.isNotNull(tagName);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(request, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(PropsValues.DL_DISPLAY_VIEWS, displayStyle)) {
		portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", displayStyle);
	}
}

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("displayStyle", String.valueOf(displayStyle));

SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, "cur2", fileEntriesPerPage, portletURL, null, null);

List<String> headerNames = new ArrayList<String>();

headerNames.add("name");
headerNames.add("description");
headerNames.add("size");
headerNames.add("create-date");
headerNames.add("modified-date");
headerNames.add("read-count");
headerNames.add(StringPool.BLANK);

searchContainer.setHeaderNames(headerNames);

searchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

Map<String, String> orderableHeaders = new HashMap<String, String>();

orderableHeaders.put("name", "name");
orderableHeaders.put("size", "size");
orderableHeaders.put("create-date", "creationDate");
orderableHeaders.put("modified-date", "modifiedDate");
orderableHeaders.put("read-count", "readCount");

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

OrderByComparator orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

searchContainer.setOrderableHeaders(orderableHeaders);
searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByType(orderByType);
searchContainer.setOrderByComparator(orderByComparator);

int entryStart = ParamUtil.getInteger(request, "entryStart", searchContainer.getStart());
int entryEnd = ParamUtil.getInteger(request, "entryEnd", searchContainer.getEnd());

int folderStart = ParamUtil.getInteger(request, "folderStart");
int folderEnd = ParamUtil.getInteger(request, "folderEnd", SearchContainer.DEFAULT_DELTA);

List results = null;
int total = 0;

if (fileEntryTypeId > 0) {
	Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntryConstants.getClassName());

	SearchContext searchContext = SearchContextFactory.getInstance(request);

	searchContext.setEnd(searchContainer.getEnd());
	searchContext.setStart(searchContainer.getStart());

	Hits hits = indexer.search(searchContext);

	results = new ArrayList();

	for (int i = 0; i < hits.getDocs().length; i++) {
		Document doc = hits.doc(i);

		long fileEntryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));

		FileEntry fileEntry = null;

		try {
			fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Document library search index is stale and contains file entry {" + fileEntryId + "}");
			}

			continue;
		}

		results.add(fileEntry);
	}

	total = results.size();
}
else {
	if (navigation.equals("documents-home")) {
		if (useAssetEntryQuery) {
			long[] classNameIds = {PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()), PortalUtil.getClassNameId(DLFileShortcut.class.getName())};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(classNameIds, searchContainer);

			assetEntryQuery.setExcludeZeroViewCount(false);

			results = AssetEntryServiceUtil.getEntries(assetEntryQuery);
			total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);
		}
		else {
			results = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(repositoryId, folderId, status, false, entryStart, entryEnd, searchContainer.getOrderByComparator());
			total = DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, status, false);
		}
	}
	else if (navigation.equals("my-documents") || navigation.equals("recent-documents")) {
		long groupFileEntriesUserId = 0;

		if (navigation.equals("my-documents") && themeDisplay.isSignedIn()) {
			groupFileEntriesUserId = user.getUserId();
		}

		results= DLAppServiceUtil.getGroupFileEntries(repositoryId, groupFileEntriesUserId, folderId, entryStart, entryEnd);
		total= DLAppServiceUtil.getGroupFileEntriesCount(repositoryId, groupFileEntriesUserId, folderId);
	}
}

searchContainer.setResults(results);
searchContainer.setTotal(total);

request.setAttribute("view_entries.jsp-total", String.valueOf(total));
%>

<c:if test='<%= !displayStyle.equals("list") %>'>
	<c:choose>
		<c:when test="<%= results.isEmpty() %>">
			<div class="portlet-msg-info">
				<%= LanguageUtil.get(pageContext, "there-are-no-documents-in-this-folder") %>
			</div>
		</c:when>
	</c:choose>
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
							tempRowURL.setParameter("redirect", HttpUtil.addParameter(currentURL, liferayPortletResponse.getNamespace() + "showSiblings", true));
							tempRowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							request.setAttribute("view_entries.jsp-fileEntry", fileEntry);

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
								<img alt="<liferay-ui:message key="image" />" border="no" src="<%= themeDisplay.getPathThemeImages() %>/application/forbidden_action.png" />
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>

				<c:otherwise>
					<liferay-util:buffer var="fileEntryTitle">
						<liferay-ui:icon
							image='<%= "../file_system/small/" + fileEntry.getExtension() %>'
							label="<%= true %>"
							message="<%= fileEntry.getTitle() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

					PortletURL rowURL = liferayPortletResponse.createRenderURL();

					rowURL.setParameter("struts_action", "/document_library/view_file_entry");
					rowURL.setParameter("redirect", HttpUtil.addParameter(currentURL, liferayPortletResponse.getNamespace() + "showSiblings", true));
					rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

					row.addText(fileEntryTitle, rowURL);
					row.addText(fileEntry.getDescription(), rowURL);
					row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k");
					row.addText(dateFormatDateTime.format(fileEntry.getCreateDate()));
					row.addText(dateFormatDateTime.format(fileEntry.getModifiedDate()));
					row.addText(String.valueOf(fileEntry.getReadCount()));

					row.addJSP("/html/portlet/document_library/file_entry_action.jsp");

					resultRows.add(row);
					%>

				</c:otherwise>
			</c:choose>
		</c:when>

		<c:when test="<%= curFolder != null %>">
			<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="viewEntriesURL">
				<portlet:param name="struts_action" value="/document_library/view" />
				<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewAddButton" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewBreadcrumb" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewFileEntrySearch" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="viewSortButton" value="<%= Boolean.TRUE.toString() %>" />
				<portlet:param name="entryStart" value="0" />
				<portlet:param name="entryEnd" value="<%= String.valueOf(entryEnd - entryStart) %>" />
				<portlet:param name="folderStart" value="0" />
				<portlet:param name="folderEnd" value="<%= String.valueOf(folderEnd - folderStart) %>" />
			</liferay-portlet:resourceURL>

			<c:choose>
				<c:when test='<%= !displayStyle.equals("list") %>'>
					<%
					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/document_library/view");
					tempRowURL.setParameter("redirect", currentURL);
					tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					request.setAttribute("view_entries.jsp-folder", curFolder);
					request.setAttribute("view_entries.jsp-folderId", String.valueOf(curFolder.getFolderId()));
					request.setAttribute("view_entries.jsp-repositoryId", String.valueOf(curFolder.getRepositoryId()));

					request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
					request.setAttribute("view_entries.jsp-viewEntriesURL", viewEntriesURL);
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
						<liferay-ui:icon
							image="folder"
							label="<%= true %>"
							message="<%= curFolder.getName() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey(), i);

					PortletURL rowURL = liferayPortletResponse.createRenderURL();

					rowURL.setParameter("struts_action", "/document_library/view");
					rowURL.setParameter("redirect", currentURL);
					rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					Map<String,Object> data = new HashMap<String,Object>();

					data.put("folder", true);
					data.put("folder-id", curFolder.getFolderId());
					data.put("refresh-folders", true);
					data.put("resource-url", viewEntriesURL);

					TextSearchEntry folderTitleSearchEntry = new TextSearchEntry();

					folderTitleSearchEntry.setData(data);
					folderTitleSearchEntry.setHref(rowURL.toString());
					folderTitleSearchEntry.setName(folderTitle);

					row.addSearchEntry(folderTitleSearchEntry);

					TextSearchEntry folderDescriptionSearchEntry = new TextSearchEntry();

					folderDescriptionSearchEntry.setData(data);
					folderDescriptionSearchEntry.setHref(rowURL.toString());
					folderDescriptionSearchEntry.setName(curFolder.getDescription());

					row.addSearchEntry(folderDescriptionSearchEntry);

					row.addText(String.valueOf(0) + "k");

					row.addText(dateFormatDateTime.format(curFolder.getCreateDate()));

					row.addText(dateFormatDateTime.format(curFolder.getModifiedDate()));

					row.addText(String.valueOf(0));

					row.addJSP("/html/portlet/document_library/folder_action.jsp");

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

<liferay-portlet:resourceURL varImpl="paginationURL">
	<portlet:param name="struts_action" value="/document_library/view" />
	<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
	<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
	<portlet:param name="viewFolders" value="<%= Boolean.TRUE.toString() %>" />
	<portlet:param name="showSiblings" value="<%= Boolean.TRUE.toString() %>" />
</liferay-portlet:resourceURL>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			paginator: {
				name: 'entryPaginator',
				state: {
					page: <%= entryEnd / (entryEnd - entryStart) %>,
					rowsPerPage: <%= (entryEnd - entryStart) %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.view_entries_jsp");
%>