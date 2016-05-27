AUI.add(
	'liferay-management-bar',
	function(A) {
		var Lang = A.Lang;

		var ATTR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_HASH = '#';

		var STR_ON = 'on';

		var STR_SELECT_ALL_CHECKBOXES_SELECTOR = 'selectAllCheckBoxesSelector';

		var ManagementBar = A.Component.create(
			{
				ATTRS: {
					checkBoxesSelector: {
						validator: Lang.isString,
						value: 'input[type=checkbox]'
					},

					itemsCountContainer: {
						setter: 'all',
						value: '.selected-items-count'
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

						instance._searchContainerRegisterHandle = Liferay.on('search-container:registered', instance._onSearchContainerRegistered, instance);
					},

					destructor: function() {
						var instance = this;

						instance._detachSearchContainerRegisterHandle();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance._searchContainer.on('rowToggled', instance._onSearchContainerRowToggled, instance),
							instance.get('rootNode').delegate(STR_CLICK, instance._toggleSelectAll, instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR), instance),
							Liferay.on('startNavigate', instance._onSurfaceStartNavigate, instance)
						];
					},

					_detachSearchContainerRegisterHandle: function() {
						var instance = this;

						var searchContainerRegisterHandle = instance._searchContainerRegisterHandle;

						if (searchContainerRegisterHandle) {
							searchContainerRegisterHandle.detach();

							instance._searchContainerRegisterHandle = null;
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

					_onSearchContainerRegistered: function(event) {
						var instance = this;

						var searchContainer = event.searchContainer;

						if (searchContainer.get('id') === instance.get('searchContainerId')) {
							instance._searchContainer = searchContainer;

							instance._detachSearchContainerRegisterHandle();

							instance._bindUI();
						}
					},

					_onSearchContainerRowToggled: function(event) {
						var instance = this;

						var elements = event.elements;

						var numberAllSelectedElements = elements.allSelectedElements.filter(':enabled').size();

						var numberCurrentPageSelectedElements = elements.currentPageSelectedElements.filter(':enabled').size();

						var numberCurrentPageElements = elements.currentPageElements.filter(':enabled').size();

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
									checkBoxesSelector: instance.get('checkBoxesSelector'),
									itemsCountContainerSelector: instance.get('itemsCountContainer').attr('class'),
									searchContainerNodeId: instance.get('searchContainerId') + 'SearchContainer',
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

						var searchContainer = instance._searchContainer;

						if (searchContainer.hasPlugin('select')) {
							var checked = event.currentTarget.attr(ATTR_CHECKED);

							searchContainer.select.toggleAllRows(checked);
						}
					},

					_toggleSelectAllCheckBox: function(checked, partial) {
						var instance = this;

						var selectAllCheckBox = instance._getSelectAllCheckBox();

						if (selectAllCheckBox) {
							selectAllCheckBox.attr(ATTR_CHECKED, checked);
							selectAllCheckBox.attr('indeterminate', partial && checked);
						}
					},

					_updateItemsCount: function(itemsCount) {
						var instance = this;

						instance.get('itemsCountContainer').html(itemsCount);
					}
				},

				restoreTask: function(state, params, node) {
					var totalSelectedItems = state.data.elements.length;

					node = A.one(node);

					if (node) {
						var itemsCountContainer = node.all('.' + params.itemsCountContainerSelector);

						itemsCountContainer.html(totalSelectedItems);

						var secondaryBar = node.one(STR_HASH + params.secondaryBarId);

						if (secondaryBar && totalSelectedItems > 0) {
							secondaryBar.addClass(STR_ON);
						}

						var searchContainerNode = node.one(STR_HASH + params.searchContainerNodeId);

						if (searchContainerNode) {
							var selectedElements = A.Array.partition(
								state.data.elements,
								function(item) {
									var valueSelector = '[value="' + item.value + '"]';

									return searchContainerNode.one(params.checkBoxesSelector + valueSelector);
								}
							);

							var onscreenSelectedItems = selectedElements.matches.length;

							var checkBoxes = searchContainerNode.all(params.checkBoxesSelector);

							if (secondaryBar) {
								var selectAllCheckBoxesCheckBox = secondaryBar.one(params.selectAllCheckBoxesSelector);

								if (selectAllCheckBoxesCheckBox) {
									selectAllCheckBoxesCheckBox.attr(ATTR_CHECKED, onscreenSelectedItems);

									if (onscreenSelectedItems !== checkBoxes.size()) {
										selectAllCheckBoxesCheckBox.attr('indeterminate', true);
									}
								}
							}
						}
					}
				},

				testRestoreTask: function(state, params, node) {
					var returnNode;

					var currentNode = A.one(node);

					if (currentNode) {
						returnNode = currentNode.one(STR_HASH + params.searchContainerNodeId);
					}

					return returnNode;
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