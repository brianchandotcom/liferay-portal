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
	'liferay-search-container-select',
	(A) => {
		const AArray = A.Array;
		const Lang = A.Lang;

		const REGEX_MATCH_EVERYTHING = /.*/;

		// eslint-disable-next-line no-empty-character-class
		const REGEX_MATCH_NOTHING = /^[]/;

		const STR_ACTIONS_WILDCARD = '*';

		const STR_CHECKBOX_SELECTOR = 'input[type=checkbox]:enabled';

		const STR_CHECKED = 'checked';

		const STR_CLICK = 'click';

		const STR_CONTENT_BOX = 'contentBox';

		const STR_HOST = 'host';

		const STR_ROW_CLASS_NAME_ACTIVE = 'rowClassNameActive';

		const STR_ROW_SELECTOR = 'rowSelector';

		const TPL_HIDDEN_INPUT_CHECKED =
			'<input class="hide" name="{name}" value="{value}" type="checkbox" ' +
			STR_CHECKED +
			' />';

		const TPL_HIDDEN_INPUT_UNCHECKED =
			'<input class="hide" name="{name}" value="{value}" type="checkbox"/>';

		const TPL_INPUT_SELECTOR = 'input[type="checkbox"][value="{value}"]';

		const SearchContainerSelect = A.Component.create({
			ATTRS: {
				bulkSelection: {
					validator: Lang.isBoolean,
					value: false,
				},

				keepSelection: {
					setter(keepSelection) {
						if (Lang.isString(keepSelection)) {
							keepSelection = new RegExp(keepSelection);
						}
						else if (!Lang.isRegExp(keepSelection)) {
							keepSelection = keepSelection
								? REGEX_MATCH_EVERYTHING
								: REGEX_MATCH_NOTHING;
						}

						return keepSelection;
					},
					value: REGEX_MATCH_EVERYTHING,
				},

				rowCheckerSelector: {
					validator: Lang.isString,
					value: '.click-selector',
				},

				rowClassNameActive: {
					validator: Lang.isString,
					value: 'active',
				},

				rowSelector: {
					validator: Lang.isString,
					value:
						'dd[data-selectable="true"],li[data-selectable="true"],tr[data-selectable="true"]',
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'searchcontainerselect',

			NS: 'select',

			prototype: {
				_addRestoreTask() {
					const instance = this;

					const host = instance.get(STR_HOST);

					Liferay.DOMTaskRunner.addTask({
						action: A.Plugin.SearchContainerSelect.restoreTask,
						condition:
							A.Plugin.SearchContainerSelect.testRestoreTask,
						params: {
							containerId: host.get(STR_CONTENT_BOX).attr('id'),
							rowClassNameActive: instance.get(
								STR_ROW_CLASS_NAME_ACTIVE
							),
							rowSelector: instance.get(STR_ROW_SELECTOR),
							searchContainerId: host.get('id'),
						},
					});
				},

				_addRestoreTaskState() {
					const instance = this;

					const host = instance.get(STR_HOST);

					const elements = [];

					const allElements = instance._getAllElements(false);

					allElements.each((item) => {
						elements.push({
							checked: item.attr('checked'),
							name: item.attr('name'),
							value: item.val(),
						});
					});

					Liferay.DOMTaskRunner.addTaskState({
						data: {
							bulkSelection: instance.get('bulkSelection'),
							elements,
							selector:
								instance.get(STR_ROW_SELECTOR) +
								' ' +
								STR_CHECKBOX_SELECTOR,
						},
						owner: host.get('id'),
					});
				},

				_getActions(elements) {
					const instance = this;

					const actions = elements
						.getDOMNodes()
						.map((node) => {
							return A.one(node).ancestor(
								instance.get(STR_ROW_SELECTOR)
							);
						})
						.filter((item) => {
							let itemActions;

							if (item) {
								itemActions = item.getData('actions');
							}

							return (
								itemActions !== undefined &&
								itemActions !== STR_ACTIONS_WILDCARD
							);
						})
						.map((item) => {
							return item.getData('actions').split(',');
						});

					return actions.reduce((commonActions, elementActions) => {
						return commonActions.filter((action) => {
							return elementActions.indexOf(action) !== -1;
						});
					}, actions[0]);
				},

				_getAllElements(onlySelected) {
					const instance = this;

					return instance._getElements(
						STR_CHECKBOX_SELECTOR,
						onlySelected
					);
				},

				_getCurrentPageElements(onlySelected) {
					const instance = this;

					return instance._getElements(
						instance.get(STR_ROW_SELECTOR) +
							' ' +
							STR_CHECKBOX_SELECTOR,
						onlySelected
					);
				},

				_getElements(selector, onlySelected) {
					const instance = this;

					const host = instance.get(STR_HOST);

					const checked = onlySelected ? ':' + STR_CHECKED : '';

					return host.get(STR_CONTENT_BOX).all(selector + checked);
				},

				_isActionUrl(url) {
					const uri = new URL(url);

					return Number(uri.searchParams.get('p_p_lifecycle')) === 1;
				},

				_notifyRowToggle() {
					const instance = this;

					const allSelectedElements = instance.getAllSelectedElements();

					const payload = {
						actions: instance._getActions(allSelectedElements),
						elements: {
							allElements: instance._getAllElements(),
							allSelectedElements,
							currentPageElements: instance._getCurrentPageElements(),
							currentPageSelectedElements: instance.getCurrentPageSelectedElements(),
						},
					};

					instance.get(STR_HOST).fire('rowToggled', payload);
				},

				_onClickRowSelector(config, event) {
					const instance = this;

					const row = event.currentTarget.ancestor(
						instance.get(STR_ROW_SELECTOR)
					);

					instance.toggleRow(config, row);
				},

				_onStartNavigate(event) {
					const instance = this;

					if (
						!instance._isActionUrl(event.path) &&
						instance.get('keepSelection').test(unescape(event.path))
					) {
						instance._addRestoreTask();
						instance._addRestoreTaskState();
					}
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				getAllSelectedElements() {
					const instance = this;

					return instance._getAllElements(true);
				},

				getCurrentPageElements() {
					const instance = this;

					return instance._getCurrentPageElements();
				},

				getCurrentPageSelectedElements() {
					const instance = this;

					return instance._getCurrentPageElements(true);
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					const hostContentBox = host.get(STR_CONTENT_BOX);

					instance.set(
						'bulkSelection',
						hostContentBox.getData('bulkSelection')
					);

					const toggleRowFn = A.bind(
						'_onClickRowSelector',
						instance,
						{
							toggleCheckbox: true,
						}
					);

					const toggleRowCSSFn = A.bind(
						'_onClickRowSelector',
						instance,
						{}
					);

					instance._eventHandles = [
						host
							.get(STR_CONTENT_BOX)
							.delegate(
								STR_CLICK,
								toggleRowCSSFn,
								instance.get(STR_ROW_SELECTOR) +
									' ' +
									STR_CHECKBOX_SELECTOR,
								instance
							),
						host
							.get(STR_CONTENT_BOX)
							.delegate(
								STR_CLICK,
								toggleRowFn,
								instance.get(STR_ROW_SELECTOR) +
									' ' +
									instance.get('rowCheckerSelector'),
								instance
							),
						Liferay.on(
							'startNavigate',
							instance._onStartNavigate,
							instance
						),
					];
				},

				isSelected(element) {
					return element.one(STR_CHECKBOX_SELECTOR).attr(STR_CHECKED);
				},

				toggleAllRows(selected, bulkSelection) {
					const instance = this;

					const elements = bulkSelection
						? instance._getAllElements()
						: instance._getCurrentPageElements();

					elements.attr(STR_CHECKED, selected);

					instance
						.get(STR_HOST)
						.get(STR_CONTENT_BOX)
						.all(instance.get(STR_ROW_SELECTOR))
						.toggleClass(
							instance.get(STR_ROW_CLASS_NAME_ACTIVE),
							selected
						);

					instance.set('bulkSelection', selected && bulkSelection);

					instance._notifyRowToggle();
				},

				toggleRow(config, row) {
					const instance = this;

					if (config && config.toggleCheckbox) {
						const checkbox = row.one(STR_CHECKBOX_SELECTOR);

						checkbox.attr(STR_CHECKED, !checkbox.attr(STR_CHECKED));
					}

					instance.set('bulkSelection', false);

					row.toggleClass(instance.get(STR_ROW_CLASS_NAME_ACTIVE));

					instance._notifyRowToggle();
				},
			},

			restoreTask(state, params, node) {
				const container = A.one(node).one('#' + params.containerId);

				container.setData('bulkSelection', state.data.bulkSelection);

				if (state.data.bulkSelection) {
					container.all(state.data.selector).each((input) => {
						input.attr(STR_CHECKED, true);
						input
							.ancestor(params.rowSelector)
							.addClass(params.rowClassNameActive);
					});
				}
				else {
					let offScreenElementsHtml = '';

					AArray.each(state.data.elements, (item) => {
						const input = container.one(
							Lang.sub(TPL_INPUT_SELECTOR, item)
						);

						if (input) {
							if (item.checked) {
								input.attr(STR_CHECKED, true);
								input
									.ancestor(params.rowSelector)
									.addClass(params.rowClassNameActive);
							}
						}
						else if (item.checked) {
							offScreenElementsHtml += Lang.sub(
								TPL_HIDDEN_INPUT_CHECKED,
								item
							);
						}
						else {
							offScreenElementsHtml += Lang.sub(
								TPL_HIDDEN_INPUT_UNCHECKED,
								item
							);
						}
					});

					container.append(offScreenElementsHtml);
				}
			},

			testRestoreTask(state, params, node) {
				return (
					state.owner === params.searchContainerId &&
					A.one(node).one('#' + params.containerId)
				);
			},
		});

		A.Plugin.SearchContainerSelect = SearchContainerSelect;
	},
	'',
	{
		requires: ['aui-component', 'aui-url', 'plugin'],
	}
);
