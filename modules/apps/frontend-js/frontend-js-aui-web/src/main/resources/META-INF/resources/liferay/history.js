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

/**
 * The History Utility, a utility for SPA.
 *
 * @deprecated As of Mueller (7.2.x), replaced by senna.js
 * @module liferay-history
 */

AUI.add(
	'liferay-history',
	A => {
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var isValue = Lang.isValue;

		var WIN = A.config.win;

		var LOCATION = WIN.location;

		var History = A.Component.create({
			EXTENDS: A.History,

			NAME: 'liferayhistory',

			PAIR_SEPARATOR: '&',

			VALUE_SEPARATOR: '=',

			prototype: {
				_parse: A.cached(str => {
					return QueryString.parse(
						str,
						History.PAIR_SEPARATOR,
						History.VALUE_SEPARATOR
					);
				}),

				get(key) {
					var instance = this;

					var value = History.superclass.get.apply(this, arguments);

					if (!isValue(value) && isValue(key)) {
						var query = LOCATION.search;

						var queryMap = instance._parse(query.substr(1));

						if (
							Object.prototype.hasOwnProperty.call(queryMap, key)
						) {
							value = queryMap[key];
						}
					}

					return value;
				}
			}
		});

		Liferay.History = History;
	},
	'',
	{
		requires: ['querystring-parse-simple']
	}
);
