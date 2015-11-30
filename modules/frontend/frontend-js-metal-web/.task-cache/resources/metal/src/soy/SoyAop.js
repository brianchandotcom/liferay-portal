'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/soy/SoyAop", ['exports', 'metal/src/soy/SoyTemplates'], function (exports, _SoyTemplates) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _SoyTemplates2 = _interopRequireDefault(_SoyTemplates);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	var SoyAop = {
		interceptFn_: null,
		getOriginalFn: function getOriginalFn(fn) {
			return fn.originalFn ? fn.originalFn : fn;
		},
		handleTemplateCall_: function handleTemplateCall_(compName, templateName, originalFn, opt_data, opt_ignored, opt_ijData) {
			if (SoyAop.interceptFn_) {
				return SoyAop.interceptFn_.call(null, compName, templateName, originalFn, opt_data, opt_ignored, opt_ijData);
			} else {
				return originalFn.call(null, opt_data, opt_ignored, opt_ijData);
			}
		},
		registerTemplates: function registerTemplates(compName) {
			var compTemplates = _SoyTemplates2.default.get(compName);

			Object.keys(compTemplates).forEach(function (templateName) {
				var originalFn = compTemplates[templateName];

				if (!originalFn.originalFn) {
					compTemplates[templateName] = SoyAop.handleTemplateCall_.bind(null, compName, templateName, originalFn);
					compTemplates[templateName].originalFn = originalFn;
				}
			});
		},
		startInterception: function startInterception(fn) {
			SoyAop.interceptFn_ = fn;
		},
		stopInterception: function stopInterception() {
			SoyAop.interceptFn_ = null;
		}
	};
	exports.default = SoyAop;
});
//# sourceMappingURL=SoyAop.js.map