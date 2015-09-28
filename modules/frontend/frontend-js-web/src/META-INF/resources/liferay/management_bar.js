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

		var STR_HIDE_SELECTOR = '.hide';

		var STR_NOT_SELECTOR = STR_COLON + 'not';

		var STR_ON = 'on';

		var STR_SELECT_ALL_CHECKBOXES_SELECTOR = 'selectAllCheckBoxesSelector';

		var STR_SELECTED_PARTIAL_SELECTOR = 'selected-partial';

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
							instance.get(STR_CHECKBOX_CONTAINER).delegate(STR_CLICK, instance._toggleSelect, instance.get(STR_CHECKBOXES_SELECTOR), instance),
							Liferay.on(
								'surfaceStartNavigate',
								function() {
									Liferay.DOMTaskRunner.addTask(
										restore,
										{
											checkBoxContainer: instance.get('checkBoxContainer').attr('id'),
											checkBoxesSelector: instance.get('checkBoxesSelector'),
											itemsCountContainerSelector: instance.get('itemsCountContainer').attr('class'),
											secondaryBar: instance.get('secondaryBar').attr('id'),
											selectAllCheckBoxesSelector: instance.get('selectAllCheckBoxesSelector')
										},
										function(data, params, frag) {
											if (frag.one(STR_HASH + params.checkBoxContainer)) {
												return true;
											}

											return false;
										}
									);
								}
							)
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
							checkBoxes = instance.get(STR_CHECKBOX_CONTAINER).all(instance.get(STR_CHECKBOXES_SELECTOR) + STR_NOT_SELECTOR + '([disabled])');

							instance._checkBoxes = checkBoxes;
						}

						return checkBoxes;
					},

					_getAllSelectedItemsCount: function() {
						var instance = this;

						return instance._getAllCheckedCheckBoxes().size();
					},

					_getPageCheckBoxes: function() {
						var instance = this;

						return instance._getAllEnabledCheckBoxes().filter(STR_NOT_SELECTOR + '(' + STR_HIDE_SELECTOR + ')');
					},

					_getPageCheckedCheckBoxes: function() {
						var instance = this;

						return instance._getAllEnabledCheckBoxes().filter(STR_CHECKED_SELECTOR + STR_NOT_SELECTOR + '(' + STR_HIDE_SELECTOR + ')');
					},

					_getPageSelectedItemsCount: function() {
						var instance = this;

						return instance._getPageCheckedCheckBoxes().size();
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

					_toggleBars: function(event) {
						var instance = this;

						instance._getPageCheckBoxes().attr(ATTR_CHECKED, event.currentTarget.attr(ATTR_CHECKED));

						instance._toggleSelect();
					},

					_toggleSecondaryBar: function(show) {
						var instance = this;

						instance.get('secondaryBar').toggleClass(STR_ON, show);
					},

					_toggleSelect: function() {
						var instance = this;

						var totalPageCheckboxes = instance._getPageCheckBoxes().size();

						var totalOn = instance._getAllSelectedItemsCount();

						var totalPageOn = instance._getPageSelectedItemsCount();

						instance._toggleSecondaryBar(totalOn > 0);

						instance._toggleSelectAllCheckBoxes(totalPageOn > 0, totalPageCheckboxes !== totalPageOn);

						instance._updateItemsCount(totalOn);
					},

					_toggleSelectAllCheckBoxes: function(checked, partial) {
						var instance = this;

						if (partial) {
							instance._getSelectAllCheckBoxes().addClass(STR_SELECTED_PARTIAL_SELECTOR);
						}
						else {
							instance._getSelectAllCheckBoxes().removeClass(STR_SELECTED_PARTIAL_SELECTOR);
						}

						instance._getSelectAllCheckBoxes().attr(ATTR_CHECKED, checked);
					},

					_updateItemsCount: function(itemsCount) {
						var instance = this;

						instance.get('itemsCountContainer').html(itemsCount);
					}
				}
			}
		);

		var restore = function(data, params, frag) {
			var checkBoxContainer = frag.one(STR_HASH + params.checkBoxContainer);

			var checkBoxes = checkBoxContainer.all(params.checkBoxesSelector);

			var elements = data.data.elements;

			var itemsCountContainer = frag.all('.' + params.itemsCountContainerSelector);

			var secondaryBar = frag.one(STR_HASH + params.secondaryBar);

			var selectAllCheckBoxes = frag.all(params.selectAllCheckBoxesSelector);

			var totalOn = 0;

			checkBoxes.each(
				function(item, index) {
					for (var i = 0; i < elements.length; i++) {
						if (item.val() === elements[i]) {
							totalOn++;
							break;
						}
					}
				}
			);

			if (totalOn === checkBoxes.size()) {
				selectAllCheckBoxes.attr(ATTR_CHECKED, true);
			}
			else if (totalOn > 0) {
				selectAllCheckBoxes.attr(ATTR_CHECKED, true);
				selectAllCheckBoxes.addClass(STR_SELECTED_PARTIAL_SELECTOR);
			}

			if (elements.length > 0) {
				itemsCountContainer.html(elements.length);
				secondaryBar.toggleClass(STR_ON, true);
			}
		};

		Liferay.ManagementBar = ManagementBar;
	},
	'',
	{
		requires: ['liferay-portlet-base']
	}
);