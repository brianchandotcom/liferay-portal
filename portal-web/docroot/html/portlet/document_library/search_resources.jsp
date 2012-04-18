<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

long repositoryId = ParamUtil.getLong(request, "repositoryId");

if (repositoryId == 0) {
	repositoryId = scopeGroupId;
}

long searchRepositoryId = ParamUtil.getLong(request, "searchRepositoryId");

if (searchRepositoryId == 0) {
	searchRepositoryId = scopeGroupId;
}

long folderId = ParamUtil.getLong(request, "folderId");

long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
long searchFolderIds = ParamUtil.getLong(request, "searchFolderIds");

long[] folderIdsArray = null;

Folder folder = null;

if (searchFolderId > 0) {
	folderIdsArray = new long[] {searchFolderId};

	folder = DLAppServiceUtil.getFolder(searchFolderId);
}
else {
	long defaultFolderId = DLFolderConstants.getFolderId(scopeGroupId, DLFolderConstants.getDataRepositoryId(scopeGroupId, searchFolderIds));

	List<Long> folderIds = DLAppServiceUtil.getSubfolderIds(scopeGroupId, searchFolderIds);

	folderIds.add(0, defaultFolderId);

	folderIdsArray = StringUtil.split(StringUtil.merge(folderIds), 0L);
}

List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

String keywords = ParamUtil.getString(request, "keywords");

int searchType = ParamUtil.getInteger(request, "searchType");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
}

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", entriesPerPage);

int total = 0;

boolean ajaxRequest = ParamUtil.getBoolean(request, "ajax");

boolean showRepositoryTabs = ParamUtil.getBoolean(request, "showRepositoryTabs");

boolean showSearchInfo = ParamUtil.getBoolean(request, "showSearchInfo");

if (searchType == DLSearchConstants.FRAGMENT) {
	if (ajaxRequest) {
		showRepositoryTabs = false;

		showSearchInfo = false;
	}
	else {
		searchType = DLSearchConstants.SINGLE;

		showSearchInfo = true;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			showRepositoryTabs = true;
		}
	}
}
else if ((searchType == DLSearchConstants.SINGLE) && !ajaxRequest) {
	showSearchInfo = true;

	if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
		showRepositoryTabs = true;
	}
}
%>

<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
<aui:input name="searchRepositoryId" type="hidden" value="<%= searchRepositoryId %>" />

<c:if test="<%= showSearchInfo %>">
	<liferay-util:buffer var="searchInfo">
		<div class="search-info">
			<span class="keywords">
				<%= (folder != null) ? LanguageUtil.format(pageContext, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), folder.getName()}) : LanguageUtil.format(pageContext, "searched-for-x-everywhere", HtmlUtil.escape(keywords)) %>
			</span>

			<c:if test="<%= folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
				<span class="change-search-folder">

					<%
					String onClick = "Liferay.fire('" + liferayPortletResponse.getNamespace() + "changeSearchFolder', {searchEverywhere: " + (folder != null) + "});";
					%>

					<aui:button onClick='<%= onClick %>' value='<%= (folder != null) ? LanguageUtil.get(pageContext, "search-everywhere") : LanguageUtil.get(pageContext, "search-in-the-current-folder") %>' />
					<aui:button onClick='<%= onClick %>' value='<%= (folder != null) ? LanguageUtil.get(pageContext, "search-everywhere") : LanguageUtil.get(pageContext, "search-in-the-current-folder") %>' />
				</span>
			</c:if>

			<liferay-ui:icon cssClass="close-search" id="closeSearch" image="../aui/closethick" url="javascript:;" />
		</div>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<aui:script>
				Liferay.Util.focusFormField(document.<portlet:namespace />fm1.<portlet:namespace />keywords);
			</aui:script>
		</c:if>

		<c:if test="<%= (searchRepositoryId == scopeGroupId) %>">
			<aui:script use="aui-base">
				A.one('#<portlet:namespace />closeSearch').on(
					'click',
					function(event) {
						Liferay.fire(
							'<portlet:namespace />dataRequest',
							{
								requestParams: {
									'<portlet:namespace />struts_action': '/document_library/view',
									'<portlet:namespace />folderId': '<%= String.valueOf(folderId) %>',
									'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>
								},
								src: Liferay.DL_SEARCH_END
							}
						);
					}
				);
			</aui:script>
		</c:if>
	</liferay-util:buffer>

	<div id="<portlet:namespace />searchInfo">
		<%= searchInfo %>
	</div>
