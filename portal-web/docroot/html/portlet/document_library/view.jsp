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
Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

long start = ParamUtil.getLong(request, "start");
long end = ParamUtil.getLong(request, "end", SearchContainer.DEFAULT_DELTA);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:layout cssClass="view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />
		</aui:column>

		<aui:column columnWidth="<%= showFolderMenu ? 80 : 100 %>" cssClass="context-pane" last="<%= true %>">
			<div class="lfr-header-row">
				<div class="lfr-header-row-content">
					<liferay-util:include page="/html/portlet/document_library/file_entry_search.jsp" />

					<div class="toolbar">
						<liferay-util:include page="/html/portlet/document_library/toolbar.jsp" />
					</div>

					<div class="display-style">
						<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>
					</div>
				</div>
			</div>

			<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
				<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
			</div>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= editFileEntryURL.toString() %>" method="get" name="fm2">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />
				<aui:input name="fileShortcutIds" type="hidden" />

				<div class="document-container" id="<portlet:namespace />documentContainer">
					<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
				</div>

				<div class="document-entries-paginator"></div>
			</aui:form>
		</aui:column>
	</aui:layout>
</div>

<%
if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
		PortalUtil.setPageSubtitle(folder.getName(), request);
		PortalUtil.setPageDescription(folder.getDescription(), request);
	}
}
%>

