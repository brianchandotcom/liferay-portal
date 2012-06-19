AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var FoldersNavigation = Liferay.FoldersNavigation;
		var Lang = A.Lang;

		var formatSelectorNS = A.Node.formatSelectorNS;

		var DEFAULT_FOLDER_ID = 0;

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var SEARCH_TYPE = 'searchType';

		var SEARCH_TYPE_SINGLE = 1;

		var STR_ADVANCED_SEARCH = 'advancedSearch';

		var STR_AND_OPERATOR = 'andOperator';

		var STR_CLICK = 'click';

		var STR_CONTENT = 'content';

		var STR_DESCRIPTION = 'description';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_FOLDER_ID = 'folderId';

		var STR_KEYWORDS = 'keywords';

		var STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX = 'rowIdsJournalFolderCheckbox';

		var STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX = 'rowIdsJournalArticleCheckbox';

		var STR_SEARCH_ARTICLE_ID = 'searchArticleId';

		var STR_SEARCH_FOLDER_ID = 'searchFolderId';

		var STR_SEARCH_RESULTS_CONTAINER = 'searchResultsContainer';

		var STR_SHOW_SEARCH_INFO = 'showSearchInfo';

		var STR_STATUS = 'status';

		var STR_TITLE = 'title';

		var STR_TYPE = 'type';

		var STRUTS_ACTION = 'struts_action';

		var SRC_HISTORY = 2;

		var SRC_SEARCH = 3;

		var TPL_MESSAGE_SEARCHING = '<div class="portlet-msg-info">{0}</div><div class="loading-animation" />';

		Liferay.JOURNAL_SEARCH = SRC_SEARCH;

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

						instance._dataRetrieveFailure = instance.ns('dataRetrieveFailure');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');
						instance._eventOpenAdvancedSearch = instance.ns('openAdvancedSearch');
						instance._eventPageLoaded = instance.ns('pageLoaded');
						instance._eventChangeSearchFolder = instance.ns('changeSearchFolder');

						instance._displayStyleToolbarNode = instance.byId(DISPLAY_STYLE_TOOLBAR);
						instance._entriesContainer = instance.byId('entriesContainer');

						instance._advanceSearchContainer = instance.byId('advanceSearchContainer');

						instance._selectAllCheckbox = instance.byId('allRowIdsCheckbox');

						instance._displayStyle = instance.ns('displayStyle');
						instance._folderId = instance.ns(STR_FOLDER_ID);

						instance._andOperatorNode = instance.byId(STR_AND_OPERATOR);
						instance._contentNode = instance.byId(STR_CONTENT);
						instance._descriptionNode = instance.byId(STR_DESCRIPTION);
						instance._keywordsNode = instance.byId(STR_KEYWORDS);
						instance._searchArticleIdNode = instance.byId(STR_SEARCH_ARTICLE_ID);
						instance._statusNode = instance.byId(STR_STATUS);
						instance._titleNode = instance.byId(STR_TITLE);
						instance._typeNode = instance.byId(STR_TYPE);

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

						instance._displayViews = config.displayViews;

						var paginatorAjaxNavigation = new PaginatorAjaxNavigation(
							{
								'entriesTotal' : config.entriesTotal,
								'entryEnd' : config.entryEnd,
								'entryRowsPerPage' : config.entryRowsPerPage,
								'entryRowsPerPageOptions' : config.entryRowsPerPageOptions,
								'entryPaginatorContainer' : '.article-entries-paginator',
								'folderEnd' : config.folderEnd,
								'folderPaginatorContainer' : '.folder-paginator',
								'folderRowsPerPage' : config.folderRowsPerPage,
								'folderRowsPerPageOptions' : config.folderRowsPerPageOptions,
								'foldersTotal' : config.foldersTotal,
								'namespace' : config.namespace
							}
						);

						instance._paginatorAjaxNavigation = paginatorAjaxNavigation;

						var selectAjaxNavigation = new SelectAjaxNavigation(
							{
								'checkBoxesId' :
									[
										instance.ns(STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX),
										instance.ns(STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX)
									],
								'displayStyle' : config.displayStyle,
								'displayStyleCssClass' : 'article-display-style',
								'folderContainer' : folderContainer,
								'namespace' : config.namespace,
								'portletContainerId': instance.ns('journalContainer'),
								'selector': 'article-selector'
							}
						);

						instance._selectAjaxNavigation = selectAjaxNavigation;

						var dragAndDropNavigation = new DragAndDropNavigation(
							{
								'actions' : config.actions,
								'allRowIds' : config.allRowIds,
								'processEntryIds' :
								{
									checkBoxesIds :
										[
											instance.ns(STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX),
											instance.ns(STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX)
										],
									entryIds :
										[
											instance.ns('articleIds'),
											instance.ns('folderIds')
										]
								},
								'displayStyleCssClass' : 'article-display-style',
								'draggableCssClass' : 'article-link',
								'editEntryUrl' : config._editEntryUrl,
								'folderIdHashRegEx' : config.folderIdHashRegEx,
								'form' : config.form,
								'moveConstant': config.moveConstant,
								'moveEntryRenderUrl': config.moveEntryRenderUrl,
								'namespace' : config.namespace,
								'portletContainerId': instance.ns('journalContainer'),
								'portletGroup' : 'journal',
								'selectAjaxNavigation' : selectAjaxNavigation,
								'updateable' : config.updateable
							}
						);

						instance._dragAndDropNavigation = dragAndDropNavigation;

						var foldersNavigation = new FoldersNavigation(
							{
								'defaultParams' : config.defaultParams,
								'defaultParentFolderId' : config.defaultParentFolderId,
								'displayStyle' : config.displayStyle,
								'displayStyleCssClass' : 'article-display-style',
								'displayStyleToolbarId' : DISPLAY_STYLE_TOOLBAR,
								'displayViews' : instance._displayViews,
								'dragAndDropNavigation' : instance._dragAndDropNavigation,
								'listView' : instance._listView,
								'mainUrl': config.mainUrl,
								'namespace': instance.NS,
								'paginatorAjaxNavigation': paginatorAjaxNavigation,
								'portletContainerId': 'journalContainer',
								'selectAjaxNavigation' : selectAjaxNavigation
							}
						);

						instance._foldersNavigation = foldersNavigation;

						journalContainer.delegate(
							STR_CLICK,
							A.bind(instance._onOpenAdvancedSearch, instance),
							'.article-advanced-search-icon'
						);

						var eventHandles = [
							Liferay.on(instance._eventDataRetrieveSuccess, instance._onDataRetrieveSuccess, instance),
							Liferay.on(instance._eventChangeSearchFolder, instance._onChangeSearchFolder, instance)
						];

						instance._listView.after('transitionComplete', A.bind(instance._foldersNavigation._initDropTargets, instance._foldersNavigation), instance);

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						journalContainer.delegate(
							STR_CLICK,
							A.bind(instance._foldersNavigation._onPortletContainerClick, instance._foldersNavigation),
							formatSelectorNS(instance.NS, '#entriesContainer a[data-folder=true], #breadcrumbContainer a')
						);

						eventHandles.push(
							History.after('stateChange', instance._afterStateChange, instance)
						);

						journalContainer.plug(A.LoadingMask);

						instance._config = config;

						instance._folderContainer = folderContainer;

						instance._eventHandles = eventHandles;

						eventHandles.push(Liferay.on(config.portletId + ':portletRefreshed', A.bind(instance.destructor, instance)));

						var searchFormNode = instance.one('#fm1');

						if (searchFormNode) {
							searchFormNode.on('submit', instance._onSearchFormSubmit, instance);
						}

						var advanceSearchFormNode = instance.one('#fmAdvancedSearch');

						if (advanceSearchFormNode) {
							advanceSearchFormNode.on('submit', instance._onAdvanceSearchFormSubmit, instance);
						}

						instance._foldersNavigation._restoreState();
					},

					destructor: function() {
						var instance = this;

						instance._listView.destroy();

						A.Array.invoke(instance._eventHandles, 'detach');

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
							requestParams = instance._foldersNavigation._getDefaultHistoryState();
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								src: SRC_HISTORY
							}
						);
					},

					_afterListViewItemChange: function(event) {
						var instance = this;

						var data = {
							entryTypeId : 'data-structure-id',
							requestParam : 'structureId'
						};

						instance._foldersNavigation._afterListViewItemChange(event, data);
					},

					_onChangeSearchFolder: function(event) {
						var instance = this;

						var selectedFolder = instance._selectAjaxNavigation._getSelectedFolder();

						var showAdvancedSearch = instance.byId('showAdvancedSearch');

						var searchData = {
							advancedSearch: showAdvancedSearch.hasClass('close-advanced-search'),
							andOperator: instance._andOperatorNode.get('value'),
							folderId: selectedFolder.id,
							content: instance._contentNode.get('value'),
							description: instance._descriptionNode.get('value'),
							keywords: '',
							searchArticleId: instance._searchArticleIdNode.get('value'),
							searchFolderId: selectedFolder.id,
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
							instance._foldersNavigation._setBreadcrumb(content);
							instance._foldersNavigation._setButtons(content);
							instance._foldersNavigation._setEntries(content);
							instance._foldersNavigation._setFolders(content);
							instance._foldersNavigation._setParentFolderTitle(content);
							instance._foldersNavigation._syncDisplayStyleToolbar(content);
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

					_onAdvanceSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._selectAjaxNavigation._getSelectedFolder();

						var showTabs = (selectedFolder.id == DEFAULT_FOLDER_ID);

						var searchData = {
							advancedSearch: true,
							andOperator: instance._andOperatorNode.get('value'),
							folderId: selectedFolder.id,
							content: instance._contentNode.get('value'),
							description: instance._descriptionNode.get('value'),
							keywords: '',
							searchArticleId: instance._searchArticleIdNode.get('value'),
							searchFolderId: selectedFolder.id,
							showSearchInfo: true,
							status: instance._statusNode.get('value'),
							title: instance._titleNode.get('value'),
							type: instance._typeNode.get('value')
						};

						instance._searchArticle(searchData);
					},

					_onSearchFormSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						var selectedFolder = instance._selectAjaxNavigation._getSelectedFolder();

						var showTabs = (selectedFolder.id == DEFAULT_FOLDER_ID);

						var searchData = {
							advancedSearch: false,
							andOperator: '',
							folderId: selectedFolder.id,
							content: '',
							description: '',
							keywords: instance._keywordsNode.get('value'),
							searchArticleId: '',
							searchFolderId: selectedFolder.id,
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

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: ['aui-loading-mask', 'aui-paginator', 'aui-parse-content', 'event-simulate', 'liferay-folders-navigation', 'liferay-history-manager', 'liferay-list-view', 'liferay-message', 'liferay-portlet-base', 'liferay-util-list-fields', 'querystring-parse-simple']
	}
);