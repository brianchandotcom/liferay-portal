'use strict'

/**
 * Base class that component renderers should extend from. It defines the
 * required methods all renderers should have.
 */
;
define("frontend-js-metal-web@1.0.0/metal/src/component/ComponentRenderer", ['exports'], function (exports) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var ComponentRenderer = (function () {
		function ComponentRenderer() {
			_classCallCheck(this, ComponentRenderer);
		}

		ComponentRenderer.getSurfaceContent = function getSurfaceContent() {};

		return ComponentRenderer;
	})();

	exports.default = ComponentRenderer;
});
//# sourceMappingURL=ComponentRenderer.js.map