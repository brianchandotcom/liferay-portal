define("frontend-js-metal-web@1.0.0/metal-soy/src/SoyTemplates", ['exports'], function (exports) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var templates = {};

	/**
  * Stores soy templates from components that use `SoyRenderer`. Soy files
  * compiled with gulp-metal automatically add their templates here when
  * imported.
  */

	var SoyTemplates = function () {
		function SoyTemplates() {
			_classCallCheck(this, SoyTemplates);
		}

		SoyTemplates.get = function get(opt_componentName, opt_templateName) {
			if (!opt_componentName) {
				return templates;
			} else if (!opt_templateName) {
				return templates[opt_componentName] || {};
			} else {
				return SoyTemplates.get(opt_componentName)[opt_templateName];
			}
		};

		SoyTemplates.set = function set(componentName, componentTemplates) {
			templates[componentName] = componentTemplates;
		};

		return SoyTemplates;
	}();

	exports.default = SoyTemplates;
});
//# sourceMappingURL=SoyTemplates.js.map