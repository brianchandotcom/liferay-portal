define("frontend-js-metal-web@1.0.0/metal-component/src/ComponentCollector", ['exports', './ComponentRegistry', 'metal/src/metal'], function (exports, _ComponentRegistry, _metal) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _ComponentRegistry2 = _interopRequireDefault(_ComponentRegistry);

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

	var ComponentCollector = function (_Disposable) {
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
	}(_metal.Disposable);

	ComponentCollector.prototype.registerMetalComponent && ComponentCollector.prototype.registerMetalComponent(ComponentCollector, 'ComponentCollector')


	/**
  * Holds all collected components, indexed by their id.
  * @type {!Object<string, !Component>}
  */
	ComponentCollector.components = {};

	exports.default = ComponentCollector;
});
//# sourceMappingURL=ComponentCollector.js.map