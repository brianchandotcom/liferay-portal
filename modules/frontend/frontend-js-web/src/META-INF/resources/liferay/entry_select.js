AUI.add(
	'liferay-entry-select',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var ATTR_CHECKED = 'checked';

		var CSS_SELECTED_ITEMS_COUNT_SELECTOR = 'selected-items-count';

		var CSS_SELECT_ALL_CHECKBOXES = 'select-all-checkboxes';

		var STR_CLICK = 'click';

		var STR_DOT = '.';

		var EntrySelect = A.Component.create(
			{
				ATTRS: {
					actionsAllCheckBox: {
						validator: Lang.isString
					},

					actionButtonsBar: {
						validator: Lang.isString
					},

					allCheckBox: {
						validator: Lang.isString
					},

					checkBoxContainer: {
						validator: Lang.isString
					},

					itemsCountSelector: {
						validator: Lang.isString,
						value: CSS_SELECTED_ITEMS_COUNT_SELECTOR
					},

					managementContainer: {
						validator: Lang.isString
					},

					selectAllCheckBoxes: {
						validator: Lang.isString,
						value: CSS_SELECT_ALL_CHECKBOXES
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-entry-select',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._actionsAllCheckBox = instance.byId(instance.get('actionsAllCheckBox'));

						instance._actionButtonsBar = instance.byId(instance.get('actionButtonsBar'));

						instance._allCheckBox = instance.byId(instance.get('allCheckBox'));

						instance._checkBoxContainer = instance.byId(instance.get('checkBoxContainer'));

						instance._managementContainer = instance.byId(instance.get('managementContainer'));

						instance._managementContainer = instance.byId(instance.get('managementContainer'));

						instance._allCheckBoxes = instance._checkBoxContainer.all('input[type=checkbox]');

						instance._eventHandles = [];

						instance._initSelectAllCheckbox();

						instance._initToggleSelect();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._actionsAllCheckBox.on(STR_CLICK, A.rbind('_toogleBars', instance, false), instance),
							instance._allCheckBox.on(STR_CLICK, A.rbind('_toogleBars', instance, true), instance)
						);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._allCheckBoxes.on(STR_CLICK, instance._toogleSelect, instance)
						);
					},

					_getSelectedItemsCount: function() {
						var instance = this;

						var totalOn = 0;

						instance._allCheckBoxes.each(
							function(item, index) {
								if (item.attr(ATTR_CHECKED)) {
									totalOn++;
								}
							}
						);

						return totalOn;
					},

					_toogleSelect: function() {
						var instance = this;

						var hide = (Util.listCheckedExcept(instance._checkBoxContainer, instance._allCheckBox).length == 0);

						instance._actionButtonsBar.toggleClass('on', !hide);

						var totalBoxes = instance._allCheckBoxes.size();
						var totalOn = instance._getSelectedItemsCount();

						instance._managementContainer.all(STR_DOT + instance.get('selectAllCheckBoxes')).attr(ATTR_CHECKED, totalBoxes == totalOn);

						instance._managementContainer.all(STR_DOT + instance.get('itemsCountSelector')).html(totalOn);
					},

					_toogleBars: function(event, checked) {
						var instance = this;

						instance._allCheckBoxes.attr(ATTR_CHECKED, checked);

						instance._actionButtonsBar.toggleClass('on', checked);

						instance._managementContainer.all(STR_DOT + instance.get('selectAllCheckBoxes')).attr(ATTR_CHECKED, checked);

						instance._managementContainer.all(STR_DOT + instance.get('itemsCountSelector')).html(instance._getSelectedItemsCount());
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