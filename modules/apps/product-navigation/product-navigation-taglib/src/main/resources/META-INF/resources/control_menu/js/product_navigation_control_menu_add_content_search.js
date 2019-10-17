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
	'liferay-product-navigation-control-menu-add-content-search',
	() => {
		var AddContentSearch = function() {};

		AddContentSearch.prototype = {
			_bindUISearch() {
				var instance = this;

				instance._eventHandles = instance._eventHandles || [];

				instance._eventHandles.push(
					instance._search.after(
						'query',
						instance._refreshContentList,
						instance
					),
					instance
						.get('inputNode')
						.on('keydown', instance._onSearchInputKeyDown, instance)
				);
			},

			_onSearchInputKeyDown(event) {
				if (event.isKey('ENTER')) {
					event.halt();
				}
			},

			initializer() {
				var instance = this;

				var contentSearch = new Liferay.SearchFilter({
					inputNode: instance.get('inputNode')
				});

				instance._search = contentSearch;

				instance._bindUISearch();
			}
		};

		Liferay.ControlMenu.AddContentSearch = AddContentSearch;
	},
	'',
	{
		requires: [
			'aui-base',
			'liferay-product-navigation-control-menu',
			'liferay-search-filter'
		]
	}
);
