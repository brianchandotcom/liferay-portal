AUI.add(
	'liferay-search-container',
	function(A) {
		var Lang = A.Lang;

		var CSS_TEMPLATE = 'lfr-template';

		var STR_BLANK = '';

		var STR_BOUNDING_BOX = 'boundingBox';

		var SearchContainer = A.Component.create(
			{
				ATTRS: {
					id: {
						value: STR_BLANK
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

						instance._actions = {};

						SearchContainer.register(instance);
					},

					renderUI: function() {
						var instance = this;

						var id = instance.get('id');

						var boundingBox = instance.get(STR_BOUNDING_BOX);

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

						instance._table = boundingBox.one('table');
						instance._parentContainer = boundingBox.ancestor();

						if (instance._table) {
							instance._table.setAttribute('data-searchContainerId', id);
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

					executeAction: function(name, params) {
						var instance = this;

						if (instance._actions[name]) {
							instance._actions[name](params);
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

					getForm: function() {
						var instance = this;

						return instance.get(STR_BOUNDING_BOX).ancestor('form');
					},

					getSize: function() {
						var instance = this;

						return instance._ids.length;
					},

					registerAction: function(name, fn) {
						var instance = this;

						instance._actions[name] = fn;
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
					}
				},

				register: function(obj) {
					var instance = this;

					var id = obj.get('id');

					instance._cache[id] = obj;

					Liferay.fire(
						'search-container:registered',
						{
							searchContainer: obj
						}
					);
				},

				_cache: {}
			}
		);

		Liferay.SearchContainer = SearchContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component']
	}
);