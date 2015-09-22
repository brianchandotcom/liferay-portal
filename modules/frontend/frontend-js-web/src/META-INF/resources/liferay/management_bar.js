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

						var checkBoxes = instance._checkBoxes;

						if (!checkBoxes) {
							checkBoxes = instance.get(STR_CHECKBOX_CONTAINER).all(instance.get(STR_CHECKBOXES_SELECTOR));

							instance._checkBoxes = checkBoxes;
						}

						return checkBoxes;
					},

					_getSelectAllCheckBoxes: function() {
						var instance = this;

						var selectAllCheckBoxes = instance._selectAllCheckBoxes;

						if (!selectAllCheckBoxes) {
							selectAllCheckBoxes = instance.all(instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR));

							instance._selectAllCheckBoxes = selectAllCheckBoxes;
						}

						return selectAllCheckBoxes;
					},

					_getSelectedItemsCount: function() {
						var instance = this;

						return instance._getCheckBoxes().filter(':checked').size();
					},

					_toggleBars: function(event) {
						var instance = this;

						var checked = event.currentTarget.attr(ATTR_CHECKED);

						instance._getCheckBoxes().attr(ATTR_CHECKED, checked);

						instance._toggleSecondaryBar(checked);
						instance._toggleSelectAllCheckBoxes(checked);

						instance._updateItemsCount(instance._getSelectedItemsCount());
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

						instance._updateItemsCount(totalOn);
					},

					_toggleSelectAllCheckBoxes: function(checked) {
						var instance = this;

						instance._getSelectAllCheckBoxes().attr(ATTR_CHECKED, checked);
					},

					_updateItemsCount: function(itemsCount) {
						var instance = this;

						instance.get('itemsCountContainer').html(itemsCount);
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