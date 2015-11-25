'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/metal/src/component/ComponentCollector", ['exports', 'metal/src/component/ComponentRegistry', 'metal/src/disposable/Disposable'], function (exports, _ComponentRegistry, _Disposable2) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _ComponentRegistry2 = _interopRequireDefault(_ComponentRegistry);

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

	var ComponentCollector = (function (_Disposable) {
		_inherits(ComponentCollector, _Disposable);

		function ComponentCollector() {
			_classCallCheck(this, ComponentCollector);

			return _possibleConstructorReturn(this, _Disposable.apply(this, arguments));
		}

		ComponentCollector.prototype.addComponent = function addComponent(component) {
			ComponentCollector.components[component.id] = component;
		};

		ComponentCollector.prototype.createComponent = function createComponent(componentName, id, opt_data) {
			var component = ComponentCollector.components[id];

			if (!component) {
				var ConstructorFn = _ComponentRegistry2.default.getConstructor(componentName);

				var data = opt_data || {};
				data.id = id;
				data.element = '#' + id;
				component = new ConstructorFn(data);
			}

			return component;
		};

		ComponentCollector.prototype.removeComponent = function removeComponent(component) {
			delete ComponentCollector.components[component.id];
		};

		ComponentCollector.prototype.updateComponent = function updateComponent(id, opt_data) {
			var component = ComponentCollector.components[id];

			if (component && opt_data) {
				component.setAttrs(opt_data);
			}

			return component;
		};

		return ComponentCollector;
	})(_Disposable3.default);

	ComponentCollector.components = {};
	exports.default = ComponentCollector;
});
//# sourceMappingURL=ComponentCollector.js.map