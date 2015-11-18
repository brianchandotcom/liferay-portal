AUI.add(
	'liferay-app-view-paginator',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var owns = AObject.owns;

		var CSS_HIDE = 'hide';

		var PAIR_SEPARATOR = History.PAIR_SEPARATOR;

		var SRC_ENTRIES_PAGINATOR = 1;

		var SRC_SEARCH = 3;

		var SRC_SEARCH_FRAGMENT = 2;

		var STR_ACTIVE = 'active';

		var STR_CHANGE_REQUEST = 'changeRequest';

		var STR_ENTRY_END = 'entryEnd';

		var STR_ENTRY_START = 'entryStart';

		var STR_FOLDER_END = 'folderEnd';

		var STR_FOLDER_ID = 'folderId';

		var STR_FOLDER_START = 'folderStart';

		var STR_ITEMS_PER_PAGE = 'itemsPerPage';

		var STR_NUMBER_OF_PAGES = 'numberOfPages';

		var STR_OLD_PAGES = 'oldPages';

		var STR_PAGE = 'page';

		var STR_SEARCH_TYPE = 'searchType';

		var STR_SHOW_CONTROLS = 'showControls';

		var STR_STRINGS = 'strings';

		var STR_TOTAL = 'total';

		var VALUE_SEPARATOR = History.VALUE_SEPARATOR;

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

					numberOfPages: {
						validator: Lang.isNumber,
						value: 5
					},

					paginationData: {
						validator: Lang.isObject
					},

					showControls: {
						value: true
					},

					strings: {
						validator: Lang.isObject,
						value: {
							first: '&laquo;',
							last: '&raquo;',
							next: Liferay.Language.get('next'),
							nextPages: '...',
							prev: Liferay.Language.get('previous'),
							prevPages: '...'
						}
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
						var showControls = instance.get(STR_SHOW_CONTROLS);

						var entryRowsPerPage = instance.get('entryRowsPerPage');

						if (entriesTotal > 0) {
							entryPage = entryEnd / entryRowsPerPage;
						}

						var totalEntryPages = instance._getTotalPages(entriesTotal, entryRowsPerPage);

						var entryPagination = new Liferay.Pagination(
							{
								boundingBox: instance.get('entryPaginationContainer'),
								circular: false,
								itemsPerPage: entryRowsPerPage,
								itemsPerPageList: instance.get('entryRowsPerPageOptions'),
								namespace: instance.NS,
								numberOfPages: instance.get(STR_NUMBER_OF_PAGES),
								page: entryPage,
								results: entriesTotal,
								showControls: showControls,
								strings: instance.get(STR_STRINGS),
								total: totalEntryPages,
								visible: (totalEntryPages > 1)
							}
						).render();

						entryPagination.after(STR_CHANGE_REQUEST, instance._afterEntryPaginationChangeRequest, instance);

						instance._entryPagination = entryPagination;

						var folderPage = 0;

						var folderRowsPerPage = instance.get('folderRowsPerPage');

						var foldersTotal = instance.get('foldersTotal');

						if (foldersTotal > 0) {
							folderPage = instance.get(STR_FOLDER_END) / folderRowsPerPage;
						}

						var totalFolderPages = instance._getTotalPages(foldersTotal, folderRowsPerPage);

						var folderPagination = new Liferay.Pagination(
							{
								boundingBox: instance.get('folderPaginationContainer'),
								circular: false,
								itemsPerPage: folderRowsPerPage,
								itemsPerPageList: instance.get('folderRowsPerPageOptions'),
								namespace: instance.NS,
								page: folderPage,
								results: foldersTotal,
								showControls: true,
								total: totalFolderPages,
								visible: (totalFolderPages > 1)
							}
						).render();

						folderPagination.after(STR_CHANGE_REQUEST, instance._afterFolderPaginationChangeRequest, instance);

						instance._folderPagination = folderPagination;

						instance._eventHandles = [
							Liferay.on('liferay-app-view-folders:dataRequest', instance._onDataRequest, instance),
							Liferay.on('liferay-app-view-folders:afterDataRequest', instance._afterDataRequest, instance),
							instance.after('paginationDataChange', instance._afterPaginationDataChange, instance),
							entryPagination.after('itemsPerPageChange', instance._onItemsPerPageChange, instance)
						];

						if (showControls && (entriesTotal || foldersTotal)) {
							instance._hideAllPaginatorNodes(entryPagination);

							instance._displayPaginator(entryPagination, entryPagination.get(STR_PAGE));
						}
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

						var entryRowsPerPage = event.state.itemsPerPage;

						var startEndParams = instance._getResultsStartEnd(instance._entryPagination, entryRowsPerPage);

						var requestParams = instance._lastDataRequest || instance._getDefaultParams();

						var customParams = {};

						customParams[instance.ns(STR_ENTRY_START)] = startEndParams[0];
						customParams[instance.ns(STR_ENTRY_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_ENTRIES_PAGE)] = true;
						customParams[instance.ns(VIEW_FOLDERS)] = false;

						if (AObject.owns(requestParams, instance.ns(STR_SEARCH_TYPE))) {
							customParams[instance.ns(STR_SEARCH_TYPE)] = SRC_SEARCH_FRAGMENT;
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

						var folderRowsPerPage = event.state.itemsPerPage;

						var startEndParams = instance._getResultsStartEnd(instance._folderPagination, folderRowsPerPage);

						var requestParams = instance._lastDataRequest || instance._getDefaultParams();

						var customParams = {};

						customParams[instance.ns(STR_FOLDER_START)] = startEndParams[0];
						customParams[instance.ns(STR_FOLDER_END)] = startEndParams[1];
						customParams[instance.ns(VIEW_ENTRIES)] = false;
						customParams[instance.ns(VIEW_FOLDERS)] = true;

						A.mix(requestParams, customParams, true);

						instance._reflowPaginator = true;

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams
							}
						);
					},

					_afterPaginationDataChange: function(event) {
						var instance = this;

						var paginationDataNewVal = event.newVal;

						var pagination = instance['_' + paginationDataNewVal.name];

						if (A.instanceOf(pagination, Liferay.Pagination)) {
							var state = paginationDataNewVal.state;

							var rowsPerPage = state.rowsPerPage;
							var total = state.total;

							pagination.set('results', total);
							pagination.set(STR_TOTAL, instance._getTotalPages(total, rowsPerPage));
							pagination.set('visible', (total > rowsPerPage));

							pagination.setState(state);

							var paginationDataPrevVal = event.prevVal;

							if (instance.get(STR_SHOW_CONTROLS)) {
								if (instance._reflowPaginator) {
									pagination._syncLabel(rowsPerPage);
									pagination._syncResults(state.page, rowsPerPage);
									instance._hideAllPaginatorNodes(pagination);
									pagination._paginationContentNode.setData(STR_OLD_PAGES, null);

									instance._reflowPaginator = false;
								}

								instance._displayPaginator(pagination, state.page);
								pagination._syncNavigationUI();
							}
						}
					},

					_displayPaginator: function(pagination, page) {
						var instance = this;

						var pageNodes = instance._getPaginationPageNodes(pagination);

						var numberOfPages = instance.get(STR_NUMBER_OF_PAGES);

						var pageNodeSize = pageNodes.size();

						if (pageNodeSize < numberOfPages) {
							numberOfPages = pageNodeSize;
						}

						var paginationContentNode = pagination._paginationContentNode;

						instance._syncPrevNextPagesControls(pageNodes, paginationContentNode, page);

						var oldPages = paginationContentNode.getData(STR_OLD_PAGES);

						instance._renderPaginatorNodes(numberOfPages, oldPages, page, pageNodes);

						paginationContentNode.setData(STR_OLD_PAGES, instance._visiblePages);

						var lastPage = page - 1;

						var lastPageNode = pageNodes.item(lastPage);

						if (lastPageNode && !lastPageNode.hasClass(STR_ACTIVE)) {
							lastPageNode.addClass(STR_ACTIVE);
						}
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

					_getEntryPagination: function() {
						var instance = this;

						return instance._entryPagination;
					},

					_getFolderPagination: function() {
						var instance = this;

						return instance._folderPagination;
					},

					_getPaginationPageNodes: function(pagination) {
						var instance = this;

						var paginationControlSize = pagination._paginationContentNode.all('.pagination-control').size();

						var shiftIndex = paginationControlSize / 2;

						var newIndex = pagination.get(STR_TOTAL) + shiftIndex;

						return pagination.get('items').slice(shiftIndex, newIndex);
					},

					_getResultsStartEnd: function(pagination, rowsPerPage, page) {
						var instance = this;

						if (!Lang.isValue(page)) {
							page = 0;

							var curPage = pagination.get(STR_PAGE) - 1;

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

					_hideAllPaginatorNodes: function(pagination) {
						var instance = this;

						var pageNodes = instance._getPaginationPageNodes(pagination);

						pageNodes.hide();
					},

					_hideCurrentPaginatorNodes: function(numberOfPages, pages, pageNodes) {
						var instance = this;

						for (var i = 0; i < pages.length; i++) {
							var oldPageNode = pageNodes.item(pages[i]);

							if (oldPageNode) {
								oldPageNode.hide();
							}
						}
					},

					_onDataRequest: function(event) {
						var instance = this;

						instance._updatePaginationValues(event.requestParams, event.resetPagination);

						if (event.src === SRC_SEARCH) {
							instance._entryPagination.setState(
								{
									page: 1
								}
							);
						}
					},

					_onItemsPerPageChange: function(event) {
						var instance = this;

						instance._reflowPaginator = true;
					},

					_renderPaginatorNodes: function(numberOfPages, oldPages, page, pageNodes) {
						var instance = this;

						if (!instance._visiblePages || (A.Array.indexOf(instance._visiblePages, page - 1) === -1) || (oldPages != instance._visiblePages)) {
							instance._updateVisiblePagesList(numberOfPages, page, pageNodes);
						}

						if (oldPages) {
							if (A.Array.indexOf(oldPages, page - 1) === -1) {
								instance._showCurrentPaginatorNodes(numberOfPages, instance._visiblePages, pageNodes);

								instance._hideCurrentPaginatorNodes(numberOfPages, oldPages, pageNodes);
							}
						}
						else {
							instance._showCurrentPaginatorNodes(numberOfPages, instance._visiblePages, pageNodes);
						}
					},

					_showCurrentPaginatorNodes: function(numberOfPages, pages, pageNodes) {
						var instance = this;

						for (var i = 0; i < pages.length; i++) {
							var visiblePageNode = pageNodes.item(pages[i]);

							if (visiblePageNode) {
								visiblePageNode.show();
							}
						}
					},

					_syncPrevNextPagesControls: function(pageNodes, paginationContentNode, page) {
						var instance = this;

						var nextPages;
						var prevPages;

						var strings = instance.get(STR_STRINGS);

						if (paginationContentNode) {
							nextPages = paginationContentNode.one('.next-pages');
							prevPages = paginationContentNode.one('.prev-pages');
						}

						if (strings.prevPages && strings.nextPages && prevPages && nextPages) {
							var numberOfPages = instance.get(STR_NUMBER_OF_PAGES);

							var totalPages = pageNodes.size();

							var nextPages = paginationContentNode.one('.next-pages');
							var prevPages = paginationContentNode.one('.prev-pages');

							if (totalPages <= numberOfPages) {
								prevPages.addClass(CSS_HIDE);
								nextPages.addClass(CSS_HIDE);
							}
							else if (page >= 1 && page <= numberOfPages) {
								prevPages.addClass(CSS_HIDE);
								nextPages.removeClass(CSS_HIDE);
							}

							var modNumberOfPages = totalPages % numberOfPages;

							if (modNumberOfPages === 0) {
								modNumberOfPages = numberOfPages;
							}

							var deltaTotalPages = totalPages - modNumberOfPages;

							if (page > numberOfPages && page <= deltaTotalPages) {
								prevPages.removeClass(CSS_HIDE);
								nextPages.removeClass(CSS_HIDE);
							}

							var hasMorePages = page > deltaTotalPages && page <= totalPages;

							if (hasMorePages && deltaTotalPages !== 0) {
								prevPages.removeClass(CSS_HIDE);
							}

							if (hasMorePages || page === totalPages) {
								nextPages.addClass(CSS_HIDE);
							}
						}
					},

					_updatePaginationValues: function(requestParams, resetPagination) {
						var instance = this;

						var customParams = {};

						var entryRowsPerPage = instance._entryPagination.get(STR_ITEMS_PER_PAGE);

						var folderRowsPerPage = instance._folderPagination.get(STR_ITEMS_PER_PAGE);

						if (resetPagination) {
							customParams[instance.ns(STR_ENTRY_START)] = 0;
							customParams[instance.ns(STR_ENTRY_END)] = entryRowsPerPage;
							customParams[instance.ns(STR_FOLDER_START)] = 0;
							customParams[instance.ns(STR_FOLDER_END)] = folderRowsPerPage;

							instance._reflowPaginator = resetPagination;
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
					},

					_updateVisiblePagesList: function(numberOfPages, page, pageNodes) {
						var instance = this;

						var pageCounter = page;

						var visiblePages = [];

						do {
							visiblePages.unshift(pageCounter - 1);

							pageCounter--;
						}
						while (pageCounter % numberOfPages > 0);

						pageCounter = page;

						while (pageCounter % numberOfPages > 0) {
							visiblePages.push(pageCounter);

							if (pageCounter >= pageNodes.size()) {
								break;
							}

							pageCounter++;
						}

						instance._visiblePages = visiblePages;
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
		requires: ['aui-parse-content', 'liferay-history-manager', 'liferay-pagination', 'liferay-portlet-base']
	}
);