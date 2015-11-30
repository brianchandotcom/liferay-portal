'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/dom/features", ['exports', 'metal/src/dom/dom', 'metal/src/string/string'], function (exports, _dom, _string) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _string2 = _interopRequireDefault(_string);

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

	var features = (function () {
		function features() {
			_classCallCheck(this, features);
		}

		features.checkAnimationEventName = function checkAnimationEventName() {
			if (features.animationEventName_ === undefined) {
				features.animationEventName_ = {
					animation: features.checkAnimationEventName_('animation'),
					transition: features.checkAnimationEventName_('transition')
				};
			}

			return features.animationEventName_;
		};

		features.checkAnimationEventName_ = function checkAnimationEventName_(type) {
			var prefixes = ['Webkit', 'MS', 'O', ''];

			var typeTitleCase = _string2.default.replaceInterval(type, 0, 1, type.substring(0, 1).toUpperCase());

			var suffixes = [typeTitleCase + 'End', typeTitleCase + 'End', typeTitleCase + 'End', type + 'end'];

			for (var i = 0; i < prefixes.length; i++) {
				if (features.animationElement_.style[prefixes[i] + typeTitleCase] !== undefined) {
					return prefixes[i].toLowerCase() + suffixes[i];
				}
			}

			return type + 'end';
		};

		features.checkAttrOrderChange = function checkAttrOrderChange() {
			if (features.attrOrderChange_ === undefined) {
				var originalContent = '<div data-component="" data-ref=""></div>';
				var element = document.createElement('div');

				_dom2.default.append(element, originalContent);

				features.attrOrderChange_ = originalContent !== element.innerHTML;
			}

			return features.attrOrderChange_;
		};

		return features;
	})();

	features.animationElement_ = document.createElement('div');
	features.animationEventName_ = undefined;
	features.attrOrderChange_ = undefined;
	exports.default = features;
});
//# sourceMappingURL=features.js.map