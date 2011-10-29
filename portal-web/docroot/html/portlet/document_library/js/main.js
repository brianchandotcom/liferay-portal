AUI().add(
	'liferay-document-library',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;

		var formatSelectorNS = A.Node.formatSelectorNS;

		var owns = AObject.owns;

		var History = Liferay.HistoryManager;

		var ATTR_CHECKED = 'checked';

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ACTIVE_AREA_PROXY = 'active-area-proxy';

		var CSS_DOCUMENT_DISPLAY_STYLE = '.document-display-style';

		var CSS_DOCUMENT_DISPLAY_STYLE_SELECTABLE = '.document-display-style.selectable';

		var CSS_DOCUMENT_DISPLAY_STYLE_SELECTED = '.document-display-style.selected';

		var CSS_RESULT_ROW = '.results-row';

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_REFRESH_FOLDERS = 'data-refresh-folders';

		var DISPLAY_STYLE_LIST = 'list';

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var DOCUMENT_LIBRARY_GROUP = 'document-library';

		var REFRESH_FOLDERS = 'refreshFolders';

		var ROWS_PER_PAGE = 'rowsPerPage';

		var SHOW_SIBLINGS = 'showSiblings';

		var STR_ACTIVE = 'active';

		var STR_BLANK = '';

		var STR_CLICK = 'click';

		var STR_DATA = 'data';

		var STR_DRAG_NODE = 'dragNode';

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOCUS = 'focus';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_START = 'folderStart';

		var STR_ICON = 'icon';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX = 'rowIdsDLFileShortcutCheckbox';

		var STR_ROW_IDS_FOLDER_CHECKBOX = 'rowIdsFolderCheckbox';

		var STR_ROW_IDS_FILE_ENTRY_CHECKBOX = 'rowIdsFileEntryCheckbox';

		var STRUTS_ACTION = 'struts_action';

		var SRC_DISPLAY_STYLE_BUTTONS = 0;

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_HISTORY = 2;

		var VIEW_ADD_BREADCRUMB = 'viewBreadcrumb';

		var VIEW_ADD_BUTTON = 'viewAddButton';

		var VIEW_DISPLAY_STYLE_BUTTONS = 'viewDisplayStyleButtons';

		var VIEW_ENRTIES = 'viewEntries';

		var VIEW_FILE_ENTRY_SEARCH = 'viewFileEntrySearch';

		var VIEW_SORT_BUTTON = 'viewSortButton';

		var DocumentLibrary = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._dataRetrieveFailure = instance.ns('dataRetrieveFailure');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');
						instance._eventPageLoaded = instance.ns('pageLoaded');

						instance._displayStyleToolbarNode = instance.byId(DISPLAY_STYLE_TOOLBAR);
						instance._entriesContainer = instance.byId('documentContainer');

						instance._selectAllCheckbox = instance.byId('allRowIdsCheckbox');

						instance._displayStyle = instance.ns('displayStyle');
						instance._folderId = instance.ns('folderId');

						var entryPage = 0;

						if (config.entriesTotal > 0) {
							entryPage = config.entryEnd / config.entryRowsPerPage;
						}

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: '.document-entries-paginator',
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: entryPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: config.entryRowsPerPage,
								rowsPerPageOptions: config.entryRowsPerPageOptions,
								total: config.entriesTotal
							}
						).render();

						entryPaginator.on('changeRequest', instance._onEntryPaginatorChangeRequest, instance);

						var folderPage = 0;

						if (config.foldersTotal > 0) {
							folderPage = config.folderEnd / config.folderRowsPerPage;
						}

						var folderPaginator = new A.Paginator(
							{
								alwaysVisible: false,
								circular: false,
								containers: '.folder-paginator',
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: folderPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: config.folderRowsPerPage,
								rowsPerPageOptions: config.folderRowsPerPageOptions,
								total: config.foldersTotal
							}
						).render();

						folderPaginator.on('changeRequest', instance._onFolderPaginatorChangeRequest, instance);

						Liferay.on(instance._eventDataRequest, instance._onDataRequest, instance);
						Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance);
						Liferay.on(instance._eventPageLoaded, instance._onPageLoaded, instance);

						Liferay.after(instance._eventDataRequest, instance._afterDataRequest, instance);

						var folderContainer = instance.byId(STR_FOLDER_CONTAINER);

						instance._listView = new Liferay.ListView(
							{
								boundingBox: formatSelectorNS(instance.NS, '#listViewContainer'),
								cssClass: 'folder-display-style lfr-list-view-content',
								itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
								contentBox: folderContainer,
								srcNode: folderContainer
							}
						).render();

						instance._listView.after('transitionComplete', instance._initDropTargets, instance);

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						documentLibraryContainer.delegate(
							STR_CLICK,
							A.bind(instance._onDocumentLibraryContainerClick, instance),
							formatSelectorNS(instance.NS, '#documentContainer a[data-folder=true], #breadcrumbContainer a')
						);

						History.after('stateChange', instance._afterStateChange, instance);

						documentLibraryContainer.plug(A.LoadingMask);

						instance._config = config;

						instance._displayViews = config.displayViews;

						instance._entryPaginator = entryPaginator;
						instance._folderPaginator = folderPaginator;

						instance._initHover();

						instance._initDragDrop();

						instance._initSelectAllCheckbox();

						instance._initToggleSelect();

						instance._restoreState();
					},

					_addHistoryState: function(data) {
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
					},

					_afterDataRequest: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var config = instance._config;

						var data = {};

						var displayStyle = instance._displayStyle;

						data[instance._folderId] = config.defaultParentFolderId;

						data[displayStyle] = History.get(displayStyle) || config.displayStyle;

						data[instance.ns('viewFolders')] = true;

						A.mix(data, requestParams, true);

						instance._documentLibraryContainer.loadingmask.show();

						if (event.src !== SRC_HISTORY) {
							instance._addHistoryState(data);
						}

						var ioRequest = instance._getIORequest();

						ioRequest.set(STR_DATA, data);

						ioRequest.start();
					},

					_afterStateChange: function(event) {
						var instance = this;

						var namespace = instance.NS;

						var requestParams = {};

						var state = History.get();

						AObject.each(
							state,
							function(item, index, collection) {
								if (index.indexOf(namespace) == 0) {
									requestParams[index] = item;
								}
							}
						);

						if (!AObject.isEmpty(requestParams)) {
							Liferay.fire(
								instance._eventDataRequest,
								{
									requestParams: requestParams,
									src: SRC_HISTORY
								}
							);
						}
					},

					_afterListViewItemChange: function(event) {
						var instance = this;

						var selFolder = A.one('.folder.selected');

						if (selFolder) {
							selFolder.removeClass(CSS_SELECTED);
						}

						var item = event.newVal;

						item.ancestor('.folder').addClass(CSS_SELECTED);

						var dataFileEntryTypeId = item.attr('data-file-entry-type-id');
						var dataFolderId = item.attr(DATA_FOLDER_ID);
						var dataNavigation = item.attr('data-navigation');
						var dataRefreshEntries = item.attr('data-refresh-entries');
						var dataRefreshFolders = item.attr(DATA_REFRESH_FOLDERS);
						var dataShowSiblings = item.attr('data-show-siblings');
						var dataShowRootFolder = item.attr('data-show-root-folder');

						var direction = 'left';

						if (item.attr('data-direction-right')) {
							direction = 'right';
						}

						instance._listView.set('direction', direction);

						var config = instance._config;

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = config.strutsAction;
						requestParams[instance.ns(STR_ENTRY_END)] = config.entryRowsPerPage || instance._entryPaginator.get('rowsPerPage');
						requestParams[instance.ns(STR_ENTRY_START)] = 0;
						requestParams[instance.ns(STR_FOLDER_END)] = config.folderRowsPerPage || instance._folderPaginator.get('rowsPerPage');
						requestParams[instance.ns(STR_FOLDER_START)] = 0;
						requestParams[instance.ns('refreshEntries')] = dataRefreshEntries;
						requestParams[instance.ns(VIEW_ADD_BUTTON)] = true;
						requestParams[instance.ns(VIEW_ADD_BREADCRUMB)] = true;
						requestParams[instance.ns(VIEW_DISPLAY_STYLE_BUTTONS)] = true;
						requestParams[instance.ns(VIEW_FILE_ENTRY_SEARCH)] = true;
						requestParams[instance.ns(VIEW_SORT_BUTTON)] = true;

						if (dataFolderId) {
							requestParams[instance._folderId] = dataFolderId;
						}

						if (dataNavigation) {
							requestParams[instance.ns('navigation')] = dataNavigation;
						}

						if (dataRefreshEntries) {
							requestParams[instance.ns(VIEW_ENRTIES)] = dataRefreshEntries;
						}

						if (dataShowSiblings) {
							requestParams[instance.ns(SHOW_SIBLINGS)] = dataShowSiblings;
						}

						if (dataShowRootFolder) {
							requestParams[instance.ns('showRootFolder')] = dataShowRootFolder;
						}

						if (dataFileEntryTypeId) {
							requestParams[instance.ns('fileEntryTypeId')] = dataFileEntryTypeId;
						}

						if (dataRefreshFolders) {
							requestParams[instance.ns(REFRESH_FOLDERS)] = dataRefreshFolders;
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_getDisplayStyle: function(style) {
						var instance = this;

						var displayStyle = History.get(instance._displayStyle) || instance._config.displayStyle;

						if (style) {
							displayStyle = (displayStyle == style);
						}

						return displayStyle;
					},

					_getIORequest: function() {
						var instance = this;

						var ioRequest = instance._ioRequest;

						if (!ioRequest) {
							var sendIOResponse = A.bind(instance._sendIOResponse, instance);

							ioRequest = A.io.request(
								instance._config.mainUrl,
								{
									after: {
										success: sendIOResponse,
										failure: sendIOResponse
									},
									autoLoad: false
								}
							);

							instance._ioRequest = ioRequest;
						}

						return ioRequest;
					},

					_getMoveText: function(selectedItemsCount, targetAvailable) {
						var moveText = STR_BLANK;

						if (targetAvailable) {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved-to-x');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved-to-x');
							}
						}
						else {
							moveText = Liferay.Language.get('x-item-is-ready-to-be-moved');

							if (selectedItemsCount > 1) {
								moveText = Liferay.Language.get('x-items-are-ready-to-be-moved');
							}
						}

						return moveText;
					},

					_getResultsStartEnd: function(paginator, page, rowsPerPage) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = (paginator.get('page') - 1) || 0;
						}

						if (!Lang.isValue(rowsPerPage)) {
							rowsPerPage = paginator.get(ROWS_PER_PAGE);
						}

						var start = page * rowsPerPage;
						var end = start + rowsPerPage;

						return [start, end];
					},

					_initDragDrop: function() {
						var instance = this;

						var ddHandler = new A.DD.Delegate(
							{
								container: instance._documentLibraryContainer,
								nodes: CSS_DOCUMENT_DISPLAY_STYLE_SELECTABLE,
								on: {
									'drag:drophit': A.bind(instance._onDragDropHit, instance),
									'drag:enter': A.bind(instance._onDragEnter, instance),
									'drag:exit': A.bind(instance._onDragExit, instance),
									'drag:start': A.bind(instance._onDragStart, instance)
								}
							}
						);

						var dd = ddHandler.dd;

						dd.set('offsetNode', false);

						dd.removeInvalid('a');

						dd.set('groups', DOCUMENT_LIBRARY_GROUP);

						dd.plug(
							[
								{
									cfg: {
										moveOnEnd: false
									},
									fn: A.Plugin.DDProxy
								},
								{
									cfg: {
										constrain2node: instance._documentLibraryContainer
									},
									fn: A.Plugin.DDConstrained
								}
							]
						);

						instance._initDropTargets();

						instance._ddHandler = ddHandler;
					},

					_initDropTargets: function() {
						var instance = this;

						var items = instance._documentLibraryContainer.all('[data-folder="true"]');

						items.each(
							function(item, index, collection) {
								item.plug(
									A.Plugin.Drop,
									{
										groups: DOCUMENT_LIBRARY_GROUP
									}
								);
							}
						);
					},

					_initHover: function() {
						var instance = this;

						instance._entriesContainer.on([STR_FOCUS, 'blur'], instance._toggleHovered, instance);
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._selectAllCheckbox.on(STR_CLICK, instance._onSelectAllCheckboxChange, instance);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._entriesContainer.delegate(
							'change',
							instance._onDocumentSelectorChange,
							'.document-selector',
							instance
						);
					},

					_onDataRetrieveSuccess: function(event) {
						var instance = this;

						var responseData = event.responseData;

						instance._documentLibraryContainer.loadingmask.hide();

						var content = A.Node.create(responseData);

						if (content) {
							instance._setBreadcrumb(content);
							instance._setButtons(content);
							instance._setEntries(content);
							instance._setFileEntrySearch(content);
							instance._setFolders(content);
							instance._setParentFolderTitle(content);
							instance._syncDisplayStyleToolbar(content);
							instance._setSearchResults(content);
						}
					},

					_onDataRequest: function(event) {
						var instance = this;

						var src = event.src;

						if (src === SRC_DISPLAY_STYLE_BUTTONS || src === SRC_ENTRIES_PAGINATOR) {
							var selectedEntries;

							var entriesSelector = CSS_DOCUMENT_DISPLAY_STYLE_SELECTED + ' :checkbox';

							if (instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
								entriesSelector = 'td > :checkbox:checked';
							}

							selectedEntries = instance._entriesContainer.all(entriesSelector);

							if (selectedEntries.size()) {
								instance._selectedEntries = selectedEntries.val();
							}
						}

						instance._processDefaultParams(event);

						instance._updatePaginatorValues(event);
					},

					_onDocumentLibraryContainerClick: function(event) {
						var instance = this;

						event.preventDefault();

						var config = instance._config;

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = config.strutsAction;
						requestParams[instance.ns('action')] = 'browseFolder';
						requestParams[instance.ns(STR_ENTRY_END)] = instance._entryPaginator.get(ROWS_PER_PAGE);
						requestParams[instance.ns(STR_FOLDER_END)] = instance._folderPaginator.get(ROWS_PER_PAGE);
						requestParams[instance._folderId] = event.currentTarget.attr(DATA_FOLDER_ID);
						requestParams[instance.ns(REFRESH_FOLDERS)] = event.currentTarget.attr(DATA_REFRESH_FOLDERS);
						requestParams[instance.ns(SHOW_SIBLINGS)] = true;
						requestParams[instance.ns(STR_ENTRY_START)] = 0;
						requestParams[instance.ns(STR_FOLDER_START)] = 0;
						requestParams[instance.ns(VIEW_ADD_BUTTON)] = true;
						requestParams[instance.ns(VIEW_ADD_BREADCRUMB)] = true;
						requestParams[instance.ns(VIEW_DISPLAY_STYLE_BUTTONS)] = true;
						requestParams[instance.ns(VIEW_ENRTIES)] = true;
						requestParams[instance.ns(VIEW_FILE_ENTRY_SEARCH)] = true;
						requestParams[instance.ns(VIEW_SORT_BUTTON)] = true;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onDocumentSelectorChange: function(event) {
						var instance = this;

						instance._toggleSelected(event.currentTarget, true);

						window[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						Liferay.Util.checkAllBox(
							instance._entriesContainer,
							[
								instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX),
								instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX),
								instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX)
							],
							instance._selectAllCheckbox
						);
					},

					_onDragDropHit: function(event) {
						var instance = this;

						var proxyNode = event.target.get(STR_DRAG_NODE);

						proxyNode.removeClass(CSS_ACTIVE_AREA_PROXY);

						proxyNode.empty();

						var dropTarget = event.drop.get('node');

						var folderId = dropTarget.attr(DATA_FOLDER_ID);

						var folderContainer = dropTarget.ancestor('.document-display-style');

						var selectedItems = instance._ddHandler.dd.get(STR_DATA).selectedItems;

						if (selectedItems.indexOf(folderContainer) == -1) {
							window[instance.ns('moveEntries')](folderId);
						}
					},

					_onDragEnter: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || dropTarget;

						if (!dragNode.compareTo(dropTarget)) {
							dropTarget.addClass(CSS_ACTIVE_AREA);

							var proxyNode = event.target.get(STR_DRAG_NODE);

							var dd = instance._ddHandler.dd;

							var selectedItemsCount = dd.get(STR_DATA).selectedItemsCount;

							var moveText = instance._getMoveText(selectedItemsCount, true);

							var itemTitle = Lang.trim(dropTarget.one('.entry-title').text());

							proxyNode.html(Lang.sub(moveText, [selectedItemsCount, itemTitle]));
						}
					},

					_onDragExit: function(event) {
						var instance = this;

						var dropTarget = event.drop.get('node');

						dropTarget = dropTarget.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || dropTarget;

						dropTarget.removeClass(CSS_ACTIVE_AREA);

						var proxyNode = event.target.get(STR_DRAG_NODE);

						var selectedItemsCount = instance._ddHandler.dd.get(STR_DATA).selectedItemsCount;

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
					},

					_onDragStart: function(event) {
						var instance = this;

						var target = event.target;

						var node = target.get('node');

						if (!node.hasClass(CSS_SELECTED)) {
							instance._unselectAllEntries();

							instance._toggleSelected(node);
						}

						var proxyNode = target.get(STR_DRAG_NODE);

						proxyNode.setStyles(
							{
								height: STR_BLANK,
								width: STR_BLANK
							}
						);

						var selectedItems = instance._entriesContainer.all(CSS_DOCUMENT_DISPLAY_STYLE_SELECTED);

						var selectedItemsCount = selectedItems.size();

						var moveText = instance._getMoveText(selectedItemsCount);

						proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

						proxyNode.addClass(CSS_ACTIVE_AREA_PROXY);

						var dd = instance._ddHandler.dd;

						dd.set(
							STR_DATA,
							{
								selectedItemsCount: selectedItemsCount,
								selectedItems: selectedItems
							}
						);
					},

					_onEntryPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._entryPaginator);

						var requestParams = instance._getIORequest().get(STR_DATA) || {};

						var customParams = {};

						customParams[instance.ns(STR_ENTRY_START)] = startEndParams[0];
						customParams[instance.ns(STR_ENTRY_END)] = startEndParams[1];
						customParams[instance.ns(REFRESH_FOLDERS)] = false;
						customParams[instance.ns(VIEW_ADD_BUTTON)] = true;
						customParams[instance.ns(VIEW_ENRTIES)] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_ENTRIES_PAGINATOR
							}
						);
					},

					_onFolderPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var requestParams = instance._getIORequest().get(STR_DATA) || {};

						var customParams = {};

						customParams[instance.ns(STR_FOLDER_START)] = startEndParams[0];
						customParams[instance.ns(STR_FOLDER_END)] = startEndParams[1];
						customParams[instance.ns(REFRESH_FOLDERS)] = true;
						customParams[instance.ns(VIEW_ENRTIES)] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onPageLoaded: function(event) {
						var instance = this;

						var paginatorData = event.paginator;

						if (paginatorData) {
							var paginator = instance['_' + paginatorData.name];

							if (A.instanceOf(paginator, A.Paginator)) {
								paginator.setState(paginatorData.state);
							}
						}
					},

					_onSelectAllCheckboxChange: function() {
						var instance = this;

						instance._toggleEntriesSelection();
					},

					_processDefaultParams: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						AObject.each(
							instance._config.defaultParams,
							function(item, index, collection) {
								if (!Lang.isValue(History.get(index))) {
									requestParams[index] = item;
								}
							}
						);
					},

					_restoreState: function() {
						var instance = this;

						if (!History.HTML5) {
							var initialState = History.get();

							if (!AObject.isEmpty(initialState)) {
								var namespace = instance.NS;

								var requestParams = {};

								AObject.each(
									initialState,
									function(item, index, collection) {
										if (index.indexOf(namespace) == 0) {
											requestParams[index] = item;
										}
									}
								);

								Liferay.fire(
									instance._eventDataRequest,
									{
										requestParams: requestParams
									}
								);
							}
						}
					},

					_setBreadcrumb: function(content) {
						var instance = this;

						var breadcrumb = instance.one('#breadcrumb', content);

						if (breadcrumb) {
							var breadcrumbContainer = instance.byId('breadcrumbContainer');

							breadcrumbContainer.setContent(breadcrumb);
						}
					},

					_setButtons: function(content) {
						var instance = this;

						var addButton = instance.one('#addButton', content);

						if (addButton) {
							var addButtonContainer = instance.byId('addButtonContainer');

							addButtonContainer.plug(A.Plugin.ParseContent);

							addButtonContainer.setContent(addButton);
						}

						var displayStyleButtons = instance.one('#displayStyleButtons', content);

						if (displayStyleButtons) {
							instance._displayStyleToolbarNode.empty();

							var displayStyleButtonsContainer = instance.byId('displayStyleButtonsContainer');

							displayStyleButtonsContainer.plug(A.Plugin.ParseContent);

							displayStyleButtonsContainer.setContent(displayStyleButtons);
						}

						var sortButton = instance.one('#sortButton', content);

						if (sortButton) {
							var sortButtonContainer = instance.byId('sortButtonContainer');

							sortButtonContainer.setContent(sortButton);
						}
					},

					_setEntries: function(content) {
						var instance = this;

						var entries = instance.one('#entries', content);

						if (entries) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.empty();

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.setContent(entries);

							instance._initDropTargets();

							instance._updateSelectedEntriesStatus();
						}
					},

					_setFileEntrySearch: function(content) {
						var instance = this;

						var fileEntrySearch = instance.one('#fileEntrySearch', content);

						if (fileEntrySearch) {
							var fileEntrySearchContainer = instance.byId('fileEntrySearchContainer');

							if (fileEntrySearchContainer) {
								fileEntrySearchContainer.plug(A.Plugin.ParseContent);

								fileEntrySearchContainer.setContent(fileEntrySearch);
							}
						}
					},

					_setFolders: function(content) {
						var instance = this;

						var folders = instance.one('#folderContainer', content);

						if (folders) {
							var listViewDataContainer = A.one('.lfr-list-view-data-container');

							listViewDataContainer.plug(A.Plugin.ParseContent);

							var refreshFolders = folders.attr(DATA_REFRESH_FOLDERS);

							if (refreshFolders) {
								instance._listView.set(STR_DATA, folders.html());
							}
						}
					},

					_setParentFolderTitle: function(content) {
						var instance = this;

						var parentFolderTitle = instance.one('#parentFolderTitle', content);

						if (parentFolderTitle) {
							var parentFolderTitleContainer = instance.byId('parentFolderTitleContainer');

							parentFolderTitleContainer.setContent(parentFolderTitle);
						}
					},

					_setSearchResults: function(content) {
						var instance = this;

						var searchResults = instance.one('#searchResults', content);

						if (searchResults) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.setContent(searchResults);
						}
					},

					_sendIOResponse: function(event) {
						var instance = this;

						var ioRequest = instance._getIORequest();

						var data = ioRequest.get(STR_DATA);
						var reponseData = ioRequest.get('responseData');

						var eventType = instance._eventDataRetrieveSuccess;

						if (event.type.indexOf('success') == -1) {
							eventType = instance._dataRetrieveFailure;
						}

						Liferay.fire(
							eventType,
							{
								data: data,
								responseData: reponseData
							}
						);
					},

					_syncDisplayStyleToolbar: function(content) {
						var instance = this;

						var displayViews = instance._displayViews;

						var length = displayViews.length;

						if (length > 1) {
							var displayStyleToolbar = instance._displayStyleToolbarNode.getData(DISPLAY_STYLE_TOOLBAR);

							var displayStyle = instance._getDisplayStyle();

							for (var i = 0; i < length; i++) {
								displayStyleToolbar.item(i).StateInteraction.set(STR_ACTIVE, displayStyle === displayViews[i]);
							}
						}
					},

					_toggleEntriesSelection: function() {
						var instance = this;

						var documentContainer = A.one('.document-container');

						var selectAllCheckbox = instance._selectAllCheckbox;

						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);
						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);
						Liferay.Util.checkAll(documentContainer, instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX), selectAllCheckbox, CSS_RESULT_ROW);

						window[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						if (!instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							var documentDisplayStyle = A.all(CSS_DOCUMENT_DISPLAY_STYLE_SELECTABLE);

							documentDisplayStyle.toggleClass(CSS_SELECTED, instance._selectAllCheckbox.attr(ATTR_CHECKED));
						}
					},

					_toggleHovered: function(event) {
						var instance = this;

						if (!instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							var documentDisplayStyle = event.target.ancestor(CSS_DOCUMENT_DISPLAY_STYLE);

							if (documentDisplayStyle) {
								documentDisplayStyle.toggleClass('hover', (event.type == STR_FOCUS));
							}
						}
					},

					_toggleSelected: function(node, preventUpdate) {
						var instance = this;

						if (instance._getDisplayStyle(DISPLAY_STYLE_LIST)) {
							node.attr(ATTR_CHECKED, !node.attr(ATTR_CHECKED));
						}
						else {
							node = node.ancestor(CSS_DOCUMENT_DISPLAY_STYLE) || node;

							if (!preventUpdate) {
								var selectElement = node.one('.document-selector :checkbox');

								selectElement.attr(ATTR_CHECKED, !selectElement.attr(ATTR_CHECKED));

								Liferay.Util.updateCheckboxValue(selectElement);
							}

							node.toggleClass(CSS_SELECTED);
						}
					},

					_unselectAllEntries: function() {
						var instance = this;

						instance._selectAllCheckbox.attr(CSS_SELECTED, false);

						instance._toggleEntriesSelection();
					},

					_updateSelectedEntriesStatus: function() {
						var instance = this;

						var selectedEntries = instance._selectedEntries;

						if (selectedEntries && selectedEntries.length) {
							var entriesContainer = instance._entriesContainer;

							A.each(
								selectedEntries,
								function(item, index, collection) {
									var entry = entriesContainer.one('input[value="' + item + '"]');

									if (entry) {
										instance._toggleSelected(entry);
									}
								}
							);

							selectedEntries.length = 0;
						}
					},

					_updatePaginatorValues: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var entryStartEndParams = instance._getResultsStartEnd(instance._entryPaginator);
						var folderStartEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var customParams = {};

						if (requestParams) {
							if (!owns(requestParams, instance.ns(STR_ENTRY_START)) && !owns(requestParams, instance.ns(STR_ENTRY_END))) {
								customParams[instance.ns(STR_ENTRY_START)] = entryStartEndParams[0];
								customParams[instance.ns(STR_ENTRY_END)] = entryStartEndParams[1];
							}

							if (!owns(requestParams, instance.ns(STR_FOLDER_START)) && !owns(requestParams, instance.ns(STR_FOLDER_END))) {
								customParams[instance.ns(STR_FOLDER_START)] = folderStartEndParams[0];
								customParams[instance.ns(STR_FOLDER_END)] = folderStartEndParams[1];
							}

							if (!AObject.isEmpty(customParams)) {
								A.mix(requestParams, customParams, true);
							}
						}
					}
				}
			}
		);

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: ['aui-paginator', 'dd-constrain', 'dd-delegate', 'dd-drag', 'dd-drop', 'dd-proxy', 'liferay-history-manager', 'liferay-list-view', 'liferay-portlet-base']
	}
);