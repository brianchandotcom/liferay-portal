AUI.add(
	'liferay-search-container-select',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var STR_CHECKBOX_SELECTOR = 'input[type=checkbox]';

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_HOST = 'host';

		var STR_ROW_CLASS_NAME_ACTIVE = 'rowClassNameActive';

		var STR_ROW_SELECTOR = 'rowSelector';

		var TPL_HIDDEN_INPUT = '<input class="hide" name="{name}" value="{value}" type="checkbox" ' + STR_CHECKED + ' />';

		var TPL_INPUT_SELECTOR = 'input[type="checkbox"][value="{value}"]';

		var SearchContainerSelect = A.Component.create(
			{
				ATTRS: {
					rowCheckerSelector: {
						validator: Lang.isString,
						value: '.click-selector'
					},

					rowClassNameActive: {
						validator: Lang.isString,
						value: 'active'
					},

					rowSelector: {
						validator: Lang.isString,
						value: 'li.selectable,tr.selectable'
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'searchcontainerselect',

				NS: 'select',

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var toggleRowFn = A.bind(
							'_onClickRowSelector',
							instance,
							{
								toggleCheckbox: true
							}
						);

						var toggleRowCSSFn = A.bind('_onClickRowSelector', instance, {});

						instance._eventHandles = [
							host.get(STR_CONTENT_BOX).delegate(STR_CLICK, toggleRowCSSFn, instance.get(STR_ROW_SELECTOR) + ' ' + STR_CHECKBOX_SELECTOR, instance),
							host.get(STR_CONTENT_BOX).delegate(STR_CLICK, toggleRowFn, instance.get(STR_ROW_SELECTOR) + ' ' + instance.get('rowCheckerSelector'), instance),
							Liferay.on('surfaceStartNavigate', instance._onSurfaceStartNavigate, instance)
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					toggleAllRows: function(selected) {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._getCurrentPageElements().attr(STR_CHECKED, selected);

						host.get(STR_CONTENT_BOX).all(instance.get(STR_ROW_SELECTOR)).toggleClass(instance.get(STR_ROW_CLASS_NAME_ACTIVE), selected);

						return instance._getAllSelectedElements();
					},

					toggleRow: function(config, row) {
						var instance = this;

						var host = instance.get(STR_HOST);

						if (config && config.toggleCheckbox) {
							var checkbox = row.one(STR_CHECKBOX_SELECTOR);

							checkbox.attr(STR_CHECKED, !checkbox.attr(STR_CHECKED));
						}

						row.toggleClass(instance.get(STR_ROW_CLASS_NAME_ACTIVE));

						host.fire(
							'rowToggled',
							{
								elements: {
									allElements: instance._getAllElements(),
									allSelectedElements: instance._getAllSelectedElements(),
									currentPageElements: instance._getCurrentPageElements(),
									currentPageSelectedElements: instance._getCurrentPageSelectedElements()
								}
							}
						);
					},

					_addRestoreTask: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						Liferay.DOMTaskRunner.addTask(
							{
								action: A.Plugin.SearchContainerSelect.restoreTask,
								condition: A.Plugin.SearchContainerSelect.testRestoreTask,
								params: {
									containerId: host.get(STR_CONTENT_BOX).attr('id'),
									rowClassNameActive: instance.get(STR_ROW_CLASS_NAME_ACTIVE),
									rowSelector: instance.get(STR_ROW_SELECTOR),
									searchContainerId: host.get('id')
								}
							}
						);
					},

					_addRestoreTaskState: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var elements = [];

						var selectedElements = instance._getAllSelectedElements();

						selectedElements.each(
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
								owner: host.get('id')
							}
						);
					},

					_getAllElements: function(onlySelected) {
						var instance = this;

						return instance._getElements(STR_CHECKBOX_SELECTOR, onlySelected);
					},

					_getAllSelectedElements: function() {
						var instance = this;

						return instance._getAllElements(true);
					},

					_getCurrentPageElements: function(onlySelected) {
						var instance = this;

						return instance._getElements(instance.get(STR_ROW_SELECTOR) + ' ' + STR_CHECKBOX_SELECTOR, onlySelected);
					},

					_getCurrentPageSelectedElements: function() {
						var instance = this;

						return instance._getCurrentPageElements(true);
					},

					_getElements: function(selector, onlySelected) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var checked = onlySelected ? ':' + STR_CHECKED : '';

						return host.get(STR_CONTENT_BOX).all(selector + checked);
					},

					_isSelected: function(element) {
						var instance = this;

						return element.one(STR_CHECKBOX_SELECTOR).attr(STR_CHECKED);
					},

					_onClickRowSelector: function(config, event) {
						var instance = this;

						var row = event.currentTarget.ancestor(instance.get(STR_ROW_SELECTOR));

						instance.toggleRow(config, row);
					},

					_onSurfaceStartNavigate: function(event) {
						var instance = this;

						instance._addRestoreTask();
						instance._addRestoreTaskState();
					}
				},

				restoreTask: function(state, params, node) {
					var container = node.one('#' + params.containerId);

					var offScreenElementsHtml = '';

					AArray.each(
						state.data.elements,
						function(item) {
							var input = container.one(Lang.sub(TPL_INPUT_SELECTOR, item));

							if (input) {
								input.attr(STR_CHECKED, true);
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
				}
			}
		);

		A.Plugin.SearchContainerSelect = SearchContainerSelect;
	},
	'',
	{
		requires: ['aui-component', 'plugin']
	}
);