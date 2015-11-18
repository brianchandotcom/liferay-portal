AUI.add(
	'liferay-pagination',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var AObject = A.Object;

		var CSS_ACTIVE = 'active';

		var CSS_DISABLED = 'disabled';

		var CSS_PAGINATION_CONTROL = 'pagination-control';

		var NAME = 'pagination';

		var STR_ITEMS = 'items';

		var STR_ITEMS_PER_PAGE = 'itemsPerPage';

		var STR_ITEMS_PER_PAGE_LIST = 'itemsPerPageList';

		var STR_PAGE = 'page';

		var STR_RESULTS = 'results';

		var STR_SHIFT = 'shift';

		var STR_SHOW_CONTROLS = 'showControls';

		var STR_SPACE = ' ';

		var STR_STRINGS = 'strings';

		var Pagination = A.Component.create(
			{
				ATTRS: {
					itemsPerPage: {
						validator: Lang.isNumber,
						value: 20
					},

					itemsPerPageList: {
						validator: Lang.isArray,
						value: [5, 10, 20, 30, 50, 75]
					},

					namespace: {
						validator: Lang.isString
					},

					numberOfPages: {
						validator: Lang.isNumber,
						value: 5
					},

					results: {
						validator: Lang.isNumber,
						value: 0
					},

					selectedItem: {
						validator: Lang.isNumber,
						value: 0
					},

					shift: {
						validator: Lang.isNumber
					},

					showControls: {
						value: false
					},

					strings: {
						setter: function(value) {
							return A.merge(
								value,
								{
									items: Liferay.Language.get('items'),
									next: Liferay.Language.get('next'),
									of: Liferay.Language.get('of'),
									page: Liferay.Language.get('page'),
									per: Liferay.Language.get('per'),
									prev: Liferay.Language.get('previous'),
									results: Liferay.Language.get('results'),
									showing: Liferay.Language.get('showing')
								}
							);
						},
						validator: Lang.isObject,
						value: {
								next: Liferay.Language.get('next'),
								prev: Liferay.Language.get('previous')
						}
					},

					visible: {
						setter: '_uiSetVisible',
						validator: Lang.isBoolean
					}
				},

				EXTENDS: A.Pagination,

				NAME: NAME,

				prototype: {
					TOTAL_CONTROLS: 4,

					TPL_CONTAINER: '<div class="lfr-pagination-controls" id="{id}"></div>',

					TPL_DELTA_SELECTOR: '<div class="lfr-pagination-delta-selector">' +
						'<div class="btn-group lfr-icon-menu">' +
							'<a class="btn direction-down dropdown-toggle max-display-items-15" href="javascript:;" id="{id}" title="{title}">' +
								'<span class="lfr-icon-menu-text">{title}</span> <i class="icon-caret-down"></i>' +
							'</a>' +
						'</div>' +
					'</div>',

					TPL_ITEM_CONTAINER: '<ul class="direction-down dropdown-menu lfr-menu-list" id="{id}" role="menu" />',

					TPL_ITEM: '<li id="{idLi}" role="presentation">' +
						'<a href="javascript:;" class="lfr-pagination-link taglib-icon" id="{idLink}" role="menuitem">' +
							'<span class="taglib-text-icon" data-index="{index}" data-value="{value}">{value}</span>' +
						'</a>' +
					'</li>',

					TPL_LABEL: '{x} {items} {per} {page}',

					TPL_RESULTS: '<small class="search-results" id="{id}">{value}</small>',

					TPL_RESULTS_MESSAGE: '{showing} {from} - {to} {of} {x} {results}.',

					TPL_RESULTS_MESSAGE_SHORT: '{showing} {x} {results}.',

					renderUI: function() {
						var instance = this;

						Pagination.superclass.renderUI.apply(instance, arguments);

						var boundingBox = instance.get('boundingBox');

						boundingBox.addClass('lfr-pagination');

						var namespace = instance.get('namespace');

						var deltaSelectorId = namespace + 'dataSelectorId';

						var deltaSelector = A.Node.create(
							Lang.sub(
								instance.TPL_DELTA_SELECTOR,
								{
									id: deltaSelectorId,
									title: instance._getLabelContent()
								}
							)
						);

						var itemContainer = A.Node.create(
							Lang.sub(
								instance.TPL_ITEM_CONTAINER,
								{
									id: namespace + 'itemContainerId'
								}
							)
						);

						var itemsContainer = A.Node.create(
							Lang.sub(
								instance.TPL_CONTAINER,
								{
									id: namespace + 'itemsContainer'
								}
							)
						);

						var searchResults = A.Node.create(
							Lang.sub(
								instance.TPL_RESULTS,
								{
									id: namespace + 'searchResultsId',
									value: instance._getResultsContent()
								}
							)
						);

						var buffer = AArray.map(
							instance.get(STR_ITEMS_PER_PAGE_LIST),
							function(item, index, collection) {
								return Lang.sub(
									instance.TPL_ITEM,
									{
										idLi: namespace + 'itemLiId' + index,
										idLink: namespace + 'itemLinkId' + index,
										index: index,
										value: item
									}
								);
							}
						);

						itemContainer.appendChild(buffer.join(''));

						deltaSelector.one('#' + deltaSelectorId).ancestor().appendChild(itemContainer);

						itemsContainer.appendChild(deltaSelector);
						itemsContainer.appendChild(searchResults);

						boundingBox.appendChild(itemsContainer);

						instance._deltaSelector = deltaSelector;
						instance._itemContainer = itemContainer;
						instance._itemsContainer = itemsContainer;
						instance._searchResults = searchResults;

						instance._paginationContentNode = boundingBox.one('.pagination-content');
						instance._paginationControls = boundingBox.one('.lfr-pagination-controls');

						instance._syncNavigationUI();

						Liferay.Menu.register(deltaSelectorId);
					},

					bindUI: function() {
						var instance = this;

						Pagination.superclass.bindUI.apply(instance, arguments);

						instance._eventHandles = [
							instance._itemContainer.delegate('click', instance._onItemClick, '.lfr-pagination-link', instance)
						];

						instance.after('resultsChange', instance._afterResultsChange, instance);
						instance.on('changeRequest', instance._onChangeRequest, instance);
						instance.on('itemsPerPageChange', instance._onItemsPerPageChange, instance);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_afterResultsChange: function(event) {
						var instance = this;

						instance._syncResults();
					},

					getItem: function(currentPage) {
						var instance = this;

						if (Lang.isNumber(currentPage)) {
							var items = instance.get(STR_ITEMS);

							if (items) {
								currentPage = items.item(currentPage + instance.get(STR_SHIFT));
							}
						}

						return currentPage;
					},

					_countPaginationControls: function(items) {
						var instance = this;

						var controlCount = 0;

						items.each(
							function(item, index) {
								if (item.hasClass(CSS_PAGINATION_CONTROL)) {
									controlCount++;
								}
							}
						);

						return controlCount;
					},

					_dispatchRequest: function(state) {
						var instance = this;

						if (!AObject.owns(state, STR_ITEMS_PER_PAGE)) {
							state.itemsPerPage = instance.get(STR_ITEMS_PER_PAGE);
						}

						Pagination.superclass._dispatchRequest.call(instance, state);
					},

					_getActiveNodeIndex: function(items) {
						var instance = this;

						var activeNodeIndex;

						items.each(
							function(item, index) {
								if (item.hasClass(CSS_ACTIVE)) {
									activeNodeIndex = items.indexOf(item) - instance.get(STR_SHIFT);
								}
							}
						);

						return activeNodeIndex;
					},

					_getLabelContent: function(itemsPerPage) {
						var instance = this;

						if (!itemsPerPage) {
							itemsPerPage = instance.get(STR_ITEMS_PER_PAGE);
						}

						var strings = instance.get(STR_STRINGS);

						return Lang.sub(
							instance.TPL_LABEL,
							{
								items: strings.items,
								page: strings.page,
								per: strings.per,
								x: itemsPerPage
							}
						);
					},

					_getResultsContent: function(page, itemsPerPage) {
						var instance = this;

						var results = instance.get(STR_RESULTS);
						var strings = instance.get(STR_STRINGS);

						if (!Lang.isValue(page)) {
							page = instance.get(STR_PAGE);
						}

						if (!Lang.isValue(itemsPerPage)) {
							itemsPerPage = instance.get(STR_ITEMS_PER_PAGE);
						}

						var tpl = instance.TPL_RESULTS_MESSAGE_SHORT;

						var values = {
							results: strings.results,
							showing: strings.showing,
							x: results
						};

						if (results > itemsPerPage) {
							var tmp = page * itemsPerPage;

							tpl = instance.TPL_RESULTS_MESSAGE;

							values.from = ((page - 1) * itemsPerPage) + 1;
							values.of = strings.of;
							values.to = (tmp < results) ? tmp : results;
						}

						return Lang.sub(tpl, values);
					},

					_goToPage: function(index, controlItem) {
						var instance = this;

						var items = instance.get(STR_ITEMS);

						var currentIndex = instance._getActiveNodeIndex(items);

						var numberOfPages = instance.get('numberOfPages');

						var modCurrentIndex = currentIndex % numberOfPages;

						var pagesStart = modCurrentIndex || numberOfPages;

						pagesStart -= 1;

						if (controlItem.hasClass('first')) {
							instance._dispatchRequest(
								{
									page: 1
								}
							);
						}
						else if (controlItem.hasClass('prev')) {
							instance.prev();
						}
						else if (controlItem.hasClass('prev-pages')) {
							instance._dispatchRequest(
								{
									page: (currentIndex - numberOfPages) - pagesStart
								}
							);
						}
						else if (controlItem.hasClass('next-pages')) {
							instance._dispatchRequest(
								{
									page: (currentIndex + numberOfPages) - pagesStart
								}
							);
						}
						else if (controlItem.hasClass('next')) {
							instance.next();
						}
						else if (controlItem.hasClass('last')) {
							instance._dispatchRequest(
								{
									page: (index - instance.TOTAL_CONTROLS) + 1
								}
							);
						}
						else {
							instance._dispatchRequest(
								{
									page: index - instance.get(STR_SHIFT)
								}
							);
						}
					},

					_onChangeRequest: function(event) {
						var instance = this;

						var state = event.state;

						var itemsPerPage = state.itemsPerPage;

						instance._syncLabel(itemsPerPage);

						instance._syncResults(state.page, itemsPerPage);
					},

					_onClickItem: function(event) {
						var instance = this;

						event.preventDefault();

						var currentTarget = event.currentTarget;

						var items = instance.get(STR_ITEMS);

						var index = items.indexOf(currentTarget);

						var lastIndex = items.size() - 1;

						var hasNoClass = (function() {
							var returnVal = true;

							if (currentTarget.hasClass(CSS_DISABLED) || currentTarget.hasClass(CSS_ACTIVE)) {
								returnVal = false;
							}

							return returnVal;
						})();

						if (hasNoClass) {
							var startRange = 2;

							var endRange = lastIndex - 2;

							if (index <= startRange || index >= endRange) {
								instance._goToPage(index, items.item(index));
							}
							else {
								instance._dispatchRequest(
									{
										page: index - instance.get(STR_SHIFT)
									}
								);
							}
						}
					},

					_onItemClick: function(event) {
						var instance = this;

						var textIcon = event.currentTarget.one('.taglib-text-icon');

						if (textIcon) {
							var dataValue = textIcon.attr('data-value');

							var itemsPerPage = Lang.toInt(dataValue);

							instance.set(STR_ITEMS_PER_PAGE, itemsPerPage);
						}
					},

					_onItemsPerPageChange: function(event) {
						var instance = this;

						var itemsPerPage = event.newVal;

						instance._dispatchRequest(
							{
								itemsPerPage: itemsPerPage,
								page: instance.get(STR_PAGE)
							}
						);
					},

					_renderItemsUI: function(total) {
						var instance = this;

						var TPL_ITEM_TEMPLATE = instance.ITEM_TEMPLATE;

						var buffer = '';

						var stringFirst = instance.getString('first');

						if (stringFirst) {
							buffer += Lang.sub(
								TPL_ITEM_TEMPLATE,
								{
									content: stringFirst,
									cssClass: 'first pagination-control'
								}
							);
						}

						var showControls = instance.get(STR_SHOW_CONTROLS);

						if (showControls) {
							var stringPrev = instance.getString('prev');

							if (stringPrev) {
								buffer += Lang.sub(
									TPL_ITEM_TEMPLATE,
									{
										content: stringPrev,
										cssClass: 'pagination-control prev'
									}
								);
							}
						}

						var stringPrevPages = instance.getString('prevPages');

						if (stringPrevPages) {
							buffer += Lang.sub(
								TPL_ITEM_TEMPLATE,
								{
									content: stringPrevPages,
									cssClass: 'pagination-control prev-pages'
								}
							);
						}

						var formatter = instance.get('formatter');
						var offset = instance.get('offset');

						var offsetEnd = (offset + total) -1;

						for (var i = offset; i <= offsetEnd; i++) {
							buffer += formatter.apply(instance, [i]);
						}

						var stringNextPages = instance.getString('nextPages');

						if (stringNextPages) {
							buffer += Lang.sub(
								TPL_ITEM_TEMPLATE,
								{
									content: stringNextPages,
									cssClass: 'next-pages pagination-control'
								}
							);
						}

						if (showControls) {
							var stringNext = instance.getString('next');

							if (stringNext) {
								buffer += Lang.sub(
									TPL_ITEM_TEMPLATE,
									{
										content: stringNext,
										cssClass: 'next pagination-control'
									}
								);
							}
						}

						var stringLast = instance.getString('last');

						if (stringLast) {
							buffer += Lang.sub(
								TPL_ITEM_TEMPLATE,
								{
									content: stringLast,
									cssClass: 'last pagination-control'
								}
							);
						}

						var items = A.NodeList.create(buffer);

						instance.TOTAL_CONTROLS = instance._countPaginationControls(items);

						if (!instance.get(STR_SHIFT)) {
							instance.set(STR_SHIFT, (instance.TOTAL_CONTROLS / 2) - 1);
						}

						instance.set(STR_ITEMS, items);

						instance.get('contentBox').setContent(items);
					},

					_syncLabel: function(itemsPerPage) {
						var instance = this;

						var result = instance._getLabelContent(itemsPerPage);

						instance._deltaSelector.one('.lfr-icon-menu-text').html(result);
					},

					_syncNavigationUI: function() {
						var instance = this;

						var items = instance.get(STR_ITEMS);

						if (items.size()) {
							var page = instance.get(STR_PAGE);
							var total = instance.get('total');

							var firstPage = page <= 1;
							var lastPage = page >= total;

							if (!instance.get('circular') && instance.get(STR_SHOW_CONTROLS)) {
								items.item(1).toggleClass(
									CSS_DISABLED,
									firstPage
								);

								var lastItemIndex = items.indexOf(items.last());

								var nextToLastItem = lastItemIndex - 1;

								items.item(nextToLastItem).toggleClass(
									CSS_DISABLED,
									lastPage
								);
							}

							items.first().toggleClass(
								CSS_DISABLED,
								firstPage
							);

							items.last().toggleClass(
								CSS_DISABLED,
								lastPage
							);
						}
					},

					_syncResults: function(page, itemsPerPage) {
						var instance = this;

						var result = instance._getResultsContent(page, itemsPerPage);

						instance._searchResults.html(result);
					},

					_uiSetPage: function(val) {
						var instance = this;

						if ((val !== 0) || (val !== instance.getTotalItems())) {
							var item = instance.getItem(val);

							if (item) {
								item.addClass(CSS_ACTIVE);
							}
						}

						instance._syncNavigationUI();
					},

					_uiSetVisible: function(val) {
						var instance = this;

						var hideClass = instance.get('hideClass');

						var hiddenClass = instance.getClassName('hidden');

						if (hideClass !== false) {
							hiddenClass += STR_SPACE + (hideClass || 'hide');
						}

						var results = instance.get(STR_RESULTS);
						var itemsPerPageList = instance.get(STR_ITEMS_PER_PAGE_LIST);

						instance._paginationControls.toggleClass(hiddenClass, (results <= itemsPerPageList[0]));

						instance._paginationContentNode.toggleClass(hiddenClass, !val);
					}
				}
			}
		);

		Liferay.Pagination = Pagination;
	},
	'',
	{
		requires: ['aui-pagination']
	}
);