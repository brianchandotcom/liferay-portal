'use strict';

define("frontend-js-metal-web@1.0.0/metal-position/src/Geometry", ['exports'], function (exports) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var Geometry = (function () {
		function Geometry() {
			_classCallCheck(this, Geometry);
		}

		Geometry.intersectRect = function intersectRect(x0, y0, x1, y1, x2, y2, x3, y3) {
			return !(x2 > x1 || x3 < x0 || y2 > y1 || y3 < y0);
		};

		return Geometry;
	})();

	exports.default = Geometry;
});
//# sourceMappingURL=Geometry.js.map