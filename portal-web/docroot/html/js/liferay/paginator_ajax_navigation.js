AUI.add(
	'liferay-paginator-ajax-navigation',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var owns = AObject.owns;

		var PAIR_SEPARATOR = History.PAIR_SEPARATOR;

		var VALUE_SEPARATOR = History.VALUE_SEPARATOR;

		var ROWS_PER_PAGE = 'rowsPerPage';

		var SEARCH_TYPE = 'searchType';

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_SEARCH_FRAGMENT = 2;

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_ID = 'folderId';

		var STR_FOLDER_START = 'folderStart';

		var VIEW_ENTRIES = 'viewEntries';

		var VIEW_ENTRIES_PAGE = 'viewEntriesPage';

		var VIEW_FOLDERS = 'viewFolders';

		var PaginatorAjaxNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferaypaginatorajaxnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._eventDataRequest = instance.ns('dataRequest');

						var entryPage = 0;

						if (config.entriesTotal > 0) {
							entryPage = config.entryEnd / config.entryRowsPerPage;
						}

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: config.entryPaginatorContainer,
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

						instance._entryPaginator = entryPaginator;

						var folderPage = 0;

						if (config.foldersTotal > 0) {
							folderPage = config.folderEnd / config.folderRowsPerPage;
						}

						var folderPaginator = new A.Paginator(
							{
								alwaysVisible: false,
								circular: false,
								containers: config.folderPaginatorContainer,
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

						instance._folderPaginator = folderPaginator;
					},

					destructor: function() {
						var instance = this;

						instance._entryPaginator.destroy();
						instance._folderPaginator.destroy();
					},

					getEntryPaginator: function() {
						var instance = this;

						return instance._entryPaginator;
					},

					getFolderPaginator: function() {
						var instance = this;

						return instance._folderPaginator;
					},

					_getDefaultParams: function() {
						var instance = this;

						var config = instance._config;

						var params = {};

						params[instance.ns(STR_ENTRY_END)] = config[STR_ENTRY_END];
						params[instance.ns(STR_ENTRY_START)] = config[STR_ENTRY_START];
						params[instance.ns(STR_FOLDER_END)] = config[STR_FOLDER_END];
						params[instance.ns(STR_FOLDER_START)] = config[STR_FOLDER_START];
						params[instance.ns(STR_FOLDER_ID)] = config[STR_FOLDER_ID];

						var namespace = instance.NS;

						var tmpParams = QueryString.parse(location.search, PAIR_SEPARATOR, VALUE_SEPARATOR);

						A.mix(tmpParams, QueryString.parse(location.hash, PAIR_SEPARATOR, VALUE_SEPARATOR));

						for (var paramName in tmpParams) {
							if (owns(tmpParams, paramName) && paramName.indexOf(namespace) == 0) {
								params[paramName] = tmpParams[paramName];
							}
						}

						return params;
					},

					_getResultsStartEnd: function(paginator, page, rowsPerPage) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = 0;

							var curPage = paginator.get('page') - 1;

							if (curPage > 0) {
								page = curPage;
							}
						}

						if (!Lang.isValue(rowsPerPage)) {
							rowsPerPage = paginator.get(ROWS_PER_PAGE);
						}

						var start = page * rowsPerPage;
						var end = start + rowsPerPage;

						return [start, end];
					},

					_onEntryPaginatorChangeRequest: function(event) {
						var instance = this;

						var startEndParams = instance._getResultsStartEnd(instance._entryPaginator);

						var requestParams = instance._lastDataRequest || instance._getDefaultParams();

						var customParams = {};

						customParams[instance.ns(STR_ENTRY_START)] = startEndParams[0];
						customParams[instance.ns(STR_ENTRY_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_ENTRIES_PAGE)] = true;
						customParams[instance.ns(VIEW_FOLDERS)] = false;

						if (AObject.owns(requestParams, instance.ns(SEARCH_TYPE))) {
							customParams[instance.ns(SEARCH_TYPE)] = SRC_SEARCH_FRAGMENT;
						}

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

						var requestParams = instance._lastDataRequest || {};

						var customParams = {};

						customParams[instance.ns(STR_FOLDER_START)] = startEndParams[0];
						customParams[instance.ns(STR_FOLDER_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_FOLDERS)] = true;

						A.mix(requestParams, customParams, true);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_setPaginatorData: function(paginatorData) {
						var instance = this;

						var paginator = instance['_' + paginatorData.name];

						if (A.instanceOf(paginator, A.Paginator)) {
							paginator.setState(paginatorData.state);
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
				},

				PAIR_SEPARATOR: '&',

				VALUE_SEPARATOR: '='
			}
		);

		Liferay.PaginatorAjaxNavigation = PaginatorAjaxNavigation;
	},
	'',
	{
		requires: ['aui-base', 'aui-paginator', 'aui-parse-content', 'liferay-history-manager', 'liferay-portlet-base']
	}
);