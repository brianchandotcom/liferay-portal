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
PortletURL editImageURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:editImageURL");
String emptyResultsMessage = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:emptyResultsMessage"));
ItemSelectorReturnType existingFileEntryReturnType = (ItemSelectorReturnType)request.getAttribute("liferay-item-selector:repository-entry-browser:existingFileEntryReturnType");
List<String> extensions = (List)request.getAttribute("liferay-item-selector:repository-entry-browser:extensions");
String itemSelectedEventName = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:itemSelectedEventName"));
ItemSelectorReturnTypeResolver<?, FileEntry> itemSelectorReturnTypeResolver = (ItemSelectorReturnTypeResolver<?, FileEntry>)request.getAttribute("liferay-item-selector:repository-entry-browser:itemSelectorReturnTypeResolver");
long maxFileSize = GetterUtil.getLong(request.getAttribute("liferay-item-selector:repository-entry-browser:maxFileSize"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:portletURL");
List<FileEntry> repositoryEntries = (List<FileEntry>)request.getAttribute("liferay-item-selector:repository-entry-browser:repositoryEntries");
int repositoryEntriesCount = GetterUtil.getInteger(request.getAttribute("liferay-item-selector:repository-entry-browser:repositoryEntriesCount"));
boolean showBreadcrumb = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showBreadcrumb"));
boolean showDragAndDropZone = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showDragAndDropZone"));
boolean showSearch = GetterUtil.getBoolean(request.getAttribute("liferay-item-selector:repository-entry-browser:showSearch"));
String tabName = GetterUtil.getString(request.getAttribute("liferay-item-selector:repository-entry-browser:tabName"));
PortletURL uploadURL = (PortletURL)request.getAttribute("liferay-item-selector:repository-entry-browser:uploadURL");

String keywords = ParamUtil.getString(request, "keywords");

boolean showSearchInfo = false;

if (Validator.isNotNull(keywords)) {
	showSearchInfo = true;
}
%>

<liferay-util:html-top>
	<link href="<%= ServletContextUtil.getContextPath() + "/repository_entry_browser/css/main.css" %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<%
RepositoryEntryBrowserDisplayContext repositoryEntryBrowserDisplayContext = new RepositoryEntryBrowserDisplayContext(request);

ItemSelectorRepositoryEntryManagementToolbarDisplayContext itemSelectorRepositoryEntryManagementToolbarDisplayContext = new ItemSelectorRepositoryEntryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, repositoryEntryBrowserDisplayContext);

SearchContainer<?> searchContainer = new SearchContainer(renderRequest, itemSelectorRepositoryEntryManagementToolbarDisplayContext.getCurrentSortingURL(), null, emptyResultsMessage);
%>

<clay:management-toolbar
	clearResultsURL="<%= String.valueOf(itemSelectorRepositoryEntryManagementToolbarDisplayContext.getClearResultsURL()) %>"
	creationMenu="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.getCreationMenu() %>"
	disabled="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= repositoryEntriesCount %>"
	searchActionURL="<%= String.valueOf(itemSelectorRepositoryEntryManagementToolbarDisplayContext.getSearchURL()) %>"
	searchFormMethod="POST"
	searchFormName="searchFm"
	selectable="<%= false %>"
	showCreationMenu="<%= true %>"
	showInfoButton="<%= false %>"
	showSearch="<%= showSearch %>"
	sortingOrder="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(itemSelectorRepositoryEntryManagementToolbarDisplayContext.getSortingURL()) %>"
	viewTypeItems="<%= itemSelectorRepositoryEntryManagementToolbarDisplayContext.getViewTypes() %>"
/>

<clay:container-fluid
	cssClass="item-selector lfr-item-viewer"
	id='<%= randomNamespace + "ItemSelectorContainer" %>'
>
	<c:if test="<%= showSearchInfo %>">
		<liferay-util:include page="/repository_entry_browser/search_info.jsp" servletContext="<%= application %>" />
	</c:if>

	<div class="message-container"></div>

	<%
	long folderId = ParamUtil.getLong(request, "folderId");
	%>

	<c:if test="<%= showBreadcrumb && !showSearchInfo %>">

		<%
		ItemSelectorRepositoryEntryBrowserUtil.addPortletBreadcrumbEntries(folderId, displayStyle, request, liferayPortletRequest, liferayPortletResponse, PortletURLUtil.clone(portletURL, liferayPortletResponse));
		%>

		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>
	</c:if>

	<c:if test="<%= showDragAndDropZone && !showSearchInfo && DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
		<liferay-util:buffer
			var="selectFileHTML"
		>
			<input accept="<%= ListUtil.isEmpty(extensions) ? "*" : StringUtil.merge(extensions) %>" class="input-file" id="<%= randomNamespace %>InputFile" type="file" />

			<label class="btn btn-secondary" for="<%= randomNamespace %>InputFile"><liferay-ui:message key="select-file" /></label>
		</liferay-util:buffer>

		<div class="drop-enabled drop-zone">
			<c:choose>
				<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
					<%= selectFileHTML %>
				</c:when>
				<c:otherwise>
					<strong><liferay-ui:message arguments="<%= selectFileHTML %>" key="drag-and-drop-to-upload-or-x" /></strong>
				</c:otherwise>
			</c:choose>
		</div>
	</c:if>

	<c:if test="<%= (existingFileEntryReturnType != null) || (itemSelectorReturnTypeResolver != null) %>">
		<liferay-ui:search-container
			cssClass='<%= displayStyle.equals("list") ? "main-content-body" : StringPool.BLANK %>'
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
						%>

						<c:if test="<%= fileEntry != null %>">

							<%
							FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

							String title = fileEntry.getTitle();

							JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);

							String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, themeDisplay);
							%>

							<liferay-ui:search-container-column-text
								name="title"
							>
								<a class="item-preview" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserUtil.getItemSelectorReturnTypeClassName(itemSelectorReturnTypeResolver, existingFileEntryReturnType)) %>" data-url="<%= HtmlUtil.escapeAttribute(DLURLHelperUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserUtil.getValue(itemSelectorReturnTypeResolver, existingFileEntryReturnType, fileEntry, themeDisplay)) %>" href="<%= Validator.isNotNull(thumbnailSrc) ? HtmlUtil.escapeHREF(DLURLHelperUtil.getImagePreviewURL(fileEntry, themeDisplay)) : themeDisplay.getPathThemeImages() + "/file_system/large/default.png" %>" title="<%= HtmlUtil.escapeAttribute(title) %>">

									<%
									String iconCssClass = DLUtil.getFileIconCssClass(fileEntry.getExtension());
									%>

									<c:if test="<%= Validator.isNotNull(iconCssClass) %>">
										<liferay-ui:icon
											icon="<%= iconCssClass %>"
											markupView="lexicon"
										/>
									</c:if>

									<span class="taglib-text">
										<%= HtmlUtil.escape(title) %>
									</span>
								</a>
							</liferay-ui:search-container-column-text>

							<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(fileEntry.getGroupId()) %>"
										/>

										<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(fileEntry.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								name="size"
								value="<%= LanguageUtil.formatStorageSize(fileEntry.getSize(), locale) %>"
							/>

							<liferay-ui:search-container-column-status
								name="status"
								status="<%= latestFileVersion.getStatus() %>"
							/>

							<liferay-ui:search-container-column-text
								name="modified-date"
							>
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(fileEntry.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text>
								<clay:button
									borderless="<%= true %>"
									cssClass="component-action icon-view"
									displayType="secondary"
									icon="view"
								/>
							</liferay-ui:search-container-column-text>
						</c:if>

						<c:if test="<%= folder != null %>">

							<%
							PortletURL viewFolderURL = EntryURLUtil.getFolderPortletURL(folder, liferayPortletRequest, liferayPortletResponse, portletURL);
							%>

							<liferay-ui:search-container-column-text
								name="title"
							>
								<a href="<%= HtmlUtil.escapeHREF(viewFolderURL.toString()) %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">
									<liferay-ui:icon
										icon="folder"
										markupView="lexicon"
									/>

									<span class="taglib-text">
										<%= HtmlUtil.escape(folder.getName()) %>
									</span>
								</a>
							</liferay-ui:search-container-column-text>

							<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
								<liferay-ui:search-container-column-text
									name="location"
								>
									<span class="text-secondary">
										<clay:icon
											symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(folder.getGroupId()) %>"
										/>

										<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(folder.getGroupId(), locale) %></small>
									</span>
								</liferay-ui:search-container-column-text>
							</c:if>

							<liferay-ui:search-container-column-text
								name="size"
								value="--"
							/>

							<liferay-ui:search-container-column-text
								name="status"
								value="--"
							/>

							<liferay-ui:search-container-column-text
								name="modified-date"
							>
								<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - folder.getModifiedDate().getTime(), true), HtmlUtil.escape(folder.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
							</liferay-ui:search-container-column-text>

							<liferay-ui:search-container-column-text
								value="--"
							/>
						</c:if>
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
								row.setCssClass("card-page-item card-page-item-directory");
								%>

								<c:if test="<%= folder != null %>">

									<%
									PortletURL viewFolderURL = EntryURLUtil.getFolderPortletURL(folder, liferayPortletRequest, liferayPortletResponse, portletURL);
									%>

									<liferay-ui:search-container-column-text
										colspan="<%= 3 %>"
									>
										<div class="card card-horizontal card-interactive card-interactive-secondary">
											<div class="card-body">
												<div class="card-row">
													<clay:content-col>
														<clay:sticker
															displayType="secondary"
															icon="folder"
															inline="<%= true %>"
														/>
													</clay:content-col>

													<div class="autofit-col autofit-col-expand autofit-col-gutters">
														<p class="card-title text-truncate">
															<aui:a href="<%= viewFolderURL.toString() %>" title="<%= HtmlUtil.escapeAttribute(folder.getName()) %>">
																<%= HtmlUtil.escape(folder.getName()) %>
															</aui:a>
														</p>

														<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
															<span class="text-secondary">
																<clay:icon
																	symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(folder.getGroupId()) %>"
																/>

																<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(folder.getGroupId(), locale) %></small>
															</span>
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</liferay-ui:search-container-column-text>
								</c:if>

								<c:if test="<%= fileEntry != null %>">

									<%
									FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

									String title = fileEntry.getTitle();

									JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);

									Map<String, Object> data = HashMapBuilder.<String, Object>put(
										"description", fileEntry.getDescription()
									).put(
										"fileEntryId", fileEntry.getFileEntryId()
									).build();

									String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, themeDisplay);

									if (Validator.isNotNull(thumbnailSrc)) {
										data.put("href", DLURLHelperUtil.getImagePreviewURL(fileEntry, themeDisplay));
									}
									else {
										data.put("href", themeDisplay.getPathThemeImages() + "/file_system/large/default.png");
									}

									data.put("metadata", itemMedatadaJSONObject.toString());
									data.put("returnType", ItemSelectorRepositoryEntryBrowserUtil.getItemSelectorReturnTypeClassName(itemSelectorReturnTypeResolver, existingFileEntryReturnType));
									data.put("title", title);
									data.put("url", DLURLHelperUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK));
									data.put("value", ItemSelectorRepositoryEntryBrowserUtil.getValue(itemSelectorReturnTypeResolver, existingFileEntryReturnType, fileEntry, themeDisplay));
									%>

									<liferay-ui:search-container-column-text>
										<c:choose>
											<c:when test="<%= Validator.isNull(thumbnailSrc) %>">
												<liferay-frontend:icon-vertical-card
													actionJsp="/repository_entry_browser/action_button_preview.jsp"
													actionJspServletContext="<%= application %>"
													cardCssClass="card-interactive"
													cssClass="file-card form-check form-check-card item-preview"
													data="<%= data %>"
													icon="documents-and-media"
													title="<%= title %>"
												>
													<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
														<liferay-frontend:vertical-card-footer>
															<span class="text-secondary">
																<clay:icon
																	symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(fileEntry.getGroupId()) %>"
																/>

																<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(fileEntry.getGroupId(), locale) %></small>
															</span>
														</liferay-frontend:vertical-card-footer>
													</c:if>

													<liferay-frontend:vertical-card-sticker-bottom>
														<liferay-document-library:mime-type-sticker
															cssClass="sticker-bottom-left sticker-secondary"
															fileVersion="<%= latestFileVersion %>"
														/>
													</liferay-frontend:vertical-card-sticker-bottom>
												</liferay-frontend:icon-vertical-card>
											</c:when>
											<c:otherwise>
												<liferay-frontend:vertical-card
													actionJsp="/repository_entry_browser/action_button_preview.jsp"
													actionJspServletContext="<%= application %>"
													cardCssClass="card-interactive"
													cssClass="form-check form-check-card image-card item-preview"
													data="<%= data %>"
													imageUrl="<%= thumbnailSrc %>"
													title="<%= title %>"
												>
													<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
														<liferay-frontend:vertical-card-footer>
															<span class="text-secondary">
																<clay:icon
																	symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(fileEntry.getGroupId()) %>"
																/>

																<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(fileEntry.getGroupId(), locale) %></small>
															</span>
														</liferay-frontend:vertical-card-footer>
													</c:if>

													<liferay-frontend:vertical-card-sticker-bottom>
														<liferay-document-library:mime-type-sticker
															cssClass="sticker-bottom-left sticker-secondary"
															fileVersion="<%= latestFileVersion %>"
														/>
													</liferay-frontend:vertical-card-sticker-bottom>
												</liferay-frontend:vertical-card>
											</c:otherwise>
										</c:choose>
									</liferay-ui:search-container-column-text>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="<%= folder != null %>">

									<%
									PortletURL viewFolderURL = EntryURLUtil.getFolderPortletURL(folder, liferayPortletRequest, liferayPortletResponse, portletURL);
									%>

									<liferay-ui:search-container-column-icon
										icon="folder"
									/>

									<liferay-ui:search-container-column-text
										colspan="<%= 3 %>"
									>
										<h5>
											<a href="<%= HtmlUtil.escapeAttribute(viewFolderURL.toString()) %>" title="<%= folder.getName() %>">
												<strong><%= HtmlUtil.escape(folder.getName()) %></strong>
											</a>
										</h5>

										<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
											<h6 class="text-default">
												<liferay-ui:message key="location" />:
												<span class="text-secondary">
													<clay:icon
														symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(folder.getGroupId()) %>"
													/>

													<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(folder.getGroupId(), locale) %></small>
												</span>
											</h6>
										</c:if>

										<h6 class="text-default">
											<liferay-ui:message key="created" />:

											<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - folder.getCreateDate().getTime(), true), HtmlUtil.escape(folder.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
										</h6>
									</liferay-ui:search-container-column-text>
								</c:if>

								<c:if test="<%= fileEntry != null %>">

									<%
									row.setCssClass("item-selector-list-row " + row.getCssClass());

									FileVersion latestFileVersion = fileEntry.getLatestFileVersion();

									String title = fileEntry.getTitle();

									JSONObject itemMedatadaJSONObject = ItemSelectorRepositoryEntryBrowserUtil.getItemMetadataJSONObject(fileEntry, locale);

									String thumbnailSrc = DLURLHelperUtil.getThumbnailSrc(fileEntry, themeDisplay);
									%>

									<c:choose>
										<c:when test="<%= Validator.isNotNull(thumbnailSrc) %>">
											<liferay-ui:search-container-column-image
												src="<%= DLURLHelperUtil.getThumbnailSrc(fileEntry, themeDisplay) %>"
											/>
										</c:when>
										<c:otherwise>
											<liferay-ui:search-container-column-text>
												<liferay-document-library:mime-type-sticker
													fileVersion="<%= latestFileVersion %>"
												/>
											</liferay-ui:search-container-column-text>
										</c:otherwise>
									</c:choose>

									<liferay-ui:search-container-column-text
										colspan="<%= 2 %>"
									>
										<div class="item-preview" data-href="<%= Validator.isNotNull(thumbnailSrc) ? HtmlUtil.escapeHREF(DLURLHelperUtil.getImagePreviewURL(fileEntry, themeDisplay)) : themeDisplay.getPathThemeImages() + "/file_system/large/default.png" %>" data-metadata="<%= HtmlUtil.escapeAttribute(itemMedatadaJSONObject.toString()) %>" data-returnType="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserUtil.getItemSelectorReturnTypeClassName(itemSelectorReturnTypeResolver, existingFileEntryReturnType)) %>" data-title="<%= HtmlUtil.escapeAttribute(title) %>" data-url="<%= HtmlUtil.escapeAttribute(DLURLHelperUtil.getPreviewURL(fileEntry, latestFileVersion, themeDisplay, StringPool.BLANK)) %>" data-value="<%= HtmlUtil.escapeAttribute(ItemSelectorRepositoryEntryBrowserUtil.getValue(itemSelectorReturnTypeResolver, existingFileEntryReturnType, fileEntry, themeDisplay)) %>">
											<h5>
												<strong><%= title %></strong>
											</h5>

											<c:if test="<%= repositoryEntryBrowserDisplayContext.isSearchEverywhere() %>">
												<h6 class="text-default">
													<liferay-ui:message key="location" />:
													<span class="text-secondary">
														<clay:icon
															symbol="<%= repositoryEntryBrowserDisplayContext.getGroupCssIcon(fileEntry.getGroupId()) %>"
														/>

														<small><%= repositoryEntryBrowserDisplayContext.getGroupLabel(fileEntry.getGroupId(), locale) %></small>
													</span>
												</h6>
											</c:if>

											<h6 class="text-default">
												<liferay-ui:message key="version" />:

												<%= String.valueOf(fileEntry.getVersion()) %>
											</h6>

											<h6 class="text-default">
												<liferay-ui:message key="last-updated" />:

												<liferay-ui:message arguments="<%= new String[] {LanguageUtil.getTimeDescription(locale, System.currentTimeMillis() - fileEntry.getModifiedDate().getTime(), true), HtmlUtil.escape(latestFileVersion.getUserName())} %>" key="x-ago-by-x" translateArguments="<%= false %>" />
											</h6>
										</div>
									</liferay-ui:search-container-column-text>

									<liferay-ui:search-container-column-text>
										<clay:button
											borderless="<%= true %>"
											cssClass="component-action icon-view"
											displayType="secondary"
											icon="view"
										/>
									</liferay-ui:search-container-column-text>
								</c:if>
							</c:otherwise> </c:choose>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
				resultRowSplitter="<%= new RepositoryEntryResultRowSplitter() %>"
				searchContainer="<%= searchContainer %>"
			/>
		</liferay-ui:search-container>

		<c:if test="<%= !showSearchInfo && (uploadURL != null) %>">
			<liferay-ui:drop-here-info
				message="drop-files-here"
			/>
		</c:if>
	</c:if>

	<div class="item-selector-preview-container"></div>
</clay:container-fluid>

<aui:script require='<%= npmResolvedPackageName + "/repository_entry_browser/js/ItemSelectorRepositoryEntryBrowser.es as ItemSelectorRepositoryEntryBrowser" %>'>
	var itemSelector = new ItemSelectorRepositoryEntryBrowser.default({
		closeCaption: '<%= UnicodeLanguageUtil.get(request, tabName) %>',

		<c:if test="<%= editImageURL != null %>">
			editImageURL: '<%= editImageURL.toString() %>',
		</c:if>

		maxFileSize: '<%= maxFileSize %>',

		rootNode: '#<%= randomNamespace %>ItemSelectorContainer',

		validExtensions:
			'<%= ListUtil.isEmpty(extensions) ? "*" : StringUtil.merge(extensions) %>',

		<c:if test="<%= uploadURL != null %>">

			<%
			String returnType = ItemSelectorRepositoryEntryBrowserUtil.getItemSelectorReturnTypeClassName(itemSelectorReturnTypeResolver, existingFileEntryReturnType);

			uploadURL.setParameter("returnType", returnType);
			%>

			uploadItemReturnType: '<%= HtmlUtil.escapeAttribute(returnType) %>',
			uploadItemURL: '<%= uploadURL.toString() %>',
		</c:if>
	});

	itemSelector.on('selectedItem', (event) => {
		Liferay.Util.getOpener().Liferay.fire(
			'<%= itemSelectedEventName %>',
			event
		);
	});
</aui:script>