<aui:script>
	function <portlet:namespace />editFileEntry(action) {
		if (action == '<%= Constants.DELETE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
		else if (action == '<%= Constants.MOVE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /></portlet:renderURL>');
		}
		else {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />doFileEntryAction',
		function(action, url) {
			document.<portlet:namespace />fm2.method = "post";
			document.<portlet:namespace />fm2.<portlet:namespace /><%= Constants.CMD %>.value = action;
			document.<portlet:namespace />fm2.<portlet:namespace />folderIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileShortcutIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox');

			submitForm(document.<portlet:namespace />fm2, url);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer');

			var disabled = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			actionsButton.toggleClass('disabled', disabled);
		},
		['liferay-util-list-fields']
	);

	<portlet:namespace />toggleActionsButton();
</aui:script>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var markSelected = function(node) {
		var documentThumbnail = node.ancestor('.document-display-style.selectable');

		documentThumbnail.toggleClass('selected');
	};

	var documentContainer = A.one('#<portlet:namespace />documentContainer');

	documentContainer.delegate(
		'change',
		function(event) {
			markSelected(event.currentTarget);

			<portlet:namespace />toggleActionsButton();

			Liferay.Util.checkAllBox(documentContainer, ['<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox'], '#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox');
		},
		'.document-selector'
	);

	<c:if test='<%= (!displayStyle.equals("list")) %>'>
		var toggleHoverClass = function(event) {
			var documentDisplayStyle = event.currentTarget.ancestor('.document-display-style');

			if (documentDisplayStyle) {
				documentDisplayStyle.toggleClass('hover', (event.type == 'focus'));
			}
		};

		documentContainer.delegate('focus', toggleHoverClass, '*');

		documentContainer.delegate('blur', toggleHoverClass, '*');
	</c:if>
</aui:script>

<span id="<portlet:namespace />displayStyleButtonsContainer">
	<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
</span>

<aui:script use="aui-paginator,liferay-list-view,liferay-history-manager">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL" />

	var AObject = A.Object;
	var Lang = A.Lang;

	var owns = AObject.owns;

	var EVENT_DATA_REQUEST = '<portlet:namespace />dataRequest';

	var EVENT_DATA_RETRIEVE_SUCCESS = '<portlet:namespace />dataRetrieveSuccess';

	var INVALID_VALUE = A.Attribute.INVALID_VALUE;

	var SRC_HISTORY = 0;

	var defaultParams = {
		'<portlet:namespace />struts_action': '/document_library/view',
		'<portlet:namespace />displayStyle': '<%= HtmlUtil.escapeJS(displayStyle) %>',
		'<portlet:namespace />folderId': '<%= folderId %>',
		'<portlet:namespace />showSiblings': <%= Boolean.TRUE.toString() %>,
		'<portlet:namespace />viewBreadcrumb': <%= Boolean.TRUE.toString() %>,
		'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
		'<portlet:namespace />viewFolders': <%= Boolean.TRUE.toString() %>
	};

	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	var documentLibraryContainer = A.one('#<portlet:namespace />documentLibraryContainer');

	var displayStyleToolbarNode = A.one('#<portlet:namespace />displayStyleToolbar');

	var History = Liferay.HistoryManager;

	var ioRequest;

	function addHistoryState(data) {
		var historyState = A.clone(data);

		var currentHistoryState = History.get();

		AObject.each(
			currentHistoryState,
			function(index, item, collection) {
				if (!owns(historyState, item)) {
					historyState[item] = null;
				}
			}
		);

		if (!AObject.isEmpty(historyState)) {
			History.add(historyState);
		}
	}

	function afterDataRequest(event) {
		var requestParams = event.requestParams;

		var data = {
			'<portlet:namespace />folderId': '<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
			'<portlet:namespace />displayStyle': History.get('<portlet:namespace />displayStyle') || '<%= HtmlUtil.escape(displayStyle) %>',
			'<portlet:namespace />viewFolders': <%= Boolean.TRUE.toString() %>
		};

		if (!AObject.isEmpty(requestParams)) {
			A.mix(data, requestParams, true);
		}

		documentLibraryContainer.loadingmask.show();

		if (event.src !== SRC_HISTORY) {
			addHistoryState(data);
		}

		ioRequest = getIORequest();

		ioRequest.set('data', data);

		ioRequest.start();
	}

	function afterStateChange(event) {
		var state = History.get();

		var requestParams = {};

		AObject.each(
			state,
			function(value, key, collection) {
				if (key.indexOf('<portlet:namespace />') == 0) {
					requestParams[key] = value;
				}
			}
		);

		if (!AObject.isEmpty(requestParams)) {
			Liferay.fire(
				EVENT_DATA_REQUEST,
				{
					requestParams: requestParams,
					src: SRC_HISTORY
				}
			);
		}
	}

	function afterListViewItemChange(event) {
		var selFolder = A.one('.folder.selected');

		if (selFolder) {
			selFolder.removeClass('selected');
		}

		var item = event.newVal;

		item.ancestor('.folder').addClass('selected');

		var dataDirectionRight = item.attr('data-direction-right');
		var dataFileEntryTypeId = item.attr('data-file-entry-type-id');
		var dataFolderId = item.attr('data-folder-id');
		var dataNavigation = item.attr('data-navigation');
		var dataRefreshEntries = item.attr('data-refresh-entries');
		var dataRefreshFolders = item.attr('data-refresh-folders');
		var dataShowSiblings = item.attr('data-show-siblings');
		var dataShowRootFolder = item.attr('data-show-root-folder');

		var direction = 'left';

		if (dataDirectionRight) {
			direction = 'right';
		}

		listView.set('direction', direction);

		var config = {
			'<portlet:namespace />struts_action': '/document_library/view',
			'<portlet:namespace />end': <%= SearchContainer.DEFAULT_DELTA %>,
			'<portlet:namespace />start': 0,
			'<portlet:namespace />refreshEntries': dataRefreshEntries,
			'<portlet:namespace />viewAddButton': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewBreadcrumb': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewDisplayStyleButtons': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewFileEntrySearch': <%= Boolean.TRUE.toString() %>,
		};

		if (dataFolderId) {
			config['<portlet:namespace />folderId'] = dataFolderId;
		}

		if (dataNavigation) {
			config['<portlet:namespace />navigation'] = dataNavigation;
		}

		if (dataRefreshEntries) {
			config['<portlet:namespace />viewEntries'] = dataRefreshEntries;
		}

		if (dataShowSiblings) {
			config['<portlet:namespace />showSiblings'] = dataShowSiblings;
		}

		if (dataShowRootFolder) {
			config['<portlet:namespace />showRootFolder'] = dataShowRootFolder;
		}

		if (dataFileEntryTypeId) {
			config['<portlet:namespace />fileEntryTypeId'] = dataFileEntryTypeId;
		}

		if (dataRefreshFolders) {
			config['<portlet:namespace />refreshFolders'] = dataRefreshFolders;
		}

		Liferay.fire(
			EVENT_DATA_REQUEST,
			{
				requestParams: config
			}
		);
	}

	function getIORequest() {
		if (!ioRequest) {
			ioRequest = A.io.request(
				'<%= mainURL %>',
				{
					autoLoad: false,
					after: {
						success: sendIOResponse,
						failure: sendIOResponse
					}
				}
			);
		}

		return ioRequest;
	}

	function getResultsStartEnd(page, rowsPerPage) {
		if (!Lang.isValue(page)) {
			page = entryPaginator.get('page') - 1 || 0;
		}

		if (!Lang.isValue(rowsPerPage)) {
			rowsPerPage = entryPaginator.get('rowsPerPage');
		}

		var start = page * rowsPerPage;
		var end = start + rowsPerPage;

		return [start, end];
	}

	function mainEntry() {
		var initialState = History.get();

		if (!AObject.isEmpty(initialState)) {
			AObject.each(
				initialState,
				function(item, index, collection) {
					if (index.indexOf('<portlet:namespace />') == 0) {
						defaultParams[index] = item;
					}
				}
			);
		}

		Liferay.fire(
			EVENT_DATA_REQUEST,
			{
				requestParams: defaultParams
			}
		);
	}

	function onDataRetrieveSuccess(event) {
		var responseData = event.responseData;

		documentLibraryContainer.loadingmask.hide();

		var content = A.Node.create(responseData);

		if (content) {
			setBreadcrumb(content);
			setButtons(content);
			setEntries(content);
			setFileEntrySearch(content);
			setFolders(content);
			setParentFolderTitle(content);
			syncDisplayStyleToolbar(content);
			setSearchResults(content);
		}
	}

	function setBreadcrumb(content) {
		var breadcrumb = content.one('#<portlet:namespace />breadcrumb');

		if (breadcrumb) {
			var breadcrumbContainer = A.one('#<portlet:namespace />breadcrumbContainer');

			breadcrumbContainer.setContent(breadcrumb);
		}
	}

	function setButtons(content) {
		var addButton = content.one('#<portlet:namespace />addButton');

		if (addButton) {
			var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');

			addButtonContainer.plug(A.Plugin.ParseContent);
			addButtonContainer.setContent(addButton);
		}

		var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

		if (displayStyleButtons) {
			displayStyleToolbarNode.empty();

			var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');

			displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
			displayStyleButtonsContainer.setContent(displayStyleButtons);
		}

		var sortButton = content.one('#<portlet:namespace />sortButton');

		if (sortButton) {
			var sortButtonContainer = A.one('#<portlet:namespace />sortButtonContainer');

			sortButtonContainer.setContent(sortButton);
		}
	}

	function setEntries(content) {
		var entries = content.one('#<portlet:namespace />entries');

		if (entries) {
			entriesContainer.plug(A.Plugin.ParseContent);
			entriesContainer.setContent(entries);
		}
	}

	function setFileEntrySearch(content) {
		var fileEntrySearch = content.one('#<portlet:namespace />fileEntrySearch');

		if (fileEntrySearch) {
			var fileEntrySearchContainer = A.one('#<portlet:namespace />fileEntrySearchContainer');

			fileEntrySearchContainer.plug(A.Plugin.ParseContent);
			fileEntrySearchContainer.setContent(fileEntrySearch);
		}
	}

	function setFolders(content) {
		var folders = content.one('#<portlet:namespace />folderContainer');

		if (folders) {
			var refreshFolders = folders.attr('data-refresh-folders');

			if (refreshFolders) {
				listView.set('data', folders);
			}
		}
	}

	function setParentFolderTitle(content) {
		var parentFolderTitle = content.one('#<portlet:namespace />parentFolderTitle');

		if (parentFolderTitle) {
			var parentFolderTitleContainer = A.one('#<portlet:namespace />parentFolderTitleContainer');

			parentFolderTitleContainer.setContent(parentFolderTitle);
		}
	}

	function setSearchResults(content) {
		var searchResults = content.one('#<portlet:namespace />searchResults');

		if (searchResults) {
			entriesContainer.plug(A.Plugin.ParseContent);
			entriesContainer.setContent(searchResults);
		}
	}

	function sendIOResponse(event) {
		var instance = this;

		var data = instance.get('data');
		var reponseData = instance.get('responseData');

		var eventType = '<portlet:namespace />dataRetrieveFailure';

		if (event.type.indexOf('success') > -1) {
			eventType = EVENT_DATA_RETRIEVE_SUCCESS;
		}

		Liferay.fire(
			eventType,
			{
				data: data,
				responseData: reponseData
			}
		);
	}

	function syncDisplayStyleToolbar(content) {
		var displayStyleToolbar = displayStyleToolbarNode.getData('displayStyleToolbar');

		var displayStyle = History.get('<portlet:namespace />displayStyle') || 'icon';

		displayStyleToolbar.item(0).StateInteraction.set('active', displayStyle === 'icon');
		displayStyleToolbar.item(1).StateInteraction.set('active', displayStyle === 'descriptive');
		displayStyleToolbar.item(2).StateInteraction.set('active', displayStyle === 'list');
	}

	function updatePaginatorValues(event) {
		var requestParams = event.requestParams;

		if (requestParams && !owns(requestParams, '<portlet:namespace />start') && !owns(requestParams, '<portlet:namespace />end')) {
			var startEndParams = getResultsStartEnd();

			A.mix(
				requestParams,
				{
					'<portlet:namespace />start': startEndParams[0],
					'<portlet:namespace />end': startEndParams[1]
				},
				true
			)
		}
	}

	var entryPaginator = new A.Paginator(
		{
			circular: false,
			containers: '.document-entries-paginator',
			firstPageLinkLabel: '<<',
			lastPageLinkLabel: '>>',
			nextPageLinkLabel: '>',
			prevPageLinkLabel: '<',
			rowsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			rowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>]
		}
	).render();

	entryPaginator.on(
		'changeRequest',
		function(event) {
			var state = event.state;

			var startEndParams = getResultsStartEnd();

			var requestParams = getIORequest().get('data');

			A.mix(
				requestParams,
				{
					'<portlet:namespace />start': startEndParams[0],
					'<portlet:namespace />end': startEndParams[1],
					'<portlet:namespace />refreshFolders': false
				},
				true
			);

			Liferay.fire(
				EVENT_DATA_REQUEST,
				{
					requestParams: requestParams
				}
			);
		}
	);

	Liferay.on(EVENT_DATA_REQUEST, updatePaginatorValues);

	Liferay.on(EVENT_DATA_RETRIEVE_SUCCESS, onDataRetrieveSuccess);

	Liferay.on(
		'viewEntriesLoaded',
		function(event) {
			entryPaginator.setState(
				{
					page: event.page,
					rowsPerPage: event.rowsPerPage,
					total: event.total
				}
			);

			paginationURL = event.paginationURL;
		}
	);

	Liferay.after(EVENT_DATA_REQUEST, afterDataRequest);

	var listView = new Liferay.ListView(
		{
			itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
			srcNode: '#<portlet:namespace />folderContainer'
		}
	).render();

	listView.after('itemChange', afterListViewItemChange);

	A.one('#<portlet:namespace />documentLibraryContainer').delegate(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.fire(
				EVENT_DATA_REQUEST,
				{
					requestParams: {
						'<portlet:namespace />struts_action': '/document_library/view',
						'<portlet:namespace />action': 'browseFolder',
						'<portlet:namespace />end': entryPaginator.get('rowsPerPage'),
						'<portlet:namespace />folderId': event.currentTarget.attr('data-folder-id'),
						'<portlet:namespace />refreshFolders': event.currentTarget.attr('data-refresh-folders'),
						'<portlet:namespace />showSiblings': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />start': 0,
						'<portlet:namespace />viewAddButton': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewBreadcrumb': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewDisplayStyleButtons': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
						'<portlet:namespace />viewFileEntrySearch': <%= Boolean.TRUE.toString() %>
					}
				}
			);
		},
		'a[data-folder=true], #<portlet:namespace />breadcrumbContainer a'
	);

	History.after('stateChange', afterStateChange);

	documentLibraryContainer.plug(A.LoadingMask);

	if (!History.HTML5) {
		var state = History.get();

		if (!AObject.isEmpty(state)) {
			mainEntry();
		}
	}
</aui:script>