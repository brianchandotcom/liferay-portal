define("frontend-js-metal-web@1.0.0/metal-anim/src/Anim", ['exports', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/dom/features', 'metal/src/dom/events'], function (exports, _core, _dom, _features) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _features2 = _interopRequireDefault(_features);

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

	var Anim = function () {
		function Anim() {
			_classCallCheck(this, Anim);
		}

		Anim.emulateEnd = function emulateEnd(element, opt_durationMs) {
			if (this.getComputedDurationMs(element, 'animation') > this.getComputedDurationMs(element, 'transition')) {
				return this.emulateEnd_(element, 'animation', opt_durationMs);
			} else {
				return this.emulateEnd_(element, 'transition', opt_durationMs);
			}
		};

		Anim.emulateAnimationEnd = function emulateAnimationEnd(element, opt_durationMs) {
			return this.emulateEnd_(element, 'animation', opt_durationMs);
		};

		Anim.emulateTransitionEnd = function emulateTransitionEnd(element, opt_durationMs) {
			this.emulateEnd_(element, 'transition', opt_durationMs);
		};

		Anim.emulateEnd_ = function emulateEnd_(element, type, opt_durationMs) {
			var duration = opt_durationMs;

			if (!_core2.default.isDef(opt_durationMs)) {
				duration = this.getComputedDurationMs(element, type);
			}

			var delayed = setTimeout(function () {
				_dom2.default.triggerEvent(element, _features2.default.checkAnimationEventName()[type]);
			}, duration);

			var abort = function abort() {
				clearTimeout(delayed);
				hoistedEvtHandler.removeListener();
			};

			var hoistedEvtHandler = _dom2.default.once(element, type + 'end', abort);

			return {
				abort: abort
			};
		};

		Anim.getComputedDurationMs = function getComputedDurationMs(element, type) {
			return (parseFloat(window.getComputedStyle(element, null).getPropertyValue(type + '-duration')) || 0) * 1000;
		};

		return Anim;
	}();

	exports.default = Anim;
});
//# sourceMappingURL=Anim.js.map