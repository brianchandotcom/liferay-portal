AUI.add(
	'liferay-search-container',
	function(A) {
		var Lang = A.Lang;

		var CSS_TEMPLATE = 'lfr-template';

		var SearchContainer = A.Component.create(
			{
				NAME: 'searchcontainer',

				ATTRS: {
					classNameHover: {
						value: ''
					},
					hover: {
						value: ''
					},
					id: {
						value: ''
					},
					rowClassNameAlternate: {
						value: ''
					},
					rowClassNameAlternateHover: {
						value: ''
					},
					rowClassNameBody: {
						value: ''
					},
					rowClassNameBodyHover: {
						value: ''
					}
				},

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

						if (instance.get('hover')) {
							var classNameHover = instance.get('classNameHover');

							var rowClassNameAlternate = instance.get('rowClassNameAlternate');
							var rowClassNameAlternateHover = instance.get('rowClassNameAlternateHover');

							var rowClassNameBody = instance.get('rowClassNameBody');
							var rowClassNameBodyHover = instance.get('rowClassNameBodyHover');

							instance._hoverHandle = instance.get('contentBox').delegate(
								['mouseenter', 'mouseleave'],
								function(event) {
									var mouseenter = (event.type == 'mouseenter');
									var row = event.currentTarget;

									var endAlternate = rowClassNameAlternateHover;
									var endBody = rowClassNameAlternateHover;
									var startAlternate = rowClassNameAlternate;
									var startBody = rowClassNameBody;

									if (mouseenter) {
										endAlternate = rowClassNameAlternate;
										endBody = rowClassNameBody;
										startAlternate = rowClassNameAlternateHover;
										startBody = rowClassNameBodyHover;
									}

									if (row.hasClass(startAlternate)) {
										row.replaceClass(startAlternate, endAlternate);
									}
									else if (row.hasClass(startBody)) {
										row.replaceClass(startBody, endBody);
									}

									row.toggleClass(classNameHover, mouseenter);
								},
								'tr'
							);
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

					addRow: function(arr, id) {
						var instance = this;

						var row;

						if (id) {
							var template = instance._table.one('.' + CSS_TEMPLATE);

							if (template) {
								row = template.clone();

								var cells = row.all('> td');

								cells.empty();

								A.each(
									arr,
									function(item, index, collection) {
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
								function(item, index, collection) {
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
							var index = A.Array.indexOf(instance._ids, id.toString());

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
					},

					_deleteRow: function(event) {
						var instance = this;

						var action = 'hide';

						if (instance._ids.length) {
							action = 'show';
						}

						instance._parentContainer[action]();
					}
				},

				register: function(obj) {
					var instance = this;

					var id = obj.get('id');

					instance._cache[id] = obj;
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