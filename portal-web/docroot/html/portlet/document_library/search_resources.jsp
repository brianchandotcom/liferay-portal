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
String redirect = ParamUtil.getString(request, "redirect");

long breadcrumbsFolderId = ParamUtil.getLong(request, "breadcrumbsFolderId");

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

String keywords = ParamUtil.getString(request, "keywords");

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}
%>

<div id="<portlet:namespace />entries">
	<div class="search-info">
		<span class="keywords">
			<%= (folder != null) ? LanguageUtil.format(pageContext, "searched-for-x-in-x", new Object[] {keywords, folder.getName()}) : LanguageUtil.format(pageContext, "searched-for-x-in-every-folder", keywords) %>
		</span>

		<c:if test="<%= folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
			<span class="change-search-folder">
				<aui:button onClick='<%= "javascript:" + liferayPortletResponse.getNamespace() + "changeSearchFolder();" %>' value='<%= (folder != null) ? LanguageUtil.get(pageContext, "search-in-every-folder") : LanguageUtil.get(pageContext, "search-in-current-folder") %>' />
			</span>
		</c:if>

		<liferay-ui:icon cssClass="close-search" id="closeSearch" image="../aui/closethick" url="javascript:;" />
	</div>

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

			headerNames.add("name");
			headerNames.add("description");
			headerNames.add("size");
			headerNames.add("create-date");
			headerNames.add("modified-date");
			headerNames.add("read-count");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(liferayPortletRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-documents-were-found-that-matched-the-keywords-x", "<strong>" + HtmlUtil.escape(keywords) + "</strong>"));

			Map<String, String> orderableHeaders = new HashMap<String, String>();

			orderableHeaders.put("name", "name");
			orderableHeaders.put("size", "size");
			orderableHeaders.put("create-date", "creationDate");
			orderableHeaders.put("modified-date", "modifiedDate");
			orderableHeaders.put("read-count", "readCount");

			searchContainer.setOrderableHeaders(orderableHeaders);

			String orderByCol = ParamUtil.getString(request, "orderByCol");

			searchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(request, "orderByType");

			searchContainer.setOrderByType(orderByType);

			OrderByComparator orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

			searchContainer.setOrderByComparator(orderByComparator);

			searchContainer.setRowChecker(new RowChecker(liferayPortletResponse));

			Hits results = null;

			try {
				Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntryConstants.getClassName());

				SearchContext searchContext = SearchContextFactory.getInstance(request);

				searchContext.setAttribute("paginationType", "more");
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setFolderIds(folderIdsArray);
				searchContext.setKeywords(keywords);
				searchContext.setStart(searchContainer.getStart());

				results = indexer.search(searchContext);

				int total = results.getLength();

				searchContainer.setTotal(total);
				%>

				<c:if test='<%= !displayStyle.equals("list") && (results.getLength() > 0) %>'>
					<div class="taglib-search-iterator-page-iterator-top">
						<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
					</div>
				</c:if>

				<%
				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < results.getDocs().length; i++) {
					Document doc = results.doc(i);

					// Folder and document

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
					%>

					<c:choose>
						<c:when test='<%= !displayStyle.equals("list") %>'>
							<c:choose>
								<c:when test="<%= DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.VIEW) %>">

									<%
									PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

									tempRowURL.setParameter("struts_action", "/document_library/view_file_entry");
									tempRowURL.setParameter("redirect", currentURL);
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
							ResultRow row = new ResultRow(doc, i, i);

							// Position

							row.setObject(fileEntry);

							PortletURL rowURL = liferayPortletResponse.createRenderURL();

							rowURL.setParameter("struts_action", "/document_library/view_file_entry");
							rowURL.setParameter("redirect", currentURL);
							rowURL.setParameter("fileEntryId", String.valueOf(fileEntry.getFileEntryId()));

							String rowHREF = rowURL.toString();

							row.addText(fileEntry.getTitle(), rowHREF);

							row.addText(fileEntry.getDescription(), rowHREF);
							row.addText(TextFormatter.formatKB(fileEntry.getSize(), locale) + "k");
							row.addText(dateFormatDateTime.format(fileEntry.getCreateDate()));
							row.addText(dateFormatDateTime.format(fileEntry.getModifiedDate()));
							row.addText(String.valueOf(fileEntry.getReadCount()));

							// Action

							row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/document_library/file_entry_action.jsp");

							// Add result row

							resultRows.add(row);
							%>

						</c:otherwise>
					</c:choose>
				<%
				}
				%>

				<c:if test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" type="more" />
				</c:if>

			<%
			}
			catch (Exception e) {
				_log.error(e.getMessage());
			}
			%>

			<c:if test='<%= !displayStyle.equals("list") && (results.getLength() > 0) %>'>
				<div class="taglib-search-iterator-page-iterator-top">
					<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
				</div>
			</c:if>
		</aui:form>
	</div>

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		<aui:script>
			Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />keywords);
		</aui:script>
	</c:if>

	<%
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "search") + ": " + keywords, currentURL);
	%>

	<aui:script use="aui-base">
		<portlet:resourceURL var="changeSearchFolder">
			<portlet:param name="struts_action" value="/document_library/search" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			<portlet:param name="searchFolderId" value="<%= (folder != null) ? String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) : String.valueOf(folderId) %>" />
			<portlet:param name="keywords" value="<%= keywords %>" />
		</portlet:resourceURL>

		Liferay.provide(
			window,
			'<portlet:namespace />changeSearchFolder',
			function() {

				var documentContainer = A.one('#<portlet:namespace />documentContainer');

				documentContainer.plug(A.LoadingMask);

				documentContainer.loadingmask.toggle();

				A.io.request(
					'<%= changeSearchFolder.toString() %>',
					{
						after: {
							success: function(event, id, obj) {
								documentContainer.unplug(A.LoadingMask);

								var responseData = this.get('responseData');

								var content = A.Node.create(responseData);

								A.one('#<portlet:namespace />displayStyleToolbar').empty();

								var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');
								var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

								displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
								displayStyleButtonsContainer.setContent(displayStyleButtons);

								var entries = content.one('#<portlet:namespace />entries');

								documentContainer.setContent(entries);
							}
						}
					}
				);
			},
			['aui-base,aui-io']
		);
	</aui:script>

	<aui:script use="aui-io">
		<portlet:resourceURL var="closeSearch">
			<portlet:param name="struts_action" value="/document_library/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
			<portlet:param name="viewDisplayStyleButtons" value="<%= Boolean.TRUE.toString() %>" />
			<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:resourceURL>

		A.one('#<portlet:namespace />closeSearch').on(
			'click',
			function(event) {
				var documentContainer = A.one('#<portlet:namespace />documentContainer');

				documentContainer.plug(A.LoadingMask);

				documentContainer.loadingmask.toggle();

				A.io.request(
					'<%= closeSearch.toString() %>',
					{
						after: {
							success: function(event, id, obj) {
								documentContainer.unplug(A.LoadingMask);

								var responseData = this.get('responseData');

								var content = A.Node.create(responseData);

								A.one('#<portlet:namespace />displayStyleToolbar').empty();

								var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');
								var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

								displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
								displayStyleButtonsContainer.setContent(displayStyleButtons);

								var entries = content.one('#<portlet:namespace />entries');

								documentContainer.setContent(entries);
							}
						}
					}
				);
			}
		);
	</aui:script>
</div>

<span id="<portlet:namespace />displayStyleButtons">
	<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
</span>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.search_resources_jsp");
%>