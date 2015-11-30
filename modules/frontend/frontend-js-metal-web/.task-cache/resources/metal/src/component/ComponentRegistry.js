'use strict';

define("frontend-js-metal-web@1.0.0/metal/src/component/ComponentRegistry", ['exports', 'metal/src/core'], function (exports, _core) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

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

	var ComponentRegistry = (function () {
		function ComponentRegistry() {
			_classCallCheck(this, ComponentRegistry);
		}

		ComponentRegistry.getConstructor = function getConstructor(name) {
			var constructorFn = ComponentRegistry.components_[name];

			if (!constructorFn) {
				console.error('There\'s no constructor registered for the component ' + 'named ' + name + '. Components need to be registered via ' + 'ComponentRegistry.register.');
			}

			return constructorFn;
		};

		ComponentRegistry.register = function register(constructorFn, opt_name) {
			var name = opt_name;

			if (!name) {
				if (constructorFn.hasOwnProperty('NAME')) {
					name = constructorFn.NAME;
				} else {
					name = _core2.default.getFunctionName(constructorFn);
				}
			}

			constructorFn.NAME = name;
			ComponentRegistry.components_[name] = constructorFn;
		};

		return ComponentRegistry;
	})();

	ComponentRegistry.components_ = {};
	exports.default = ComponentRegistry;
});
//# sourceMappingURL=ComponentRegistry.js.map