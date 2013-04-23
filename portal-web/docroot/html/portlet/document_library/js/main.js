AUI.add(
	'liferay-document-library',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var History = Liferay.HistoryManager;

		var IE = A.UA.ie;

		var CSS_SYNC_MESSAGE_HIDDEN = 'sync-message-hidden';

		var DEFAULT_FOLDER_ID = 0;

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var MESSAGE_TYPE_ERROR = 'error';

		var SEARCH_REPOSITORY_ID = 'searchRepositoryId';

		var SEARCH_TYPE = 'searchType';

		var SEARCH_TYPE_SINGLE = 1;

		var STR_CLICK = 'click';

		var STR_DATA_SEARCH_PROCESSED = 'data-searchProcessed';

		var STR_FOLDER_ID = 'folderId';

		var STR_KEYWORDS = 'keywords';

		var STR_PAGINATOR_DATA = 'paginatorData';

		var STR_PARENT_NODE = 'parentNode';

		var STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX = 'rowIdsDLFileShortcutCheckbox';

		var STR_ROW_IDS_FOLDER_CHECKBOX = 'rowIdsFolderCheckbox';

		var STR_ROW_IDS_FILE_ENTRY_CHECKBOX = 'rowIdsFileEntryCheckbox';

		var STR_SEARCH_FOLDER_ID = 'searchFolderId';

		var STR_SEARCH_RESULTS_CONTAINER = 'searchResultsContainer';

		var STR_SELECTED_FOLDER = 'selectedFolder';

		var STR_SHOW_REPOSITORY_TABS = 'showRepositoryTabs';

		var STR_SHOW_SEARCH_INFO = 'showSearchInfo';

		var STRUTS_ACTION = 'struts_action';

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_HISTORY = 2;

		var SRC_SEARCH = 3;

		var TPL_MESSAGE_SEARCHING = '<div class="portlet-msg-info">{0}</div><div class="loading-animation" />';

		var WIN = A.config.win;

		var DocumentLibrary = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase, Liferay.DocumentLibraryUpload],

				EXTENDS: A.Base,

				NAME: 'documentlibrary',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var documentLibraryContainer = instance.byId('documentLibraryContainer');

						instance._documentLibraryContainer = documentLibraryContainer;

						instance._eventDataProcessed = instance.ns('dataProcessed');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');
						instance._eventChangeSearchFolder = instance.ns('changeSearchFolder');

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventPageLoaded = instance.ns('pageLoaded');

						instance._keywordsNode = instance.byId(STR_KEYWORDS);

						if (!config.syncMessageDisabled) {
							instance._syncMessage = new Liferay.Message(
								{
									boundingBox: instance.byId('syncNotification'),
									contentBox: instance.byId('syncNotificationContent'),
									id: instance.NS + 'show-sync-message',
									trigger: instance.one('#showSyncMessageIconContainer'),
									visible: !config.syncMessageSuppressed
								}
							).render();
						}

						var checkBoxesId = [
							instance.ns(STR_ROW_IDS_FILE_SHORTCUT_CHECKBOX),
							instance.ns(STR_ROW_IDS_FOLDER_CHECKBOX),
							instance.ns(STR_ROW_IDS_FILE_ENTRY_CHECKBOX)
						];

						var displayStyle = config.displayStyle;

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('documentLibraryContainer');

						var paginatorConfig = config.paginator;

						paginatorConfig.entryPaginatorContainer = '.document-entries-paginator';
						paginatorConfig.folderPaginatorContainer = '.folder-paginator';
						paginatorConfig.namespace = namespace;

						var appViewPaginator = new Liferay.AppViewPaginator(paginatorConfig);

						instance._appViewPaginator = appViewPaginator;

						var selectConfig = config.select;

						selectConfig.checkBoxesId = checkBoxesId;
						selectConfig.displayStyle = displayStyle;
						selectConfig.displayStyleCSSClass = displayStyleCSSClass;
						selectConfig.displayStyleToolbar = displayStyleToolbar;
						selectConfig.folderContainer = instance.byId('folderContainer');
						selectConfig.namespace = namespace;
						selectConfig.portletContainerId = portletContainerId;
						selectConfig.repositories = config.repositories;
						selectConfig.selector = 'entry-selector';

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = config.move;

						moveConfig.processEntryIds = {
							checkBoxesIds: checkBoxesId,
							entryIds: [
								instance.ns('fileShortcutIds'),
								instance.ns('folderIds'),
								instance.ns('fileEntryIds')
							]
						};

						moveConfig.displayStyleCSSClass = displayStyleCSSClass;
						moveConfig.draggableCSSClass = '.entry-link';
						moveConfig.namespace = namespace;
						moveConfig.portletContainerId = portletContainerId;
						moveConfig.portletGroup = 'document-library';

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);

						var foldersConfig = config.folders;

						foldersConfig.displayStyle = displayStyle;
						foldersConfig.displayStyleCSSClass = displayStyleCSSClass;
						foldersConfig.displayStyleToolbar = displayStyleToolbar;
						foldersConfig.namespace = namespace;
						foldersConfig.portletContainerId = portletContainerId;

						instance._appViewFolders = new Liferay.AppViewFolders(foldersConfig);

						var eventHandles = [
							Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance),
							Liferay.on(instance._eventPageLoaded, instance._onPageLoaded, instance),
							History.after('stateChange', instance._afterStateChange, instance),
							Liferay.on('showTab', instance._onShowTab, instance),
							Liferay.on(instance._eventChangeSearchFolder, instance._onChangeSearchFolder, instance)
						];

						instance._config = config;

						instance._eventHandles = eventHandles;

						instance._repositoriesData = {};

						eventHandles.push(Liferay.on(config.portletId + ':portletRefreshed', A.bind('destructor', instance)));

						var searchFormNode = instance.one('#fm1');

						if (searchFormNode) {
							searchFormNode.on('submit', instance._onSearchFormSubmit, instance);
						}

						instance._toggleSyncNotification();

						instance._toggleTrashAction();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._appViewFolders.destroy();
						instance._appViewMove.destroy();
						instance._appViewPaginator.destroy();
						instance._appViewSelect.destroy();

						instance._documentLibraryContainer.purge(true);
					},

					_afterStateChange: function(event) {
						var instance = this;

						var namespace = instance.NS;

						var requestParams = {};

						var state = History.get();

						AObject.each(
							state,
							function(item, index, collection) {
								if (index.indexOf(namespace) === 0) {
									requestParams[index] = item;
								}
							}
						);

						instance._tuneStateChangeParams(requestParams);

						if (AObject.isEmpty(requestParams)) {
							requestParams = instance._getDefaultHistoryState();
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_HISTORY
							}
						);
					},

					_getDefaultHistoryState: function() {
						var instance = this;

						var initialState = History.get();

						if (AObject.isEmpty(initialState)) {
							initialState = instance._appViewPaginator.get('defaultParams');
						}

						return initialState;
					},

					_getRepositoryName: function(repositoryId) {
						var instance = this;

						var repositoryName = null;

						var repositories = instance._config.repositories;

						var length = repositories.length;

						for (var i = 0; i < length; i++) {
							var repository = repositories[i];

							if (repository.id == repositoryId) {
								repositoryName = repository.name;

								break;
							}
						}

						return repositoryName;
					},

					_onChangeSearchFolder: function(event) {
						var instance = this;

						var selectedFolder = instance._appViewSelect.get(STR_SELECTED_FOLDER);

						var searchData = {
							folderId: selectedFolder.id,
							keywords: instance._keywordsNode.get('value'),
							repositoryId: selectedFolder.repositoryId,
							showSearchInfo: true
						};

						if (event.searchEverywhere) {
							searchData[SEARCH_REPOSITORY_ID] = instance._config.repositories[0].id;
							searchData[STR_SEARCH_FOLDER_ID] = DEFAULT_FOLDER_ID;
							searchData[STR_SHOW_REPOSITORY_TABS] = true;
						}
						else {
							searchData[SEARCH_REPOSITORY_ID] = selectedFolder.repositoryId;
							searchData[STR_SEARCH_FOLDER_ID] = selectedFolder.id;
							searchData[STR_SHOW_REPOSITORY_TABS] = false;
						}

						instance._searchFileEntry(searchData);
					},

					_onDataRetrieveSuccess: function(event) {
						var instance = this;

						var responseData = event.responseData;

						instance._documentLibraryContainer.loadingmask.hide();

						var content = A.Node.create(responseData);

						if (content) {
							instance._setSearchResults(content);

							instance._appViewFolders.processData(content);

							instance._appViewSelect.syncDisplayStyleToolbar();
						}

						Liferay.fire(instance._eventDataProcessed);

						WIN[instance.ns('toggleActionsButton')]();

						if (event.data[instance.ns('viewEntries')]) {
							instance._toggleTrashAction();
						}
					},

					_onPageLoaded: function(event) {
						var instance = this;

						var paginatorData = event.paginator;

						if (paginatorData) {
							if (event.src == SRC_SEARCH) {
								var repositoriesData = instance._repositoriesData;

								var repositoryData = repositoriesData[event.repositoryId];

								if (!repositoryData) {
									repositoryData = {};

									instance._repositoriesData[event.repositoryId] = repositoryData;
								}

								repositoryData.paginatorData = paginatorData;
							}

							instance._appViewPaginator.set(STR_PAGINATOR_DATA, paginatorData);

							instance._toggleSyncNotification();
						}
					},

					_onSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._appViewSelect.get(STR_SELECTED_FOLDER);

						var showTabs = (selectedFolder.id == DEFAULT_FOLDER_ID);

						var searchData = {
							folderId: selectedFolder.id,
							keywords: instance._keywordsNode.get('value'),
							repositoryId: selectedFolder.repositoryId,
							searchFolderId: selectedFolder.id,
							searchRepositoryId: selectedFolder.repositoryId,
							showRepositoryTabs: showTabs,
							showSearchInfo: true
						};

						instance._searchFileEntry(searchData);
					},

					_onShowTab: function(event) {
						var instance = this;

						if (event.namespace.indexOf(instance.get('namespace')) === 0) {
							var tabSection = event.tabSection;

							var searchResultsWrapper = tabSection.one('[data-repositoryId]');

							var repositoryId = searchResultsWrapper.attr('data-repositoryId');

							var repositoryData = instance._repositoriesData[repositoryId];

							if (repositoryData) {
								var paginatorData = repositoryData.paginatorData;

								if (paginatorData) {
									instance._appViewPaginator.set(STR_PAGINATOR_DATA, paginatorData);
								}
							}

							if (!searchResultsWrapper.hasAttribute(STR_DATA_SEARCH_PROCESSED)) {
								searchResultsWrapper.setAttribute(STR_DATA_SEARCH_PROCESSED, true);

								var selectedFolder = instance._appViewSelect.get(STR_SELECTED_FOLDER);

								var searchData = {
									folderId: selectedFolder.id,
									keywords: instance._keywordsNode.get('value'),
									repositoryId: selectedFolder.repositoryId,
									searchFolderId: DEFAULT_FOLDER_ID,
									searchRepositoryId: repositoryId
								};

								instance._searchFileEntry(searchData);
							}
							else {
								instance._documentLibraryContainer.all('.document-entries-paginator').show();
							}
						}
					},

					_searchFileEntry: function(searchData) {
						var instance = this;

						instance._documentLibraryContainer.all('.document-entries-paginator').hide();

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = '/document_library/search';
						requestParams[instance.ns('repositoryId')] = searchData.repositoryId;
						requestParams[instance.ns(SEARCH_REPOSITORY_ID)] = searchData.searchRepositoryId;
						requestParams[instance.ns(STR_FOLDER_ID)] = searchData.folderId;
						requestParams[instance.ns(STR_SEARCH_FOLDER_ID)] = searchData.searchFolderId;
						requestParams[instance.ns(STR_KEYWORDS)] = searchData.keywords;
						requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						requestParams[instance.ns(STR_SHOW_REPOSITORY_TABS)] = searchData.showRepositoryTabs;
						requestParams[instance.ns(STR_SHOW_SEARCH_INFO)] = searchData.showSearchInfo;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: Liferay.DL_SEARCH
							}
						);

						if (searchData.showRepositoryTabs || searchData.showSearchInfo) {
							var entriesContainer = instance._entriesContainer;

							var searchingTPL = Lang.sub(TPL_MESSAGE_SEARCHING, [Liferay.Language.get('searching,-please-wait')]);

							entriesContainer.html(searchingTPL);
						}
					},

					_setSearchResults: function(content) {
						var instance = this;

						var repositoryId;

						var repositoryIdNode = instance.one('#' + instance.ns(SEARCH_REPOSITORY_ID), content);

						if (repositoryIdNode) {
							repositoryId = repositoryIdNode.val();
						}

						var searchInfo = instance.one('#' + instance.ns('searchInfo'), content);

						var entriesContainer = instance._entriesContainer;

						if (searchInfo) {
							entriesContainer.empty();

							entriesContainer.setContent(searchInfo);
						}

						var fragmentSearchResults = instance.one('#fragmentSearchResults', content);

						var searchResults;

						if (fragmentSearchResults) {
							searchResults = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER + repositoryId, entriesContainer);

							if (searchResults) {
								searchResults.empty();

								searchResults.setContent(fragmentSearchResults.html());
							}
						}

						searchResults = instance.one('.local-search-results', content);

						if (searchResults) {
							var searchResultsContainer = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER, content);

							if (!searchInfo) {
								entriesContainer.empty();
							}

							entriesContainer.append(searchResultsContainer);
						}

						var repositorySearchResults = instance.one('.repository-search-results', content);

						if (repositorySearchResults) {
							var resultsContainer = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER + repositoryId, entriesContainer);

							if (!resultsContainer) {
								resultsContainer = entriesContainer;
							}

							if (!searchInfo) {
								resultsContainer.empty();
							}

							resultsContainer.append(repositorySearchResults);
						}

						if (searchResults || repositorySearchResults) {
							instance.all('#addButtonContainer, #sortButtonContainer').hide();
						}

						var repositoryName = instance._getRepositoryName(repositoryId);

						if (repositoryName) {
							var tabLinkSelector = 'li[id$="' + Liferay.Util.toCharCode(repositoryName) + 'TabsId' + '"] a';

							var tabLink = entriesContainer.one(tabLinkSelector);

							if (tabLink) {
								tabLink.simulate(STR_CLICK);
							}
						}
					},

					_tuneStateChangeParams: function(requestParams) {
						var instance = this;

						var entriesContainer = instance._entriesContainer;

						var namespacedShowRepositoryTabs = instance.ns(STR_SHOW_REPOSITORY_TABS);

						if (AObject.owns(requestParams, namespacedShowRepositoryTabs) &&
							!requestParams[namespacedShowRepositoryTabs] &&
							!entriesContainer.one('ul.aui-tabview-list')) {

							requestParams[namespacedShowRepositoryTabs] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}

						var namespacedShowSearchInfo = instance.ns(STR_SHOW_SEARCH_INFO);

						if (AObject.owns(requestParams, namespacedShowSearchInfo) &&
							!requestParams[namespacedShowSearchInfo] &&
							!entriesContainer.one('.search-info')) {

							requestParams[namespacedShowSearchInfo] = true;

							requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						}
					},

					_toggleSyncNotification: function() {
						var instance = this;

						if (instance._syncMessage) {
							var entryPaginator = instance._appViewPaginator.get('entryPaginator');

							var entriesPaginatorState = entryPaginator.get('state');

							var syncMessageBoundingBox = instance._syncMessage.get('boundingBox');

							syncMessageBoundingBox.toggleClass(CSS_SYNC_MESSAGE_HIDDEN, entriesPaginatorState.total <= 0);
						}
					},

					_toggleTrashAction: function() {
						var instance = this;

						var isTrashEnabled = instance._config.isTrashEnabled;

						var repositoryId = instance._appViewSelect.get(STR_SELECTED_FOLDER).repositoryId;

						var scopeGroupId = themeDisplay.getScopeGroupId();

						var deleteAction = A.one('#' + instance.ns('deleteAction'));

						var moveToTrashAction = A.one('#' + instance.ns('moveToTrashAction'));

						if ((scopeGroupId == repositoryId) && isTrashEnabled) {
							deleteAction.get(STR_PARENT_NODE).hide();

							moveToTrashAction.get(STR_PARENT_NODE).show();
						}
						else {
							deleteAction.get(STR_PARENT_NODE).show();

							moveToTrashAction.get(STR_PARENT_NODE).hide();
						}
					}
				}
			}
		);

		Liferay.DL_ENTRIES_PAGINATOR = SRC_ENTRIES_PAGINATOR;

		Liferay.DL_SEARCH = SRC_SEARCH;

		Liferay.Portlet.DocumentLibrary = DocumentLibrary;
	},
	'',
	{
		requires: ['aui-loading-mask', 'aui-parse-content', 'document-library-upload', 'event-simulate', 'liferay-app-view-folders', 'liferay-app-view-move', 'liferay-app-view-paginator', 'liferay-app-view-select', 'liferay-history-manager', 'liferay-message', 'liferay-portlet-base', 'querystring-parse-simple']
	}
);