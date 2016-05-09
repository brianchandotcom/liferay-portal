define(['exports'], function (exports) {
	'use strict';

	/**
   * Debounces function execution.
   * @param {!function()} fn
   * @param {number} delay
   * @return {!function()}
   */

	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	function debounce(fn, delay) {
		var id;
		return function () {
			var args = arguments;
			clearTimeout(id);
			id = setTimeout(function () {
				fn.apply(null, args);
			}, delay);
		};
	}

	exports.default = debounce;
});
//# sourceMappingURL=debounce.js.map