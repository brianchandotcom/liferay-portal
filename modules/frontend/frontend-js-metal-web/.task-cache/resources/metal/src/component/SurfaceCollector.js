'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal/src/component/SurfaceCollector", ['exports', 'metal/src/object/object', 'metal/src/disposable/Disposable'], function (exports, _object, _Disposable2) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _object2 = _interopRequireDefault(_object);

	var _Disposable3 = _interopRequireDefault(_Disposable2);

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

		return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

	var SurfaceCollector = (function (_Disposable) {
		_inherits(SurfaceCollector, _Disposable);

		function SurfaceCollector() {
			_classCallCheck(this, SurfaceCollector);

			var _this = _possibleConstructorReturn(this, _Disposable.call(this));

			_this.surfaces_ = {};
			return _this;
		}

		SurfaceCollector.prototype.addSurface = function addSurface(surfaceElementId, opt_data) {
			if (this.surfaces_[surfaceElementId]) {
				this.updateSurface(surfaceElementId, opt_data);
			} else {
				this.surfaces_[surfaceElementId] = opt_data || {};
				this.surfaces_[surfaceElementId].surfaceElementId = surfaceElementId;
			}
		};

		SurfaceCollector.prototype.disposeInternal = function disposeInternal() {
			this.surfaces_ = null;
		};

		SurfaceCollector.prototype.getSurface = function getSurface(surfaceElementId) {
			return this.surfaces_[surfaceElementId] ? this.surfaces_[surfaceElementId] : null;
		};

		SurfaceCollector.prototype.removeAllSurfaces = function removeAllSurfaces() {
			this.surfaces_ = [];
		};

		SurfaceCollector.prototype.removeSurface = function removeSurface(surfaceElementId) {
			this.surfaces_[surfaceElementId] = null;
		};

		SurfaceCollector.prototype.updateSurface = function updateSurface(surfaceElementId, opt_data) {
			_object2.default.mixin(this.surfaces_[surfaceElementId], opt_data);
		};

		return SurfaceCollector;
	})(_Disposable3.default);

	SurfaceCollector.prototype.registerMetalComponent && SurfaceCollector.prototype.registerMetalComponent(SurfaceCollector, 'SurfaceCollector')
	exports.default = SurfaceCollector;
});
//# sourceMappingURL=SurfaceCollector.js.map