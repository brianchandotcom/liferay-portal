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

(function() {
	const getModule = window[Symbol.for('__LIFERAY_WEBPACK_GET_MODULE__')];

	getModule('frontend-js-react-web').then(
		function(frontendJsReactWeb) {
			Liferay.Loader.define(
				"frontend-js-react-web@5.0.0/js/index.es", ['module'],
				function (module) {
					module.exports = frontendJsReactWeb;
				}
			);
		}
	).catch(console.error);
})();
