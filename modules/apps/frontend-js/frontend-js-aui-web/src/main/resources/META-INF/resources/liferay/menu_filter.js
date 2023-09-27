/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-menu-filter',
	(A) => {
		const Lang = A.Lang;

		const CSS_HIDE = 'hide';

		const STR_EMPTY = '';

		const TPL_INPUT_FILTER =
			'<li class="btn-toolbar search-panel">' +
			'<div class="form-group">' +
			'<input class="col-md-12 field focus menu-item-filter search-query" placeholder="{placeholder}" type="text" />' +
			'</div>' +
			'</li>';

		const MenuFilter = A.Component.create({
			ATTRS: {
				content: {
					setter: A.one,
				},

				inputNode: {
					validator: Lang.isString,
					value: '.menu-item-filter',
				},

				menu: {
					validator: Lang.isObject,
					value: {},
				},

				strings: {
					validator: Lang.isObject,
					value: {
						placeholder: Liferay.Language.get('search'),
					},
				},
			},

			AUGMENTS: A.AutoCompleteBase,

			EXTENDS: A.Base,

			NAME: 'menufilter',

			prototype: {
				_filterMenu(event) {
					const instance = this;
					const menuInstance = instance.get('menu');

					instance._menuItems.addClass(CSS_HIDE);

					event.results.forEach((result) => {
						result.raw.node.removeClass(CSS_HIDE);
					});

					if (menuInstance) {
						menuInstance._focusManager.refresh();
					}
				},

				_renderUI() {
					const instance = this;

					const node = instance.get('content');

					const menuItems = node.all('li');

					node.prepend(
						Lang.sub(TPL_INPUT_FILTER, {
							placeholder: instance.get('strings').placeholder,
						})
					);

					instance._menuItems = menuItems;

					instance.on('results', instance._filterMenu, instance);
				},

				initializer() {
					const instance = this;

					instance._renderUI();
					instance._bindUIACBase();
					instance._syncUIACBase();
				},

				reset() {
					const instance = this;

					instance.get('inputNode').val(STR_EMPTY);

					instance._menuItems.removeClass(CSS_HIDE);
				},
			},
		});

		Liferay.MenuFilter = MenuFilter;
	},
	'',
	{
		requires: [
			'aui-component',
			'aui-node',
			'autocomplete-base',
			'autocomplete-filters',
		],
	}
);
