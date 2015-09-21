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

<%@ include file="/browser/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_ui_browser_page") + StringPool.UNDERLINE;

String displayStyle = GetterUtil.getString(request.getAttribute("liferay-item-selector:browser:displayStyle"));
ItemSelectorReturnType draggableFileReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-item-selector:browser:draggableFileReturnType");
ItemSelectorReturnType existingFileEntryReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-item-selector:browser:existingFileEntryReturnType");
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("liferay-item-selector:browser:itemSelectedEventName"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:browser:portletURL");
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-item-selector:browser:searchContainer");
boolean showBreadcrumb = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:browser:showBreadcrumb"));
boolean showDragAndDropZone = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:browser:showDragAndDropZone"));
String tabName = GetterUtil.getString(request.getAttribute("liferay-item-selector:browser:tabName"));
PortletURL uploadURL = (PortletURL)request.getAttribute("liferay-item-selector:browser:uploadURL");
%>

<liferay-util:html-top>
	<link href="<%= ServletContextUtil.getContextPath() + "/browser/css/main.css" %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="lfr-item-viewer" id="<%= randomNamespace %>ItemSelectorContainer">
	<aui:nav-bar>
		<aui:nav collapsible="<%= true %>" cssClass="nav-display-style-buttons navbar-nav" icon="th-list" id="displayStyleButtons">
			<liferay-ui:app-view-display-style
				displayStyle="<%= displayStyle %>"
				displayStyles="<%= BrowserTag.DISPLAY_STYLES %>"
				displayStyleURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>
		</aui:nav>
	</aui:nav-bar>

	<%
	long folderId = ParamUtil.getLong(request, "folderId");
	String keywords = ParamUtil.getString(request, "keywords");
	String selectedTab = ParamUtil.getString(request, "selectedTab");

	boolean showSearchInfo = false;

	if (Validator.isNotNull(keywords) && tabName.equals(selectedTab)) {
		showSearchInfo = true;
	}

	if (showBreadcrumb && !showSearchInfo) {
		ItemSelectorBrowserUtil.addPortletBreadcrumbEntries(folderId, displayStyle, request, PortletURLUtil.clone(portletURL, liferayPortletResponse));
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

	<c:if test="<%= (draggableFileReturnType != null) && showDragAndDropZone && !showSearchInfo %>">
		<liferay-util:buffer var="selectFileHTML">
			<label class="btn btn-default" for="<%= randomNamespace %>InputFile"><liferay-ui:message key="select-file" /></label>

			<input class="hide" id="<%= randomNamespace %>InputFile" type="file" />
		</liferay-util:buffer>

		<div class="drop-enabled drop-zone no-border">
			<strong><liferay-ui:message arguments="<%= selectFileHTML %>" key="drag-and-drop-to-upload-or-x" /></strong>
		</div>
	</c:if>

	<c:if test="<%= existingFileEntryReturnType != null %>">
		<c:choose>
			<c:when test='<%= displayStyle.equals("list") %>'>
				<div class="list-content">
					<liferay-ui:search-container
						searchContainer="<%= searchContainer %>"
						total="<%= searchContainer.getTotal() %>"
						var="listSearchContainer"
					>
						<liferay-ui:search-container-results
							results="<%= searchContainer.getResults() %>"
						/>

						<liferay-ui:search-container-row
							className="com.liferay.portal.kernel.repository.model.RepositoryEntry"
							modelVar="repositoryEntry"
						>

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

								JSONObject itemMedatadaJSONObject = ItemSelectorBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
							%>

								<liferay-ui:search-container-column-text name="title">
									<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">

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

						</liferay-ui:search-container-row>

						<liferay-ui:search-iterator />
					</liferay-ui:search-container>
				</div>
			</c:when>
			<c:otherwise>

				<%
				List<FileEntry> fileEntries = new ArrayList<FileEntry>();
				List<Folder> folders = new ArrayList<Folder>();

				for (Object result : searchContainer.getResults()) {
					if (result instanceof FileEntry) {
						fileEntries.add((FileEntry)result);
					}
					else if (result instanceof FileShortcut) {
						FileShortcut fileShortcut = (FileShortcut)result;

						fileEntries.add(DLAppLocalServiceUtil.getFileEntry(fileShortcut.getToFileEntryId()));

					}
					else {
						folders.add((Folder)result);
					}
				}
				%>

				<c:choose>
					<c:when test='<%= displayStyle.equals("icon") %>'>
						<div class="row" id="folderSection">

							<%
							for (Folder folder : folders) {
								PortletURL viewFolderURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

								viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));
							%>

								<div class="col-md-3">
									<div class="card-horizontal">
										<div class="card-row card-row-padded">
											<div class="card-col-field">
												<span class="icon-folder-close-alt icon-monospaced"></span>
											</div>
											<div class="card-col-content card-col-gutters">
												<h4>
													<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">
														<%= HtmlUtil.escapeAttribute(folder.getName()) %>
													</a>
												</h4>
											</div>
										</div>
									</div>
								</div>

							<%
							}
							%>

						</div>

						<div class="row" id="cardSection">

							<%
							for (FileEntry fileEntry : fileEntries) {
								FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

								String title = DLUtil.getTitleWithExtension(fileEntry);

								JSONObject itemMedatadaJSONObject = ItemSelectorBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
							%>

								<div class="col-lg-2 col-md-3 col-sm-4 col-xs-6">

									<%
									String imageThumbnailSrc = DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
									%>

									<div class="aspect-ratio aspect-ratio-middle">
										<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" style="background-image: url('<%= imageThumbnailSrc %>')" title="<%= HtmlUtil.escapeAttribute(title) %>">
											<img class="hidden" src="<%= imageThumbnailSrc %>" />
										</a>
									</div>
								</div>

							<%
							}
							%>

						</div>
					</c:when>
					<c:otherwise>
						<ul class="tabular-list-group">

							<%
							for (Folder folder : folders) {
								PortletURL viewFolderURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

								viewFolderURL.setParameter("folderId", String.valueOf(folder.getFolderId()));

								String folderImage = "folder_empty_document";

								if (PropsValues.DL_FOLDER_ICON_CHECK_COUNT && (DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), WorkflowConstants.STATUS_APPROVED, true) > 0)) {
									folderImage = "folder_full_document";
								}
							%>

								<li class="list-group-item list-group-item-default">
									<div class="list-group-item-field">
										<img src="<%= themeDisplay.getPathThemeImages() + "/file_system/large/" + folderImage + ".png" %>" />
									</div>

									<div class="list-group-item-content">
										<h5>
											<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">
												<%= HtmlUtil.escape(folder.getName()) %>
											</a>
										</h5>
									</div>
								</li>

							<%
							}
							for (FileEntry fileEntry : fileEntries) {
								FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

								String title = DLUtil.getTitleWithExtension(fileEntry);

								JSONObject itemMedatadaJSONObject = ItemSelectorBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);
							%>

								<li class="list-group-item list-group-item-default" data-href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
									<div class="list-group-item-field">
										<img src="<%= DLUtil.getThumbnailSrc(fileEntry, themeDisplay) %>" style="<%= DLUtil.getThumbnailStyle(true, 9, 128, 128) %>" />
									</div>

									<div class="list-group-item-content">
										<h6>
											<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(fileEntry.getUserName()), LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true)} %>" key="x-modified-x-ago" translateArguments="<%= false %>" />
										</h6>

										<h5>
											<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorBrowserReturnTypeUtil.getValue(existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= HtmlUtil.escapeHREF(DLUtil.getImagePreviewURL(fileEntry, themeDisplay)) %>" title="<%= HtmlUtil.escapeAttribute(title) %>">
												<%= HtmlUtil.escape(title) %>
											</a>
										</h5>

										<h6><liferay-ui:message key="<%= WorkflowConstants.getStatusLabel(latestFileVersion.getStatus()) %>" /></h6>
									</div>
								</li>

							<%
							}
							%>

						</ul>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
			</c:otherwise>
		</c:choose>

		<c:if test="<%= (draggableFileReturnType != null) && !showSearchInfo %>">
			<liferay-ui:drop-here-info message="drop-files-here" />
		</c:if>
	</c:if>
</div>

<aui:script use="liferay-item-selector-browser">
	new Liferay.ItemSelectorBrowser(
		{
			closeCaption: '<%= UnicodeLanguageUtil.get(request, tabName) %>',
			on: {
				selectedItem: function(event) {
					Liferay.Util.getOpener().Liferay.fire('<%= itemSelectedEventName %>', event);
				}
			},
			rootNode: '#<%= randomNamespace %>ItemSelectorContainer'

			<c:if test="<%= draggableFileReturnType != null %>">
				, uploadItemReturnType: '<%= HtmlUtil.escapeAttribute(ClassUtil.getClassName(draggableFileReturnType)) %>',
				uploadItemUrl: '<%= uploadURL.toString() %>'
			</c:if>
		}
	);
</aui:script>