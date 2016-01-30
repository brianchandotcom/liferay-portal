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

<%@ include file="/repository_entry_browser/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_repository_entry_browse_page") + StringPool.UNDERLINE;

String displayStyle = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:displayStyle"));
String emptyResultsMessage = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:emptyResultsMessage"));
ItemSelectorReturnType existingFileEntryReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-item-selector:repository-entry-browser:existingFileEntryReturnType");
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:itemSelectedEventName"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:portletURL");
List repositoryEntries = (List)request.getAttribute("liferay-item-selector:repository-entry-browser:repositoryEntries");
int repositoryEntriesCount = GetterUtil.getInteger(request.getAttribute("liferay-item-selector:repository-entry-browser:repositoryEntriesCount"));
boolean showBreadcrumb = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showBreadcrumb"));
boolean showDragAndDropZone = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showDragAndDropZone"));
String tabName = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:tabName"));
PortletURL uploadURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:uploadURL");

SearchContainer searchContainer = new SearchContainer(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse), null, emptyResultsMessage);

String keywords = ParamUtil.getString(request, "keywords");

boolean showSearchInfo = false;

if (Validator.isNotNull(keywords)) {
	showSearchInfo = true;
}
%>

<liferay-util:html-top>
	<link href="<%= ServletContextUtil.getContextPath() + "/repository_entry_browser/css/main.css" %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<c:if test="<%= !showSearchInfo %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews="<%= RepositoryEntryBrowserTag.DISPLAY_STYLES %>"
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>

			<%
			PortletURL sortURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
			String orderByType = ParamUtil.getString(request, "orderByType", "asc");

			Map<String, String> orderColumns = new HashMap<String, String>();

			orderColumns.put("modifiedDate", "modified-date");
			orderColumns.put("size", "size");
			orderColumns.put("title", "title");
			%>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns="<%= orderColumns %>"
				portletURL="<%= sortURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>
</c:if>

