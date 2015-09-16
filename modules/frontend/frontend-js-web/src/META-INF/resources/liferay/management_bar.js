AUI.add(
	'liferay-management-bar',
	function(A) {
		var Lang = A.Lang;

		var ATTR_CHECKED = 'checked';

		var STR_CHECKBOX_CONTAINER = 'checkBoxContainer';

		var STR_CHECKBOXES_SELECTOR = 'checkBoxesSelector';

		var STR_CLICK = 'click';

		var STR_SELECT_ALL_CHECKBOXES_SELECTOR = 'selectAllCheckBoxesSelector';

		var ManagementBar = A.Component.create(
			{
				ATTRS: {
					secondaryBar: {
						setter: '_setNode'
					},

					selectAllCheckBoxesSelector: {
						validator: Lang.isString,
						value: '.select-all-checkboxes'
					},

					checkBoxContainer: {
						setter: '_setNode'
					},

					checkBoxesSelector: {
						validator: Lang.isString,
						value: 'input[type=checkbox]'
					},

					itemsCountContainer: {
						setter: '_setNodes',
						value: '.selected-items-count'
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

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.get('rootNode').delegate(STR_CLICK, instance._toggleBars, instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR), instance),
							instance.get(STR_CHECKBOX_CONTAINER).delegate(STR_CLICK, instance._toggleSelect, instance.get(STR_CHECKBOXES_SELECTOR), instance)
						];
					},

					_getCheckBoxes: function() {
						var instance = this;

						if (!instance._checkBoxes) {
							instance._checkBoxes = instance.get(STR_CHECKBOX_CONTAINER).all(instance.get(STR_CHECKBOXES_SELECTOR));
						}

						return instance._checkBoxes;
					},

					_getSelectAllCheckBoxes: function() {
						var instance = this;

						if (!instance._selectAllCheckBoxes) {
							instance._selectAllCheckBoxes = instance.all(instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR));
						}

						return instance._selectAllCheckBoxes;
					},

					_getSelectedItemsCount: function() {
						var instance = this;

						return instance._getCheckBoxes().filter(':checked').size();
					},

					_printItemsCount: function(itemsCount) {
						var instance = this;

						instance.get('itemsCountContainer').html(itemsCount);
					},

					_setNode: function(val) {
						var instance = this;

						return instance.one(val);
					},

					_setNodes: function(val) {
						var instance = this;

						return instance.all(val);
					},

					_toggleBars: function(event) {
						var instance = this;

						var checked = event.currentTarget.attr(ATTR_CHECKED);

						instance._getCheckBoxes().attr(ATTR_CHECKED, checked);

						instance._toggleSecondaryBar(checked);

						instance._toggleSelectAllCheckBoxes(checked);

						instance._printItemsCount(instance._getSelectedItemsCount());
					},

					_toggleSecondaryBar: function(show) {
						var instance = this;

						instance.get('secondaryBar').toggleClass('on', show);
					},

					_toggleSelect: function() {
						var instance = this;

						var totalBoxes = instance._getCheckBoxes().size();

						var totalOn = instance._getSelectedItemsCount();

						instance._toggleSecondaryBar(totalOn > 0);

						instance._toggleSelectAllCheckBoxes(totalBoxes == totalOn);

						instance._printItemsCount(totalOn);
					},

					_toggleSelectAllCheckBoxes: function(checked) {
						var instance = this;

						instance._getSelectAllCheckBoxes().attr(ATTR_CHECKED, checked);
					}
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