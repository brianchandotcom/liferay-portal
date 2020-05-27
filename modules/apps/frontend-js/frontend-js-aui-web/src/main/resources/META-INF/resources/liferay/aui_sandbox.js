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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
(function () {
	var ALLOY = YUI();

	if (ALLOY.html5shiv) {
		ALLOY.html5shiv();
	}

	var originalUse = ALLOY.use;

	ALLOY.use = function () {
		var args = Array.prototype.slice.call(arguments, 0);

		var currentURL = Liferay.currentURL;

		var originalCallback = args[args.length - 1];

		if (typeof originalCallback === 'function') {
			args[args.length - 1] = function () {
				if (Liferay.currentURL === currentURL) {
					originalCallback.apply(this, arguments);
				}
			};
		}

		return originalUse.apply(this, args);
	};

	window.AUI = function () {
		return ALLOY;
	};

	ALLOY.mix(AUI, YUI);

	AUI.$ = window.jQuery;
	AUI._ = window._;
})();
