'use strict';

define("frontend-js-metal-web@1.0.0/metal-drag-drop/src/helpers/DragShim", ['exports', 'metal/src/dom/dom'], function (exports, _dom) {
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

	var DragShim = (function () {
		function DragShim() {
			_classCallCheck(this, DragShim);
		}

		DragShim.attachDocListeners = function attachDocListeners(useShim, listeners) {
			var element = document;

			if (useShim) {
				element = DragShim.getDocShim();
				element.style.display = 'block';
			}

			var eventTypes = Object.keys(listeners);
			return eventTypes.map(function (type) {
				var isTouch = type.substr(0, 5) === 'touch';
				return _dom2.default.on(isTouch ? document : element, type, listeners[type]);
			});
		};

		DragShim.getDocShim = function getDocShim() {
			if (!DragShim.docShim_) {
				DragShim.docShim_ = document.createElement('div');
				DragShim.docShim_.className = 'shim';
				DragShim.docShim_.style.position = 'fixed';
				DragShim.docShim_.style.top = 0;
				DragShim.docShim_.style.left = 0;
				DragShim.docShim_.style.width = '100%';
				DragShim.docShim_.style.height = '100%';
				DragShim.docShim_.style.display = 'none';
				DragShim.docShim_.style.opacity = 0;
				DragShim.docShim_.style.zIndex = 9999;

				_dom2.default.enterDocument(DragShim.docShim_);
			}

			return DragShim.docShim_;
		};

		DragShim.hideDocShim = function hideDocShim() {
			DragShim.getDocShim().style.display = 'none';
		};

		DragShim.reset = function reset() {
			if (DragShim.docShim_) {
				_dom2.default.exitDocument(DragShim.docShim_);

				DragShim.docShim_ = null;
			}
		};

		return DragShim;
	})();

	DragShim.docShim_ = null;
	exports.default = DragShim;
});
//# sourceMappingURL=DragShim.js.map