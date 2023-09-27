/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

_.mixin({
	bindKeyRight(context, key) {
		const args = _.toArray(arguments).slice(2);

		args.unshift(_.bindKey(context, key));

		return _.partialRight.apply(_, args);
	},

	bindRight(fn, context) {
		const args = _.toArray(arguments).slice(2);

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

			for (let i = 0; i < path.length; i++) {
				const name = path[i];

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
