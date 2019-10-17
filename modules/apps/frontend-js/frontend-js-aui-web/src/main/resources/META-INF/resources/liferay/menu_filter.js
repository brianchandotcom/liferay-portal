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
	'liferay-menu-filter',
	A => {
		var Lang = A.Lang;

		var CSS_HIDE = 'hide';

		var STR_EMPTY = '';

		var TPL_INPUT_FILTER =
			'<li class="btn-toolbar search-panel">' +
			'<div class="form-group">' +
			'<input class="col-md-12 field focus menu-item-filter search-query" placeholder="{placeholder}" type="text" />' +
			'</div>' +
			'</li>';

		var MenuFilter = A.Component.create({
			ATTRS: {
				content: {
					setter: A.one
				},

				inputNode: {
					validator: Lang.isString,
					value: '.menu-item-filter'
				},

				strings: {
					validator: Lang.isObject,
					value: {
						placeholder: 'Search'
					}
				}
			},

			AUGMENTS: A.AutoCompleteBase,

			EXTENDS: A.Base,

			NAME: 'menufilter',

			prototype: {
				_filterMenu(event) {
					var instance = this;

					instance._menuItems.addClass(CSS_HIDE);

					event.results.forEach(result => {
						result.raw.node.removeClass(CSS_HIDE);
					});
				},

				_renderUI() {
					var instance = this;

					var node = instance.get('content');

					var menuItems = node.all('li');

					node.prepend(
						Lang.sub(TPL_INPUT_FILTER, {
							placeholder: instance.get('strings').placeholder
						})
					);

					instance._menuItems = menuItems;

					instance.on('results', instance._filterMenu, instance);
				},

				initializer() {
					var instance = this;

					instance._renderUI();
					instance._bindUIACBase();
					instance._syncUIACBase();
				},

				reset() {
					var instance = this;

					instance.get('inputNode').val(STR_EMPTY);

					instance._menuItems.removeClass(CSS_HIDE);
				}
			}
		});

		Liferay.MenuFilter = MenuFilter;
	},
	'',
	{
		requires: [
			'aui-component',
			'aui-node',
			'autocomplete-base',
			'autocomplete-filters'
		]
	}
);
