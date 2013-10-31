AUI.add(
	'liferay-pagination',
	function(A) {
		var Lang = A.Lang;
		var AArray = A.Array;
		var ANode = A.Node;
		var AObject = A.Object;

		var NAME = 'pagination';

		var BOUNDING_BOX = 'boundingBox';

		var ITEMS = 'items';

		var ITEMS_PER_PAGE = 'itemsPerPage';

		var ITEMS_PER_PAGE_LIST = 'itemsPerPageList';

		var PAGE = 'page';

		var RESULTS = 'results';

		var SELECTED_ITEM = 'selectedItem';

		var STRINGS = 'strings';

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

					results: {
						validator: Lang.isNumber,
						value: 0
					},

					selectedItem: {
						validator: Lang.isNumber,
						value: 0
					},

					strings: {
						setter: function(value) {
							return A.merge(
								value,
								{
									items: 'items',
									of: 'of',
									page: 'Page',
									per: 'per',
									results: 'results',
									showing: 'Showing'
								}
							);
						},
						validator: Lang.isObject
					}
				},

				EXTENDS: A.Pagination,

				NAME: NAME,

				prototype: {
					TPL_CONTAINER: '<div class="pull-left lfr-pagination lfr-pagination" id="{id}"></div>',

					TPL_DELTA_SELECTOR: '<div class="lfr-pagination-delta-selector">' +
						'<div class="btn-group lfr-icon-menu">' +
							'<a class="dropdown-toggle direction-down max-display-items-15 btn" href="javascript:;" id={id} title="{title}">' +
								'<span class="lfr-icon-menu-text">{title}</span>' +
								'<i class="caret"></i>' +
							'</a>' +
						'</div>' +
					'</div>',

					TPL_ITEM_CONTAINER: '<ul class="dropdown-menu lfr-menu-list direction-down" id="{id}" role="menu"></ul>',

					TPL_ITEM: '<li role="presentation" id="{idLi}">' +
						'<a href="javascript:;" class="taglib-icon lfr-pagination-link" id="{idLink}" role="menuitem">' +
							'<span class="taglib-text-icon" data-index="{index}" data-value="{value}"">{value}</span>' +
						'</a>' +
					'</li>',

					TPL_LABEL: '{x} {items} {per} {page}',

					TPL_RESULTS: '<small class="search-results" id="id">{value}</small>',

					TPL_RESULTS_MESSAGE: '{showing} {from} - {to} {of} {x} {results}.',

					TPL_RESULTS_MESSAGE_SHORT: '{showing} {x} {results}.',

					bindUI: function() {
						var instance = this;

						Pagination.superclass.bindUI.apply(instance, arguments);

						instance._eventHandles = [
							instance._itemContainer.delegate('click', instance._onItemClick, '.lfr-pagination-link', instance)
						];

						instance.on('itemsPerPageChange', instance._onItemsPerPageChange, instance);
						instance.on('changeRequest', instance._onChangeRequest, instance);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					renderUI: function() {
						var instance = this;

						Pagination.superclass.renderUI.apply(instance, arguments);

						var boundingBox = instance.get(BOUNDING_BOX);

						var namespace = instance.get('namespace');

						instance._itemsContainer = boundingBox.appendChild(
							Lang.sub(
								instance.TPL_CONTAINER,
								{
									id: namespace + 'itemsContainer'
								}
							)
						);

						var deltaSelectorId = namespace + 'dataSelectorId';

						instance._deltaSelector = instance._itemsContainer.appendChild(
							Lang.sub(
								instance.TPL_DELTA_SELECTOR,
								{
									id: deltaSelectorId,
									title: instance._getLabelContent()
								}
							)
						);

						instance._searchResults = instance._itemsContainer.appendChild(
							Lang.sub(
								instance.TPL_RESULTS,
								{
									id: namespace + 'searchResultsId',
									value: instance._getResultsContent()
								}
							)
						);

						instance._itemContainer = instance._deltaSelector.one('#' + deltaSelectorId).ancestor().appendChild(
							Lang.sub(
								instance.TPL_ITEM_CONTAINER,
								{
									id: namespace + 'itemContainerId'
								}
							)
						);

						AArray.each(
							instance.get(ITEMS_PER_PAGE_LIST),
							function(item, index, collection) {
								instance._itemContainer.appendChild(
									Lang.sub(
										instance.TPL_ITEM,
										{
											idLi: namespace + 'itemLiId' + index,
											idLink:  namespace + 'itemLinkId' + index,
											index: index,
											value: item
										}
									)
								);
							}
						);

						Liferay.Menu.register(deltaSelectorId);
					},

					_dispatchRequest: function(state) {
						var instance = this;

						if (!AObject.owns(state, ITEMS_PER_PAGE)) {
							state.itemsPerPage = instance.get(ITEMS_PER_PAGE);
						}

						Pagination.superclass._dispatchRequest.call(instance, state);
					},

					_getLabelContent: function(itemsPerPage) {
						var instance = this;

						var result;

						var reuslts = instance.get(RESULTS);

						var strings = instance.get(STRINGS);

						if (!itemsPerPage) {
							itemsPerPage = instance.get(ITEMS_PER_PAGE);
						}

						result = Lang.sub(
							instance.TPL_LABEL,
							{
								items: strings.items,
								page: strings.page,
								per: strings.per,
								x: itemsPerPage
							}
						);

						return result;
					},

					_getResultsContent: function(page, itemsPerPage) {
						var instance = this;

						var result;

						var results = instance.get(RESULTS);

						var strings = instance.get(STRINGS);

						page = Lang.isValue(page) ? page : instance.get(PAGE);

						itemsPerPage = Lang.isValue(itemsPerPage) ? itemsPerPage : instance.get(ITEMS_PER_PAGE);

						if (results > itemsPerPage) {
							var tmp = page * itemsPerPage;

							result = Lang.sub(
								instance.TPL_RESULTS_MESSAGE,
								{
									from: ((page - 1) * itemsPerPage) + 1,
									of: strings.of,
									results: strings.results,
									showing: strings.showing,
									to: tmp < results ? tmp : results,
									x: results
								}
							);
						} else {
							result = Lang.sub(
								instance.TPL_RESULTS_MESSAGE_SHORT,
								{
									results: strings.results,
									showing: strings.showing,
									x: results
								}
							);
						}

						return result;
					},

					_onChangeRequest: function(event) {
						var instance = this;

						var state = event.state;

						var page = state.page;

						var itemsPerPage = state.itemsPerPage;

						instance._syncLabel(itemsPerPage);

						instance._syncResults(page, itemsPerPage);
					},

					_onItemClick: function(event) {
						var instance = this;

						var itemsPerPage = Lang.toInt(event.currentTarget.one('.taglib-text-icon').attr('data-value'));

						instance.set(ITEMS_PER_PAGE, itemsPerPage);
					},

					_onItemsPerPageChange: function(event) {
						var instance = this;

						var page = instance.get(PAGE);

						var itemsPerPage = event.newVal;

						instance._dispatchRequest(
							{
								itemsPerPage: itemsPerPage,
								page: page
							}
						);
					},

					_syncLabel: function(itemsPerPage) {
						var instance = this;

						var result = instance._getLabelContent(itemsPerPage);

						instance._deltaSelector.one('.lfr-icon-menu-text').html(result);
					},

					_syncResults: function(page, itemsPerPage) {
						var instance = this;

						var result = instance._getResultsContent(page, itemsPerPage);

						instance._searchResults.html(result);
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