<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long newFolderId = ParamUtil.getLong(request, "newFolderId");

List<BookmarksFolder> folders = (List<BookmarksFolder>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDERS);

List<BookmarksFolder> invalidMoveFolders = new ArrayList<BookmarksFolder>();
List<BookmarksFolder> validMoveFolders = new ArrayList<BookmarksFolder>();

for (BookmarksFolder curFolder : folders) {
	boolean movePermission = BookmarksFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE);

	if (movePermission) {
		validMoveFolders.add(curFolder);
	}
	else {
		invalidMoveFolders.add(curFolder);
	}
}

BookmarksEntry entry = (BookmarksEntry)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRY);

List<BookmarksEntry> entries = null;

if (entry != null) {
	entries = new ArrayList<BookmarksEntry>();

	entries.add(entry);
}
else {
	entries = (List<BookmarksEntry>)request.getAttribute(BookmarksWebKeys.BOOKMARKS_ENTRIES);
}

List<BookmarksEntry> validMoveEntries = new ArrayList<BookmarksEntry>();
List<BookmarksEntry> invalidMoveEntries = new ArrayList<BookmarksEntry>();

for (BookmarksEntry curEntry : entries) {
	boolean movePermission = BookmarksEntryPermission.contains(permissionChecker, curEntry, ActionKeys.UPDATE);

	if (movePermission) {
		validMoveEntries.add(curEntry);
	}
	else {
		invalidMoveEntries.add(curEntry);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(LanguageUtil.get(request, "move-entries"));
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl\"" : StringPool.BLANK %>>
	<portlet:actionURL name="/bookmarks/move_entry" var="moveEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/bookmarks/move_entry" />
	</portlet:actionURL>

	<aui:form action="<%= moveEntryURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveEntry(false);" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.MOVE %>" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="newFolderId" type="hidden" value="<%= newFolderId %>" />

		<c:if test="<%= !portletTitleBasedNavigation %>">
			<liferay-ui:header
				backURL="<%= redirect %>"
				title="move-entries"
			/>
		</c:if>

		<div class="sheet">
			<div class="panel-group panel-group-flush">
				<aui:fieldset>
					<c:if test="<%= !validMoveFolders.isEmpty() %>">
						<div class="move-list-info">
							<h4><liferay-ui:message arguments="<%= validMoveFolders.size() %>" key="x-folders-are-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
						</div>

						<div class="move-list">
							<ul class="list-unstyled">

								<%
								for (BookmarksFolder folder : validMoveFolders) {
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksFolder.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
								%>

									<li class="move-folder">
										<liferay-ui:icon
											icon="<%= assetRenderer.getIconCssClass() %>"
											markupView="lexicon"
										/>

										<span class="folder-title">
											<%= HtmlUtil.escape(folder.getName()) %>
										</span>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</c:if>

					<c:if test="<%= !invalidMoveFolders.isEmpty() %>">
						<div class="move-list-info">
							<h4><liferay-ui:message arguments="<%= invalidMoveFolders.size() %>" key="x-folders-cannot-be-moved" translateArguments="<%= false %>" /></h4>
						</div>

						<div class="move-list">
							<ul class="list-unstyled">

								<%
								for (BookmarksFolder folder : invalidMoveFolders) {
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksFolder.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(folder.getFolderId());
								%>

									<li class="icon-warning-sign move-error move-folder">
										<liferay-ui:icon
											icon="<%= assetRenderer.getIconCssClass() %>"
											markupView="lexicon"
										/>

										<span class="folder-title">
											<%= HtmlUtil.escape(folder.getName()) %>
										</span>
										<span class="error-message">
											<liferay-ui:message key="you-do-not-have-the-required-permissions" />
										</span>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</c:if>

					<aui:input name="rowIdsBookmarksFolder" type="hidden" value="<%= ListUtil.toString(validMoveFolders, BookmarksFolder.FOLDER_ID_ACCESSOR) %>" />

					<c:if test="<%= !validMoveEntries.isEmpty() %>">
						<div class="move-list-info">
							<h4><liferay-ui:message arguments="<%= validMoveEntries.size() %>" key="x-entries-are-ready-to-be-moved" translateArguments="<%= false %>" /></h4>
						</div>

						<div class="move-list">
							<ul class="list-unstyled">

								<%
								for (BookmarksEntry validMoveEntry : validMoveEntries) {
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksEntry.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(validMoveEntry.getEntryId());
								%>

									<li class="move-file">
										<liferay-ui:icon
											icon="<%= assetRenderer.getIconCssClass() %>"
											markupView="lexicon"
										/>

										<span class="file-title" title="<%= HtmlUtil.escapeAttribute(validMoveEntry.getName()) %>">
											<%= HtmlUtil.escape(validMoveEntry.getName()) %>
										</span>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</c:if>

					<c:if test="<%= !invalidMoveEntries.isEmpty() %>">
						<div class="move-list-info">
							<h4><liferay-ui:message arguments="<%= invalidMoveEntries.size() %>" key="x-entries-cannot-be-moved" translateArguments="<%= false %>" /></h4>
						</div>

						<div class="move-list">
							<ul class="list-unstyled">

								<%
								for (BookmarksEntry invalidMoveEntry : invalidMoveEntries) {
									AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(BookmarksEntry.class.getName());

									AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(invalidMoveEntry.getEntryId());
								%>

									<li class="icon-warning-sign move-error move-file">
										<liferay-ui:icon
											icon="<%= assetRenderer.getIconCssClass() %>"
											markupView="lexicon"
										/>

										<span class="file-title" title="<%= HtmlUtil.escapeAttribute(invalidMoveEntry.getName()) %>">
											<%= HtmlUtil.escape(invalidMoveEntry.getName()) %>
										</span>
										<span class="error-message">
											<liferay-ui:message key="you-do-not-have-the-required-permissions" />
										</span>
									</li>

								<%
								}
								%>

							</ul>
						</div>
					</c:if>

					<aui:input name="rowIdsBookmarksEntry" type="hidden" value="<%= ListUtil.toString(validMoveEntries, BookmarksEntry.ENTRY_ID_ACCESSOR) %>" />

					<%
					String folderName = StringPool.BLANK;

					if (newFolderId > 0) {
						BookmarksFolder folder = BookmarksFolderLocalServiceUtil.getFolder(newFolderId);

						folder = folder.toEscapedModel();

						folderName = folder.getName();
					}
					else {
						folderName = LanguageUtil.get(request, "home");
					}
					%>

					<div class="form-group">
						<aui:input label="new-folder" name="folderName" type="resource" value="<%= folderName %>" />

						<aui:button name="selectFolderButton" value="select" />
					</div>
				</aui:fieldset>
			</div>
		</div>

		<aui:button-row>
			<aui:button type="submit" value="move" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />saveEntry() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			submitForm(form);
		}
	}

	var <portlet:namespace />selectFolderButton = document.getElementById(
		'<portlet:namespace />selectFolderButton'
	);

	if (<portlet:namespace />selectFolderButton) {
		<portlet:namespace />selectFolderButton.addEventListener(
			'click',
			(event) => {
				var folderName = document.getElementById(
					'<portlet:namespace />folderName'
				);

				if (folderName) {
					Liferay.Util.openSelectionModal({
						onSelect: function (event) {
							var folderData = {
								idString: 'newFolderId',
								idValue: event.entityid,
								nameString: 'folderName',
								nameValue: event.entityname,
							};

							Liferay.Util.selectFolder(
								folderData,
								'<portlet:namespace />'
							);
						},
						selectedData: [folderName.value],
						selectEventName: '<portlet:namespace />selectFolder',
						title:
							'<liferay-ui:message arguments="folder" key="select-x" />',

						<portlet:renderURL var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" />
							<portlet:param name="folderId" value="<%= String.valueOf(newFolderId) %>" />
						</portlet:renderURL>

						url: '<%= selectFolderURL.toString() %>',
					});
				}
			}
		);
	}
</aui:script>

<%
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "move-entries"), currentURL);
%>