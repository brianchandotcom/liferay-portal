AUI.add(
	'liferay-app-view-paginator',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var owns = AObject.owns;

		var ENTRY_ROWS_PER_PAGE = 'entryRowsPerPage';

		var FOLDER_ROWS_PER_PAGE = 'folderRowsPerPage';

		var PAIR_SEPARATOR = History.PAIR_SEPARATOR;

		var SEARCH_TYPE = 'searchType';

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_SEARCH_FRAGMENT = 2;

		var SRC_SEARCH = 3;

		var STR_CHANGE_REQUEST = 'changeRequest';

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_ID = 'folderId';

		var STR_FOLDER_START = 'folderStart';

		var VIEW_ENTRIES = 'viewEntries';

		var VIEW_ENTRIES_PAGE = 'viewEntriesPage';

		var VIEW_FOLDERS = 'viewFolders';

		var VALUE_SEPARATOR = History.VALUE_SEPARATOR;

		var AppViewPaginator = A.Component.create(
			{
				ATTRS: {
					defaultParams: {
						getter: '_getDefaultParams',
						readOnly: true
					},

					entriesTotal: {
						validator: Lang.isNumber
					},

					entryEnd: {
						validator: Lang.isNumber
					},

					entryPagination: {
						getter: '_getEntryPagination',
						readOnly: true
					},

					entryPaginationContainer: {
						validator: Lang.isString
					},

					entryRowsPerPage: {
						validator: Lang.isNumber
					},

					entryRowsPerPageOptions: {
						validator: Lang.isArray
					},

					entryStart: {
						validator: Lang.isNumber,
						value: 0
					},

					folderEnd: {
						validator: Lang.isNumber
					},

					folderId: {
						validator: Lang.isNumber
					},

					folderPagination: {
						getter: '_getFolderPagination',
						readOnly: true
					},

					folderPaginationContainer: {
						validator: Lang.isString
					},

					folderStart: {
						validator: Lang.isNumber,
						value: 0
					},

					folderRowsPerPage: {
						validator: Lang.isNumber
					},

					folderRowsPerPageOptions: {
						validator: Lang.isArray
					},

					foldersTotal: {
						validator: Lang.isNumber
					},

					namespace: {
						validator: Lang.isString
					},

					paginationData: {
						validator: Lang.isObject
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-app-view-paginator',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventDataRequest = instance.ns('dataRequest');

						var entryPage = 0;

						var entryEnd = instance.get(STR_ENTRY_END);

						var entriesTotal = instance.get('entriesTotal');

						var entryRowsPerPage = instance.get(ENTRY_ROWS_PER_PAGE);

						if (entriesTotal > 0) {
							entryPage = entryEnd / entryRowsPerPage;
						}

						var totalEntryPages = instance._getTotalPages(entriesTotal, entryRowsPerPage);

						var entryPagination = new A.Pagination(
							{
								boundingBox: instance.get('entryPaginationContainer'),
								circular: false,
								page: entryPage,
								total: totalEntryPages,
								visible: totalEntryPages > 1
							}
						).render();

						entryPagination.after(STR_CHANGE_REQUEST, instance._afterEntryPaginationChangeRequest, instance);

						instance._entryPagination = entryPagination;

						var folderPage = 0;

						var folderRowsPerPage = instance.get(FOLDER_ROWS_PER_PAGE);

						var foldersTotal = instance.get('foldersTotal');

						if (foldersTotal > 0) {
							folderPage = instance.get(STR_FOLDER_END) / folderRowsPerPage;
						}

						var totalFolderPages = instance._getTotalPages(foldersTotal, folderRowsPerPage);

						var folderPagination = new A.Pagination(
							{
								boundingBox: instance.get('folderPaginationContainer'),
								circular: false,
								page: folderPage,
								total: totalFolderPages,
								visible: totalFolderPages > 1
							}
						).render();

						folderPagination.after(STR_CHANGE_REQUEST, instance._afterFolderPaginationChangeRequest, instance);

						instance._folderPagination = folderPagination;

						instance._eventHandles = [
							Liferay.on('liferay-app-view-folders:dataRequest', instance._onDataRequest, instance),
							Liferay.on('liferay-app-view-folders:afterDataRequest', instance._afterDataRequest, instance),
							instance.after('paginationDataChange', instance._afterPaginationDataChange, instance)
						];
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._entryPagination.destroy();
						instance._folderPagination.destroy();
					},

					_afterDataRequest: function(event) {
						var instance = this;

						instance._lastDataRequest = event.data;
					},

					_afterEntryPaginationChangeRequest: function(event) {
						var instance = this;

						var entryRowsPerPage = instance.get(ENTRY_ROWS_PER_PAGE);

						var startEndParams = instance._getResultsStartEnd(instance._entryPagination, entryRowsPerPage);

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

					_afterFolderPaginationChangeRequest: function(event) {
						var instance = this;

						var folderRowsPerPage = instance.get(FOLDER_ROWS_PER_PAGE);

						var startEndParams = instance._getResultsStartEnd(instance._folderPagination, folderRowsPerPage);

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

					_afterPaginationDataChange: function(event) {
						var instance = this;

						var paginationData = event.newVal;

						var pagination = instance['_' + paginationData.name];

						if (A.instanceOf(pagination, A.Pagination)) {
							var state = paginationData.state;

							pagination.set('total', instance._getTotalPages(state.total, state.rowsPerPage));
							pagination.set('visible', (state.total > state.rowsPerPage));

							pagination.setState(state);
						}
					},

					_getEntryPagination: function() {
						var instance = this;

						return instance._entryPagination;
					},

					_getFolderPagination: function() {
						var instance = this;

						return instance._folderPagination;
					},

					_getDefaultParams: function() {
						var instance = this;

						var params = {};

						params[instance.ns(STR_ENTRY_END)] = instance.get(STR_ENTRY_END);
						params[instance.ns(STR_ENTRY_START)] = instance.get(STR_ENTRY_START);
						params[instance.ns(STR_FOLDER_END)] = instance.get(STR_FOLDER_END);
						params[instance.ns(STR_FOLDER_START)] = instance.get(STR_FOLDER_START);
						params[instance.ns(STR_FOLDER_ID)] = instance.get(STR_FOLDER_ID);

						var namespace = instance.NS;

						var tmpParams = QueryString.parse(location.search, PAIR_SEPARATOR, VALUE_SEPARATOR);

						A.mix(tmpParams, QueryString.parse(location.hash, PAIR_SEPARATOR, VALUE_SEPARATOR));

						for (var paramName in tmpParams) {
							if (owns(tmpParams, paramName) && paramName.indexOf(namespace) === 0) {
								params[paramName] = tmpParams[paramName];
							}
						}

						return params;
					},

					_getResultsStartEnd: function(pagination, rowsPerPage, page) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = 0;

							var curPage = pagination.get('page') - 1;

							if (curPage > 0) {
								page = curPage;
							}
						}

						var start = page * rowsPerPage;
						var end = start + rowsPerPage;

						return [start, end];
					},

					_getTotalPages: function(totalRows, rowsPerPage) {
						var instance = this;

						return Math.ceil(totalRows / rowsPerPage);
					},

					_onDataRequest: function(event) {
						var instance = this;

						instance._updatePaginationValues(event.requestParams, event.resetPagination);

						var src = event.src;

						if (src === SRC_SEARCH) {
							instance._entryPagination.setState(
								{
									page: 1
								}
							);
						}
					},

					_updatePaginationValues: function(requestParams, resetPagination) {
						var instance = this;

						var customParams = {};

						var entryRowsPerPage = instance.get(ENTRY_ROWS_PER_PAGE);

						var folderRowsPerPage = instance.get(FOLDER_ROWS_PER_PAGE);

						if (resetPagination) {
							customParams[instance.ns(STR_ENTRY_START)] = 0;
							customParams[instance.ns(STR_ENTRY_END)] = entryRowsPerPage;
							customParams[instance.ns(STR_FOLDER_START)] = 0;
							customParams[instance.ns(STR_FOLDER_END)] = folderRowsPerPage;
						}
						else {
							var entryStartEndParams = instance._getResultsStartEnd(instance._entryPagination, entryRowsPerPage);
							var folderStartEndParams = instance._getResultsStartEnd(instance._folderPagination, folderRowsPerPage);

							if (!owns(requestParams, instance.ns(STR_ENTRY_START)) && !owns(requestParams, instance.ns(STR_ENTRY_END))) {
								customParams[instance.ns(STR_ENTRY_START)] = entryStartEndParams[0];
								customParams[instance.ns(STR_ENTRY_END)] = entryStartEndParams[1];
							}

							if (!owns(requestParams, instance.ns(STR_FOLDER_START)) && !owns(requestParams, instance.ns(STR_FOLDER_END))) {
								customParams[instance.ns(STR_FOLDER_START)] = folderStartEndParams[0];
								customParams[instance.ns(STR_FOLDER_END)] = folderStartEndParams[1];
							}
						}

						if (!AObject.isEmpty(customParams)) {
							A.mix(requestParams, customParams, true);
						}
					}
				},

				PAIR_SEPARATOR: '&',

				VALUE_SEPARATOR: '='
			}
		);

		Liferay.AppViewPaginator = AppViewPaginator;
	},
	'',
	{
		requires: ['aui-pagination', 'aui-parse-content', 'liferay-history-manager', 'liferay-portlet-base']
	}
);