AUI.add(
	'liferay-app-view-paginator',
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

					entryPaginator: {
						getter: '_getEntryPaginator',
						readOnly: true
					},

					entryPaginatorContainer: {
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

					folderPaginator: {
						getter: '_getFolderPaginator',
						readOnly: true
					},

					folderPaginatorContainer: {
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

					paginatorData: {
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

						var entryRowsPerPage = instance.get('entryRowsPerPage');

						if (entriesTotal > 0) {
							entryPage = entryEnd / entryRowsPerPage;
						}

						var TPL = {
							pageReportLabel: Lang.sub(Liferay.Language.get('x-of-x'), ['{page}', '{totalPages}']),
							totalLabel: '(' + Liferay.Language.get('total') + ' {total})'
						};

						var entryPaginator = new A.Paginator(
							{
								circular: false,
								containers: instance.get('entryPaginatorContainer'),
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: entryPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: entryRowsPerPage,
								rowsPerPageOptions: instance.get('entryRowsPerPageOptions'),
								total: entriesTotal,
								TPL: TPL
							}
						).render();

						entryPaginator.on(STR_CHANGE_REQUEST, instance._onEntryPaginatorChangeRequest, instance);

						instance._entryPaginator = entryPaginator;

						var folderPage = 0;

						var folderRowsPerPage = instance.get('folderRowsPerPage');

						var foldersTotal = instance.get('foldersTotal');

						if (foldersTotal > 0) {
							folderPage = instance.get(STR_FOLDER_END) / folderRowsPerPage;
						}

						var folderPaginator = new A.Paginator(
							{
								alwaysVisible: false,
								circular: false,
								containers: instance.get('folderPaginatorContainer'),
								firstPageLinkLabel: '&lt;&lt;',
								lastPageLinkLabel: '&gt;&gt;',
								nextPageLinkLabel: '&gt;',
								page: folderPage,
								prevPageLinkLabel: '&lt;',
								rowsPerPage: folderRowsPerPage,
								rowsPerPageOptions: instance.get('folderRowsPerPageOptions'),
								total: foldersTotal,
								TPL: TPL
							}
						).render();

						folderPaginator.on(STR_CHANGE_REQUEST, instance._onFolderPaginatorChangeRequest, instance);

						instance._folderPaginator = folderPaginator;

						instance._eventHandles = [
							Liferay.on('liferay-app-view-folders:dataRequest', instance._onDataRequest, instance),
							Liferay.on('liferay-app-view-folders:afterDataRequest', instance._afterDataRequest, instance),
							instance.after('paginatorDataChange', instance._afterPaginatorDataChange, instance)
						];
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._entryPaginator.destroy();
						instance._folderPaginator.destroy();
					},

					_afterDataRequest: function(event) {
						var instance = this;

						instance._lastDataRequest = event.data;
					},

					_afterPaginatorDataChange: function(event) {
						var instance = this;

						var paginatorData = event.newVal;

						var paginator = instance['_' + paginatorData.name];

						if (A.instanceOf(paginator, A.Paginator)) {
							paginator.setState(paginatorData.state);
						}
					},

					_getEntryPaginator: function() {
						var instance = this;

						return instance._entryPaginator;
					},

					_getFolderPaginator: function() {
						var instance = this;

						return instance._folderPaginator;
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

					_onDataRequest: function(event) {
						var instance = this;

						instance._updatePaginatorValues(event.requestParams, event.resetPaginator);

						var src = event.src;

						if (src === SRC_SEARCH) {
							instance._entryPaginator.setState(
								{
									page: 1
								}
							);
						}
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

					_updatePaginatorValues: function(requestParams, resetPaginator) {
						var instance = this;

						var customParams = {};

						if (resetPaginator) {
							customParams[instance.ns(STR_ENTRY_START)] = 0;
							customParams[instance.ns(STR_ENTRY_END)] = instance._entryPaginator.get(ROWS_PER_PAGE);
							customParams[instance.ns(STR_FOLDER_START)] = 0;
							customParams[instance.ns(STR_FOLDER_END)] = instance._folderPaginator.get(ROWS_PER_PAGE);
						}
						else {
							var entryStartEndParams = instance._getResultsStartEnd(instance._entryPaginator);
							var folderStartEndParams = instance._getResultsStartEnd(instance._folderPaginator);

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
		requires: ['aui-paginator', 'aui-parse-content', 'liferay-history-manager', 'liferay-portlet-base']
	}
);