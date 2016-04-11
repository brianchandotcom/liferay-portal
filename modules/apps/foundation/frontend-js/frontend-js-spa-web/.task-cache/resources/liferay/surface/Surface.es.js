define("frontend-js-spa-web@1.0.6/liferay/surface/Surface.es", ['exports', 'metal/src/core', 'metal-dom/src/dom', 'senna/src/surface/Surface'], function (exports, _core, _dom, _Surface2) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _Surface3 = _interopRequireDefault(_Surface2);

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

	function _possibleConstructorReturn(self, call) {
		if (!self) {
			throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
		}

		return call && (typeof call === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
		if (typeof superClass !== "function" && superClass !== null) {
			throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
		}

		subClass.prototype = Object.create(superClass && superClass.prototype, {
			constructor: {
				value: subClass,
				enumerable: false,
				writable: true,
				configurable: true
			}
		});
		if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var LiferaySurface = function (_Surface) {
		_inherits(LiferaySurface, _Surface);

		function LiferaySurface() {
			_classCallCheck(this, LiferaySurface);

			return _possibleConstructorReturn(this, _Surface.apply(this, arguments));
		}

		LiferaySurface.prototype.addContent = function addContent(scrrenId, content) {
			if (_core2.default.isString(content)) {
				content = _dom2.default.buildFragment(content);
			}

			Liferay.DOMTaskRunner.runTasks(content);

			return _Surface.prototype.addContent.call(this, scrrenId, content);
		};

		return LiferaySurface;
	}(_Surface3.default);

	exports.default = LiferaySurface;
});
//# sourceMappingURL=Surface.es.js.map