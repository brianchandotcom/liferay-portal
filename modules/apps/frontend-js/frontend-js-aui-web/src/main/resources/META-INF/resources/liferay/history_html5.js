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
 * The History HTML5 Component.
 *
 * @deprecated As of Mueller (7.2.x), replaced by senna.js
 * @module liferay-history-html5
 */

AUI.add(
	'liferay-history-html5',
	A => {
		var AObject = A.Object;
		var History = Liferay.History;
		var Lang = A.Lang;
		var QueryString = A.QueryString;

		var isEmpty = AObject.isEmpty;
		var isValue = Lang.isValue;

		var WIN = A.config.win;

		var HISTORY = WIN.history;

		var LOCATION = WIN.location;

		A.mix(
			History.prototype,
			{
				_init(config) {
					var instance = this;

					var hash = LOCATION.hash;

					var locationHashValid =
						hash.indexOf(History.VALUE_SEPARATOR) != -1;

					if (locationHashValid) {
						HISTORY.replaceState(null, null, instance._updateURI());
					}

					config = config || {};

					if (
						!Object.prototype.hasOwnProperty.call(
							config,
							'initialState'
						)
					) {
						if (locationHashValid) {
							config.initialState = instance._parse(
								hash.substr(1)
							);
						}

						History.superclass._init.call(instance, config);
					}
				},

				_updateURI(state) {
					var instance = this;

					var uriData = [
						LOCATION.search.substr(1),
						LOCATION.hash.substr(1)
					];

					var hash = uriData[1];
					var query = uriData[0];

					var queryMap = {};

					if (query) {
						queryMap = instance._parse(query);
					}

					if (!state && hash) {
						var hashMap = instance._parse(hash);

						if (!isEmpty(hashMap)) {
							var protectedHashMap = {};

							state = hashMap;

							A.each(state, (value1, key1) => {
								instance.PROTECTED_HASH_KEYS.forEach(value2 => {
									if (value2.test(key1)) {
										delete state[key1];
										protectedHashMap[key1] = value1;
									}
								});
							});

							uriData.pop();

							uriData.push(
								'#',
								QueryString.stringify(protectedHashMap)
							);
						}
					}

					A.mix(queryMap, state, true);

					AObject.each(queryMap, (item, index) => {
						if (!isValue(item)) {
							delete queryMap[index];
						}
					});

					uriData[0] = QueryString.stringify(queryMap, {
						eq: History.VALUE_SEPARATOR,
						sep: History.PAIR_SEPARATOR
					});

					uriData.unshift(
						LOCATION.protocol,
						'//',
						LOCATION.host,
						LOCATION.pathname,
						'?'
					);

					return uriData.join('');
				},

				PROTECTED_HASH_KEYS: [/^liferay$/, /^tab$/, /^_\d+_tab$/],

				add(state, options) {
					var instance = this;

					options = options || {};

					options.url = options.url || instance._updateURI(state);

					state.liferay = true;

					return History.superclass.add.call(
						instance,
						state,
						options
					);
				}
			},
			true
		);
	},
	'',
	{
		requires: [
			'history-html5',
			'liferay-history',
			'querystring-stringify-simple'
		]
	}
);
