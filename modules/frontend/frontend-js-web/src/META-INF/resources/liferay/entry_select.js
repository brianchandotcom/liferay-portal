AUI.add(
	'liferay-entry-select',
	function(A) {
		var Lang = A.Lang;

		var ATTR_CHECKED = 'checked';

		var CSS_CHECKBOX_SELECTOR = 'input[type=checkbox]';

		var CSS_SELECT_ALL_CHECKBOXES_SELECTOR = '.select-all-checkboxes';

		var CSS_SELECTED_ITEMS_COUNT_CONTAINER_SELECTOR = '.selected-items-count';

		var STR_CLICK = 'click';

		var EntrySelect = A.Component.create(
			{
				ATTRS: {
					actionButtonsBar: {
						validator: Lang.isString
					},

					selectAllCheckBoxes: {
						validator: Lang.isString,
						value: CSS_SELECT_ALL_CHECKBOXES_SELECTOR
					},

					checkBoxContainer: {
						validator: Lang.isString
					},

					checkBoxes: {
						validator: Lang.isString,
						value: CSS_CHECKBOX_SELECTOR
					},

					itemsCountContainerSelector: {
						validator: Lang.isString,
						value: CSS_SELECTED_ITEMS_COUNT_CONTAINER_SELECTOR
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-entry-select',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._checkBoxContainer = instance.all(instance.get('checkBoxContainer'));

						instance._eventHandles = [];

						instance._itemsCountContainer = instance.all(instance.get('itemsCountContainerSelector'));

						instance._secondaryBar = instance.all(instance.get('actionButtonsBar'));

						instance._selectAllCheckBoxes = instance.all(instance.get('selectAllCheckBoxes'));

						instance._checkBoxes = instance._checkBoxContainer.all(instance.get('checkBoxes'));

						instance._initSelectAllCheckbox();

						instance._initToggleBars();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_getSelectedItemsCount: function() {
						var instance = this;

						var totalOn = 0;

						instance._checkBoxes.each(
							function(item, index) {
								if (item.attr(ATTR_CHECKED)) {
									totalOn++;
								}
							}
						);

						return totalOn;
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._selectAllCheckBoxes.on(STR_CLICK, instance._toggleBars, instance)
						);
					},

					_initToggleBars: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._checkBoxes.on(STR_CLICK, instance._toggleSelect, instance)
						);
					},

					_printItemsCount: function(itemsCount) {
						var instance = this;

						instance._itemsCountContainer.html(itemsCount);
					},

					_toggleBars: function(event) {
						var instance = this;

						var checked = event.currentTarget.attr(ATTR_CHECKED);

						instance._checkBoxes.attr(ATTR_CHECKED, checked);

						instance._toggleSecondaryBar(checked);

						instance._toggleSelectAllCheckBoxes(checked);

						instance._printItemsCount(instance._getSelectedItemsCount());
					},

					_toggleSecondaryBar: function(show) {
						var instance = this;

						instance._secondaryBar.toggleClass('on', show);
					},

					_toggleSelect: function() {
						var instance = this;

						var totalBoxes = instance._checkBoxes.size();
						var totalOn = instance._getSelectedItemsCount();

						instance._toggleSecondaryBar(totalOn > 0);

						instance._toggleSelectAllCheckBoxes(totalBoxes == totalOn);

						instance._printItemsCount(totalOn);
					},

					_toggleSelectAllCheckBoxes: function(checked) {
						var instance = this;

						instance._selectAllCheckBoxes.attr(ATTR_CHECKED, checked);
					}
				}
			}
		);

		Liferay.EntrySelect = EntrySelect;
	},
	'',
	{
		requires: ['liferay-portlet-base']
	}
);