'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/eval/globalEval", ['exports', 'metal/src/dom/dom'], function (exports, _dom) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var globalEval = (function () {
		function globalEval() {
			_classCallCheck(this, globalEval);
		}

		globalEval.run = function run(text) {
			var script = document.createElement('script');
			script.text = text;
			document.head.appendChild(script).parentNode.removeChild(script);
		};

		globalEval.runFile = function runFile(src) {
			var script = document.createElement('script');
			script.src = src;

			_dom2.default.on(script, 'load', function () {
				script.parentNode.removeChild(script);
			});

			_dom2.default.on(script, 'error', function () {
				script.parentNode.removeChild(script);
			});

			document.head.appendChild(script);
		};

		globalEval.runScript = function runScript(script) {
			if (script.parentNode) {
				script.parentNode.removeChild(script);
			}

			if (script.src) {
				globalEval.runFile(script.src);
			} else {
				globalEval.run(script.text);
			}
		};

		return globalEval;
	})();

	exports.default = globalEval;
});
//# sourceMappingURL=globalEval.js.map