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
	'liferay-search-filter',
	A => {
		var Lang = A.Lang;

		var SearchImpl = A.Component.create({
			AUGMENTS: [A.AutoCompleteBase],

			EXTENDS: A.Base,

			NAME: 'searchimpl',

			prototype: {
				initializer() {
					this._bindUIACBase();
					this._syncUIACBase();
				}
			}
		});

		var SearchFilter = A.Component.create({
			ATTRS: {
				minQueryLength: {
					validator: Lang.isNumber,
					value: 0
				},

				nodeList: {
					setter: A.one
				},

				nodeSelector: {
					validator: Lang.isString
				},

				queryDelay: {
					validator: Lang.isNumber,
					value: 300
				},

				resultFilters: {
					setter: '_setResultFilters',
					value: 'phraseMatch'
				},

				resultTextLocator: {
					setter: '_setLocator',
					value: 'search'
				},

				searchDataLocator: {
					value: 'data-search'
				}
			},

			EXTENDS: SearchImpl,

			NAME: 'searchfilter',

			prototype: {
				initializer() {
					var instance = this;

					var nodeList = instance.get('nodeList');

					if (nodeList) {
						var nodeSelector = instance.get('nodeSelector');

						var nodes = nodeList.all(nodeSelector);

						var searchDataLocator = instance.get(
							'searchDataLocator'
						);

						var searchData = [];

						nodes.each(item => {
							searchData.push({
								node: item,
								search: item.attr(searchDataLocator)
							});
						});

						instance.set('source', searchData);

						instance._nodes = nodes;
						instance._searchData = searchData;
					}
				}
			}
		});

		Liferay.SearchFilter = SearchFilter;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters']
	}
);
