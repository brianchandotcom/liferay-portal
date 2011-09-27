AUI().add(
	'liferay-document-library',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;

		var owns = AObject.owns;

		var History = Liferay.HistoryManager;

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_REFRESH_FOLDERS = 'data-refresh-folders';

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var REFRESH_FOLDERS = 'refreshFolders';

		var ROWS_PER_PAGE = 'rowsPerPage';

		var SHOW_SIBLINGS = 'showSiblings';

		var STR_ACTIVE = 'active';

		var STR_DATA = 'data';

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_START = 'folderStart';

		var STRUTS_ACTION = 'struts_action';

		var SRC_HISTORY = 0;

		var VIEW_ADD_BREADCRUMB = 'viewBreadcrumb';

		var VIEW_ADD_BUTTON = 'viewAddButton';

		var VIEW_DISPLAY_STYLE_BUTTONS = 'viewDisplayStyleButtons';

		var VIEW_ENRTIES = 'viewEntries';

		var VIEW_FILE_ENTRY_SEARCH = 'viewFileEntrySearch';

		var VIEW_SORT_BUTTON = 'viewSortButton';

		var DocumentLibrary = A.Component.create(
			{
				NAME: 'documentlibrary',

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var namespace = config.namespace;

						var documentLibraryContainer = A.one('#' + namespace + 'documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._dataRetrieveFailure = namespace + 'dataRetrieveFailure';
						instance._eventDataRequest = namespace + 'dataRequest';
						instance._eventDataRetrieveSuccess = namespace + 'dataRetrieveSuccess';
						instance._eventPageLoaded = namespace + 'pageLoaded';

						instance._displayStyleToolbarNode = A.one('#' + namespace + DISPLAY_STYLE_TOOLBAR);
						instance._entriesContainer = A.one('#' + namespace + 'documentContainer');

						instance._displayStyle = namespace + 'displayStyle';
						instance._folderId = namespace + 'folderId';

						var entryPage = 0;

						if (config.entriesTotal > 0) {
							entryPage = config.entryEnd / config.entryRowsPerPage;
						}

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: '.document-entries-paginator',
								firstPageLinkLabel: '<<',
								lastPageLinkLabel: '>>',
								nextPageLinkLabel: '>',
								page: entryPage,
								prevPageLinkLabel: '<',
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
								firstPageLinkLabel: '<<',
								lastPageLinkLabel: '>>',
								nextPageLinkLabel: '>',
								page: folderPage,
								prevPageLinkLabel: '<',
								rowsPerPage: config.folderRowsPerPage,
								rowsPerPageOptions: config.folderRowsPerPageOptions,
								total: config.foldersTotal
							}
						).render();

						folderPaginator.on('changeRequest', instance._onFolderPaginatorChangeRequest, instance);

						Liferay.on(instance._eventDataRequest, instance._updatePaginatorValues, instance);
						Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance);
						Liferay.on(instance._eventPageLoaded, instance._onPageLoaded, instance);

						Liferay.after(instance._eventDataRequest, instance._afterDataRequest, instance);

						instance._listView = new Liferay.ListView(
							{
								boundingBox: '#' + namespace + 'listViewContainer',
								itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
								contentBox: '#' + namespace + STR_FOLDER_CONTAINER,
								srcNode: '#' + namespace + STR_FOLDER_CONTAINER
							}
						).render();

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						documentLibraryContainer.delegate(
							'click',
							A.bind(instance._onDocumentLibraryContainerClick, instance),
							'a[data-folder=true], #' + namespace + 'breadcrumbContainer a'
						);

						History.after('stateChange', instance._afterStateChange, instance);

						documentLibraryContainer.plug(A.LoadingMask);

						instance._config = config;

						instance._displayViews = config.displayViews;

						instance._entryPaginator = entryPaginator;
						instance._folderPaginator = folderPaginator;

						instance._namespace = namespace;

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
						var namespace = instance._namespace;

						var data = {};

						var displayStyle = instance._displayStyle;

						data[instance._folderId] = config.defaultParentFolderId;

						data[displayStyle] = History.get(displayStyle) || config.displayStyle;

						data[namespace + 'viewFolders'] = true;

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

						var namespace = instance._namespace;

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

						var namespace = instance._namespace;

						var requestParams = {};

						requestParams[namespace + STRUTS_ACTION] = config.strutsAction;
						requestParams[namespace + STR_ENTRY_END] = config.entryRowsPerPage || instance._entryPaginator.get('rowsPerPage');
						requestParams[namespace + STR_ENTRY_START] = 0;
						requestParams[namespace + STR_FOLDER_END] = config.folderRowsPerPage || instance._folderPaginator.get('rowsPerPage');
						requestParams[namespace + STR_FOLDER_START] = 0;
						requestParams[namespace + 'refreshEntries'] = dataRefreshEntries;
						requestParams[namespace + VIEW_ADD_BUTTON] = true;
						requestParams[namespace + VIEW_ADD_BREADCRUMB] = true;
						requestParams[namespace + VIEW_DISPLAY_STYLE_BUTTONS] = true;
						requestParams[namespace + VIEW_FILE_ENTRY_SEARCH] = true;
						requestParams[namespace + VIEW_SORT_BUTTON] = true;

						if (dataFolderId) {
							requestParams[instance._folderId] = dataFolderId;
						}

						if (dataNavigation) {
							requestParams[namespace + 'navigation'] = dataNavigation;
						}

						if (dataRefreshEntries) {
							requestParams[namespace + VIEW_ENRTIES] = dataRefreshEntries;
						}

						if (dataShowSiblings) {
							requestParams[namespace + SHOW_SIBLINGS] = dataShowSiblings;
						}

						if (dataShowRootFolder) {
							requestParams[namespace + 'showRootFolder'] = dataShowRootFolder;
						}

						if (dataFileEntryTypeId) {
							requestParams[namespace + 'fileEntryTypeId'] = dataFileEntryTypeId;
						}

						if (dataRefreshFolders) {
							requestParams[namespace + REFRESH_FOLDERS] = dataRefreshFolders;
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
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

					_onDocumentLibraryContainerClick: function(event) {
						var instance = this;

						event.preventDefault();

						var config = instance._config;
						var namespace = instance._namespace;

						var requestParams = {};

						requestParams[namespace + STRUTS_ACTION] = config.strutsAction;
						requestParams[namespace + 'action'] = 'browseFolder';
						requestParams[namespace + STR_ENTRY_END] = instance._entryPaginator.get(ROWS_PER_PAGE);
						requestParams[namespace + STR_FOLDER_END] = instance._folderPaginator.get(ROWS_PER_PAGE);
						requestParams[instance._folderId] = event.currentTarget.attr(DATA_FOLDER_ID);
						requestParams[namespace + REFRESH_FOLDERS] = event.currentTarget.attr(DATA_REFRESH_FOLDERS);
						requestParams[namespace + SHOW_SIBLINGS] = true;
						requestParams[namespace + STR_ENTRY_START] = 0;
						requestParams[namespace + STR_FOLDER_START] = 0;
						requestParams[namespace + VIEW_ADD_BUTTON] = true;
						requestParams[namespace + VIEW_ADD_BREADCRUMB] = true;
						requestParams[namespace + VIEW_DISPLAY_STYLE_BUTTONS] = true;
						requestParams[namespace + VIEW_ENRTIES] = true;
						requestParams[namespace + VIEW_FILE_ENTRY_SEARCH] = true;
						requestParams[namespace + VIEW_SORT_BUTTON] = true;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onEntryPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._entryPaginator);

						var requestParams = instance._getIORequest().get(STR_DATA) || {};

						var namespace = instance._namespace;

						var customParams = {};

						customParams[namespace + STR_ENTRY_START] = startEndParams[0];
						customParams[namespace + STR_ENTRY_END] = startEndParams[1];
						customParams[namespace + REFRESH_FOLDERS] = false;
						customParams[namespace + VIEW_ADD_BUTTON] = true;
						customParams[namespace + VIEW_ENRTIES] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_onFolderPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var requestParams = instance._getIORequest().get(STR_DATA) || {};

						var namespace = instance._namespace;

						var customParams = {};

						customParams[namespace + STR_FOLDER_START] = startEndParams[0];
						customParams[namespace + STR_FOLDER_END] = startEndParams[1];
						customParams[namespace + REFRESH_FOLDERS] = true;
						customParams[namespace + VIEW_ENRTIES] = true;

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

					_restoreState: function() {
						var instance = this;

						if (!History.HTML5) {
							var initialState = History.get();

							if (!AObject.isEmpty(initialState)) {
								var namespace = instance._namespace;

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

						var namespace = instance._namespace;

						var breadcrumb = content.one('#' + namespace + 'breadcrumb');

						if (breadcrumb) {
							var breadcrumbContainer = A.one('#' + namespace + 'breadcrumbContainer');

							breadcrumbContainer.setContent(breadcrumb);
						}
					},

					_setButtons: function(content) {
						var instance = this;

						var namespace = instance._namespace;

						var addButton = content.one('#' + namespace + 'addButton');

						if (addButton) {
							var addButtonContainer = A.one('#' + namespace + 'addButtonContainer');

							addButtonContainer.plug(A.Plugin.ParseContent);

							addButtonContainer.setContent(addButton);
						}

						var displayStyleButtons = content.one('#' + namespace + 'displayStyleButtons');

						if (displayStyleButtons) {
							instance._displayStyleToolbarNode.empty();

							var displayStyleButtonsContainer = A.one('#' + namespace + 'displayStyleButtonsContainer');

							displayStyleButtonsContainer.plug(A.Plugin.ParseContent);

							displayStyleButtonsContainer.setContent(displayStyleButtons);
						}

						var sortButton = content.one('#' + namespace + 'sortButton');

						if (sortButton) {
							var sortButtonContainer = A.one('#' + namespace + 'sortButtonContainer');

							sortButtonContainer.setContent(sortButton);
						}
					},

					_setEntries: function(content) {
						var instance = this;

						var entries = content.one('#' + instance._namespace + 'entries');

						if (entries) {
							instance._entriesContainer.plug(A.Plugin.ParseContent);

							instance._entriesContainer.setContent(entries);
						}
					},

					_setFileEntrySearch: function(content) {
						var instance = this;

						var namespace = instance._namespace;

						var fileEntrySearch = content.one('#' + namespace + 'fileEntrySearch');

						if (fileEntrySearch) {
							var fileEntrySearchContainer = A.one('#' + namespace + 'fileEntrySearchContainer');

							if (fileEntrySearchContainer) {
								fileEntrySearchContainer.plug(A.Plugin.ParseContent);

								fileEntrySearchContainer.setContent(fileEntrySearch);
							}
						}
					},

					_setFolders: function(content) {
						var instance = this;

						var folders = content.one('#' + instance._namespace + STR_FOLDER_CONTAINER);

						if (folders) {
							var listViewDataContainer = A.one('.lfr-list-view-data-container');

							listViewDataContainer.plug(A.Plugin.ParseContent);

							var refreshFolders = folders.attr(DATA_REFRESH_FOLDERS);

							if (refreshFolders) {
								instance._listView.set(STR_DATA, folders);
							}
						}
					},

					_setParentFolderTitle: function(content) {
						var instance = this;

						var namespace = instance._namespace;

						var parentFolderTitle = content.one('#' + namespace + 'parentFolderTitle');

						if (parentFolderTitle) {
							var parentFolderTitleContainer = A.one('#' + namespace + 'parentFolderTitleContainer');

							parentFolderTitleContainer.setContent(parentFolderTitle);
						}
					},

					_setSearchResults: function(content) {
						var instance = this;

						var searchResults = content.one('#' + instance._namespace + 'searchResults');

						if (searchResults) {
							var entriesContainer = instance._entriesContainer;

							instance._entriesContainer.plug(A.Plugin.ParseContent);

							instance._entriesContainer.setContent(searchResults);
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

							var displayStyle = History.get(instance._displayStyle) || config.displayStyle;

							for (var i = 0; i < length; i++) {
								displayStyleToolbar.item(i).StateInteraction.set(STR_ACTIVE, displayStyle === displayViews[i]);
							}
						}
					},

					_updatePaginatorValues: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var namespace = instance._namespace;

						var entryStartEndParams = instance._getResultsStartEnd(instance._entryPaginator);
						var folderStartEndParams = instance._getResultsStartEnd(instance._folderPaginator);

						var customParams = {};

						if (requestParams) {
							if (!owns(requestParams, namespace + STR_ENTRY_START) && !owns(requestParams, namespace + STR_ENTRY_END)) {
								customParams[namespace + STR_ENTRY_START] = entryStartEndParams[0];
								customParams[namespace + STR_ENTRY_END] = entryStartEndParams[1];
							}

							if (!owns(requestParams, namespace + STR_FOLDER_START) && !owns(requestParams, namespace + STR_FOLDER_END)) {
								customParams[namespace + STR_FOLDER_START] = folderStartEndParams[0];
								customParams[namespace + STR_FOLDER_END] = folderStartEndParams[1];
							}

							if (customParams.length > 0) {
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
		requires: ['aui-paginator', 'liferay-list-view', 'liferay-history-manager']
	}
);