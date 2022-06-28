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
	(A) => {
		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;
		const History = Liferay.History;
		const Lang = A.Lang;
		const QueryString = A.QueryString;

		const isEmpty = AObject.isEmpty;
		const isValue = Lang.isValue;

		const WIN = A.config.win;

		const HISTORY = WIN.history;

		const LOCATION = WIN.location;

		A.mix(
			History.prototype,
			{
				_init(config) {
					const instance = this;

					const hash = LOCATION.hash;

					const locationHashValid =
						hash.indexOf(History.VALUE_SEPARATOR) !== -1;

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
					const instance = this;

					const uriData = [
						LOCATION.search.substr(1),
						LOCATION.hash.substr(1),
					];

					const hash = uriData[1];
					const query = uriData[0];

					let queryMap = {};

					if (query) {
						queryMap = instance._parse(query);
					}

					if (!state && hash) {
						const hashMap = instance._parse(hash);

						if (!isEmpty(hashMap)) {
							const protectedHashMap = {};

							state = hashMap;

							A.each(state, (value1, key1) => {
								instance.PROTECTED_HASH_KEYS.forEach(
									(value2) => {
										if (value2.test(key1)) {
											delete state[key1];
											protectedHashMap[key1] = value1;
										}
									}
								);
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
						sep: History.PAIR_SEPARATOR,
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
					const instance = this;

					options = options || {};

					options.url = options.url || instance._updateURI(state);

					state.liferay = true;

					return History.superclass.add.call(
						instance,
						state,
						options
					);
				},
			},
			true
		);
	},
	'',
	{
		requires: [
			'history-html5',
			'liferay-history',
			'querystring-stringify-simple',
		],
	}
);
