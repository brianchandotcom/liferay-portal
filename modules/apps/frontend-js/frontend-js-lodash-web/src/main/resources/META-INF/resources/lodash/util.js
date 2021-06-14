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

_.mixin({
	bindKeyRight(context, key) {
		var args = _.toArray(arguments).slice(2);

		args.unshift(_.bindKey(context, key));

		return _.partialRight.apply(_, args);
	},

	bindRight(fn, context) {
		var args = _.toArray(arguments).slice(2);

		args.unshift(_.bind(fn, context));

		return _.partialRight.apply(_, args);
	},

	cached(fn) {
		return _.memoize(fn, function () {
			return arguments.length > 1
				? Array.prototype.join.call(arguments, '_')
				: String(arguments[0]);
		});
	},
});

_.mixin(
	{
		namespace(object, path) {
			if (arguments.length === 1) {
				path = object;
				object = this;
			}

			if (_.isString(path)) {
				path = path.split('.');
			}

			for (var i = 0; i < path.length; i++) {
				var name = path[i];

				object[name] = object[name] || {};
				object = object[name];
			}

			return object;
		},
	},
	{
		chain: false,
	}
);
