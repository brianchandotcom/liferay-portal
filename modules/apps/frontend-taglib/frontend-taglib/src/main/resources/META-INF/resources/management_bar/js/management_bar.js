/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-management-bar',
	A => {
		var Lang = A.Lang;

		var ATTR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_HASH = '#';

		var STR_SECONDARY_BAR_OPEN = 'secondary-bar-open';

		var STR_SELECT_ALL_CHECKBOXES_SELECTOR = 'selectAllCheckBoxesSelector';

		var ManagementBar = A.Component.create({
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
				_bindUI() {
					var instance = this;

					instance._eventHandles = [
						instance._searchContainer.on(
							'rowToggled',
							instance._onSearchContainerRowToggled,
							instance
						),
						instance
							.get('rootNode')
							.delegate(
								STR_CLICK,
								instance._toggleSelectAll,
								instance.get(
									STR_SELECT_ALL_CHECKBOXES_SELECTOR
								),
								instance
							),
						Liferay.on(
							'startNavigate',
							instance._onSurfaceStartNavigate,
							instance
						)
					];
				},

				_detachSearchContainerRegisterHandle() {
					var instance = this;

					var searchContainerRegisterHandle =
						instance._searchContainerRegisterHandle;

					if (searchContainerRegisterHandle) {
						searchContainerRegisterHandle.detach();

						instance._searchContainerRegisterHandle = null;
					}
				},

				_getSelectAllCheckBox() {
					var instance = this;

					var selectAllCheckBox = instance._selectAllCheckBox;

					if (!selectAllCheckBox) {
						selectAllCheckBox = instance
							.get('secondaryBar')
							.one(
								instance.get(STR_SELECT_ALL_CHECKBOXES_SELECTOR)
							);

						instance._selectAllCheckBox = selectAllCheckBox;
					}

					return selectAllCheckBox;
				},

				_onSearchContainerRegistered(event) {
					var instance = this;

					var searchContainer = event.searchContainer;

					if (
						searchContainer.get('id') ===
						instance.get('searchContainerId')
					) {
						instance._searchContainer = searchContainer;

						instance._detachSearchContainerRegisterHandle();

						instance._bindUI();
					}
				},

				_onSearchContainerRowToggled(event) {
					var instance = this;

					var elements = event.elements;

					var numberAllSelectedElements = elements.allSelectedElements
						.filter(':enabled')
						.size();

					var numberCurrentPageSelectedElements = elements.currentPageSelectedElements
						.filter(':enabled')
						.size();

					var numberCurrentPageElements = elements.currentPageElements
						.filter(':enabled')
						.size();

					instance._updateItemsCount(numberAllSelectedElements);

					instance._toggleSelectAllCheckBox(
						numberCurrentPageSelectedElements > 0,
						numberCurrentPageSelectedElements <
							numberCurrentPageElements
					);

					instance._toggleSecondaryBar(numberAllSelectedElements > 0);
				},

				_onSurfaceStartNavigate() {
					var instance = this;

					Liferay.DOMTaskRunner.addTask({
						action: Liferay.ManagementBar.restoreTask,
						condition: Liferay.ManagementBar.testRestoreTask,
						params: {
							checkBoxesSelector: instance.get(
								'checkBoxesSelector'
							),
							id: instance.get('id'),
							itemsCountContainerSelector: instance
								.get('itemsCountContainer')
								.attr('class'),
							searchContainerNodeId:
								instance.get('searchContainerId') +
								'SearchContainer',
							secondaryBarId: instance
								.get('secondaryBar')
								.attr('id'),
							selectAllCheckBoxesSelector: instance.get(
								'selectAllCheckBoxesSelector'
							)
						}
					});
				},

				_toggleSecondaryBar(show) {
					var instance = this;

					var managementBarContainer = instance
						.get('secondaryBar')
						.ancestor('.management-bar-container');

					managementBarContainer.toggleClass(
						STR_SECONDARY_BAR_OPEN,
						show
					);
				},

				_toggleSelectAll(event) {
					var instance = this;

					if (
						!instance
							.get('secondaryBar')
							.contains(event.currentTarget)
					) {
						event.preventDefault();
					}

					var searchContainer = instance._searchContainer;

					if (searchContainer.hasPlugin('select')) {
						var checked = event.currentTarget.attr(ATTR_CHECKED);

						searchContainer.select.toggleAllRows(checked);
					}
				},

				_toggleSelectAllCheckBox(checked, partial) {
					var instance = this;

					var selectAllCheckBox = instance._getSelectAllCheckBox();

					if (selectAllCheckBox) {
						selectAllCheckBox.attr(ATTR_CHECKED, checked);
						selectAllCheckBox.attr(
							'indeterminate',
							partial && checked
						);
					}
				},

				_updateItemsCount(itemsCount) {
					var instance = this;

					instance.get('itemsCountContainer').html(itemsCount);
				},

				destructor() {
					var instance = this;

					instance._detachSearchContainerRegisterHandle();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._searchContainerRegisterHandle = Liferay.on(
						'search-container:registered',
						instance._onSearchContainerRegistered,
						instance
					);
				}
			},

			restoreTask(state, params, node) {
				var totalSelectedItems = state.data.elements.length;

				node = A.one(node);

				if (node) {
					var itemsCountContainer = node.all(
						'.' + params.itemsCountContainerSelector
					);

					itemsCountContainer.html(totalSelectedItems);

					var secondaryBar = node.one(
						STR_HASH + params.secondaryBarId
					);

					var managementBarContainer = secondaryBar.ancestor(
						'.management-bar-container'
					);

					if (secondaryBar && totalSelectedItems > 0) {
						managementBarContainer.addClass(STR_SECONDARY_BAR_OPEN);
					}

					var searchContainerNode = node.one(
						STR_HASH + params.searchContainerNodeId
					);

					if (searchContainerNode) {
						var selectedElements = A.Array.partition(
							state.data.elements,
							item => {
								var valueSelector =
									'[value="' + item.value + '"]';

								return searchContainerNode.one(
									params.checkBoxesSelector + valueSelector
								);
							}
						);

						var onscreenSelectedItems =
							selectedElements.matches.length;

						var checkBoxes = searchContainerNode.all(
							params.checkBoxesSelector
						);

						if (secondaryBar) {
							var selectAllCheckBoxesCheckBox = secondaryBar.one(
								params.selectAllCheckBoxesSelector
							);

							if (selectAllCheckBoxesCheckBox) {
								selectAllCheckBoxesCheckBox.attr(
									ATTR_CHECKED,
									onscreenSelectedItems
								);

								if (
									onscreenSelectedItems !== checkBoxes.size()
								) {
									selectAllCheckBoxesCheckBox.attr(
										'indeterminate',
										true
									);
								}
							}
						}
					}
				}
			},

			testRestoreTask(state, params, node) {
				if (state.owner !== params.id) {
					return;
				}

				var returnNode;

				var currentNode = A.one(node);

				if (currentNode) {
					returnNode = currentNode.one(
						STR_HASH + params.searchContainerNodeId
					);
				}

				return returnNode;
			}
		});

		Liferay.ManagementBar = ManagementBar;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base']
	}
);
