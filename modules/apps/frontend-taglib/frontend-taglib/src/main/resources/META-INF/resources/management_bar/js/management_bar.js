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
	(A) => {
		const Lang = A.Lang;

		const ATTR_CHECKED = 'checked';

		const STR_CLICK = 'click';

		const STR_HASH = '#';

		const STR_SECONDARY_BAR_OPEN = 'secondary-bar-open';

		const STR_SELECT_ALL_CHECKBOXES_SELECTOR =
			'selectAllCheckBoxesSelector';

		const ManagementBar = A.Component.create({
			ATTRS: {
				checkBoxesSelector: {
					validator: Lang.isString,
					value: 'input[type=checkbox]',
				},

				itemsCountContainer: {
					setter: 'all',
					value: '.selected-items-count',
				},

				searchContainerId: {
					validator: Lang.isString,
				},

				secondaryBar: {
					setter: 'one',
				},

				selectAllCheckBoxesSelector: {
					validator: Lang.isString,
					value: '.select-all-checkboxes',
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'liferay-management-bar',

			prototype: {
				_bindUI() {
					const instance = this;

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
						),
					];
				},

				_detachSearchContainerRegisterHandle() {
					const instance = this;

					const searchContainerRegisterHandle =
						instance._searchContainerRegisterHandle;

					if (searchContainerRegisterHandle) {
						searchContainerRegisterHandle.detach();

						instance._searchContainerRegisterHandle = null;
					}
				},

				_getSelectAllCheckBox() {
					const instance = this;

					let selectAllCheckBox = instance._selectAllCheckBox;

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
					const instance = this;

					const searchContainer = event.searchContainer;

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
					const instance = this;

					const elements = event.elements;

					const numberAllSelectedElements = elements.allSelectedElements
						.filter(':enabled')
						.size();

					const numberCurrentPageSelectedElements = elements.currentPageSelectedElements
						.filter(':enabled')
						.size();

					const numberCurrentPageElements = elements.currentPageElements
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
					const instance = this;

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
							),
						},
					});
				},

				_toggleSecondaryBar(show) {
					const instance = this;

					const managementBarContainer = instance
						.get('secondaryBar')
						.ancestor('.management-bar-container');

					managementBarContainer.toggleClass(
						STR_SECONDARY_BAR_OPEN,
						show
					);
				},

				_toggleSelectAll(event) {
					const instance = this;

					if (
						!instance
							.get('secondaryBar')
							.contains(event.currentTarget)
					) {
						event.preventDefault();
					}

					const searchContainer = instance._searchContainer;

					if (searchContainer.hasPlugin('select')) {
						const checked = event.currentTarget.attr(ATTR_CHECKED);

						searchContainer.select.toggleAllRows(checked);
					}
				},

				_toggleSelectAllCheckBox(checked, partial) {
					const instance = this;

					const selectAllCheckBox = instance._getSelectAllCheckBox();

					if (selectAllCheckBox) {
						selectAllCheckBox.attr(ATTR_CHECKED, checked);
						selectAllCheckBox.attr(
							'indeterminate',
							partial && checked
						);
					}
				},

				_updateItemsCount(itemsCount) {
					const instance = this;

					instance.get('itemsCountContainer').html(itemsCount);
				},

				destructor() {
					const instance = this;

					instance._detachSearchContainerRegisterHandle();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._searchContainerRegisterHandle = Liferay.on(
						'search-container:registered',
						instance._onSearchContainerRegistered,
						instance
					);
				},
			},

			restoreTask(state, params, node) {
				const totalSelectedItems = state.data.elements.length;

				node = A.one(node);

				if (node) {
					const itemsCountContainer = node.all(
						'.' + params.itemsCountContainerSelector
					);

					itemsCountContainer.html(totalSelectedItems);

					const secondaryBar = node.one(
						STR_HASH + params.secondaryBarId
					);

					const managementBarContainer = secondaryBar.ancestor(
						'.management-bar-container'
					);

					if (secondaryBar && totalSelectedItems > 0) {
						managementBarContainer.addClass(STR_SECONDARY_BAR_OPEN);
					}

					const searchContainerNode = node.one(
						STR_HASH + params.searchContainerNodeId
					);

					if (searchContainerNode) {
						const selectedElements = A.Array.partition(
							state.data.elements,
							(item) => {
								const valueSelector =
									'[value="' + item.value + '"]';

								return searchContainerNode.one(
									params.checkBoxesSelector + valueSelector
								);
							}
						);

						const onscreenSelectedItems =
							selectedElements.matches.length;

						const checkBoxes = searchContainerNode.all(
							params.checkBoxesSelector
						);

						if (secondaryBar) {
							const selectAllCheckBoxesCheckBox = secondaryBar.one(
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

				let returnNode;

				const currentNode = A.one(node);

				if (currentNode) {
					returnNode = currentNode.one(
						STR_HASH + params.searchContainerNodeId
					);
				}

				return returnNode;
			},
		});

		Liferay.ManagementBar = ManagementBar;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base'],
	}
);
