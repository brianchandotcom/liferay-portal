AUI.add(
	'liferay-search-container',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CSS_TEMPLATE = 'lfr-template';

		var STR_BLANK = '';

		var TPL_HIDDEN_INPUT = '<input class="hide" name="{name}" value="{value}" type="checkbox" checked />';

		var TPL_INPUT_SELECTOR = 'input[type="checkbox"][value="{value}"]';

		var SearchContainer = A.Component.create(
			{
				ATTRS: {
					classNameHover: {
						value: STR_BLANK
					},

					hover: {
						value: STR_BLANK
					},

					id: {
						value: STR_BLANK
					},

					rowClassNameActive: {
						validator: Lang.isString,
						value: 'active'
					},

					rowClassNameAlternate: {
						value: STR_BLANK
					},

					rowClassNameAlternateHover: {
						value: STR_BLANK
					},

					rowClassNameBody: {
						value: STR_BLANK
					},

					rowClassNameBodyHover: {
						value: STR_BLANK
					},

					rowSelector: {
						validator: Lang.isString,
						value: 'li.selectable,tr.selectable'
					}
				},

				NAME: 'searchcontainer',

				constructor: function(config) {
					var id = config.id;

					config.boundingBox = config.boundingBox || '#' + id;
					config.contentBox = config.contentBox || '#' + config.id + 'SearchContainer';

					SearchContainer.superclass.constructor.apply(this, arguments);
				},

				get: function(id) {
					var instance = this;

					var searchContainer = null;

					if (instance._cache[id]) {
						searchContainer = instance._cache[id];
					}
					else {
						searchContainer = new SearchContainer(
							{
								id: id
							}
						).render();
					}

					return searchContainer;
				},

				prototype: {
					initializer: function() {
						var instance = this;

						instance._ids = [];
					},

					renderUI: function() {
						var instance = this;

						var id = instance.get('id');

						var contentBox = instance.get('contentBox');

						instance._dataStore = A.one('#' + id + 'PrimaryKeys');
						instance._emptyResultsMessage = A.one('#' + id + 'EmptyResultsMessage');

						if (instance._dataStore) {
							var dataStoreForm = instance._dataStore.attr('form');

							if (dataStoreForm) {
								var method = dataStoreForm.attr('method').toLowerCase();

								if (method && method == 'get') {
									instance._dataStore = null;
								}
							}
						}

						instance._table = contentBox.one('table');
						instance._parentContainer = contentBox.ancestor('.lfr-search-container');

						if (instance._table) {
							instance._table.setAttribute('data-searchContainerId', id);

							SearchContainer.register(instance);
						}
					},

					bindUI: function() {
						var instance = this;

						instance.publish(
							'addRow',
							{
								defaultFn: instance._addRow
							}
						);

						instance.publish(
							'deleteRow',
							{
								defaultFn: instance._deleteRow
							}
						);

						instance._eventHandles = [
							Liferay.on('surfaceStartNavigate', instance._onSurfaceStartNavigate, instance),
							instance.get('contentBox').delegate('click', instance._toggleSelect, 'input[type=checkbox]', instance),
							instance.get('contentBox').delegate('click', instance._toggleExtraSelect, '.click-selector', instance)
						];

						if (instance.get('hover')) {
							instance._eventHandles.push(instance.get('contentBox').delegate(['mouseenter', 'mouseleave'], instance._onContentHover, 'tr', instance));
						}
					},

					syncUI: function() {
						var instance = this;

						var dataStore = instance._dataStore;

						var initialIds = dataStore && dataStore.val();

						if (initialIds) {
							initialIds = initialIds.split(',');

							instance.updateDataStore(initialIds);
						}
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					addRow: function(arr, id) {
						var instance = this;

						var row;

						if (id) {
							var template = instance._table.one('.' + CSS_TEMPLATE);

							if (template) {
								row = template.clone();

								var cells = row.all('> td');

								cells.empty();

								arr.forEach(
									function(item, index) {
										var cell = cells.item(index);

										if (cell) {
											cell.html(item);
										}
									}
								);

								template.placeBefore(row);

								row.removeClass(CSS_TEMPLATE);

								instance._ids.push(id);
							}

							instance.updateDataStore();

							instance.fire(
								'addRow',
								{
									id: id,
									ids: instance._ids,
									row: row,
									rowData: arr
								}
							);
						}

						return row;
					},

					deleteRow: function(obj, id) {
						var instance = this;

						if (Lang.isNumber(obj) || Lang.isString(obj)) {
							var row = null;

							instance._table.all('tr').some(
								function(item, index) {
									if (!item.hasClass(CSS_TEMPLATE) && index == obj) {
										row = item;
									}

									return row;
								}
							);

							obj = row;
						}
						else {
							obj = A.one(obj);
						}

						if (id) {
							var index = instance._ids.indexOf(id.toString());

							if (index > -1) {
								instance._ids.splice(index, 1);

								instance.updateDataStore();
							}
						}

						instance.fire(
							'deleteRow',
							{
								id: id,
								ids: instance._ids,
								row: obj
							}
						);

						if (obj) {
							if (obj.get('nodeName').toLowerCase() !== 'tr') {
								obj = obj.ancestor('tr');
							}

							obj.remove(true);
						}
					},

					getData: function(toArray) {
						var instance = this;

						var ids = instance._ids;

						if (!toArray) {
							ids = ids.join(',');
						}

						return ids;
					},

					getSize: function() {
						var instance = this;

						return instance._ids.length;
					},

					updateDataStore: function(ids) {
						var instance = this;

						if (ids) {
							if (typeof ids == 'string') {
								ids = ids.split(',');
							}

							instance._ids = ids;
						}

						var dataStore = instance._dataStore;

						if (dataStore) {
							dataStore.val(instance._ids.join(','));
						}
					},

					_addRestoreTask: function() {
						var instance = this;

						Liferay.DOMTaskRunner.addTask(
							{
								action: Liferay.SearchContainer.restoreTask,
								condition: Liferay.SearchContainer.testRestoreTask,
								params: {
									containerId: instance.get('contentBox').attr('id'),
									rowClassNameActive: instance.get('rowClassNameActive'),
									rowSelector: instance.get('rowSelector'),
									searchContainerId: instance.get('id')
								}
							}
						);
					},

					_addRestoreTaskState: function() {
						var instance = this;

						var elements = [];

						var checkedCheckBoxes = instance.get('contentBox').all('input:checked');

						checkedCheckBoxes.each(
							function(item, index) {
								elements.push(
									{
										name: item.attr('name'),
										value: item.val()
									}
								);
							}
						);

						Liferay.DOMTaskRunner.addTaskState(
							{
								data: {
									elements: elements
								},
								owner: instance.get('id')
							}
						);
					},

					_addRow: function(event) {
						var instance = this;

						instance._parentContainer.show();

						if (instance._emptyResultsMessage) {
							instance._emptyResultsMessage.hide();
						}
					},

					_deleteRow: function(event) {
						var instance = this;

						var action = 'show';

						if (instance._ids.length == 0) {
							action = 'hide';

							if (instance._emptyResultsMessage) {
								instance._emptyResultsMessage.show();
							}
						}

						instance._parentContainer[action]();
					},

					_onContentHover: function(event) {
						var instance = this;

						var mouseenter = event.type == 'mouseenter';
						var row = event.currentTarget;

						var endAlternate = instance.get('rowClassNameAlternateHover');
						var endBody = instance.get('rowClassNameAlternateHover');
						var startAlternate = instance.get('rowClassNameAlternate');
						var startBody = instance.get('rowClassNameBody');

						if (mouseenter) {
							endAlternate = instance.get('rowClassNameAlternate');
							endBody = instance.get('rowClassNameBody');
							startAlternate = instance.get('rowClassNameAlternateHover');
							startBody = instance.get('rowClassNameBodyHover');
						}

						if (row.hasClass(startAlternate)) {
							row.replaceClass(startAlternate, endAlternate);
						}
						else if (row.hasClass(startBody)) {
							row.replaceClass(startBody, endBody);
						}

						row.toggleClass(instance.get('classNameHover'), mouseenter);
					},

					_onSurfaceStartNavigate: function(event) {
						var instance = this;

						instance._addRestoreTask();
						instance._addRestoreTaskState();
					},

					_toggleActiveElement: function(element) {
						var instance = this;

						element.toggleClass(instance.get('rowClassNameActive'));
					},

					_toggleExtraSelect: function(event) {
						var instance = this;

						var element = event.currentTarget.ancestor('li,tr');

						var checkbox = element.one('input[type=checkbox]');

						checkbox.attr('checked', !checkbox.attr('checked'));

						instance._toggleActiveElement(element);
					},

					_toggleSelect: function(event) {
						var instance = this;

						instance._toggleActiveElement(event.currentTarget.ancestor(instance.get('rowSelector')));
					}
				},

				register: function(obj) {
					var instance = this;

					var id = obj.get('id');

					instance._cache[id] = obj;
				},

				restoreTask: function(state, params, node) {
					var container = node.one('#' + params.containerId);

					var offScreenElementsHtml = '';

					AArray.each(
						state.data.elements,
						function(item) {
							var input = container.one(Lang.sub(TPL_INPUT_SELECTOR, item));

							if (input) {
								input.attr('checked', true);
								input.ancestor(params.rowSelector).addClass(params.rowClassNameActive);
							}
							else {
								offScreenElementsHtml += Lang.sub(TPL_HIDDEN_INPUT, item);
							}
						}
					);

					container.append(offScreenElementsHtml);
				},

				testRestoreTask: function(state, params, node) {
					return state.owner === params.searchContainerId && node.one('#' + params.containerId);
				},

				_cache: {}
			}
		);

		Liferay.SearchContainer = SearchContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'event-mouseenter']
	}
);