</c:if>

<liferay-util:buffer var="searchResults">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="struts_action" value="/document_library/search" />
	</liferay-portlet:renderURL>

	<div class="document-container" id="<portlet:namespace />documentContainer">
		<aui:form action="<%= searchURL %>" method="get" name="fm">
			<liferay-portlet:renderURLParams varImpl="searchURL" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="breadcrumbsFolderId" type="hidden" value="<%= breadcrumbsFolderId %>" />
			<aui:input name="searchFolderId" type="hidden" value="<%= searchFolderId %>" />
			<aui:input name="searchFolderIds" type="hidden" value="<%= searchFolderIds %>" />

			<%
			PortletURL portletURL = liferayPortletResponse.createRenderURL();

			portletURL.setParameter("struts_action", "/document_library/search");
			portletURL.setParameter("redirect", redirect);
			portletURL.setParameter("breadcrumbsFolderId", String.valueOf(breadcrumbsFolderId));
			portletURL.setParameter("searchFolderId", String.valueOf(searchFolderId));
			portletURL.setParameter("searchFolderIds", String.valueOf(searchFolderIds));
			portletURL.setParameter("keywords", keywords);

			List<String> headerNames = new ArrayList<String>();

			for (String headerName : entryColumns) {
				if (headerName.equals("action")) {
					headerName = StringPool.BLANK;
				}
				else if (headerName.equals("name")) {
					headerName = "title";
				}

				headerNames.add(headerName);
			}

			SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, entriesPerPage, portletURL, headerNames, null);

			Map<String, String> orderableHeaders = new HashMap<String, String>();

			orderableHeaders.put("title", "title");
			orderableHeaders.put("size", "size");
			orderableHeaders.put("create-date", "creationDate");
			orderableHeaders.put("modified-date", "modifiedDate");
			orderableHeaders.put("downloads", "downloads");

			searchContainer.setOrderableHeaders(orderableHeaders);

			String orderByCol = ParamUtil.getString(request, "orderByCol");

			searchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(request, "orderByType");

			searchContainer.setOrderByType(orderByType);

			OrderByComparator orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

			searchContainer.setOrderByComparator(orderByComparator);

			searchContainer.setRowChecker(new EntriesChecker(liferayPortletRequest, liferayPortletResponse));

			try {
				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setAttribute("paginationType", "regular");
				searchContext.setEnd(entryEnd);
				searchContext.setFolderIds(folderIdsArray);
				searchContext.setKeywords(keywords);
				searchContext.setStart(entryStart);

				Hits hits = DLAppServiceUtil.search(searchRepositoryId, searchContext);

				List results = new ArrayList();

				List resultRows = searchContainer.getResultRows();

				Document[] docs = hits.getDocs();

				if (docs != null) {
					for (Document doc : docs) {

						// Folder and document

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

				total = hits.getLength();

				searchContainer.setResults(results);
				searchContainer.setTotal(total);

				request.setAttribute("view.jsp-total", String.valueOf(total));

				for (int i = 0; i < results.size(); i++) {
					Object result = results.get(i);
				%>

					<%@ include file="/html/portlet/document_library/cast_result.jspf" %>

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

							<%
							resultRows = searchContainer.getResultRows();

							ResultRow row = new ResultRow(fileEntry, fileEntry.getFileEntryId(), i);

							// Position

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("struts_action", "/document_library/view_file_entry");
							rowURL.setParameter("redirect", HttpUtil.removeParameter(currentURL, liferayPortletResponse.getNamespace() + "ajax"));
							rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							for (String columnName : entryColumns) {
								if (columnName.equals("action")) {
									row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_entry_action.jsp");
								}

								if (columnName.equals("create-date")) {
									row.addText(dateFormatDateTime.format(fileEntry.getCreateDate()));
								}

								if (columnName.equals("downloads")) {
									row.addText(String.valueOf(fileEntry.getReadCount()));
								}

								if (columnName.equals("modified-date")) {
									row.addText(dateFormatDateTime.format(fileEntry.getModifiedDate()));
								}

								if (columnName.equals("name")) {
									row.addText(fileEntry.getTitle(), rowURL);
								}

								if (columnName.equals("size")) {
									row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k");
								}
							}

							// Add result row

							resultRows.add(row);
							%>

						</c:otherwise>
					</c:choose>

				<%
				}
				%>

				<c:if test="<%= results.isEmpty() %>">
					<div class="portlet-msg-info">
						<%= LanguageUtil.format(pageContext, "no-documents-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>") %>
					</div>
				</c:if>

				<c:if test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" type="more" />
				</c:if>

			<%
			}
			catch (Exception e) {
				_log.error(e, e);
			}
			%>

		</aui:form>
	</div>

	<aui:script>
		Liferay.fire(
			'<portlet:namespace />pageLoaded',
			{
				paginator: {
					name: 'entryPaginator',
					state: {
						page: <%= total == 0 ? 0 : entryEnd / (entryEnd - entryStart) %>,
						rowsPerPage: <%= (entryEnd - entryStart) %>,
						total: <%= total %>
					}
				},
				repositoryId: '<%= searchRepositoryId %>',
				src: Liferay.DL_SEARCH
			}
		);
	</aui:script>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= searchType == DLSearchConstants.SINGLE %>">
		<c:choose>
			<c:when test="<%= showRepositoryTabs %>">

			<%
				String selectedTab = LanguageUtil.get(pageContext, "local");

				for (Folder mountFolder : mountFolders) {
					if (mountFolder.getRepositoryId() == searchRepositoryId) {
						selectedTab = mountFolder.getName();
					}
				}
			%>


				<div class="search-results-container" id="<portlet:namespace />searchResultsContainer">
					<liferay-ui:tabs
						names='<%= LanguageUtil.get(pageContext, "local") + "," + ListUtil.toString(mountFolders, "name") %>'
						refresh="<%= false %>"
						value="<%= selectedTab %>"
					>
						<liferay-ui:section>
							<div class="local-search-results" data-repositoryId="<%= scopeGroupId %>" <%= scopeGroupId == searchRepositoryId ? "data-searchProcessed" : "" %> id="<portlet:namespace />searchResultsContainer<%= scopeGroupId %>">
								<c:choose>
									<c:when test="<%= scopeGroupId == searchRepositoryId %>">
											<%= searchResults %>
									</c:when>
									<c:otherwise>
										<div class="portlet-msg-info">
											<%= LanguageUtil.get(pageContext, "searching,-please-wait") %>
										</div>
										<div class="loading-animation"></div>
									</c:otherwise>
								</c:choose>
							</div>
						</liferay-ui:section>

						<%
						for (Folder mountFolder : mountFolders) {
						%>

							<liferay-ui:section>
								<div data-repositoryId="<%= mountFolder.getRepositoryId() %>" <%= mountFolder.getRepositoryId() == searchRepositoryId ? "data-searchProcessed" : "" %> id="<portlet:namespace />searchResultsContainer<%= mountFolder.getRepositoryId() %>">
									<c:choose>
										<c:when test="<%= mountFolder.getRepositoryId() == searchRepositoryId %>">
											<%= searchResults %>
										</c:when>
										<c:otherwise>
											<div class="portlet-msg-info">
												<%= LanguageUtil.get(pageContext, "searching,-please-wait") %>
											</div>
											<div class="loading-animation"></div>
										</c:otherwise>
									</c:choose>
								</div>
							</liferay-ui:section>

						<%
						}
						%>

					</liferay-ui:tabs>
				</div>

				<span id="<portlet:namespace />displayStyleButtons">
					<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
				</span>
			</c:when>
			<c:otherwise>
				<div class="repository-search-results" data-repositoryId="<%= searchRepositoryId %>" id='<%= liferayPortletResponse.getNamespace() + "searchResults" + searchRepositoryId %>'>
					<%= searchResults %>
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= searchType == DLSearchConstants.FRAGMENT %>">
		<div data-repositoryId="<%= searchRepositoryId %>" id="<portlet:namespace />fragmentSearchResults">
			<%= searchResults %>
		</div>
	</c:when>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.search_resources_jsp");
%>