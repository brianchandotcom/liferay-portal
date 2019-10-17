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
 * The Liferay Node Utility
 *
 * @deprecated As of Athanasius(7.3.x), with no direct replacement
 * @module liferay-node
 */

AUI.add(
	'liferay-node',
	A => {
		var getRegExp = A.DOM._getRegExp;

		var prefix = A.Lang.String.prefix;

		var formatSelectorNS = function(ns, selector) {
			return selector.replace(
				getRegExp('(#|\\[id=(\\"|\\\'))(?!' + ns + ')', 'g'),
				'$1' + ns
			);
		};

		A.mix(A.Node.prototype, {
			allNS(ns, selector) {
				var instance = this;

				return instance.all(formatSelectorNS(ns, selector));
			},

			oneNS(ns, selector) {
				var instance = this;

				return instance.one(formatSelectorNS(ns, selector));
			}
		});

		A.Node.formatSelectorNS = formatSelectorNS;

		A.queryNS = function(ns, selector, methodName) {
			return A[methodName || 'one'](formatSelectorNS(ns, selector));
		};

		A.oneNS = A.queryNS;

		A.allNS = function(ns, selector) {
			return A.queryNS(ns, selector, 'all');
		};

		A.byIdNS = function(ns, id) {
			return A.one('#' + prefix(ns, id));
		};
	},
	'',
	{
		requires: ['aui-node-base']
	}
);
