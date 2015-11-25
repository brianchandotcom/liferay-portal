'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/soy/SoyTemplates", ['exports'], function (exports) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var templates = {};

	var SoyTemplates = (function () {
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
	})();

	exports.default = SoyTemplates;
});
//# sourceMappingURL=SoyTemplates.js.map