<div class="container-fluid-1280 lfr-item-viewer" id="<%= randomNamespace %>ItemSelectorContainer">

	<%
	long folderId = ParamUtil.getLong(request, "folderId");

	if (showBreadcrumb && !showSearchInfo) {
		ItemSelectorRepositoryEntryBrowserUtil.addPortletBreadcrumbEntries(folderId, displayStyle, request, liferayPortletResponse, PortletURLUtil.clone(portletURL, liferayPortletResponse));
	%>

		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

	<%
	}
	else if (showSearchInfo) {
	%>

		<div class="search-info">
			<span class="keywords">

				<%
				Folder folder = null;
				boolean searchEverywhere = true;

				String searchInfoMessage = StringPool.BLANK;
				boolean showRerunSearchButton = true;

				if (!showBreadcrumb) {
					searchInfoMessage = LanguageUtil.format(request, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), HtmlUtil.escape(tabName)}, false);

					showRerunSearchButton = false;
				}
				else {
					long searchFolderId = ParamUtil.getLong(request, "searchFolderId");

					if (folderId > DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
						searchEverywhere = false;

						folder = DLAppServiceUtil.getFolder(folderId);
					}
					else {
						folderId = searchFolderId;
					}

					if ((folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) && (searchFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
						showRerunSearchButton = false;
					}

					searchInfoMessage = !searchEverywhere ? LanguageUtil.format(request, "searched-for-x-in-x", new Object[] {HtmlUtil.escape(keywords), HtmlUtil.escape(folder.getName())}, false) : LanguageUtil.format(request, "searched-for-x-everywhere", HtmlUtil.escape(keywords), false);
				}
				%>

				<%= searchInfoMessage %>
			</span>

			<c:if test="<%= showRerunSearchButton %>">
				<span class="change-search-folder">

					<%
					PortletURL searchURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

					searchURL.setParameter("folderId", !searchEverywhere ? String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) : String.valueOf(folderId));
					searchURL.setParameter("searchFolderId", String.valueOf(folderId));
					searchURL.setParameter("keywords", keywords);
					%>

					<aui:button href="<%= searchURL.toString() %>" value='<%= !searchEverywhere ? "search-everywhere" : "search-in-the-current-folder" %>' />
				</span>
			</c:if>

			<%
			PortletURL closeSearchURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

			closeSearchURL.setParameter("folderId", String.valueOf(folderId));
			%>

			<liferay-ui:icon cssClass="close-search" iconCssClass="icon-remove" id="closeSearch" message="remove" url="<%= closeSearchURL.toString() %>" />
		</div>

	<%
	}
	%>

	<c:if test="<%= showDragAndDropZone && !showSearchInfo %>">
		<liferay-util:buffer var="selectFileHTML">
			<label class="btn btn-default" for="<%= randomNamespace %>InputFile"><liferay-ui:message key="select-file" /></label>

			<input class="hide" id="<%= randomNamespace %>InputFile" type="file" />
		</liferay-util:buffer>

		<div class="drop-enabled drop-zone no-border">
			<strong><liferay-ui:message arguments="<%= selectFileHTML %>" key="drag-and-drop-to-upload-or-x" /></strong>
		</div>
	</c:if>

	<c:if test="<%= existingFileEntryReturnType != null %>">
		<liferay-ui:search-container
			searchContainer="<%= searchContainer %>"
			total="<%= repositoryEntriesCount %>"
			var="listSearchContainer"
		>
			<liferay-ui:search-container-results
				results="<%= repositoryEntries %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.RepositoryEntry"
				modelVar="repositoryEntry"
			>

				<c:choose>
					<c:when test='<%= displayStyle.equals("list") %>'>

						<%
						FileEntry fileEntry = null;
						FileShortcut fileShortcut = null;
						Folder folder = null;

						if (repositoryEntry instanceof FileEntry) {
							fileEntry = (FileEntry)repositoryEntry;
						}
						else if (repositoryEntry instanceof FileShortcut) {
							fileShortcut = (FileShortcut)repositoryEntry;

							fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
						}
						else {
							folder = (Folder)repositoryEntry;
						}

						if (fileEntry != null) {
							FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

							String title = DLUtil.getTitleWithExtension(fileEntry);

							JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
						%>

							<liferay-ui:search-container-column-text name="title">
								<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">

									<%
									String iconCssClass = DLUtil.getFileIconCssClass(fileEntry.getExtension());
									%>

									<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
										<i class="<%= iconCssClass %>"></i>
									</c:if>

									<span class="taglib-text">
										<%= HtmlUtil.escape(title) %>
									</span>
								</a>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text name="size" value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>" />

							<liferay-ui:search-container-column-status name="status" status="<%= latestFileVersion.getStatus() %>" />

							<liferay-ui:search-container-column-text name="modified-date">
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

						<%
						}
						if (folder != null) {
							PortletURL viewFolderURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

							viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
						%>

							<liferay-ui:search-container-column-text name="title">
								<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">

									<i class="icon-folder-open"></i>

									<span class="taglib-text">
										<%= HtmlUtil.escape(folder.getName()) %>
									</span>
								</a>
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text name="size" value="--" />

							<liferay-ui:search-container-column-text name="status" value="--" />

							<liferay-ui:search-container-column-text name="modified-date">
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - folder.getModifiedDate().getTime(), true), HtmlUtil.escape(folder.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

						<%
						}
						%>

					</c:when>
					<c:otherwise>

						<%
						FileEntry fileEntry = null;
						Folder folder = null;

						if (repositoryEntry instanceof FileEntry) {
							fileEntry = (FileEntry)repositoryEntry;
						}
						else if (repositoryEntry instanceof FileShortcut) {
							FileShortcut fileShortcut = (FileShortcut)repositoryEntry;

							fileEntry = DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId());
						}
						else {
							folder = (Folder)repositoryEntry;
						}
						%>

						<c:choose>
							<c:when test='<%= displayStyle.equals("icon") %>'>

								<%
								row.setCssClass("col-md-3");

								if (folder != null) {
									PortletURL viewFolderURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

									viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
								%>

									<liferay-ui:search-container-column-text colspan="<%= 3 %>">
										<liferay-frontend:horizontal-card
											resultRow="<%= row %>"
											text="<%= HtmlUtil.escape(folder.getName()) %>"
											url="<%= viewFolderURL.toString() %>"
										>
											<liferay-frontend:horizontal-card-col>
												<liferay-frontend:horizontal-card-icon
													icon="folder"
												/>
											</liferay-frontend:horizontal-card-col>
										</liferay-frontend:horizontal-card>
									</liferay-ui:search-container-column-text>

								<%
								}

								if (fileEntry != null) {
									row.setCssClass("col-lg-3 col-md-3 col-sm-4 col-xs-6");

									FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

									String title = DLUtil.getTitleWithExtension(fileEntry);

									JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);

									Map<String, Object> data = new HashMap<String, Object>();

									data.put("href", DLUtil.getImagePreviewURL(fileEntry, themeDisplay));
									data.put("metadata", itemMedatadaJSONObject.toString());
									data.put("returnType", ClassUtil.getClassName(existingFileEntryReturnType));
									data.put("title", title);
									data.put("url", DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK));
									data.put("value", ItemSelectorRepositoryEntryBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay));
								%>

									<liferay-ui:search-container-column-text>
										<liferay-frontend:image-card
											cssClass="item-preview"
											data="<%= data %>"
											imageUrl="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>"
										/>
									</liferay-ui:search-container-column-text>

								<%
								}
								%>

							</c:when>
							<c:otherwise>

								<%
								if (folder != null) {
									PortletURL viewFolderURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

									viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));

									String folderImage = "folder_empty_document";

									if (PropsValues.DL_FOLDER_ICON_CHECK_COUNT && (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0)) {
										folderImage = "folder_full_document";
									}
								%>

									<liferay-ui:search-container-column-image
										src='<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>'
									/>

									<liferay-ui:search-container-column-text colspan="<%= 3 %>">
										<liferay-ui:app-view-entry
											author="<%= folder.getUserName() %>"
											createDate="<%= folder.getCreateDate() %>"
											description="<%= folder.getDescription() %>"
											displayStyle="descriptive"
											folder="<%= true %>"
											markupView="lexicon"
											modifiedDate="<%= folder.getModifiedDate() %>"
											showCheckbox="<%= false %>"
											title="<%= folder.getName() %>"
											url="<%= viewFolderURL.toString() %>"
										/>
									</liferay-ui:search-container-column-text>

								<%
								}

								if (fileEntry != null) {
									FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

									String title = DLUtil.getTitleWithExtension(fileEntry);

									JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
								%>

									<liferay-ui:search-container-column-image
										src="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>"
									/>

									<liferay-ui:search-container-column-text colspan="<%= 2 %>">
										<div class="item-preview" data-href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-title="<%= HtmlUtil.escapeAttribute(title) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>">
											<liferay-ui:app-view-entry
												assetCategoryClassName="<%= DLFileEntry.class.getName() %>"
												assetCategoryClassPK="<%= fileEntry.getFileEntryId() %>"
												assetTagClassName="<%= DLFileEntry.class.getName() %>"
												assetTagClassPK="<%= fileEntry.getFileEntryId() %>"
												author="<%= fileEntry.getUserName() %>"
												createDate="<%= fileEntry.getCreateDate() %>"
												description="<%= fileEntry.getDescription() %>"
												displayStyle="descriptive"
												groupId="<%= fileEntry.getGroupId() %>"
												markupView="lexicon"
												modifiedDate="<%= fileEntry.getModifiedDate() %>"
												showCheckbox="<%= false %>"
												status="<%= latestFileVersion.getStatus() %>"
												title="<%= title %>"
												version="<%= String.valueOf(fileEntry.getVersion()) %>"
											/>
										</div>
									</liferay-ui:search-container-column-text>

								<%
								}
								%>

							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" resultRowSplitter="<%= new RepositoryEntryResultRowSplitter() %>" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>

		<c:if test="<%= !showSearchInfo %>">
			<liferay-ui:drop-here-info message="drop-files-here" />
		</c:if>
	</c:if>
</div>

<aui:script use="liferay-item-selector-repository-entry-browser">
	new Liferay.ItemSelectorRepositoryEntryBrowser(
		{
			closeCaption: '<%= UnicodeLanguageUtil.get(request, tabName) %>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', event);
				}
			},
			rootNode: '#<%= randomNamespace %>ItemSelectorContainer'

			<c:if test="<%= uploadURL != null %>">
				, uploadItemReturnType: '<%= ClassUtil.getClassName(existingFileEntryReturnType) %>',
				uploadItemUrl: '<%= uploadURL.toString() %>'
			</c:if>
		}
	);
</aui:script>