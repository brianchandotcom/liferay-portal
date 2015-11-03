AUI.add(
	'liferay-management-bar',
	function(A) {
		var Lang = A.Lang;

		var ATTR_CHECKED = 'checked';

		var STR_CHECKBOX_CONTAINER = 'checkBoxContainer';

		var STR_CHECKBOXES_SELECTOR = 'checkBoxesSelector';

		var STR_CLICK = 'click';

		var STR_COLON = ':';

		var STR_CHECKED_SELECTOR = STR_COLON + ATTR_CHECKED;

		var STR_HASH = '#';

		var STR_ON = 'on';

		var STR_SELECT_ALL_CHECKBOXES_SELECTOR = 'selectAllCheckBoxesSelector';

		var STR_SELECTED_PARTIAL = 'selected-partial';

		var STR_VISIBLE_SELECTOR = STR_COLON + 'visible';

		var ManagementBar = A.Component.create(
			{
				ATTRS: {
					checkBoxContainer: {
						setter: 'one'
					},

					checkBoxesSelector: {
						validator: Lang.isString,
						value: 'input[type=checkbox]'
					},

					itemsCountContainer: {
						setter: 'all',
						value: '.selected-items-count'
					},

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
					},

					searchContainerId: {
						validator: Lang.isString
					},

					secondaryBar: {
						setter: 'one'
					},

					selectAllCheckBoxesSelector: {
						validator: Lang.isString,
						value: '.select-all-checkboxes'
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-management-bar',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_addSearchContainerEvents: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._searchContainer.on(
								'rowToggled',
								instance._onSearchContainerToggle,
								instance
							)
						);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.get('rootNode').delegate(STR_CLICK, instance._toggleSelectAll, instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR), instance),
							Liferay.on('surfaceStartNavigate', instance._onSurfaceStartNavigate, instance)
						];

						if (!instance._searchContainer) {
							instance._eventHandles.push(
								Liferay.on(
									'search-container:registered',
									function(event) {
										if (event.searchContainer.get('id') === instance.get('searchContainerId')) {
											instance._searchContainer = event.searchContainer;
											instance._addSearchContainerEvents();
										}
									}
								)
							);
						}
						else {
							instance._addSearchContainerEvents();
						}
					},

					_getSelectAllCheckBox: function() {
						var instance = this;

						var selectAllCheckBox = instance._selectAllCheckBox;

						if (!selectAllCheckBox) {
							selectAllCheckBox = instance.get('secondaryBar').one(instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR));

							instance._selectAllCheckBox = selectAllCheckBox;
						}

						return selectAllCheckBox;
					},

					_onSearchContainerToggle: function(event) {
						var instance = this;

						var elements = event.elements;

						var numberAllSelectedElements = elements.allSelectedElements.size();

						var numberCurrentPageSelectedElements = elements.currentPageSelectedElements.size();

						var numberCurrentPageElements = elements.currentPageElements.size();

						instance._updateItemsCount(numberAllSelectedElements);

						instance._toggleSelectAllCheckBox(numberCurrentPageSelectedElements > 0, numberCurrentPageSelectedElements < numberCurrentPageElements);

						instance._toggleSecondaryBar(numberAllSelectedElements > 0);
					},

					_onSurfaceStartNavigate: function(event) {
						var instance = this;

						Liferay.DOMTaskRunner.addTask(
							{
								action: Liferay.ManagementBar.restoreTask,
								condition: Liferay.ManagementBar.testRestoreTask,
								params: {
									checkBoxContainerId: instance.get('checkBoxContainer').attr('id'),
									checkBoxesSelector: instance.get('checkBoxesSelector'),
									itemsCountContainerSelector: instance.get('itemsCountContainer').attr('class'),
									secondaryBarId: instance.get('secondaryBar').attr('id'),
									selectAllCheckBoxesSelector: instance.get('selectAllCheckBoxesSelector')
								}
							}
						);
					},

					_toggleSecondaryBar: function(show) {
						var instance = this;

						instance.get('secondaryBar').toggleClass(STR_ON, show);
					},

					_toggleSelectAll: function(event) {
						var instance = this;

						if (!instance.get('secondaryBar').contains(event.currentTarget)) {
							event.preventDefault();
						}

						var checked = event.currentTarget.attr(ATTR_CHECKED);

						var totalSelected = instance._searchContainer.select.toggleAllRows(checked).size();

						instance._updateItemsCount(totalSelected);

						instance._toggleSelectAllCheckBox(checked, false);

						instance._toggleSecondaryBar(totalSelected > 0);
					},

					_toggleSelectAllCheckBox: function(checked, partial) {
						var instance = this;

						var selectAllCheckBox = instance._getSelectAllCheckBox();

						partial = partial && checked;

						selectAllCheckBox.attr(ATTR_CHECKED, checked);

						if (A.UA.gecko > 0 || A.UA.ie > 0) {
							selectAllCheckBox.attr('indeterminate', partial);
						}
						else {
							selectAllCheckBox.toggleClass(STR_SELECTED_PARTIAL, partial);
						}
					},

					_updateItemsCount: function(itemsCount) {
						var instance = this;

						instance.get('itemsCountContainer').html(itemsCount);
					}
				},

				restoreTask: function(state, params, node) {
					var totalSelectedItems = state.data.elements.length;

					var itemsCountContainer = node.all('.' + params.itemsCountContainerSelector);

					itemsCountContainer.html(totalSelectedItems);

					var secondaryBar = node.one(STR_HASH + params.secondaryBarId);

					if (totalSelectedItems > 0) {
						secondaryBar.addClass(STR_ON);
					}

					var checkBoxContainer = node.one(STR_HASH + params.checkBoxContainerId);

					var selectedElements = A.Array.partition(
						state.data.elements,
						function(item) {
							var valueSelector = '[value="' + item.value + '"]';

							return checkBoxContainer.one(params.checkBoxesSelector + valueSelector);
						}
					);

					var onscreenSelectedItems = selectedElements.matches.length;

					var checkBoxes = checkBoxContainer.all(params.checkBoxesSelector);

					var selectAllCheckBoxesCheckBox = secondaryBar.one(params.selectAllCheckBoxesSelector);

					selectAllCheckBoxesCheckBox.attr(ATTR_CHECKED, onscreenSelectedItems);

					if (onscreenSelectedItems !== checkBoxes.size()) {
						selectAllCheckBoxesCheckBox.addClass(STR_SELECTED_PARTIAL);
					}
				},

				testRestoreTask: function(state, params, node) {
					return node.one(STR_HASH + params.checkBoxContainerId);
				}
			}
		);

		Liferay.ManagementBar = ManagementBar;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base']
	}
);