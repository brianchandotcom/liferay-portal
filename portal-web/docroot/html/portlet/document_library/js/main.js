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

		var STR_END = 'end';

		var STR_ICON = 'icon';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_START = 'start';

		var STRUTS_ACTION = 'struts_action';

		var SRC_HISTORY = 0;

		var VIEW_ADD_BREADCRUMB = 'viewBreadcrumb';

		var VIEW_ADD_BUTTON = 'viewAddButton';

		var VIEW_DISPLAY_STYLE_BUTTONS = 'viewDisplayStyleButtons';

		var VIEW_ENRTIES = 'viewEntries';

		var VIEW_FILE_ENTRY_SEARCH = 'viewFileEntrySearch';

		var DocumentLibrary = A.Component.create(
			{
				NAME: 'documentlibrary',

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var namespace = '_' + config.portletId + '_';

						instance._dataRetrieveFailure = namespace + 'dataRetrieveFailure';
						instance._eventDataRequest = namespace + 'dataRequest';
						instance._eventDataRetrieveSuccess = namespace + 'dataRetrieveSuccess';

						instance._documentLibraryContainer = A.one('#' + namespace + 'documentLibraryContainer');
						instance._displayStyleToolbarNode = A.one('#' + namespace + DISPLAY_STYLE_TOOLBAR);
						instance._entriesContainer = A.one('#' + namespace + 'documentContainer');

						instance._prefxedDisplayStyle = namespace + 'displayStyle';
						instance._prefixedFolderId = namespace + 'folderId';

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: '.document-entries-paginator',
								firstPageLinkLabel: '<<',
								lastPageLinkLabel: '>>',
								nextPageLinkLabel: '>',
								prevPageLinkLabel: '<',
								rowsPerPage: config.rowsPerPage,
								rowsPerPageOptions: config.rowsPerPageOptions
							}
						).render();

						entryPaginator.on('changeRequest', instance._onEntryPaginatorChangeRequest, instance);

						Liferay.on(instance._eventDataRequest, instance._updatePaginatorValues, instance);

						Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance);

						Liferay.on(
							namespace + 'viewEntriesLoaded',
							function(event) {
								entryPaginator.setState(
									{
										page: event.page,
										rowsPerPage: event.rowsPerPage,
										total: event.total
									}
								);
							},
							instance
						);
						Liferay.after(instance._eventDataRequest, instance._afterDataRequest, instance);

						instance._listView = new Liferay.ListView(
							{
								itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
								srcNode: '#' + namespace + STR_FOLDER_CONTAINER
							}
						).render();

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						instance._documentLibraryContainer.delegate(
							'click',
							A.bind(instance._onDocumentLibraryContainerClick, instance),
							'a[data-folder=true], #' + namespace + 'breadcrumbContainer a'
						);

						History.after('stateChange', instance._afterStateChange, instance);

						instance._documentLibraryContainer.plug(A.LoadingMask);

						instance._config = config;
						instance._entryPaginator = entryPaginator;
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

						var namespace = instance._namespace;

						var config = instance._config;

						var data = {};

						var displayStyle = instance._prefxedDisplayStyle;

						data[instance._prefixedFolderId] = config.defaultParentFolderId;
						data[displayStyle] = History.get(displayStyle) || config.displayStyle;
						data[namespace + 'viewFolders'] = true;

						if (!AObject.isEmpty(requestParams)) {
							A.mix(data, requestParams, true);
						}

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

						var state = History.get();

						var requestParams = {};

						AObject.each(
							state,
							function(value, key, collection) {
								if (key.indexOf(instance._namespace) == 0) {
									requestParams[key] = value;
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
						requestParams[namespace + STR_END] = config.end;
						requestParams[namespace + STR_START] = config.start;
						requestParams[namespace + 'refreshEntries'] = dataRefreshEntries;
						requestParams[namespace + VIEW_ADD_BUTTON] = true;
						requestParams[namespace + VIEW_ADD_BREADCRUMB] = true;
						requestParams[namespace + VIEW_DISPLAY_STYLE_BUTTONS] = true;
						requestParams[namespace + VIEW_FILE_ENTRY_SEARCH] = true;

						if (dataFolderId) {
							requestParams[instance._prefixedFolderId] = dataFolderId;
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
							ioRequest = A.io.request(
								instance._config.mainUrl,
								{
									after: {
										success: A.bind(instance._sendIOResponse, instance),
										failure: A.bind(instance._sendIOResponse, instance)
									},
									autoLoad: false
								}
							);

							instance._ioRequest = ioRequest;
						}

						return ioRequest;
					},

					_getResultsStartEnd: function(page, rowsPerPage) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = instance._entryPaginator.get('page') - 1 || 0;
						}

						if (!Lang.isValue(rowsPerPage)) {
							rowsPerPage = instance._entryPaginator.get(ROWS_PER_PAGE);
						}

						var start = page * rowsPerPage;
						var end = start + rowsPerPage;

						return [start, end];
					},

					_onEntryPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd();

						var requestParams = instance._getIORequest().get(STR_DATA) || {};

						var namespace = instance._namespace;

						var customParams = {};

						customParams[namespace + STR_START] = startEndParams[0];
						customParams[namespace + STR_END] = startEndParams[1];
						customParams[namespace + REFRESH_FOLDERS] = false;
						customParams[namespace + VIEW_ENRTIES] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
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

					_onDocumentLibraryContainerClick: function(event) {
						var instance = this;

						event.preventDefault();

						var config = instance._config;
						var namespace = instance._namespace;

						var requestParams = {};

						requestParams[namespace + STRUTS_ACTION] = config.strutsAction;
						requestParams[namespace + 'action'] = 'browseFolder';
						requestParams[namespace + STR_END] = instance._entryPaginator.get(ROWS_PER_PAGE);
						requestParams[instance._prefixedFolderId] = event.currentTarget.attr(DATA_FOLDER_ID);
						requestParams[namespace + REFRESH_FOLDERS] = event.currentTarget.attr(DATA_REFRESH_FOLDERS);
						requestParams[namespace + SHOW_SIBLINGS] = true;
						requestParams[namespace + STR_START] = 0;
						requestParams[namespace + VIEW_ADD_BUTTON] = true;
						requestParams[namespace + VIEW_ADD_BREADCRUMB] = true;
						requestParams[namespace + VIEW_DISPLAY_STYLE_BUTTONS] = true;
						requestParams[namespace + VIEW_ENRTIES] = true;
						requestParams[namespace + VIEW_FILE_ENTRY_SEARCH] = true;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
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

							fileEntrySearchContainer.plug(A.Plugin.ParseContent);
							fileEntrySearchContainer.setContent(fileEntrySearch);
						}
					},

					_setFolders: function(content) {
						var instance = this;

						var folders = content.one('#' + instance._namespace + STR_FOLDER_CONTAINER);

						if (folders) {
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

						var displayStyleToolbar = instance._displayStyleToolbarNode.getData(DISPLAY_STYLE_TOOLBAR);

						var displayStyle = History.get(instance._prefxedDisplayStyle) || STR_ICON;

						displayStyleToolbar.item(0).StateInteraction.set(STR_ACTIVE, displayStyle === STR_ICON);
						displayStyleToolbar.item(1).StateInteraction.set(STR_ACTIVE, displayStyle === 'descriptive');
						displayStyleToolbar.item(2).StateInteraction.set(STR_ACTIVE, displayStyle === 'list');
					},

					_updatePaginatorValues: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var namespace = instance._namespace;

						if (requestParams && !owns(requestParams, namespace + STR_START) && !owns(requestParams, namespace + STR_END)) {
							var startEndParams = instance._getResultsStartEnd();

							var customParams =  {};

							customParams[namespace + STR_START] = startEndParams[0];
							customParams[namespace + STR_END] = startEndParams[1];

							A.mix(requestParams, customParams, true);
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