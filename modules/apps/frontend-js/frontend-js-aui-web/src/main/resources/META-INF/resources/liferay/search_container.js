/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-container',
	(A) => {
		const Lang = A.Lang;

		const CSS_TEMPLATE = 'd-none';

		const STR_BLANK = '';

		const STR_BOUNDING_BOX = 'boundingBox';

		const SearchContainer = A.Component.create({
			_cache: {},

			ATTRS: {
				id: {
					value: STR_BLANK,
				},
			},

			NAME: 'searchcontainer',

			// NOTE: Do not convert the constructor to an object concise method.
			//
			// See: https://stackoverflow.com/a/45119651/2103996

			constructor: function constructor(config) {
				const id = config.id;

				config.boundingBox = config.boundingBox || '#' + id;
				config.contentBox =
					config.contentBox || '#' + config.id + 'SearchContainer';

				SearchContainer.superclass.constructor.apply(this, arguments);
			},

			get(id) {
				const instance = this;

				let searchContainer = null;

				if (instance._cache[id]) {
					searchContainer = instance._cache[id];
				}
				else {
					searchContainer = new SearchContainer({
						id,
					}).render();
				}

				return searchContainer;
			},

			prototype: {
				_addRow() {
					const instance = this;

					instance._parentContainer.show();

					if (instance._emptyResultsMessage) {
						instance._emptyResultsMessage.hide();
					}
				},

				_deleteRow() {
					const instance = this;

					let action = 'show';

					if (!instance._ids.length) {
						action = 'hide';

						if (instance._emptyResultsMessage) {
							instance._emptyResultsMessage.show();
						}
					}

					instance._parentContainer[action]();
				},

				addRow(array, id, columnsCssClasses) {
					const instance = this;

					let row;

					if (id) {
						const template = instance._table.one(
							'.' + CSS_TEMPLATE
						);

						if (template) {
							row = template.previous()
								? template.previous().clone()
								: template.clone();

							const cells = row.all('> td');

							cells.empty();

							array.forEach((item, index) => {
								const cell = cells.item(index);

								if (cell) {
									cell.html(item);
									if (
										columnsCssClasses &&
										columnsCssClasses[index]
									) {
										cell.addClass(columnsCssClasses[index]);
									}
								}
							});

							template.placeBefore(row);

							row.removeClass(CSS_TEMPLATE);

							row.attr('id', instance.get('id') + '_' + id);

							instance._ids.push(id);
						}

						instance.updateDataStore();

						instance.fire('addRow', {
							id,
							ids: instance._ids,
							row,
							rowData: array,
						});
					}

					return row;
				},

				bindUI() {
					const instance = this;

					instance.publish('addRow', {
						defaultFn: instance._addRow,
					});

					instance.publish('deleteRow', {
						defaultFn: instance._deleteRow,
					});
				},

				deleteRow(object, id) {
					const instance = this;

					if (Lang.isNumber(object) || Lang.isString(object)) {
						let row = null;

						instance._table.all('tr').some((item, index) => {
							if (
								!item.hasClass(CSS_TEMPLATE) &&
								index === object
							) {
								row = item;
							}

							return row;
						});

						object = row;
					}
					else {
						object = A.one(object);
					}

					if (id) {
						const index = instance._ids.indexOf(id.toString());

						if (index > -1) {
							instance._ids.splice(index, 1);

							instance.updateDataStore();
						}
					}

					instance.fire('deleteRow', {
						id,
						ids: instance._ids,
						row: object,
					});

					if (object) {
						if (object.get('nodeName').toLowerCase() !== 'tr') {
							object = object.ancestor('tr');
						}

						// LPS-83031

						setTimeout(() => {
							object.remove(true);
						}, 0);
					}
				},

				executeAction(name, params) {
					const instance = this;

					if (instance._actions[name]) {
						instance._actions[name](params);
					}
				},

				getData(toArray) {
					const instance = this;

					let ids = instance._ids;

					if (!toArray) {
						ids = ids.join(',');
					}

					return ids;
				},

				getForm() {
					const instance = this;

					return instance.get(STR_BOUNDING_BOX).ancestor('form');
				},

				getSize() {
					const instance = this;

					return instance._ids.length;
				},

				initializer() {
					const instance = this;

					instance._ids = [];

					instance._actions = {};

					SearchContainer.register(instance);
				},

				registerAction(name, fn) {
					const instance = this;

					instance._actions[name] = fn;
				},

				renderUI() {
					const instance = this;

					const id = instance.get('id');

					const boundingBox = instance.get(STR_BOUNDING_BOX);

					instance._dataStore = A.one('#' + id + 'PrimaryKeys');
					instance._emptyResultsMessage = A.one(
						'#' + id + 'EmptyResultsMessage'
					);

					if (instance._dataStore) {
						const dataStoreForm = instance._dataStore.attr('form');

						if (dataStoreForm) {
							const method = dataStoreForm
								.attr('method')
								.toLowerCase();

							if (method && method === 'get') {
								instance._dataStore = null;
							}
						}
					}

					instance._table = boundingBox.one('table');
					instance._parentContainer = boundingBox.ancestor(
						'.lfr-search-container-wrapper'
					);

					if (instance._table) {
						instance._table.setAttribute(
							'data-searchContainerId',
							id
						);
					}
				},

				syncUI() {
					const instance = this;

					const dataStore = instance._dataStore;

					let initialIds = dataStore && dataStore.val();

					if (initialIds) {
						initialIds = initialIds.split(',');

						instance.updateDataStore(initialIds);
					}
				},

				updateDataStore(ids) {
					const instance = this;

					if (ids) {
						if (typeof ids === 'string') {
							ids = ids.split(',');
						}

						instance._ids = ids;
					}

					const dataStore = instance._dataStore;

					if (dataStore) {
						dataStore.val(instance._ids.join(','));
					}
				},
			},

			register(object) {
				const instance = this;

				const id = object.get('id');

				instance._cache[id] = object;

				Liferay.component(id, object, {
					destroyOnNavigate: true,
				});

				Liferay.fire('search-container:registered', {
					searchContainer: object,
				});
			},
		});

		Liferay.SearchContainer = SearchContainer;
	},
	'',
	{
		requires: ['aui-base', 'aui-component'],
	}
);
