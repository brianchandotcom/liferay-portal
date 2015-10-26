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

					rowClassNameActive: {
						validator: Lang.isString,
						value: 'active'
					},

					rowSelector: {
						validator: Lang.isString,
						value: 'li.selectable,tr.selectable'
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

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.get('rootNode').delegate(STR_CLICK, instance._toggleSelectAll, instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR), instance),
							instance.get(STR_CHECKBOX_CONTAINER).delegate(STR_CLICK, instance._toggleSelect, instance.get(STR_CHECKBOXES_SELECTOR), instance),
							Liferay.on('surfaceStartNavigate', instance._onSurfaceStartNavigate, instance)
						];
					},

					_getAllCheckedCheckBoxes: function() {
						var instance = this;

						return instance._getAllEnabledCheckBoxes().filter(STR_CHECKED_SELECTOR);
					},

					_getAllEnabledCheckBoxes: function() {
						var instance = this;

						var checkBoxes = instance._checkBoxes;

						if (!checkBoxes) {
							checkBoxes = instance.get(STR_CHECKBOX_CONTAINER).all(instance.get(STR_CHECKBOXES_SELECTOR) + ':enabled');

							instance._checkBoxes = checkBoxes;
						}

						return checkBoxes;
					},

					_getPageCheckBoxes: function() {
						var instance = this;

						return instance._getAllEnabledCheckBoxes().filter(STR_VISIBLE_SELECTOR);
					},

					_getPageCheckedCheckBoxes: function() {
						var instance = this;

						return instance._getAllEnabledCheckBoxes().filter(STR_CHECKED_SELECTOR + STR_VISIBLE_SELECTOR);
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

					_toggleSelect: function() {
						var instance = this;

						var totalPageCheckboxes = instance._getPageCheckBoxes().size();

						var totalSelectedItems = instance._getAllCheckedCheckBoxes().size();

						var totalPageOn = instance._getPageCheckedCheckBoxes().size();

						instance._toggleSecondaryBar(totalSelectedItems > 0);

						instance._toggleSelectAllCheckBox(totalPageOn > 0, totalPageCheckboxes !== totalPageOn);

						instance._updateItemsCount(totalSelectedItems);
					},

					_toggleSelectAll: function(event) {
						var instance = this;

						if (!instance.get('secondaryBar').contains(event.currentTarget)) {
							event.preventDefault();
						}

						var checked = event.currentTarget.attr(ATTR_CHECKED);

						instance._getPageCheckBoxes().attr(ATTR_CHECKED, checked);

						instance.get(STR_CHECKBOX_CONTAINER).all(instance.get('rowSelector')).toggleClass(instance.get('rowClassNameActive'), checked);

						instance._toggleSelect();
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

					var selectAllCheckBoxesCheckBox = node.all(params.selectAllCheckBoxesSelector);

					selectAllCheckBoxesCheckBox.attr(ATTR_CHECKED, onscreenSelectedItems);

					if (onscreenSelectedItems !== checkBoxes.size()) {
						selectAllCheckBoxesCheckBox.addClass(STR_SELECTED_PARTIAL);
					}

					var totalSelectedItems = onscreenSelectedItems + selectedElements.rejects.length;

					if (totalSelectedItems) {
						var itemsCountContainer = node.all('.' + params.itemsCountContainerSelector);
						var secondaryBar = node.one(STR_HASH + params.secondaryBarId);

						itemsCountContainer.html(totalSelectedItems);
						secondaryBar.addClass(STR_ON);
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
		requires: ['liferay-portlet-base']
	}
);