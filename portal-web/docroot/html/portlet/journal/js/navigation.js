AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var History = Liferay.HistoryManager;

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var SEARCH_TYPE = 'searchType';

		var SEARCH_TYPE_SINGLE = 1;

		var STR_ADVANCED_SEARCH = 'advancedSearch';

		var STR_AND_OPERATOR = 'andOperator';

		var STR_CLICK = 'click';

		var STR_CONTENT = 'content';

		var STR_DESCRIPTION = 'description';

		var STR_FOLDER_ID = 'folderId';

		var STR_KEYWORDS = 'keywords';

		var STR_PAGINATOR_DATA = 'paginatorData';

		var STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX = 'rowIdsJournalFolderCheckbox';

		var STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX = 'rowIdsJournalArticleCheckbox';

		var STR_SEARCH_ARTICLE_ID = 'searchArticleId';

		var STR_SEARCH_FOLDER_ID = 'searchFolderId';

		var STR_SEARCH_RESULTS_CONTAINER = 'searchResultsContainer';

		var STR_SELECTED_FOLDER = 'selectedFolder';

		var STR_SHOW_SEARCH_INFO = 'showSearchInfo';

		var STR_STATUS = 'status';

		var STR_TITLE = 'title';

		var STR_TYPE = 'type';

		var STRUTS_ACTION = 'struts_action';

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_HISTORY = 2;

		var SRC_SEARCH = 3;

		var SRC_SEARCH_END = 4;

		var TPL_MESSAGE_SEARCHING = '<div class="portlet-msg-info">{0}</div><div class="loading-animation" />';

		var JournalNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var journalContainer = instance.byId('journalContainer');

						instance._journalContainer = journalContainer;

						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');
						instance._eventOpenAdvancedSearch = instance.ns('openAdvancedSearch');
						instance._eventChangeSearchFolder = instance.ns('changeSearchFolder');

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventPageLoaded = instance.ns('pageLoaded');

						instance._andOperatorNode = instance.byId(STR_AND_OPERATOR);
						instance._contentNode = instance.byId(STR_CONTENT);
						instance._descriptionNode = instance.byId(STR_DESCRIPTION);
						instance._keywordsNode = instance.byId(STR_KEYWORDS);
						instance._searchArticleIdNode = instance.byId(STR_SEARCH_ARTICLE_ID);
						instance._statusNode = instance.byId(STR_STATUS);
						instance._titleNode = instance.byId(STR_TITLE);
						instance._typeNode = instance.byId(STR_TYPE);

						var checkBoxesId = [
							instance.ns(STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX),
							instance.ns(STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX)
						];

						var displayStyle = config.displayStyle;

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('journalContainer');

						var paginatorConfig = config.paginator;

						paginatorConfig.entryPaginatorContainer = '.article-entries-paginator';
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
						selectConfig.selector = 'entry-selector';

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = config.move;

						moveConfig.processEntryIds = {
							checkBoxesIds: checkBoxesId,
							entryIds: [
								instance.ns('articleIds'),
								instance.ns('folderIds')
							]
						};

						moveConfig.displayStyleCSSClass = displayStyleCSSClass;
						moveConfig.draggableCSSClass = '.entry-link';
						moveConfig.namespace = namespace;
						moveConfig.portletContainerId = portletContainerId;
						moveConfig.portletGroup = 'journal';

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);

						var foldersConfig = config.folders;

						foldersConfig.displayStyle = displayStyle;
						foldersConfig.displayStyleCSSClass = displayStyleCSSClass;
						foldersConfig.displayStyleToolbar = displayStyleToolbar;
						foldersConfig.entry = {
							paramName: 'structureId',
							typeId: 'data-structure-id'
						};
						foldersConfig.namespace = namespace;
						foldersConfig.portletContainerId = portletContainerId;

						instance._appViewFolders = new Liferay.AppViewFolders(foldersConfig);

						var eventHandles = [
							Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance),
							Liferay.on(instance._eventPageLoaded, instance._onPageLoaded, instance),
							History.after('stateChange', instance._afterStateChange, instance),
							Liferay.on(instance._eventChangeSearchFolder, instance._onChangeSearchFolder, instance)
						];

						journalContainer.delegate(
							STR_CLICK,
							A.bind(instance._onOpenAdvancedSearch, instance),
							'.article-advanced-search-icon'
						);

						instance._config = config;

						instance._eventHandles = eventHandles;

						eventHandles.push(Liferay.on(config.portletId + ':portletRefreshed', A.bind(instance.destructor, instance)));

						var searchFormNode = instance.one('#fm1');

						if (searchFormNode) {
							searchFormNode.on('submit', instance._onSearchFormSubmit, instance);
						}

						var advancedSearchFormNode = instance.one('#fmAdvancedSearch');

						if (advancedSearchFormNode) {
							advancedSearchFormNode.on('submit', instance._onAdvancedSearchFormSubmit, instance);
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._appViewFolders.destroy();
						instance._appViewMove.destroy();
						instance._appViewPaginator.destroy();
						instance._appViewSelect.destroy();

						instance._journalContainer.purge(true);
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

					_onAdvancedSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._appViewSelect._getSelectedFolder();

						var searchFolderId = selectedFolder.id;

						if (searchFolderId === 0) {
							searchFolderId = -1;
						}

						var searchData = {
							advancedSearch: true,
							andOperator: instance._andOperatorNode.get('value'),
							folderId: selectedFolder.id,
							content: instance._contentNode.get('value'),
							description: instance._descriptionNode.get('value'),
							keywords: '',
							searchArticleId: instance._searchArticleIdNode.get('value'),
							searchFolderId: searchFolderId,
							showSearchInfo: true,
							status: instance._statusNode.get('value'),
							title: instance._titleNode.get('value'),
							type: instance._typeNode.get('value')
						};

						instance._searchArticle(searchData);
					},

					_onChangeSearchFolder: function(event) {
						var instance = this;

						var selectedFolder = instance._appViewSelect.get(STR_SELECTED_FOLDER);

						var showAdvancedSearch = instance.byId('showAdvancedSearch');

						var searchData = {
							advancedSearch: showAdvancedSearch.hasClass('close-advanced-search'),
							andOperator: instance._andOperatorNode.get('value'),
							folderId: selectedFolder.id,
							content: instance._contentNode.get('value'),
							description: instance._descriptionNode.get('value'),
							keywords: instance._keywordsNode.get('value'),
							searchArticleId: instance._searchArticleIdNode.get('value'),
							showSearchInfo: true,
							status: instance._statusNode.get('value'),
							title: instance._titleNode.get('value'),
							type: instance._typeNode.get('value')
						};

						if (event.searchEverywhere) {
							searchData[STR_SEARCH_FOLDER_ID] = -1;
						}
						else {
							searchData[STR_SEARCH_FOLDER_ID] = selectedFolder.id;
						}

						instance._searchArticle(searchData);
					},

					_onDataRetrieveSuccess: function(event) {
						var instance = this;

						var responseData = event.responseData;

						instance._journalContainer.loadingmask.hide();

						var content = A.Node.create(responseData);

						if (content) {
							instance._appViewFolders.processData(content);

							instance._appViewSelect.syncDisplayStyleToolbar();

							instance._setSearchResults(content);
						}
					},

					_onOpenAdvancedSearch: function(event) {
						var instance = this;

						var advancedSearch = instance.byId('advancedSearch');

						var showAdvancedSearch = instance.byId('showAdvancedSearch');

						var advancedSearchClosed = !showAdvancedSearch.hasClass('close-advanced-search');

						showAdvancedSearch.toggleClass('close-advanced-search', advancedSearchClosed);

						advancedSearch.toggle(advancedSearchClosed);
					},

					_onPageLoaded: function(event) {
						var instance = this;

						var paginatorData = event.paginator;

						if (paginatorData) {
							instance._appViewPaginator.set(STR_PAGINATOR_DATA, paginatorData);
						}
					},

					_onSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._appViewSelect.get(STR_SELECTED_FOLDER);

						var searchFolderId = selectedFolder.id;

						if (searchFolderId === 0) {
							searchFolderId = -1;
						}

						var searchData = {
							advancedSearch: false,
							andOperator: '',
							folderId: selectedFolder.id,
							content: '',
							description: '',
							keywords: instance._keywordsNode.get('value'),
							searchArticleId: '',
							searchFolderId: searchFolderId,
							showSearchInfo: true,
							status: '',
							title: '',
							type: ''
						};

						instance._searchArticle(searchData);
					},

					_searchArticle: function(searchData) {
						var instance = this;

						if (searchData.showSearchInfo) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.empty();

							var searchingTPL = Lang.sub(TPL_MESSAGE_SEARCHING, [Liferay.Language.get('searching,-please-wait')]);

							entriesContainer.html(searchingTPL);
						}

						instance._journalContainer.all('.article-entries-paginator').hide();

						var requestParams = {};

						requestParams[instance.ns(STRUTS_ACTION)] = '/journal/search';
						requestParams[instance.ns(STR_ADVANCED_SEARCH)] = searchData.advancedSearch;
						requestParams[instance.ns(STR_AND_OPERATOR)] = searchData.andOperator;
						requestParams[instance.ns(STR_CONTENT)] = searchData.content;
						requestParams[instance.ns(STR_DESCRIPTION)] = searchData.description;
						requestParams[instance.ns(STR_FOLDER_ID)] = searchData.folderId;
						requestParams[instance.ns(STR_SEARCH_FOLDER_ID)] = searchData.searchFolderId;
						requestParams[instance.ns(STR_SEARCH_ARTICLE_ID)] = searchData.searchArticleId;
						requestParams[instance.ns(STR_STATUS)] = searchData.status;
						requestParams[instance.ns(STR_TITLE)] = searchData.title;
						requestParams[instance.ns(STR_TYPE)] = searchData.type;
						requestParams[instance.ns(SEARCH_TYPE)] = SEARCH_TYPE_SINGLE;
						requestParams[instance.ns(STR_KEYWORDS)] = searchData.keywords;
						requestParams[instance.ns(STR_SHOW_SEARCH_INFO)] = searchData.showSearchInfo;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: Liferay.JOURNAL_SEARCH
							}
						);
					},

					_setSearchResults: function(content) {
						var instance = this;

						var searchInfo = instance.one('#' + instance.ns('searchInfo'), content);

						var entriesContainer = instance._entriesContainer;

						if (searchInfo) {
							entriesContainer.empty();

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.setContent(searchInfo);
						}

						var fragmentSearchResults = instance.one('#fragmentSearchResults', content);

						var searchResults;

						if (fragmentSearchResults) {
							searchResults = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER, entriesContainer);

							if (searchResults) {
								searchResults.empty();

								searchResults.plug(A.Plugin.ParseContent);

								searchResults.setContent(fragmentSearchResults.html());
							}
						}

						var searchResultsContainer = instance.one('#' + STR_SEARCH_RESULTS_CONTAINER, content);

						if (searchResultsContainer) {
							if (!searchInfo) {
								entriesContainer.empty();
							}

							entriesContainer.plug(A.Plugin.ParseContent);

							entriesContainer.append(searchResultsContainer);
						}
					}
				}
			}
		);

		Liferay.JOURNAL_SEARCH = SRC_SEARCH;

		Liferay.JOURNAL_SEARCH_END = SRC_SEARCH_END;

		Liferay.JOURNAL_ENTRIES_PAGINATOR = SRC_ENTRIES_PAGINATOR;

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: ['aui-loading-mask', 'aui-paginator', 'aui-parse-content', 'event-simulate', 'liferay-app-view-folders', 'liferay-app-view-move', 'liferay-app-view-paginator', 'liferay-app-view-select', 'liferay-history-manager', 'liferay-list-view', 'liferay-message', 'liferay-portlet-base', 'liferay-util-list-fields', 'querystring-parse-simple']
	}
);